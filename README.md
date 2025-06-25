# Online Quiz Application (Spring Boot Edition)

[![License](https://img.shields.io/github/license/vamsi-31/Online-QuizApplication)](https://github.com/vamsi-31/Online-QuizApplication/blob/main/LICENSE)
[![GitHub Issues](https://img.shields.io/github/issues/vamsi-31/Online-QuizApplication)](https://github.com/vamsi-31/Online-QuizApplication/issues)
[![GitHub Pull Requests](https://github.com/vamsi-31/Online-QuizApplication)](https://github.com/vamsi-31/Online-QuizApplication/pulls)

## Overview

This Online Quiz Application is a comprehensive system designed for creating, managing, and taking quizzes. It offers role-based access control (Admin/User), ensuring secure and efficient quiz administration and participation.

**This version has been migrated to Spring Boot**, leveraging its auto-configuration, dependency management via starters, and embedded server capabilities. It now offers both a **Command-Line Interface (CLI)** for interactive use and a **RESTful API** for programmatic access and potential integration with web frontends.

The application maintains a layered architecture and robust error handling, promoting testability, maintainability, and future extensibility.

## Key Features

*   **Database Persistence:**
    *   Data is persisted in a relational database (MySQL) using Spring Data JPA.
    *   Automatic schema generation/update via Hibernate (`ddl-auto`).
*   **Dual Interface:**
    *   **Console UI:** Interactive command-line interface for direct user interaction.
    *   **REST API:** Provides a full suite of endpoints for managing users, questions, and quizzes.
*   **User Management:**
    *   Secure registration and authentication via REST API.
    *   Role-based access control (ADMIN/USER).
    *   Full CRUD operations for users via REST API.
*   **Quiz Management (Admin):**
    *   Full CRUD operations for quizzes via REST API.
    *   Automatic generation of unique access codes.
    *   Quiz locking to prevent further modification.
*   **Question Management (Admin):**
    *   Full CRUD operations for multiple-choice questions via REST API and Console UI.
    *   Configuration options for difficulty level, topic tagging, and point values.
    *   Questions are stored in a centralized database repository.
*   **Quiz Taking (User):**
    *   Simple quiz access via unique access codes.
    *   Real-time score calculation via both Console UI and REST API.
*   **Spring Boot Integration:**
    *   Simplified setup with `spring-boot-starter-web`, `spring-boot-starter-data-jpa`, and `spring-boot-starter-test`.
    *   Auto-configuration and component scanning.
    *   Executable JAR packaging via `spring-boot-maven-plugin`.
*   **Logging and Error Handling:**
    *   Integration with SLF4j and Logback.
    *   Custom exception handling and appropriate HTTP status codes in API responses.
*   **Testing and Code Quality:**
    *   Extensive unit tests using JUnit and Mockito.
    *   GitHub Actions for continuous integration (CI).
    *   Codecov integration for code coverage.

## Technologies Used

*   **Java 17+:** Core programming language.
*   **Spring Boot 3.3+:** Framework for building robust Java applications quickly.
*   **Spring Web (MVC):** For building RESTful APIs.
*   **Maven:** Project management, dependency resolution, and build automation.
*   **Lombok:** Reduces boilerplate code for model classes.
*   **SLF4j & Logback:** Flexible and efficient logging.
*   **JUnit 5 & Mockito:** Unit testing.
*   **GitHub Actions:** Continuous Integration (CI).
*   **Codecov:** Code coverage reporting.

## Getting Started

### Prerequisites

*   **Java Development Kit (JDK) 17 or higher:** Download from [Oracle](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html) or [OpenJDK](https://openjdk.java.net/projects/jdk/17/). Set `JAVA_HOME`.
*   **Maven:** Download from [Apache Maven](https://maven.apache.org/download.cgi). Add the `bin` directory to your system's PATH.
*   **Git:** Download from [Git SCM](https://git-scm.com/downloads).
*   **MySQL Server:** A running instance of a MySQL database.

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
    This command compiles the code, runs tests, and packages the application into an executable JAR file (e.g., `target/OnlineQuizManagement-1.0-SNAPSHOT.jar`) using the `spring-boot-maven-plugin`.

### Running the Application

You can run the application in two modes:

1.  **API Mode (Default):** Starts the web server to serve REST API requests.
    ```bash
    java -jar target/OnlineQuizManagement-1.0-SNAPSHOT.jar
    ```
    The application will start, and the API will be available (typically on `http://localhost:8080`). Check the console output for the exact port.

2.  **Console Mode:** Starts the interactive command-line interface.
    ```bash
    java -jar target/OnlineQuizManagement-1.0-SNAPSHOT.jar console
    ```
    The console menu will appear, allowing you to interact with the application as before.

### Usage

**1. Console Interface:**

*   Run the application with the `console` argument as shown above.
*   **Login/Register:** Register as a new user (ADMIN or USER) or login.
*   **Admin Menu:** Create/view quizzes, create/view questions, lock quizzes.
*   **User Menu:** Take quizzes using access codes.
*   Follow the on-screen prompts.

**2. REST API:**

*   Run the application without arguments (API mode).
*   Use an API client (like Postman, Insomnia, or `curl`) to interact with the endpoints.
*   **Base URL:** Typically `http://localhost:8080/api` (check console output for port).
*   **Available Endpoints (Examples):**
    *   `POST /api/users/register` - Register a new user.
    *   `POST /api/users/login` - Authenticate a user.
    *   `GET /api/users` - Get all users (likely requires ADMIN role).
    *   `GET /api/users/{id}` - Get user by ID.
    *   `POST /api/questions` - Create a new question (requires ADMIN role).
    *   `GET /api/questions` - Get all questions.
    *   `GET /api/questions/{id}` - Get question by ID.
    *   `PUT /api/questions/{id}` - Update a question.
    *   `DELETE /api/questions/{id}` - Delete a question.
    *   *(Quiz endpoints can be added similarly)*

    Refer to the `QuestionController.java` and `UserController.java` files for specific request/response formats and supported methods.

## Project Architecture

The project follows a layered architecture, enhanced by Spring Boot:

```🌳 OnlineQuizApplication/
├── 📁 src/
│   ├── 📁 main/
│   │   ├── 📁 java/
│   │   │   ├── 📁 com/onlinequiz/
│   │   │   │   ├── 📄 Main.java 📜 Spring Boot Application Entry Point (@SpringBootApplication)
│   │   │   │   ├── 📁 config/ 📜 Java-based Spring Configuration (@Configuration)
│   │   │   │   │   ├── 📄 AppConfig.java
│   │   │   │   ├── 📁 constants/ 📜 Application Constants
│   │   │   │   │   ├── 📄 Constants.java
│   │   │   │   ├── 📁 controllers/ 📜 REST API Controllers (@RestController)
│   │   │   │   │   ├── 📄 QuestionController.java
│   │   │   │   │   ├── 📄 UserController.java
│   │   │   │   ├── 📁 dao/ 📜 Data Access Object Interfaces
│   │   │   │   │   ├── 📄 QuestionDAO.java
│   │   │   │   │   ├── 📄 QuizDAO.java
│   │   │   │   │   ├── 📄 UserDAO.java
│   │   │   │   ├── 📁 dao/impl/ 📜 DAO Implementations (@Repository)
│   │   │   │   │   ├── 📄 QuestionDAOImpl.java
│   │   │   │   │   ├── 📄 QuizDAOImpl.java
│   │   │   │   │   ├── 📄 UserDAOImpl.java
│   │   │   │   ├── 📁 exception/ 📜 Custom Exception Classes
│   │   │   │   │   ├── 📄 ... (Specific Exceptions)
│   │   │   │   ├── 📁 models/ 📜 Data Model Classes (Entities) - Using Lombok (@Data)
│   │   │   │   │   ├── 📄 Question.java
│   │   │   │   │   ├── 📄 Quiz.java
│   │   │   │   │   ├── 📄 User.java
│   │   │   │   ├── 📁 services/ 📜 Service Interfaces
│   │   │   │   │   ├── 📄 QuestionService.java
│   │   │   │   │   ├── 📄 QuizService.java
│   │   │   │   │   ├── 📄 UserService.java
│   │   │   │   ├── 📁 services/impl/ 📜 Service Implementations (@Service)
│   │   │   │   │   ├── 📄 QuestionServiceImpl.java
│   │   │   │   │   ├── 📄 QuizServiceImpl.java
│   │   │   │   │   ├── 📄 UserServiceImpl.java
│   │   │   │   ├── 📁 ui/ 📜 Console User Interface (@Component)
│   │   │   │   │   ├── 📄 ConsoleUI.java
│   │   ├── 📁 resources/
│   │   │   ├── 📄 application.properties 📜 Spring Boot Configuration (Optional)
│   │   │   ├── 📄 logback.xml 📜 Logback Configuration
│   ├── 📁 test/
│   │   ├── 📁 java/ 📜 Unit Tests (DAO, Services, etc.) using JUnit & Mockito
│   │   │   ├── 📁 com/onlinequiz/
│   │   │   │   ├── 📁 dao/impl/
│   │   │   │   ├── 📁 services/impl/
│   │   │   │   ├── ...
├── 📁 .github/
│   └── 📁 workflows/ 📜 GitHub Actions CI Workflow
│       └── 📄 maven.yml
├── 📄 pom.xml 📜 Maven Project Configuration (Spring Boot Parent, Starters)
├── 📄 LICENSE 📜 MIT License
└── 📄 README.md 📜 This File
```
*   **Main Class:** (`Main.java`) The application's entry point, annotated with `@SpringBootApplication`. Configures a `CommandLineRunner` to conditionally start the `ConsoleUI`.
*   **Controller Layer:** (`controllers/`) Contains classes annotated with `@RestController` that handle incoming HTTP requests for the REST API (e.g., `UserController`, `QuestionController`). **This layer was added for the Spring Boot web capabilities.**
*   **Configuration Layer:** (`config/`) Contains classes annotated with `@Configuration` that explicitly define Spring beans using `@Bean` methods (e.g., `AppConfig`).
*   **Constants Layer:** (`constants/`) Defines static final constants used throughout the application (e.g., error messages, roles).
*   **DAO Layer:** (`dao/`, `dao/impl/`) Provides interfaces and their implementations (annotated with `@Repository`) for data access. Data is currently stored in-memory (`HashMap`). This layer interacts with the data source.
*   **Exception Layer:** (`exception/`) Defines custom runtime exception classes for handling specific application errors gracefully (e.g., `UserException`, `QuizException`).
*   **Repository Layer:** (`repositories/`) Contains Spring Data JPA interfaces that extend `JpaRepository`. Spring automatically provides the implementation for standard CRUD operations, significantly simplifying data access code.
*   **Model Layer:** (`models/`) Contains the data model classes (`User`, `Quiz`, `Question`). These are JPA **entities** annotated with `@Entity` to map them to database tables.
*   **Service Layer:** (`services/`, `services/impl/`) Contains interfaces and their implementations (annotated with `@Service`) defining the core business logic, orchestrating calls to the DAO layer and enforcing rules.
*   **UI Layer:** (`ui/`) Contains the `ConsoleUI` class (annotated with `@Component`), responsible for managing the command-line user interface interactions.
*   **Resources:** (`src/main/resources/`) Contains external configuration files like `logback.xml` (for logging) and potentially `application.properties` (for Spring Boot settings).
*   **Tests:** (`src/test/java/`) Contains unit tests (using JUnit 5 and Mockito, often via `spring-boot-starter-test`) for verifying the functionality of DAO, Service, and potentially Controller layers.

*   **Configuration Layer:** (config/) Configures the Spring beans and dependency injection. **This is new with the Spring Boot migration.**
    
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
