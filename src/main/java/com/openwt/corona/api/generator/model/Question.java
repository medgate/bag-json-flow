package com.openwt.corona.api.generator.model;

import com.openwt.corona.api.generator.LocalizedText;

public class Question
{
    private final String id;
    private LocalizedText title;
    private LocalizedText description;

    public Question(String id, LocalizedText title, LocalizedText description)
    {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public void title(LocalizedText text)
    {
        title = text;
    }

    public void description(LocalizedText text)
    {
        description = text;
    }
}