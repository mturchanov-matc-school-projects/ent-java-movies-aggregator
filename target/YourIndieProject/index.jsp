<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<c:set var="title" value="Movie Aggregator - Home" scope="request"/>
<c:import url="includes/head.jsp"/>

<c:import url="includes/header.jsp"/>
<a href="#content">
    <button onclick="topFunction()" id="myBtn" title="Go to top">Top</button>
</a>

<main>
    <div class="container" id="content">

        <h1>Find movies quickly!</h1>
        <form action="${pageContext.request.contextPath}/searchMovie" method="get" class="">
            <div class="row">

                <div class="card-header col-md-12 " id="mainFunc">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">Search the movie</h5>

                            <p class="card-text">Application has two movie sources on choice:</p>
                            <ol>
                                <li>IMDB movies source</li>
                                <li>Kinopoisk movies source. Caution, results will be in <span class="text-danger">russian</span>.
                                </li>
                            </ol>

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

                    <div class="container-fluid">
                        <div id="movieSearch" class="row mt-2">
                            <div class="mb-3 col-md-8">
                                <label for="search" style="display: block;">
                                    <input type="text" class="form-control form-control-lg"
                                           placeholder="Search for movies"
                                           name="searchVal" id="search">
                                </label>
                                <p class="text-warning">${headerTitle}</p>
                            </div>
                            <div class="col-md-4 text-center">
                                <button class="btn btn-outline-success btn-lg" type="submit" value="Search">Search
                                </button>
                            </div>
                        </div>
                    </div>

                    <hr>
                    <div class="container-fluid extra-info tables-adjust">
                        <div class="row">
                            <div class="col-4 text-center">
                                <button id="topSearchesBtn" type="button" class="btn btn-outline-info">Show top
                                    searches
                                </button>
                            </div>

                            <div class="col-4 text-center">
                                <button id="topRevsBtn" type="button" class="btn btn-outline-info text-center">
                                    Top review sources
                                </button>
                            </div>
                            <div class="col-4 text-center">
                                <button id="reviewSourcesBtn" type="button" class="btn btn-outline-success btn-lg">
                                    Show review sources
                                </button>
                            </div>

                        </div>
                        <div class="container-fluid extra-info tables-adjust">
                            <div class="row">
                                <div class="col-6-sm" style="width: auto;">
                                <table id="topSearches"
                                       class="table caption-top table-secondary extraInformation ">
                                    <caption>Top searches</caption>
                                    <thead class="table-dark">
                                    <tr>
                                        <th scope="col">Name</th>
                                        <th scope="col">Number</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${topSearches}" var="search">
                                        <tr>
                                            <td>
                                                <a href="${pageContext.request.contextPath}/searchMovie?searchVal=${search.name}&movieSourceBase=imdb">${search.name}</a>
                                            </td>
                                            <td>${search.number}</td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                                </div>
                               <!-- <div class="col-2"></div> -->
                                <div class="col-6-sm tables-adjust" style="width: auto;"">
                                <table id="topRevs" class="table caption-top table-secondary extraInformation col-md-6">
                                    <caption>Top review sources</caption>
                                    <thead class="table-dark">
                                    <tr>
                                        <th scope="col">Review source</th>
                                        <th scope="col">In use</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${sessionScope.topRevs}" var="rev">
                                        <tr>
                                            <td><c:out value="${rev.key}"/></td>
                                            <td><c:out value="${rev.value}"/></td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
</div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-md-8 extraInformation" id="reviewSources">
                    <c:choose>
                        <c:when test="${empty allReviewSources}">
                            none
                        </c:when>
                        <c:otherwise>
                            <div class="text-center py-2" id="checkboxBtns">
                                <button id="checkAllBtn" type="button" class="btn btn-outline-success mx-1">Check All
                                </button>
                                <button id="resetAllBtn" type="button" class="btn btn-outline-danger mx-1">Reset
                                </button>

                            </div>

                            <div class="list-group d-flex justify-content-evenly flex-row flex-wrap ">
                                <c:forEach items="${allReviewSources}" var="reviewSource">
                                    <label class="list-group-item list-group-item-action my-2" style="width: 50%;"
                                           aria-current="true">
                                        <div class="d-flex w-100 justify-content-between">
                                            <h5 class="mb-1">${reviewSource.fullName}</h5>
                                            <small>${reviewSource.feature}</small>
                                        </div>
                                        <p class="mb-1">${reviewSource.description}</p>
                                        <div class="d-flex bd-highlight align-items-stretch">
                                            <div class="me-auto p-2 bd-highlight">
                                                <input class="form-check-input me-1" name="reviewsSources"
                                                       type="checkbox"
                                                       value="${reviewSource.name}"
                                                        <c:if test="${reviewSource.checked == true}"> checked </c:if> />
                                            </div>
                                            <div class="bd-highlight"></div>
                                            <div class=" bd-highlight">
                                                <small><img class="revImg" src="${reviewSource.icon}"
                                                            alt="${reviewSource.fullName} icon"></small>

                                            </div>
                                        </div>
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