import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionConfiguration {

    public static Connection getConnection(){
        Connection connection = null;
        try{
            Class.forName("oracle.jdbc.OracleDriver");
            connection = DriverManager.getConnection("jdbc:oracle:thin:SYSTEM/oracle@//localhost");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("SQLconnectionError");
        }
        return connection;
    }
}
