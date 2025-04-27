package model.dto.requests;

public class UpdateRequestDto {
    private int requestId;
    private String status;
    private String responseNotes;

    public UpdateRequestDto(int requestId, String status, String responseNotes) {
        this.requestId = requestId;
        this.status = status;
        this.responseNotes = responseNotes;
    }

    public int getRequestId() {
        return requestId;
    }

    public String getStatus() {
        return status;
    }

    public String getResponseNotes() {
        return responseNotes;
    }


    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setResponseNotes(String responseNotes) {
        this.responseNotes = responseNotes;
    }
}