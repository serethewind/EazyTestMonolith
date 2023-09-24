# EazyTest Online Assessment Solutions

Introducing EazyTest: Your Solution for Tech Talent Recruitment

In the tech world, finding the right talent is tough. Enter EazyTest, the modern online assessment tool that makes talent hunting effortless.

EazyTest streamlines your recruitment process with a wide range of expertly crafted questions and efficient evaluations. Focus on what matters most—identifying top tech talent to drive innovation and success.

EazyTest is where efficiency, accuracy, and ease meet to simplify talent acquisition.

## Prerequisites

Before you begin, ensure you have the following prerequisites installed:

- Java 17
- Spring 6
- Spring Boot 3
- Spring Security
- MySQL database
- Gradle Groovy

## Business Documentaion

The below link is a power point slide that documents the problem statement, the value proposition and the identified users : https://docs.google.com/presentation/d/1TdVXoysbbSQgSfHAMzQYCcGfDc2XQt8rcjY6s8iqKeE/edit?usp=sharing

Also contained in this repo is a Project Description directory which contains the entity relationship diagram.

## Table of Contents

- [Dependencies](#dependencies)
- [Installation](#installation)
- [Database Configuration](#database-configuration)
- [JSON Web Token Configuration (JWT)](#json-web-token-configuration-jwt)
- [Mail Configuration](#mail-configuration)
- [Usage/Examples](#usageexamples)

## Dependencies

The following dependencies are required:

- Spring Web
- Spring Data Jpa
- Project Lombok
- Spring Validation
- Spring JDBC
- Spring Doc & Open API
- Java Mail Service
- Spring Security
- JSON Web Token (JWT)
- MySQL
- Spring Actuator

## Installation

1. Clone the repository to your local machine: `git clone https://github.com/serethewind/EazyTestMonolith`
2. Build the project.
3. Configure the application.

The configuration for this API is stored in the `application.properties` file. To configure the API or make changes to its behavior, you can edit this file.

## Database Configuration

```properties
spring.datasource.url=your-database-url
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

## JSON Web Token Configuration (JWT)

```properties
application.security.jwt.secret-key=
application.security.jwt.expiration=
```

## Mail Configuration

```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email
spring.mail.password=google-given-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.ssl=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

## Usage/Examples

This project comes with a comprehensive Postman API documentation which can be accessed [here](https://documenter.getpostman.com/view/16267047/2s9YC7TXgK).


