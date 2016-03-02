<?php
//load and connect to MySQL database 
require("config.inc.php");
if (!empty($_POST)) {
    //gets user's info based off of a gymid.
    $query = "
            SELECT
                name,
				address,
				phonenumber,
				squat,
				bench,
				legs,
				smith,
				dumbbell,
				treadmill
            FROM gyminfo
            WHERE
                gymid = :gymid
        ";
    $query_params = array(
        ':gymid' => $_POST['gymid']
    );
    try {
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) {
        // For testing, you could use a die and message.
        //die("Failed to run query: " . $ex->getMessage());
        //or just use this use this one to product JSON data:
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

} else {
?>
		<h1>Login</h1>
		<form action="login.php" method="post">
		    Username:<br />
		    <input type="text" name="username" placeholder="username" />
		    <br /><br />
		    Password:<br />
		    <input type="password" name="password" placeholder="password" value="" />
		    <br /><br />
		    <input type="submit" value="Login" />
		</form>
		<a href="register.php">Register</a>
	<?php
}
?>
