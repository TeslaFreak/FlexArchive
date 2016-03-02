<?php
//load and connect to MySQL database stuff
require("config.inc.php");
if (!empty($_POST)) {
    //gets user's info based off of a expertise.
    $query = "
            SELECT
                username,
				        expertise,
                email
            FROM userinfo
            WHERE
                expertise = :expertise
            ORDER BY
                username
        ";
    $query_params = array(
        ':expertise' => $_POST['expertise']
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

    //fetching all the rows from the query
    $row = $stmt->fetch();
	$rows=array();
	while($row)
	{
		array_push($rows, $row);
		$row = $stmt->fetch();
	}
	echo(json_encode($rows));

}
?>
