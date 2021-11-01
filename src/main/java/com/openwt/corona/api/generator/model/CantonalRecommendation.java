package com.openwt.corona.api.generator.model;

import com.openwt.corona.api.generator.LocalizedText;

public class CantonalRecommendation {

    private final String id;
    private LocalizedText description;

    public CantonalRecommendation(String id)
    {
        this.id = id;
    }

    public CantonalRecommendation description(LocalizedText text)
    {
        description = text;
        return this;
    }
}