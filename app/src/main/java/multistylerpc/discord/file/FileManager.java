package multistylerpc.discord.file;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import multistylerpc.discord.file.files.ConfigFile;
import multistylerpc.discord.file.files.PathJson;

public class FileManager {
    private static final Logger logger = LoggerFactory.getLogger(FileManager.class);
    public ConfigFile cConfigFile;
    private String workingDirectory = System.getProperty("user.dir");
    private File pathjson = new File(workingDirectory + "/path.json");

    public void loadAll() {
        loadPathJson();
        if (cConfigFile != null) {
            cConfigFile.load();
        }
    }

    public void savePathJson() {
        PathJson configData = new PathJson();
        configData.setConfigPath(workingDirectory + "/config.json");
        configData.setSettingsPath(workingDirectory + "/settings.json");
        Gson gson = new Gson();
        String jsonData = gson.toJson(configData);
        try (FileWriter writer = new FileWriter(workingDirectory + "/path.json")) {
            writer.write(jsonData);
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }
    }

    public void loadPathJson() {
        if (!pathjson.exists())
            savePathJson();
        try (FileReader reader = new FileReader(pathjson)) {
            Gson gson = new Gson();
            PathJson configData = gson.fromJson(reader, PathJson.class);

            logger.info("Config Path: " + configData.getConfigPath());
            if (new File(configData.getConfigPath()).exists()) {
                cConfigFile = new ConfigFile(configData.getConfigPath());
            }else {
                cConfigFile = new ConfigFile(workingDirectory + "/config.json");
                logger.info(configData.getConfigPath() + " is not Existing");
            }
            logger.info("Settings Path: " + configData.getSettingsPath());
            if (new File(configData.getSettingsPath()).exists()) {
                
            }else {
                logger.info(configData.getSettingsPath() + " is not Existing");
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
