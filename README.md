Documentation

To start the application, you have to run docker compose up -d in the resources/docker package. Flyway will then create the initial structure to ensure the database is configured correctly and you will receive a user with the username/email admin@admin.com.Instructions on how to access endpoints in Postman can be found below in this document.

When it comes to project implementation, I think I was able to successfully implement the functionality.The most challenging part of the exam was the implementation of integration and end-to-end testing. After all, I have good code coverage and some integration and end-to-end testing.

Endpoints in AuthenticationController
- @Requestmapping(“/api/authentication”)
- @GetMapping(“/all”)
- @PostMapping(“/create”)
- @PostMapping(“/add”)

Endpoints in ShelterController
- @RequestMapping(“/api/shelter”)
- @GetMapping(“/all”)
- @GetMapping(“/{id}”)
- @PostMapping(“add-animal”)
- @PutMapping(“/update-animal”)
- @DeleteMapping(“/delete-animal/{id}”)

Endpoints in UserController
- @RequestMapping(“/api/user”)
- @PostMapping(“/register”)
- @GetMapping(“/all”)

You can login to the application using the loginform in the browser or with postman, use following syntax in a post request to login with postman:
http://localhost:8080/api/authentication
{
"email": "admin@admin.com",
"password": "pirate"
}

to register a new user make a post request to:
http://localhost:8080/api/user/register
{
"email": "admin@svendsen.com",
"password": "pirate"
}


In order to list all users send a get request to:
http://localhost:8080/api/user/all

To get all authorities send a get request to:
http://localhost:8080/api/authentication/all

To create an authority send a post requset to:
http://localhost:8080/api/authentication/create
{
"authorityName": "SHELTEREMPLOYEE"
}
To grant a user authorities send a post request to:
http://localhost:8080/api/authentication/add
{
"email": "admin@loen.com",
"authorityName": "WORKER"
}

To add a new animal send a post request to:
http://localhost:8080/api/shelter/add-animal
{
"name": "caty",
"type": "cat",
"breed": "catycat",
"age": 102,
"health": "mentalyill"
}

To show all animals in shelter send a get request:
http://localhost:8080/api/shelter/all

To show only one animal from the shelter send a get request:
http://localhost:8080/api/shelter/10

To update a animal send a put request to:
http://localhost:8080/api/shelter/update-animal
{
"id": "6",
"name": "laro",
"type": "cat",
"breed": "unknown",
"age": 17,
"health": "ill"
}

To delete an animal send a delete request:
http://localhost:8080/api/shelter/delete-animal/9
