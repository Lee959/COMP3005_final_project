# Fitness Club Management System
### COMP3005 Final Project — Hibernate + PostgreSQL
### Group Members: Qin Li (101296723), Shuting Li (101304351)

This project implements a **console-based Fitness Club Management System** supporting Members, Trainers, and Administrative Staff.  
It uses **Java**, **Hibernate ORM**, and **PostgreSQL** to simulate a real fitness club’s scheduling, booking, and maintenance operations.

The system includes complete CRUD operations, trainer availability, class management, room booking, equipment maintenance, billing, and SQL enhancements (trigger, view, index).

---

# Technologies Used

- **Java 21**
- **Hibernate 7.1** (ORM for database persistence)
- **PostgreSQL 17**
- **Maven**
- **JPA / Jakarta Persistence** (imports)
- **pgAdmin**
- **Markdown** (documentation)

---

# Database Design (Hibernate + PostgreSQL)

The system uses **11+ fully mapped JPA entities**, including:
* Member
* Trainer
* AdminStaff
* FitnessGoal
* HealthMetric
* Room
* TrainerAvailability
* PersonalTrainingSession
* GroupClass
* Equipment
* MaintenanceTicket


### Entity Design Includes:
- Proper `@Entity` and `@Table` usage
- Relationships:
    - `@ManyToOne`
    - `@OneToMany`
    - `@JoinColumn`
- Cascading behavior where appropriate
- Auto-generated IDs using PostgreSQL sequences
- 

--- 
# Project Structure
src/main/java/org/example  
├── entity/  
│   ├── Member.java  
│   ├── Trainer.java  
│   ├── AdminStaff.java  
│   ├── Room.java  
│   ├── Equipment.java  
│   ├── MaintenanceTicket.java  
│   ├── GroupClass.java  
│   ├── PersonalTrainingSession.java  
│   ├── TrainerAvailability.java
│   └── FitnessGoal.java
├── utils/  
│   └── HibernateUtil.java  
│  
├── FitnessClubCLI.java
└── README.md (current file)  


# Implemented Features (Based on Project Requirements)

## 1. Member Functions
The system includes **all required operations** for members:

### User Registration
Members register with:
- Unique email
- Auto-generated password
- Name, gender, registration timestamp

### Profile Management
Members may update:
- Personal info
- Fitness goals
- Health metrics

### Health History
Every metric is stored with a timestamp.  
Old data is **not overwritten**, satisfying the “history-based metrics” requirement.

### Member Dashboard
Shows:
- Latest health metric
- Current fitness goal
- Upcoming sessions
- Past class count

### Group Class Registration
Includes validation:
- Room capacity
- Time conflicts
- Trigger-based capacity enforcement

### PT Session Scheduling
Validates:
- Trainer availability
- Room availability
- Time conflicts

---

## 2. Trainer Functions
Trainers perform all required operations:

### Set Availability
Trainers add non-overlapping availability windows.

### View Schedule
Displays all:
- Assigned personal training sessions
- Assigned group classes

### Member Lookup
Search by *partial or complete* name (case-insensitive).  
Displays:
- Latest health metric
- Current fitness goal

---

## 3. Administrative Staff Functions
Admins support club operations in the system:

### Equipment Maintenance
Full maintenance workflow:
- Add equipment
- Open ticket
- Resolve ticket
- List open tickets

### Class Management
Admins can:
- Create classes
- Assign trainer
- Assign room
- Set time and capacity


# Additional Required SQL Features

##  1. Index
Improves member name search performance:

```sql
CREATE INDEX idx_members_last_first 
ON members(last_name, first_name);
This significantly speeds up the trainer "member lookup" operation
```

## 2. Trigger
Improves member name search performance:

