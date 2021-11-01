package com.openwt.corona.api.backend.api.screening;

import com.mauriciotogneri.stewie.annotations.EndPoint;
import com.mauriciotogneri.stewie.annotations.Response;
import com.mauriciotogneri.stewie.annotations.Responses;
import com.mauriciotogneri.stewie.types.MimeType;
import com.openwt.corona.api.backend.model.Screening;

import static com.mauriciotogneri.stewie.types.Method.GET;
import static com.mauriciotogneri.stewie.types.StatusCode.OK;

@EndPoint(
        path = "/v1/screening/questions",
        method = GET
)
@Responses({
        @Response(
                code = OK,
                produces = MimeType.JSON,
                type = Screening.class,
                description = "Successful operation"
        )
})
public interface GetScreening
{
}