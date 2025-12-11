# Notes Taking
## Back-end Technologies (API REST)
<section align="left">
    <img alt="Static Badge" src="https://img.shields.io/badge/Kotlin-grey?style=flat&logo=kotlin">
    <img alt="Static Badge" src="https://img.shields.io/badge/Spring%20Boot-grey?style=flat&logo=springboot">
    <img alt="Static Badge" src="https://img.shields.io/badge/Spring%20Web-grey?style=flat&logo=Spring%20Boot">
    <img alt="Static Badge" src="https://img.shields.io/badge/Spring%20Validation-grey?style=flat&logo=Spring%20Boot">
    <img alt="Static Badge" src="https://img.shields.io/badge/Spring%20Data%20JPA-grey?style=flat&logo=Spring%20Boot"> 
    <img alt="Static Badge" src="https://img.shields.io/badge/Hibernate-grey?style=flat&logo=Hibernate">
    <img alt="Static Badge" src="https://img.shields.io/badge/JUnit5-grey?style=flat&logo=JUnit5">
    <img alt="Static Badge" src="https://img.shields.io/badge/Docker-grey?style=flat&logo=Docker">
    <img alt="Static Badge" src="https://img.shields.io/badge/PostgreSQL-grey?style=flat&logo=PostgreSQL">
    <img alt="Static Badge" src="https://img.shields.io/badge/pgAdmin-grey?style=flat&logo=PostgreSQL">
    <img alt="Static Badge" src="https://img.shields.io/badge/Postman-grey?style=flat&logo=Postman">
    <img alt="Static Badge" src="https://img.shields.io/badge/Yaml-grey?style=flat&logo=yaml">
    <img alt="Static Badge" src="https://img.shields.io/badge/Swagger/OpenAPI-grey?style=flat&logo=Swagger">
    <img alt="Static Badge" src="https://img.shields.io/badge/Makefile-grey?style=flat&logo=Make">
    
</section>

## API Contract and Definitions
### Endpoints

#### Notes
Endpoints for the main HTTP operations in Notes

| Method | URL               | Description   |
| ------ | ----------------- | ------------- |
| GET    | `/api/notes`      | All Notes     |
| GET    | `/api/notes/{id}` | Note by ID    |
| POST   | `/api/notes`      | Create a Note |
| DELETE | `/api/notes/{id}` | Delete by ID  |
| PUT    | `/api/notes/{id}` | Change by ID  |

#### Tags
Endpoints for the main HTTP operations in Tags

| Method | URL                | Description        |
| ------ | ------------------ | ------------------ |
| GET    | `/api/tags`        | List all tags      |
| POST   | `/api/tags`        | Create a new tag   |
| DELETE | `/api/tags/{name}` | Delete tag by name |
| PUT    | `/api/tags/{name}` | Update tag by name |


