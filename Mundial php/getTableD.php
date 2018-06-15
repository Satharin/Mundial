<?php 

	
		
		require_once('dbConnect.php');
		
		$sql = "SELECT id_group, team, matches, goals_scored, goals_lost, IF(LOCATE('-',balance2) > 0, CONCAT(REPLACE(balance2,'-',''),'-'), balance2) as balance, points FROM Group_D
            ORDER BY points DESC, balance2 DESC";
        
		$r = mysqli_query($con,$sql);
                
		while($e=mysqli_fetch_assoc($r))
                $output[]=$e;
 
                print(json_encode(array("result"=>$output)));
 
mysqli_close($con);