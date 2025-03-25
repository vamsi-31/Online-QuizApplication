# Online Quiz Application

[![License](https://img.shields.io/github/license/vamsi-31/Online-QuizApplication)](https://github.com/vamsi-31/Online-QuizApplication/blob/main/LICENSE)
[![GitHub Issues](https://img.shields.io/github/issues/vamsi-31/Online-QuizApplication)](https://github.com/vamsi-31/Online-QuizApplication/issues)
[![GitHub Pull Requests](https://github.com/vamsi-31/Online-QuizApplication)](https://github.com/vamsi-31/Online-QuizApplication/pulls)
[![Java CI with Maven](https://github.com/vamsi-31/Online-QuizApplication/actions/workflows/maven.yml/badge.svg)](https://github.com/vamsi-31/Online-QuizApplication/actions/workflows/maven.yml)
[![codecov](https://codecov.io/gh/vamsi-31/Online-QuizApplication/branch/main/graph/badge.svg?token=YOUR_CODECOV_TOKEN)](https://codecov.io/gh/vamsi-31/Online-QuizApplication)

## Overview

This Online Quiz Application is a comprehensive system designed for creating, managing, and taking quizzes through a command-line interface.  It offers role-based access control (Admin/User), ensuring secure and efficient quiz administration and participation. **The application has been refactored to leverage the Spring Core framework for dependency injection and component management, enhancing maintainability and scalability.** Leveraging a layered architecture and robust error handling, this application is designed for testability, maintainability, and future extensibility.

## Key Features

*   **User Management:**
    *   Secure registration and authentication with role-based access (ADMIN/USER).
    *   Secure password handling (consider using hashing in future implementations for enhanced security).
*   **Quiz Management (Admin):**
    *   Creation of quizzes with titles, descriptions, and question sets.
    *   Automatic generation of unique access codes for quiz access.
    *   Listing and searching quizzes.
    *   Quiz locking to prevent modification after deployment.
*   **Question Management (Admin):**
    *   Creation of multiple-choice questions.
    *   Configuration options for difficulty level, topic tagging, and point values.
    *   Centralized question repository.
*   **Quiz Taking (User):**
    *   Simple quiz access via unique access codes.
    *   Real-time score calculation and feedback on completion.  (Note: Viewing quiz results is a planned feature enhancement.)
*   **Console UI:**
    *   User-friendly command-line interface with clear prompts.
*   **Logging and Error Handling:**
    *   Integration with SLF4j and Logback for comprehensive logging.
    *   Custom exception handling for graceful error recovery.
*   **Testing and Code Quality:**
    *   Extensive unit tests using JUnit and Mockito.
    *   GitHub Actions for continuous integration (CI).
    *   Codecov integration for measuring code coverage.

## Technologies Used

*   **Java 17+:** The core programming language.
*   **Maven:** Project management, dependency resolution, and build automation.
*   **Spring Framework:** For dependency injection and managing application components.  **This project has been migrated to use Spring Core for dependency management and configuration.**
*   **SLF4j & Logback:**  Flexible and efficient logging.
*   **JUnit & Mockito:**  Unit testing.
*   **GitHub Actions:**  Continuous Integration (CI).
*   **Codecov:**  Code coverage reporting.

## Getting Started

### Prerequisites

*   **Java Development Kit (JDK) 17 or higher:**  Download from [Oracle](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html) or [OpenJDK](https://openjdk.java.net/projects/jdk/17/). Set `JAVA_HOME`.
*   **Maven:** Download from [Apache Maven](https://maven.apache.org/download.cgi).  Add the `bin` directory to your system's PATH.
*   **Git:** Download from [Git SCM](https://git-scm.com/downloads).

### Installation

1.  **Clone the repository:**

    ```bash
    git clone https://github.com/vamsi-31/Online-QuizApplication.git
    cd Online-QuizApplication
    ```

2.  **Build the project with Maven:**

    ```bash
    mvn clean package
    ```

    This creates a self-contained "uber JAR" in the `target/` directory using the `maven-shade-plugin`.

### Running the Application

1.  **Execute the JAR file:**

    ```bash
    java -jar target/OnlineQuizManagement-1.0-SNAPSHOT.jar
    ```

    This starts the application and displays the console UI.

### Usage

Interact with the application through the console menu.

1.  **Login/Register:** Register as a new user (ADMIN or USER) or login with existing credentials.

2.  **Admin Menu:** Available to ADMIN users:
    *   Create, view, and manage quizzes and questions.
    *   Lock quizzes.

3.  **User Menu:** Available to USER users:
    *   Take quizzes using access codes.
    *   View quiz results (feature enhancement in progress).

Follow the on-screen prompts to navigate the menus.

## Project Architecture

The project follows a layered architecture:

```🌳 OnlineQuizApplication/
├── 📁 src/
│   ├── 📁 main/
│   │   ├── 📁 java/
│   │   │   ├── 📁 com/onlinequiz/
│   │   │   │   ├── 📁 config/ 📜 Spring Configuration
│   │   │   │   │   ├── 📄 AppConfig.java
│   │   │   │   ├── 📁 dao/ 📜 Data Access Object Interfaces (Contracts for data access)
│   │   │   │   │   ├── 📄 QuestionDAO.java
│   │   │   │   │   ├── 📄 QuizDAO.java
│   │   │   │   │   ├── 📄 UserDAO.java
│   │   │   │   ├── 📁 dao/impl/ 📜 Data Access Object Implementations (Concrete data access logic)
│   │   │   │   │   ├── 📄 QuestionDAOImpl.java
│   │   │   │   │   ├── 📄 QuizDAOImpl.java
│   │   │   │   │   ├── 📄 UserDAOImpl.java
│   │   │   │   ├── 📁 exception/ 📜 Custom Exception Classes (Application-specific error handling)
│   │   │   │   │   ├── 📄 QuestionException.java
│   │   │   │   │   ├── 📄 QuizException.java
│   │   │   │   │   ├── 📄 UserException.java
│   │   │   │   ├── 📁 models/ 📜 Data Model Classes (Representing application entities)
│   │   │   │   │   ├── 📄 Question.java
│   │   │   │   │   ├── 📄 Quiz.java
│   │   │   │   │   ├── 📄 User.java
│   │   │   │   ├── 📁 services/ 📜 Service Interfaces (Defining business logic contracts)
│   │   │   │   │   ├── 📄 QuestionService.java
│   │   │   │   │   ├── 📄 QuizService.java
│   │   │   │   │   ├── 📄 UserService.java
│   │   │   │   ├── 📁 services/impl/ 📜 Service Implementations (Concrete business logic implementations)
│   │   │   │   │   ├── 📄 QuestionServiceImpl.java
│   │   │   │   │   ├── 📄 QuizServiceImpl.java
│   │   │   │   │   ├── 📄 UserServiceImpl.java
│   │   │   │   ├── 📁 ui/ 📜 User Interface (Console-based interaction)
│   │   │   │   │   ├── 📄 ConsoleUI.java
│   │   │   │   ├── 📄 Main.java 📜 Main Application Class (Entry point for execution)
│   │   ├── 📁 resources/
│   │   │   ├── 📄 logback.xml 📜 Logback Configuration File (Logging settings)
│   ├── 📁 test/
│   │   ├── 📁 java/
│   │   │   ├── 📁 com/onlinequiz/
│   │   │   │   ├── 📁 dao/impl/ 📜 DAO Implementation Tests (Testing data access logic)
│   │   │   │   │   ├── 📄 QuestionDAOImplTest.java
│   │   │   │   │   ├── 📄 QuizDAOImplTest.java
│   │   │   │   │   ├── 📄 UserDAOImplTest.java
│   │   │   │   ├── 📁 services/impl/ 📜 Service Implementation Tests (Testing business logic)
│   │   │   │   │   ├── 📄 QuestionServiceImplTest.java
│   │   │   │   │   ├── 📄 QuizServiceImplTest.java
│   │   │   │   │   ├── 📄 UserServiceImplTest.java
├── 📁 .github/
│   └── 📁 workflows/ 📜 GitHub Actions Workflows (CI/CD configuration)
│       └── 📄 maven.yml
├── 📄 pom.xml 📜 Maven Project Configuration File (Dependencies, build settings)
├── 📄 LICENSE 📜 License Information (MIT License)
└── 📄 README.md 📜 This File (Project documentation)
```




*   **Configuration Layer:** (config/) Configures the Spring beans and dependency injection. **This is new with the Spring Core migration.**
    
*   **DAO Layer:** (dao/, dao/impl/) Provides interfaces and implementations for data access operations. Data is currently stored in-memory using HashMaps. In a real-world application, this would be replaced with a database integration.
    
*   **Exception Layer:** (exception/) Defines custom exception classes for application-specific error handling.
    
*   **Model Layer:** (models/) Contains the data model classes (User, Quiz, Question).
    
*   **Service Layer:** (services/, services/impl/) Defines the business logic, orchestrating data access and enforcing business rules.
    
*   **UI Layer:** (ui/) Contains the ConsoleUI class, managing the command-line interface.
    
*   **Main Class:** (Main.java) The application's entry point.
    
*   **Resources:** (logback.xml) Configures the application's logging behavior.
    
*   **Tests:** (src/test/java) Contains JUnit tests for DAO and service implementations.

## Testing

To execute the unit tests and verify the functionality of the application:

```bash
mvn test

```
# Contributing  
We welcome contributions to enhance and improve this project!

Fork the repository.

Create a new branch for your feature or bug fix.

Implement your changes and write appropriate unit tests.

Submit a pull request with a clear description of your changes.


## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
```
