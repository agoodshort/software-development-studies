<?php
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

/* ---------------------------------------------------------------------------- */

/* RESERVATION LOOKUP */
if (isset($_GET['resLookup'])) {

    //2. Store variables
    $firstName = mysqli_real_escape_string($connection, $_GET['firstName']);
    if (empty($firstName)) {
        $firstName = '%';
    }
    $surname =  mysqli_real_escape_string($connection, $_GET['surname']);
    if (empty($surname)) {
        $surname = '%';
    }
    $checkin = mysqli_real_escape_string($connection, $_GET['checkin']);
    if (empty($checkin)) {
        $checkin = '%';
    }
    $checkout = mysqli_real_escape_string($connection, $_GET['checkout']);
    if (empty($checkout)) {
        $checkout = '%';
    }
    $room =  mysqli_real_escape_string($connection, $_GET['room']);
    if ($room == "any") {
        $room = "'%' OR `Rooms`.`roomNo` IS NULL";
    }

    //3. SQL query             
    $sql = "SELECT `Bookings`.`arrivalDate`,`Bookings`.`departureDate`,
                        `Persons`.`firstName`,`Persons`.`surname`,
                        `Rooms`.`roomNo`
                        FROM `Bookings`
                        LEFT JOIN `Persons` ON `Bookings`.`personID` = `Persons`.`personID`
                        LEFT JOIN `Rooms` ON `Bookings`.`roomID` = `Rooms`.`roomID`
                        WHERE 
                        `Bookings`.`arrivalDate` LIKE '$checkin' AND
                        `Bookings`.`departureDate` LIKE '$checkout' AND
                        `Persons`.`firstName` LIKE '$firstName' AND
                        `Persons`.`surname` LIKE '$surname' AND
                        (`Rooms`.`roomNo` LIKE $room)";
    $result = mysqli_query($connection, $sql) or die(mysqli_error($connection));

    //4. Print out table
    echo "<div class='col-fixed-450' id='resView'>";
    echo "<button class='btn btn-lg btn-primary' style='display: block; width: 100%;'>2. Reservations table</button><br>";
    echo "<table>
		              <tr>
                        <th>First Name</th>
		                <th>Surname</th>
                        <th>Arrival Date</th>
                        <th>Departure Date</th>
                        <th>Room #</th>
		              </tr>";
    while ($row = mysqli_fetch_array($result)) {
        echo "<tr>";
        echo "<td>" . $row['firstName'] . "</td>";
        echo "<td>" . $row['surname'] . "</td>";
        echo "<td>" . $row['arrivalDate'] . "</td>";
        echo "<td>" . $row['departureDate'] . "</td>";
        echo "<td>" . $row['roomNo'] . "</td>";
        echo "</tr>";
    }
    echo "</table>";
    echo "</div>";

    //5.  Release returned data
    mysqli_free_result($result);
}


/* ---------------------------------------------------------------------------- */

