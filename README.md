# Movies aggregator
### Presentation Description/Overview
#### Problem
Before watching a movie many people look at reviews on various resources.  
An average person has several movie sources where he/she checks reviews.  
As a result, a person needs to visit each movie source,  
find a movie that user would like to read about  
and finally visit movie's review page.

Why all movies' reviews can be checked on a single movie source?

1. Movies are rated very differently on various platforms(IMDb, rotted tomatoes, etc)
   based on country, genre, typer  and more.
    * For example, a niche-genre or subgenre movie is better to check  
      on a special platform(eastern movies-kinopoisk, anime-mal, polish movies-filmweb.pl and more).
2. Any single movie source cannot provide full information/reviews on each movie.


#### Solution
A website that gathers reviews from most popular movie sources.  
The application provides brief information of searched movie and provides  
links to the chosen/favorite movie sources' review page.  
As a result, user can save time because all needed reviews are gathered in one place.

## Project Technologies (WIP, more will be added during development)

* Project Design
    * Figma for screen design / wireframes
* Security/Authentication
    * Tomcat's JDBC Realm Authentication
* Database
    * MySQL 8.0.22
* ORM Framework
    * Hibernate 5
* Build Tool & Dependency Management
    * Gradle
* CSS
    * Bootstrap
* Data Validation
    * Bootstrap Validator for front end
* Logging
    * Log4J2
* Hosting
    * AWS
* Used APIs/sercices to gather information
    * [OMDB API](https://www.omdbapi.com/)
    * [Kinopoisk Unofficial Api](https://kinopoiskapiunofficial.tech/)
    * [SPARQL](https://www.wikidata.org/wiki/Wikidata:SPARQL_tutorial)
* Unit Testing
    * JUnit tests to achieve 80%+ code coverage in persistence package
* IDE: IntelliJ IDEA
* Spring Framework
    * Spring Security
    * Spring MVC
    * Spring DI & IoC


## Useful Links
[**Database Design**](designDocs/updated_final_urd.png)  
[**Video Demo of Basic Application Functionality**](https://youtu.be/9wRx-UV7kys)  
[**Link to the journal**](Journal.md)  

