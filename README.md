
# Product Sales Management API

A Spring Boot application that exposes a REST API for managing Products and Sales with revenue calculation, security, and PDF export features.

------------------------------------------------------------
FEATURES
------------------------------------------------------------
- Product Management
  - Add, update, delete products (ADMIN only)
  - Pagination support for product listing
- Sales Management
  - Add sales linked to products
  - Automatic revenue calculation
- Revenue APIs
  - Get total revenue
  - Get revenue by product
- Security
  - Spring Security with role-based access (USER, ADMIN)
  - Only allowed IP addresses can access APIs ( The IP addresses are configure in the application.properties )
- PDF Export
  - Download product list with revenue as PDF
- Logging
  - Logs all user activity for auditing

------------------------------------------------------------
TECH STACK
------------------------------------------------------------
- Java 17
- Spring Boot 3
- Spring Data JPA (Hibernate)
- MySQL
- Spring Security
- Swagger / OpenAPI

------------------------------------------------------------
SETUP INSTRUCTIONS
------------------------------------------------------------
1. Clone the Repository

2. Configure MySQL
   Run MySQL locally (example with Docker):
   docker run --name mysql-local \
  -e MYSQL_ROOT_PASSWORD=root \
  -e MYSQL_DATABASE=mydb \
  -e MYSQL_USER=myuser \
  -e MYSQL_PASSWORD=mypassword \
  -p 3306:3306 \
  -d mysql:8.0

3. Run the Application
   mvn spring-boot:run

------------------------------------------------------------
SECURITY
------------------------------------------------------------
- Roles:
  USER → Can access GET APIs
  ADMIN → Can access all APIs (POST, PUT, DELETE)
- Hardcoded users (in-memory):
  user / userpass
  admin / adminpass
- IP Whitelisting: Only allowed IPs can access (configured in security filter).

------------------------------------------------------------
SWAGGER DOCUMENTATION
------------------------------------------------------------
Swagger UI is available at:
http://localhost:8080/swagger-ui.html
