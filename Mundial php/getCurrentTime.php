<?php 

	if($_SERVER['REQUEST_METHOD']=='GET'){
		
		$output = null;
		
		require_once('dbConnect.php');
		
		$sql = "SELECT (CONVERT_TZ(NOW(), @@session.time_zone, '+02:00')) as currentdate";
	
		
		$r = mysqli_query($con,$sql);
                
		while($e=mysqli_fetch_assoc($r))
                $output[]=$e;
                
                if(is_null($output)){
                   $output[]="";}
 
                print(json_encode(array("result"=>$output)));
 
mysqli_close($con);
}