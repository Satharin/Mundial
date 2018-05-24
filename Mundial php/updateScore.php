<?php 

if($_SERVER["REQUEST_METHOD"]=="POST"){

       require_once('dbConnect.php');

       updateScore();
}

function updateScore()
{

       global $con;

        $bet_a = $_POST['bet_a'];
        $bet_b = $_POST['bet_b'];
        $id_match = $_POST['id_match'];
 
       $sql = "UPDATE Matches SET result_a = '$bet_a', result_b = '$bet_b' 
        WHERE id_match = '$id_match'";
  
       mysqli_query($con, $sql) or die (mysqli_error($con));
       mysqli_close($con);
  
}



