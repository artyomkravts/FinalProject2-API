## Project: Automated API Tests for Stellar Burgers Website
URL: https://stellarburgers.nomoreparties.site/

### Technology Stack
- Java
- JUnit
- Rest-Assured
- Maven
- Allure
- Hamcrest Matchers
- Java Faker, RandomStringUtils 
- POM (Page Object Model) 
- POJO

### Main Test Cases
- User registration and authentication
- Updating user data
- Order creation with ingredient validation
- Status code and API response verification

### Installation and Execution
1. Clone the repository:  
*git clone https://github.com/artyomkravts/FinalProject2-API.git*   
2. Navigate to the project folder:  
*cd <project folder name>*   
3. Build the project:  
*mvn clean install*
4. Run the tests:  
*mvn clean test*
5. Generate the report:   
*mvn allure:report*   
(or view the pre-generated report in the site folder > index.html)
