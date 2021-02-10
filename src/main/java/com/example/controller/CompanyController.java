package com.example.controller;




import javax.servlet.http.HttpSession;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.example.entity.Company;
import com.example.model.CompanyManager;
import com.example.model.EmployeeManager;
import com.example.model.db.CompanyDAO;


@Controller
public class CompanyController {

	
	@RequestMapping(value="/dashboard")
	public String returnCompanyDashBoard(Model model,HttpSession session) {
		if(session.getAttribute("VAT_Number")==null) {	
			session.removeAttribute("VAT_Number");
			return "redirect:/login";
		}
		Company company=CompanyManager.getInstance().returnCompanyByVATNumber(session.getAttribute("VAT_Number").toString());
		model.addAttribute("companyName", company.getCompanyName());
		model.addAttribute("priceForArea", company.getPriceArea());
		model.addAttribute("priceForPet", company.getPricePet());
		model.addAttribute("showPrices",true);
		model.addAttribute("priceForPerson", company.getPricePerson());
		model.addAttribute("collectiongTaxesStatus", EmployeeManager.getTaxStatusMsg());
		model.addAttribute("companyProfitStatus", " profit: "+company.getProfit());
		model.addAttribute("sortByProft","Show companies by profit");
		model.addAttribute("showEmployeesByNameAndBuildings", "Show employees by name and buildings");
		
		if(company.getProfit()>0.0)
		model.addAttribute("showAllCollectedTaxes", "Show all collected taxes");
		model.addAttribute("addNewBuilding", "Add new Building");
		model.addAttribute("showAllBuildings", "Show all buildinga");
		model.addAttribute("hireEmployee", "Hire an employee");
		model.addAttribute("showAllEmployees", "Show all employees");
		model.addAttribute("changeCompanyPrice", "Change price");
		return "companyDashboard";
	}
	
	//change price
	@RequestMapping(value = "/changePrice",method = RequestMethod.GET)
	public String prepForChangePrice(Model model,HttpSession session) {
		if(session.getAttribute("VAT_Number")==null) {	
			session.removeAttribute("VAT_Number");
			return "redirect:/login";
		}
		Company company=CompanyManager.getInstance().returnCompanyByVATNumber(session.getAttribute("VAT_Number").toString());
		model.addAttribute("changePriceRequest", true);
		model.addAttribute("changePrice", company);///////
		model.addAttribute("company", company);
		
		return "changeDetails";
	}
	
	@RequestMapping(value = "/changePrice",method = RequestMethod.POST)
	public String readyForChangePrice(@ModelAttribute Company newCompany,Model model,HttpSession session) {
		if(session.getAttribute("VAT_Number")==null) {	
			session.removeAttribute("VAT_Number");
			return "redirect:/login";
		}
		Company company=CompanyManager.getInstance().returnCompanyByVATNumber(session.getAttribute("VAT_Number").toString());
		company.setPriceArea(newCompany.getPriceArea());
		company.setPricePet(newCompany.getPricePet());
		company.setPricePerson(newCompany.getPricePerson());
		CompanyDAO.getInstance().changePrice(company, company.getComapnyId());
		return "redirect:/dashboard";
	}
	//end change price
	 
	
	//show companies by profit
	@RequestMapping(value="/showCompaniesByProfit",method = RequestMethod.GET)
	public String returnCompaniesByProftit(Model model,HttpSession	session) {
		if(session.getAttribute("VAT_Number")==null) {	
			session.removeAttribute("VAT_Number");
			return "redirect:/login";
		}
		model.addAttribute("hideAllCompanies", "Hide all companies");
		model.addAttribute("showCompaniesSortByProft", CompanyManager.getInstance().getSortedByProfit());
		return "companyDashboard";
	}
	
	@RequestMapping(value="/hideCompaniesByProfit",method = RequestMethod.GET)
	public String hideCompaniesByProftit(HttpSession	session) {
		if(session.getAttribute("VAT_Number")==null) {	
			session.removeAttribute("VAT_Number");
			return "redirect:/login";
		}
		return "redirect:/dashboard";
	}
	// end show companies by profit
	
	
	
	
}
