import co.elgranero.controller.Runner;

public class App {
    public static void main(String[] args) throws Exception {
        (new Runner()).init();
    }
}
/*
Gson gson = new Gson();
    try {
        Connection conn = DriverManager.getConnection(
            "jdbc:oracle:thin:@192.168.1.12:15022/sebq", 
            "system",
            "Z4P4T0Z0!"
        );
        
        PreparedStatement pSt = conn.prepareStatement(
            "SELECT sysdate AS fecha_actual, '¡Conexión exitosa!' AS mensaje FROM dual"
        );

        ResultSet rs = pSt.executeQuery();

        while (rs.next()) {
            String fecha = rs.getString("fecha_actual");
            String msj = rs.getString("mensaje");
            
            System.out.println("Fecha del servidor virtual: " + fecha);
            System.out.println("Mensaje de Oracle: " + msj);
        }

        rs.close();
        pSt.close();
        conn.close();
    } catch (Exception e) {
        System.out.println("Hubo un error de conexión:");
        e.printStackTrace();
    }
*/
