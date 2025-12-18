📘 Personal Expense Tracker (DevOps Project)
📌 Project Overview

The Personal Expense Tracker is a Java Swing–based desktop application that helps users track, store, and analyze daily expenses.
The project follows DevOps principles by using Maven for build automation and Git & GitHub for version control, ensuring a clean, maintainable, and scalable project structure.

🎯 Objectives

Record daily expenses with category-wise classification

Store expense data persistently using CSV files

Generate a final expense summary report

Provide a modern Dark / Light mode UI

Apply DevOps tools for build and source code management

🛠️ Tech Stack & Tools Used
🔹 Programming Language

Java (JDK 17)
Used for application logic and GUI development.

🔹 Frontend (UI Layer)

Java Swing

JFrame, JTable, JButton, JLabel

Event-driven UI handling

Dark / Light mode switching

Category representation using Unicode icons

🔹 Backend / Core Logic

Core Java

File I/O (CSV read/write)

Data aggregation and calculations

Final report generation

🔹 Build Automation (DevOps)

Apache Maven

Standard project structure

Automated build lifecycle

Dependency management

Execution using Maven goals

mvn clean install
mvn exec:java

🔹 Version Control (DevOps)

Git

Source code tracking

Commit history management

GitHub

Remote repository hosting

Collaboration and documentation

🔹 Resource Management

Maven Resource Directory

Background image loading from classpath

Separation of code and assets

📂 Project Structure
personal-expense-tracker
│
├── pom.xml
├── README.md
├── expenses.csv
└── src
    └── main
        ├── java
        │   └── com
        │       └── expensetracker
        │           └── ExpenseTrackerApp.java
        │
        └── resources
            └── images
                └── bg.jpg

⚙️ Features Implemented

➕ Add expenses with amount and category

💾 Save and load expenses using CSV

📊 Generate a final category-wise expense report

🌗 Toggle between Dark and Light modes

🧮 Automatic total calculation

🧱 Clean Maven-based project structure

🚀 How to Run the Project
🔹 Prerequisites

Java JDK 17 or above

Apache Maven

Git

🔹 Steps to Run
git clone git@github.com:shudhanshu002/Devops_project.git
cd personal-expense-tracker
mvn clean install
mvn exec:java

📊 Final Report

The Final Report feature provides:

Category-wise total expenditure

Number of entries per category

Overall total expenses

Displayed using a dialog window for quick analysis.

🧠 DevOps Relevance

This project demonstrates:

Automated builds using Maven

Organized project structure

Version control using Git

GitHub-based source code management

Readiness for CI/CD pipeline integration

📌 Future Enhancements

Custom fonts and advanced UI styling

Graphical analytics (charts)

Export reports to PDF

Dockerization of the application

CI/CD pipeline using GitHub Actions or Jenkins

👨‍💻 Author

Sudhanshu kumar singh
B.Tech CSE Student

📜 License

This project is developed for academic and learning purposes.