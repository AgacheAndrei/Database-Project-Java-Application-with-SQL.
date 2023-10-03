# Database Project -- Java Application with SQL
## Project for univeristy

## The goal of the app
<p>
 The application developed addresses the issue of managing both scholarship and non-scholarship students within a high school.
</p>

## Analyze problems

<p>
A high school is a general education institution where graduates of primary schools are admitted, and where the knowledge required for further studies in higher education or various vocational fields is taught.
A student is considered a scholarship recipient if they meet certain conditions imposed by the state and the educational institution they are a part of. According to the current legislation, there can be several types of scholarship recipients:
Please note that all scholarships require a minimum overall grade that must be exceeded to enter the scholarship application process. These minimum grades vary widely depending on the type of scholarship and current legislation.
</p>
<pre>
- Merit Scholars: These students have an overall grade above a minimum set by the high school and the Inspectorate for Education in the city where the high school is located.
- Olympic Scholars: These are students who have achieved outstanding results at national or international levels in various olympiads.
- Social Scholarships are divided into different categories based on the challenges faced by a student. These categories include scholarships for students who are orphaned by one or both parents, students from rural areas, students facing difficult situations, and students with serious health issues or disabilities.
</pre>
<p>
Scholarships represent compensation that varies depending on the type of scholarship and the current legislation, and it is given to the student or their guardian during the validity period of the scholarship application.
There are scholarships that last for:
</p>
<pre>
- 6 months, such as academic scholarships like merit scholarships and Olympic scholarships.
- 12 months, such as social scholarships in the categories mentioned above.
</pre>
For example, a merit scholarship can reach up to 600 lei per month.
<p>
All types of scholarships require different types of documents to be submitted by students in a dossier, and after the evaluation process, the student finds out whether they will be on the list of scholarship recipients. For example, a merit scholarship requires a copy of the ID card, a copy of the student's transcript, and other documents that are already available at the high school, such as a copy of the birth certificate.
Each student receives a specific code for the documents they submit for a particular scholarship, which helps in the quick identification of the desired scholarship type. The secretariat can search for students much more quickly and easily based on this code.
For example, the code "CICNFM" is for a student applying for a merit scholarship.
</p>
<pre>
The main entities involved in the student management system are:
- The Student: Identified by:
  - Registration Number
  - Last Name
  - First Name
  - Personal Identification Number (CNP)
  - Other details such as address, phone number, overall grade, etc.
- The Administrator: Identified by:
  - An identifier received from the high school (authentication code, email, etc.)
  - Password
  - Role - What they are responsible for
- The Secretary: Identified by:
  - An identifier received from the high school (authentication code, email, etc.)
  - Password
  - Role - What they are responsible for
- The Type of Scholarship: Identified by:
  - Affiliated code
  - Set of rules for obtaining
  - Name
  - Required documents
  - Value
  - Duration
</pre>

## Interaction Module
<p>
The Administrator is the person responsible for managing students, modifying, altering, or deleting student data, as well as removing students from various systems, files, and lists that the high school maintains.
The Secretary is responsible for managing student records, financial records, creating various reports required by the educational institution, and organizing students into different categories (scholarship type, class, year of study, etc.).
The Student is the individual for whom documents and records are managed.
The Type of Scholarship represents the rules for approving scholarship applications, the duration of the scholarship, the value, and the eligible individuals for specific scholarships.
</p>
In addition to these main entities, there are some ancillary entities that play roles in categorizing students into multiple categories, such as:
<pre>
Classes:
 -Class Name
 -Start Year of the educational cycle
 -Specialization
 -Schedule
 -Specializations:
Name:
 -Duration
 -Subjects studied
 -Specification of the in-depth area
Profiles:
 -Name
 -General area of subjects studied
</pre>

## Code solution
<pre>
Application with a Java Swing interface for high school student management, data retrieval done in Java, SQL database.
Features:
- Adding new students to the system as scholarship or non-scholarship students.
- Modifying student data.
- Deleting students from the school's database.
- Searching for a student to analyze their data.
- Listing data from the database.
- Filtering data based on various fields in the database and displaying it.
- Generating graphs with data from various critical reports from different tables.
- Generating reports with essential information for the school.
- Maintaining an activity history in the database.
- Exporting reports in Excel format.
- Exporting tables in Excel format.
- Importing tables from Excel format.
- User authentication with field validation.
- Login and sign-out without closing the application.

Roles:
-Administrator:
  - Adding new students to the system as scholarship or non-scholarship students.
  - Modifying student data.
  - Deleting students from the school's database.
  - Listing data from the database.
  - Generating graphs with data from various critical reports from different tables.
  - Exporting tables in Excel format.
  - Analyzing the history of activities in the database.

-Secretary:
  - Searching for a student to analyze their data.
  - Listing data from the database.
  - Filtering data based on various fields in the database and displaying it.
  - Generating graphs with data from various critical reports from different tables.
  - Generating reports with essential information for the school.
  - Exporting reports in Excel format.
  - Importing student data from Excel format.

It includes essential features for data management, reporting, and user interaction.
</pre>
### Programing languages and technology used
<img align="left" width="40px" style="padding-right:10px" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original-wordmark.svg" /> 
<img align="left" width="40px" style="padding-right:10px" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/mysql/mysql-original-wordmark.svg" /> 
<img align="left" width="40px" style="padding-right:10px" src="https://github.com/AgacheAndrei/Database-Project-Java-Application-with-SQL./assets/36128809/a70ec2cd-1130-4719-ab97-0f7d0a67b843" /> 

<br>

### Data base - concept
![Picture1](https://github.com/AgacheAndrei/php-website-for-student-management/assets/36128809/38608ac0-f09d-4c72-a4c4-5cc636508c27)

### How to use it
