<?php 

	if($_SERVER['REQUEST_METHOD']=='GET'){

		$login  = $_GET['login'];
		
		require_once('dbConnect.php');
		
		$sql = "SELECT Teams.name AS team_a, teams_b.team_b, DATE_FORMAT(Matches.date_match, '%d-%m-%Y') AS date_match, TIME_FORMAT(Matches.time_match, '%H:%i') AS time_match, Matches.id_match, IFNULL(Bets.bet_a, '-') AS bet_a, Bets.bet_b, (SELECT CONVERT_TZ(NOW(), @@session.time_zone, '+02:00')) AS currentdate FROM Matches LEFT JOIN Teams ON Teams.id_team = Matches.team_a LEFT JOIN Bets ON Bets.id_match = Matches.id_match AND login = '".$login."' LEFT JOIN (SELECT Matches.id_match, Teams.name AS team_b, date_match, time_match FROM Matches LEFT JOIN Teams ON Teams.id_team = Matches.team_b) as teams_b ON teams_b.id_match = Matches.id_match WHERE (TIMESTAMP(Matches.date_match,Matches.time_match) > (SELECT CONVERT_TZ(NOW(), @@session.time_zone, '+02:00'))) AND (login = '".$login."' OR login IS NULL) GROUP BY Matches.id_match ORDER BY Matches.date_match, Matches.time_match LIMIT 5";
		
		$r = mysqli_query($con,$sql);
                
		while($e=mysqli_fetch_assoc($r))
                $output[]=$e;
 
                print(json_encode(array("result"=>$output)));
 
mysqli_close($con);
}