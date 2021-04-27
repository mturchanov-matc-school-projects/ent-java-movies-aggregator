<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<c:set var="title" value="Movie - ${movie.engName}" scope="request"/>
<c:import url="includes/head.jsp"/>
<body>
<c:import url="includes/header.jsp"/>
<a href="#wrapper">
    <button onclick="topFunction()" id="myBtn" title="Go to top">Top</button>
</a>

<div class="container-fluid bg-white" id="wrapper">
    ${reviewSource.metacritic}

    <h2>${movie.engName}</h2>

    <div id="movieInfo" class="row mb-3">
        <div class="col-lg-4 col-sm-12 text-center" id="movieImage">
            <img src="${movie.kinopoiskImage}" alt="image ${movie.engName}">
        </div>

        <div class="col-lg-8 col-sm-12">
            <div class="row" id="movieTitleDescInfo">
                <div class="card">
                    <div class="card-header">

                    </div>
                    <div class="card-body">
                        <c:if test="${movie.engName != null} ">
                            <h5 class="card-title">English name: ${movie.engName}</h5>
                        </c:if>
                        <h5 class="card-title">Russian name: ${movie.rusName}</h5>

                        <p class="card-text">
                            ${movie.imdbDescription}
                        </p>
                    </div>
                </div>
            </div>
            <hr>
            <c:if test="${movie.imdbRating != null} && ${movie.kinopoiskRating != null}">
                <div class="row" id="imdbKinopoiskRatings">
                    <c:if test="${movie.imdbRating != null}">
                        <div class="col-md-5 col-sm-12" class="mainRatingVal">
                            <img class="sourceIcon" src="resources/images/imdb_icon.svg" alt="imdb-icon">
                            <span class="mainSourcesRatingValue">${movie.imdbRating}</span>/10 (${movie.imdbVotes}) <br>
                            <a href="https://www.imdb.com/title/${movie.imdbId}/reviews?ref_=tt_urv" target="_blank">Read
                                reviews</a>
                        </div>
                    </c:if>

                    <c:if test="${movie.kinopoiskRating != null}">
                        <div class="col-md-5 col-sm-12" class="mainRatingVal">
                            <img class="sourceIcon"
                                 src="${pageContext.request.contextPath}/resources/images/imdb_icon.svg"
                                 alt="imdb-icon">
                            <span class="mainSourcesRatingValue">${movie.kinopoiskRating}</span>/10
                            (${movie.kinopoiskVotes})
                            <br> Reviews: ${movie.kinopoiskReviews}
                            <br><a href="https://www.kinopoisk.ru/film/${movie.kinopoiskId}/reviews/?ord=rating"
                                   target="_blank">Read
                            reviews</a>
                        </div>
                    </c:if>
                </div>
            </c:if>
            <hr>
            <div class="row" id="movieTableInfo">
                <c:if test="${movie.imdbRating != null}">
                    <div class="col-lg-4 col-sm-12">
                        <table class="table table-bordered table-striped">
                            <tr>
                                <thead class="thdead-dark">
                                <th>Other ratings</th>
                                </thead>
                            </tr>
                            <tbody>
                            <tr>
                                <td>Rated</td>
                                <td>${movie.audienceRating}</td>
                            </tr>
                            <tr>
                                <td>Metascore</td>
                                <td>${movie.metascore}</td>
                            </tr>
                            <tr>
                                <td>Metacritic</td>
                                <td>${movie.metacriticRating}</td>
                            </tr>
                            <tr>
                                <td>TBDM</td>
                                <td>${movie.theMovieDbRating}</td>
                            </tr>
                            <tr>
                                <td>Rotten tomatoes</td>
                                <td>${movie.rottenTomatoesRating}</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </c:if>
                <div class="col-lg-7 col-sm-12">
                    <table class="table table-bordered table-striped">
                        <thead class="thead-dark">
                        <tr>
                            <th>Information</th>
                        </tr>
                        </thead>

                        <tbody>
                        <c:if test="${movie.year != null}">
                            <tr>
                                <td>Year</td>
                                <td>${movie.year}</td>
                            </tr>
                        </c:if>
                        <c:if test="${movie.released != null}">
                            <tr>
                                <td>Released Date</td>
                                <td>${movie.released}</td>
                            </tr>
                        </c:if>
                        <c:if test="${movie.imdbGenre != null}">
                            <tr>
                                <td>Genre(s)</td>
                                <td>${movie.imdbGenre}</td>
                            </tr>
                        </c:if>
                        <c:if test="${movie.duration != null}">
                            <tr>
                                <td>Duration</td>
                                <td>${movie.duration}</td>
                            </tr>
                        </c:if>
                        <c:if test="${movie.imdbCountry != null}">
                            <tr>
                                <td>Country</td>
                                <td>${movie.imdbCountry}</td>
                            </tr>
                        </c:if>
                        <c:if test="${movie.language != null}">
                            <tr>
                                <td>Language</td>
                                <td>${movie.language}</td>
                            </tr>
                        </c:if>

                        <c:if test="${movie.kinopoiskDirector != null}">
                            <tr>
                                <td>Director</td>
                                <td>${movie.kinopoiskDirector}</td>
                            </tr>
                        </c:if>
                        <c:if test="${movie.writer != null}">
                            <tr>
                                <td>Writer</td>
                                <td>${movie.writer}</td>
                            </tr>
                        </c:if>
                        <c:if test="${movie.actors != null}">
                            <tr>
                                <td>Actors</td>
                                <td>${movie.actors}</td>
                            </tr>
                        </c:if>
                        <c:if test="${movie.awards != null}">
                            <tr>
                                <td>Award(s)</td>
                                <td>${movie.awards}</td>
                            </tr>
                        </c:if>
                        <c:if test="${movie.production != null}">
                            <tr>
                                <td>Production</td>
                                <td>${movie.production}</td>
                            </tr>
                        </c:if>

                        <c:if test="${movie.boxOffice != null}">
                            <tr>
                                <td>Box office</td>
                                <td>${movie.boxOffice}</td>
                            </tr>
                        </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
            <hr>
        </div>
    </div>
    <div class="card">
        <h5 class="card-header">revs</h5>
        <div class="card-body">
            <div class="container">
                <details>
                    <summary>Show reviews urls</summary>
                    <div class="d-flex flex-wrap justify-content-center align-items-center">

                    </div>
                </details>

            </div>
        </div>
    </div>





    <div class="card">
        <h5 class="card-header">Images</h5>
        <div class="card-body">
            <div class="container">


                <c:choose>
                <c:when test="${movie.images.isEmpty()}">
                    <a href="${pageContext.request.contextPath}/uploadImages?id=${movie.id}">
                        <div class="d-grid gap-2 col-4 mx-auto">
                            <button class="btn btn-primary" type="button">Load images</button>
                        </div>
                    </a>
                </c:when>
                <c:otherwise>
                <details id="frames">
                    <summary>Show images</summary>
                    <div id="pic_container" class="d-flex flex-wrap justify-content-center align-items-center">
                        <c:forEach items="${movie.images}" var="image">
                            <img class="frame" src="${image.url}" alt="">
                        </c:forEach>
                    </div>
                    </c:otherwise>
                    </c:choose>
                </details>

            </div>

        </div>
    </div>

    <%-- <c:import url="includes/footer.jsp"/> --%>
</div>

</body>
</html>
