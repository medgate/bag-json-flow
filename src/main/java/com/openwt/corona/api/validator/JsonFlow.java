package com.openwt.corona.api.validator;

import static java.util.Comparator.comparing;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.openwt.corona.api.app.Generator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class JsonFlow
{
    public JsonQuestion[] questions;
    public JsonAnswer[] answers;
    public JsonRecommendation[] recommendations;
    public JsonNode[] nodes;
    public JsonCantonalRecommendation[] cantons;
    public String[] cantonalRecommendationFor;

    public void validate()
    {
        Set<String> nodeIds = nodeIds();
        Set<String> questionIds = questionIds();
        Set<String> answerIds = answerIds();
        Set<String> recommendationIds = recommendationIds();

        checkValidity(nodeIds, questionIds, answerIds, recommendationIds);
        checkDuplicates(nodeIds, questionIds, answerIds, recommendationIds, cantons);
    }

    private Set<String> nodeIds()
    {
        Set<String> result = new HashSet<>();

        for (JsonNode node : nodes)
        {
            result.add(node.id);
        }

        return result;
    }

    private Set<String> questionIds()
    {
        Set<String> result = new HashSet<>();

        for (JsonQuestion question : questions)
        {
            result.add(question.id);
        }

        return result;
    }

    private Set<String> answerIds()
    {
        Set<String> result = new HashSet<>();

        for (JsonAnswer answer : answers)
        {
            result.add(answer.id);
        }

        return result;
    }

    private Set<String> recommendationIds()
    {
        Set<String> result = new HashSet<>();

        for (JsonRecommendation recommendation : recommendations)
        {
            result.add(recommendation.id);

            String severity = recommendation.severity;

            if (!severity.equals("NONE") && !severity.equals("LOW") && !severity.equals("MEDIUM") && !severity.equals("HIGH"))
            {
                logError(String.format("Recommendation %s has invalid severity: %s", recommendation.id, severity));
            }
        }

        return result;
    }

    private Set<String> cantonalIds()
    {
        Set<String> result = new HashSet<>();

        for (JsonCantonalRecommendation cantonalRecommendation : cantons)
        {
            result.add(cantonalRecommendation.id);
        }

        return result;
    }

    private void checkValidity(Set<String> nodeIds, Set<String> questionIds, Set<String> answerIds, Set<String> recommendationIds)
    {
        for (JsonNode node : nodes)
        {
            if (!questionIds.contains(node.question))
            {
                logError(String.format("Node %s has invalid question: %s", node.id, node.question));
            }

            for (JsonNodeAnswer answer : node.answers)
            {
                if (!answerIds.contains(answer.text))
                {
                    logError(String.format("Node %s has invalid answer: %s", node.id, answer.text));
                }

                String type = answer.type;

                if (!type.equals("NODE") && !type.equals("RECOMMENDATION"))
                {
                    logError(String.format("Recommendation %s has invalid answer type: %s", node.id, type));
                }

                if (answer.node != null)
                {
                    if (!nodeIds.contains(answer.node))
                    {
                        logError(String.format("Node %s has invalid node answer: %s", node.id, answer.node));
                    }
                }
                else if (answer.recommendation != null)
                {
                    if (!recommendationIds.contains(answer.recommendation))
                    {
                        logError(String.format("Node %s has invalid recommendation answer: %s", node.id, answer.recommendation));
                    }
                }
                else
                {
                    logError(String.format("Node %s has no node or recommendation", node.id));
                }
            }
        }
    }

    private void checkDuplicates(Set<String> nodeIds, Set<String> questionIds, Set<String> answerIds, Set<String> recommendationIds, JsonCantonalRecommendation[] cantonalRecommendations)
    {
        for (String nodeId : nodeIds)
        {
            if (!nodeId.equals("n_0") && !existsNode(nodeId))
            {
                logError(String.format("Node %s is never used", nodeId));
            }
        }

        for (String questionId : questionIds)
        {
            if (!existsQuestion(questionId))
            {
                logError(String.format("Question %s is never used", questionId));
            }
        }

        for (String answerId : answerIds)
        {
            if (!existsAnswer(answerId))
            {
                logError(String.format("Answer %s is never used", answerId));
            }
        }

        for (String recommendationId : recommendationIds)
        {
            if (!existsRecommendation(recommendationId))
            {
                logError(String.format("Recommendation %s is never used", recommendationId));
            }
        }

        if (cantonalIds().size() < cantonalRecommendations.length)
        {
            logError(String.format("Cantonal recommendations contain duplicates"));
        }
    }

    private boolean existsNode(String id)
    {
        for (JsonNode node : nodes)
        {
            for (JsonNodeAnswer answer : node.answers)
            {
                if ((answer.node != null) && answer.node.equals(id))
                {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean existsQuestion(String id)
    {
        for (JsonNode node : nodes)
        {
            if (node.question.equals(id))
            {
                return true;
            }
        }

        return false;
    }

    private boolean existsAnswer(String id)
    {
        for (JsonNode node : nodes)
        {
            for (JsonNodeAnswer answer : node.answers)
            {
                if (answer.text.equals(id))
                {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean existsRecommendation(String id)
    {
        for (JsonNode node : nodes)
        {
            for (JsonNodeAnswer answer : node.answers)
            {
                if ((answer.recommendation != null) && answer.recommendation.equals(id))
                {
                    return true;
                }
            }
        }

        return false;
    }

    public void merge(JsonFlow json)
    {
        questions = sortQuestions(json.questions, nodes);
        answers = json.answers;
        recommendations = sortRecommendations(json.recommendations);
        cantons = sortCantonalRecommendations(json.cantons);
        cantonalRecommendationFor = json.cantonalRecommendationFor;
        Arrays.sort(cantonalRecommendationFor);
    }

    private JsonQuestion[] sortQuestions(JsonQuestion[] questions, JsonNode[] nodes)
    {
        List<JsonQuestion> sortedQuestions = new ArrayList<>();

        for (JsonNode node : nodes)
        {
            if (!contains(sortedQuestions, node.question))
            {
                sortedQuestions.add(findQuestionById(questions, node.question));
            }
        }

        return sortedQuestions.toArray(new JsonQuestion[0]);
    }

    private boolean contains(List<JsonQuestion> questions, String questionId)
    {
        return questions.stream().anyMatch(question -> question.id.equals(questionId));
    }

    private JsonRecommendation[] sortRecommendations(JsonRecommendation[] recommendations)
    {
        return Stream.of(recommendations)
            .sorted(comparing(recommendation -> recommendation.id))
            .toArray(JsonRecommendation[]::new);
    }

    private JsonCantonalRecommendation[] sortCantonalRecommendations(JsonCantonalRecommendation[] cantonalRecommendations)
    {
        return Stream.of(cantonalRecommendations)
                .sorted(comparing(recommendation -> recommendation.id))
                .toArray(JsonCantonalRecommendation[]::new);

    }

    private JsonQuestion findQuestionById(JsonQuestion[] questions, String questionId)
    {
        return Stream.of(questions)
                .filter(question -> question.id.equals(questionId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Unable to find question with id " + questionId));
    }

    public void save(String treeName) throws Exception
    {
        OutputStream outputStream = null;

        try
        {
            Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
            String json = gson.toJson(this);

            outputStream = new FileOutputStream(new File(String.format("json/flow-%s.json", treeName)));
            outputStream.write(json.getBytes());

        }
        finally
        {
            if (outputStream != null)
            {
                outputStream.close();
            }
        }
    }

    private void logError(String text)
    {
        System.err.println(text);
    }
}
