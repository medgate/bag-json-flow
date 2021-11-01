package com.openwt.corona.api.generator;

public class LocalizedText
{
    private String en;
    private String de;
    private String fr;
    private String it;

    public LocalizedText(String en, String de, String fr, String it)
    {
        this.en = en;
        this.de = de;
        this.fr = fr;
        this.it = it;
    }

    public void addEN(String text)
    {
        this.en = text;
    }

    public void addDE(String text)
    {
        this.de = text;
    }

    public void addFR(String text)
    {
        this.fr = text;
    }

    public void addIT(String text)
    {
        this.it = text;
    }
}