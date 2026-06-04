import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;

import co.elgranero.controller.Runner;
import co.elgranero.controller.util.DatabaseUtils;
import co.elgranero.persistence.SqlInstructionsReader;
import co.elgranero.persistence.SqlQueryInstructions;

public class App {
    public static void main(String[] args) throws Exception {

        Runner.getInstance().init();
    }
}
