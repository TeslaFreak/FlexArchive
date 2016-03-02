<?php

require("config.inc.php");

if (!empty($_POST)) {

	$query = "INSERT INTO flexdata ( location, username, biceps, triceps, chest, back, shoulder, legs, calves, abs, cardio, inTime, outTime ) VALUES ( :location, :username, :biceps, :triceps, :chest, :back, :shoulder, :legs, :calves, :abs, :cardio, :inTime, :outTime  ) ";

    $query_params = array(
        ':location' => $_POST['location'],
		':username' => $_POST['username'],
        ":biceps" => $_POST['biceps'],
		":triceps" => $_POST['triceps'],
		":chest" => $_POST['chest'],
		":back" => $_POST['back'],
		":shoulder" => $_POST['shoulder'],
		":legs" => $_POST['legs'],
		":calves" => $_POST['calves'],
		":abs" => $_POST['abs'],
		":cardio" => $_POST['cardio'],
		":inTime" => $_POST['inTime'],
		":outTime" => $_POST['outTime'],

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
