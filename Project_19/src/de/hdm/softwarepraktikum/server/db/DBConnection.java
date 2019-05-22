package de.hdm.softwarepraktikum.server.db;
	
	
	import java.sql.Connection;
	import java.sql.DriverManager;
	import com.google.appengine.api.utils.SystemProperty;

	
		public class DBConnection {

		    /**
		     * Die Klasse DBConnection wird nur einmal instantiiert. Man spricht hierbei
		     * von einem sogenannten <b>Singleton</b>.
		     * <p>
		     * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal
		     * für sämtliche eventuellen Instanzen dieser Klasse vorhanden. Sie
		     * speichert die einzige Instanz dieser Klasse.
		     * 
		     */
			
		    private static Connection con = null;
		    
		    private static String googleUrl = "jdbc:google:mysql://fabled-rookery-239112:europe-west3:itprojekt-ss19/19ssprojekt?user=root&password=bruno";
		    private static String localUrl = "jdbc:mysql://127.0.0.1:3306/it_project_db?user=root&password=&serverTimezone=UTC";
		   

		   
		    /**
		     * Diese statische Methode kann aufgrufen werden durch
		     * <code>DBConnection.connection()</code>. Sie stellt die
		     * Singleton-Eigenschaft sicher, indem Sie daf�r sorgt, dass nur eine
		     * einzige Instanz von <code>DBConnection</code> existiert.
		     * <p>
		     * 
		     * @return DAS <code>DBConncetion</code>-Objekt.
		     * @see con
		     */
		    public static Connection connection() {
		        // Wenn es bisher keine Conncetion zur DB gab, ...
		        if (con == null) {
		        	
		        	
		            String url = null;
		            try {
		                if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
		                    // Load the class that provides the new
		                    // "jdbc:google:mysql://" prefix.
		                    Class.forName("com.mysql.jdbc.GoogleDriver");
		                    url = googleUrl;
		                } else {
		                    // Local MySQL instance to use during development.
		                	 Class.forName("com.mysql.jdbc.Driver");
		                    url = localUrl;

		                }
		                /*
		                 * Dann erst kann uns der DriverManager eine Verbindung mit den
		                 * oben in der Variable url angegebenen Verbindungsinformationen
		                 * aufbauen.
		                 * 
		                 * Diese Verbindung wird dann in der statischen Variable con
		                 * abgespeichert und fortan verwendet.
		                 */
		                con = DriverManager.getConnection(url);
		            } catch (Exception e) {
		                con = null;
		                e.printStackTrace();
		                throw new RuntimeException(e.getMessage());
		            }
		        }

		        // Zurückgeben der Verbindung
		        return con;
		    }

		}