# AvtoPark - Fleet Management System

## Project Topic
Course project for building a car fleet management system with `Spring Boot` and a simple frontend in `static` resources.

The system provides:
- REST API for cars, users, rentals, services, car details, and car images
- Role-based security (`ADMIN`, `USER`)
- Session-based login via custom login page
- Database schema and seed data via Liquibase

## Implemented Functionalities

### Roles and Access
- **ADMIN**
  - Manage users (CRUD)
  - Manage cars (CRUD)
  - Manage rentals (CRUD)
  - Manage services (CRUD)
  - Manage car images and car details
- **USER**
  - Sign in to the system
  - Create and view rentals (based on secured API rules)

### Core Entities and Relations
- `users` -> many-to-one -> `roles`
- `users` -> one-to-many -> `rentals`
- `cars` -> one-to-one -> `car_details`
- `cars` -> one-to-many -> `car_images`
- `cars` -> one-to-many -> `rentals`
- `cars` -> many-to-many -> `services` through `car_service`

### Backend (REST)
Implemented controllers:
- `/api/users`
- `/api/cars`
- `/api/car-details`
- `/api/rentals`
- `/api/services`
- `/api/car-images`
- `/api/auth/me` (current session/user check)

### Security
- Custom login page: `/login.html`
- Login endpoint: `POST /perform_login`
- Logout endpoint: `POST /perform_logout`
- Session-based auth with `JSESSIONID`
- Redirect to login page for protected resources

### Frontend (Static pages)
- `index.html` - Home
- `login.html` - Sign in
- `cars.html` - Cars view
- `rentals.html` - Rentals view
- `services.html` - Services view
- `users.html` - User management page (admin-focused)

## Technology Stack
- Spring Boot (Web, Data JPA, Security, Actuator)
- Liquibase
- OpenAPI (Swagger UI)
- Lombok
- MapStruct
- H2 database (in-memory)
- HTML/CSS/JS frontend in static resources

## How to Run

### 1) Start application
```bash
.\mvnw.cmd spring-boot:run
```

### 2) Open in browser
- Home: [http://localhost:8080/](http://localhost:8080/)
- Login: [http://localhost:8080/login.html](http://localhost:8080/login.html)
- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- H2 console: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

### 3) Demo accounts
- **Admin**
  - username: `admin`
  - password: `admin123`
- **User**
  - username: `user`
  - password: `user123`

## API/Session Check
- Endpoint: [http://localhost:8080/api/auth/me](http://localhost:8080/api/auth/me)
- Returns current logged user info and session id when authenticated.

## Screenshots for Submission
Add screenshots to the `docs/screenshots/` folder and keep the names:
- `01-login-page.png` - Login page
- `02-home-after-login.png` - Home page after successful login
- `03-cars-page.png` - Cars page
- `04-rentals-page.png` - Rentals page
- `05-services-page.png` - Services page
- `06-users-page-admin.png` - Users page as admin
- `07-swagger.png` - Swagger UI
- `08-running-app-terminal.png` - Running Spring Boot app in terminal

Short screenshot notes are in `docs/SCREENSHOTS.md`.

## Project Status
The submitted part is working, runnable, documented, and includes backend + frontend mockup pages with authentication flow.
