<?php

    $estaciones = explode("[",$_POST["estaciones"]);
    $rid = $_POST["rid"];

	
    if (count($estaciones) > 1) {  
        $estaciones = explode("]",$estaciones[1]);
  	    $estaciones = explode(", ",$estaciones[0]);
    }
    else {
  	    $estaciones = $_POST["estaciones"]; //estaciones[] = NULL
    }

    $dbhost = 'localhost';
	$dbuser = 'adrian';
	$dbpass = 'aalvarez3282';
	$dbname = 'revisores';

	$conn = mysql_connect($dbhost, $dbuser, $dbpass);
	if(! $conn )
	{
	  die('Could not connect: ' . mysql_error());
	}
	echo "DB ok";
	error_log("Connection DB successfully\n", 3, "/opt/lampp/htdocs/revisores/php.log");
	$sql = "CREATE TABLE IF NOT EXISTS usuarios( ".
	       "rid VARCHAR(50) NOT NULL, ".
	       "estaciones VARCHAR(500), ".
	       "PRIMARY KEY ( rid )); ";
	mysql_select_db( 'revisores' );
	$retval = mysql_query( $sql, $conn );
	if(! $retval )
	{
	  die('Could not create table: ' . mysql_error());
	}
	echo "TABLE ok";
	error_log("Connection Table successfully\n", 3, "/opt/lampp/htdocs/revisores/php.log");
	mysql_close($conn);

	$con = mysqli_connect($dbhost,$dbuser,$dbpass,$dbname);
	// Check connection
	if (mysqli_connect_errno()) {
	  echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}

	$estaciones = implode(",", $estaciones);
	mysqli_query($con,"INSERT INTO usuarios (rid, estaciones) VALUES('$rid','$estaciones') ON DUPLICATE KEY UPDATE estaciones=VALUES(estaciones)");
	mysqli_close($con);
?>
