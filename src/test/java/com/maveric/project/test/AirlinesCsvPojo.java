package com.maveric.project.test;

import com.opencsv.bean.CsvBindByName;

public class AirlinesCsvPojo 
{
	
	@CsvBindByName(column ="id")
     private int id;
	
	@CsvBindByName(column ="name")
	private String name;
	
	@CsvBindByName(column ="country")
	private String country;

	
	public AirlinesCsvPojo() 
	{
		
	}
	
	public AirlinesCsvPojo(int id, String name, String country) 
	{
		super();
		this.id = id;
		this.name = name;
		this.country = country;
	}
	public int getID() 
	{
		return id;
	}
	public void setID(int id) 
	{
		this.id = id;
	}
	public String getName() 
	{
		return name;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	public String getCountry() 
	{
		return country;
	}
	public void setCountry(String country) 
	{
		this.country = country;
	}
	
	
	@Override
	public String toString() 
	{
		return "EmployeePojo [id="+id+",name="+name+",country="+country+"]";
	}
}