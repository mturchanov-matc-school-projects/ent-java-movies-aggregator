<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
          crossorigin="anonymous">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lato">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <title>Document</title>

    <style>
        body,h1,h2,h3,h4,h5,h6 {font-family: "Lato", sans-serif}
        .w3-bar,h1,button {font-family: "Montserrat", sans-serif}
        .fa-anchor,.fa-coffee {font-size:200px}
        img {
            width: 200px;
        }
    </style>
</head>
<body>
<%--<a href="/">Home</a>--%>
<div class="container-fluid">
    <h2>$Result </h2>
    <!-- TODO: add 'ADD/DELETE' functionality to each parsed movie
                 -> js sending request
                 -> if no working then simple mappings in controler
                 -->
    <c:forEach items="${movies}" var="movie">
        <div class="container">
            <ul class="list-unstyled">
                <li class="media w3-padding w3-container w3-round-large w3-light-gray">
                    <img class="mr-3" src="<c:out value="${movie.image}" />" alt="image" />
                    <div class="media-body">
                        <h3 class="mt-0 mb-1"><c:out value="${movie.name}  ${movie.year}" /></h3>
                        <h4 class="mt-0 mb-1"><a href="<c:out value="https://www.imdb.com/title/${movie.imdbId}"/>">imdb reviews</a></h4>
                        <h4 class="mt-0 mb-1"><a href="<c:out value="https://www.kinopoisk.ru/film/${movie.kinopoiskId}/#user-reviews"/>">Kinopoisk reviews</a></h4>
                        <h4 class="mt-0 mb-1"><c:out value="IMDB Rating ${movie.imdbRating}" /> </h4>
                        <h4 class="mt-0 mb-1"><c:out value="Kinopoisk Rating ${movie.kinopoiskRating}" /> </h4>
                        <h4 class="mt-0 mb-1"><c:out value="Metacritic Rating ${movie.metacriticRating}" /> </h4>
                        <h4 class="mt-0 mb-1"><c:out value="rottenTomatoes Rating ${movie.rottenTomatoesRating}" /> </h4>
                       <!-- TODO: delete --> <h4 class="mt-0 mb-1"><c:out value="isAdded  ${movie.addedToUserList}" /> </h4>

                    </div>
                    <sec:authorize access="isAuthenticated()">
                        <c:choose>
                            <c:when test="${!movie.addedToUserList}">
                                <form action="${pageContext.request.contextPath}/addMovie" method="get">
                                    <input type="submit" value="Add">
                                    <input type="hidden" name="movieId" value="${movie.id}" />
                                </form>
                            </c:when>
                            <c:otherwise>
                                <form action="${pageContext.request.contextPath}/deleteMovie" method="get">
                                    <input type="submit" value="Delete">
                                    <input type="hidden" name="movieId" value="${movie.id}" />
                                </form>
                            </c:otherwise>
                        </c:choose>
                    </sec:authorize>


                    <a href="${pageContext.request.contextPath}/movie?id=${movie.id}">
                        <button type="button" class="btn btn-info">More Info</button>
                    </a>
                </li>
            </ul>
        </div>
    </c:forEach>
</div>
</body>
</html>

