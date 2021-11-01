package com.openwt.corona.api.generator;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class LocalizedTranslations
{
    private static final String FILE_EN = "master.en.json";
    private static final String FILE_DE = "master.de.json";
    private static final String FILE_IT = "master.it.json";
    private static final String FILE_FR = "master.json";

    private final HashMap<String, LocalizedText> translations = new HashMap<>();

    public LocalizedTranslations(TranslationMap en, TranslationMap de, TranslationMap fr, TranslationMap it)
    {
        for (Entry<String, String> entry : en.entrySet())
        {
            String key = entry.getKey();
            String value = entry.getValue();

            if (translations.containsKey(key))
            {
                LocalizedText localizedText = translations.get(key);
                localizedText.addEN(value);
            }
            else
            {
                translations.put(key, new LocalizedText(value, "", "", ""));
            }
        }

        for (Entry<String, String> entry : de.entrySet())
        {
            String key = entry.getKey();
            String value = entry.getValue();

            if (translations.containsKey(key))
            {
                LocalizedText localizedText = translations.get(key);
                localizedText.addDE(value);
            }
            else
            {
                translations.put(key, new LocalizedText("", value, "", ""));
            }
        }

        for (Entry<String, String> entry : fr.entrySet())
        {
            String key = entry.getKey();
            String value = entry.getValue();

            if (translations.containsKey(key))
            {
                LocalizedText localizedText = translations.get(key);
                localizedText.addFR(value);
            }
            else
            {
                translations.put(key, new LocalizedText("", "", value, ""));
            }
        }

        for (Entry<String, String> entry : it.entrySet())
        {
            String key = entry.getKey();
            String value = entry.getValue();

            if (translations.containsKey(key))
            {
                LocalizedText localizedText = translations.get(key);
                localizedText.addIT(value);
            }
            else
            {
                translations.put(key, new LocalizedText("", "", "", value));
            }
        }
    }

    public static LocalizedTranslations load(String wtiPath) throws Exception
    {
        Process process = Runtime.getRuntime().exec(String.format("%s pull", wtiPath));
        process.waitFor();

        TranslationMap en = translations(FILE_EN);
        TranslationMap de = translations(FILE_DE);
        TranslationMap fr = translations(FILE_FR);
        TranslationMap it = translations(FILE_IT);

        return new LocalizedTranslations(en, de, fr, it);
    }

    private static TranslationMap translations(String path) throws IOException
    {
        String content = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);

        File file = new File(path);
        file.delete();

        TranslationMap map = new TranslationMap();

        JsonObject root = JsonParser.parseString(content).getAsJsonObject();
        processJson(root, map, "");

        return map;
    }

    private static void processJson(JsonObject object, TranslationMap map, String prefix)
    {
        for (String key : object.keySet())
        {
            String name = prefix.isEmpty() ? key : String.format("%s.%s", prefix, key);
            JsonElement element = object.get(key);

            if (element.isJsonObject())
            {
                processJson(element.getAsJsonObject(), map, name);
            }
            else if (element.isJsonPrimitive())
            {
                map.put(name, element.getAsString());
            }
            else
            {
                map.put(name, "");
            }
        }
    }

    public Map<String, LocalizedText> startWith(String pattern)
    {
        Map<String, LocalizedText> result = new HashMap<>();

        for (Entry<String, LocalizedText> entry : translations.entrySet())
        {
            if (entry.getKey().startsWith(pattern))
            {
                result.put(entry.getKey(), entry.getValue());
            }
        }

        return result;
    }
}
