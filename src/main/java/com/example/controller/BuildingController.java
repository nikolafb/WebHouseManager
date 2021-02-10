package com.example.controller;



import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.entity.Building;
import com.example.entity.Company;
import com.example.model.BuildingManager;
import com.example.model.CompanyManager;
import com.example.model.db.BuildingDAO;

@Controller
public class BuildingController {

	private BuildingManager bm=BuildingManager.getInstance();
	private String status;
	private String deleteBulding="";
	
	@RequestMapping(value="/createBuilding",method =RequestMethod.GET)
	public String prepToAddBuilding(Model model,HttpSession session)  {
		if(session.getAttribute("VAT_Number")==null) {	
			//session=request.getSession();
			session.removeAttribute("VAT_Number");
			return "redirect:/login";
		}
		model.addAttribute("status", status);
		model.addAttribute("add", new Building());
		return "addBuilding";
	}
	
	@RequestMapping(value="createBuilding",method = RequestMethod.POST)
	public String addNewBuilding(@ModelAttribute Building building,HttpSession session) {
		if(session.getAttribute("VAT_Number")==null) {	
			//session=request.getSession();
			session.removeAttribute("VAT_Number");
			return "redirect:/login";
		}
		if(building.getCity().isEmpty()||building.getCity().equals("null")) {
			status="City is empty";
			return "redirect:/createBuilding";
		}
		if(building.getAddress().isEmpty()||building.getAddress().equals("null")) {
			status="Address is empty";
			return "redirect:/createBuilding";
		}
		if(building.getFloors()<=0) {
			status="Floors can not be 0";
			return "redirect:/createBuilding";
		}
		if(building.getBuiltUpArea().isEmpty()||building.getBuiltUpArea().equals("null")) {
			status="Build up area is empty";
			return "redirect:/createBuilding";
		}
		int compId=CompanyManager.getInstance().returnCompanyId(session.getAttribute("VAT_Number").toString());
		if(compId==-1) {
			System.err.println("BuildingController-> returnCompanyID- return -1!");
			return "redirect:/";
		}
		building.setCompanyId(compId);
		int lastBuildingId=BuildingManager.getInstance().getLastBuildingId();
		lastBuildingId++;
		building.setBuildingId(lastBuildingId);
		bm.addBuilding(lastBuildingId, building);
		BuildingManager.setLastBuildingId(lastBuildingId++);
		BuildingDAO.getInstance().saveBuilding(building);
		return "redirect:/dashboard";
	}
	
	@RequestMapping(value="/back",method = RequestMethod.GET)
	public String backPage() {
		return "redirect:/dashboard";
	}
	
	
	@RequestMapping(value="showBuildings",method = RequestMethod.GET)
	public String returnBuildings(Model model,HttpSession session) {
		if(session.getAttribute("VAT_Number")==null) {	
			session.removeAttribute("VAT_Number");
			return "redirect:/login";
		}
		int compId=CompanyManager.getInstance().returnCompanyId(session.getAttribute("VAT_Number").toString());
		if(compId==-1) {
			System.err.println("BuildingController-> returnCompanyID- return -1!");
			return "redirect:/";
		}
		model.addAttribute("buildings", bm.buildingsOnThisCompany(compId));
		model.addAttribute("hideBuildings", "Hide all buildings");
		return "companyDashboard";
	}
	
	
	@RequestMapping(value = "/hideBuildings",method = RequestMethod.GET)
	public String showLessBuildings() {
		return "redirect:/dashboard";
	}

	
	@RequestMapping(value = "/removeBuilding/{buildingId}",method = RequestMethod.GET)
	public String removeBuildingRequest(@PathVariable("buildingId") Integer buildingId,HttpSession session,Model model) {
		if(session.getAttribute("VAT_Number")==null) {	
			session.removeAttribute("VAT_Number");
			return "redirect:/login";
		}
		int companyId=CompanyManager.getInstance().returnCompanyId(session.getAttribute("VAT_Number").toString());
		if(companyId==-1) {
			System.err.println("BuildingController-> returnCompanyID- return -1!");
			return "redirect:/";
		}
		if(!bm.checkIfThisCompnayIdServeThisBuildingId(companyId, buildingId)) {
			return "redirect:/dashboard";
		}
		model.addAttribute("deleteBuildingReguest", true);
		model.addAttribute("requestToDeleteBuildingStatus", deleteBulding);
		model.addAttribute("requestToDeleteBuilding", new Company());
		return "removeBuilding";
	}
	
	
	@RequestMapping(value = "/removeBuilding/{buildingId}",method = RequestMethod.POST)
	public String removeBuilding(@PathVariable("buildingId") Integer buildingId,HttpSession session,@ModelAttribute Company company) {
		if(session.getAttribute("VAT_Number")==null) {	
			session.removeAttribute("VAT_Number");
			return "redirect:/login";
		}
		Company com=CompanyManager.getInstance().returnCompanyByVATNumber(session.getAttribute("VAT_Number").toString());
		if(com.getComapnyId()==-1) {
			System.err.println("BuildingController-> returnCompanyID- return -1!");
			return "redirect:/";
		}
		if(!bm.checkIfThisCompnayIdServeThisBuildingId(com.getComapnyId(), buildingId)) {
			return "redirect:/dashboard";
		}
		if(!company.getPassword().equals(company.getConfirmPassword())) {
			deleteBulding="Passwords do not match";
			return "redirect:/removeBuilding/"+buildingId;
		} 
		CompanyManager cm=CompanyManager.getInstance();
		if(!cm.checkPassword(com,company.getPassword())) {
			deleteBulding="Wrong password";
			return "redirect:/removeBuilding/"+buildingId;
		}
		bm.removeThisBuildng(buildingId);
		BuildingDAO.getInstance().removeBuilding(buildingId);
		return "redirect:/dashboard";
	}
	
	@RequestMapping(value="removeBuilding/requestToDeleteBuildingCancel",method = RequestMethod.GET)
	public String cancelDelbulding() {
		return "redirect:/dashboard";
	}
	
	//members of the buildings
	@RequestMapping(value = "/",method = RequestMethod.GET)
	public String membersInBuildingByNameAndKids() {
		
		return "";
	}
	
}
