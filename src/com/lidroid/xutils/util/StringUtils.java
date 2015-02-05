package com.lidroid.xutils.util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * 字符串操作工具包
 * @author 
 * @version 1.0
 * @created 2012-10-16
 */
public class StringUtils 
{
	private final static Pattern emailer = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
	private final static SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final static SimpleDateFormat dateFormater2 = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 将字符串转位日期类型
	 * @param sdate
	 * @return
	 */
	public static Date toDate(String sdate) {
		try {
			if(isBlank(sdate)){
				return null;
			}
			return dateFormater.parse(sdate);
		} catch (ParseException e) {
			return null;
		}
	}
	
	
	public static int indexOfArray(String name,String[] names){
		if(names.length>0){
		for(int i=0;i<names.length;i++){
			if(name.equals(names[i])){
				return i;
			}
		}
		}
		return 0;
	}
	/**
	 * 以友好的方式显示时间
	 * @param sdate
	 * @return
	 */
	public static String friendly_time(String sdate) {
		if(sdate=="" || sdate ==null){
			return "";
		}
		Date time = toDate(sdate);
		if(time == null) {
			return "";
		}
		String ftime = "";
		Calendar cal = Calendar.getInstance();
		
		//判断是否是同一天
		String curDate = dateFormater2.format(cal.getTime());
		String paramDate = dateFormater2.format(time);
		if(curDate.equals(paramDate)){
			int hour = (int)((cal.getTimeInMillis() - time.getTime())/3600000);
			if(hour == 0)
				ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000,1)+"分钟前";
			else 
				ftime = hour+"小时前";
			return ftime;
		}
		
