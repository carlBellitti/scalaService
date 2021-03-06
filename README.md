## General Info

- Scala service that contains routes and interfaces locally with MySql

- It uses the akka Actor system to keep track of data entities (in this example it's users)

- Note that when the server initializes, and the UserRegistryActor class initializes, a call to the database is made to update the users in the Actor model.  So that when the client makes a request to get all of the users, or filter the users, the users have already been retrieved and exist in the Actor 'model'.

### Database Setup

- Set up a local mySQL server on your computer. For the Mac it is under Settings, if you do not have one, you can download.
- Be sure to disconnect from any other local sql instances (like the 'memberchange' - Docker instance)
- Be sure to start your local SQL instance
- SQL settings are in the 'resources/application.conf' file.  Things like username, password, database name etc.  Database name is 'carl' - obviously can be changed.
- The table that the app looks for is 'users' with 4 fields (id: int, name: text or varchar, age: int, and countryOfResidence: text or varchar)

### Routes
- There are multiple routes pre-configured for adding users, deleting etc.
- However, I have only focused on one that is 'http://localhost:8080/users', and that is being leveraged to retrieve all of the users.
- An additional route was added to serve static files (html) - 'http://localhost:8080/client' - this should display the index.html page that is in the 'resources/client' folder

### Serving Static Files
- Static files are served using the .../client' route.  Files are located in the 'resources/client' folder.  What is interesting, is that if you modify the static file (like index.html), you only have to re-run the service, not re-compile.

### Running
- sbt compile
- sbt run

### Testing
- Set up your local database.
- Populate the 'users' table as described above
- Open a browser and navigate to 'http://localhost:8080/users', and you should see your data

### Notes
- The 'master' branch is basically the akka template with the addition of mysql (instead of h2) - this currently compiles and works - assuming you have a local sql database set up as described above
- The 'refactor-test' branch is under construction - and more closely organized to how some of the newer maxwell Scala projects
- The UserRegistry actor is treated as the model and moved to the 'root' of the project (renamed to 'users')
- The properties of 'User' were changed to custom properties (not primitives).
- Because of the custom 'User' properties, codecs were created to convert the model 'User' to and from 1) a database object (UserRow and UserRows) and 2) a marshallable JSON object with primitives (UsersJson and UsersJson)

### Issues with the 'refactor-test' branch
- The re-arranging of the files has introduced some errors - particularly related to the Spray JSON implementation

