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
- **Frontend**: Next.js/Typescript for the user interface.
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

## Frontend Pages
### Home Page / Property Listing Page
```bash
Path: /
Description: The landing page of the application, showcasing featured properties/adverts and recent listings. Displays a list of properties available for sale or rent
```
<img width="1512" alt="Screenshot 2024-07-23 at 05 03 39" src="https://github.com/user-attachments/assets/d6e03070-1226-473f-a1c5-818926189103">

### Create/Edit Adverts Screen
```bash
Path: /
Description: You can create and edit the advert that you created
```
![Screenshot 2024-07-23 at 05 19 46](https://github.com/user-attachments/assets/23b45ce5-284b-420f-9e92-4492a01db5dc)


Property Detail Page
```bash
Path: /advert/:id
Description: Detailed view of a property, including photos, price, description, and contact information.
```
![Screenshot 2024-07-23 at 05 15 03](https://github.com/user-attachments/assets/290592f5-5ddd-4ad3-b8b0-c011cf62e41b)

User Packages Page
```bash
Path: /packages
Description: The packages that user bought and display advert rights
```
<img width="1511" alt="Screenshot 2024-07-23 at 05 04 25" src="https://github.com/user-attachments/assets/748c4574-8670-447b-bf96-092338136754">

Package Purchase Screen
```bash
Path: /packages
Description: You can buy package to get advert right to publish adverts
```

<img width="1512" alt="Screenshot 2024-07-23 at 05 04 35" src="https://github.com/user-attachments/assets/f4578637-30e2-4511-b128-250ca4fac556">

Login Page
```bash
Path: /login
Description: Authentication page for users to log in.
```
<img width="1512" alt="Screenshot 2024-07-23 at 05 05 08" src="https://github.com/user-attachments/assets/e2d9207f-0a2b-443d-a5b2-9b20de24e57e">

Register Page
```bash
path: /register
Desription: Register new user
```
<img width="1512" alt="Screenshot 2024-07-23 at 05 05 17" src="https://github.com/user-attachments/assets/2bff5b00-8644-4a1a-aa3a-e9d4724cff18">



