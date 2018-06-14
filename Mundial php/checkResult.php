<?php 

	if($_SERVER['REQUEST_METHOD']=='GET'){
		
		$id_match = $_GET['id_match'];
		$output = null;
		
		require_once('dbConnect.php');
		
		$sql = "SELECT * FROM Matches WHERE id_match = '$id_match'";
	
		
		$r = mysqli_query($con,$sql);
                
		while($e=mysqli_fetch_assoc($r))
                $output[]=$e;
                
                if(is_null($output)){
                   $output[]="";}
 
                print(json_encode(array("result"=>$output)));
 
mysqli_close($con);
}