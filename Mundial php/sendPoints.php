<?php 
require_once('dbConnect.php');
$id_bet = $_POST['id_bet'];
$points = $_POST['points'];
$exactResult = $_POST['exact_result'];
 
$sql = "UPDATE Bets SET Points = '$points', exact_result = '$exactResult'
        WHERE Bets.id_bet = '$id_bet'";
  if(mysqli_query($con,$sql)){
    echo 'success';
  }
  else{
    echo 'failure';
  }
  mysqli_close($con);
?>