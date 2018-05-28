<?php 

	
		
		require_once('dbConnect.php');
		
		$sql = "SELECT * FROM Group_B ORDER BY points DESC, balance DESC";
        
		$r = mysqli_query($con,$sql);
                
		while($e=mysqli_fetch_assoc($r))
                $output[]=$e;
 
                print(json_encode(array("result"=>$output)));
 
mysqli_close($con);