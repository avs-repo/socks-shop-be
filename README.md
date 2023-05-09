<div id="badges" align="center">
  
## Socks Shop Storage Management System API
</div>
<div id="badges" align="center">
  
![JetBrains](https://img.shields.io/badge/IntelliJ%20IDEA-java-blue?style=for-the-badge&logo=jetbrains&logoColor=white)
![Spring](https://img.shields.io/badge/Spring-green?style=for-the-badge&logo=spring&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
</div>

___
## Description
Application is made to manage socks production warehouse.

___
## Features:
- Registering socks income (POST api/socks/income)
- Registering socks outcome (POST api/socks/outcome)
- Recieving total socks quantity of required color and cotton content at storage (GET api/socks?color=X&operation=Y&cottonPart=Z)
- All data saved at PostgresSQL data base
- Implemented integration testing

___
## Tools used
- Java 17
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- PostgreSQL
- Liquibase
- Mapstruct
- Lombok
- Docker (DB at Docker container)
- Heroku (use this [link](https://socks-shop.herokuapp.com) to access app)