/*CREATE RESERVATION */
if (isset($_GET['resCreate']) || isset($_GET['roomSelected'])) {
    do { // Allows to use break in the variables storage
        //2. Store variables
        $checkin = mysqli_real_escape_string($connection, $_GET['checkin']);
        if (empty($checkin)) break;
        $checkout = mysqli_real_escape_string($connection, $_GET['checkout']);
        if (empty($checkout)) break;
        $adults =  mysqli_real_escape_string($connection, $_GET['adults']);
        if (empty($adults)) {
            $adults = '%';
        }
        $children =  mysqli_real_escape_string($connection, $_GET['children']);
        if (empty($children)) {
            $children = 0;
        }
        $roomSelected = mysqli_real_escape_string($connection, $_GET['roomSelected']);

        //3. SQL query             
        $sql = "SELECT DISTINCT `Bookings`.`arrivalDate`,`Bookings`.`departureDate`,
                        `Rooms`.`roomType`,`Rooms`.`roomNo`,`Rooms`.`adultsNb`,`Rooms`.`childrenNb`,
                        `Rates`.`price`
                        FROM `Rooms`
                        LEFT JOIN `Bookings` ON `Rooms`.`roomID` = `Bookings`.`roomID`
                        LEFT JOIN `Rates` ON `Rooms`.`rateID` = `Rates`.`rateID`
                        WHERE 
                        ((`Bookings`.`arrivalDate` IS NULL AND `Bookings`.`departureDate` IS NULL) OR
                        ('$checkin' NOT BETWEEN `Bookings`.`arrivalDate` AND `Bookings`.`departureDate` AND
                        '$checkout' NOT BETWEEN `Bookings`.`arrivalDate` AND `Bookings`.`departureDate`)) AND
                        `Rooms`.`adultsNb` >= '$adults' AND
                        `Rooms`.`childrenNb` >= '$children'";
        $result = mysqli_query($connection, $sql) or die(mysqli_error($connection));

        //4. Print out table
        echo "<div class='col-fixed-250' id='roomSelect'>";
        echo "<button class='btn btn-lg btn-primary' style='display: block; width: 100%;'>2. Room table</button><br>";
        echo "<form action='" . htmlspecialchars($_SERVER['PHP_SELF']) . "' method='get'>";
        echo "<input type='hidden' name='checkin' value=$checkin />";
        echo "<input type='hidden' name='checkout' value=$checkout />";
        echo "<input type='hidden' name='adults' value=$adults />";
        echo "<input type='hidden' name='children' value=$children />";
        echo "<input type='hidden' name='resCreate' value=submitted />";


        echo "<table>
		              <tr>
						<th></th>
                        <th>Room</th>
		                <th>Room Type</th>
                        <th>Room Number</th>
		              </tr>";
        while ($row = mysqli_fetch_array($result)) {
            $id = "roomSelected" . $row['roomNo'];
            echo "<tr>";
            echo "<td><input  type='radio' name='roomSelected'" . htmlspecialchars(($roomSelected == $row['roomNo'] || "NULL") ? 'checked="checked"' : '') . "id=" . $id . " value=" . $row['roomNo'] . "> </td>";
            echo "<td>" . $row['roomNo'] . "</td>";
            echo "<td>" . $row['roomType'] . "</td>";
            echo "<td>" . $row['price'] . "</td>";
            echo "</tr>";
        }

        echo "</table>";
        echo "<button formaction='#profileCreate' type='submit' id='resCreate' class='btn btn-primary search'>Select</button>"; // formaction is used mostly to bring user to next step while on mobile
        echo "</form>";
        echo "</div>";

        //5.  Release data
        mysqli_free_result($result);
    } while (false); // Allows to use break in the variables storage
}


/* ---------------------------------------------------------------------------- */


