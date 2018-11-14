# GotCamel


# Description

This application's goals are to send requests and receive responses through Apache Camel. It's possible to request information about movies, actors, actresses, directors and other members of the movie cast and crew. This was developed with learning purposes, in order to study more about Apache Camel, ActiveMQ and Spring Boot.
The requests are made using "The Movie Database"© (https://www.themoviedb.org/) API.

# Stack

In order to run this application you will need an Java IDE with Maven and the following dependencies:

*    spring-boot-starter-activemq
*    spring-boot-starter-data-jpa
*    camel-spring-boot
*    spring-web
*    jackson-datatype-jsr310
*    spring-boot-starter-test
*    camel-jackson
*    jackson-databind
*    spring-boot-configuration-processor
*    lombok

And it is necessary to create an application-prod.yml file at resources package with the following content:

    spring:
      activemq:
        user: yourUser
        password: yourPassword
    
    com:
      gotcamel:
        apiKey: yourAPIKey
    	
To get an API key create a free account at The Movie Database (https://www.themoviedb.org/).


# Road Map
In order to create a new route follow the steps ahead:

    1) Creating the service
	    1.1) Create a new String relative to your Service URL at GotCamelConfiguratioServices;
    	1.2) In application.yml in resources package, at services, put the value relative to the String URL you just created;
    	1.1) Create a new Service class extending AbstractRequestService relative to the new route, and implement the abstract methods.
    	1.2) Override the AbstractRequestService getUrl and getParams methods in order to satisfy the new route url.
    	1.3) Create the method in the new service to get the required data, it's parameters should be the identification header and the headers.
    	1.4) this last method should return a Response of the type requested.
    2) Creating the route 
    	2.1) In SearchType class enum, add the new value relative to the new route
    	2.2) Create a new ComposablePredicate at Predicates class in order to check the new route;
    	2.3) Go to the MovieApiRoute and add to the choice the new option to the route with the following pattern:
    		.when(isFindYourRoute())
    			.bean(yourRouteService,"getYourRoute").id("getYourRouteServiceBean")
    		.endChoice()
    		
You can follow the examples that are already done at the project.

# Unit Tests

To create a unit test do as follow:
1) Create a Response of the type you are requesting like the following example:

        Response<Movie> returned = sendMessage(
                configuration.getRoutes().getInbound().getMovie(),
                Request.<TypeValueData>builder().body( TypeValueData.builder().type(SearchType.CASTMOVIEID).value("1911").build() ).user("testuser").build(),
                NO_HEADERS,
                (new TypeReference<Response<Movie>>(){})
        );
		
		change getMovie(), SearchType.CASTMOVIEID and value("1911") in order to fulfill your request.
		
2) Write the assertions to confirm that the returned information is right.


