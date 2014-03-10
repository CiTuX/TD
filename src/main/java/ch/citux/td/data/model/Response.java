package ch.citux.td.data.model;

public class Response<Result> {

    public enum Status {
        OK,
        ERROR_URL,
        ERROR_CONNECTION,
        ERROR_CONTENT
    }

    private Status status;
    private Result result;

    public Response(Status status, Result result) {
        this.status = status;
        this.result = result;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "status = " + status + " | result = " + result;
    }
}
