# Sample REST API for Money Transfer between accounts

### About
A simple single API implementation. It uses Java 8, Jetty, Jersey, JUnit and Jackson API. This application is packaged into a single jar that will allow users to run the program

### Usage
The target folder has the packaged jar file. Use it to run the API on port number 9000.
java -jar mtransferprj-1.0-SNAPSHOT.jar

### Assumptions
The sample application only implements one API. It also uses a test set of accounts with a local store without connecting to the database.

Test Dataset used for Accounts
````
{id:1, amount:10000.00}
{id:2, amount:12000.00}
{id:3, amount:23000.00}
{id:4, amount:15000.00}
{id:5, amount:13000.00}
{id:6, amount:12000.00}
{id:7, amount:19000.00}
{id:8, amount:16000.00}
````

### Test the API
#### Step 1: Run the Java API
````
java -jar mtransferprj-1.0-SNAPSHOT.jar
````
#### Step 2: Call the API
````
Invoke the URL on Postman or other client using any of the sample payloads http://localhost:9000/transfer
````
#### Sample Payloads: 
````
Sample payload for the request for a valid transfer
  {
    "senderAccountId":"1",
    "receiverAccountId":"2",
    "amount":"120.00"
  }

Sample payload for the request for a insufficient funds
  {
    "senderAccountId":"1",
    "receiverAccountId":"2",
    "amount":"12000.00"
  }

  Sample payload for the request for an invalid transfer
  {
    "senderAccountId":"1",
    "receiverAccountId":"19",
    "amount":"120.00"
  }
````
### Possible Improvements
* Having a configuration file to be able to tweak the port number API is running on.

* Using a data file to feed the API with sample account data

* Implement more API for balance enquiry

* Accept currency type to transfer the money

* Currently only wrote functional tests. Extend it to write REST API tests.

* Better packaging structure


