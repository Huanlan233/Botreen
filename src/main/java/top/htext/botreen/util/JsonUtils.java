package top.htext.botreen.util;

import com.google.gson.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonUtils {
    public static Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static JsonParser PARSER = new JsonParser();

    public static JsonElement file2Json(File file) {
        try {
            FileReader reader = new FileReader(file);
            return PARSER.parse(reader);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public  static File json2File(JsonElement element) {
        String str = GSON.toJson(element);
        return new File(str);
    }

    public static void jsonWrite2File(JsonElement element, File file) {
        try {
            String str = GSON.toJson(element).formatted();
            FileWriter writer = new FileWriter(file);

            writer.write(str);

            writer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void str2JsonWrite2File(String str, File file) {
        JsonElement element = PARSER.parse(str);
        jsonWrite2File(element, file);
    }

    public static JsonElement obj2Json(Object obj) {
        return PARSER.parse(GSON.toJson(obj));
    }

    public static Object json2Obj(JsonElement element, Class<?> c) {
        return GSON.fromJson(GSON.toJson(element), c);
    }
}
