package com.example.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.entity.Company;
import com.example.model.CompanyManager;
import com.example.model.db.CompanyDAO;

@Controller
@Scope("session")
@SessionAttributes("VAT_Number")
public class LoginRegistrationController {
	private CompanyManager cm=CompanyManager.getInstance();
	private String status="";
	private String loginStatus="";
	
	@RequestMapping(value="/registration",method = RequestMethod.GET)
	public String goToRegister(Model model) {
		model.addAttribute("status", status);
		model.addAttribute("reg",new Company() );
		return "register";
	}
	
	
	@RequestMapping(value="/registration",method=RequestMethod.POST)
	public String registration(@ModelAttribute Company company,Model model,HttpServletResponse response)  {
		if(company.getCompanyName().isEmpty()||company.getCompanyName().equals("null")) {
			status="Company name is empty";
			return "redirect:/registration";
		}
		if(cm.isThisCompanyNameExists(company.getCompanyName())) {
			status="This company name already exists";
			return "redirect:/registration";
		}
		if(cm.ifThisVAT_NumberExists(company.getVAT_Number())) {
			status="This VAT number already exists";
			 return "redirect:/registration";
		}
		if(company.getVAT_Number().isEmpty()||company.getVAT_Number().equals("null")) {
			status="VAT number is empty";
			 return "redirect:/registration";
		}
		if(company.getSeat().isEmpty()||company.getSeat().equals("null")) {
			status="Company seat is empty";
			 return "redirect:/registration";
		}
		if(company.getCity().isEmpty()||company.getCity().equals("null")) {
			status="Company city is empty";
			 return "redirect:/registration";
		}
		if(company.getMOL().isEmpty()||company.getMOL().equals("null")) {
			status="Company MOL is empty";
			 return "redirect:/registration";
		}
		if(!company.getPassword().equals(company.getConfirmPassword())||company.getPassword().isEmpty()) {	
				status="Passwords do not match";
			 return "redirect:/registration";
		}
			int lastCompanyId=CompanyManager.getInstance().getLastCompanyId();
			lastCompanyId++;
			company.setComapnyId(lastCompanyId);
			CompanyManager.getInstance().addNewCompany(lastCompanyId,company);
			cm.setLastCompanyId(lastCompanyId++);
			CompanyDAO.getInstance().saveCompany(company);
			return "redirect:/index.jsp";
	}
	
	@RequestMapping(value="/login",method = RequestMethod.GET)
	public String goToLogin(Model model) {
		model.addAttribute("log",new Company());
		model.addAttribute("loginStatus", loginStatus);
		return "login";
	}
	
	@RequestMapping(value="/login",method = RequestMethod.POST)
	public String login(@ModelAttribute Company company,HttpSession session,HttpServletRequest request,Model model) {
		if(company.getVAT_Number().isEmpty()) {
			loginStatus="VAT number is empty";
			return "redirect:/login";
		}
		if(company.getPassword().isEmpty()) {
			loginStatus="Password is empty";
			return "redirect:/login";
		}
		if(cm.prepForLogin(company.getVAT_Number(), company.getPassword())) {
			session.setAttribute("VAT_Number", company.getVAT_Number());
			int companyId=CompanyManager.getInstance().returnCompanyId(company.getVAT_Number());
			cm.calculateBuildings(companyId);
			return "redirect:/dashboard";
		}
		else{
			loginStatus="Wrong VAT number or password";
			return "redirect:/login";
		}
			
	}
	
	
	
}
