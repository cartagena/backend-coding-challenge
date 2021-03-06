Building and running
====

## Dependencies
All you need is Java 8 and a MySQL Server installed.

### Creating the database
In order to create the database, run the following command:

    $> mysql -u root -p < src/main/resources/ddl.sql

### Running the application
All you need to do is run the command:

    $> ./gradlew bootRun
    
Or the following if you are using Windows:
    
    $> ./gradlew.bat bootRun
    
After a few seconds, use your browser and go to http://localhost:8080.
    
#### What happens under the hood
Gradle, the dependency/build tool used, will do the below:

1. Call Gulp build, so any changes in frontend code will be visible.
2. Copy the files generated in step before to `src/main/resources/static` folder.
3. Call Spring Boot plugin so it will compile the java code and start the application

## Running with Docker and Docker compose
If you do not have Java 8 and/or MySQL installed, you can run the application using [Docker](https://www.docker.com/) and [Docker Compose](https://docs.docker.com/compose/). Use the following command from the project folder:

    $> ./gradlew build buildDocker
    $> docker-compose -f src/main/docker/docker-compose.yml up -d
    
In a few seconds, use your browser and go to http://localhost:8080.

---

Goal
====
Produce a simple web-app backend to complement the supplied front-end code.

Mandatory Work
--------------
Fork this repository. Starting with the provided HTML, CSS, and JS, create a Java-based REST API that:

1. Saves expenses as entered to a database.
2. Retrieves them for display on the page. 
3. Add a new column to the table displaying the VAT amount for each expense.
4. Alter the README to contain instructions on how to build and run your app.

VAT is the UK’s sales tax. It is 20% of the value of the expense, and is included in the amount entered by the user.

Give our account `alchemytec` access to your fork, and send us an email when you’re done. Feel free to ask questions if anything is unclear, confusing, or just plain missing.

Extra Credit
------------
Calculate the VAT client-side as the user enters a new expense, before they save the expense to the database.

Questions
---------
##### What frameworks can I use?
That’s entirely up to you, as long as they’re OSS. We’ll ask you to explain the choices you’ve made. Please pick something you're familiar with, as you'll need to be able to discuss it.

##### What application servers can I use?
Anyone you like, as long as it’s available OSS. You’ll have to justify your decision. We use dropwizard and Tomcat internally. Please pick something you're familiar with, as you'll need to be able to discuss it.

##### What database should I use?
MySQL or PostgreSQL. We use MySQL in-house.

##### What will you be grading me on?
Elegance, robustness, understanding of the technologies you use, tests, security. 

##### Will I have a chance to explain my choices?
Feel free to comment your code, or put explanations in a pull request within the repo. If we proceed to a phone interview, we’ll be asking questions about why you made the choices you made. 

##### Why doesn’t the test include X?
Good question. Feel free to tell us how to make the test better. Or, you know, fork it and improve it!
