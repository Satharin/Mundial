<?php 

require_once('dbConnect.php');

$name = $_POST['login'];
$password = $_POST['password'];
$group_name = $_POST['group_name'];
 
$sql = "INSERT INTO Users (login, password, group_name) 
        VALUES ('$name','$password', '$group_name')";
  if(mysqli_query($con,$sql)){
    echo 'success';
  }
  else{
    echo 'failure';
  }
  mysqli_close($con);
?>