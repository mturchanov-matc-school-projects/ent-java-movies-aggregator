<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Movie Aggregator - Home" scope="request" />
<c:import url="includes/head.jsp" />
<%@ page contentType="text/html; charset=UTF-8" language="java" %>


<body>
<c:import url="includes/header.jsp" />

<h3>Welcome, new user</h3>
<h4 id="warning-sign-up">${warning}</h4>

<form:form method="POST"
           action="registrationProcessing" modelAttribute="user">
    <form:label path="username">Username</form:label>
    <form:input path="username"/>
    <br>
    <form:label for="password"  path="password">Password</form:label>
    <form:password id="password" name="password" path="password"/>
    <br>
    <label for="confirm_password"> Retype password</label>
    <input type="password" id="confirm_password" name="confirm_password">
    <br>
    <span id='message'></span>
    <br>
    <input type="submit" id="submitRegistration" value="Submit"/>

    <script>
        $('form').submit( function (event) {
            if ($('#password').val() == $('#confirm_password').val()) {
                $('#message').html('Matching').css('color', 'green');
                return;
            }
            $( "span" ).text( "Not valid!" ).css('color', 'red').show().fadeOut( 3000 );
            event.preventDefault();
        });
    </script>
</form:form>
<c:import url="includes/footer.jsp" />

</body>
</html>