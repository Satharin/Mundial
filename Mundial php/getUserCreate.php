<?php 

	if($_SERVER['REQUEST_METHOD']=='GET'){
		
		$login  = $_GET['login'];
                
		
		require_once('dbConnect.php');
		
		$sql = "SELECT login FROM Users WHERE login='".$login."'";
		
		$r = mysqli_query($con,$sql);
		
		$res = mysqli_fetch_array($r);
		
		$result = array();
		
		array_push($result,array(
			"login"=>$res['login'],
			)
		);
		
	
		echo json_encode(array("result"=>$result));
		
		mysqli_close($con);
		
	}