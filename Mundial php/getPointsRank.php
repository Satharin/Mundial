<?php 
	if($_SERVER['REQUEST_METHOD']=='GET'){
		
		$login = $_GET['login'];
		$output = null;
		
		require_once('dbConnect.php');
		
		$sql = "SELECT Bets.login as users, sum(Points) as points, sum(exact_result) as exact_results, Users.group_name FROM Bets
            LEFT JOIN Users ON Users.login = Bets.login
            WHERE Users.group_name = (SELECT group_name FROM Users
            WHERE login = '".$login."')
            GROUP BY users ORDER BY points DESC, exact_result DESC";
	
		
		$r = mysqli_query($con,$sql);
                
		while($e=mysqli_fetch_assoc($r))
                $output[]=$e;
 
                print(json_encode(array("result"=>$output)));
 
mysqli_close($con);
}