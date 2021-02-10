package com.example.controller;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.entity.Building;
import com.example.entity.Company;
import com.example.entity.Employee;
import com.example.model.ApartmentManager;
import com.example.model.BuildingManager;
import com.example.model.CompanyManager;
import com.example.model.EmployeeManager;
import com.example.model.db.BuildingDAO;
import com.example.model.db.CompanyDAO;
import com.example.model.db.EmployeeDAO;


@Controller
public class EmployeeController {

	private String hireEmployeeStatus="";
	private String fireEmployeeStats="";
	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");  
	//create employee
	@RequestMapping(value = "/hireEmployee",method = RequestMethod.GET)
	public String prepHireEmployee(Model model,HttpSession session) {
		if(session.getAttribute("VAT_Number")==null) {	
			session.removeAttribute("VAT_Number");
			return "redirect:/login";
		}
		
		model.addAttribute("hireEmployeeStatus", hireEmployeeStatus);
		model.addAttribute("addEmp", new Employee());
		return "addEmployee";
	}
	
	@RequestMapping(value = "/hireEmployee",method = RequestMethod.POST)
	public String readyToHireEmployee(@ModelAttribute Employee employee,HttpSession session) {
		if(session.getAttribute("VAT_Number")==null) {	
			session.removeAttribute("VAT_Number");
			return "redirect:/login";
		}
		int companyId=CompanyManager.getInstance().returnCompanyId(session.getAttribute("VAT_Number").toString());
		int lastEmployeeId=EmployeeManager.getLastEmployeeId();
		lastEmployeeId++;
		employee.setDateOfCollectingTaxes("[non]");
		employee.setEmployeeId(lastEmployeeId);
		employee.setCompanyId(companyId);
		EmployeeManager.getInstance().addEmployee(lastEmployeeId, employee);
		EmployeeManager.setLastEmployeeId(lastEmployeeId++);
		EmployeeDAO.getInstance().saveEmployee(employee);
		CompanyManager.getInstance().calculateBuildings(companyId);
		return "redirect:/dashboard";
	}
	//end of creating employee
	
	
	//show all employees
	@RequestMapping(value = "/showEmployees",method = RequestMethod.GET)
	public String getAllEmployees(Model model,HttpSession session) {
		if(session.getAttribute("VAT_Number")==null) {	
			session.removeAttribute("VAT_Number");
			return "redirect:/login";
		}
		
		int companyId=CompanyManager.getInstance().returnCompanyId(session.getAttribute("VAT_Number").toString());
		model.addAttribute("employees", EmployeeManager.getInstance().getAllEmployeesByCompanyId(companyId));
		model.addAttribute("hideAllEmployees", "Hide all employees");
		return "companyDashboard";
	}

	@RequestMapping(value = "/hideEmployees",method = RequestMethod.GET)
	public String hideAllEmployees() {
		return "redirect:/dashboard";
	}
	//end of showing employees 
	
	
	//change employee details
	@RequestMapping(value = "/changeEmployeeDetails/{employeeId}", method =RequestMethod.GET)
	public String prepForChangeEmployeeDetails(@PathVariable("employeeId") Integer employeeId,Model model,HttpSession session) {
		if(session.getAttribute("VAT_Number")==null) {	
			session.removeAttribute("VAT_Number");
			return "redirect:/login";
		}
		int companyId=CompanyManager.getInstance().returnCompanyId(session.getAttribute("VAT_Number").toString());
		if(!EmployeeManager.getInstance().returnTrueIfThisEmployeeThisCompanyId(companyId,employeeId)) {
			return "redirect:/dashboard";
		}
		model.addAttribute("employee", EmployeeManager.getInstance().returnEmployeeDetailsByEmployeeId(employeeId));
		model.addAttribute("prepForChangeEmployeeDetails", true);
		model.addAttribute("changeEmpDetails", EmployeeManager.getInstance().returnEmployeeDetailsByEmployeeId(employeeId));
		return "changeDetails";		
	}
	
