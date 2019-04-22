package de.hdm.softwarepraktikum.server.db;
	
	
	import java.sql.Connection;
	import java.sql.DriverManager;
	import com.google.appengine.api.utils.SystemProperty;

	public class DBConnection {

	   
		
	    private static Connection con = null;
	   
	   private static String googleUrl = "jdbc:google:mysql://";
	   private static String localUrl = "jdbc:mysql://127.0.0.1";
	   

	  
	    public static Connection connection() {
	     
	        if (con == null) {
	        	
	        	
	            String url = null;
	            try {
	                if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
	                   
	                    Class.forName("com.mysql.jdbc.GoogleDriver");
	                    url = googleUrl;
	                } else {
	                   
	                	 Class.forName("com.mysql.jdbc.Driver");
	                    url = localUrl;

	                }
	             
	                con = DriverManager.getConnection(url);
	            } catch (Exception e) {
	                con = null;
	                e.printStackTrace();
	                throw new RuntimeException(e.getMessage());
	            }
	        }

	        return con;
	    }

}
