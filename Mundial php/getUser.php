<?php 

	if($_SERVER['REQUEST_METHOD']=='GET'){
		
		$login  = $_GET['login'];
		$password  = $_GET['password'];
		
		require_once('dbConnect.php');
		
		$sql = "SELECT login, password FROM Users WHERE login='".$login."' AND password='".$password."'";
		
		$r = mysqli_query($con,$sql);
		
		$res = mysqli_fetch_array($r);
		
		$result = array();
		
		array_push($result,array(
			"login"=>$res['login'],
			"password"=>$res['password']
			)
		);
		
		echo json_encode(array("result"=>$result));
		
		mysqli_close($con);
		
	}