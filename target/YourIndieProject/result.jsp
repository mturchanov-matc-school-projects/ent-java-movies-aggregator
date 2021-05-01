<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<c:set var="title" value="Movies" scope="request" />
<c:import url="includes/head.jsp" />

<body>
<c:import url="includes/header.jsp" />

<div class="container-fluid" id="wrapper">
    <h2>$Result </h2>
    <c:forEach items="${movies}" var="movie">
        <div class="container">
            <ul class="list-unstyled">
                <li class="media w3-padding w3-container w3-round-large w3-light-gray">
                    <c:choose>
                        <c:when test="${movieSourceBase == 'imdb'}">
                            <img class="mr-3 movieImg" src="<c:out value="${movie.imdbPoster}" />" alt="image" />
                        </c:when>
                        <c:when test="${movieSourceBase == 'kinopoisk'}">
                            <img class="mr-3 movieImg" src="<c:out value="${movie.kinopoiskImage}" />" alt="image" />
                        </c:when>
                    </c:choose>
                    <div class="media-body">
                        <h3 class="mt-0 mb-1"><c:out value="${movie.engName}  ${movie.year}" /></h3>
                        <h4 class="mt-0 mb-1"><c:out value="${movie.rusName}" /></h4>
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


                    <a href="${pageContext.request.contextPath}/movie?id=${movie.id}&movieSourceBase=${movieSourceBase}">
                        <button type="button" class="btn btn-info">More Info</button>
                    </a>
                </li>
            </ul>
        </div>
    </c:forEach>

</div>

</body>
</html>

