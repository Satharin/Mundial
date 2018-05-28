<?php 

if($_SERVER["REQUEST_METHOD"]=="POST"){

       require_once('dbConnect.php');

       saveBet();
}

function saveBet()
{

       global $con;

        $login = $_POST['login'];
        $bet_a = $_POST['bet_a'];
        $bet_b = $_POST['bet_b'];
        $id_match = $_POST['id_match'];
      
 
       $sql = "INSERT INTO Bets (login, bet_a, bet_b, id_match, points) 
        VALUES ('$login', '$bet_a', '$bet_b', '$id_match', '0')";
  
       mysqli_query($con, $sql) or die (mysqli_error($con));
       mysqli_close($con);
  
}



