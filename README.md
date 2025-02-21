## 📚 **Library Management System API**  
_A RESTful API built with Spring Boot for managing books, patrons, and borrowing records in a library._

---

## 📖 **Overview**
This **Library Management System API** allows users to:
- 📚 **Manage Books** (Add, Update, Delete, View).
- 👨‍🎓 **Manage Patrons** (Library users).
- 🔄 **Track Borrowing & Returns**.
- 🚀 **Optimized Performance with Caching**.

Built with **Spring Boot**, **Spring Data JPA**, and **Spring Cache**, this API is structured, efficient, and scalable.

---

## 🚀 **Features**
✅ **Book & Patron CRUD Operations**  
✅ **Borrowing & Return System**  
✅ **Spring Cache for Performance Optimization**  
✅ **AOP Logging for Method Calls**  
✅ **Spring Data JPA for Database Access**  
✅ **Unit Testing with JUnit & Mockito**  
✅ **MySQL Database Integration**  
❌ **Security Not Implemented Yet**  

---

## 🏗 **Project Structure**
```
📂 src/
 ├── 📂 main/java/com/example/library_management
 │    ├── 📂 controller        # REST Controllers (Handles API Requests)
 │    ├── 📂 service           # Business Logic (Handles core logic)
 │    ├── 📂 repository        # Data Access Layer (Spring Data JPA)
 │    ├── 📂 entity            # Database Models (JPA Entities)
 │    ├── 📂 dto               # Data Transfer Objects (DTOs)
 │    ├── 📂 exception         # Custom Exception Handling
 │    ├── 📂 aspect            # Logging with AOP
 │    ├── LibraryManagementApplication.java  # Main Application Entry Point
 ├── 📂 test/java/com/example/library_management # Unit Tests
```

---

## 🛠 **Tech Stack**
| Technology  | Description  |
|-------------|-------------|
| **Spring Boot** | Core framework for the API |
| **Spring Data JPA** | ORM for database interactions |
| **Spring Cache** | Performance optimization with caching |
| **Spring AOP** | Aspect-Oriented Programming for logging |
| **MySQL** | Primary Database |
| **JUnit & Mockito** | Unit Testing |

---

## 🔌 **API Endpoints**
### **📚 Books**
| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/books` | Retrieve all books |
| `GET` | `/api/books/{id}` | Get details of a specific book |
| `POST` | `/api/books` | Add a new book |
| `PUT` | `/api/books/{id}` | Update an existing book |
| `DELETE` | `/api/books/{id}` | Remove a book |

### **🧑 Patrons**
| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/patrons` | Retrieve all patrons |
| `GET` | `/api/patrons/{id}` | Get patron details |
| `POST` | `/api/patrons` | Add a new patron |
| `PUT` | `/api/patrons/{id}` | Update patron information |
| `DELETE` | `/api/patrons/{id}` | Remove a patron |

### **🔄 Borrowing & Returns**
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/borrow/{bookId}/patron/{patronId}` | Borrow a book |
| `PUT` | `/api/return/{bookId}/patron/{patronId}` | Return a borrowed book |

---

## 🚀 **Setup & Installation**
### **1️⃣ Clone the Repository**
```sh
git clone https://github.com/your-repo/library-management-system.git
cd library-management-system
```

### **2️⃣ Configure Database**
Edit `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/library_management
spring.datasource.username=root
spring.jpa.hibernate.ddl-auto=update
```

### **3️⃣ Run the Application**
```sh
mvn spring-boot:run
```

---

## 🔥 **Performance Optimization (Caching)**
| Cached Data  | Annotation Used  |
|-------------|----------------|
| **All Books** | `@Cacheable("books")` |
| **Single Book** | `@Cacheable(value = "book", key = "#id")` |
| **All Patrons** | `@Cacheable("patrons")` |
| **Single Patron** | `@Cacheable(value = "patron", key = "#id")` |

When adding, updating, or deleting data, cache is cleared using `@CacheEvict` to ensure consistency.

---

## 📑 **Testing**
To run unit tests:
```sh
mvn test
```

---

## ❗ **Future Improvements**
- **Enable Security:** Implement JWT authentication & role-based access control.

