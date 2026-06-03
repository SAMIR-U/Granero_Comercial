import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import co.elgranero.controller.Runner;
import co.elgranero.persistence.SqlInstructionsReader;
import co.elgranero.persistence.SqlQueryInstructions;

public class App {
    public static void main(String[] args) throws Exception {
        Runner.getInstance().init();
    }
}
/*
SqlInstructionsReader sir = SqlInstructionsReader.getInstance();
SqlQueryInstructions sqlQI = sir.getSqlQueryInstructions();
StringBuilder sb = new StringBuilder();
for (String key: sqlQI.creates.keySet()) {
sb.append(Files.readString(Paths.get(sqlQI.creates.get(key))));
sb.append('\n');
}
System.out.println(sb);

 */

/*
String data = new String(Files.readAllBytes(Paths.get("sql/deletes/deletes.txt")));
        String[] blocks = data.split("--");
        int count = 1;
        for (String block : blocks) {
            if (!block.isBlank()) {
                String[] sqls = block.split("\n");
                StringBuilder sqlQuery = new StringBuilder();
                String name = sqls[0].trim();

                for (int i = 1; i < sqls.length; i++) {
                    if (!block.isBlank()) {
                        sqlQuery.append(sqls[i].replace(";", ""));
                        sqlQuery.append('\n');
                    }
                }
                Path path = Paths.get("sql/deletes/" + name.toLowerCase() + ".sql");
                Files.createFile(path);
                Files.writeString(path, sqlQuery);
                System.out.println("File created: " + path);
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
