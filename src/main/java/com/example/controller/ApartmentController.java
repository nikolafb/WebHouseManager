package com.example.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.entity.Apartment;
import com.example.entity.Building;
import com.example.model.ApartmentManager;
import com.example.model.BuildingManager;
import com.example.model.CompanyManager;
import com.example.model.db.ApartmentDAO;

@Controller
public class ApartmentController {

	private ApartmentManager am=ApartmentManager.getInstance();
	private CompanyManager cm=CompanyManager.getInstance();
	private BuildingManager bm=BuildingManager.getInstance();
	private String status="";
	private String changeApartmentDetailsStatus="";
	
	@RequestMapping(value="/apartment/{buildingId}",method = RequestMethod.GET)
	public String prepToAddNewApartment(@PathVariable("buildingId") Integer buildingId, Model model,HttpSession session) {
		if(session.getAttribute("VAT_Number")==null) {	
			session.removeAttribute("VAT_Number");
			return "redirect:/login";
		}
		
		int companyId=cm.returnCompanyId(session.getAttribute("VAT_Number").toString());
		if(companyId==-1) {
			System.err.println("ApartmentController-> returnCompanyID- return -1!");
			return "redirect:/";
		}
		if(bm.checkIfThisCompnayIdServeThisBuildingId(companyId, buildingId)) {
			model.addAttribute("status",status);
			model.addAttribute("addApartment", new Apartment());
			return "addApartment";
		}
		else {
			return "companyDashboard";
		}
	}
	
	@RequestMapping(value="/apartment/{buildingId}",method = RequestMethod.POST)
	public String addApartment(@ModelAttribute Apartment apartment,@PathVariable("buildingId") Integer buildingId,HttpSession session) {
		if(session.getAttribute("VAT_Number")==null) {	
			session.removeAttribute("VAT_Number");
			return "redirect:/login";
		}
		if(apartment.getMembers()<=apartment.getKids()) {
			status="Members cannot be less than kids";
			return "redirect:/apartment/"+buildingId;
		}
		if(am.ifThisapartmentNumberAlreadyExists(apartment.getApartmentNumber())) {
			status="This apartment already been added";
			return "redirect:/apartment/"+buildingId;
		}
		
		int companyId=cm.returnCompanyId(session.getAttribute("VAT_Number").toString());
		if(companyId==-1) {
			System.err.println("ApartmentController-> returnCompanyID- return -1!");
			return "redirect:/";
		}
		if(!bm.checkIfThisCompnayIdServeThisBuildingId(companyId, buildingId)) {
			return "redirect:/apartment"+buildingId;
		}
		apartment.setBuildingId(buildingId);
		int lastApartmentId=ApartmentManager.getLastApartmentgId();
		lastApartmentId++;
		apartment.setApartmentId(lastApartmentId);
		am.addApartment(lastApartmentId, apartment);
		ApartmentManager.setLastApartmentgId(lastApartmentId++);
		ApartmentDAO.getInstance().saveApartment(apartment);
		
		return "redirect:/dashboard";
	}
	
	
	
	@RequestMapping(value="/showApartments/{buildingId}",method = RequestMethod.GET)
	public String returnBuildingWithAparment(@PathVariable("buildingId") Integer buildingId,Model model,HttpSession session) {
		if(session.getAttribute("VAT_Number")==null) {	
			session.removeAttribute("VAT_Number");
			return "redirect:/login";
		}
		int compId=CompanyManager.getInstance().returnCompanyId(session.getAttribute("VAT_Number").toString());
		if(compId==-1) {
			System.err.println("BuildingController-> returnCompanyID- return -1!");
			return "redirect:/";
		}
		int companyId=cm.returnCompanyId(session.getAttribute("VAT_Number").toString());
		if(companyId==-1) {
			System.err.println("ApartmentController-> returnCompanyID- return -1!");
			return "redirect:/";
		}
		if(!bm.checkIfThisCompnayIdServeThisBuildingId(companyId, buildingId)) {
			return "redirect:/dashboard";
		}
		if(bm.returnOnlyOneBuildingByBuildingId(buildingId)==null) {
			return "redirect:/dashboard";//if there is no building this building id
		}
		if(am.returnApartmentsWithBuildingId(buildingId).size()==0) {
			return "redirect:/dashboard"; // if there is no apartments
		}
		else
		model.addAttribute("apartments", am.returnApartmentsWithBuildingId(buildingId));
		Building building=bm.returnOnlyOneBuildingByBuildingId(buildingId);
		model.addAttribute("buildingInfo", true);
		//String buildingDetails="City:"+building.getCity()+"  Address "+building.getAddress()+"  Floors  "+building.getFloors()+"  Build up area: "+building.getBuiltUpArea();
		model.addAttribute("building", building);
		model.addAttribute("hideApartments", "Hide all apartments");
		
		return "companyDashboard";
	} 
	
