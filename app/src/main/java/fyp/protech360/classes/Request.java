package fyp.protech360.classes;



public class Request {

    private String requestUID;
    private String requestName;
    private String sentTo;
    private int requestType;
    private int requestRange;

    public Request(String requestID, String requestName, String sentTo, int requestType) {
        this.requestUID = requestID;
        this.requestName = requestName;
        this.sentTo = sentTo;
        this.requestType = requestType;
    }

    public Request(String requestUID, String requestName, String sentTo, int requestType, int requestRange) {
        this.requestUID = requestUID;
        this.requestName = requestName;
        this.sentTo = sentTo;
        this.requestType = requestType;
        this.requestRange = requestRange;
    }

    public int getRequestRange() {
        return requestRange;
    }

    public void setRequestRange(int requestRange) {
        this.requestRange = requestRange;
    }

    public Request() {
    }

    public String getSentTo() {
        return sentTo;
    }

    public void setSentTo(String sentTo) {
        this.sentTo = sentTo;
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
