<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<c:set var="title" value="Movie Aggregator - Home" scope="request"/>
<c:import url="includes/head.jsp"/>

<c:import url="includes/header.jsp"/>


<main>
    <div class="container">

        <h1>Find movies quickly!</h1>
        <form action="${pageContext.request.contextPath}/searchMovie" method="get" class="">
            <div class="row">

                <div class="card-header col-md-5">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">Card title</h5>
                            <h6 class="card-subtitle mb-2 text-muted">Card subtitle</h6>
                            <p class="card-text">Some quick example text to build on the card title and make up the
                                bulk of the card's content.</p>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" value="imdb" name="movieSourceBase"
                                       id="sourceBaseImdb" checked>
                                <label class="form-check-label" for="sourceBaseImdb">
                                    IMDB
                                </label>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" value="kinopoisk"
                                       name="movieSourceBase" id="sourceBaseKinopoisk">
                                <label class="form-check-label" for="sourceBaseKinopoisk">
                                    Kinopoisk
                                </label>
                            </div>
                        </div>
                    </div>

                    <hr>
                    <div class="row mb-3">
                        <label for="search">
                            <input type="text" class="form-control form-control-lg" placeholder="Search for movies"
                                   name="searchVal" id="search">
                        </label>
                        <p class="text-warning">${headerTitle}</p>

                        <br>
                    </div>
                    <input type="submit" value="Search">
                </div>


                <div class="col-md-7">
                    <a href="${pageContext.request.contextPath}/showReviewSources">123</a>

                    <hr>
                    <c:choose>
                        <c:when test="${empty allReviewSources}">
                            none
                        </c:when>
                        <c:otherwise>
                            <div class="list-group d-flex justify-content-evenly flex-row flex-wrap">
                                <c:forEach items="${allReviewSources}" var="reviewSource">

                                    <label class="list-group-item list-group-item-action" aria-current="true">
                                        <div class="d-flex w-100 justify-content-between">
                                            <h5 class="mb-1">${reviewSource.name}</h5>
                                            <small>3 days ago</small>
                                        </div>
                                        <p class="mb-1">Some placeholder content in a paragraph.</p>
                                        <input class="form-check-input me-1" name="reviewsSources" type="checkbox"
                                               value="${reviewSource.name}"
                                            <c:if test="${reviewSource.checked == true}"> checked </c:if> />
                                        <small>And some small print.</small>
                                    </label>

                                </c:forEach>
                            </div>
                        </c:otherwise>

                    </c:choose>

                </div>

            </div>
        </form>
    </div>

    </div>


</main>


</body>
</html>