# ChallengeCoreBanking

A Spring Boot-based core banking system that provides RESTful APIs for basic banking operations including deposits, withdrawals, and transfers.

## 🏦 Overview

ChallengeCoreBanking is a lightweight banking application built with Spring Boot that demonstrates core banking operations through a clean REST API. The system manages accounts and supports three main operations: deposits, withdrawals, and transfers between accounts.

## ✨ Features

- **Account Management**: Create, retrieve, and manage bank accounts
- **Deposit Operations**: Add funds to existing or new accounts
- **Withdrawal Operations**: Remove funds from existing accounts
- **Transfer Operations**: Move funds between accounts
- **RESTful API**: Clean HTTP endpoints for all operations
- **In-Memory Storage**: Fast, temporary storage using HashMap
- **H2 Database Console**: Web-based database management interface
- **Comprehensive Testing**: Full JUnit test suite with examples

## 🚀 Quick Start

### Prerequisites

- Java 8 or higher
- Maven 3.6 or higher (optional, for building)

### Running the Application

1. **Clone or download the project**
   ```bash
   git clone <repository-url>
   cd challengeCoreBanking
   ```

2. **Run with Java**
   ```bash
   java -jar target/challengeCoreBanking-1.0-SNAPSHOT.jar
   ```

3. **Run with Maven**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**
   - API Base URL: `http://localhost:8000`
   - H2 Console: `http://localhost:8000/h2-console`

## 📚 API Documentation

### Base URL
```
http://localhost:8000
```

### Endpoints

#### 1. Reset System
Reset all accounts and clear the system state.

```http
POST /reset
```

**Response:**
```json
"OK"
```

#### 2. Get Account Balance
Retrieve the balance of a specific account.

```http
GET /balance?account_id={account_id}
```

**Parameters:**
- `account_id` (query): The account identifier

**Response:**
- **200 OK**: Account balance as string
- **404 Not Found**: "0" if account doesn't exist

**Example:**
```bash
curl "http://localhost:8000/balance?account_id=ACC001"
```

#### 3. Perform Banking Operation
Execute deposits, withdrawals, or transfers.

```http
POST /event
Content-Type: application/json
```

**Request Body:**
```json
{
  "type": "deposit|withdraw|transfer",
  "amount": "100",
  "destination": "account_id",
  "origin": "account_id"
}
```

**Response:**
- **201 Created**: Operation result with account details
- **404 Not Found**: "0" if operation fails

### Operation Types

