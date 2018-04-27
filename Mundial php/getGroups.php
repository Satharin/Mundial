<?php 

	if($_SERVER['REQUEST_METHOD']=='GET'){
		
		$output = null;
		
		require_once('dbConnect.php');
		
		$sql = "SELECT team_a, team_b FROM Matches 
		WHERE stage = "group" 
		ORDER BY date, time";
	
		
		$r = mysqli_query($con,$sql);
                
		while($e=mysqli_fetch_assoc($r))
                $output[]=$e;
 
                print(json_encode(array("result"=>$output)));
 
mysqli_close($con);
}