<?php

//load and connect to MySQL database 
require("config.inc.php");

if (!empty($_POST)) {
    //gets user's info based off of emaiil
    $query = "
            SELECT
                email,
                username,
                password
            FROM userinfo
            WHERE
                email = :email
        ";

    $query_params = array(
        ':email' => $_POST['email']
    );

    try {
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) {
        $response["success"] = 0;
        $response["message"] = "Database Error1. Please Try Again!";
        die(json_encode($response));

    }

    $validated_info = false;

    $row = $stmt->fetch();
    if ($row) {
        //compare the two passwords
        if ($_POST['password'] === $row['password']) {
            $login_ok = true;
        }
    }

    //Return success of login and if successful return username
    if ($login_ok) {
        $response["success"] = 1;
        $response["message"] = "Login successful!";
        $response["username"] = $row['username'];
        die(json_encode($response));
    } else {
        $response["success"] = 0;
        $response["message"] = "Invalid Credentials!";
        die(json_encode($response));
    }
} else {
?>
		<h1>Login</h1>
		<form action="login.php" method="post">
		    Username:<br />
		    <input type="text" name="email" placeholder="email" />
		    <br /><br />
		    Password:<br />
		    <input type="password" name="password" placeholder="password" value="" />
		    <br /><br />
		    <input type="submit" value="Login" />
		</form>
		<a href="userinfo.php">Register</a>
	<?php
}

?>
