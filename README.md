# Generate and Download Pdf

This project is a simple spring boot app (Rest Api) to generate a secured pdf document and to download files.

The pdf file is generated with itextpdf library. 
It's a dynamic file containing all the employees saved in the database. 

## Content
The app has two resources:
* The first resource is to generate a secured pdf file containing the list of employees registered in the database.
It also generates a txt file containing the password to open the pdf file.
  
* The second resource allows to download the generated files (pdf/txt). 
  It's a GET resource and takes the file type, and the file name as parameters.

## How to quickly start?

- Clone the project
- Make sure you have java, maven and mysql installed
- Set your database credentials in the **application.properties** file
- Download dependencies ``mvn clean install`` or through your IDE (like intellij)
- Run the project ``mvn spring-boot:run`` or through your IDE (like intellij). 
  The fake employees from ***seeds/EmployeeSeed** will be automatically saved in the database.
- Test The endpoints, for example: http://localhost:8080/employees/generate/pdf

## Remarks

- To be able to use html when building the pdf content, you have to add an itextpdf tool (xmlworker) you can find it in the pom file of the project
- In some cases, you can get some errors when you encrypt the pdf file. Bellow is the error:

----
java.lang.ClassNotFoundException: org.bouncycastle.asn1.ASN1Encodable
at java.base/jdk.internal.loader.BuiltinClassLoader.loadClass(BuiltinClassLoader.java:581) ~[na:na]
at java.base/jdk.internal.loader.ClassLoaders$AppClassLoader.loadClass(ClassLoaders.java:178) ~[na:na]
at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:522) ~[na:na]
at com.itextpdf.text.pdf.PdfEncryption.<init>(PdfEncryption.java:147) ~[itextpdf-5.5.13.jar:5.5.13]
at com.itextpdf.text.pdf.PdfWriter.setEncryption(PdfWriter.java:2132) ~[itextpdf-5.5.13.jar:5.5.13]
----

So you have to add the **"bcprov-jdk15on"** dependency.