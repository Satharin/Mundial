<?php 

if($_SERVER["REQUEST_METHOD"]=="POST"){

       require_once('dbConnect.php');

       updateBet();
}

function updateBet()
{

       global $con;

        $login = $_POST['login'];
        $bet_a = $_POST['bet_a'];
        $bet_b = $_POST['bet_b'];
        $id_match = $_POST['id_match'];
 
       $sql = "UPDATE Bets SET bet_a = '$bet_a', bet_b = '$bet_b' 
        WHERE login = '$login' AND id_match = '$id_match'";
  
       mysqli_query($con, $sql) or die (mysqli_error($con));
       mysqli_close($con);
  
}