#### Deposit
Add funds to an account (creates account if it doesn't exist).

```json
{
  "type": "deposit",
  "amount": "500",
  "destination": "ACC001"
}
```

**Response:**
```json
{"destination": {"id": "ACC001", "balance": 500}}
```

#### Withdraw
Remove funds from an existing account.

```json
{
  "type": "withdraw",
  "amount": "200",
  "origin": "ACC001"
}
```

**Response:**
```json
{"origin": {"id": "ACC001", "balance": 300}}
```

#### Transfer
Move funds from one account to another.

```json
{
  "type": "transfer",
  "amount": "100",
  "origin": "ACC001",
  "destination": "ACC002"
}
```

**Response:**
```json
{
  "origin": {"id": "ACC001", "balance": 200},
  "destination": {"id": "ACC002", "balance": 100}
}
```

## 🧪 Testing

The project includes comprehensive JUnit tests demonstrating various testing scenarios:

### Running Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=AccountTest

# Run test suite
mvn test -Dtest=AllTestsSuite
```

### Test Structure

- **Model Tests**: `AccountTest.java`, `OperationTest.java`
- **Service Tests**: `ChallengeCoreBankingFacadeTest.java`
- **Utility Tests**: `ChallengeCoreUtilsTest.java`
- **Integration Tests**: `BankingSystemIntegrationTest.java`
- **Advanced Examples**: `AdvancedJUnitTestExamples.java`

For detailed testing documentation, see [TEST_README.md](TEST_README.md).

## 🏗️ Project Structure

```
challengeCoreBanking/
├── src/
│   ├── main/
│   │   ├── java/org/orelio/
│   │   │   ├── Application.java                 # Spring Boot main class
│   │   │   ├── controllers/
│   │   │   │   └── GreetingsController.java     # REST API endpoints
│   │   │   ├── facade/
│   │   │   │   └── ChallengeCoreBankingFacade.java # Business logic
│   │   │   ├── model/
│   │   │   │   ├── Account.java                # Account entity
│   │   │   │   ├── Operation.java              # Operation entity
│   │   │   │   └── Constants.java              # Application constants
│   │   │   └── util/
│   │   │       └── ChallengeCoreUtils.java     # Utility functions
│   │   └── resources/
│   │       └── application.properties           # Configuration
│   └── test/
│       └── java/org/orelio/
│           ├── model/                           # Model tests
│           ├── facade/                          # Service tests
│           ├── util/                           # Utility tests
│           ├── integration/                    # Integration tests
│           ├── advanced/                       # Advanced test examples
│           └── suite/                          # Test suites
├── pom.xml                                     # Maven configuration
├── TEST_README.md                              # Testing documentation
├── run_tests.sh                               # Test runner script
└── README.md                                  # This file
```

## 🔧 Configuration

### Application Properties

```properties
# Server configuration
server.address=0.0.0.0
server.port=8000

# H2 Database console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

### Maven Dependencies

Key dependencies include:
- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- H2 Database
- JUnit 4
- Jackson (JSON processing)

## 🎯 Usage Examples

### Complete Banking Workflow

```bash
# 1. Reset the system
curl -X POST http://localhost:8000/reset

# 2. Create account with initial deposit
curl -X POST http://localhost:8000/event \
  -H "Content-Type: application/json" \
  -d '{"type": "deposit", "amount": "1000", "destination": "ACC001"}'

# 3. Check account balance
curl "http://localhost:8000/balance?account_id=ACC001"

# 4. Make a withdrawal
curl -X POST http://localhost:8000/event \
  -H "Content-Type: application/json" \
  -d '{"type": "withdraw", "amount": "200", "origin": "ACC001"}'

# 5. Transfer funds to another account
curl -X POST http://localhost:8000/event \
  -H "Content-Type: application/json" \
  -d '{"type": "transfer", "amount": "300", "origin": "ACC001", "destination": "ACC002"}'

# 6. Check both account balances
curl "http://localhost:8000/balance?account_id=ACC001"
curl "http://localhost:8000/balance?account_id=ACC002"
```

## 🛠️ Development

### Building the Project

```bash
# Compile
mvn compile

# Package
mvn package

# Clean and package
mvn clean package
```

### Running in Development Mode

```bash
# With Spring Boot DevTools (auto-reload)
mvn spring-boot:run
```

### Database Console

Access the H2 database console at `http://localhost:8000/h2-console`:
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: (leave empty)

## 🐛 Error Handling

The API returns appropriate HTTP status codes:

- **200 OK**: Successful GET requests
- **201 Created**: Successful POST operations
- **404 Not Found**: Account not found or invalid operations

Error responses return "0" for failed operations.

## 🔒 Security Considerations

⚠️ **Note**: This is a demonstration project and should not be used in production without proper security measures:

- No authentication/authorization
- No input validation
- No rate limiting
- In-memory storage (data lost on restart)
- No encryption

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Add tests for new functionality
4. Ensure all tests pass
5. Submit a pull request

## 📄 License

This project is for educational and demonstration purposes.

## 👨‍💻 Author

**Marcos Orelio**
- Version: 1.0-SNAPSHOT
- Created: September 28, 2025

## 📞 Support

For questions or issues:
1. Check the [TEST_README.md](TEST_README.md) for testing guidance
2. Review the test examples for usage patterns
3. Examine the source code for implementation details

---

**Happy Banking! 🏦**
