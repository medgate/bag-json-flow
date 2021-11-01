package com.openwt.corona.api.app;

import com.openwt.corona.api.generator.FlowFile;
import com.openwt.corona.api.generator.Translations;
import com.openwt.corona.api.generator.model.Answer;
import com.openwt.corona.api.generator.model.CantonalRecommendation;
import com.openwt.corona.api.generator.model.Question;
import com.openwt.corona.api.generator.model.Recommendation;
import com.openwt.corona.api.merger.Merger;

import java.util.List;

public class Generator
{
    public static String[] SCREENING_TREE_NAMES = {
        "kid",
        "child-low",
        "child-medium",
        "child-high",
        "junior-low",
        "junior-medium",
        "junior-high",
        "adult-low",
        "adult-medium",
        "adult-high",
        "senior-low",
        "senior-medium",
        "senior-high"
    };
    public void start(String wtiPath) throws Exception
    {
        Translations translations = new Translations(wtiPath);

        List<Question> questions = translations.questions();
        List<Answer> answers = translations.answers();
        List<Recommendation> recommendations = translations.recommendations();
        List<CantonalRecommendation> cantons = translations.cantonalRecommendations();
        List<String> cantonalRecommendationFor = translations.getCantonalRecommendationFor();

        for (String treeName: Generator.SCREENING_TREE_NAMES) {
            FlowFile flowFile = new FlowFile(questions, answers, recommendations, cantons, cantonalRecommendationFor);
            flowFile.save();

            Merger merger = new Merger();
            merger.merge(treeName);
        }
    }

    public static void main(String[] args) throws Exception
    {
        if (args.length >= 1)
        {
            Generator generator = new Generator();
            generator.start(args[0]);
        }
        else
        {
            System.err.println("Usage: java -jar json-generator.jar WTI_FOLDER_PATH");
        }
    }
}