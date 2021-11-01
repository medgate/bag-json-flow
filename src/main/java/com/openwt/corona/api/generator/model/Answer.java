package com.openwt.corona.api.generator.model;

import com.openwt.corona.api.generator.LocalizedText;

public class Answer
{
    private final String id;
    private LocalizedText text;

    public Answer(String id, LocalizedText text)
    {
        this.id = id;
        this.text = text;
    }
}