# ClubConnect - Microservices Architecture

A comprehensive club management system built with Spring Boot microservices architecture, featuring service discovery, API gateway, MySQL persistence, and inter-service communication.

## 🎯 **Project Overview**

**ClubConnect** demonstrates modern microservices architecture patterns with 6 services working together to provide a complete club management solution. The system features service discovery, API gateway routing, database per service pattern, and robust inter-service communication.

---

## 🏗️ **System Architecture**

### **Microservices Architecture Diagram**
```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Eureka Server │    │   API Gateway   │    │  Club Service   │
│   (Port 8761)   │◄───┤   (Port 8080)   │◄───┤   (Port 8081)   │
│  Service Discovery│    │  Load Balancer  │    │  Club Management│
└─────────────────┘    └─────────────────┘    └─────────────────┘
                                │
                ┌───────────────┼───────────────┐
                │               │               │
        ┌───────▼──────┐ ┌──────▼──────┐ ┌─────▼──────┐
        │Member Service│ │Event Service│ │Registration│
        │ (Port 8082)  │ │ (Port 8083) │ │  Service   │
        │Member Mgmt   │ │Event Mgmt   │ │ (Port 8084)│
        │              │ │Capacity     │ │Registration│
        └──────────────┘ └─────────────┘ └────────────┘
```

### **Database Architecture (Database per Service)**
```
┌─────────────┐  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐
│  memberdb   │  │   clubdb    │  │   eventdb   │  │registrationdb│
│ (MySQL)     │  │ (MySQL)     │  │ (MySQL)     │  │ (MySQL)     │
│             │  │             │  │             │  │             │
│ members     │  │ clubs       │  │ events      │  │ registrations│
│ - id        │  │ - id        │  │ - id        │  │ - id        │
│ - name      │  │ - name      │  │ - name      │  │ - memberId  │
│ - email     │  │ - description│  │ - description│  │ - eventId   │
│ - phone     │  │ - category  │  │ - location  │  │ - memberName│
│ - clubName  │  │             │  │ - dateTime  │  │ - eventName │
│ - joinDate  │  │             │  │ - clubName  │  │ - status    │
│ - status    │  │             │  │ - maxCapacity│  │             │
└─────────────┘  └─────────────┘  └─────────────┘  └─────────────┘
```

---

## 🚀 **Technology Stack**

- **Framework**: Spring Boot 3.2.0
- **Java Version**: 17
- **Service Discovery**: Netflix Eureka
- **API Gateway**: Spring Cloud Gateway
- **Database**: MySQL 8.0
- **Build Tool**: Maven
- **Cloud**: Spring Cloud 2023.0.0
- **Inter-Service Communication**: RestClient
- **Data Transfer**: DTOs with ModelMapper

---

## 🔧 **Services Overview**

### **1. Eureka Server (Port 8761)**
**Role**: Service Discovery & Registration Center
- Registers all microservices when they start
- Provides service discovery for inter-service communication
- Monitors health of all services
- Acts as a phone book for services

### **2. API Gateway (Port 8080)**
**Role**: Single Entry Point & Load Balancer
- Routes client requests to appropriate services
- Provides load balancing
- Handles cross-cutting concerns
- Single point of access for all clients

### **3. Club Service (Port 8081)**
**Role**: Club Management & Data Orchestration
- Manages club information (name, description, category)
- Fetches member and event counts from other services
- Provides enriched club data with statistics
- Acts as central reference for club validation

### **4. Member Service (Port 8082)**
**Role**: Member Management & Club Validation
- Manages member information (name, email, phone, club)
- Validates club existence before creating members
- Provides member statistics and club-based filtering
- Enriches member data with club details

### **5. Event Service (Port 8083)**
**Role**: Event Management & Capacity Control
- Manages events (name, description, location, date, capacity)
- Controls event capacity and prevents overbooking
- Validates club existence for events
- Provides upcoming events filtering

### **6. Registration Service (Port 8084)**
**Role**: Member-Event Linking & Validation
- Links members to events (registrations)
- Validates both member and event existence
- Prevents duplicate registrations
- Updates event capacity when registrations change

---

## 🚀 **Quick Start Guide**

### **Prerequisites**
- Java 17 or higher
- Maven 3.6 or higher
- MySQL 8.0 or higher

