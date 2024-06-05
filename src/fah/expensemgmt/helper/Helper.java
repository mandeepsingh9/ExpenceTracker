package fah.expensemgmt.helper;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Helper {

	public static Date handledate(String date) {
		if (date != null && date.trim().length() != 0) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			try {
				java.util.Date returnDate = formatter.parse(date);
				java.sql.Date sqlDate = new java.sql.Date(returnDate.getTime());
				return sqlDate;

			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	public static String handlestring(String string) {
		if (string != null && string.trim().length() != 0) {
			return string.trim();
		}
		return null;
	}

	public static Integer handleInteger(String integer) {
		if (integer != null && integer.trim().length() != 0) {
			Integer val = Integer.parseInt(integer);
			return val;
		}
		return null;
	}
	
	public static int handleint(String intval) {
		if (intval != null && intval.trim().length() != 0) {
			int val = Integer.parseInt(intval);
			return val;
		}
		return 0;
	}
	
	public static Float handlefloat(String floatNum) {
		if (floatNum != null && floatNum.trim().length() != 0) {
			Float val = Float.parseFloat(floatNum);
			return val;
		}
		return null;
	}
	
	public static String loggedInUser(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		if(session.getAttribute("loggedinuser")!=null) {
			String username = (String)session.getAttribute("loggedinuser");
			 return username;
		}
		 return "";
	}
	
	public static String getToken(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		if(session.getAttribute("token")!=null) {
			String token = (String)session.getAttribute("token");
			 return token;
		}
		 return null;
	}
	
	public static void generateToken(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		session.setAttribute("token",Math.random());
	}
	
	public static Timestamp getCurrentTime(){
		return new Timestamp(System.currentTimeMillis());
	}
	
	public static void main(String[] args) {
		//ResourceBundle rd = ResourceBundle.getBundle("fah");
		//System.out.println(rd.getString("driver"));
	}
	
	
}

