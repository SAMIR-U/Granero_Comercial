import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class App {
    public static void main(String[] args) throws Exception {
        Connection conn = DriverManager.getConnection(
            "192.168.1.1:15022/sebq", 
            "system",
            "Z4P4T0Z0!"
        );
        PreparedStatement pSt = conn.prepareStatement(
            "SELECT * FROM TAB"
        );

        ResultSet rs = pSt.executeQuery();

        while (rs.next()) {
            System.out.println(rs);
        }
    }
}
