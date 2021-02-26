<?php
    $login_error = "";

    //1. Create a database connection
    $dbhost = "localhost";
    $dbuser = "root";
    $dbpassword = "";
    $dbname = "GoodRoom";
    $connection = mysqli_connect($dbhost, $dbuser, $dbpassword, $dbname);

    //Test database connection
    if (mysqli_connect_errno()) {
        die("DB connection failed: " .
            mysqli_connect_error() .
            " (" . mysqli_connect_errno() . ")");
    }
    if (!$connection) {
        die('Could not connect: ' . mysqli_error($connection));
    }

    /* RESERVATION LOOKUP */
    if (isset($_POST['signin'])) {

        //2. Store variables
        $username = mysqli_real_escape_string($connection, $_POST['username']);
        $password =  mysqli_real_escape_string($connection, $_POST['password']);

        //3. SQL query             
        $sql = "SELECT `password` FROM `users`
            WHERE `username` = '$username'";
        $result = mysqli_query($connection, $sql) or die(mysqli_error($connection));

        $correctpwd = mysqli_fetch_array($result);

        //4. Redirect to dashboard
        if (!empty($correctpwd)) {
            if ($correctpwd['password'] == $password) {
                session_start();
                $_SESSION['signedInUser'] = $username;
                $host  = $_SERVER['HTTP_HOST'];
                $uri   = rtrim(dirname($_SERVER['PHP_SELF']), '/\\');
                $extra = 'home.php';
                header("Location: http://$host$uri/$extra");
                exit();
            }
        } else {
            $login_error = "Username and Password incorrect";
        }

        //5.  Release returned data
        mysqli_free_result($result);
    }
    // Close database connection
    mysqli_close($connection);
    ?>