<!doctype html>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">

<c:set var="title" value="Movie Aggregator - Home" scope="request" />
<c:import url="includes/head.jsp" />

<c:import url="includes/header.jsp" />
<div id="wrapper" class="container text-center">

<main class="p-3">

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
    <hr>
    <p class="text-warning">${headerTitle}</p>


</main>

</div>
<c:import url="includes/footer.jsp" />
</body>
</html>