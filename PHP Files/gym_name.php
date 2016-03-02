<?php
//load and connect to MySQL database
require("config.inc.php");



	$query = "
    SELECT name
			FROM gyminfo

	";


    try {
        $stmt   = $db->prepare($query);
				$result = $stmt->execute();
    }
    catch (PDOException $ex) {

        $response = 0;
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





?>
