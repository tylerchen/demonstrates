package org.iff.sample.infra.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	
	/*
	 * 得到日期中的时间
	 * hh:mm:ss
	 */
	public static String getTime(Date date){
		String dateStr="";
		if(date!=null){
			SimpleDateFormat format=new SimpleDateFormat("HH:mm");
			dateStr=format.format(date);
		}
		
		/*String dateStr=dateToString(date);
		String[] dates=new String[2];
		dates=dateStr.split(" ");
		return dates[1];*/
		return dateStr;
	}
	
	
	
	/*
	 * 将日期转换为字符串  并将其格式化
	 * yyyy-mm-dd hh:mm:ss
	 */
	public static String dateToString(Date date ){
		
		String dateStr="";		
		if(date!=null){
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			dateStr=format.format(date);
		}
		return dateStr;
		
	}
	
	/*
	 * 得到当前时间 并将日期和时间分别放在数组中
	 * yyyy-mm-dd hh:mm:ss
	 */
	public static String[] curTime(){
		Long curtimeLong=System.currentTimeMillis();
		String[] time=new String[2];
		String dateStr="";		
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateStr=format.format(curtimeLong);
		time=dateStr.split(" ");
		return time;
		
	}
	
	
	
	
	
	/*
	 * 得到日期中的日期     不要时间
	 * yyyy-mm-dd
	 */
	public static String getDate(Date date){
		String dateStr="";
		if(date!=null){
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
			dateStr=format.format(date);
		}
		
		return dateStr;
		/*String dateStr=dateToString(date);
		String[] dates=new String[2];
		dates=dateStr.split(" ");
		return dates[0];*/
	}
	
	
	/*
	 * 返回时间段
	 */
	public static String[] getTimes(String time){
		String[] times=new String[2];
		if(time.equals("morning")){
			times[0]="06:00:00";
			times[1]="08:00:00";
		}
		else if(time.equals("am")){
			times[0]="08:00:01";
			times[1]="11:00:00";
		}
		else if(time.equals("zh")){
			times[0]="11:00:01";
			times[1]="13:00:00";
		}
		else if(time.equals("pm")){
			times[0]="13:00:01";
			times[1]="17:00:00";
		}
		else if(time.equals("night")){
			times[0]="17:00:01";
			times[1]="20:00:00";
		}
		else if(time.equals("Unlimited")){
			times[0]="00:00:01";
			times[1]="23:59:59";
			
		}
		return times;
		
	}
	
	
	//比较日期
	public static int compareDate(Date date1,Date date2){
		int result=0;
		if(date1!=null && date2!=null){
			result =date1.compareTo(date2);
		}
		return result;
	}
	
	//比较时间
	public static int compareTime(String time1,String time2){
		int result=0;
		if(time1!=null && time2!=null){
			result =time1.compareTo(time2);
		}
		return result;
	}
}
