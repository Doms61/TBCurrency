# tbkurz

tbkurz is a Spring Boot application that provides currency conversion functionality. It supports retrieving all available currencies and converting a specified amount from one currency to another.

## Prerequisites

Before you begin, ensure you have met the following requirements:

- **Java 17**: Make sure you have Java 17 installed on your machine.
- **Maven**: You should have Maven installed for building the project.
- **Docker**: Install Docker and Docker Desktop to run the application in a container.

## Getting Started

To run the application locally, follow these steps:

1. **Clone the repository:**
   ```bash
   git clone https://github.com/Doms61/TBCurrency.git
   cd TBCurrency

2. **Build the project:**
   ```bash
   mvn clean install
   
3. **Run the application:**
   ```bash
   mvn spring-boot:run
   
4. **Access the application:**
   Open your browser and navigate to [http://localhost:8080/api/swagger-ui/index.html](http://localhost:8080/api/swagger-ui/index.html) to interact with the API using Swagger UI.

## Containerization

To run the application using Docker, follow these steps:

1. **Build the Docker image:**
   ```bash
   docker build -t tbkurz .
2. **Run the Docker container:**
   ```bash
   docker run -p 8080:8080 tbkurz
4. **Access the application:**
   Open your browser and navigate to [http://localhost:8080/api/swagger-ui/index.html](http://localhost:8080/api/swagger-ui/index.html) to interact with the API using Swagger UI.

## API Endpoints

### Currency Controller

1. **Get All Currencies**
    - **Endpoint:** `GET /api/currency/getAll`
    - **Description:** Retrieves a list of all currencies.
    - **Response:**
        - **Status 200:** Returns a list of currencies.
        - **Example:**
          ```json
          [
            {
              "currencyCode": "AUD",
              "exchangeRate": 1.157
            },
            {
              "currencyCode": "CZK",
              "exchangeRate": 25.256
            }
          ]
          ```

2. **Convert Currency**
    - **Endpoint:** `GET /api/currency/convert/{currencyCode}/{amount}`
    - **Description:** Converts the specified amount from the given currency code to the target currency.
    - **Path Parameters:**
        - `currencyCode`: The code of the currency to convert from (e.g., "CZK").
        - `amount`: The amount to convert.
    - **Response:**
        - **Status 200:** Returns the converted currency details.
        - **Example:**
          ```json
          {
            "currencyCode": "EUR",
            "amount": 99.976
          }
          ```

3. **Currency Not Found**
    - **Endpoint:** `GET /api/currency/convert/{currencyCode}/{amount}`
    - **Description:** If the currency code does not exist, it will return a 404 status.
    - **Response:**
        - **Status 404:** Returns an error message indicating that the currency was not found.
        - **Example:**
          ```
          Currency XYZ was not found.
          ```