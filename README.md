

# Social Media Platform Project

## Prerequisites

* **Java 17** or later (check with `java -version`)
* **Maven 3.8+** (check with `mvn -version`)
* Your IDE of choice (IntelliJ IDEA, Eclipse, VS Code, etc.)

---

## 1. Clone the Repository

```bash
git clone https://github.com/SyedAli0805/Social-Media-Platform
cd Social-Media-Platform
```

---

## 2. Build the Project

```bash
mvn clean install
```

---

## 3. Run the Application

```bash
mvn spring-boot:run
```

The application will start at:

```
http://localhost:8080
```

---

## 4. Access Swagger UI

Swagger is available at:

```
http://localhost:8080/swagger-ui/index.html
```

---

## 5. Using JWT Token in Swagger

Some APIs require authentication.

1. First, log in or call the authentication API to get a JWT token,
   or use the provided token:
2. In Swagger, click the **Authorize** button (top right).
3. Enter:

   ```
   Bearer <your-token-here>
   ```

   Example:

   ```
   Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGkiLCJpYXQiOjE3NTUxMDEwMzUsImV4cCI6MTc1NTE4NzQzNX0.uZBFJ_WTB-VIF8jr2uDkV7JPJMf4XAUsf5PUvtoIHfQ
   ```
4. Click **Authorize**, then close the dialog.
   Now you can call the secured APIs.

---

## 6. Example API Calls

### Public API (No Auth)

```http
GET /
```

### Secured API (Requires JWT)

```http
POST /post
Authorization: Bearer <your-token>
```

---

## 7. Stopping the Application

Press `STOP ICON` in the terminal where it's running.

---

## 8. Notes

* Make sure your token is valid. Expired tokens will cause `401 Unauthorized`.
* To change server port or other configs, edit `src/main/resources/application.properties`.

---