	@RequestMapping(value = "/changeEmployeeDetails/{employeeId}", method =RequestMethod.POST)
	public String readyForChangeEmployeeDetails(@PathVariable("employeeId") Integer employeeId,@ModelAttribute Employee employee,HttpSession session) {
		if(session.getAttribute("VAT_Number")==null) {	
			session.removeAttribute("VAT_Number");
			return "redirect:/login";
		}
		int companyId=CompanyManager.getInstance().returnCompanyId(session.getAttribute("VAT_Number").toString());
		if(!EmployeeManager.getInstance().returnTrueIfThisEmployeeThisCompanyId(companyId,employeeId)) {
			return "redirect:/dashboard";
		}
		if(EmployeeManager.getInstance().changeEmployeeDetails(companyId,employee)==null) {
			return "redirect:/dashboard";
		}
		EmployeeManager.getInstance().changeEmployeeDetails(companyId,employee);
		EmployeeDAO.getInstance().changeEmployeeDetails(employeeId, EmployeeManager.getInstance().changeEmployeeDetails(companyId,employee));
		return "redirect:/dashboard";
	}
	
	
	@RequestMapping(value = "changeEmployeeDetails/dashboard",method = RequestMethod.GET)
	public String backToDashboard() {
		return "redirect:/dashboard";
	}
	//end change employee details
	
	
	
	//fire employee
	@RequestMapping(value = "/fireEmployee/{employeeId}",method=RequestMethod.GET)
	public String prepForFireEmployee(@PathVariable("employeeId") Integer employeeId,Model model,HttpSession session) {
		if(session.getAttribute("VAT_Number")==null) {	
			session.removeAttribute("VAT_Number");
			return "redirect:/login";
		}
		int companyId=CompanyManager.getInstance().returnCompanyId(session.getAttribute("VAT_Number").toString());
		if(!EmployeeManager.getInstance().returnTrueIfThisEmployeeThisCompanyId(companyId,employeeId)) {
			return "redirect:/dashboard";
		}
		model.addAttribute("fireEmployeeStats", fireEmployeeStats);
		model.addAttribute("fireEmployeeRequest", true);
		model.addAttribute("confPass", new Company());
		return "removeBuilding";
	}
	
	@RequestMapping(value = "/fireEmployee/{employeeId}",method=RequestMethod.POST)
	public String readyForFireEmployee(@PathVariable("employeeId") Integer employeeId,@ModelAttribute Company company,HttpSession session) {
		if(session.getAttribute("VAT_Number")==null) {	
			session.removeAttribute("VAT_Number");
			return "redirect:/login";
		}
		Company com=CompanyManager.getInstance().returnCompanyByVATNumber(session.getAttribute("VAT_Number").toString());
		if(!EmployeeManager.getInstance().returnTrueIfThisEmployeeThisCompanyId(com.getComapnyId(),employeeId)) {
			return "redirect:/dashboard";
		}
		if(!company.getPassword().equals(company.getConfirmPassword())) {
			fireEmployeeStats="Passwords do not match";
			return "redirect:/fireEmployee/"+employeeId;
		} 
		if(!CompanyManager.getInstance().checkPassword(com,company.getPassword())) {
			fireEmployeeStats="Wrong password";
			return "redirect:/fireEmployee/"+employeeId;
		}
		EmployeeManager.getInstance().deleteEmployee(employeeId);
		EmployeeDAO.getInstance().deleteEmployee(employeeId);
		CompanyManager.getInstance().calculateBuildings(com.getComapnyId());
		return "redirect:/dashboard";
	}
	
	@RequestMapping(value = "/fireEmployee/backToDashboard",method=RequestMethod.GET)
	public String backFromDeleteBuilding() {
		return "redirect:/dashboard";
	}
	//end fire employee
	
	//shows employee and buildings
	@RequestMapping(value = "employeeAndBuildings/{employeeId}",method = RequestMethod.GET)
	public String showEmployeeBuildings(@PathVariable("employeeId") Integer employeeId,Model model,HttpSession session) {
		if(session.getAttribute("VAT_Number")==null) {	
			session.removeAttribute("VAT_Number");
			return "redirect:/login";
		}
		int companyId=CompanyManager.getInstance().returnCompanyId(session.getAttribute("VAT_Number").toString());
		if(!EmployeeManager.getInstance().returnTrueIfThisEmployeeThisCompanyId(companyId,employeeId)) {
			return "redirect:/dashboard";
		}
		model.addAttribute("hideAll", "Hide all information");
		model.addAttribute("backToEmployeeInforamtion", "Back to employees information");
		model.addAttribute("showEployeeDetailsAndBuildingsInfo", true);
		model.addAttribute("employeeDetails", EmployeeManager.getInstance().returnEmployeeDetailsByEmployeeId(employeeId));
		if(EmployeeManager.getInstance().returnEmployeeDetailsByEmployeeId(employeeId).getEmployeeBuildings()==null)
			CompanyManager.getInstance().calculateBuildings(companyId);
		model.addAttribute("showEmployeeBuildings", EmployeeManager.getInstance().returnEmployeeDetailsByEmployeeId(employeeId).getEmployeeBuildings());
		System.out.println(EmployeeManager.getInstance().returnEmployeeDetailsByEmployeeId(employeeId).getEmployeeBuildings());
		return "companyDashboard";
	}
	
