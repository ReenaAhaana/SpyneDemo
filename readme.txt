Project Name: SpyneDemo

Description: This project demonstrates a Image Upscaling on Spyne application

Project Structure:

- src/main/java:
    - utils.TestReportListner: Utility listener class (e.g., TestListener.java)
- src/main/resources:
    - files inputs required for testcases e.g. files to upload
- pom.xml: Maven project configuration file
- TestNG.xml - TestNG configuration file

Dependencies:

- Selenium WebDriver
- TestNG
- Maven

Running the Project:

1. Install Maven and Java on your machine.
2. Clone or download the project repository.
3. Navigate to the project directory in your terminal/command prompt.
4. Run mvn clean test to execute the test.

Test Class: SpyneImageTest.java

- Contains test methods to demonstrate basic upscaling usecases

Utility Listener Class: TestListener.java

- Implements TestNG's ITestListener interface to listen for test events such as TakeScreenshots

