<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Movie Aggregator - Home" scope="request"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:import url="includes/head.jsp"/>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>


<body>
<c:import url="includes/header.jsp"/>

<h4 id="warning-sign-up">${warning}</h4>


<div class="container" style="width: 30%;">
    <form:form id="formSignUp" method="POST"
               action="registrationProcessing" class="form-signin" modelAttribute="user">
        <h2 class="form-signin-heading">Please sign up</h2>
        <h4 id="warning-sign-up">${warning}</h4>


        <p>
            <form:label for="username" path="username" class="sr-only">Username</form:label>
            <form:input id="username" class="form-control" placeholder="Username" path="username"/>
        </p>
        <p>
            <span class="lead text-center text-danger" id="message"></span>

            <form:label for="password" path="password" class="sr-only">Password</form:label>
            <form:input type="password" id="password" class="form-control" name="password" placeholder="Password" path="password"/>
        </p>
        <p>
            <label for="confirm_password" class="sr-only">Retype Password</label>
            <input type="password" id="confirm_password" name="confirm_password" class="form-control"
                   placeholder="Retype Password" required>
        </p>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
    </form:form>

</div>


<c:import url="includes/footer.jsp"/>
<script>
    $('form#formSignUp').submit(function (event) {
        if ($('#password').val() == $('#confirm_password').val()) {
            $('#message').html('Matching').css('color', 'green');
            return;
        }
        $("#message").text("Passwords are not matching!").css('color', 'red').show().fadeOut(3000);
        event.preventDefault();
    });
</script>
</body>
</html>