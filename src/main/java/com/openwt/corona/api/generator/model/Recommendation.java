package com.openwt.corona.api.generator.model;

import com.openwt.corona.api.generator.LocalizedText;

public class Recommendation
{
    private final String id;
    private final String severity;
    private LocalizedText title;
    private LocalizedText description;
    private LocalizedText cantonalDescription;
    private LocalizedText medgateTitle;
    private LocalizedText medgateDescription;

    public Recommendation(String id, String severity)
    {
        this.id = id;
        this.severity = severity;
    }

    public Recommendation title(LocalizedText text)
    {
        title = text;
        return this;
    }

    public Recommendation description(LocalizedText text)
    {
        description = text;
        return this;
    }

    public Recommendation cantonalDescription(LocalizedText text)
    {
        cantonalDescription = text;
        return this;
    }

    public Recommendation medgateTitle(LocalizedText text)
    {
        medgateTitle = text;
        return this;
    }

    public Recommendation medgateDescription(LocalizedText text)
    {
        medgateDescription = text;
        return this;
    }
}