### **Step 1: Start MySQL Service**
```bash
# Windows
net start MySQL80

# Or use our script
mysql-service-manager.bat
```

### **Step 2: Create Databases**
```bash
# Run the database creation script
create-databases-now.bat
```

### **Step 3: Start All Services**
```bash
# One command startup
start-all-services-final.bat

# Or start manually in order:
# 1. Eureka Server
cd eureka-server && mvn spring-boot:run

# 2. API Gateway
cd api-gateway && mvn spring-boot:run

# 3. All Microservices
cd club-service && mvn spring-boot:run
cd member-service && mvn spring-boot:run
cd event-service && mvn spring-boot:run
cd registration-service && mvn spring-boot:run
```

### **Step 4: Verify Services**
```bash
# Check all ports
check-ports.bat

# Test services
test-all-services-final.bat
```

---

## 🌐 **Service URLs**

- **Eureka Dashboard**: http://localhost:8761
- **API Gateway**: http://localhost:8080
- **Club Service**: http://localhost:8081
- **Member Service**: http://localhost:8082
- **Event Service**: http://localhost:8083
- **Registration Service**: http://localhost:8084

---

## 📋 **API Endpoints**

### **Club Service (Port 8081)**
- `GET /clubs` - Get all clubs
- `GET /clubs/{id}` - Get club by ID
- `POST /clubs` - Create new club
- `PUT /clubs/{id}` - Update club
- `DELETE /clubs/{id}` - Delete club
- `GET /clubs/{id}/statistics` - Get club statistics

### **Member Service (Port 8082)**
- `GET /members` - Get all members
- `GET /members/{id}` - Get member by ID
- `POST /members` - Create new member
- `PUT /members/{id}` - Update member
- `DELETE /members/{id}` - Delete member
- `GET /members/club/{clubName}` - Get members by club
- `GET /members/statistics` - Get member statistics

### **Event Service (Port 8083)**
- `GET /events` - Get all events
- `GET /events/{id}` - Get event by ID
- `POST /events` - Create new event
- `PUT /events/{id}` - Update event
- `DELETE /events/{id}` - Delete event
- `GET /events/upcoming` - Get upcoming events
- `GET /events/club/{clubName}` - Get events by club
- `GET /events/statistics` - Get event statistics

### **Registration Service (Port 8084)**
- `GET /registrations` - Get all registrations
- `GET /registrations/{id}` - Get registration by ID
- `POST /registrations` - Create new registration
- `PUT /registrations/{id}` - Update registration
- `DELETE /registrations/{id}` - Delete registration
- `GET /registrations/member/{memberId}` - Get registrations by member
- `GET /registrations/event/{eventId}` - Get registrations by event
- `GET /registrations/statistics` - Get registration statistics

### **API Gateway (Port 8080)**
- `GET /clubs` - Route to Club Service
- `GET /members` - Route to Member Service
- `GET /events` - Route to Event Service
- `GET /registrations` - Route to Registration Service

---

## 🧪 **Testing the Services**

### **Using curl**
```bash
# Test service status
curl http://localhost:8081/clubs
curl http://localhost:8082/members
curl http://localhost:8083/events
curl http://localhost:8084/registrations

# Test through API Gateway
curl http://localhost:8080/clubs
curl http://localhost:8080/members
curl http://localhost:8080/events
curl http://localhost:8080/registrations

# Create a club
curl -X POST http://localhost:8081/clubs \
  -H "Content-Type: application/json" \
  -d '{"name":"Tech Club","description":"Technology enthusiasts","category":"Technology"}'

# Create a member
curl -X POST http://localhost:8082/members \
  -H "Content-Type: application/json" \
  -d '{"name":"Alice Smith","email":"alice@example.com","phone":"123-456-7890","clubName":"Tech Club"}'
```

### **Using Postman**
Import the `postman-collection.json` file to test all endpoints with pre-configured requests.

---

## 🔗 **Service Communication Flow**

### **Example: Creating a Member**
```
Client Request → API Gateway → Member Service → Club Service (validation) → Database
```

### **Example: Getting Club Statistics**
```
Client Request → API Gateway → Club Service → Member Service + Event Service → Database
```

### **Example: Event Registration**
```
Client Request → API Gateway → Registration Service → Member Service + Event Service → Database
```

---

## 🗄️ **Database Management**

