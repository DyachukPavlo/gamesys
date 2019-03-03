package gamesys.domain;

import org.springframework.http.HttpStatus;

public class Response {
    private HttpStatus result;
    private Object payload;

    public static Response ok(Object payload){
        Response response = new Response();
        response.setPayload(payload);
        response.setResult(HttpStatus.OK);
        return response;
    }

    public Response() {
    }

    public Response(HttpStatus result, Object payload) {
        this.result = result;
        this.payload = payload;
    }

    public static Response error(String errorMessage) {
        return error(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static Response error(String errorMessage, HttpStatus httpStatus) {
        Response response = new Response();
        response.setPayload(errorMessage);
        response.setResult(httpStatus);

        return response;
    }

    private Response setPayload(Object payload) {
        this.payload = payload;
        return this;
    }

    public Object getPayload() {
        return payload;
    }

    public HttpStatus getResult() {
        return result;
    }

    private void setResult(HttpStatus result) {
        this.result = result;
    }
}
