<?php 

	if($_SERVER['REQUEST_METHOD']=='GET'){
		
		$id_match = $_GET['id_match'];
		
		require_once('dbConnect.php');
		
		$sql = "SELECT login, Bets.id_match, bet_a, bet_b, Matches.result_a, Matches.result_b, id_bet FROM Bets 
		LEFT JOIN Matches ON Matches.id_match = Bets.id_match 
		WHERE Bets.id_match = '$id_match'";
        
		
		$r = mysqli_query($con,$sql);
                
		while($e=mysqli_fetch_assoc($r))
                $output[]=$e;
 
                print(json_encode(array("result"=>$output)));
 
mysqli_close($con);
}