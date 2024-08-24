package cafeshopmanagmentsystem;

import java.sql.DriverManager;

import com.mysql.jdbc.Connection;

public class database {
    
    public static Connection connectionDB(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/cafe","root", "") ;//Link your database
            return connect ;
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
        
    }
}
