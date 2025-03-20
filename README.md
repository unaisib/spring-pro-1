# AI Demo Spring Boot Application

A Spring Boot application demonstrating JWT authentication, user management, and RESTful API design.

## Technologies

- Java 21
- Spring Boot 3.4.3
- Spring Security
- PostgreSQL
- JWT (JSON Web Tokens)
- Maven

## Prerequisites

- JDK 21
- Maven 3.9+
- PostgreSQL 15+
- Docker (optional)

## Getting Started

### Database Setup

1. Create a PostgreSQL database:
```sql
CREATE DATABASE spring-demo;
```

2. Configure database connection in `src/main/resources/application.properties` or use environment variables.

### Building the Application

```bash
# Clone the repository
git clone https://github.com/yourusername/ai-demo.git
cd ai-demo

# Build the project
./mvnw clean install

# Run the application
./mvnw spring-boot:run
```

### Using Docker

```bash
# Build Docker image
docker build -t ai-demo .

# Run the container
docker run -p 8080:8080 ai-demo
```

## API Endpoints

### Authentication

```
POST /api/auth/register
POST /api/auth/authenticate
POST /api/auth/refresh-token
```

#### Register New User
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username": "user", "password": "password"}'
```

#### Authenticate
```bash
curl -X POST http://localhost:8080/api/auth/authenticate \
  -H "Content-Type: application/json" \
  -d '{"username": "user", "password": "password"}'
```

#### Refresh Token
```bash
curl -X POST http://localhost:8080/api/auth/refresh-token \
  -H "Authorization: Bearer <refresh_token>"
```

## Security

- JWT-based authentication
- Password encryption using BCrypt
- Token-based refresh mechanism
- Secure endpoints with role-based access control

## Configuration

Key application properties (`src/main/resources/application.properties`):

```properties
spring.application.name=ai-demo
spring.datasource.url=jdbc:postgresql://localhost:5432/spring-demo
spring.datasource.username=postgres
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
jwt.secret=your_secret_key_here
jwt.expiration=3600000
jwt.refresh-expiration=604800000
```

## Development

### Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── bcast/
│   │           └── ai_demo/
│   │               ├── configs/
│   │               ├── controllers/
│   │               ├── dtos/
│   │               ├── filters/
│   │               ├── models/
│   │               ├── repositories/
│   │               ├── services/
│   │               └── utils/
│   └── resources/
│       └── application.properties
└── test/
    └── java/
```

### Running Tests

```bash
./mvnw test
```

### Code Style

This project follows the Google Java Style Guide. You can format your code using:

```bash
./mvnw spotless:apply
```

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Support

For support, please open an issue in the GitHub repository.
