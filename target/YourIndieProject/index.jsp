<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

</head>
<body>




<hr>

<sec:authorize access="!isAuthenticated()">
    <a href="${pageContext.request.contextPath}/registrationProcessing">Register</a>
</sec:authorize>
<br>
<sec:authorize access="!isAuthenticated()">
<input type="button" value="LOGIN"
       onclick="window.location.href='login'">
</sec:authorize>

<<br>
<sec:authorize access="isAuthenticated()">
    <a href="${pageContext.request.contextPath}/logout" >">Logout</a>
</sec:authorize>

<h1>Search</h1>

<a href="${pageContext.request.contextPath}/myMovies">Go to My Movies</a>
<br>

<br>
<a href="${pageContext.request.contextPath}/test">Testing</a>
<br>
<hr>
<form action="${pageContext.request.contextPath}/searchMovie"
      method="get">
    <label for="search">
        Search: <input type="text" name="searchVal" id="search">
    </label>
    <input type="submit" value="Search">
</form>

<sec:authorize access="isAuthenticated()">
    Welcome Back, <sec:authentication property="name"/>
</sec:authorize>

</body>
</html>