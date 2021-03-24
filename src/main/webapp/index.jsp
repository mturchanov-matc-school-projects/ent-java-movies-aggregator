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
<security:authorize access="hasRole('anonymous')">
    <input type="button" value="LOGIN"
           onclick="window.location.href='login'">
</security:authorize>

<h1>Search</h1>

<a href="${pageContext.request.contextPath}/myMovies">Go to My Movies</a>
<br>

<a href="${pageContext.request.contextPath}/registrationProcessing">Register</a>
<br>
<hr>
<form action="${pageContext.request.contextPath}/searchMovie"
      method="get">
    <label for="search">
        Search: <input type="text" name="searchVal" id="search">
    </label>
    <input type="submit" value="Search">
</form>

<script>
    //console.log($( "#content" ).load( "ajax/https://api.jquery.com/load/ nav#main" ));

</script>
</body>
</html>