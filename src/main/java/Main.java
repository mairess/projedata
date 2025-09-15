import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import model.Employee;
import utils.PrintEmployee;

public class Main {
  private static final BigDecimal MINIMUM_WAGE = new BigDecimal("1212.00");

  public static void main(String[] args) {
    List<Employee> employees = new ArrayList<>(Arrays.asList(
        new Employee("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"),
        new Employee("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"),
        new Employee("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"),
        new Employee("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"),
        new Employee("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"),
        new Employee("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"),
        new Employee("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"),
        new Employee("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"),
        new Employee("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"),
        new Employee("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente")
    ));

    employees.removeIf(e -> e.getName().equals("João"));

    System.out.println("---- List of Employees ----");
    employees.forEach(PrintEmployee::print);
    System.out.println();

    employees.forEach(e -> e.setSalary((e.getSalary().multiply(new BigDecimal("1.10")))));

    System.out.println("---- After a 10% salary increase ----");
    employees.forEach(PrintEmployee::print);

    Map<String, List<Employee>> employeePerRole = employees.stream()
        .collect(Collectors.groupingBy(Employee::getRole));

    System.out.println("\n---- Employees grouped by role ----");
    employeePerRole.forEach((role, list) -> {
      System.out.println("Role: " + role);
      list.forEach(PrintEmployee::print);
      System.out.println();
    });

    System.out.println("\n---- Birthdays of October and December ----");
    employees.stream()
        .filter(e -> {
          int monthValue = e.getBirthDate().getMonthValue();
          return monthValue == 10 || monthValue == 12;
        })
        .forEach(PrintEmployee::print);

    Employee oldestEmployee = employees.stream()
        .min(Comparator.comparing(Employee::getBirthDate))
        .orElse(null);

    if (oldestEmployee != null) {
      int age = Period.between(oldestEmployee.getBirthDate(), LocalDate.now()).getYears();
      System.out.println("\n---- Oldest employee ----");
      System.out.println("Name: " + oldestEmployee.getName() + " | Age: " + age + " years old");
    }

    System.out.println("\n---- Employees in alphabetical order ----");
    employees.stream()
        .sorted(Comparator.comparing(Employee::getName))
        .forEach(PrintEmployee::print);

    BigDecimal totalSalaries = employees.stream()
        .map(Employee::getSalary)
        .reduce(BigDecimal.ZERO, BigDecimal::add);

    System.out.println("\n---- Payroll total ----");
    System.out.println("R$ " + PrintEmployee.SALARY_FORMATTER.format(totalSalaries));

    System.out.println("\n---- Minimum wage per employee ----");
    employees.forEach(e -> {
      BigDecimal salaries = e.getSalary().divide(MINIMUM_WAGE, 2, BigDecimal.ROUND_HALF_UP);
      System.out.println(e.getName() + " earns " + salaries + " minimum wage.");
    });

  }

}