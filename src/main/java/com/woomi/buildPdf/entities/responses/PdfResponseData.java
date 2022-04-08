package com.woomi.buildPdf.entities.responses;

public class PdfResponseData {
    private String pdfFileName;
    private String pwdFileName;

    public PdfResponseData(String pdfFileName, String pwdFileName) {
        this.pdfFileName = pdfFileName;
        this.pwdFileName = pwdFileName;
    }

    public PdfResponseData() {
    }

    public String getPdfFileName() {
        return pdfFileName;
    }

    public void setPdfFileName(String pdfFileName) {
        this.pdfFileName = pdfFileName;
    }

    public String getPwdFileName() {
        return pwdFileName;
    }

    public void setPwdFileName(String pwdFileName) {
        this.pwdFileName = pwdFileName;
    }
}