	//show apartment owner and kids sorted
	@RequestMapping(value = "showApartments/sortByNameAndKids/{buildingId}",method = RequestMethod.GET)
	public String returnApartmentsSortedByNameAndKids(@PathVariable("buildingId") Integer buildingId,Model model,HttpSession session) {
		if(session.getAttribute("VAT_Number")==null) {	
			session.removeAttribute("VAT_Number");
			return "redirect:/login";
		}
		int compId=CompanyManager.getInstance().returnCompanyId(session.getAttribute("VAT_Number").toString());
		if(compId==-1) {
			System.err.println("BuildingController-> returnCompanyID- return -1!");
			return "redirect:/";
		}
		int companyId=cm.returnCompanyId(session.getAttribute("VAT_Number").toString());
		if(companyId==-1) {
			System.err.println("ApartmentController-> returnCompanyID- return -1!");
			return "redirect:/";
		}
		if(!bm.checkIfThisCompnayIdServeThisBuildingId(companyId, buildingId)) {
			return "redirect:/dashboard";
		}
		if(bm.returnOnlyOneBuildingByBuildingId(buildingId)==null) {
			return "redirect:/dashboard";//if there is no building with this building id
		}
		if(am.returnApartmentsWithBuildingId(buildingId).size()==0) {
			return "redirect:/dashboard"; // if there is no apartments
		}
		else
		model.addAttribute("apartments", am.returnApartmentsWithBuildingIdSortedByOwnerAndKids(buildingId));
		Building building=bm.returnOnlyOneBuildingByBuildingId(buildingId);
		model.addAttribute("buildingInfoSorted", true);
		
		model.addAttribute("building", building);
		
		return "companyDashboard";	
	}
	
	@RequestMapping(value = "/showApartments/sortByNameAndKids/backShowBuildings",method = RequestMethod.GET)
	public String redirectBackToBuildings() {
		return "redirect:/showBuildings";
	}
	
	@RequestMapping(value = "/showApartments/sortByNameAndKids/backToDash",method = RequestMethod.GET)
	public String backToDash() {
		return "redirect:/dashboard";
	}
	//end show apartment owner and kids sorted
	
	
	
	//change details for some apartment
	@RequestMapping(value="showApartments/changeApartmentDetails/{apartmentId}",method = RequestMethod.GET)
	public String prepToChangeApartmentDetails(@PathVariable("apartmentId") Integer apartmentId,Model model,HttpSession session) {
		if(session.getAttribute("VAT_Number")==null) {	
			session.removeAttribute("VAT_Number");
			return "redirect:/login";
		}
		
		
		if(am.returnApartmentDetails(apartmentId)==null) {
			return "redirect:/dashboard";
		}
		
		Apartment apartment=am.returnApartmentDetails(apartmentId);
			
		int companyId=cm.returnCompanyId(session.getAttribute("VAT_Number").toString());
		if(companyId==-1) {
			System.err.println("ApartmentController-> returnCompanyID- return -1!");
			return "redirect:/";
		}
		if(!bm.checkIfThisCompnayIdServeThisBuildingId(companyId, apartment.getBuildingId())) {
			return "redirect:/dashboard";
		}
		model.addAttribute("requestForChangeApartmentDetails", true);
		model.addAttribute("changeApartmentDetailsStatus", changeApartmentDetailsStatus);
		model.addAttribute("apartment", apartment);
		model.addAttribute("changeDetails", new Apartment());
		return "changeDetails";
	}
	
	

		@RequestMapping(value="showApartments/changeApartmentDetails/{apartmentId}",method = RequestMethod.POST)
		public String readyToChangeApartmentDetails(@PathVariable("apartmentId") Integer apartmentId,@ModelAttribute Apartment newApartment,HttpSession session) {
			if(session.getAttribute("VAT_Number")==null) {	
				session.removeAttribute("VAT_Number");
				return "redirect:/login";
			}
			
			
			if(am.returnApartmentDetails(apartmentId)==null) {
				return "redirect:/dashboard";
			}
			if(newApartment.getMembers()<=newApartment.getKids()) {
				changeApartmentDetailsStatus="Members cannot be more or equals as the kids";
				return "redirect:/showApartments/changeApartmentDetails/"+apartmentId;
			}
			
			Apartment apartment=am.returnApartmentDetails(apartmentId);
				
			int companyId=cm.returnCompanyId(session.getAttribute("VAT_Number").toString());
			if(companyId==-1) {
				System.err.println("ApartmentController-> returnCompanyID- return -1!");
				return "redirect:/";
			}
			if(!bm.checkIfThisCompnayIdServeThisBuildingId(companyId, apartment.getBuildingId())) {
				return "redirect:/dashboard";
			}
			if(am.ifThisapartmentNumberAlreadyExists(newApartment.getApartmentNumber())) {
				changeApartmentDetailsStatus="This apartment number already been added";
				return "redirect:/showApartments/changeApartmentDetails/"+apartmentId;
			}
			if(am.changeApartmentDetails(newApartment, apartmentId)) {//it should return false if its okey
				changeApartmentDetailsStatus="Something went wrong";
				return "redirect:/showApartments/changeApartmentDetails/"+apartmentId;
			}
			ApartmentDAO.getInstance().changeApartmentDetails(newApartment, apartmentId);
			return "redirect:/dashboard";
		}
		
		@RequestMapping(value = "/showApartments/changeApartmentDetails/dashboard",method = RequestMethod.GET)
		public String backFromChangeApartmentDetails() {
			return "redirect:/dashboard";
		}
		//end of change details for some apartment
	
	
	
	@RequestMapping(value="/showApartments/hideApartments",method = RequestMethod.GET)
	public String hideApartments() {
		 return "redirect:/dashboard";
	}

	@RequestMapping(value="apartment/dashboard",method = RequestMethod.GET)
	public String backFromAddNewApartment() {
		 return "redirect:/dashboard";
	}
	
}
