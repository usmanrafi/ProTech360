package fyp.protech360.classes;



public class Request {

    private String requestUID;
    private String requestName;
    private int requestType;

    public Request(String requestID, String requestName, int requestType) {
        this.requestUID = requestID;
        this.requestName = requestName;
        this.requestType = requestType;
    }

    public String getRequestUID() {
        return requestUID;
    }

    public void setRequestUID(String requestID) {
        this.requestUID = requestID;
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
