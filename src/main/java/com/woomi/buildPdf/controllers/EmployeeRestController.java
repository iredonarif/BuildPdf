package com.woomi.buildPdf.controllers;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.woomi.buildPdf.entities.Employee;
import com.woomi.buildPdf.entities.responses.GeneratePdfResponse;
import com.woomi.buildPdf.entities.responses.PdfResponseData;
import com.woomi.buildPdf.entities.responses.ResponseStatus;
import com.woomi.buildPdf.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.core.io.ResourceLoader;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("employees")
public class EmployeeRestController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ResourceLoader resourceLoader;

    public static final String GENERATED_FILES_DIR = "../Generated_Files_Dir/";

    @GetMapping("download/{type}/{fileName:.+}")
    public void downloadPdf(HttpServletResponse response, @PathVariable("type") String type, @PathVariable("fileName") String fileName) throws IOException {
        File file = new File(GENERATED_FILES_DIR + fileName);
        System.out.println("File Name => " + fileName);

        if (file.exists()) {
            System.out.println("File exists");

            response.setContentType(getFileContentType(type));
            response.setHeader("Content-Disposition", String.format("attachment; filename=" + file.getName()));
            response.setContentLength((int) file.length());
            InputStream inputStream = file.toURL().openStream();
            FileCopyUtils.copy(inputStream, response.getOutputStream());
        }
        else System.out.println("File doesn't exists");
    }

    @GetMapping("generate/pdf")
    private GeneratePdfResponse generatePdf() {
        GeneratePdfResponse response = new GeneratePdfResponse();
        try {
            // CREATE PDF FILE
            String fileNameWithoutExt = getFileName();
            String pdfFileName = fileNameWithoutExt + ".pdf";

            Document document = new Document();

            File generatedFilesDir = new File(GENERATED_FILES_DIR);
            if (!generatedFilesDir.exists()) {
                System.out.println("Directory doesn't exists.");
                generatedFilesDir.mkdir();
                System.out.println("Generated files directory created successfully....");
            }
            PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(GENERATED_FILES_DIR + pdfFileName));
            String pdfPwd = generateRandomCodee(8);
            pdfWriter.setEncryption(pdfPwd.getBytes(), pdfPwd.getBytes(), PdfWriter.ALLOW_COPY, PdfWriter.STANDARD_ENCRYPTION_40);
            document.open();

            StringReader stringReader = new StringReader(buildPdfContent());

            XMLWorkerHelper xwh = XMLWorkerHelper.getInstance();
            xwh.parseXHtml(pdfWriter, document, stringReader);

            document.close();
            stringReader.close();
            // END PDF FILE CREATION

            // PDF PASSWORD FILE -- IT'S A TEXT FILE
            String textFileName = fileNameWithoutExt + ".txt";
            createTextFile(textFileName, pdfPwd);
            // END

            response.setStatus(new ResponseStatus(200, "SUCCESS", "Request succeed. Files generated successfully."));
            response.setData(new PdfResponseData(pdfFileName, textFileName));
        }
        catch (Exception e) {
            System.out.println("Exception message => " + e.getMessage());

            response.setStatus(new ResponseStatus(400, "REQUEST_FAILED", "Request failed. Something went wrong."));
            response.setData(null);
        }
        return response;
    }

    private String buildPdfContent() {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html><html lang='en'><head><meta charset='utf-8'/><title>Employees</title>");
        sb.append("<style type='text/css'> .employeesTable { border-collapse:collapse; width:90%; } table[class='employeesTable'] th, table[class='employeesTable'] td {border:1px solid black;width:20%} table[class='employeesTable'] td {text-align:center;}caption {font-weight:bold}</style>");
        sb.append("</head><body>");
        sb.append("<div><br></br><br></br>");
        sb.append("<h2 style='color:blue;text-align:center;'>List of Woomi employees</h2>");

        sb.append("<table border='1' class='employeesTable'>");
        sb.append("<tr>" +
                "<th>First Name</th>" +
                "<th>Last Name</th>" +
                "<th>Phone Number</th>" +
                "<th>Email</th>" +
                "<th>Gender</th>" +
                "<th>Role</th>" +
                "<th>Birth Date</th>" +
                "<th>Joined Date</th>" +
                "<th>Address</th>" +
                "</tr>");

        for (Employee employee : employeeRepository.findAll()) {

            sb.append("<tr>" +
                "<td>" + employee.getFirstName() + "</td>" +
                "<td>" + employee.getLastName() + "</td>" +
                "<td>" + employee.getPhoneNumber() + "</td>" +
                "<td>" + employee.getEmail() + "</td>" +
                "<td>" + employee.getGender() + "</td>" +
                "<td>" + employee.getRole() + "</td>" +
                "<td >" + employee.getBirthDate() + "</td>" +
                "<td>" + employee.getJoinedDate() + "</td>" +
                "<td>" + employee.getAddress() + "</td>" +
                "</tr>");
        }
        sb.append("</table>");

        sb.append("</div></body></html>");
        return sb.toString();
    }

    private void createTextFile(String fileName, String pwd) throws IOException {
        FileWriter fileWriter = new FileWriter(GENERATED_FILES_DIR + fileName);
        fileWriter.write(pwd);
        fileWriter.close();
    }

    private String getFileName() {
        DateFormat df = new SimpleDateFormat("ddMMyyyyhhmm");
        String date = df.format(new Date());

        return "woomi-employees-" + date;
    }

    private String getFileContentType(String contentType) {
        String type;
        if (contentType.equals("pdf")) {
            type = "application/pdf";
        }
        else if (contentType.equals("text")) {
            type = "text/plain";
        }
        else type = "application/octet-stream";

        return type;
    }

    private String generateRandomCodee(int length) {
        String characters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder token = new StringBuilder();
        for (int i = 0; i <= length; i++) {
            token.append(characters.charAt(getRandomNumber(characters.length() - 1)));
        }
        return token.toString();
    }

    private int getRandomNumber(int max) {
        return (int)(1 + (max - 1) * Math.random());
    }
}
