# Quantity Measurement App

## 📏 UC1 – Feet Measurement Equality

## Description  
This use case checks the equality of two numerical values measured in feet using proper object-oriented design principles.

## Objective  
To compare two feet measurements and return `true` if equal, otherwise `false`.

## Preconditions  
- `Feet.java` is instantiated  
- Two numeric values (in feet) are provided  

## Main Flow  
1. User provides two feet values  
2. Objects of `Feet` class are created  
3. `equals()` method is invoked  
4. Values are compared using `Double.compare()`  
5. Result is returned  

## Postcondition  
Returns `true` if both values are equal, otherwise `false`.

## Key Concepts  
- Overriding `equals()` correctly  
- Floating-point comparison using `Double.compare()`  
- Null and type safety checks  
- Encapsulation and immutability  
- Equality contract (reflexive, symmetric, transitive, consistent)

🔗 _Code Link:_ 
👉 [UC1 – Feet Measurement Equality](https://github.com/Deepanshukhoushi/QuantityMeasurementApp/tree/feature/UC1-FeetEquality)

--- 

## 📏 UC2 – Feet and Inches Measurement Equality

## Description  
This use case extends UC1 to support equality checks for both Feet and Inches measurements.  
Feet and Inches are treated as separate entities and compared independently.

## Objective  
To validate and compare two Feet values and two Inches values using proper equality logic.

## Preconditions  
- `Inches.java` is instantiated  
- Two numeric values for Feet and Inches are provided  

## Main Flow  
1. Main method calls static method for Feet equality  
2. Main method calls static method for Inches equality  
3. Separate `Feet` and `Inches` objects are created  
4. `equals()` method is invoked  
5. Values are compared using `Double.compare()`  
6. Result (`true` / `false`) is returned  

## Postcondition  
Returns equality result for Feet-to-Feet and Inches-to-Inches comparisons.

## Key Concepts  
- Object Equality and equals() contract  
- Floating-point comparison using `Double.compare()`  
- Null and type safety checks  
- Encapsulation and immutability  
- DRY principle consideration (code duplication in Feet & Inches classes)

🔗 _Code Link:_ 
👉 [UC2 – Feet and Inches Measurement Equality](https://github.com/Deepanshukhoushi/QuantityMeasurementApp/tree/feature/UC2-InchEquality)

---

## 📏 UC3 – Generic Quantity Class (DRY Principle)

## Description  
UC3 refactors separate Feet and Inches classes into a single `QuantityLength` class using an enum.  
This eliminates code duplication and follows the DRY (Don't Repeat Yourself) principle.

## Objective  
To compare measurements across units (e.g., 1 ft == 12 inches) using a common base unit conversion.

## Preconditions  
- `QuantityMeasurementApp` is instantiated  
- Two numeric values with unit types are provided  
- Conversion factors are defined in `LengthUnit` enum  

## Main Flow  
1. User provides value and unit  
2. Input and unit are validated  
3. Values are converted to base unit (feet)  
4. Converted values are compared using `Double.compare()`  
5. Equality result is returned  

## Postcondition  
Returns `true` if converted values are equal, otherwise `false`.  
All UC1 and UC2 functionality remains preserved.

## Key Concepts  
- DRY Principle (no duplicate unit classes)  
- Enum for type-safe units  
- Cross-unit comparison (1 ft = 12 inches)  
- Encapsulation and abstraction  
- Equality contract and null safety  
- Scalable design for adding new units

🔗 _Code Link:_ 
👉 [UC3 – Generic Quantity Class (DRY Principle)](https://github.com/Deepanshukhoushi/QuantityMeasurementApp/tree/feature/UC3-GenericLength)

---

## 📏 UC4 – Extended Unit Support (Yards & Centimeters)

## Description  
UC4 extends UC3 by adding YARDS and CENTIMETERS to the `LengthUnit` enum.  
The generic `QuantityLength` class now supports feet, inches, yards, and centimeters without code duplication.

## Objective  
To compare measurements across multiple units (ft, in, yd, cm) using common base conversion.

## Preconditions  
- Refactored `QuantityLength` class from UC3 is used  
- Units supported: FEET, INCHES, YARDS, CENTIMETERS  
- Conversion factors are defined in the enum  

## Main Flow  
1. User provides value and unit  
2. Input and unit are validated  
3. Values are converted to a common base unit  
4. Converted values are compared using `Double.compare()`  
5. Equality result is returned  

## Postcondition  
Returns `true` if converted values are equal across any unit combination.  
All UC1–UC3 functionality remains intact.

## Key Concepts  
- Scalable generic design  
- Enum extensibility  
- Cross-unit conversion (1 yd = 3 ft = 36 in, 1 cm = 0.393701 in)  
- DRY principle validation  
- Mathematical accuracy in conversions  
- Backward compatibility

🔗 _Code Link:_ 
👉 [UC4 – Extended Unit Support (Yards & Centimeters)](https://github.com/Deepanshukhoushi/QuantityMeasurementApp/tree/feature/UC4-YardEquality)

---

## 📏 UC5 – Unit-to-Unit Conversion (Same Measurement Type)

## Description  
UC5 extends UC4 by adding explicit unit-to-unit conversion functionality.  
The `QuantityLength` API now provides a `convert()` method to transform values between supported units.

## Objective  
To convert a numeric value from one LengthUnit to another using centralized conversion factors.

## Preconditions  
- `QuantityLength` class and `LengthUnit` enum exist  
- Supported units: FEET, INCHES, YARDS, CENTIMETERS  
- Conversion factors are defined relative to a base unit  

## Main Flow  
1. Client calls `convert(value, sourceUnit, targetUnit)`  
2. Validate value (finite number) and units (non-null)  
3. Normalize value to base unit  
4. Convert base value to target unit  
5. Return converted numeric result  

## Postcondition  
Returns mathematically equivalent value in target unit.  
Invalid inputs throw `IllegalArgumentException`.

## Key Concepts  
- Enum-based conversion factor management  
- Base unit normalization formula  
- Immutability and value object design  
- Method overloading and API usability  
- Floating-point precision handling  
- Bidirectional and round-trip conversion accuracy  
- Clean, scalable conversion API

🔗 _Code Link:_ 
👉 [UC5 – Unit-to-Unit Conversion](https://github.com/Deepanshukhoushi/QuantityMeasurementApp/tree/feature/UC5-UnitConversion)

---

## 📏 UC6 – Addition of Two Length Units (Same Category)

## Description  
UC6 extends UC5 by introducing addition between two length measurements.  
Two lengths (possibly different units) can be added, and the result is returned in the unit of the first operand.

## Objective  
To add two QuantityLength objects using base unit normalization and return a new immutable result.

## Preconditions  
- `QuantityLength` class and `LengthUnit` enum exist  
- Units supported: FEET, INCHES, YARDS, CENTIMETERS  
- Both operands belong to the same measurement category (length)  

## Main Flow  
1. Validate operands and units (non-null, finite values)  
2. Convert both values to a common base unit  
3. Add the normalized values  
4. Convert the sum to the unit of the first operand  
5. Return a new `QuantityLength` object  

## Postcondition  
Returns a new immutable object representing the sum.  
Original objects remain unchanged.  
Invalid inputs throw `IllegalArgumentException`.

## Key Concepts  
- Arithmetic on value objects  
- Base unit normalization for cross-unit addition  
- Immutability and factory-style method design  
- Commutative property (A + B = B + A)  
- Identity element (adding zero)  
- Precision handling with floating-point tolerance  
- Reuse of conversion logic from UC5

🔗 _Code Link:_ 
👉 [UC6 – Addition of Two Length Units](https://github.com/Deepanshukhoushi/QuantityMeasurementApp/tree/feature/UC6-UnitAddition)

---

## 📏 UC7 – Addition with Target Unit Specification

## Description  
UC7 extends UC6 by allowing the caller to explicitly specify the target unit for the addition result.  
The sum is returned in the chosen unit, not automatically inferred from operands.

## Objective  
To add two QuantityLength objects and return the result in any supported target unit.

## Preconditions  
- `QuantityLength` class and `LengthUnit` enum exist  
- Supported units: FEET, INCHES, YARDS, CENTIMETERS  
- A valid target unit is explicitly provided  

## Main Flow  
1. Validate operands and target unit (non-null, finite values)  
2. Convert both operands to base unit  
3. Add normalized values  
4. Convert sum to specified target unit  
5. Return a new immutable `QuantityLength` object  

## Postcondition  
Returns a new object expressed in the explicitly specified unit.  
Original objects remain unchanged.  
Invalid inputs throw `IllegalArgumentException`.

## Key Concepts  
- Method overloading for flexible API design  
- Explicit target unit control  
- Base unit normalization for arithmetic  
- Immutability and DRY principle preservation  
- Commutativity of addition across all target units  
- Precision handling using epsilon tolerance  
- Clear and scalable API design

🔗 _Code Link:_ 
👉 [UC7 – Addition with Target Unit Specification](https://github.com/Deepanshukhoushi/QuantityMeasurementApp/tree/feature/UC7-TargetUnitAddition)

---

## 📏 UC8 – Standalone LengthUnit with Conversion Responsibility

## Description  
UC8 refactors the design by extracting `LengthUnit` into a standalone enum.  
Conversion logic is moved from `QuantityLength` to `LengthUnit`, improving cohesion and scalability.

## Objective  
To centralize unit conversion responsibility inside `LengthUnit` and simplify `QuantityLength`.

## Preconditions  
- `LengthUnit` is a top-level enum (FEET, INCHES, YARDS, CENTIMETERS)  
- Conversion factors are defined inside the enum  
- `QuantityLength` delegates conversion logic to `LengthUnit`  

## Main Flow  
1. `LengthUnit` provides `convertToBaseUnit()` and `convertFromBaseUnit()`  
2. `QuantityLength` delegates conversion to unit methods  
3. Equality, conversion, and addition use delegated logic  
4. All UC1–UC7 functionality remains unchanged  

## Postcondition  
- Conversion logic is centralized in `LengthUnit`  
- `QuantityLength` focuses only on arithmetic and comparison  
- Circular dependency risks are eliminated  
- Backward compatibility is preserved  

## Key Concepts  
- Single Responsibility Principle (SRP)  
- Separation of concerns  
- Delegation pattern  
- Enum encapsulating behavior  
- Architectural scalability for multiple measurement categories  
- Refactoring without breaking public API  
- Improved cohesion and reduced coupling

🔗 _Code Link:_ 
👉 [UC8 – Standalone LengthUnit with Conversion Responsibility](https://github.com/Deepanshukhoushi/QuantityMeasurementApp/tree/feature/UC8-StandaloneUnit)

---

## ⚖️ UC9 – Weight Measurement (Kilogram, Gram, Pound)

## Description  
UC9 extends the application to support a new measurement category: **Weight**.  
It introduces `WeightUnit` and `QuantityWeight`, mirroring the length design (UC1–UC8).

## Objective  
To support equality, conversion, and addition for weight units:  
KILOGRAM (base), GRAM, and POUND.

## Preconditions  
- `WeightUnit` is a standalone enum  
- Conversion factors are relative to kilogram (base unit)  
- Weight and Length categories are independent  

## Main Flow  
1. Validate value and unit (non-null, finite number)  
2. Normalize to base unit (kilogram)  
3. Perform equality, conversion, or addition  
4. Convert result to target unit (if specified)  
5. Return new immutable `QuantityWeight` object  

## Postcondition  
- Equivalent weights across units are equal (1 kg = 1000 g ≈ 2.20462 lb)  
- Conversion and addition are mathematically accurate within epsilon  
- Length and weight are incomparable categories  
- All UC1–UC8 functionality remains unaffected  

## Key Concepts  
- Multiple measurement categories  
- Enum-based conversion responsibility  
- Base unit normalization (kilogram)  
- Category type safety (weight ≠ length)  
- Immutability and value object design  
- Overloaded add() methods (implicit & explicit target unit)  
- Scalable architecture for future categories

🔗 _Code Link:_ 
👉 [UC9 – Weight Measurement (Kilogram, Gram, Pound)](https://github.com/Deepanshukhoushi/QuantityMeasurementApp/tree/feature/UC9-Weight-Measurement)

---

## 🔷 UC10 – Generic Quantity with Unit Interface (Multi-Category Support)

## Description  
UC10 refactors the system into a single generic `Quantity<U extends IMeasurable>` class.  
All measurement categories (Length, Weight, etc.) share a common `IMeasurable` interface.

## Objective  
To eliminate duplication across category-specific Quantity classes and enable scalable multi-category support.

## Preconditions  
- `IMeasurable` interface defines conversion contract  
- `LengthUnit` and `WeightUnit` implement `IMeasurable`  
- Generic `Quantity<U>` replaces QuantityLength & QuantityWeight  

## Main Flow  
1. Units implement `IMeasurable` (conversion responsibility)  
2. `Quantity<U>` stores value + unit (immutable)  
3. Equality normalizes via base unit comparison  
4. Conversion and addition delegate to unit methods  
5. Generics enforce compile-time category safety  

## Postcondition  
- Single reusable `Quantity<U>` class for all categories  
- No duplicate logic across length, weight, etc.  
- Cross-category comparisons prevented (Length ≠ Weight)  
- All UC1–UC9 functionality preserved  
- New categories require only a new enum implementing `IMeasurable`

## Key Concepts  
- Generic programming with bounded types  
- Interface-based design & polymorphism  
- DRY and Single Responsibility Principle  
- Open-Closed Principle (easy extension)  
- Composition over inheritance  
- Type safety with generics  
- Scalable architecture for future measurement categories

🔗 _Code Link:_ 
👉 [UC10 – Generic Quantity](https://github.com/Deepanshukhoushi/QuantityMeasurementApp/tree/feature/UC10-Generic-Quantity)

---

## 🧪 UC11 – Volume Measurement (Litre, Millilitre, Gallon)

## Description  
UC11 introduces a new measurement category: **Volume**, using the generic `Quantity<U>` class from UC10.  
Only a new `VolumeUnit` enum implementing `IMeasurable` is required.

## Objective  
To support equality, conversion, and addition for volume units:  
LITRE (base), MILLILITRE, and GALLON.

## Preconditions  
- `Quantity<U extends IMeasurable>` is fully operational  
- `IMeasurable` interface is implemented  
- No changes to existing Length or Weight logic  

## Main Flow  
1. Create `VolumeUnit` implementing `IMeasurable`  
2. Normalize values to base unit (LITRE)  
3. Perform equality, conversion, or addition  
4. Convert result to target unit (if specified)  
5. Return new immutable `Quantity<VolumeUnit>` object  

## Postcondition  
- Equivalent volumes across units are equal (1 L = 1000 mL ≈ 0.264172 gal)  
- Conversion and addition are accurate within epsilon  
- Volume is incompatible with Length and Weight  
- No changes required to `Quantity<U>` or app logic  

## Key Concepts  
- True scalability of generic architecture  
- Enum implementing shared interface  
- Base unit normalization (litre)  
- Cross-category type safety  
- Immutability and value object design  
- DRY and Open-Closed Principle validation  
- Linear system growth with new categories

🔗 _Code Link:_ 
👉 [UC11 – Volume Measurement](https://github.com/Deepanshukhoushi/QuantityMeasurementApp/tree/feature/UC11-Volume-Measurement)

---

## ➖➗ UC12 – Subtraction and Division Operations

## Description  
UC12 extends the generic `Quantity<U>` class by adding:
- **Subtraction** → returns a new `Quantity<U>`
- **Division** → returns a dimensionless `double` ratio  

Supports all measurement categories (Length, Weight, Volume).

## Objective  
Enable full arithmetic manipulation while maintaining:
- Cross-unit support
- Immutability
- Type safety
- SOLID principles

## Preconditions  
- `Quantity<U extends IMeasurable>` is operational  
- All units implement `IMeasurable`  
- Addition, conversion, equality already functional  

## Subtraction Flow  
1. Validate non-null & same category  
2. Convert both operands to base unit  
3. Subtract base values  
4. Convert result to implicit or explicit target unit  
5. Return new immutable `Quantity<U>`  

## Division Flow  
1. Validate non-null & same category  
2. Prevent division by zero  
3. Convert both to base unit  
4. Divide values  
5. Return dimensionless `double` result  

## Postconditions  
- Subtraction supports implicit & explicit target units  
- Division returns pure scalar ratio  
- Cross-category operations are prevented  
- Original objects remain unchanged  
- Works for Length, Weight, and Volume  

## Key Concepts  
- Non-commutative operations  
- Immutability in arithmetic  
- Base unit normalization  
- Division-by-zero validation  
- Consistent validation patterns  
- Method overloading for flexibility  
- Scalable arithmetic architecture  

🔗 _Code Link:_ 
👉 [UC12 – Subtraction and Division Operations](https://github.com/Deepanshukhoushi/QuantityMeasurementApp/tree/feature/UC12-Subtraction-and-Division)

---

## ♻️ UC13 – Centralized Arithmetic Logic (DRY Refactoring)

## Description  
UC13 refactors arithmetic operations (add, subtract, divide) to eliminate duplication.  
A centralized private helper method handles validation, base-unit conversion, and operation execution.

## Objective  
Enforce the **DRY principle** by extracting repeated arithmetic logic into a reusable helper method without changing public APIs.

## Key Refactoring Changes  
- Introduced private `ArithmeticOperation` enum (ADD, SUBTRACT, DIVIDE)  
- Created `validateArithmeticOperands()` helper  
- Created `performBaseArithmetic()` helper  
- Public method signatures remain unchanged  

## Flow  
1. Validate operands (null, category, finiteness, target unit)  
2. Convert both quantities to base unit  
3. Execute operation via enum dispatch  
4. Convert result to target unit (if applicable)  
5. Return new immutable `Quantity<U>` or scalar (for divide)  

## Postconditions  
- No code duplication across arithmetic methods  
- Validation logic centralized  
- Conversion logic centralized  
- All UC12 behavior preserved  
- Error handling consistent across operations  
- Future operations (multiply, modulo) easily extendable  

## Benefits  
- Single source of truth for validation & conversion  
- Reduced maintenance burden  
- Cleaner, shorter public methods  
- Enum-based type-safe operation dispatch  
- Improved scalability and readability  
- Zero behavioral change (regression-free refactoring)

🔗 _Code Link:_ 
👉 [UC13 – Centralized Arithmetic Logic](https://github.com/Deepanshukhoushi/QuantityMeasurementApp/tree/feature/UC13-Centralized-Arithmetic-Logic)

---

## 🌡️ UC14 – Temperature Measurement with Selective Arithmetic Support

## Description  
UC14 introduces **Temperature** (Celsius, Fahrenheit, Kelvin) into the generic system.  
Unlike length, weight, and volume, temperature supports **conversion and equality only**, while arithmetic operations are restricted.

## Objective  
Refactor `IMeasurable` to support **optional arithmetic operations** using default methods and capability checks.

## Key Enhancements  
- Added `TemperatureUnit` enum (CELSIUS, FAHRENHEIT, KELVIN)  
- Refactored `IMeasurable` with default operation-support methods  
- Introduced `SupportsArithmetic` functional interface  
- Arithmetic validation added to `Quantity<U>`  
- Unsupported operations throw `UnsupportedOperationException`  

## Supported Operations (Temperature)  
✔ Equality comparison  
✔ Unit conversion  
❌ Addition (absolute temperatures)  
❌ Subtraction (absolute temperatures)  
❌ Division  

## Flow  
1. Validate category compatibility  
2. Convert via non-linear temperature formulas  
3. Validate operation support before arithmetic  
4. Throw meaningful exception for unsupported operations  

## Postconditions  
- Temperature integrates without modifying existing categories  
- Length, Weight, Volume remain fully arithmetic-enabled  
- Cross-category comparisons prevented  
- Interface Segregation Principle applied  
- Backward compatibility preserved (UC1–UC13 unchanged)  

## Key Concepts  
- Interface Segregation Principle (ISP)  
- Default methods in interfaces  
- Functional interfaces & lambda expressions  
- Capability-based design  
- Non-linear unit conversion handling  
- UnsupportedOperationException semantics  
- Scalable architecture for diverse measurement rules  

🔗 _Code Link:_ 
👉 [UC14 – Temperature Measurement](https://github.com/Deepanshukhoushi/QuantityMeasurementApp/tree/feature/UC14-Temperature-Measurement)

---

## 🏗️ UC15 – N-Tier Architecture Refactoring

## Description
UC15 refactors the Quantity Measurement Application from a monolithic design into a **professional N-Tier architecture**.  
The system is divided into **Application, Controller, Service, and Entity/Model layers** to achieve clear separation of concerns.

## Objective
Improve scalability, maintainability, and testability by separating **presentation, business logic, and data representation**.

## Architecture Layers
- **Application Layer** – `QuantityMeasurementApp` initializes components and starts the application.
- **Controller Layer** – `QuantityMeasurementController` handles requests and delegates operations.
- **Service Layer** – `QuantityMeasurementServiceImpl` implements business logic for comparison, conversion, and arithmetic.
- **Entity/Model Layer** – `QuantityDTO`, `QuantityModel`, and `QuantityMeasurementEntity` represent data structures.

## Key Components
- `IQuantityMeasurementService` – Service contract
- `IQuantityMeasurementRepository` – Data access contract
- `QuantityMeasurementCacheRepository` – Singleton in-memory repository
- `QuantityMeasurementException` – Custom exception for measurement errors

## Key Benefits
- Separation of Concerns (SoC)
- Improved testability and maintainability
- Reusable service layer for CLI, REST, or GUI
- Dependency Injection ready
- Supports design patterns: **Factory, Singleton, Facade**

## Postconditions
- All UC1–UC14 functionality preserved
- Business logic isolated from UI
- Layered architecture ready for **REST APIs or Spring Boot integration**

🔗 _Code Link:_ 
👉 [UC15 – N-Tier Architecture Refactoring](https://github.com/Deepanshukhoushi/QuantityMeasurementApp/tree/feature/UC15-N-Tier)

---

## 🗄️ UC16 – Database Integration with JDBC

## Description
UC16 enhances the Quantity Measurement Application by integrating a **relational database using JDBC** for persistent storage.  
The application now stores measurement operations in a database instead of relying only on in-memory caching.

## Objective
Enable **long-term persistence, audit history, and scalable storage** using a database repository while maintaining the N-Tier architecture from UC15.

## Key Enhancements
- Introduced `QuantityMeasurementDatabaseRepository`
- JDBC integration with **H2 database (development/testing)**
- Maven project structure and dependency management
- Connection pooling for efficient database access
- Parameterized SQL queries for **SQL injection protection**
- Database schema initialization using `schema.sql`

## Architecture Integration
- **Controller Layer** → Handles requests  
- **Service Layer** → Business logic  
- **Repository Layer** → JDBC database operations  
- **Entity Layer** → Data models and DTOs  

## Key Components
- `ApplicationConfig` – Loads database configuration  
- `ConnectionPool` – Manages database connections  
- `DatabaseException` – Handles database errors  
- `QuantityMeasurementDatabaseRepository` – JDBC persistence implementation  

## Features
- Store and retrieve quantity measurement history
- Query measurements by operation or measurement type
- Support both **cache and database repositories via dependency injection**
- Logging using SLF4J
- Maven build and test automation

## Postconditions
- All UC1–UC15 functionality preserved
- Measurements persisted in database
- Connection pooling and transaction management implemented
- System ready for **enterprise-level persistence and analytics**

🔗 _Code Link:_ 
👉 [UC16 – Database Integration with JDBC](https://github.com/Deepanshukhoushi/QuantityMeasurementApp/tree/feature/UC16-Database-Integration-with-JDBC)

---

## 🚀 UC17 – Spring Boot REST + JPA Integration

## Description
UC17 transforms the Quantity Measurement Application into a **Spring Boot-based REST API** using **Spring Data JPA** for persistence.  
It replaces JDBC with ORM and exposes functionality via HTTP endpoints.

## Objective
Modernize the application with **Spring ecosystem**, enabling scalable, maintainable, and production-ready architecture.

## Key Enhancements
- Spring Boot auto-configuration and embedded Tomcat
- REST APIs using `@RestController`
- Spring Data JPA replacing JDBC
- Dependency Injection with `@Autowired`
- Global exception handling using `@ControllerAdvice`
- Validation using annotations (`@NotNull`, `@NotEmpty`)
- Swagger/OpenAPI for API documentation

## Architecture
- **Controller Layer** → REST endpoints  
- **Service Layer** → Business logic + transactions  
- **Repository Layer** → JPA repositories  
- **Model Layer** → Entities & DTOs  

## Key Components
- `QuantityMeasurementRepository` – JPA repository  
- `QuantityMeasurementServiceImpl` – Spring service  
- `QuantityMeasurementController` – REST API  
- `GlobalExceptionHandler` – centralized error handling  
- `QuantityMeasurementApplication` – Spring Boot entry point  

## Features
- Perform compare, convert, add operations via REST APIs
- Store and retrieve measurement history using JPA
- Query by operation type and measurement type
- Structured JSON responses with proper HTTP status codes
- Swagger UI for interactive API testing

## Postconditions
- Application runs on `http://localhost:8080`
- APIs available at `/api/v1/quantities/*`
- H2 database integrated for development
- Fully Spring-managed architecture
- Ready for future enhancements (Security, Microservices)

🔗 _Code Link:_ 
👉 [UC17 – Spring Boot REST + JPA Integration](https://github.com/Deepanshukhoushi/QuantityMeasurementApp/tree/feature/UC17-Spring-Backend-for-Quantity-Measurement)

---

## 🔐 UC18 – Google Authentication and User Management

## Description
UC18 implements comprehensive **OAuth2 authentication with Google** and advanced **user management** features.  
The system supports both local email/password authentication and Google OAuth2, with seamless user registration and profile management.

## Objective
Provide secure, scalable authentication with Google OAuth2 integration and complete user lifecycle management.

## Key Features
- **Dual Authentication**: Local (email/password) + Google OAuth2
- **Automatic User Registration**: First-time Google users auto-created
- **Profile Management**: User details, profile pictures, email verification
- **Session Management**: JWT tokens with refresh token rotation
- **Security**: Password strength validation, email OTP for password reset
- **Audit Trail**: Track authentication events and user activities

## Authentication Flow

### Local Authentication
1. **Registration**: User provides email, password, name, mobile
2. **Validation**: Strong password requirements enforced
3. **Storage**: Password BCrypt hashed, email marked unverified
4. **Welcome Email**: Sent asynchronously after registration
5. **Login**: Email/password validation, JWT generation
6. **Session**: Access token (10 days) + Refresh token (30 days)

### Google OAuth2 Authentication
1. **Redirect**: User redirected to Google OAuth2 consent screen
2. **Authorization**: Google authenticates user and returns authorization code
3. **Token Exchange**: Backend exchanges code for access/id tokens
4. **User Lookup**: Find existing user by Google provider ID
5. **Auto Registration**: Create new user if not found
6. **JWT Generation**: Generate access and refresh tokens
7. **Frontend Redirect**: Redirect to frontend with JWT

### Password Reset Flow
1. **Request**: User provides email address
2. **Validation**: Check email exists and is verified
3. **OTP Generation**: 6-digit OTP with 15-minute expiry
4. **Email Delivery**: Send OTP to user's email
5. **Verification**: User provides OTP and new password
6. **Update**: Validate OTP, update password, invalidate sessions

## User Management Features

### Profile Management
- **Personal Information**: First name, last name, email, mobile
- **Profile Picture**: URL from OAuth2 provider or custom
- **Email Verification**: Track verification status
- **Authentication Provider**: Track local vs Google authentication
- **Provider ID**: Store OAuth2 provider's unique user ID

### Security Features
- **Password Strength**: 8+ chars, uppercase, lowercase, number, special character
- **Email Validation**: Format validation and domain verification
- **Mobile Validation**: 10-digit number validation
- **Session Security**: JWT with 512-bit signing key
- **Token Rotation**: Refresh tokens rotated on each use
- **Blacklisting**: Access tokens blacklisted on logout

### Audit and Monitoring
- **Authentication Events**: Log successful/failed logins
- **Password Changes**: Track password reset events
- **OAuth2 Events**: Log Google authentication attempts
- **User Activity**: Track measurement operations per user
- **Error Tracking**: Comprehensive error logging

## API Endpoints

### Authentication Endpoints
```http
POST /api/auth/register          # Local user registration
POST /api/auth/login             # Local user login
POST /api/auth/logout            # Secure logout with token blacklisting
POST /api/auth/refresh           # Refresh access token
POST /api/auth/forgotPassword/{email}  # Request password reset OTP
POST /api/auth/resetPassword/{email}   # Reset password with OTP
GET  /oauth2/authorize/google    # Google OAuth2 authorization
GET  /login/oauth2/code/google   # Google OAuth2 callback
```

### User Management Endpoints
```http
GET  /api/user/me               # Get current user profile
PUT  /api/user/profile          # Update user profile
DELETE /api/user/account        # Delete user account
GET  /api/user/history          # Get user's measurement history
GET  /api/user/statistics       # Get user's usage statistics
```

## Security Implementation

### JWT Token Structure
```json
{
  "sub": "123",                    // User ID
  "jti": "uuid",                   // JWT ID for blacklisting
  "iat": 1640995200,              // Issued at
  "exp": 1640998800,              // Expires at
  "roles": ["ROLE_USER"],         // User roles
  "provider": "google",           // Authentication provider
  "email": "user@example.com"     // User email
}
```

### OAuth2 Configuration
```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          google:
            client-id: ${GOOGLE_CLIENT_ID}
            client-secret: ${GOOGLE_CLIENT_SECRET}
            scope: email,profile
        provider:
          google:
            user-name-attribute: sub
```

### Password Security
- **BCrypt Hashing**: 12 rounds for secure password storage
- **Strength Validation**: Comprehensive password policy
- **Reset Token**: 6-digit OTP with 15-minute expiry
- **Email Verification**: Track email verification status

## Integration Points

### Frontend Integration
- **OAuth2 Redirect**: Seamless redirect to Google consent screen
- **Token Storage**: Secure JWT storage in HTTP-only cookies or localStorage
- **Session Management**: Automatic token refresh and logout handling
- **Profile Sync**: Synchronize user profile from Google on login

### Email Service Integration
- **Welcome Emails**: Send welcome email after registration
- **Password Reset**: Send OTP via email for password reset
- **Email Templates**: Professional email templates for all communications
- **Async Processing**: Non-blocking email sending

### Monitoring Integration
- **Actuator Endpoints**: Health checks and metrics
- **Logging**: Structured logging for authentication events
- **Error Tracking**: Comprehensive error handling and logging
- **Performance Monitoring**: Track authentication performance

## Postconditions
- Users can authenticate via email/password or Google OAuth2
- Seamless user registration for first-time Google users
- Secure password reset with email OTP
- Complete audit trail of authentication events
- Scalable user management for enterprise use
- All UC1–UC17 functionality preserved with authenticated access

## Key Concepts
- **OAuth2 Authorization Code Flow** with PKCE
- **JWT-based Stateless Authentication**
- **Refresh Token Rotation** for security
- **Email OTP** for password reset
- **User Profile Management** with OAuth2 integration
- **Security Best Practices** (BCrypt, JWT, HTTPS)
- **Audit Trail** for compliance and monitoring
- **Scalable User Management** for enterprise deployment

🔗 _Code Link:_ 
👉 [UC18 – Google Authentication and User Management](https://github.com/Deepanshukhoushi/QuantityMeasurementApp/tree/feature/UC18-Google-Authentication-and-User-Management)

---

## 🧩 UC21 – Microservices Architecture Implementation

## Description
UC21 evolves the application from a single deployable Spring Boot backend into a **microservices-based system**.  
Core responsibilities are split into independently deployable services with centralized discovery and routing.

## Objective
Enable modular development, independent scaling, and resilient service communication by introducing service registry, gateway routing, and domain-specific services.

## Microservices Introduced
- **Eureka Server (`:8761`)** – Service discovery and registration  
- **API Gateway (`:8080`)** – Single entry point, request routing, cross-cutting filters  
- **Measurement Service (`:8081`)** – Quantity conversion/business logic APIs  
- **User Service (`:8082`)** – User profile/auth/history responsibilities  

## Main Flow
1. Client sends request to API Gateway  
2. Gateway resolves target service via Eureka  
3. Request is routed to Measurement/User service  
4. Services process business logic independently  
5. Optional inter-service communication occurs via declarative clients  
6. Response is returned through Gateway to client  

## Postconditions
- Monolithic responsibilities are split across focused services  
- Services can be built, deployed, and scaled independently  
- Discovery-based routing removes hardcoded service endpoints  
- Foundation is ready for resilience, observability, and containerized deployment  

## Key Concepts
- Microservices decomposition  
- Service discovery (Netflix Eureka)  
- API Gateway pattern (Spring Cloud Gateway)  
- Inter-service communication (OpenFeign style integration)  
- Independent persistence and service boundaries  
- Cloud-native scalability and fault isolation  

🔗 _Code Link:_ 
👉 [UC21 – Microservices Architecture](https://github.com/Deepanshukhoushi/QuantityMeasurementApp/tree/feature/UC21-microservices-architecture)

---

## 🐳 Docker Guide (Implemented for this App)

This repo now includes Docker support for:
- `eureka-server` (`8761`)
- `admin-server` (`9090`)
- `api-gateway` (`8080`)
- `measurement-service` (`8081`)
- `user-service` (`8082`)
- `auth-service` (`8083`)
- `mysql` (`3306`)

Added files:
- `docker-compose.yml` (root)
- `Dockerfile` in each service folder above

### Useful Docker Commands

```bash
docker ps                         # running containers only
docker ps -a                      # all containers (running + stopped)
docker stop <container_id>        # stop running container
docker rm <container_id>          # delete stopped container

docker run -d -p 8080:80 nginx    # run nginx in background
# run = create + start container
# -d  = detached mode
# -p  = host:container port map

docker images                     # list images
docker rmi <image_id>             # delete image
docker system prune -a            # remove unused images/containers/networks
docker logs <container_id>        # inspect container logs
```

### Things to Check Before Docker Run/Deploy

1. Verify `application.yml/properties` (DB URL, ports, service URLs).
2. Ensure Java version in `pom.xml` matches Docker image (`Java 17`).
3. Build JARs before image build.
4. Verify Dockerfiles (JAR copy path + entrypoint).
5. Use `docker logs <container_id>` for troubleshooting.
6. Validate port mapping while running containers.
7. Rebuild images after code changes.
8. Ensure service binding/networking (`SERVER_ADDRESS=0.0.0.0` if needed).

---

## ▶️ Run This Microservices App Locally with Docker

### Step 1: Build all service JARs

```bash
mvn -f eureka-server/pom.xml clean package -DskipTests
mvn -f admin-server/pom.xml clean package -DskipTests
mvn -f api-gateway/pom.xml clean package -DskipTests
mvn -f measurement-service/pom.xml clean package -DskipTests
mvn -f user-service/pom.xml clean package -DskipTests
mvn -f auth-service/pom.xml clean package -DskipTests
```

### Step 2: Build and start containers

```bash
docker compose up --build -d
```

### Step 3: Verify containers

```bash
docker ps
```

### Step 4: Test endpoints

- API Gateway: `http://localhost:8080`
- Eureka Dashboard: `http://localhost:8761`
- Spring Boot Admin: `http://localhost:9090`

### Step 5: Stop all containers

```bash
docker compose down
```

To remove volumes too:

```bash
docker compose down -v
```

---

## ☁️ EC2 Deployment with Docker (Quick Steps)

1. Launch EC2 (Amazon Linux, t2.micro, 8–20 GB).
2. Open Security Group ports: `22`, `8080` (and others if needed).
3. Install Docker:

```bash
sudo yum update -y
sudo yum install docker -y
sudo service docker start
sudo usermod -aG docker ec2-user
docker --version
```

4. Push image(s) to Docker Hub:

```bash
docker tag qm-api-gateway <dockerhub-username>/qm-api-gateway:latest
docker push <dockerhub-username>/qm-api-gateway:latest
```

5. On EC2, pull and run:

```bash
docker pull <dockerhub-username>/qm-api-gateway:latest
docker run -d -p 8080:8080 <dockerhub-username>/qm-api-gateway:latest
```

6. Access app:

`http://<ec2-public-ip>:8080`



---

## ?? Production Deployment (EC2 + Docker)

To deploy the Quantity Measurement App in a production environment (such as AWS EC2 using Docker), ensure the following environment variables are correctly configured in your container environment or .env file. 

### Core Configuration
- **SPRING_PROFILES_ACTIVE**: Set to prod to enable production configurations and startup validation.
- **SERVER_PORT**: The port the service will listen on (default: 8080 for API Gateway, 8083 for Auth).

### Security & CORS
- **APP_OAUTH2_REDIRECT_URI**: The authorized redirect URI for Google OAuth2 (e.g., https://quantitymeasurementapp-frontend-iibq.onrender.com/oauth2/redirect). **Mandatory in production.**
- **APP_CORS_ALLOWED_ORIGINS**: Comma-separated list of allowed origins (e.g., https://quantitymeasurementapp-frontend-iibq.onrender.com). **Mandatory in production.**
- **APP_AUTH_TOKEN_SECRET**: A secure, long string (at least 32 bytes) used to sign JWT tokens.

### Database & Persistence
- **DB_URL**: The JDBC connection string for your production MySQL database (e.g., jdbc:mysql://db-instance-address:3306/quantity_db).
- **DB_USERNAME**: Production database username.
- **DB_PASSWORD**: Production database password.
- **REDIS_HOST**: Hostname of your Redis instance for token blacklisting.
- **REDIS_PORT**: Port of your Redis instance (default: 6379).

### Service Discovery & Integration
- **EUREKA_SERVER_URL**: The URL of your Eureka Discovery Server (e.g., http://eureka-server:8761/eureka/).
- **GOOGLE_CLIENT_ID**: Your Google Cloud Console OAuth2 Client ID.
- **GOOGLE_CLIENT_SECRET**: Your Google Cloud Console OAuth2 Client Secret.
- **MAIL_USERNAME**: SMTP username for email services (Reset Password).
- **MAIL_PASSWORD**: SMTP password or App Password.

### Startup Validation
Each service performs a **Startup Validation** check in the prod profile. The application will **fail to start** if the APP_OAUTH2_REDIRECT_URI or APP_CORS_ALLOWED_ORIGINS are missing or set to localhost values.

