# Film Management Service

## Overview
This project is a Spring Boot service designed to manage films and directors in a database. It provides a REST API for creating, retrieving, updating, and deleting film records, as well as managing director records. The service includes endpoints for generating reports, importing data from JSON files, and integrating with a database using Liquibase for schema management.

## Endpoints

1. **Create Film**
    - **Method:** POST
    - **Endpoint:** `/api/v1/films`
    - **Description:** Creates a new film record.

2. **Get Film by ID**
    - **Method:** GET
    - **Endpoint:** `/api/v1/films/{id}`
    - **Description:** Retrieves the details of a film record by its ID.

3. **Update Film**
    - **Method:** PUT
    - **Endpoint:** `/api/v1/films/{id}`
    - **Description:** Updates the data of a film record by its ID.

4. **Delete Film**
    - **Method:** DELETE
    - **Endpoint:** `/api/v1/films/{id}`
    - **Description:** Deletes a film record by its ID.

5. **List Films**
    - **Method:** POST
    - **Endpoint:** `/api/v1/films/_list`
    - **Description:** Returns a list of film elements that match the requested page and total number of pages.

6. **Generate Film Report**
    - **Method:** POST
    - **Endpoint:** `/api/v1/films/_report`
    - **Description:** Generates and offers to download a report file with all film records that match the filter criteria.

7. **Upload Films from JSON**
    - **Method:** POST
    - **Endpoint:** `/api/v1/films/upload`
    - **Description:** Accepts a JSON file for data import. Stores all valid records from this file in the database.

8. **List Directors**
    - **Method:** GET
    - **Endpoint:** `/api/v1/directors`
    - **Description:** Returns a list of all director records available in the database.

9. **Create Director**
    - **Method:** POST
    - **Endpoint:** `/api/v1/directors`
    - **Description:** Adds a new director record.

10. **Update Director**
    - **Method:** PUT
    - **Endpoint:** `/api/v1/directors/{id}`
    - **Description:** Updates the data of a director record by its ID.

11. **Delete Director**
    - **Method:** DELETE
    - **Endpoint:** `/api/v1/directors/{id}`
    - **Description:** Deletes a director record by its ID.

## Integration Tests
All endpoints are covered with integration tests to ensure the functionality and reliability of the service.

## Liquibase Script
A Liquibase script is included to create the necessary database schema and populate data for directors.

## JSON Data Import
A JSON file is provided for data import, ensuring consistency with the Liquibase filling script.(by path: src/main/resources/films/file1.json)

**Note:** Entity 1 represents films, and Entity 2 represents directors in this project context. Ensure to follow the provided API specifications and data formats for seamless integration and functionality.
