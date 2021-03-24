<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>

</head>
<body>
<h3>Welcome, new user</h3>


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
</body>
</html>