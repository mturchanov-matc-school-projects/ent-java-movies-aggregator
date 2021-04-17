<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<header>
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="${pageContext.request.contextPath}/">HOME</a>
            </div>
            <ul class="nav navbar-nav">
                <sec:authorize access="!isAuthenticated()">
                    <li><a href="${pageContext.request.contextPath}/registrationProcessing">REGISTER</a></li>
                    <li><a href="${pageContext.request.contextPath}/login">LOGIN</a></li>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
                    <li><a href="${pageContext.request.contextPath}/logout">LOGOUT</a></li>
                    <li><a href="${pageContext.request.contextPath}/myMovies">MY MOVIES</a></li>
                </sec:authorize>
                <li><a href="${pageContext.request.contextPath}/test">TEST</a></li>
            </ul>
        </div>
    </nav>
</header>