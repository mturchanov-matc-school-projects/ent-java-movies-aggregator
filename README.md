# Movies aggregator
### Presentation Description/Overview
#### Problem
Before watching a movie many people look at rating/reviews on various resources. 
For example, I check around 2-3 resources before deciding.

1. Movies are rated very differently on various platforms(IMDb, rotted tomatoes)
2. If you check a niche-genre or subgenre usually it is better to check reviews/rating
on a special platform(e.g eastern movies-kinopoisk, anime-mal, polish movies-filmweb.pl and more).
3. Some unpopular movies often have low information(reviews, votes, etc.) on popular common source movie platforms; 
moreover, such a movie could be absent.

#### Solution
A website that gathers a rating of a searched movie from different movie source platforms + general info(frames,actors, etc) + useful links(reviews)

#### Pages and functionality:
###### Index
* searchbar
* check movies
* add movies to my_list if logged
* delete movies from my_list if logged
* add a note/review regarding a moview

###### Logic on search
1. checks whether such search word is in DB
1.1 if no such word in db then app calls APIs requests. 
   It parses JSONs, adds to DB and display results
1.2 If there is such word then app just gets movie(s) associated the searchword from DB
   
As a result, it will increase loading speed + will record movie info that can be
  used later in REST service

###### Login/Signup
* login/signup to access my movie list

### Supporting Materials
* omdb api - http://www.omdbapi.com/
* unofficail kinopoisk api - https://kinopoiskapiunofficial.tech/
### Journal

[Link to the journal](Journal.md)
