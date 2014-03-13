package controllers;

public class DateValidator {
	
	
	// Date string format: DD:MM:YYYY or DD.MM.YYYY 
	//Both will work
	public static boolean validateDate(String date){
		int day = Integer.parseInt(date.substring(0,2)); 
		if (day > 31|| day < 1){
			return false;
		}
		int month = Integer.parseInt(date.substring(3,5)); 
		if(month > 12|| month < 1){
			return false;
		}
		int year = Integer.parseInt(date.substring(6,10));
		if(month == 4 || month == 6 || month == 9 || month == 11){
			if (day == 31){
				return false;
			}
			
		}
		if (month == 2){
			if(!isLeapYear(year)){
				if (day > 28){
					return false;
				}
			}
			else{
				if(day > 29){
					return false;
				}
			}
		}
		return true;
		
	}
	private static boolean isLeapYear(int year){
		if (year % 400==0){
			return true;
		}
		else if (year % 100== 0){
			return false;
		}
		else if (year %4==0){
			return true;
		}
		else {
			return false;
		}
	}
}
