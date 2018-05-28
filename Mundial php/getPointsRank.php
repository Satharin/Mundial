<?php 
	if($_SERVER['REQUEST_METHOD']=='GET'){
		
		$output = null;
		
		require_once('dbConnect.php');
		
		$sql = "SELECT login as users, sum(Points) as points, sum(exact_result) as exact_results FROM Bets
				GROUP BY users ORDER BY points DESC, exact_result DESC";
	
		
		$r = mysqli_query($con,$sql);
                
		while($e=mysqli_fetch_assoc($r))
                $output[]=$e;
 
                print(json_encode(array("result"=>$output)));
 
mysqli_close($con);
}