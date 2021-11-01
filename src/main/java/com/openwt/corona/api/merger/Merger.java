package com.openwt.corona.api.merger;

import com.google.gson.Gson;
import com.openwt.corona.api.validator.JsonFlow;
import com.openwt.corona.api.validator.JsonNode;
import com.openwt.corona.api.validator.JsonRecommendation;
import com.openwt.corona.api.validator.JsonText;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Merger
{
    public void merge(String treeName) throws Exception
    {
        String contentFlow = new String(Files.readAllBytes(Paths.get(String.format("json/flow-%s.json", treeName))));
        JsonFlow jsonFlow = new Gson().fromJson(contentFlow, JsonFlow.class);

        String contentText = new String(Files.readAllBytes(Paths.get("json/text.json")));
        JsonFlow jsonText = new Gson().fromJson(contentText, JsonFlow.class);

        if (jsonFlow.nodes.length == 0) {
            keepSingleRecommendation(jsonText, jsonFlow.recommendations[0].id);
        }

        jsonFlow.merge(jsonText);
        jsonFlow.save(treeName);
    }

    private void keepSingleRecommendation(JsonFlow jsonText, String recommendationId) {
        for (int i = 0; i < jsonText.recommendations.length; i++) {
            if (jsonText.recommendations[i].id.equals(recommendationId)) {
                jsonText.recommendations = new JsonRecommendation[]{jsonText.recommendations[i]};
                break;
            }
        }
    }
}