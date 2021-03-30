<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Home</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
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
                </sec:authorize>
              <!--  <li><a href="${pageContext.request.contextPath}/myMovies">Go to My Movies</a></li> -->
                <li><a href="${pageContext.request.contextPath}/test">TEST</a></li>
            </ul>
        </div>
    </nav>
</header>
<main>
    <div class="container text-center">
        <sec:authorize access="isAuthenticated()">
            <h1>Welcome Back, <sec:authentication property="name"/></h1>
        </sec:authorize>
        <hr>
        <h2>Search</h2>
        <form action="${pageContext.request.contextPath}/searchMovie"
              method="get">
            <label for="search">
                Search: <input type="text" name="searchVal" id="search">
            </label>
            <input type="submit" value="Search">
        </form>
    </div>
</main>
</body>
</html>