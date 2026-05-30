package co.elgranero.persistence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.Gson;

public final class ConfigReader {
    private static ConfigReader conReader;
    private Config config;

    private ConfigReader() throws IOException{
        readConfig();
    }

    private void readConfig() throws IOException{
        try {
            String json = new String(
                Files.readAllBytes(Paths.get("config.json"))
            );
            Gson gson = new Gson();
            this.config = gson.fromJson(json, Config.class);
        } catch (Exception e) {
            throw new IOException("fatal error: it was not possible to read \"config.json\"");
        }
    }

    public String getBdIp() throws IOException{
        if (config==null) {
            readConfig();
        }
        return config.bdIp;
    }

    public String getBdUser() throws IOException{
        if (config==null) {
            readConfig();
        }
        return config.bdUser;
    }

    public static ConfigReader getInstance() throws IOException{
        if (conReader == null) {
            conReader = new ConfigReader();
        }
        return conReader;
    }
}

class Config {
    public String bdIp;
    public String bdUser;
}