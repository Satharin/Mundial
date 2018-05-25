<?php 

require_once('dbConnect.php');

$name = $_POST['login'];
$password = $_POST['password'];
 
$sql = "INSERT INTO Users (login, password) 
        VALUES ('$name','$password')";
  if(mysqli_query($con,$sql)){
    echo 'success';
  }
  else{
    echo 'failure';
  }
  mysqli_close($con);
?>