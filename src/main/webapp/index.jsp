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
<h1>Search</h1>
<form action="${pageContext.request.contextPath}/searchMovie"
      method="get">
    <label for="search">
        Search: <input type="text" name="search" id="search">
    </label>
    <input type="submit" value="Search">
</form>
</body>
</html>