```sql
CREATE OR REPLACE FUNCTION check_class_capacity()
RETURNS TRIGGER AS $$
DECLARE
current_count INT;
room_cap INT;
BEGIN
SELECT COUNT(*) INTO current_count
FROM group_classes gc
WHERE gc.class_id = NEW.class_id;

    SELECT capacity INTO room_cap
    FROM rooms r
    JOIN group_classes gc ON r.room_id = gc.room_id
    WHERE gc.class_id = NEW.class_id;

    IF current_count >= room_cap THEN
        RAISE EXCEPTION 'Room is full — cannot register.';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_class_capacity
BEFORE INSERT ON group_classes
FOR EACH ROW EXECUTE FUNCTION check_class_capacity();
```
This trigger is included in the database and shown in pgAdmin during the demo video.

##  3. View
A view to retrieve the latest health metric for each member:
```sql
CREATE VIEW latest_health_metrics AS
SELECT DISTINCT ON (member_id)
        member_id, metric_value, metric_type, recorded_at
        FROM health_metric
        ORDER BY member_id, recorded_at DESC;

```

# Test Data Generators

To simplify testing during the demo, several entities use **automatic testing constructors** to generate realistic random data. This helps populate the database quickly and consistently during development and demonstration.

### Entities with Automatic Test Constructors

- `Member`
- `Trainer`
- `AdminStaff`
- `Room`
- `Equipment`

### What Gets Auto-Generated

Each of these entities generates fields such as:

- Random names
- Random emails
- Random passwords
- Random genders
- Random room names (for Room)
- Random room capacity
- Random equipment status

These constructors allow the CLI to create multiple sample records rapidly, supporting easy testing of scheduling, class assignment, ticket workflows, and more.

---
# Instruction to run program
### 1. Create the Database
   CREATE DATABASE comp3005_final_project;

### 2. Configure hibernate.cfg.xml

Set your own credentials in `hibernate.cfg.xml` by replacing the user and password:

```xml
<property name="connection.url">jdbc:postgresql://localhost:5432/comp3005_final_project</property>
<property name="connection.username">YOUR_USER</property>
<property name="connection.password">YOUR_PASSWORD</property>
<property name="hibernate.hbm2ddl.auto">update</property>
```

### 3. Create All the Table
Using ``
### 3. Build the Application with Maven
   mvn clean install

### 4. Run the Program

You will see:

Health & Fitness Club Management System
1. Member Menu
2. Trainer Menu
3. Admin Menu
0. Exit

### 5. Generate the room, member, trainer, and admin staff
1. Run the generating functions
2. Then just follow the instructions 

# BONUS (ORM - HIBERNATE)

This project makes extensive use of **Hibernate ORM** to manage database persistence and entity relationships.  
Instead of manually writing SQL for every operation, our system relies on Hibernate to:

### Map Java classes to PostgreSQL tables
Each entity in the `org.example.model` package is annotated using JPA annotations such as:

- `@Entity`
- `@Table`
- `@Id` + `@GeneratedValue`
- `@ManyToOne`, `@OneToMany`
- `@JoinColumn`

Hibernate automatically creates, updates, and manages the underlying schema based on these mappings.

### Handle queries using HQL (Hibernate Query Language)
Operations like member search, trainer lookup, and retrieving schedules use **HQL** or **Criteria Queries**, for example:

```java
Query<Member> query = session.createQuery(
    "FROM Member WHERE LOWER(first_name) LIKE :name", Member.class);
```

Hibernate converts HQL to optimized SQL for PostgreSQL.

### Manage relationships

Complex relationships such as:

* trainers ↔ availability
* members ↔ health metrics
* rooms ↔ classes
* equipment ↔ maintenance tickets

are handled automatically by Hibernate through cascading and lazy loading.

### Provide transaction management

Every database operation is wrapped in Hibernate-managed transactions:

```
Transaction tx = session.beginTransaction();
session.persist(entity);
tx.commit();
```

This ensures data integrity for booking, scheduling, and maintenance workflows.
 
### Simplify persistence

By using Hibernate, the system avoids manual SQL for:

* inserting new members, trainers, equipment
* updating profiles
* creating classes and PT sessions
* opening/closing maintenance tickets
* room assignments
* Hibernate automatically handles SQL generation and object persistence.