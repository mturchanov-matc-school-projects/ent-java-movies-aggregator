<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
<sec:authorize access="!isAuthenticated()">
    <input type="button" value="LOGIN"
           onclick="window.location.href='login'">
</sec:authorize>
<sec:authorize access="isAuthenticated()">
    Logout
</sec:authorize>


<sec:authorize access="isAuthenticated()">
    Welcome Back, <sec:authentication property="name"/>
</sec:authorize>
<br>
<h3>Movie tyest dao</h3>
getAll: ${movies} <br>
<hr>
getByID: ${getMovie} <br>
<hr>
addNew: ${moviesWiehNew}<br>
<hr>
getAllByColumnProperty: ${searches}
<hr>
seacg Result: ${search}
<br>
<hr>
<h2>Here's username: ${username}</h2>

</body>
</html>