# Skate School Booking Management System :)


## Project Description
Skate School is a management application developer for all skateboarding school to help them with organizing their bookings.
It allows for the management of students, teachers, lessons, and bookings.
The application offer CRUD functionality for all main entities and implements a secure authentication system.


## Class Diagram
![VisualClassDiagram.png](src/main/resources/docs/VisualClassDiagram.png)
Find the same diagram in src/main/resources/docs/ClassDiagram.puml (PlantUML plugin needed for the visual render of the diagram)

## Configuration
1. Clone the repository
2. Ensure you have Java 21 and Maven installed
3. Set up the following environment variables:
    - `DB_USERNAME`: Your MySQL database username
    - `DB_PASSWORD`: Your MySQL database password
    - `JWT_SECRET`: A secret key for JWT token generation
4. Configure your MySQL database in the `application.properties` file
5. Run `mvn clean install` to install dependencies
6. Start the application with `mvn spring-boot:run`


## Environment Variables
The application uses the following environment variables:
- `DB_USERNAME`: Database username
- `DB_PASSWORD`: Database password
- `JWT_SECRET`: Secret key for JWT token generation

Make sure to set these variables in your environment or use a `.env` file before running the application.


## Technologies Used
- Java 21
- Spring Boot 3.2.3 (stable version)
- Spring Data JPA
- Spring Security
- MySQL 8.2.0
- Lombok 1.18.30
- MapStruct 1.5.5.Final
- JWT 0.12.5 for authentication
- Flyway 9.22.3 for database migrations 
- (Flyway is currently disabled, was not requested but i've implemented it for learning purposes.)


## Controller Structure and Endpoints

### AuthController
- POST /api/auth/signup : Register a new user
- POST /api/auth/signin : User login

### UserController
- GET /api/v1/users/me : Retrieve authenticated user's information
- GET /api/v1/users : Retrieve all active users (admin only)
- GET /api/v1/users/{userId} : Retrieve a user by their ID (admin only)
- POST /api/v1/users : Create a new user (admin only)
- PUT /api/v1/users/{userId} : Update an existing user's information (admin only)
- PATCH /api/v1/users/{userId}/status : Update user's active status (admin only)
- DELETE /api/v1/users/{userId} : Delete a user (admin only)
- GET /api/v1/users/test : Verify access to protected resources
- GET /api/v1/users/student-only : Test endpoint accessible only to users with STUDENT role
- GET /api/v1/users/teacher-only : Test endpoint accessible only to users with TEACHER role
- GET /api/v1/users/debug/users : Debugging endpoint to list all users (admin only)

### StudentController
- GET /api/students : Retrieve all students
- GET /api/students/{id} : Retrieve a specific student
- POST /api/students : Create a new student
- PUT /api/students/{id} : Update an existing student
- DELETE /api/students/{id} : Delete a student
- GET /by-style/{skateStyle} : Retrieve by Skate Style
- GET /by-skill/{skillLevel} : Retrieve by Skill Level

### TeacherController
- GET /api/teachers : Retrieve all teachers
- GET /api/teachers/{id} : Retrieve a specific teacher
- POST /api/teachers : Create a new teacher
- PUT /api/teachers/{id} : Update an existing teacher
- DELETE /api/teachers/{id} : Delete a teacher
- GET /by-specialty : Retrieve by specialty
- GET /sorted-by-experience : Sort by experience (in ages)
- GET /lesson-counts : Retrieve teachers with most lessons

### LessonController
- GET /api/lessons : Retrieve all lessons
- GET /api/lessons/{id} : Retrieve a specific lesson
- POST /api/lessons : Create a new lesson
- PUT /api/lessons/{id} : Update an existing lesson
- DELETE /api/lessons/{id} : Delete a lesson
- GET /api/lessons/teacher/{teacherId} : Retrieve all lessons by teacher ID

### BookingController
- GET /api/bookings : Retrieve all bookings
- GET /api/bookings/{id} : Retrieve a specific booking
- POST /api/bookings : Create a new booking
- PUT /api/bookings/{id} : Update an existing booking
- DELETE /api/bookings/{id} : Delete a booking
- PATCH /api/bookings/{id}/confirm : Confirm a booking
- GET /api/bookings/student/{studentId} : Retrieve all bookings for a specific student
- GET /api/bookings/lesson/{lessonId} : Retrieve all bookings for a specific lesson


## Database Configuration
The application uses MySQL as its database. The connection details are configured in the `application.properties` file. Make sure to set up your MySQL database and update the connection URL if necessary.


## Security
The application uses JWT for authentication. Make sure to keep your JWT secret key secure and do not expose it in your code or version control system.


## Flyway Migration
Flyway is configured in the application but is currently disabled. If you wish to enable it for database migrations, set `spring.flyway.enabled=true` in the `application.properties` file.

## Additional material
POSTMAN collection directory path:
src/main/resources/docs/Skate School API.postman_collection.json
If else: https://marcox-6864112.postman.co/workspace/Marco-X's-Workspace~b4e9325d-f072-4e7d-8bfc-cf0811bb9983/collection/43880328-06a68101-ac20-4849-b8b7-311d71d67924?action=share&creator=43880328
Google Presentation - https://docs.google.com/presentation/d/1p41sY0S9TpZdUjRavgDJdGU-ZFhMpSibSdefptejHyI/edit?usp=sharing
Trello - https://trello.com/invite/b/683428a753a59b2f9bc03921/ATTI32b8236f58766411daa45ce305187329DCE905B1/final-project

## Future Development 
- The front-end implementation, photo and video upload extension, comments and greater interest in the front-end part are all key.  The code is ready to be implemented. It will be based on the needs of the app itself and its use. It is essential that there is better control or a pre-set schedule by the administrator or teacher for private bookings or public lessons. Sending a confirmation email within the app with eventual options like:

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
</dependency>
Adding links to bookings and lessons to access the Google Maps location and/or make a booking.

Security:
Implement rate limiting to prevent brute force attacks with options like:

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cache</artifactId>
</dependency>
<dependency>
    <groupId>com.github.vladimir-bukhtoyarov</groupId>
    <artifactId>bucket4j-core</artifactId>
    <version>7.6.0</version>
</dependency>
<dependency>
    <groupId>com.github.vladimir-bukhtoyarov</groupId>
    <artifactId>bucket4j-jcache</artifactId>
    <version>7.6.0</version>
</dependency>

## Resources
- google.com
- w3schools.com
- dev.to
- github.com
- spotify.com
- youtube.com