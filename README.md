
# MMBAO - E-commerce Management System

MMBAO is a cross-platform e-commerce management system designed to streamline product and sales management processes. Developed using JavaScript and Spring Boot, it features robust modules and scalable infrastructure.

## Features

### 1. **Product Management**
- Developed full CRUD (Create, Read, Update, Delete) functionality for product and user data.
- Enabled parent-child menu relationships, fuzzy search, and multi-table join queries.
- Developed functionality for product price adjustments, including submission and approval workflows.
  
### 2. **User Login System**
- Built a role-based authentication system with different permissions and page access for various user roles.
- Used Redis for session management and implemented cookie-based "Remember Me" functionality.
- Reduced login wait time with multithreading: one thread handles login while another preloads user data into Redis.

### 3. **High-Performance User Authentication**
- Built a secure, role-based login system leveraging **Redis** for session management.
- Reduced login wait time by 33% through multithreading and parallel data retrieval.

### 4. **Excel-Based Data Features**
- Enabled efficient large-scale inventory updates by uploading and downloading via Excel sheets.

### 5. **Automated Notification**
- Configured email notifications using JavaMailSender to notify approvers of pending price adjustment requests.

### 6. **High-Performance System**
- Implemented **Nginx** for load balancing and **Spring-Cache** for optimized caching.
- Used **Sharding-JDBC** for MySQL replication, boosting system scalability by 48% and ensuring stability during peak traffic.

### 7. **Database Design and Integration**
- Designed a scalable database schema to manage products, categories, brands, and suppliers, ensuring atomicity and relational integrity.
- Used timestamp-based IDs and optimized unique identifiers with snowflake-like algorithms.
  
### 8. **Front-End and Back-End Integration**
- Connected a Vue.js frontend with a Spring Boot backend, ensuring compatibility for local and network access.

## Technologies Used
- **Frontend**: JavaScript
- **Backend**: Spring Boot
- **Database**: MySQL, Redis
- **Tools**: JavaMailSender, Nginx, Sharding-JDBC, Postman

## How to Run the Project
1. Clone the repository:
   ```bash
   git clone https://github.com/UchihaSusie/mmbao.git
   ```
2. Navigate to the project directory:
   ```bash
   cd mmbao
   ```
3. Install dependencies and build the project:
   ```bash
   mvn install
   ```
4. Run the application:
   ```bash
   java -jar target/mmbao.jar
   ```
5. Access the application in your browser at:
   ```
   http://localhost:8080
   ```

## Contribution Guidelines
Contributions are welcome! Feel free to fork the repository and submit a pull request.