/* CREATE PROFILE */
if (isset($_GET['roomSelected'])) {
    if ($_GET['roomSelected'] != "NULL") {
        do { // Allows to use break in the variables storage
            //2. Store variables
            $checkin = mysqli_real_escape_string($connection, $_GET['checkin']);
            if (empty($checkin)) break;
            $checkout = mysqli_real_escape_string($connection, $_GET['checkout']);
            if (empty($checkout)) break;
            $adults =  mysqli_real_escape_string($connection, $_GET['adults']);
            if (empty($adults)) {
                $adults = '%';
            }
            $children =  mysqli_real_escape_string($connection, $_GET['children']);
            if (empty($children)) {
                $children = 0;
            }
            $roomSelected = mysqli_real_escape_string($connection, $_GET['roomSelected']);
            if (empty($roomSelected)) break;

            echo "<div class='col-fixed-250' id='profileCreate'>";
            echo "<button class='btn btn-lg btn-primary' style='display: block; width: 100%;'>3. Guest details</button><br>";
            echo "<div class='lookup-form'>";
            echo "<form action='" . htmlspecialchars($_SERVER['PHP_SELF']) . "' method='GET'>";
            echo "<input type='hidden' name='checkin' value=$checkin />";
            echo "<input type='hidden' name='checkout' value=$checkout />";
            echo "<input type='hidden' name='adults' value=$adults />";
            echo "<input type='hidden' name='children' value=$children />";
            echo "<input type='hidden' name='resCreate' value=submitted />";
            echo "<input type='hidden' name='roomSelected' value=$roomSelected />";


            echo "<div class='input-grp'>";
            echo "<label>Firstname</label>";
            echo "<input type='text' name='firstName' class='form-control' placeholder='Firstname' value=$firstNameVal>";
            echo "<span class='error'>$firstName_error</span>";
            echo "</div>";

            echo "<div class='input-grp'>";
            echo "<label>Surname</label>";
            echo "<input type='text' name='surname' class='form-control' placeholder='Surname' value=$surnameVal>";
            echo "<span class='error'>$surname_error</span>";
            echo "</div>";

            echo "<div class='input-grp'>";
            echo "<label>Phone Number</label>";
            echo "<input type='text' name='phoneNo' class='form-control' placeholder='+353-83-880-1296' value=$phoneNoVal>";
            echo "<span class='error'>$phoneNo_error</span>";
            echo "</div>";

            echo "<input type='hidden' name='resCreateProfileShow' value='NULL' />";
            echo "<input type='hidden' name='profileSelected' value='NULL' />";



            echo "<div class='input-grp'>";
            echo "<span class='error'>$resLookup_error</span>";
            echo "<button formaction='#profileList' type='submit' name='profileShow' id='profileShow' value='true' class='btn btn-primary search'>Show</button>";
            echo "</div>";

            echo "</div>";
            echo "</div>";
        } while (false); // Allows to use break in the variables storage
    }


    /* ---------------------------------------------------------------------------- */

    /*  PROFILE LOOKUP */
    if (isset($_GET['profileShow'])) {
        $firstName = mysqli_real_escape_string($connection, $_GET['firstName']);
        if (empty($firstName)) {
            $firstName = '%';
        }
        $surname =  mysqli_real_escape_string($connection, $_GET['surname']);
        if (empty($surname)) {
            $surname = '%';
        }
        $phoneNo = mysqli_real_escape_string($connection, $_GET['phoneNo']);
        if (empty($phoneNo)) {
            $phoneNo = '%';
        }
        //3. SQL query             
        $sql = "SELECT `personID`,`firstName`,`surname`,`phoneNo`
                        FROM `persons`
                        WHERE 
                        `firstName` LIKE '$firstName' AND
                        `surname` LIKE '$surname' AND
                        `phoneNo` LIKE '$phoneNo'";
        $result = mysqli_query($connection, $sql) or die(mysqli_error($connection));


        //4. Print out table
        echo "<div class='col-fixed-450' id='profileList'>";
        echo "<button class='btn btn-lg btn-primary' style='display: block; width: 100%;'>4. Guest list</button><br>";
        echo "<div class='lookup-form'>";
        echo "<form action='" . htmlspecialchars($_SERVER['PHP_SELF']) . "' method='GET'>";
        echo "<input type='hidden' name='profileShow' value='true' />";



        echo "<table>
		              <tr>
						<th></th>
                        <th>Firstname</th>
		                <th>Surname</th>
                        <th>Phone Number</th>
		              </tr>";
        while ($row = mysqli_fetch_array($result)) {
            echo "<tr>";
            echo "<td><input  type='radio' name='profileSelected'" . htmlspecialchars(($_GET['profileSelected'] == $row['personID']) ? 'checked="checked"' : '') . " value=" . $row['personID'] . "> </td>";
            echo "<td>" . $row['firstName'] . "</td>";
            echo "<td>" . $row['surname'] . "</td>";
            echo "<td>" . $row['phoneNo'] . "</td>";
            echo "</tr>";
        }
        echo "</table>";

        echo "<button name='profileBook' value='true' type='submit' class='btn btn-primary search'>Book for selected profile</button>";
        echo "</form>";



        //5.  Release data
        mysqli_free_result($result);
    }


    /* ---------------------------------------------------------------------------- */


    /*  PROFILE CREATE RESERVATION */
    if (isset($_GET['profileBook'])) {
        if (isset($_GET['resCreateProfileShow']) && isset($_GET['profileShow'])) {
            if ($_GET['profileSelected'] != "NULL") {
                do { // Allows to use break in the variables storage
                    $profileShow = mysqli_real_escape_string($connection, $_GET['profileShow']);
                    if (empty($profileShow)) break;

                    //3. SQL query             
                    $sql = "INSERT INTO `Bookings`(`personID`,`roomID`,`arrivalDate`,`departureDate`) VALUES
                        ($profileShow,$roomSelected,'$checkin','$checkout');";

                    $result = mysqli_query($connection, $sql) or die(mysqli_error($connection));

                    $host  = $_SERVER['HTTP_HOST'];
                    $uri   = rtrim(dirname($_SERVER['PHP_SELF']), '/\\');
                    $extra = 'home.php';

                    // Not a great way to redirect to "home.php" but this will do for this project
                    echo "<script type='text/javascript'>window.top.location='http://$host$uri/$extra';</script>";
                    exit();
                } while (false); // Allows to use break in the variables storage
            } else {
                echo "Please, select a profile";
            }
        }
        echo "</div>";
        echo "</div>";
    }
}

// Close database connection
mysqli_close($connection);
