package com.openwt.corona.api.backend.api.screening;

import com.mauriciotogneri.stewie.annotations.EndPoint;
import com.mauriciotogneri.stewie.annotations.Parameters;
import com.mauriciotogneri.stewie.annotations.Response;
import com.mauriciotogneri.stewie.annotations.Responses;
import com.openwt.corona.api.backend.api.screening.FinishScreening.DataParameters;
import com.openwt.corona.api.backend.model.AcceptLanguage;
import com.openwt.corona.api.backend.model.Profile;

import static com.mauriciotogneri.stewie.types.Method.POST;
import static com.mauriciotogneri.stewie.types.StatusCode.BAD_REQUEST;
import static com.mauriciotogneri.stewie.types.StatusCode.NO_CONTENT;

@EndPoint(
        path = "/v1/screening/results",
        method = POST
)
@Parameters(
        data = DataParameters.class,
        header = AcceptLanguage.class
)
@Responses({
        @Response(
                code = NO_CONTENT,
                description = "Successful operation"
        ),
        @Response(
                code = BAD_REQUEST,
                description = "Bad Request"
        )
})
public interface FinishScreening
{
    class DataParameters
    {
        public String version;

        public Profile profile;

        public String[] nodes;

        public String recommendation;
    }
}