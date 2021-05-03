<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<c:set var="title" value="Movies" scope="request" />
<c:import url="includes/head.jsp" />

<body>
<c:import url="includes/header.jsp" />

<div class="container" id="wrapper">
    <h2>$Result </h2>
<c:forEach items="${movies}" var="movie">
    <div class="card mb-3">
        <div class="row">
            <div class="col-md-4">
                <c:choose>
                    <c:when test="${movie.imdbPoster == 'N/A'}">
                        <img class="mr-3 movieImg" src="https://user-images.githubusercontent.com/24848110/33519396-7e56363c-d79d-11e7-969b-09782f5ccbab.png"
                             alt="image ${movie.engName}" />
                    </c:when>
                    <c:when test="${movieSourceBase == 'imdb'}">
                        <img class="mr-3 movieImg" src="<c:out value="${movie.imdbPoster}" />" alt="image ${movie.engName}" />
                    </c:when>

                </c:choose>
            </div>
            <div class="col-md-7 text-center">
                <div class="card-body">
                    <h5 class="card-title">${movie.engName} (${movie.year})</h5>
                    <p class="card-text">${movie.imdbDescription}</p>
                    <a href="https://www.imdb.com/title/${movie.imdbId}/reviews?ref_=tt_ql_3">IMDb reviews</a>
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
                    <br>

                </div>
            </div>
        </div>
    </div>
</c:forEach>

</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js" integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script>

</body>
</html>

