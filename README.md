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

# 📏 UC2 – Feet and Inches Measurement Equality

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