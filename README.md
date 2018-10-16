# scalaService
Scala service that contains routes and interfaces locally with MqSql

It uses the akka Actor system to keep track of data entities (in this example it's users)

Note that when the server initializes, and the UserRegistryActor class initializes, a call to the database is made to update the users in the Actor model.  So that when the client makes a request to get all of the users, or filter the users, the users have already been retrieved.

Database Setup

Set up a local mySQL server on your computer. For the Mac it is under Settings, if you do not have one, you can download.

SQL settings are in the 'resources/application.conf' file.  Things like username, password, database name etc.

Database name is 'carl' - obviously can be changed.

The table that the app looks for is 'users' with 4 fields (id: int, name: text or varchar, age: int, and countryOfResidence: text or varchar)

Routes:
There are multiple routes pre-configured for adding users, deleting etc.  However, I have only focused on one that is 'http://localhost:8080/users'


