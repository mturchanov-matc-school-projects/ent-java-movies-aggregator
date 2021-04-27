<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<c:set var="title" value="Movie Aggregator - Home" scope="request" />
<c:import url="includes/head.jsp" />

<c:import url="includes/header.jsp" />
<div id="wrapper" class="container text-center">

    <main class="p-3">

        <sec:authorize access="isAuthenticated()">
            <h1>Welcome Back, <sec:authentication property="name"/></h1>
        </sec:authorize>


        <hr>
        <h2>Search</h2>





        <form action="${pageContext.request.contextPath}/searchMovie"
              method="get">
            <div id="checkboxes" class="text-left">
                <div class="form-check form-check-inline">
                    <input type="radio" class="btn-check" name="movieSourceBase" value="imdb" id="imdbSource" autocomplete="off" checked>
                    <label class="btn btn-outline-success" for="imdbSource">Imdb source</label><br>
                </div>
                <div class="form-check form-check-inline">
                    <input type="radio" class="btn-check" name="movieSourceBase" value="kinopoisk" id="kinopoiskSource" autocomplete="off">
                    <label class="btn btn-outline-success" for="kinopoiskSource">Kinopoisk source(russian results)</label>
                    </label>
                </div>
            </div>


            <hr>
            <div class="row mb-3">
            <label for="search">
                <input type="text" class="form-control form-control-lg" placeholder="Search for movies" name="searchVal" id="search">
            </label>
            <br>
            </div>
            <input type="submit" value="Search">
        </form>
        <hr>
        <p class="text-warning">${headerTitle}</p>


    </main>

</div>
<c:import url="includes/footer.jsp" />
</body>
</html>