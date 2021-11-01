package com.openwt.corona.api.generator;

import com.openwt.corona.api.generator.model.Answer;
import com.openwt.corona.api.generator.model.CantonalRecommendation;
import com.openwt.corona.api.generator.model.Question;
import com.openwt.corona.api.generator.model.Recommendation;

import java.util.*;
import java.util.Map.Entry;

public class Translations
{
    private final LocalizedTranslations translations;

    public Translations(String wtiPath) throws Exception
    {
        this.translations = LocalizedTranslations.load(wtiPath);
    }

    public List<Question> questions()
    {
        Map<String, Question> map = new HashMap<>();

        Map<String, LocalizedText> questions = translations.startWith("question.");

        for (Entry<String, LocalizedText> entry : questions.entrySet())
        {
            String key = entry.getKey();
            String id = questionName(key);
            LocalizedText text = entry.getValue();

            if (map.containsKey(id))
            {
                Question question = map.get(id);

                if (key.endsWith("title"))
                {
                    question.title(text);
                }
                else if (key.endsWith("description"))
                {
                    question.description(text);
                }
            }
            else
            {
                Question question = null;

                if (key.endsWith("title"))
                {
                    question = new Question(id, text, null);
                }
                else if (key.endsWith("description"))
                {
                    question = new Question(id, null, text);
                }

                if (question != null)
                {
                    map.put(id, question);
                }
            }
        }

        List<Question> result = new ArrayList<>();

        for (Entry<String, Question> entry : map.entrySet())
        {
            result.add(entry.getValue());
        }

        return result;
    }

    private String questionName(String name)
    {
        String[] parts = name.split("\\.");

        return "QUESTION_" + parts[1].toUpperCase();
    }

    public List<Answer> answers()
    {
        List<Answer> result = new ArrayList<>();

        Map<String, LocalizedText> questions = translations.startWith("answer.");

        for (Entry<String, LocalizedText> entry : questions.entrySet())
        {
            String id = answerName(entry.getKey());
            LocalizedText text = entry.getValue();

            Answer answer = new Answer(id, text);
            result.add(answer);
        }

        return result;
    }

    private String answerName(String name)
    {
        String[] parts = name.split("\\.");

        return "ANSWER_" + parts[1].toUpperCase();
    }

    public List<Recommendation> recommendations()
    {
        Map<String, Recommendation> map = new HashMap<>();

        Map<String, LocalizedText> recommendations = translations.startWith("recommendation.");

        for (Entry<String, LocalizedText> entry : recommendations.entrySet())
        {
            String key = entry.getKey();
            String id = recommendationName(key);
            String severity = recommendationSeverity(key);
            LocalizedText text = entry.getValue();

            if (map.containsKey(id))
            {
                Recommendation recommendation = map.get(id);

                if (key.endsWith("title"))
                {
                    recommendation.title(text);
                }
                else if (key.endsWith("description"))
                {
                    recommendation.description(text);
                }
                else if (key.endsWith("cantonalDescription"))
                {
                    recommendation.cantonalDescription(text);
                }
                else if (key.endsWith("medgateTitle"))
                {
                    recommendation.medgateTitle(text);
                }
                else if (key.endsWith("medgateDescription"))
                {
                    recommendation.medgateDescription(text);
                }
            }
            else
            {
                Recommendation recommendation = null;

                if (key.endsWith("title"))
                {
                    recommendation = new Recommendation(id, severity).title(text);
                }
                else if (key.endsWith("description"))
                {
                    recommendation = new Recommendation(id, severity).description(text);
                }
                else if (key.endsWith("cantonalDescription"))
                {
                    recommendation = new Recommendation(id, severity).cantonalDescription(text);
                }
                else if (key.endsWith("medgateTitle"))
                {
                    recommendation = new Recommendation(id, severity).medgateTitle(text);
                }
                else if (key.endsWith("medgateDescription"))
                {
                    recommendation = new Recommendation(id, severity).medgateDescription(text);
                }

                if (recommendation != null)
                {
                    map.put(id, recommendation);
                }
            }
        }

        List<Recommendation> result = new ArrayList<>();

        for (Entry<String, Recommendation> entry : map.entrySet())
        {
            result.add(entry.getValue());
        }

        return result;
    }

    public List<CantonalRecommendation> cantonalRecommendations()
    {
        Map<String, CantonalRecommendation> map = new HashMap<>();

        Map<String, LocalizedText> cantonalRecommendations = translations.startWith("cantonal_recommendation.");

        List<CantonalRecommendation> result = new ArrayList<>();

        for (Entry<String, LocalizedText> entry : cantonalRecommendations.entrySet())
        {
            String key = entry.getKey();
            String id = getCantonShortName(key);
            LocalizedText text = entry.getValue();

            CantonalRecommendation cantonalRecommendation = new CantonalRecommendation(id).description(text);
            result.add(cantonalRecommendation);
        }

        return result;
    }

    public List<String> getCantonalRecommendationFor() {
        return Arrays.asList(
            "RECOMMENDATION_A",
            "RECOMMENDATION_B2",
            "RECOMMENDATION_C2_TEST",
            "RECOMMENDATION_C3_TEST",
            "RECOMMENDATION_C4_TEST",
            "RECOMMENDATION_D",
            "RECOMMENDATION_E2",
            "RECOMMENDATION_F",
            "RECOMMENDATION_G");
    }

    private String recommendationName(String name)
    {
        String[] parts = name.split("\\.");

        return "RECOMMENDATION_" + parts[1].toUpperCase();
    }

    private String recommendationSeverity(String name)
    {
        String[] parts = name.split("\\.");

        return parts[2].toUpperCase();
    }

    private String getCantonShortName(String name)
    {
        String[] parts = name.split("\\.");

        return parts[1].toUpperCase();
    }
}
