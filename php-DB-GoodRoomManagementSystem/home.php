<!doctype html>
<html lang="en">

<head>
    <link rel="icon" href="resources/img/logo.png">

    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">

    <!-- My own CSS -->
    <link rel="stylesheet" href="resources/css/style.css">

    <!-- PHP process -->
    <?php include('resources/php_process/res_process.php'); ?>

    <!-- Does not allow to access the home page without signing in -->
    <?php
    session_start();
    if (!isset($_SESSION['signedInUser'])) {
        //Send to login page
        header("Location: signin.php");
    } else {
        $surname = $_SESSION['signedInUser'];
    }
    ?>

    <title>The Good Room / Home</title>
</head>

<!--------------------------- jumbotron -------------------------------------->

<body>
    <div class="jumbotron">
        <div class="container-fluid">
            <div class="row">
                <div class="col-fixed-250">
                    <div id="carouselExampleControls" class="carousel slide" data-ride="carousel">
                        <div class="carousel-inner">
                            <div class="carousel-item active">
                                <img class="d-block w-100" src="resources/img/1.jpg" alt="First slide">
                            </div>
                            <div class="carousel-item">
                                <img class="d-block w-100" src="resources/img/2.jpg" alt="Second slide">
                            </div>
                            <div class="carousel-item">
                                <img class="d-block w-100" src="resources/img/3.jpg" alt="Third slide">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col text-center">
                    <h1 class="display-4">The Good Room</h1>
                    <p class="lead">Your hotel management system made simple.</p>
                </div>
                <div class="col-fixed-250">
                    <p class="lead"><b>Username:</b> <?php echo $surname ?></p>
                    <form action="<?php echo $_SERVER['PHP_SELF']; ?>" method="POST">
                        <button class="btn btn-sm btn-primary" name="signout" type="submit">Sign out</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <?php
    if ($_SERVER['REQUEST_METHOD'] == "POST" and isset($_POST['signout'])) {
        session_unset();
        header("Location: signin.php");
    }
    ?>

    <br>

    <!----------------------------------------------------------------------------->
    <div class="container-fluid">
        <div class="row">
            <div class="col-fixed-250">
            <button class="btn btn-lg btn-primary" style="display: block; width: 100%;" name="signout" type="submit">1. Menu</button>
            <br>
                <ul class="nav nav-pills nav-justified" role="tablist">
                    <li class="nav-item">
                        <a class="nav-link <?php if (isset($_GET['resLookup']) || !isset($_GET['resCreate'])) {
                                                echo ("active");
                                            } ?>" data-toggle="pill" href="#guestlookup" role="tab" aria-controls="guestlookup" aria-selected="true">Reservation lookup</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link <?php if (isset($_GET['resCreate'])) {
                                                echo ("active");
                                            } ?>" data-toggle="pill" href="#reservation" role="tab" aria-controls="reservation" aria-selected="false">Create reservation</a>
                    </li>
                </ul>

                <div class="tab-content">
                    <div class="tab-pane <?php if (isset($_GET['resLookup']) || !isset($_GET['resCreate'])) {
                                                echo ("active");
                                            } ?>" id="guestlookup" role="tabpanel" aria-labelledby="guestlookup-tab">
                        <div class="lookup-form">
                            <form action="<?php echo $_SERVER['PHP_SELF']; ?>" method="GET" id="resLookup">

                                <div class="input-grp">
                                    <label>Firstname</label>
                                    <input type="text" name="firstName" class="form-control" value="<?= $firstNameVal ?>" placeholder="Firstname">
                                    <span class="error"><?= $firstName_error ?></span>
                                </div>

                                <div class="input-grp">
                                    <label>Surname</label>
                                    <input type="text" name="surname" class="form-control" value="<?= $surnameVal ?>" placeholder="Surname">
                                    <span class="error"><?= $surname_error ?></span>
                                </div>

                                <div class="input-grp">
                                    <label>Check-in</label>
                                    <input type="date" name="checkin" class="form-control select-date" value="<?= $checkinVal ?>">
                                </div>

                                <div class="input-grp">
                                    <label>Check-out</label>
                                    <input type="date" name="checkout" class="form-control select-date" value="<?= $checkoutVal ?>">
                                </div>

                                <!-- Nedd to prepopulate the number of rooms -->
                                <div class="input-grp">
                                    <label>Room number</label>
                                    <select class="custom-select" name="room">
                                        <option <?php if ($roomVal == "any") {
                                                    echo ("selected");
                                                } ?> value="any">Any</option>
                                        <option <?php if ($roomVal == 1) {
                                                    echo ("selected");
                                                } ?> value="1">1</option>
                                        <option <?php if ($roomVal == 2) {
                                                    echo ("selected");
                                                } ?> value="2">2</option>
                                    </select>
                                </div>

                                <div class="input-grp">
                                    <button type="submit" formaction='#resView' name="resLookup" class="btn btn-primary search">Show</button>
                                    <span class="error"><?= $resLookup_error ?></span>
                                </div>
                            </form>
                        </div>
                    </div>

                    <!----------------------------------------------------------------------------->

                    <div class="tab-pane <?php if (isset($_GET['resCreate'])) {
                                                echo ("active");
                                            } ?>" id="reservation" role="tabpanel" aria-labelledby="reservation-tab">
                        <div class="lookup-form">
                            <form action="<?php echo $_SERVER['PHP_SELF']; ?>" method="GET" id="resCreate">

                                <div class="input-grp">
                                    <label>Check-in</label>
                                    <input type="date" name="checkin" class="form-control select-date" value="<?= $checkinVal ?>">
                                    <span class="error"><?= $checkin_error ?></span>
                                </div>

                                <div class="input-grp">
                                    <label>Check-out</label>
                                    <input type="date" name="checkout" class="form-control select-date" value="<?= $checkoutVal ?>">
                                    <span class="error"><?= $checkout_error ?></span>
                                </div>

                                <div class="input-grp">
                                    <label>Adults</label>
                                    <input type="number" name="adults" class="form-control" value="1">
                                </div>

                                <div class="input-grp">
                                    <label>Children</label>
                                    <input type="number" name="children" class="form-control" value="0">
                                </div>

                                <div class="input-grp">
                                    <button type="submit" formaction='#roomSelect' name="resCreate" id="resCreate" value="submitted" class="btn btn-primary search">Search</button>
                                </div>

                                <input type="hidden" name="roomSelected" value="NULL" />

                            </form>
                        </div>
                    </div>
                </div>

                <!---------------------------- Shows the related tab ------------------------------>

                <script>
                    $(function() {
                        $('li:last-child a').tab('show')
                    })
                </script>
            </div>

            <!-- DB process -->
            <?php include('resources/php_process/db_process.php'); ?>

        </div> <!-- div class="row" -->
    </div>

    <!----------------------------------------------------------------------------->

    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>

</html>