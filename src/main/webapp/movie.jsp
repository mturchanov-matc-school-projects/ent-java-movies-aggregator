<!doctype html>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">

<c:set var="title" value="Movie - ${movie.name}" scope="request" />
<c:import url="includes/head.jsp" />
<body>
<c:import url="includes/header.jsp" />

<div class="container bg-white" id="wrapper">

<h2>full movie info</h2>
<table>

    <tr>
        <th>id</th>
        <td>${movie.id}</td>
    </tr>
        <tr>
            <th>image</th>
            <td>${movie.image}</td>
        </tr>
    <tr>
        <th>name</th>
        <td>${movie.name}(${movie.easternName})</td>
    </tr>
        <tr>
            <th>year</th>
            <td>${movie.year}</td>
        </tr>
    <tr>
        <th>description</th>
        <td>${movie.description}</td>
    </tr>
    <tr>
        <th>duration</th>
        <td>${movie.duration}</td>
    </tr>
    <tr>
        <th>genre</th>
        <td>${movie.metascore}</td>
    </tr>
    <tr>
        <th>director</th>
        <td>${movie.director}</td>
    </tr>
    <tr>
        <th>actors</th>
        <td>${movie.actors}</td>
    </tr>
    <tr>
        <th>country</th>
        <td>${movie.country}</td>
    </tr>
    <tr>
        <th>imdbRating</th>
        <td>${movie.imdbRating}${movie.imdbVotes}</td>
    </tr>
    <tr>
        <th>kinopoiskRating</th>
        <td>${movie.kinopoiskRating}(${movie.kinopoiskVotes})(</td>
    </tr>
    <tr>
        <th>metacriticRating</th>
        <td>${movie.metacriticRating}</td>
    </tr>
    <tr>
        <th>rottenTomatoesRating</th>
        <td>${movie.rottenTomatoesRating}</td>
    </tr>
    <tr>
        <th>filmAffinityRating</th>
        <td>${movie.filmAffinityRating}</td>
    </tr>
        <tr>
            <th>boxOffice</th>
            <td>${movie.boxOffice}</td>
        </tr>
        <tr>
            <th>theMovieDbRating</th>
            <td>${movie.theMovieDbRating}</td>
        </tr>
        <tr>
            <th>tV_comRating</th>
            <td>${movie.tV_comRating}</td>
        </tr>
        <tr>
            <th>metascore</th>
            <td>${movie.metascore}</td>
        </tr>
</table>

<c:import url="includes/footer.jsp" />
</div>
</body>
</html>