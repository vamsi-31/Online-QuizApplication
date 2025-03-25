# Online Quiz Application


[![License](https://img.shields.io/github/license/vamsi-31/Online-QuizApplication)](https://github.com/vamsi-31/Online-QuizApplication/blob/main/LICENSE)
[![GitHub Issues](https://img.shields.io/github/issues/vamsi-31/Online-QuizApplication)](https://github.com/vamsi-31/Online-QuizApplication/issues)
[![GitHub Pull Requests](https://img.shields.io/github/pulls/vamsi-31/Online-QuizApplication)](https://github.com/vamsi-31/Online-QuizApplication/pulls)

This project is a robust and user-friendly Online Quiz Management application designed to facilitate efficient quiz creation, administration, and participation. It provides a streamlined interface for administrators to manage quizzes and questions, while enabling users to easily access and complete quizzes.

## Key Features

*   **Comprehensive User Management:**
    *   Secure user registration with role-based access control (ADMIN/USER).
    *   Robust authentication mechanism to protect user accounts.
*   **Powerful Quiz Management (Admin):**
    *   Intuitive interface for creating quizzes with titles, descriptions, and sets of questions.
    *   Automatic generation of unique, secure access codes for each quiz.
    *   Comprehensive quiz listing and search functionalities.
    *   Quiz locking capability to prevent unauthorized modifications after deployment.
*   **Flexible Question Management (Admin):**
    *   Easy-to-use question creation interface with support for various question types (e.g., multiple-choice, true/false).
    *   Detailed question configuration options, including difficulty level, topic tagging, and point value assignment.
    *   Centralized question repository for efficient reuse and management.
*   **Seamless Quiz Taking (User):**
    *   Simple quiz access using the unique access code.
    *   Clear presentation of quiz questions and answer options.
    *   Real-time score calculation and feedback upon quiz completion.
*   **Intuitive Console UI:**
    *   Well-structured command-line interface for effortless navigation and interaction.
    *   Clear and concise prompts for user input and guidance.
*   **Robust Logging and Error Handling:**
    *   SLF4j and Logback integration for comprehensive logging of application events.
    *   Custom exception handling for graceful error recovery and informative error messages.
*   **Testability and Maintainability:**
    *   Extensive unit tests using JUnit and Mockito for ensuring code quality and reliability.
    *   Well-defined architecture and coding conventions for easy maintenance and extension.

## Technologies Used

*   **Java:** The core programming language for building the application's logic and functionality.
*   **Maven:** For streamlined project management, dependency resolution, and build automation.
*   **SLF4j & Logback:** For flexible and efficient logging, enabling detailed monitoring and debugging.
*   **JUnit & Mockito:** For writing and executing comprehensive unit tests, ensuring code reliability and correctness.
*   **GitHub Actions:** Used for Continuous Integration (CI) to automate the build and testing process.
*   **Codecov:** Used for measuring and reporting code coverage to ensure comprehensive testing.

## Getting Started

Follow these instructions to set up and run the Online Quiz Application on your local development environment.

### Prerequisites

*   **Java Development Kit (JDK) 17 or higher:** Ensure that you have JDK 17 or a more recent version installed and properly configured. You can download the latest JDK from [Oracle](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html) or [OpenJDK](https://openjdk.java.net/projects/jdk/17/).  Set the `JAVA_HOME` environment variable to your JDK installation directory.
*   **Maven:** Install Maven and configure it correctly on your system. You can download the latest version of Maven from [Apache Maven](https://maven.apache.org/download.cgi). Add the Maven `bin` directory to your system's PATH environment variable.
*   **Git:** Install Git to clone the project. Get it from [Git SCM](https://git-scm.com/downloads).

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

    This command will download all necessary dependencies, compile the source code, and package the application into an executable JAR file.  The `maven-shade-plugin` is configured to create a self-contained "uber JAR" in the `target/` directory.

### Running the Application

1.  **Execute the JAR file:**

    ```bash
    java -jar target/OnlineQuizManagement-1.0-SNAPSHOT.jar
    ```

    This command will start the application, and the console UI will appear, ready for interaction.

### Usage

Once the application is running, you can interact with it via the console menu.

1.  **Login/Register:** If you're a new user, register an account with a unique username, strong password, and the appropriate role (ADMIN or USER). Existing users can log in with their credentials.

2.  **Admin Menu:** If you log in as an ADMIN, you can:
    *   Create, view, and manage quizzes and questions.
    *   Lock quizzes to prevent modifications.
    *   Perform other administrative tasks.

3.  **User Menu:** If you log in as a USER, you can:
    *   Take available quizzes using their access codes.
    *   View your quiz results (feature enhancement in progress).

Carefully follow the prompts displayed on the screen to navigate the menus and perform desired actions.

## Project Architecture

The project is structured using a layered architecture, promoting separation of concerns and enhancing maintainability:
```🌳 OnlineQuizApplication/
├── 📁 src/
│   ├── 📁 main/
│   │   ├── 📁 java/
│   │   │   ├── 📁 com/onlinequiz/
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




*   **DAO Layer:**  Provides abstract interfaces (`dao/`) and concrete implementations (`dao/impl/`) for data access operations. In this implementation, data is stored in memory using `HashMap`s for simplicity. In a real-world scenario, this layer would interact with a persistent data store like a database.
*   **Exception Layer:** Defines custom exception classes (`exception/`) to handle specific error conditions within the application.
*   **Model Layer:**  Contains the data model classes (`models/`), such as `User`, `Quiz`, and `Question`, representing the core entities of the application.
*   **Service Layer:**  Defines interfaces (`services/`) and implementations (`services/impl/`) for the business logic of the application. This layer orchestrates data access through the DAO layer and enforces business rules.
*   **UI Layer:** Contains the `ConsoleUI` class (`ui/`), which manages the command-line interface for user interaction.
*   **Main Class:**  The `Main.java` file contains the `main` method, which serves as the entry point for the application.
*   **Resources:** The `logback.xml` file configures the logging behavior of the application using Logback.
*   **Tests:** The `src/test/java` directory contains JUnit tests for the DAO and service implementations, ensuring code quality and reliability.
*   **GitHub Actions:** The `.github/workflows/maven.yml` file configures a CI workflow to automatically build and test the project on every push.

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
