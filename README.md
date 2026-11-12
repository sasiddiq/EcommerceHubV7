🛒 EcommerceHub V7
Hybrid Test Automation Framework | Selenium • Java • TestNG • Maven
A modular UI test automation framework for an e-commerce application, designed for maintainability, repeatable execution, and extensible reporting.





Repository · Getting Started · Execution · Architecture
</div>📖 Overview
EcommerceHub V7 is a Java-based Selenium automation project organized around a hybrid testing approach: reusable page objects, TestNG test orchestration, Maven dependency management, and supporting utilities for test data, logging, and reports.

It is intended as a practical foundation for automated e-commerce regression and smoke testing, and as a demonstration of QA automation engineering practices.
Implementation note: The public repository confirms the dependencies and top-level folders described below. Some advanced features in this document are marked verify/configure because their Java implementations were not fully inspected. Adjust those descriptions to match the actual source before publishing.

✨ Key Capabilities
Area	Approach / Tool
Browser automation	Selenium WebDriver 4.51.0
Programming language	Java
Test management	TestNG 7.12.0
Build and dependencies	Apache Maven
UI design pattern	Page Object Model (verify implementation)
Data-driven testing	Apache POI 6.0.0 and TestNG DataProvider (verify wiring)
Execution reporting	ExtentReports 5.1.2; TestNG output
Logging	Apache Log4j2 2.26.1
Failure diagnostics	Screenshots and listeners (verify implementation)
CI/CD	Maven CLI; Jenkins integration can be configured
Email utilities	Apache Commons Email 1.6.0 dependency


🏗️ Framework Architecture
                         ┌───────────────────────┐
                         │    TestNG Suite       │
                         │     testng.xml        │
                         └───────────┬───────────┘
                                     │
                         ┌───────────▼───────────┐
                         │      Test Cases       │
                         └───────────┬───────────┘
                                     │
                ┌────────────────────▼────────────────────┐
                │    Page Objects / Reusable Actions      │
                └────────────────────┬────────────────────┘
                                     │
                         ┌───────────▼───────────┐
                         │  Selenium WebDriver   │
                         └───────────┬───────────┘
                                     │
                         ┌───────────▼───────────┐
                         │  E-commerce Web App   │
                         └───────────────────────┘

        Supporting services: Test Data • Logs • Reports • Screenshots
The architecture separates test intent from page interaction logic, helping reduce duplication and simplify maintenance as the test suite grows.
📂 Repository Structure
The following top-level paths are visible in the public repository:
EcommerceHubV7/
├── src/
│   └── test/               # Automation test source tree
├── testData/               # Test data assets
├── screenshots/            # Screenshot artifacts
├── reports/                # Reporting artifacts
├── logs/                   # Execution logs
├── test-output/            # TestNG-generated output
├── target/                 # Maven build artifacts
├── pom.xml                 # Maven dependencies and build configuration
├── testng.xml              # TestNG suite configuration
└── README.md               # Project documentation
The detailed package layout inside src/test should be checked against the actual repository before adding a package-by-package tree (for example, pageObjects, testCases, testBase, and utilities).

🧰 Technology Stack
Component	Version in pom.xml	Purpose
Selenium Java	4.51.0	Browser interaction and UI automation
TestNG	7.12.0	Test execution, assertions, groups, and suites
ExtentReports	5.1.2	HTML execution reporting
Apache POI / POI OOXML	6.0.0	Excel file support
Log4j API / Core	2.26.1	Application and test logging
Log4j SLF4J binding	2.26.1	Logging bridge
Apache Commons Email	1.6.0	Email functionality
Maven Surefire	3.5.4	Maven test execution plugin
Maven Compiler Plugin	3.16.0	Java compilation plugin


🚀 Getting Started
Prerequisites
- JDK installed and JAVA_HOME configured (Java 21 is a practical starting point; verify project compiler settings).
- Apache Maven installed, or use an IDE with Maven integration.
- Google Chrome, Mozilla Firefox, or another browser supported by the project's WebDriver setup.
- Git installed.
- Network access for first-time Maven dependency downloads.
Clone the repository
git clone https://github.com/sasiddiq/EcommerceHubV7.git
cd EcommerceHubV7
Confirm your environment
java -version
mvn -version
Download dependencies and compile tests
mvn clean test-compile
If the test source or environment requires additional properties, configure them before running the full suite.
▶️ Running Tests
The repository contains a root-level testng.xml and configures Maven Surefire to use it.
Run the configured suite
mvn clean test
Run from an IDE
1. Import the project as an Existing Maven Project.
2. Allow Maven dependencies to resolve.
3. Open testng.xml.
4. Run the suite with the TestNG plugin/integration.
5. Inspect the output under the configured reporting directories.
Targeted TestNG execution
TestNG supports group-based and class-based execution. Exact commands depend on the suite definitions and Surefire configuration. For example, after confirming group definitions:
mvn test -Dgroups=regression
Validate targeted commands against the project's testng.xml and any custom listeners or configuration before documenting them as supported workflows.

