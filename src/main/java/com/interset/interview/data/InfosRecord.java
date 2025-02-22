package com.interset.interview.data;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.TimeZone;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder(value = {"first_name","last_name","siblings","favourite_food","birth_timezone","birth_timestamp"})
public class InfosRecord {
	
	@JsonProperty
	private String first_name;
	
	@JsonProperty
	private String last_name;
	
	@JsonProperty
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private int siblings;
	
	@JsonProperty
	private String favourite_food;
	
	@JsonProperty
	private String birth_timezone;
	
	@JsonProperty
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private long birth_timestamp;
	
	//ALL STATIC DATA
	public static int sum_siblings = 0;
	public static LinkedHashMap<String, Integer> foods = new LinkedHashMap<String, Integer>();
	/*** we initialize the list of month using hashmap **/
	public static LinkedHashMap<String, Integer> bMonthStats = new LinkedHashMap<String, Integer>();
	static
	{
		bMonthStats.put("January",0);
		bMonthStats.put("February",0);
		bMonthStats.put("March",0);
		bMonthStats.put("April",0);
		bMonthStats.put("May",0);
		bMonthStats.put("June",0);
		bMonthStats.put("July",0);
		bMonthStats.put("August",0);
		bMonthStats.put("September",0);
		bMonthStats.put("October",0);
		bMonthStats.put("November",0);
		bMonthStats.put("December",0);
	}
	/******************************************************/
	
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public int getSiblings() {
		return siblings;
	}
	public void setSiblings(int siblings) {
		sum_siblings += siblings;
		this.siblings = siblings;
	}
	
	public String getFavourite_food() {
		return favourite_food;
	}
	
	public void setFavourite_food(String favourite_food) {		
		if(foods.containsKey(favourite_food))
		{
			int nb = foods.get(favourite_food);
			foods.replace(favourite_food, ++nb);
		}
		else
		{
			foods.put(favourite_food, 1);
		}
		this.favourite_food = favourite_food;
	}
	
	public String getBirth_timezone() {
		return birth_timezone;
	}
	
	public void setBirth_timezone(String birth_timezone) {
		this.birth_timezone = birth_timezone;
	}
	
	public long getBirth_timestamp() {
		return birth_timestamp;		
	}
	
	public void setBirth_timestamp(long birth_timestamp) {
		this.birth_timestamp = birth_timestamp;	
		String month = this.getMonth();
		int nb = bMonthStats.get(month);
		bMonthStats.replace(month, ++nb);	
	}
	
	/**
	 * get the month from timestamp and using the timezone
	 * @return
	 */
	public String getMonth()
	{
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date(birth_timestamp));
		calendar.setTimeZone(TimeZone.getTimeZone(this.getBirth_timezone()));		
		DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        int numMonth = calendar.get(Calendar.MONTH);
        String month = null;
        if (numMonth >= 0 && numMonth <= 11 ) {
            month = months[numMonth];
        }
		return month;	
	}
}
