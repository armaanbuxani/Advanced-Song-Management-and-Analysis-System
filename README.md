# Song Management and Analysis System 

A Java-based application designed to manage and analyze a large dataset of songs. It allows users to perform dynamic queries based on song attributes such as BPM and release year. Built using a custom Red-Black Tree to ensure fast and scalable data operations.

---

## Features

-	Developed a Java application for efficient storage, retrieval, and analysis of a large song dataset.
-	Implemented a custom Red-Black Tree for optimized data management and dynamic queries (e.g., BPM, year).
-	Built a modular backend architecture with interfaces and generics, ensuring extensibility.
-	Authored JUnit tests and incorporated exception handling to enhance functionality and robustness.

---

## Technologies Used

- **Java**
- **JUnit**
- **CSV Parsing**
- **Custom Data Structures** 

---

## How to run

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/song-management-system.git

2. Navigate into the project folder:
   cd song-management-system

3. Ensure that junit5.jar is available in the parent directory

4. Use the Makefile commands:
	To compile and run the application:
		make runApp
	To run backend developer tests:
		make runBDTests
	To run frontend developer tests:
		make runFDTests
	To clean compiled .class files:
		make clean
