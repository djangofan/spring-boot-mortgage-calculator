# spring-boot-mortgage-calculator
Just a mortgage calculator application using spring-boot and Thymeleaf.

# Versions
<table>
  <tr>
    <th>Tags&#92;Versions</th>
    <th>Description</th>
    <th>Date</th>
  </tr>
  <tr>
    <td>0.90</td>
    <td>Initial prototype.</td>
    <td>Dec 19, 2015</td>
  </tr>
  <tr>
    <td>1.0.0</td>
    <td>Initial release.</td>
    <td>Dec 20, 2015</td>
  </tr>
  <tr>
    <td>1.0.1</td>
    <td>Refactored unnecessary controller logic into separate Service classes.</td>
    <td>Dec 22, 2015</td>
  </tr>
  <tr>
    <td>1.0.2</td>
    <td>In progress...</td>
    <td></td>
  </tr>
</table>

# Features

1. Displays all payment dates during the loan amortization.
2. Supports compounded "end of period" payment type as well as up-front payment type.
3. Variable interest rate.
4. Variable initial loan balance.
5. Will calculate a arbitrary future value.
6. Calculates monthly payment.

TODO features:

- On the amortization page, be able to add an increase payment at any point during the loan term and recalculate the ending date (with remainder).
- If user enters desired mortgage payment, clear the 'durationInMonths' field, and calculate the loan term with that payment.  Do not allow infinity.


# How-To

1. Install Gradle 2.10+ on your local system.  Put GRADLE_HOME/bin on your PATH variable.
2. Make sure you have Java 1.8 JDK (not 1.7 and not a JRE).
3. Import this Gradle project into your IDE.  I use IntelliJ-IDEA, but this might also work with Eclipse or "Spring Source Suite".
4. Using Gradle, or a 'run configuration' in your IDE, run 'gradle clean build' to build the .war application archive.
5. To run the application, run 'gradle bootRun'.
6. Navigate locally to http://localhost:8080/

# Images Of Application

Form page:

![alt text](https://raw.githubusercontent.com/djangofan/spring-boot-mortgage-calculator/master/form.png)

Schedule page:

![alt text](https://raw.githubusercontent.com/djangofan/spring-boot-mortgage-calculator/master/schedule.png)

