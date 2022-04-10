package com.woomi.buildPdf.seeds;

import com.woomi.buildPdf.entities.Employee;
import com.woomi.buildPdf.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class EmployeeSeed implements CommandLineRunner {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public void run(String... args) throws Exception {
        setEmployees();
    }

    public void setEmployees() {
        String[][] employees = {
            {"Komlan", "KODJO", "00809-25-42-63-97", "komlan.kodjo@woomi.com", "Male", "Seller", "25-08-1995", "04-04-2024", "Takbal city"},
            {"Ignore", "TAKOU", "00809-36-42-63-96", "ignore.takou@woomi.com", "Male", "Product Owner", "15-07-1994", "12-12-2023", "Djanta town"},
            {"Solange", "INIERE", "00809-45-53-23-18", "solange.iniere@woomi.com", "Female", "Communication Agent", "14-05-1997", "12-06-2024", "Slicon valley"},
            {"Itext", "JAVA", "00809-45-68-63-12", "itext.java@woomi.com", "Male", "Software Developer", "23-04-1996", "04-04-2024", "Fantastic world"},
            {"Django", "PYTHON", "00809-23-57-71-26", "django.python@woomi.com", "Male", "Software Developer", "13-03-1997", "12-02-2024", "Emergency city"},
            {"Laravel", "PHP", "00809-59-73-63-42", "laravel.php@woomi.com", "Female", "Software Developer", "16-07-1995", "14-11-2023", "It city"},
            {"Insert", "ORACLE", "00809-29-38-54-94", "insert.oracle@woomi.com", "Female", "Database Admin", "12-07-1996", "17-07-2024", "Phico valley"},
            {"Plsql", "POSTGRESQL", "00809-70-20-39-12", "plsql.postgresql@woomi.com", "Male", "Database Admin", "23-08-1995", "17-07-2024", "Promo city"},
            {"Koute", "FAKOU", "00809-84-17-64-51", "koute.fakou@woomi.com", "Male", "Product Seller", "15-04-1996", "06-06-2024", "Sun city"},
            {"Fabiane", "ISTA", "00809-63-37-18-14", "fabiane.ista@woomi.com", "Female", "Secretary", "25-08-1997", "26-04-2023", "Moon city"},
            {"itext", "PDF", "00809-26-39-17-28", "itext.pdf@woomi.com", "Male", "VAS Projet Manager", "21-07-1994", "02-03-2024", "Phibo tech land"},
            {"Red", "Phougami", "00809-43-54-12-77", "red.phougami@woomi.com", "Male", "Team Lead", "25-08-1994", "04-04-2024", "Gta road"},
            {"White", "LEBLANC", "00809-33-45-11-78", "white.leblanc@woomi.com", "Male", "Software Developer", "14-03-1996", "10-08-2024", "Road 52"},
            {"Spring", "BOOT", "00809-48-42-16-84", "spring.boot@woomi.com", "Female", "Java Developer", "15-08-1995", "14-01-2025", "Woomi House"},
            {"Safaya", "FATASOW", "00809-19-94-61-97", "safaya.fatasow@woomi.com", "Female", "DevOps", "25-08-1995", "12-02-2025", "Woomi City"},
            {"Koudous", "SANGUIRA", "00809-17-99-41-19", "koudous.sanguira@woomi.com", "Male", "DevOps", "21-07-1995", "12-02-2024", "Woomi City"},
        };

        for (String[] employee: employees) {
            if (!employeeRepository.existsByPhoneNumber(employee[2])) {
                employeeRepository.save(new Employee(employee[0], employee[1], employee[2], employee[3], employee[4], employee[5], employee[6], employee[7], employee[8]));
            }
        }
    }
}
