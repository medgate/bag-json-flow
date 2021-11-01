package com.openwt.corona.api.generator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.openwt.corona.api.app.Generator;
import com.openwt.corona.api.generator.model.Answer;
import com.openwt.corona.api.generator.model.CantonalRecommendation;
import com.openwt.corona.api.generator.model.Question;
import com.openwt.corona.api.generator.model.Recommendation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

public class FlowFile
{
    private final List<Question> questions;
    private final List<Answer> answers;
    private final List<Recommendation> recommendations;
    private final List<CantonalRecommendation> cantons;
    private final List<String> cantonalRecommendationFor;


    public FlowFile(List<Question> questions, List<Answer> answers, List<Recommendation> recommendations, List<CantonalRecommendation> cantons, List<String> cantonalRecommendationFor)
    {
        this.questions = questions;
        this.answers = answers;
        this.recommendations = recommendations;
        this.cantons = cantons;
        this.cantonalRecommendationFor = cantonalRecommendationFor;
    }

    public void save() throws Exception
    {
        OutputStream outputStream = null;

        try {
            Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
            String json = gson.toJson(this);

            outputStream = new FileOutputStream(new File("json/text.json"));
            outputStream.write(json.getBytes());

        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }
}