	@RequestMapping(value = "/employeeAndBuildings/hideAll",method = RequestMethod.GET)
	public String goBackToDash() {
		return "redirect:/dashboard";
	}
	
	@RequestMapping(value = "employeeAndBuildings/showEmployees",method =RequestMethod.GET )
	public String goBackToEmployeeInfo() {
		return "redirect:/showEmployees";
	}
	//end shows employee and buildings



	//collect tax
	@RequestMapping(value = "/employeeAndBuildings/collectTaxForBuilding/{buildingId}/{employeeId}",method = RequestMethod.GET)
	public String collectTax(@PathVariable("buildingId") Integer buildingId,@PathVariable("employeeId") Integer employeeId,HttpSession session,Model model) {
		if(session.getAttribute("VAT_Number")==null) {	
			session.removeAttribute("VAT_Number");
			return "redirect:/login";
		}
		Company company=CompanyManager.getInstance().returnCompanyByVATNumber(session.getAttribute("VAT_Number").toString());
		if(!EmployeeManager.getInstance().returnTrueIfThisEmployeeThisCompanyId(company.getComapnyId(),employeeId)) {
			return "redirect:/dashboard";
		}
		if(!BuildingManager.getInstance().checkIfThisCompnayIdServeThisBuildingId(company.getComapnyId(), buildingId)) {
			return "redirect:/dashboard";
		}
		if(ApartmentManager.getInstance().returnApartmentsWithBuildingId(buildingId).size()==0) {
			EmployeeManager.setTaxStatusMsg("No buildings added");
			return "redirect:/dashboard";
		}
		Employee employee=EmployeeManager.getInstance().returnEmployeeDetailsByEmployeeId(employeeId);
		Building building=BuildingManager.getInstance().returnOnlyOneBuildingByBuildingId(buildingId);
		
		LocalDateTime now = LocalDateTime.now(); 
		building.setDateOfCollectingTaxes(dtf.format(now));
		
		double sumFromBuilding=EmployeeManager.getInstance().returnTaxForBuilding(ApartmentManager.getInstance().returnApartmentsWithBuildingId(buildingId),
				company.getPriceArea(), company.getPricePet(), company.getPricePerson(),company,employee,building,dtf.format(now));
		
		EmployeeManager.setTaxStatusMsg("added: "+sumFromBuilding+"BGN on building city: "+building.getCity()+" address: "+building.getAddress());
		double sumEmp=employee.getCollectedTaxes()+sumFromBuilding;
		employee.setCollectedTaxes(sumEmp);
		sumFromBuilding+=company.getProfit();
		company.setProfit(sumFromBuilding);
		
		CompanyDAO.getInstance().addProfit(company);
		
		BuildingDAO.getInstance().dateOfCollectingTaxes(buildingId, dtf.format(now));
		
		employee.setDateOfCollectingTaxes(dtf.format(now));
		EmployeeDAO.getInstance().collectedTaxes(employeeId, employee);
		
		
		return "redirect:/dashboard";
	}
	//end collect tax
	
	
	//show employees sorted by name and buildings
	@RequestMapping(value = "/showEmployeesByNameAndBuildings",method = RequestMethod.GET)
	public String returnEmployeesByNameAndByBuildings(HttpSession session,Model model) {
		if(session.getAttribute("VAT_Number")==null) {	
			session.removeAttribute("VAT_Number");
			return "redirect:/login";
		}
		Company company=CompanyManager.getInstance().returnCompanyByVATNumber(session.getAttribute("VAT_Number").toString());
		
		model.addAttribute("returnEmployeesByNameAndByBuildings", "Hide employees");
		model.addAttribute("employeesByNameAndBuildings", EmployeeManager.getInstance().returnEmployeesByNameAndBuildings(company.getComapnyId()));
		
		return "companyDashboard";
	}

	@RequestMapping(value = "/backToDash")
	public String backTodash() {
		return "redirect:/dashboard";
	}
	
}

