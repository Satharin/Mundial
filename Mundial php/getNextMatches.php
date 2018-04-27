<?php 

	if($_SERVER['REQUEST_METHOD']=='GET'){
		
		$date  = $_GET['date_match'];
		
		require_once('dbConnect.php');
		
		$sql = "SELECT team_a, team_b, date_match FROM Matches
		WHERE date_match > '".$date."' OR date_match = '".$date."'
		ORDER BY date_match
        LIMIT 3;"
		
		$r = mysqli_query($con,$sql);
                
		while($e=mysqli_fetch_assoc($r))
                $output[]=$e;
 
                print(json_encode(array("result"=>$output)));
 
mysqli_close($con);
}