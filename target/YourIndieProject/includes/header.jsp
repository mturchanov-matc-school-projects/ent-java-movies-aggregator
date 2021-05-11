<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<header>


    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown"
                    aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNavDropdown">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link active lead" aria-current="page"
                           href="${pageContext.request.contextPath}/home">Home</a>
                    </li>
                    <sec:authorize access="!isAuthenticated()">
                        <li class="nav-item">
                            <a class="nav-link lead" href="${pageContext.request.contextPath}/registrationProcessing">Register</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link lead" href="${pageContext.request.contextPath}/login">Login</a>
                        </li>
                    </sec:authorize>
                    <sec:authorize access="isAuthenticated()">
                        <li class="nav-item">
                            <a class="nav-link lead" href="${pageContext.request.contextPath}/logout">Logout</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link lead" href="${pageContext.request.contextPath}/myMovies?movieSourceBase=">My movies</a>
                        </li>
                    </sec:authorize>
                   <!-- <li class="nav-item"><a class="nav-link lead" href="/test">Test</a>
                    </li> -->
                </ul>
            </div>
        </div>
    </nav>
</header>