package com.totsp.DateManage;

import java.text.DateFormat;
import java.util.Date;

import android.app.Activity;


/**
 * @author 090181J
 *
 */
public class HelloDatePicker {
	
	private String date;
	private String month;
	private String year;
	private int monthnumber;
	public static String currentDate;
	public static String currentMonth;
	private static final String TAG="HelloData";
	
	  public void getCurrentDate(Activity A){
		 
		  String currentDateTimeString = DateFormat.getDateInstance().format(new Date());  //get current date
		
		  String[] dat=currentDateTimeString.split(" ");
		  month=dat[0];
		  date=dat[1];
		  year=dat[2];
		 
		  String dd1=date.replace(',', ' ');
		  String dd2=dd1.trim();
		 getMonthh(month);
		 
		  currentDate=dd2;
		  currentMonth=Integer.toString(monthnumber);
		  
	  }
	  
	  public void getMonthh(String moth){   //  get relevant month
		   
		  if(moth.equals("Jan")){
			  setMonthnumber(1);
		  }else if(moth.equals("Feb")){
			  setMonthnumber(2);
		  }else if(moth.equals("Mar")){
			  setMonthnumber(3);
		  }else if(moth.equals("Apr")){
			  setMonthnumber(4);
		  }else if(moth.equals("May")){
			  setMonthnumber(5);
		  }else if(moth.equals("Jun")){
			  setMonthnumber(6);
		  }else if(moth.equals("Jul")){
			  setMonthnumber(7);
		  }else if(moth.equals("Aug")){
			  setMonthnumber(8);
		  }else if(moth.equals("Sep")){
			  setMonthnumber(9);
		  }else if(moth.equals("Oct")){
			  setMonthnumber(10);
		  }else if(moth.equals("Nov")){
			  setMonthnumber(11);
		  }else if(moth.equals("Dec")){
			  setMonthnumber(12);
		  }else{
			  
		  }
	  }

	private String getDate() {  //get date
		return date;
	}

	private void setDate(String date) {    //set date
		this.date = date;
	}

	private String getMonth() {   //get month
		return month;
	}

	private void setMonth(String month) {   //set month
		this.month = month; 
	}

	private String getYear() {  //get year
		return year;
	}

	private void setYear(String year) {   //set year
		this.year = year;
	}

	private int getMonthnumber() {    //get month number
		return monthnumber;
	}

	private void setMonthnumber(int monthnumber) {   //set month number
		this.monthnumber = monthnumber;
	}

	
	    
}

