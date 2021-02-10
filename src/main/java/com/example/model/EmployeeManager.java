package com.example.model;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

import com.example.entity.Apartment;
import com.example.entity.Building;
import com.example.entity.Company;
import com.example.entity.Employee;
import com.example.model.db.EmployeeDAO;



public class EmployeeManager {

	private final static File FOLDER=new File("E:\\Eclipse Java\\testproject\\WebHouseManager\\src\\main\\webapp\\static\\paidTaxes");
	private static EmployeeManager instance;
	private static int lastEmployeeId=1;
	private static String taxStatusMsg="";
	
	private ConcurrentNavigableMap<Integer,Employee> employees=new ConcurrentSkipListMap<Integer,Employee>(new Comparator<Integer>() {//KEY-> employeeId; VALUE-> Employee
		@Override
		public int compare(Integer o1, Integer o2) {if(o1>o2)return -1;else if(o1<o2)return 1; else return 0;}});
	
	private EmployeeManager() {
		for(Employee e:EmployeeDAO.getInstance().getAllEmployees()) {
			if(lastEmployeeId<e.getEmployeeId()) {
				lastEmployeeId=e.getEmployeeId();
			}
			System.out.println("emp: "+e);
				employees.put(e.getEmployeeId(), e);
		}
	}
	
	public static synchronized EmployeeManager getInstance() {
		if(instance==null) {
			instance=new EmployeeManager();
		}
		return instance;
	}
	
	
	public void addEmployee(int employeeId,Employee emp) {
		employees.put(employeeId, emp);
		System.out.println(emp);
	}
	
	//get all employees by companyId
	public synchronized ArrayList<Employee> getAllEmployeesByCompanyId(int companyId) {
		ArrayList<Employee> list=new ArrayList<Employee>();
		for(Iterator<Entry<Integer, Employee>> it=employees.entrySet().iterator();it.hasNext();) {
			Entry<Integer, Employee> e =it.next();
			if(e.getValue().getCompanyId()==companyId) {
				list.add(e.getValue());
			}
			
		}
		
		return list;
	}
	
	
	//by employeeId get details
	public synchronized Employee returnEmployeeDetailsByEmployeeId(int employeeId) {
		for(Iterator<Entry<Integer, Employee>> it=employees.entrySet().iterator();it.hasNext();) {
			Entry<Integer, Employee> e =it.next();
			if(e.getKey()==employeeId) {
				return e.getValue();
			}
			
		}
		return null;
	}
	
	//check if this employee is in this companyId
	public synchronized boolean returnTrueIfThisEmployeeThisCompanyId(int companyId,int employeeId) {
		for(Employee e:employees.values()) {
			if(e.getEmployeeId()==employeeId&&e.getCompanyId()==companyId)
				return true;
		}
		return false;
	}
	
	//change details 
	public synchronized Employee changeEmployeeDetails(int companyId,Employee emp) {
		for(Iterator<Entry<Integer, Employee>> it=employees.entrySet().iterator();it.hasNext();) {
			Entry<Integer, Employee> e =it.next();
			if(e.getValue().getCompanyId()==companyId) {
				if(e.getKey()==emp.getEmployeeId()) {
				e.getValue().setfName(emp.getfName());
				e.getValue().setlName(emp.getlName());
				e.getValue().setLocalAddress(emp.getLocalAddress());
				return e.getValue();
				}
			}
			
		}
		return null;
	}
	
	
	
	
	//all employees in this company by compId-> return emp
	public synchronized ArrayList<Employee> returnEmployeesInThisCopany(int companyId) {
		ArrayList<Employee> list=new ArrayList<Employee>();
		for(Iterator<Entry<Integer, Employee>> it= employees.entrySet().iterator();it.hasNext();) {
			Entry<Integer, Employee> e=it.next();
			if(e.getValue().getCompanyId()==companyId) {
				e.getValue().removeAllFormList();
				list.add(e.getValue());
			}
		}
		return list;
	}
	
	
	//showing employees by name and buildings
	public synchronized ArrayList<Employee> returnEmployeesByNameAndBuildings(int companyId) {
		ArrayList<Employee> list=new ArrayList<Employee>();
		for(Employee e:employees.values()) {
			if(e.getCompanyId()==companyId) {
				list.add(e);
			}
		}
		Collections.sort(list,Comparator.comparing(Employee::getfName)
				.thenComparingInt(Employee::getEmpleeBuildingsSize));
		Collections.reverse(list);
		return list;
	}
	
