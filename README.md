# OMC-Back-PruebaTecnica
Back-end for OMC technical test

## First steps
In the src/main/resources/application.properties file you have the string connection to the database, also the user and the password used to connect,
make sure your PostgreSQL is running in the same port and has the same database with the username and password with rights to modify

## Adding data to the DB
In the /resources/ folder there are 2 SQL scripts to create tables and add data to them, it just adds two users, if you'd like to add more users, please feel free.

## Running the server
With the connection to the database made you should be able to connect to http://localhost:8080 and start using the endpoints

## WebServices
### /GET
### `todos`