		long lt = time.getTime()/86400000;
		long ct = cal.getTimeInMillis()/86400000;
		int days = (int)(ct - lt);		
		if(days == 0){
			int hour = (int)((cal.getTimeInMillis() - time.getTime())/3600000);
			if(hour == 0)
				ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000,1)+"分钟前";
			else 
				ftime = hour+"小时前";
		}
		else if(days == 1){
			ftime = "昨天";
		}
		else if(days == 2){
			ftime = "前天";
		}
		else if(days > 2 && days <= 10){ 
			ftime = days+"天前";			
		}
		else if(days > 10){			
			ftime = dateFormater2.format(time);
		}
		return ftime;
	}
	
	/**
	 * 判断给定字符串时间是否为今日
	 * @param sdate
	 * @return boolean
	 */
	public static boolean isToday(String sdate){
		boolean b = false;
		Date time = toDate(sdate);
		Date today = new Date();
		if(time != null){
			String nowDate = dateFormater2.format(today);
			String timeDate = dateFormater2.format(time);
			if(nowDate.equals(timeDate)){
				b = true;
			}
		}
		return b;
	}
	

	/**
	 * 判断是不是一个合法的电子邮件地址
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email){
		if(email == null || email.trim().length()==0) 
			return false;
	    return emailer.matcher(email).matches();
	}
	/**
	 * 字符串转整数
	 * @param str
	 * @param defValue
	 * @return
	 */
	public static int toInt(String str, int defValue) {
		try{
			return Integer.parseInt(str);
		}catch(Exception e){}
		return defValue;
	}
	/**
	 * 对象转整数
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static int toInt(Object obj) {
		if(obj==null) return 0;
		return toInt(obj.toString(),0);
	}
	/**
	 * 对象转整数
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static long toLong(String obj) {
		try{
			return Long.parseLong(obj);
		}catch(Exception e){}
		return 0;
	}
	/**
	 * 字符串转布尔值
	 * @param b
	 * @return 转换异常返回 false
	 */
	public static boolean toBool(String b) {
		try{
			return Boolean.parseBoolean(b);
		}catch(Exception e){}
		return false;
	}
	
	
	 public static String listToStr(List<String> list){
	    	StringBuffer sb = new StringBuffer();
	    	if(list != null && list.size()>0){
		    	for(String item: list){
		    		sb.append(item+",");
				}
		    	return sb.substring(0, sb.length()-1);
	    	}
	    	return null;
	    }
	 
	 public static String listToStrByInteger(List<Integer> list){
	    	StringBuffer sb = new StringBuffer();
	    	if(list != null && list.size()>0){
		    	for(Integer item: list){
		    		sb.append(item+",");
				}
		    	return sb.substring(0, sb.length()-1);
	    	}
	    	return "";
	    }
	 
	 
	 public static boolean isBlank(String s){
			return s==null || s.trim().length()<1;
		}
		
		@SuppressWarnings("rawtypes")
		public static boolean isBlank(List list){
			return list == null || list.size()<=0;
		}
		
		public static Object showValue(Object o){
			if(null == o){
				return "";
			}else{
				return o;
			}
		}
		
		/**
		 * 截取字符串text中前len个字母或者汉字,以defaultChar结尾
		 * @param text
		 * @param escape 是否过滤html字符
		 * @param len
		 * @param defaultChar
		 * @return
		 */
		public static String simplifyString(String text, Boolean escape, Integer len, String defaultChar){
			if(len == null){
				len = 10;
			}
			
			if(isBlank(defaultChar)){
				defaultChar = "...";
			}
			
			if(escape){
				text = text.replaceAll("<[^>]*>","");
			}
			
			int textLength = text.length();
			String str = "";
			if(textLength >= len){
				str = text.substring(0, len) + defaultChar;
			}else{
				str = text;
			}
			
			return str;
		}

		/**
		 * 过滤HTML tag
		 * @param string
		 * @return
		 */
		public static String escapeHTML(String string){
			if(isBlank(string)){
				return "";
			}
			return string.replaceAll("&", "&amp;").replaceAll(">", "&gt;").replaceAll("<", "&lt;").replaceAll("\n", "<br/>").replaceAll("\"", "&quot;");
		}
		
		public static String deleteHTML(String html){
			if(isBlank(html)){
				return "";
			}
			return html.replaceAll("<[^>]*>", "");
		}
		
		/**  
	     * 获得随机数字符串（fangdo get from google......）  
	     *   
	     * @param length  需要获得随机数的长度  
	     * 
	     * @param type 随机数的类型：
	     * 			'0':表示仅获得数字随机数；
	     * 			'1'：表示仅获得字符随机数；
	     * 			'2'：表示获得数字字符混合随机数  
	     * 
	     * @return  
	     */  
	    public static String generateRandomCode(int length, int type) {   
	        // 随机字符串   
	        String strRandom = "";   
	  
	        Random rnd = new Random();   
	  
	        if (length < 0) {   
	            length = 5;   
	        }   
	  
	        if ((type > 2) || (type < 0)) {   
	            type = 2;   
	        }   
	  
	        switch (type) {   
		        case 0:   
		            for (int iLoop = 0; iLoop < length; iLoop++) {   
		                strRandom += Integer.toString(rnd.nextInt(10));   
		            }   
		            break;   
		        case 1:   
		            for (int iLoop = 0; iLoop < length; iLoop++) {   
		                strRandom += Integer.toString((35 - rnd.nextInt(10)), 36);   
		            }   
		            break;   
		        case 2:   
		            for (int iLoop = 0; iLoop < length; iLoop++) {   
		                strRandom += Integer.toString(rnd.nextInt(36), 36);   
		            }   
		            break;   
	        }   
	           
	        return strRandom;   
	    }
	    
	    public static List<String> strToList(String s,String split){
	    	if(isBlank(s))
	    		return null;
	    	
	    	ArrayList<String> r = new ArrayList<String>();
	    	String[] t = s.split(split);
	    	for(String m: t){
	    		r.add(m);
	    	}
	    	return r;
	    }
	    
	    
	    
	    public static List<String> strToList(String s){
	    	return strToList(s,",");
	    }
	    
	    public static String arrayToString(Integer[] values){
	    	if(values != null && values.length>0){
	    		StringBuffer sb = new StringBuffer();
	    		for(Integer v : values){
	    			sb.append(v.intValue()+",");
	    		}
	    		return sb.substring(0, sb.length()-1);
	    	}
	    	return null;
	    }
	    
	    public static String ListToString(List<Integer> values){
	    	if(values != null && values.size()>0){
	    		StringBuffer sb = new StringBuffer();
	    		for(Integer v : values){
	    			sb.append(v.intValue()+",");
	    		}
	    		return sb.substring(0, sb.length()-1);
	    	}
	    	return null;
	    }
	    
	    public static String arrayToString(String[] values){
	    	if(values != null && values.length>0){
	    		StringBuffer sb = new StringBuffer();
	    		for(String v : values){
	    			sb.append(v+",");
	    		}
	    		return sb.substring(0, sb.length()-1);
	    	}
	    	return null;
	    }
	    
	    public static String[] strToArray(String s){
	    	return strToArray(s,",");
	    }
	    public static String[] strToArray(String s,String split){
	    	if(isBlank(s))
	    		return null;
	    	return s.split(split);
	    }
	    
	    public static int[] strToIntArray(String s){
			String[] codes = strToArray(s, ",");
			int keys[] = new int[codes.length];;
			for(int i=0;i<codes.length;i++){
				keys[i] = Integer.valueOf(codes[i]).intValue();
			}	
			return keys;
	    }
	    public static String listToStr(List<String> list, String splitStr){
	    	StringBuffer sb = new StringBuffer();
	    	if(list != null && list.size()>0){
		    	for(String item: list){
		    		sb.append(item + splitStr);
				}
		    	return sb.substring(0, sb.length()-1);
	    	}
	    	return null;
	    }
	    
	    public static String collectionToString(Collection<String> coll){
	    	StringBuffer sb = new StringBuffer();
	    	if(coll != null && coll.size()>0){
		    	for(String item: coll){
		    		sb.append(item+",");
				}
		    	return sb.substring(0, sb.length()-1);
	    	}
	    	return null;
	    }
	    
	    /**
		 * 以一种简单的方式格式化字符串
		 * 如  String s = StringHelper.format("{0} is {1}", "apple", "fruit");
		 * System.out.println(s);	//输出  apple is fruit.
		 * @param pattern
		 * @param args
		 * @return
		 */
		public static String format(String pattern, Object ...args){
			for(int i=0; i<args.length; i++) {
				pattern = pattern.replace("{"+i+"}", args[i].toString().trim());
			}
			return pattern;
		}
		
		/**
		 * 校验密码
		 * 	只能包含 字母、数字和.!@#$%^&*+-_等特殊字符，长度限定为6-16位。
		 * @param pwd
		 * @return
		 */
		public static boolean isPassword(String pwd){
			String check = "^[a-zA-Z0-9.!@#$%^&*+-_]{6,16}$";
		    Pattern regex = Pattern.compile(check);
		    Matcher matcher = regex.matcher(pwd);
	        return matcher.matches();
		}
		
		/**
		 * 校验网机号
		 * @param phoneid
		 * @return
		 */
		public static boolean isPhoneid(String phoneid){
			if(isBlank(phoneid)) 
				return false;
			//匹配总机,以#分隔的分机,及以#开头的分机
			String check = "^[1-9]\\d{4,10}|[1-9]\\d{4,10}#[1-9]\\d{0,3}|#[1-9]\\d{4,10}";
		    Pattern regex = Pattern.compile(check);
		    Matcher matcher = regex.matcher(phoneid.trim());
	        return matcher.matches();		
		}
		
		public static boolean isSubPhoneid(String subPhoneid){
			if(subPhoneid == null) {
				return false;
			}
					
			String check = "^[1-9]\\d{4,10}#[1-9]\\d{0,3}|#[1-9]\\d{4,10}$";
		    Pattern regex = Pattern.compile(check);
		    Matcher matcher = regex.matcher(subPhoneid.trim());
		    return matcher.matches();	
		}
		
		/**
		 * 校验组机号
		 * @param searchPhoneid
		 * @return
		 */
		public static boolean isGroupid(String groupid){
			if(groupid == null) {
				return false;
			}		
			String check = "^\\*[1-9]\\d{4,15}$";
		    Pattern regex = Pattern.compile(check);
		    Matcher matcher = regex.matcher(groupid.trim());
	        return matcher.matches();		
		}
		
		public static boolean isAHref(String href){
			if(isBlank(href))
				return false;
			
			String regExp= "<a href='(.*)'>(.*)</a>";
			Pattern regex = Pattern.compile(regExp);
		    Matcher matcher = regex.matcher(href);
	        return matcher.matches();
		}
		
