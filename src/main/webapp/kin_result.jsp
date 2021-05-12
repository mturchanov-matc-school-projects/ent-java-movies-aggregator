<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<c:set var="title" value="Movies" scope="request" />
<c:import url="includes/head.jsp" />

<body>
<c:import url="includes/header.jsp" />

<div class="container" id="wrapper">
    <h2>${resultTitle}</h2>
    <c:forEach items="${movies}" var="movie">
        <div class="card mb-3">
            <div class="row">
                <div class="col-md-4">
                    <c:choose>
                        <c:when test="${movieSourceBase == 'imdb'}">
                            <img class="mr-3 movieImg" src="<c:out value="${movie.imdbPoster}" />" alt="image ${movie.engName}" />
                        </c:when>
                        <c:when test="${movieSourceBase == 'kinopoisk'}">
                            <img class="mr-3 movieImg" src="<c:out value="${movie.kinopoiskImage}" />" alt="image ${movie.rusName}" />
                        </c:when>
                        <c:when test="${movie.imdbPoster != null}">
                            <img class="mr-3 movieImg" src="<c:out value='${movie.imdbPoster}' />" alt="image ${movie.engName}" />
                        </c:when>
                        <c:when test="${movie.kinopoiskImage != null}">
                            <img class="mr-3 movieImg" src="<c:out value="${movie.kinopoiskImage}" />" alt="image ${movie.rusName}" />
                        </c:when>
                    </c:choose>
                </div>
                <div class="col-md-7 text-center">
                    <div class="card-body">

                        <h5 class="card-title">${movie.engName} (${movie.year})</h5>
                        <c:when test="${movieSourceBase == 'imdb'}">
                            <p class="card-text">${movie.imdbDescription}</p>
                            <a href="https://www.imdb.com/title/${movie.imdbId}/reviews?ref_=tt_ql_3">IMDb reviews</a>
                        </c:when>
                        <c:when test="${movieSourceBase == 'kinopoisk'}">
                            <p class="card-text">${movie.kinopoiskDescription}</p>
                            <a href="https://www.kinopoisk.ru/film/${movie.kinopoiskId}/reviews/?ord=rating">Kinopoisk reviews</a>
                        </c:when>
                        <sec:authorize access="isAuthenticated()">
                            <c:choose>
                                <c:when test="${movie.addedToUserList == false}">
                                    <form class="mt-3 mx-auto" action="${pageContext.request.contextPath}/addMovie" method="get">
                                        <button type="submit" class="btn btn-outline-success">Add</button>
                                        <input type="hidden" name="movieId" value="${movie.id}" />
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <form class="mt-3 mx-auto" action="${pageContext.request.contextPath}/deleteMovie" method="get">
                                        <button type="submit" class="btn btn-outline-danger">Delete</button>
                                        <input type="hidden" name="movieId" value="${movie.id}" />
                                    </form>
                                </c:otherwise>
                            </c:choose>
                        </sec:authorize>
                        <br>
                        <a href="${pageContext.request.contextPath}/movie?id=${movie.id}&movieSourceBase=${movieSourceBase}">
                            <button type="button" class="btn btn-outline-primary">More Info</button>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>

</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js" integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script>

</body>
</html>

