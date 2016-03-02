<?php

//load and connect to MySQL database 
require("config.inc.php");

if (!empty($_POST)) {

	$query = "UPDATE userinfo
						SET 	email = :email,
									password = :password,
									expertise = :expertise,
									birthdate = :birthdate,
									age = :age
						WHERE username=:username";

    $query_params = array(
        ':password' => $_POST['password'],
				':expertise' => $_POST['expertise'],
        ':birthdate' => $_POST['birthdate'],
				':age' => $_POST['age'],
				':email' => $_POST['email'],
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

    $response = "success";
    die(json_encode($response));

} else {
?>
		<h1>Add User Info</h1>
		<form action="userinfo.php" method="post">
		     		 Password:<br />
		    <input type="text" name="password" placeholder="p word" />
		    <br /><br />
		    		Expertise:<br />
		    <input type="number" name="expertise" placeholder="expertise level" />
		    <br /><br />
		   		 Birthdate:<br />
		    <input type="text" name="birthdate" placeholder="birthdate" />
		    <br /><br />
				Age:<br />
		    <input type="number" name="age" placeholder="age" />
		    <br /><br />
				Email:<br />
		    <input type="text" name="email" placeholder="email" />
		    <br /><br />
		    		User Name:<br />
		    <input type="text" name="username" placeholder="username" />
		    <br /><br />
				<input type="submit" value="Add User Info" />
		</form>
	<?php
}

?>
