<?php 

	if($_SERVER['REQUEST_METHOD']=='GET'){
		
		$date  = $_GET['date_match'];
		$time  = $_GET['time_match'];
		
		require_once('dbConnect.php');
		
		$sql = "SELECT Teams.name AS team_a, teams_b.team_b, DATE_FORMAT(Matches.date_match, '%d-%m-%Y') AS date_match, TIME_FORMAT(Matches.time_match, '%H:%i') AS time_match, Matches.id_match FROM Matches
        LEFT JOIN Teams ON Teams.id_team = Matches.team_a
        LEFT JOIN (SELECT Matches.id_match, Teams.name AS team_b, date_match, time_match FROM Matches
        LEFT JOIN Teams ON Teams.id_team = Matches.team_b) as teams_b ON teams_b.id_match = Matches.id_match
        WHERE IF(Matches.date_match = '".$date."', (Matches.date_match = '".$date."' AND (Matches.time_match > '".$time."' OR Matches.time_match = '".$time."')),Matches.date_match > '".$date."')
        ORDER BY Matches.date_match, Matches.time_match";
        
        
		
		$r = mysqli_query($con,$sql);
                
		while($e=mysqli_fetch_assoc($r))
                $output[]=$e;
 
                print(json_encode(array("result"=>$output)));
 
mysqli_close($con);
}