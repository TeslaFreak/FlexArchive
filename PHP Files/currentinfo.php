<?php
//load and connect to MySQL database 
require("config.inc.php");

if (!empty($_POST)) {

	$query = "
    SELECT
        password,
        expertise,
        birthdate,
        age,
        email
	FROM userinfo
			WHERE
                 username = :username
	";

    $query_params = array(
      ':username' => $_POST['username']

    );

    try {
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) {

        $response = 0;
        die(json_encode($response));
    }
    $row = $stmt->fetch();
		$response["password"] = $row['password'];
		$response["expertise"] = $row['expertise'];
		$response["birthdate"] = $row['birthdate'];
		$response["age"] = $row['age'];
		$response["email"] = $row['email'];
    die(json_encode($response));

}

?>