### **View Databases**
```bash
# Check all databases
view-databases.bat

# Or manually
mysql -u root -p2004 -e "SHOW DATABASES;"
```

### **Database Details**
- **Host**: localhost:3306
- **Username**: root
- **Password**: 2004
- **Databases**: clubdb, memberdb, eventdb, registrationdb

---

## 🎓 **Viva Presentation Guide**

### **What to Show to Teacher**

1. **Project Structure** - Show the folder organization
2. **Eureka Dashboard** - Show service registration at http://localhost:8761
3. **Database View** - Show data persistence in MySQL
4. **API Gateway** - Show routing at http://localhost:8080
5. **Inter-Service Communication** - Show services talking to each other
6. **CRUD Operations** - Demonstrate Create, Read, Update, Delete
7. **Data Enrichment** - Show how services combine data from multiple sources

### **Key Points to Explain**

- **Microservices Architecture**: Each service is independent
- **Service Discovery**: Eureka manages service registration
- **API Gateway**: Single entry point for all requests
- **Database per Service**: Each service has its own database
- **Inter-Service Communication**: Services talk to each other via HTTP
- **Data Enrichment**: Services combine data from multiple sources
- **Fault Tolerance**: System continues working if one service fails
- **Scalability**: Each service can be scaled independently

---

## 📊 **Project Structure**

```
ClubConnect/
├── 📁 eureka-server/           # Service Discovery Server
├── 📁 api-gateway/             # API Gateway & Load Balancer
├── 📁 club-service/            # Club Management Service
├── 📁 member-service/          # Member Management Service
├── 📁 event-service/           # Event Management Service
├── 📁 registration-service/    # Registration Management Service
├── 📄 postman-collection.json  # API Testing Collection
├── 📄 start-all-services-final.bat # Complete Startup Script
├── 📄 test-all-services-final.bat # Complete Test Script
├── 📄 check-ports.bat          # Port Verification Script
├── 📄 view-databases.bat       # Database Viewer Script
├── 📄 mysql-service-manager.bat # MySQL Service Manager
└── 📄 create-databases-now.bat # Database Creation Script
```

---

## ✅ **Features Implemented**

### **✅ Completed Features**
- [x] Microservices architecture setup
- [x] Service discovery with Eureka
- [x] API Gateway with routing
- [x] Complete CRUD operations for all services
- [x] MySQL database integration
- [x] Inter-service communication
- [x] Data enrichment and validation
- [x] DTO pattern with ModelMapper
- [x] Error handling and logging
- [x] Sample data initialization
- [x] Service health monitoring
- [x] Database per service pattern
- [x] Professional-grade business logic

### **🎯 Key Technical Achievements**
- **Professional Architecture**: Clean microservices design with proper separation of concerns
- **Inter-Service Communication**: Robust communication between services with error handling
- **Data Enrichment**: Services fetch and combine data from multiple sources
- **Validation**: Cross-service validation ensures data integrity
- **Scalability**: Each service can be scaled independently
- **Maintainability**: Clean code with proper patterns and documentation
- **Real-world Features**: Capacity management, registration system, statistics
- **MySQL Integration**: Production-ready database with data persistence
- **Service Discovery**: Eureka-based service registration and discovery
- **API Gateway**: Centralized routing and load balancing

---

## 🚨 **Troubleshooting**

### **Common Issues**

1. **Services Not Starting**
   - Check if MySQL is running
   - Check if ports are available
   - Check service configuration

2. **Services Not Registering with Eureka**
   - Start Eureka Server first
   - Check Eureka configuration
   - Check service names

3. **Database Connection Failed**
   - Check MySQL service
   - Check password (2004)
   - Check database names

4. **Inter-Service Communication Failing**
   - Check service discovery
   - Check service names
   - Check Eureka dashboard

---

## 📝 **Quick Reference Commands**

```bash
# Start everything
start-all-services-final.bat

# Check ports
check-ports.bat

# Test services
test-all-services-final.bat

# View databases
view-databases.bat

# Manage MySQL
mysql-service-manager.bat

# Check Eureka
http://localhost:8761

# Test API Gateway
curl http://localhost:8080/clubs
```

---

## 🤝 **Contributing**

This project demonstrates a complete microservices architecture with proper service communication, data persistence, and professional-grade features. It's ready for viva presentation and showcases modern software development practices.

## 📝 **License**

This project is for educational and demonstration purposes.