//		public static String sortListToString(List<String> keys){
//			if( keys == null )
//				return null;
//			
//			HashMap<String, String> tmp = new HashMap<String, String>();
//			for(String key : keys){
//				tmp.put(key, key);
//			}
//			return com.fangdo.netmachine.utils.StringUtils.collectionToString(tmp.values());
//		}
		
		/**
		 * 
		 * 按照指定的size，分割objectlist成若干个子list放到returnList中返回
		 * @param <E>
		 * 
		 * @param size
		 * @param list
		 * @return
		 */
		public static <E> List<List<E>> subList(Integer size, List<E> list){
			
			//若size为null,给一个 默认值
			if(null == size){
				size = 5;
			}
			
			//统计循环的次数
			int j=0;// 步长
			int listSize = list.size();
			
			//拆分ojbectList成多个包放到resultList中
			List<List<E>> returnList = new ArrayList<List<E>>();
			List<E> subList = null;
			//不需要分包的情况
			if( listSize <= size  ){
				returnList.add( list );
			}else{
			
				//计算要循环的次数
				int times = listSize/size;
				if(listSize % size > 0){
					times++;
				}
				for(int i=0; i<times; i++){
					subList = new ArrayList<E>();
					for(int n=0; n<size; n++){
						if((j+n) < listSize){
							subList.add(list.get(j+n));
						}else{
							break;
						}
					}
					
					returnList.add(subList);
					j += size;
				}
			}
			
			return returnList;
		}

		/**
		 * Double型的数据转换成非科学计数方式的字符串
		 * @param value
		 * @return
		 */
		public static String Double2String(Double value){
			NumberFormat format = NumberFormat.getInstance();
			format.setGroupingUsed(false);
			return format.format(value);
		}
		
		public static String uuid(){
			return UUID.randomUUID().toString();
		}
		
		
		public static  String IntegerToString(Integer callid , Integer callid2){
			if(callid == null){
				return "";
			}
			
			if(callid2 == null ){
				callid2 = callid ;
			}
			
			return callid+"-"+callid2;
		}
		 
}