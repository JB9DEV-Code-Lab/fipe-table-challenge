# FIPE Table challenge

This repository was created for studying purposes where I implement a challenge from a Spring Boot Alura course focused
on build a web API application using the framework.

The challenge itself requires to request [FIPE API](https://deividfortuna.github.io/fipe/) and handle it responses
in a CLI application using Spring Boot, to list down the price of a vehicle based on its type, brand, model and year. To
get this information about the user preferences, it's proposed to use the standard system input and create a classic CLI.

## Dependencies

The project is using Maven to handle the dependencies using mainly Jackson to deserialize the response into DTOs.

## My implementation

For these kind of challenges I use to implement everything on my way and then at the end I just compare with what I did
and, sometimes I adapt some things from the course / teacher implementation.

### Presenters

My main idea here was separating each part of the questions for a separeted presenter, which knows what should be asked
and is responsible for returning a string from the `show` method, with the answer for their question. In this case, I
separeted the presenters by:

#### IntroductionPresenter

It's responsible for showing the application welcome message and asking for the vehicle type, to then return it to who
ever use it, because the vehicle type it's the very first thing it's required to know to start the API flow.

#### BrandPresenter

It's responsible for asking if the user has a favorite brand, to use it for the next step, if it's found from the brands
API response. If the user favorite brand isn't in the API response, it filters by the first letter and asks if one of
these brands is the one the user should want. If not, it lists all brands got from the API response and asks the user
which one the user wants. If the user mistype it of write anything that doesn't match with any brand from the list, the
application keep asking it until one of the brands is correctly chosen.

#### ModelPresenter

If kind of does the same that the brand does, focused on the vehicle model though.

### Services

I created some specialized services to abstract and get it easy to do the things, because doing it everywhere it's
required, should be too much repetitive.

#### RequestAPIService

Abstracts HTTP requests using the `java.net.http` API, but using the Restful API approach. This way it gets easy to
make different API requests using a much easier API in the application. Take a look at `RequestFipeApiService`, to see
an example of its usage.

#### RequestFipeApiService

It's responsible for abstracting the different resources the FIPE API has by implementing the `RequestAPIService` to get
it easy to be used on each step of the application.

#### JsonSerializerService

It's responsible for deserializing from a string (usually raw data from the API response) into a given DTO class.

### Utils

The idea for this package was putting here all reusable code, which isn't necessarily a service, just as a faster way to
use standard java utilities.

#### Reader

It's basically a wrapper for the Scanner class, with some prints with it, to ask things in a standardized way.

### Java standard way

Besides all that I used DTOs, enums and custom exceptions as commonly used in Java applications to organize and use the
object-oriented programming concepts.

### To do list
     
- steps
- [x] call presenter introduction, to give a small introduction about the application
- [x] call reader to ask which type of vehicle the person wants to search for
- [x] call reader to ask if there is any specific brand that the person wants to filter (brand name if yes, blank if no)
  - if the brand isn't in the list return by the API
    - [x] call filter to get only the brands with the same first letter
    - [x] call presenter to list them
  - if there is no brand with the same first letter
    - [x] call presenter to show all brands
      - if there is
        - [x] call presenter to show them
        - [x] call reader to ask if one of them should be the one or if the person wants to list all of them
        - [x] if that is the case, call reader to ask which branch the person wants to search for
- [x] call reader to ask which of the models the person wants to search for
- [ ] call presenter to list all models and years that matches the choices
- DTOs
- [x] brands
- [x] model
- [ ] vehicles
- services
- [x] fetch data from an API
- [x] desirialize from DTO
- [ ] serialize to DTO
- presenters
- [x] introduction
- [x] brand
- [x] model
- [ ] vehicle
