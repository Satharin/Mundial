<?php 

	if($_SERVER['REQUEST_METHOD']=='GET'){
		
		$id_match  = $_GET['id_match'];
		$login  = $_GET['login'];
		
		require_once('dbConnect.php');
		
		$sql = "SELECT Bets.login, bet_a, bet_b FROM Bets
		        LEFT JOIN Users ON Users.login = Bets.login
				WHERE id_match = '$id_match' AND Users.group_name = 
				(SELECT group_name FROM Users WHERE login = '$login')";
		
		$r = mysqli_query($con,$sql);
                
		while($e=mysqli_fetch_assoc($r))
                $output[]=$e;
 
                print(json_encode(array("result"=>$output)));
 
mysqli_close($con);
}