package com.woomi.buildPdf.entities.responses;

public class GeneratePdfResponse {
    private ResponseStatus status;
    private PdfResponseData data;

    public GeneratePdfResponse(ResponseStatus status, PdfResponseData data) {
        this.status = status;
        this.data = data;
    }

    public GeneratePdfResponse() {
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public PdfResponseData getData() {
        return data;
    }

    public void setData(PdfResponseData data) {
        this.data = data;
    }
}