	//calculate price for building
	public synchronized double returnTaxForBuilding(ArrayList<Apartment> apartments,double priceArea,double pricePet,double pricePerson,Company company,Employee employee,Building building,String date) {
		double sum=0;
		for(int i=0;i<apartments.size();i++) {
			int amaout=0;
			sum+=(apartments.get(i).getArea()*priceArea)+
				(apartments.get(i).getMembers()-apartments.get(i).getKids())*pricePerson;
			if(apartments.get(i).isHasPet()) {
				sum+=pricePet;
			}
			amaout+=sum;
			double roundOff1 = (double) Math.round(amaout * 100) / 100;
			fileWriter(apartments.size(), apartments.get(i).getFloor(), apartments.get(i).getApartmentNumber(),
					apartments.get(i).getOwner(), apartments.get(i).getMembers(), apartments.get(i).isHasPet(),
					apartments.get(i).getKids(), roundOff1, company, employee, building, date);
		}
		
		double roundOff = (double) Math.round(sum * 100) / 100;
		return roundOff;
	}
	
	
	private void fileWriter(int numberOfAparments,int floor,int number,String owner,int members,boolean pet,int kids,double amaunt,Company company,Employee employee,Building building,String date) {
		FOLDER.mkdir();
		BufferedWriter bw=null;
		FileOutputStream fos=null;
		File file=new File(FOLDER,company.getCompanyName()+".txt");
		try{
			file.createNewFile();
			fos=new FileOutputStream(file,true);
			bw=new BufferedWriter(new OutputStreamWriter(fos));
			bw.write("------------------"+date+"---------------------");
			bw.newLine();
			bw.write("Price for area: "+company.getPriceArea()+" price for pet: "+company.getPricePet()+" price for person: "+company.getPricePerson()+" date: "+date);
			bw.newLine();
			bw.write("Employee name: "+employee.getlName()+" "+employee.getlName()+"  ID number: "+employee.getIDNumber());
			bw.newLine();
			bw.write("Building in city: "+building.getCity()+"  address: "+building.getAddress()+" number of floors: "+building.getFloors()+" number of apatments: "+numberOfAparments);
			bw.newLine();
			bw.write("Apartment on floor: "+floor+" apatment number: "+number+" owner: "+owner+" members: "+members+" pet: "+pet+" kids: "+kids);
			bw.newLine();
			bw.write("amount: "+amaunt);
			bw.newLine();
			bw.write("-----------------------------------------------");
			bw.newLine();
		}catch (IOException e) {
			e.printStackTrace();
			System.err.println("File is not created!");
		}
		finally {
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("bw.close()");
			}
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("fos.close()");
			}
		}
	}
	
	public static String getTaxStatusMsg() {
		return taxStatusMsg;
	}
	
	public static void setTaxStatusMsg(String taxStatusMsg) {
		EmployeeManager.taxStatusMsg = taxStatusMsg;
	}
	public void deleteEmployee(int employeeId) {
		employees.remove(employeeId);
	}
	
	public void removeAllemployees() {
		employees.clear();
	}
	
	public static int getLastEmployeeId() {
		return lastEmployeeId;
	}
	
	public static void setLastEmployeeId(int lastEmployeeId) {
		EmployeeManager.lastEmployeeId = lastEmployeeId;
	}
	
}
