package com.openwt.corona.api.backend.api.faq;

import com.mauriciotogneri.stewie.annotations.EndPoint;
import com.mauriciotogneri.stewie.annotations.Parameters;
import com.mauriciotogneri.stewie.annotations.Response;
import com.mauriciotogneri.stewie.annotations.Responses;
import com.mauriciotogneri.stewie.types.MimeType;
import com.openwt.corona.api.backend.model.AcceptLanguage;
import com.openwt.corona.api.backend.model.FAQ;

import static com.mauriciotogneri.stewie.types.Method.GET;
import static com.mauriciotogneri.stewie.types.StatusCode.OK;

@EndPoint(
        path = "/v1/faq",
        method = GET
)
@Parameters(
        header = AcceptLanguage.class
)
@Responses({
        @Response(
                code = OK,
                produces = MimeType.JSON,
                type = FAQ[].class,
                description = "Successful operation"
        )
})
public interface GetFAQ
{
}