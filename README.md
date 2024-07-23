# Real Estate Application Documentation

## Overview
This document provides a comprehensive overview of the Real Estate Application, designed to streamline the process of buying, selling, and renting real estate properties. It connects property owners with potential buyers or tenants through a user-friendly interface.

## Application Structure
The application leverages a microservices architecture, enhancing scalability and deployment efficiency. Each microservice is dedicated to a specific function within the application, facilitating independent development and maintenance.

### Services and Endpoints
- **Advert Service** (`/advert`):
    - `GET /`: List all adverts.
    - `GET /{id}`: Get an advert by ID.
    - `POST /`: Create a new advert.
    - `PUT /{id}`: Update an advert by ID.
    - `DELETE /{id}`: Delete an advert by ID.
    - `GET /adverts/{userId}`: Get all adverts by user ID.

- **Auth Service** (`/auth`):
    - `POST /token`: Generate an authentication token.
    - `GET /validate`: Validate an authentication token.
    - `POST /register`: Register a new user.

- **Order Service** (`/order`):
    - `GET /`: List all orders.
    - `GET /{id}`: Get an order by ID.
    - `POST /`: Create a new order.
    - `PUT /{id}`: Update an order by ID.
    - `GET /availableAdvertRights/{userId}`: Get available advert rights by user ID.
    - `GET /orderByUserId/{userId}`: Get orders by user ID.

- **Payment Service** (`/payment`):
    - `GET /`: List all payments.
    - `GET /{id}`: Get a payment by ID.
    - `POST /`: Create a new payment.
    - `PUT /{id}`: Update a payment by ID.
    - `GET /{orderId}`: Get payments by order ID.

- **User Service** (`/user`):
    - `GET /`: List all users.
    - `GET /{id}`: Get a user by ID.
    - `PUT /{id}`: Update a user by ID.
    - `GET /exists/{id}`: Check if a user exists by ID.

### Technologies
- **Backend**: Java with Spring Boot for microservices.
- **Frontend**: React for the user interface.
- **Database**: PostgreSQL for data storage.
- **Messaging**: RabbitMQ for asynchronous communication.
- **Containerization**: Docker & Docker Compose for service orchestration.

## Getting Started
To run the application locally:

1. Clone the repository:
   ```bash
   git clone https://github.com/celebiabdulkadir/realestateapp.git
   cd realestateapp
   ```
Build and start the services using Docker Compose:<pre>docker-compose up --build </pre>
Directory Structure
```bash
/advert/: Advert Service and its Dockerfile.
/auth/: Auth Service and its Dockerfile.
/order/: Order Service and its Dockerfile.
/payment/: Payment Service and its Dockerfile.
/user/: User Service and its Dockerfile.
/gateway/: Gateway Service and its Dockerfile.
/serviceregistry/: Service Registry and its Dockerfile.
docker-compose.yaml: Docker Compose configuration.
```
## API Endpoints
Detailed API endpoints for each service are provided in the Services and Endpoints section above.

## Frontend Setup
After setting up the microservices, you can start the frontend application as follows:  

1- Ensure Node.js is installed on your system. You can download it from https://nodejs.org/.  
2- Navigate to the frontend directory:  <pre>cd frontend </pre>
3- Install the necessary dependencies:  <pre>npm install </pre>
4- Start the development server:  <pre>npm run dev </pre>
The application will be accessible at http://localhost:3000. 