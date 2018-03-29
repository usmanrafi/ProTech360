package fyp.protech360.classes;



public class Request {

    private String requestID;
    private String requestName;
    private int requestType;

    public Request(String requestID, String requestName, int requestType) {
        this.requestID = requestID;
        this.requestName = requestName;
        this.requestType = requestType;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public String getRequestName() {
        return requestName;
    }

    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }

    public int getRequestType() {
        return requestType;
    }

    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }
}
