package utils;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import model.Employee;

public class PrintEmployee {
  public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
  public static final DecimalFormat SALARY_FORMATTER = new DecimalFormat("#,##0.00");

  public static void print(Employee e) {
    System.out.println(
        "Name: " + e.getName() +
            " | BirthDate: " + e.getBirthDate().format(DATE_TIME_FORMATTER) +
            " | Salary: R$ " + SALARY_FORMATTER.format(e.getSalary()) +
            " | Role: " + e.getRole()
    );
  }

}