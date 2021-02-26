<?php
$firstNameVal = $surnameVal = $roomVal = $checkinVal = $checkoutVal = $phoneNoVal = "";
$firstName_error = $surname_error = $resLookup_error = $checkin_error = $checkout_error = $phoneNo_error = "";

if ($_SERVER["REQUEST_METHOD"] == "GET") {

    /* RESERVATION LOOKUP */
    if (isset($_GET['resLookup'])) {

        if (empty($_GET["firstName"]) && empty($_GET["surname"]) && empty($_GET["checkin"]) && empty($_GET["checkout"])) {
            $resLookup_error = "Showing all reservations";

        }

        $firstNameVal = test_input($_GET["firstName"]);
        $surnameVal = test_input($_GET["surname"]);
        $checkinVal = $_GET["checkin"];
        $checkoutVal = $_GET["checkout"];
        $roomVal = $_GET["room"];

        // error check
        $firstName_error = check_name($firstNameVal);
        $surname_error = check_name($surnameVal);

        /* CREATE RESERVATION */
    } else if (isset($_GET['resCreate'])) {
        if (empty($_GET["checkin"])) {
            $checkin_error = "Check-in date is required";
        } else {
            $checkinVal = $_GET["checkin"];
        }

        if (empty($_GET["checkout"])) {
            $checkout_error = "Check-out date is required";
        } else {
            $checkoutVal = $_GET["checkout"];
        }
    }

    /* PROFILE LOOKUP */
    if (isset($_GET['profileCreate'])) {
        $firstNameVal = test_input($_GET["firstName"]);
        $surnameVal = test_input($_GET["surname"]);
        $phoneNoVal = $_GET["phoneNo"];
        $checkinVal = $_GET["checkin"];
        $checkoutVal = $_GET["checkout"];
        $adultsVal = $_GET["adults"];
        $childrenVal = $_GET["children"];
        $resCreateVal = $_GET["resCreate"];
        $roomTypeSelectedVal = $_GET["roomTypeSelected"];

        // error check
        $firstName_error = check_name($firstNameVal);
        $surname_error = check_name($surnameVal);

        if (!empty($phoneNoVal)) {
            if (!preg_match("/^(\+?\(?[0-9]{2,3}\)?)([ -]?[0-9]{2,4}){3}/", $phoneNoVal)) {
                $phoneNo_error = "Please enter a valid phone number";
            }
        }
    }
}

// Makes the input fields safer
function test_input($data)
{
    $data = trim($data);
    $data = stripslashes($data);
    $data = htmlspecialchars($data);
    return $data;
}

// Check firstName and surname values
function check_name($data)
{
    if (!preg_match("/^[a-zA-Z ]*$/", $data)) {
        $error = "Only letters and white space allowed";
        return $error;
    } else {
        return "";
    }
}
