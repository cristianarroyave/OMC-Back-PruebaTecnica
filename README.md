# OMC-Back-PruebaTecnica
Back-end for OMC technical test

## First steps
In the src/main/resources/application.properties file you have the string connection to the database, also the user and the password used to connect,
make sure your PostgreSQL is running in the same port and has the same database with the username and password with rights to modify

## Adding data to the DB
In the /resources/ folder there are 2 SQL scripts to create tables and add data to them, it just adds two users, if you'd like to add more users, please feel free.

## Running the server
With the connection to the database made you should be able to connect to http://localhost:8080 and start using the endpoints

##
In the /resources/ folder there are all the postman requests ready to be used

## WebServices
### GET
### `/todos`
Get the first 10 ToDos available
### `/todos/{pageNumber}/{pageLength}`
Get a list of ToDos with pagination pageNumber parameter stands for the number of the page you want to see, and pageLength for the items in each page
### `/todos/{pageNumber}/{pageLenght}/{sortBy}/{sortDir}`
Get a list of ToDos filtered by the field sortBy and the direction specified by the parameter sortDir, also adds pagination like the previous endpoint.
  Expected parameters for sortBy : titulo, completado, usuario, id
  Expected parameters for sortDir: asc, desc
### `/todos/titulo/{title}`
Get a list of ToDos containing the text passed in the parameter title
### `/todos/username/{username}`
Get a list of ToDos of the given username
 
### POST
### `/todos/`
Create a ToDo with, request body example
`{
    "titulo": "Example",
    "usuario": 1,
    "completado" : false
}`
### `/login/`
Get a JWT Token if the log in is successfull, request body example

