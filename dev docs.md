# Beeflow Development Documentation

## Project Overview
Beeflow is a workflow management system built with Spring Boot and Activiti. It provides REST APIs for workflow definition management and organization structure management.

## Technical Stack
- Java 17
- Spring Boot 3.1.0
- Activiti 7.1.0.M6
- MyBatis-Plus 3.5.3.1
- H2 Database (Development)
- MySQL (Production)

## Project Structure
```
backend/
├── src/main/java/com/beeflow/workflow/backend/
│   ├── BeeflowApplication.java
│   ├── config/
│   │   └── SecurityConfig.java
│   ├── controller/
│   │   ├── WorkflowDefinitionController.java
│   │   └── OrganizationController.java
│   ├── model/
│   │   ├── WorkflowDefinition.java
│   │   ├── WorkflowInstance.java
│   │   ├── WorkflowTask.java
│   │   └── organization/
│   │       ├── User.java
│   │       ├── Role.java
│   │       ├── Dept.java
│   │       └── enums/
│   │           ├── LayerType.java
│   │           └── Layer.java
│   ├── repository/
│   │   ├── WorkflowDefinitionMapper.java
│   │   ├── UserMapper.java
│   │   ├── RoleMapper.java
│   │   └── DeptMapper.java
│   └── service/
│       ├── WorkflowDefinitionService.java
│       ├── OrganizationService.java
│       └── impl/
│           ├── WorkflowDefinitionServiceImpl.java
│           └── OrganizationServiceImpl.java
```

## Setup and Configuration

### Database Configuration
The project uses H2 in-memory database for development and MySQL for production.

#### Development (H2)
```yaml
spring:
  datasource:
    url: jdbc:h2:mem:beeflow
    username: sa
    password: password
    driverClassName: org.h2.Driver
  h2:
    console:
      enabled: true
      path: /h2-console
```

#### Production (MySQL)
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/beeflow?useUnicode=true&characterEncoding=utf8&nullCatalogMeansCurrent=true
    username: root
    password: root
    driver-class-name: com.mysql.cj.jdbc.Driver
```

### Security Configuration
- CSRF is disabled for development
- H2 console and API endpoints are publicly accessible
- Frame options are disabled to allow H2 console access

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/api/**").permitAll()
                .anyRequest().authenticated()
            )
            .headers(headers -> headers.frameOptions().disable());
        return http.build();
    }
}
```

## API Endpoints

### Workflow Definition APIs
- `POST /api/workflow/definition` - Create workflow definition
- `PUT /api/workflow/definition/{id}` - Update workflow definition
- `DELETE /api/workflow/definition/{id}` - Delete workflow definition
- `GET /api/workflow/definition/{id}` - Get workflow definition by ID
- `GET /api/workflow/definition/list` - List workflow definitions

### Organization APIs
- `GET /api/organization/users/{userId}` - Get user by ID
- `GET /api/organization/users` - List users
- `GET /api/organization/roles` - List roles
- `GET /api/organization/departments` - List departments
- `GET /api/organization/users/{userId}/superior` - Get user's superior
- `GET /api/organization/users/{userId}/deptLeader` - Get user's department leader

## API Testing Results

### Workflow Definition APIs
All endpoints are implemented but returning empty responses:
- `POST /api/workflow/definition` - Returns empty response
- `GET /api/workflow/definition/list` - Returns empty response
- `GET /api/workflow/definition/{id}` - Returns empty response
- `PUT /api/workflow/definition/{id}` - Returns empty response

### Organization APIs
All endpoints are implemented but returning empty responses:
- `GET /api/organization/users` - Returns empty response
- `GET /api/organization/users/{userId}` - Returns empty response
- `GET /api/organization/roles` - Returns empty response
- `GET /api/organization/departments` - Returns empty response
- `GET /api/organization/users/{userId}/superior` - Returns empty response
- `GET /api/organization/users/{userId}/deptLeader` - Returns empty response

### Required Next Steps
1. Database Schema
   - Create SQL schema for all tables
   - Add Flyway or Liquibase for database migration
   - Create initial data scripts

2. Response Handling
   - Implement proper response DTOs
   - Add error handling
   - Add validation
   - Add proper HTTP status codes

3. Testing
   - Add integration tests
   - Add unit tests
   - Add API documentation

4. Security
   - Add authentication
   - Add authorization
   - Add role-based access control

## Troubleshooting

### MyBatis-Plus Version Compatibility
**Issue**: Incompatibility between MyBatis and MyBatis-Plus versions causing startup failure.
**Solution**: 
1. Explicitly define MyBatis version
2. Exclude MyBatis from MyBatis-Plus starter
```xml
<properties>
    <mybatis-plus.version>3.5.3.1</mybatis-plus.version>
    <mybatis.version>3.5.13</mybatis.version>
</properties>
<dependencies>
    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis</artifactId>
        <version>${mybatis.version}</version>
    </dependency>
    <dependency>
        <groupId>com.baomidou</groupId>
        <artifactId>mybatis-plus-boot-starter</artifactId>
        <version>${mybatis-plus.version}</version>
        <exclusions>
            <exclusion>
                <groupId>org.mybatis</groupId>
                <artifactId>mybatis</artifactId>
            </exclusion>
        </exclusions>
    </dependency>
</dependencies>
```

### Spring Security Configuration
**Issue**: Application failing to start due to missing HttpSecurity bean.
**Solution**: Create SecurityConfig class with proper security chain configuration.

### Database Access
**Issue**: MySQL connection issues in development environment.
**Solution**: Switch to H2 in-memory database for development to avoid external database dependencies.

## Best Practices

### Database Management
1. Use H2 for development and testing
2. Use MySQL for production
3. Enable JPA auto DDL for development only
4. Use explicit SQL scripts for production schema management

### Security
1. Disable CSRF for development only
2. Use proper authentication in production
3. Restrict API access based on roles
4. Use HTTPS in production

### Code Organization
1. Follow layered architecture (Controller -> Service -> Repository)
2. Use interfaces for services
3. Keep business logic in service layer
4. Use enums for fixed values (e.g., LayerType, Layer)

## TODO
- [ ] Implement user authentication
- [ ] Add workflow instance management
- [ ] Add workflow task management
- [ ] Implement organization relationship management
- [ ] Add API documentation (Swagger/OpenAPI)
- [ ] Add unit tests
- [ ] Configure production security settings
- [ ] Set up MySQL for production
