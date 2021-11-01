package com.openwt.corona.api.backend.model;

import com.mauriciotogneri.jsonschema.annotations.Optional;

public class Answer
{
    public AnswerType type;

    public String text;

    public String node;

    @Optional
    public Recommendation recommendation;
}