package dev.wave.wavecore.file.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import dev.wave.wavecore.file.gson.adapters.ClassTypeAdapter;
import dev.wave.wavecore.file.gson.adapters.ConfigurationSerializableAdapter;
import dev.wave.wavecore.file.gson.adapters.UserDataAdapter;
import dev.wave.wavecore.user.data.UserData;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GsonUtil {

    private static final Gson gson = new GsonBuilder()
        .setPrettyPrinting()    // Optional
        .disableHtmlEscaping()
        .registerTypeHierarchyAdapter(ConfigurationSerializable.class, new ConfigurationSerializableAdapter())
        .registerTypeAdapter(Class.class, new ClassTypeAdapter())
        .registerTypeAdapter(UserData.class, new UserDataAdapter())
        .create();

    // Saves a class in json
    public static void save(File folder, String fileName, Object object) {
        Path path = Paths.get(folder + "/" + fileName + ".json");

        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            gson.toJson(object, bufferedWriter);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Read data from json and return the unserialized class
    public static <T> T read(File folder, String fileName, Type type) {
        Path path = Paths.get(folder + "/" + fileName);

        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
            }

            BufferedReader bufferedReader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
            JsonReader jsonReader = new JsonReader(bufferedReader);

            return gson.fromJson(jsonReader, type);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
