<?php
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
	$sql = "CREATE TABLE IF NOT EXISTS alertas( ".
	       "date TIMESTAMP NOT NULL, ".
	       "linea VARCHAR(10) NOT NULL,".
	       "estacion VARCHAR(50) NOT NULL, ".
	       "PRIMARY KEY ( date )); ";
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
	mysqli_query($con,"INSERT INTO alertas (rid, estaciones) VALUES('$rid','$estaciones') ON DUPLICATE KEY UPDATE estaciones=VALUES(estaciones)");
	mysqli_close($con);



?>