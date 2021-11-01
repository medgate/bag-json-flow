package com.openwt.corona.api.backend.model;

import com.mauriciotogneri.jsonschema.annotations.Default;
import com.mauriciotogneri.jsonschema.annotations.Name;

public class AcceptLanguage
{
    @Name("Accept-Language")
    @Default("en")
    public String language;
}