# ABC Restaurant Management System

ABC Restaurant is a popular restaurant chain located in many cities across Sri Lanka. To expand its operations and leverage technological advancements, the board of directors has decided to develop an interactive web-based application for the restaurant chain. This application will enable customers to make online reservations, search for services and facilities, and submit queries. It will also provide restaurant staff and administrators with tools to manage reservations and respond to customer queries.


## Project Overview

The ABC Restaurant Management System is a database-driven web application that serves three types of users:
1. **Admin**: Can manage the entire system, including adding, updating, and deleting restaurants, users, and handling overall restaurant configurations.
2. **Restaurant Staff**: Can view and manage reservations, respond to customer queries, and process payments.
3. **Customers**: Can view restaurant services, make reservations, search for services, and submit queries to the restaurant administration.

### Functional Requirements:
1. **Online Reservation**: Customers can make reservations for dine-in or delivery services.
2. **Search Services**: Customers can search for hospitality services and available facilities.
3. **Query Submission**: Customers can submit queries, and staff can respond accordingly.
4. **Reports Generation**: Generate various reports such as reservation statistics, payment records, and query response times.

## Features
- Online reservation system for dining and delivery services.
- Advanced search for hospitality services and facilities.
- Query submission and response management.
- User roles with differential access rights (Admin, Staff, Customer).
- Interactive and user-friendly UI/UX design.
- Clear implementation of the business flow.
- Report generation for business insights.

## Technology Stack
- **Backend**: Java (Servlets), MySQL
- **Frontend**: JSP, HTML5, Boostrap, JavaScript
- **Web Server**: Apache Tomcat
- **Database**: MySQL
- **IDE**: Eclipse

## Prerequisites
1. Java 11 or higher
2. MySQL Server 5.7 or higher
3. Apache Tomcat 9.x
4. Eclipse IDE with Maven Plugin
5. MySQL Workbench (optional, for database management)

## Database Setup

### Step 1: Import the Database
1. Open MySQL Workbench.
2. Create a new schema named `abc_restaurant`.
3. Import the provided SQL file (`abc_restaurant.sql`) into the newly created schema.
   - Go to `File > Open SQL Script`.
   - Select the `abc_restaurant.sql` file and execute it.
4. Verify that all tables have been created successfully, including `users`, `reservations`, `services`, `queries`, and `roles`.

### Step 2: Database Connection Configuration
Update the database connection details in the `DBConnection.java` file:

```java
public class DBConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/abc_restaurant";
    private static final String USER = "root";
    private static final String PASSWORD = "your_password";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
