# CarRentals

## Overview
A Spring-boot,Mysql application used to keep track of list of avaiable entities and make operations on them using Complete AuthN and AuthZ principles, based on the roles assigned!

## Technologies Used
- Spring Boot
- MySQL
- Docker

## Setup Instructions
1. Clone the repository:
   ```bash
   git clone https://github.com/ashu00026/CarRentals.git
   ```

2. Navigate to the project directory:
   ```bash
   cd carsRental/
   ```

3. Build the Docker images and start the containers:
   ```bash
   docker-compose up --build
   ```

4. Access the application:
   - The Spring Boot application will be accessible at [http://localhost:8080](http://localhost:8080).
   - The MySQL database can be accessed on port 3306.

## Directory Structure
```
project_directory/
├── docker-compose.yml
├── Dockerfile
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── cars/carsRental
│       │           └── ...
│       └── resources/
│           └── application.properties
├── mysql-init-scripts/
│   └── init.sql
├── .m2/
└── ...
```

## Configuration
### `docker-compose.yml`
- Defines services for the Spring Boot application and MySQL database.
- Sets environment variables for MySQL database configuration.

### `Dockerfile`
- Builds the Docker image for the Spring Boot application.

### `application.properties`
- Configures the connection to the MySQL database.

### `init.sql`
- Contains SQL script for initializing the database schema.

## Development Environment
- Java Development Kit (JDK)
- Docker Desktop

## Deployment
- For production deployment, consider using a container orchestration platform like Kubernetes or deploying to a cloud provider.

## Troubleshooting
- If tables are not created during startup, ensure that the SQL script (`init.sql`) is correctly formatted and mounted into the MySQL container.

## Dependencies
- List of key dependencies and their versions, defined in the `pom.xml` file.

## Future Improvements
- Potential enhancements or features to be added in future iterations.
