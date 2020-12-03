package ir.moke.foodpicker.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

@Provider
public class BusinessExceptionHandler implements ExceptionCode, ExceptionMapper<BusinessException> {
    @Override
    public Response toResponse(BusinessException exception) {
        String message = exception.getMessage();
        int code = exception.getCode();

        Response.Status status;
        switch (code) {
            case UNKNOWN_ERROR:
                status = Response.Status.INTERNAL_SERVER_ERROR;
                message = "خطای داخلی";
                break;
            case OBJECT_EXISTS:
                status = Response.Status.CONFLICT;
                break;
            case OBJECT_NOT_EXIST:
                status = Response.Status.NOT_FOUND;
                break;
            default:
                status = Response.Status.OK;
        }

        Map<String, Object> expMap = new HashMap<>();
        expMap.put("code", code);
        expMap.put("message", message);

        return Response.status(status)
                .entity(expMap)
                .build();
    }
}
