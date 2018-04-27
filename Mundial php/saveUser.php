<?php 

require_once('dbConnect.php');

$name = $_POST['login'];
$password = $_POST['password'];
 
$sql = "INSERT INTO Players (login, password) 
        VALUES ('$login','$password')";
  if(mysqli_query($con,$sql)){
    echo 'success';
  }
  else{
    echo 'failure';
  }
  mysqli_close($con);
?>