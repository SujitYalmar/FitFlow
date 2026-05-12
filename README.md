# FitFlow

FitFlow is a Spring Boot fitness web app with Thymeleaf templates, Spring Security,
JPA, and MySQL.

## Project Structure

```text
FitFlow-1/
├── src/
│   ├── main/
│   │   ├── java/com/becoder/
│   │   │   ├── config/        # Security and custom user details
│   │   │   ├── controller/    # Web routes and page handlers
│   │   │   ├── entity/        # JPA entities
│   │   │   ├── repository/    # Database repositories
│   │   │   ├── service/       # Business logic
│   │   │   └── Application.java
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── css/       # Site styles
│   │       │   ├── images/    # App images and workout media
│   │       │   ├── js/        # Browser scripts
│   │       │   ├── uploads/   # User-uploaded profile images
│   │       │   └── videos/
│   │       ├── templates/     # Thymeleaf HTML pages
│   │       └── application.properties
│   └── test/java/             # Spring Boot tests
├── .mvn/wrapper/              # Maven wrapper files only
├── mvnw
├── mvnw.cmd
└── pom.xml
```

## Run Locally

```bash
./mvnw spring-boot:run
```

On Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

The app runs on the port configured in `application.properties`.
