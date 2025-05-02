# E-commerce CQRS Microservices

This project demonstrates an implementation of Command Query Responsibility Segregation (CQRS) pattern in a microservices architecture for an e-commerce application.

## What is CQRS?

Command Query Responsibility Segregation (CQRS) is an architectural pattern that separates read and write operations for a data store. The fundamental concept is:

- **Commands**: Change the state of the system (write operations)
- **Queries**: Return data without modifying state (read operations)

By segregating these responsibilities into separate services, we can optimize each for their specific purpose, leading to improved scalability, performance, and maintainability.

## Why CQRS for E-commerce?

E-commerce platforms specifically benefit from CQRS architecture for several compelling reasons:

1. **Asymmetric Workload Handling**: E-commerce typically has a read-heavy workload (many users browsing products) with relatively fewer writes (actual purchases). CQRS allows optimizing and scaling these operations independently.

2. **Real-time Inventory Management**: Command services can handle complex inventory updates while query services ensure customers see accurate, up-to-date product availability.

3. **Complex Domain Logic**: Purchase transactions involve complex business rules (inventory checks, payment processing, fraud detection) that are better isolated in command services.

4. **Performance During Peak Times**: During sales events or high-traffic periods, the query services can be heavily scaled without affecting the transactional integrity of commands.

5. **Tailored Data Models**: Product browsing requires different data structures than order processing. CQRS allows each service to maintain optimized data representations.

6. **Analytics Integration**: Read models can be structured to facilitate powerful analytics without impacting transaction performance.

7. **Flexible Deployment**: Different components can be deployed and updated independently, reducing risk during feature rollouts.

## Project Structure

The project is organized into two main microservices:
![ChatGPT Image May 2, 2025, 07_27_56 PM](https://github.com/user-attachments/assets/fca7cd93-0769-4244-84ff-06d5de4a2825)


### 1. Product-Query-Microservice

Responsible for handling all read operations through the following components:
- **Controller**: Handles HTTP GET requests for product data
- **Service**: Contains business logic for retrieving products
- **Repository**: Interfaces with the database for read operations

API Endpoints:
- `/GET - Fetch Products`: Retrieves product information

### 2. Product-Command-Microservice

Manages all write operations with similar components:
- **Controller**: Processes HTTP POST, PUT, DELETE requests
- **Service**: Implements business logic for modifying products
- **Repository**: Interfaces with the database for write operations

API Endpoints:
- `/POST - Create Products`: Adds new products
- `/PUT - Update Products`: Modifies existing products
- `/DELETE - Delete Products`: Removes products

## Event Communication

The two microservices communicate through events:
- When commands modify data, events (`CreateEvent`, `UpdateEvent`) are generated
- These events ensure that the query-side database remains synchronized with changes made through commands

### Kafka Setup for Event Communication

This project uses Apache Kafka as the event streaming platform for communication between microservices. Follow these steps to set up Kafka:

1. **Start ZooKeeper**
   ```bash
   # Start ZooKeeper service
   bin/zookeeper-server-start.sh config/zookeeper.properties
   ```

2. **Start Kafka Broker**
   ```bash
   # Start Kafka broker service
   bin/kafka-server-start.sh config/kafka.properties
   ```

3. **Create Required Topics**
   ```bash
   # Create product events topic with appropriate partitions and replication factor
   bin/kafka-topics.sh --create --topic product-events --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1
   
   # Create additional topics as needed
   bin/kafka-topics.sh --create --topic inventory-events --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1
   ```

4. **Verify Topic Creation**
   ```bash
   # List all topics to verify creation
   bin/kafka-topics.sh --list --bootstrap-server localhost:9092
   ```

These Kafka topics serve as communication channels between the command and query services, ensuring that when data is modified through a command, the query services are updated accordingly.

## Benefits of This Architecture

1. **Scalability**: Read and write services can be scaled independently based on workload
2. **Performance**: Each service can be optimized for its specific operation type
3. **Simplified Models**: Query models can be denormalized for read efficiency
4. **Flexibility**: Different database technologies can be used for each service based on requirements
5. **Resilience**: Issues in one service don't directly impact the other

## Getting Started

### Prerequisites
- Java JDK 11+
- Maven or Gradle
- Docker (optional, for containerization)

### Running the Application
1. Clone the repository
2. Navigate to the project root directory
3. Build each microservice:
   ```
   cd product-query-service
   ./mvnw clean package
   
   cd ../product-command-service
   ./mvnw clean package
   ```
4. Run each microservice separately or using Docker Compose

## Development Notes

When extending this application:
1. Maintain strict separation between command and query responsibilities
2. Ensure proper event propagation between services
3. Consider implementing event sourcing for enhanced audit capabilities
4. Add appropriate validation in command handlers
5. Implement caching strategies in query services when needed

## License

This project is licensed under the terms of the LICENSE file included in the repository.
