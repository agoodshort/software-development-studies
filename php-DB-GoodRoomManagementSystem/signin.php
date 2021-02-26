<!DOCTYPE html>
<html lang="en">

<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="resources/img/logo.png">

    <title>The Good Room / Sign-in</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">

    <!-- Custom styles for this template -->
    <link rel="stylesheet" href="resources/css/signin.css">

    <!-- signin PHP -->
    <?php include('resources/php_process/signin_process.php'); ?>

</head>

<body class="text-center">
    <form class="form-signin" action="<?php echo $_SERVER['PHP_SELF']; ?>" method="POST" id="signin">
        <img class="mb-4" src="resources/img/logo.png" alt="" width="72" height="72">
        <h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>
        <input type="text" name="username" class="form-control" placeholder="Username = admin" required="" autofocus="" autocomplete="off" style="background-repeat: no-repeat; background-attachment: scroll; background-size: 16px 18px; background-position: 98% 50%; cursor: auto;">
        <input type="password" name="password" class="form-control" placeholder="Password = admin" required="" autocomplete="off" style="background-repeat: no-repeat; background-attachment: scroll; background-size: 16px 18px; background-position: 98% 50%; cursor: auto;">
        <span class="error"><?= $login_error ?></span>
        <button class="btn btn-lg btn-primary btn-block" name="signin" type="submit">Sign in</button>
        <p class="mt-5 mb-3 text-muted">Adrien Biencourt</p>
        <p class="mb-3 text-muted"><a href="https://github.com/goodshort/GoodRoom">https://github.com/goodshort/GoodRoom</a></p>
        <p class="mb-3 text-muted">2020</p>
    </form>

</body>

</html>