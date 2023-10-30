package multistylerpc.discord.file.files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import multistylerpc.App;
import multistylerpc.discord.style.StyleModule;
import multistylerpc.discord.value.Value;

public class ConfigFile {
    private static final Logger logger = LoggerFactory.getLogger(ConfigFile.class);
    private String pathString;
    private File file;

    public ConfigFile(String pathToFile) {
        pathString = pathToFile;
        file = new File(pathString);
    }

    public void save() {
        final JsonObject jsonObject = new JsonObject();
        App.cDiscordClient.mStyleManager.getModules().stream().filter(module -> !module.getValues().isEmpty())
                .forEach(module -> {
                    final JsonObject jsonModule = new JsonObject();
                    module.getValues().forEach(value -> jsonModule.add(value.getName(), value.toJson()));
                    jsonObject.add(module.styleName, jsonModule);
                });
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(file))) {
            Gson gson = new Gson();
            printWriter.println(gson.toJson(jsonObject));
            printWriter.close();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void load() {
        if (!file.exists())
            save();
        JsonElement jsonElement = JsonNull.INSTANCE;
        try {
            jsonElement = JsonParser.parseReader(new BufferedReader(new FileReader(file)));
        } catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
            logger.info("No JSON file found or Can't parse json file " + file.getAbsolutePath());
            logger.error(e.getMessage(),e);
            return;
        }

        if (jsonElement instanceof JsonNull)
            return;

        final JsonObject jsonObject = (JsonObject) jsonElement;

        final Iterator<Map.Entry<String, JsonElement>> iterator = jsonObject.entrySet().iterator();
        while (iterator.hasNext()) {
            final Map.Entry<String, JsonElement> entry = iterator.next();
            final StyleModule module = App.cDiscordClient.mStyleManager.getModule(entry.getKey());
            if (module != null) {
                final JsonObject jsonModule = (JsonObject) entry.getValue();

                for (final Value<?> moduleValue : module.getValues()) {
                    try {
                        final JsonElement element = jsonModule.get(moduleValue.getName());

                        if (element == null) continue;
                        moduleValue.fromJson(element);
                        logger.info(String.format("%s has set %s to %s", module.styleName, moduleValue.name,element.toString()));
                    } catch (Exception e) {
                        e.printStackTrace();
                        continue;
                    }
                }
            } else {
                logger.info("No " + entry.getKey() + " call -> " + entry.getValue());
            }
        }
    };
}
