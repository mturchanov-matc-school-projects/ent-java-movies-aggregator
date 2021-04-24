<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<c:set var="title" value="Movie Aggregator - Home" scope="request" />
<c:import url="includes/head.jsp" />

<c:import url="includes/header.jsp" />
<div id="wrapper" class="container text-center">

<main class="p-3">

        <sec:authorize access="isAuthenticated()">
            <h1>Welcome Back, <sec:authentication property="name"/></h1>
        </sec:authorize>

test of russian chars: Производится тест
        <hr>
        <h2>Search</h2>
        <form action="${pageContext.request.contextPath}/searchMovie"
              method="get">
            <label for="search">
                Search: <input type="text" name="searchVal" id="search">
            </label>
            <input type="submit" value="Search">

            <!--
            <fieldset class="form-group">
                <div class="row">
                    <legend class="col-form-label col-sm-2 pt-0">Language</legend>
                    <div class="col-sm-10">
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="lang" id="engLangRadio" value="eng" checked>
                            <label class="form-check-label" for="engLangRadio">
                                English
                            </label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="lang" id="rusLangRadio" value="rus">
                            <label class="form-check-label" for="rusLangRadio">
                                Russian
                            </label>
                        </div>
                    </div>
                </div>
            </fieldset>
            -->

        </form>
    <hr>
    <p class="text-warning">${headerTitle}</p>


</main>

</div>
<c:import url="includes/footer.jsp" />
</body>
</html>