🧪 Test Design
A well-structured hybrid framework typically separates responsibilities:
- Test classes express scenarios and assertions.
- Page objects encapsulate locators and UI interactions.
- Base/setup code handles browser creation and cleanup.
- Utilities manage test data, screenshots, and configuration.
- Listeners respond to test lifecycle events for logging and reporting.
Illustrative TestNG test
The example below demonstrates the intended separation of concerns; it is not copied from this repository.
@Test(groups = {"smoke", "regression"})
public void userCanOpenLoginPage() {
    HomePage home = new HomePage(getDriver());
    home.clickMyAccount();
    home.goToLogin();
    // Assert the expected login page state here.
}
📊 Data-Driven Testing
The project declares Apache POI dependencies for Excel handling. When paired with TestNG @DataProvider, Excel-backed data can support reusable scenarios with multiple input combinations.

Recommended flow: Excel workbook → data reader utility → TestNG DataProvider → test method → result/report.

Verify the actual workbook paths, sheet names, and DataProvider classes in src/test before adding concrete execution examples.
📈 Reporting, Logging & Screenshots
The repository includes directories named reports/, logs/, screenshots/, and test-output/, as well as ExtentReports and Log4j2 dependencies.
Artifact	Typical purpose
reports/	Custom HTML or summary reports
test-output/	TestNG-generated reports
logs/	Runtime and diagnostic log files
screenshots/	Visual evidence captured during test execution


Recommended reporting behavior (verify implementation): log test start/end, record assertion failures, capture screenshots on failure, attach evidence to ExtentReports, and retain useful build artifacts in CI.
🔁 Continuous Integration with Jenkins
The framework can be executed from a Jenkins job that checks out the repository and invokes Maven. A basic pipeline template is provided below; this is a suggested integration, not a verified committed Jenkinsfile.
pipeline {
    agent any

    tools {
        jdk 'JDK21'
        maven 'Maven3'
    }

    stages {
        stage('Checkout') {
            steps { checkout scm }
        }
        stage('Run TestNG Suite') {
            steps { bat 'mvn -B clean test' }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'reports/**/*,screenshots/**/*,logs/**/*,test-output/**/*',
                             allowEmptyArchive: true
        }
    }
}
Jenkins setup notes: This example assumes a Windows agent, Jenkins installations named JDK21 and Maven3, Git checkout configuration, and a browser/WebDriver environment. On Linux agents, replace bat with sh. Configure a test-report publisher separately if needed.
🛠️ Troubleshooting
Issue	What to check
Git clone fails	Repository URL, Git installation, network/proxy, and credentials if required
Maven dependencies fail	Maven settings, repository access, Java version, and pom.xml
NoSuchElementException	Locator validity, waits, frames/windows, page state, and browser differences
Tests not discovered	testng.xml, package/class naming, TestNG annotations, Surefire configuration
Browser does not start	Browser availability, driver setup, permissions, and headless configuration
Reports missing	Reporter/listener registration, output paths, filesystem permissions


🔐 Configuration & Repository Hygiene
- Keep passwords, tokens, and credentials out of committed source and configuration files.
- Prefer environment variables or CI credentials stores for secrets.
- Avoid committing generated build artifacts and temporary test outputs unless intentionally publishing examples.
- Consider adding or updating .gitignore for target/, transient test-output/, logs, and temporary screenshots/reports.
- Do not expose personal or production customer data in Excel fixtures, logs, screenshots, or reports.
🗺️ Potential Enhancements
- [ ] Document verified test modules and real end-to-end scenarios.
- [ ] Add a concise package-level architecture diagram.
- [ ] Add CI execution with published reports and archived screenshots.
- [ ] Introduce browser/environment configuration through CI parameters.
- [ ] Document retry logic, parallel execution, and thread safety if implemented.
- [ ] Add sample sanitized ExtentReports screenshots.
- [ ] Add a CONTRIBUTING guide and an appropriate LICENSE file.
👨‍💻 Maintainer
Shadab Siddiqui
GitHub: @sasiddiq
<div align="center">Built to demonstrate practical, maintainable QA automation engineering.

⭐ If you find this project useful, consider starring the repository.
