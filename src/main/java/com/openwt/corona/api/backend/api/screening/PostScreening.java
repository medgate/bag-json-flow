package com.openwt.corona.api.backend.api.screening;

import com.mauriciotogneri.stewie.annotations.EndPoint;
import com.mauriciotogneri.stewie.annotations.Parameters;
import com.mauriciotogneri.stewie.annotations.Response;
import com.mauriciotogneri.stewie.annotations.Responses;
import com.mauriciotogneri.stewie.types.MimeType;
import com.openwt.corona.api.backend.api.screening.PostScreening.DataParameters;
import com.openwt.corona.api.backend.model.AcceptLanguage;
import com.openwt.corona.api.backend.model.Node;
import com.openwt.corona.api.backend.model.Profile;

import static com.mauriciotogneri.stewie.types.Method.POST;
import static com.mauriciotogneri.stewie.types.StatusCode.BAD_REQUEST;
import static com.mauriciotogneri.stewie.types.StatusCode.OK;

@EndPoint(
        path = "/v1/screening/questions",
        method = POST
)
@Parameters(
        data = DataParameters.class,
        header = AcceptLanguage.class
)
@Responses({
        @Response(
                code = OK,
                produces = MimeType.JSON,
                type = Node.class,
                description = "Successful operation"
        ),
        @Response(
                code = BAD_REQUEST,
                description = "Bad Request"
        )
})
public interface PostScreening
{
    class DataParameters
    {
        public String version;

        public Profile profile;

        public String[] nodes;
    }
}