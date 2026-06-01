import co.elgranero.controller.Runner;

public class App {
    public static void main(String[] args) throws Exception {
        (new Runner()).init();
    }
}
/*
String data = new String(Files.readAllBytes(Paths.get("sql/update/a.txt")));
        String[] blocks = data.split("--");
        int count = 1;
        for (String block: blocks) {
            if (!block.isBlank()) {
                String[] sqls = block.split("\n");
                StringBuilder sqlQuery = new StringBuilder();
                String name = "";
                
                for (int i = 1; i < sqls.length; i++) {
                    if (!block.isBlank()) {
                        if (i==1)
                            name = sqls[i].replace("UPDATE", "").trim();
                        sqlQuery.append(sqls[i]);
                        sqlQuery.append('\n');
                    }
                }
                Path path = Paths.get("sql/update/"+name.toLowerCase()+".sql");
                Files.createFile(path);
                Files.writeString(path, sqlQuery);
                count++;
            }
        }
*/


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
