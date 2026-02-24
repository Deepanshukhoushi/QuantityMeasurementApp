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