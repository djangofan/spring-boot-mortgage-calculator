# spring-boot-mortgage-calculator
Just a mortgage calculator application using spring-boot and Thymeleaf.

# Versions
<table>
  <tr>
    <th>Tags&#92;Versions</th>
    <th>Description</th>
  </tr>
  <tr>
    <td>0.90</td>
    <td>Initial prototype.</td>
  </tr>
  <tr>
    <td>1.0.0</td>
    <td>Initial release.</td>
  </tr>
</table>

# Features

1. Displays all payment dates during the loan amortization.
2. Supports compounded "end of period" payment type as well as up-front payment type.
3. Variable interest rate.
4. Variable initial loan balance.
5. Will calculate a arbitrary future value.
6. Calculates monthly payment.

# How-To

1. Using Maven, run 'mvn clean install' to build the .war file.
2. Then, place the .war in your Tomcat webapps dir.
3. Navigate locally to http://localhost:8080/mortgage-calculator/ or http://localhost:8080/

# Images Of Application

Form page:

![alt text](https://raw.githubusercontent.com/djangofan/spring-boot-mortgage-calculator/master/form.png)

Schedule page:

![alt text](https://raw.githubusercontent.com/djangofan/spring-boot-mortgage-calculator/master/schedule.png)

