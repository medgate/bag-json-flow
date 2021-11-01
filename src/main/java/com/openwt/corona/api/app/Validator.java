package com.openwt.corona.api.app;

import com.google.gson.Gson;
import com.openwt.corona.api.validator.JsonFlow;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Validator
{
    public void start(String jsonPath) throws Exception
    {
        String content = new String(Files.readAllBytes(Paths.get(jsonPath)));
        JsonFlow jsonFlow = new Gson().fromJson(content, JsonFlow.class);
        jsonFlow.validate();
    }

    public static void main(String[] args) throws Exception
    {
        if (args.length >= 1)
        {
            Validator generator = new Validator();
            generator.start(args[0]);
        }
        else
        {
            System.err.println("Usage: java -jar json-validator.jar JSON_PATH");
        }
    }
}