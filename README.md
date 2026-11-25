# Expense Tracker API

A RESTful backend application built with Java and Spring Boot to manage personal income and expense transactions.

## Features

- Create, retrieve, update and delete transactions.
- Track income and expenses separately.
- Associate transactions with users.
- Calculate total income and expenses.
- Calculate current financial balance.
- Retrieve total transaction count.
- Validate transaction input.
- Store data persistently using MySQL.

## Tech Stack

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- REST APIs
- Postman

## Architecture

The application follows a layered architecture:

- **Controller:** Handles HTTP requests and responses.
- **Service:** Contains business logic.
- **Repository:** Handles database operations.
- **Entity:** Represents database tables.
- **DTO:** Controls API request and response structures.

## API Endpoints

### Transactions

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/transactions?userId=1` | Create transaction |
| GET | `/api/transactions?userId=1` | Get all transactions |
| GET | `/api/transactions/{id}?userId=1` | Get transaction by ID |
| PUT | `/api/transactions/{id}?userId=1` | Update transaction |
| DELETE | `/api/transactions/{id}?userId=1` | Delete transaction |

### Financial Summary

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/transactions/summary?userId=1` | Get financial summary |

### Example Response

```json
{
  "totalIncome": 50000.00,
  "totalExpenses": 18000.00,
  "balance": 32000.00,
  "totalTransactions": 4
}
```

## Setup Instructions

1. Clone the repository.
2. Open the project in IntelliJ IDEA.
3. Create a MySQL database named `expense_tracker`.
4. Configure the required environment variables.
5. Run the Spring Boot application.

### Environment Variables

```properties
DB_URL=jdbc:mysql://localhost:3306/expense_tracker
DB_USERNAME=your_username
DB_PASSWORD=your_password
```

## Future Improvements

- JWT authentication and authorization.
- Date-range filtering.
- Pagination and sorting.
- Global exception handling.
- Unit and integration testing.
- Swagger/OpenAPI documentation.

## Author

Anand