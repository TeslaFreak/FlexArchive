<?php

require("config.inc.php");

if (!empty($_POST)) {

	$query = "INSERT INTO gyminfo ( name, squat, bench, legs, smith, dumbbell, treadmill, address, phonenumber ) VALUES ( :name, :squat, :bench, :legs, :smith, :dumbbell, :treadmill, :address, :phonenumber ) ";

    $query_params = array(
        ':name' => $_POST['name'],
				':squat' => $_POST['squat'],
        ':bench' => $_POST['bench'],
				':legs' => $_POST['legs'],
				':smith' => $_POST['smith'],
        ':dumbbell' => $_POST['dumbbell'],
				':treadmill' => $_POST['treadmill'],
				':address' => $_POST['address'],
        ':phonenumber' => $_POST['phonenumber']

    );

    try {
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) {
        $response["success"] = 0;
        $response["message"] = "Database Error. Couldn't add post!";
        die(json_encode($response));
    }

    $response["success"] = 1;
    $response["message"] = "Gym Successfully Added!";
    echo json_encode($response);

}

?>
