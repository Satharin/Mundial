<?php 

require_once('dbConnect.php');

$name = $_POST['login'];
$password = $_POST['password'];
 
$sql = "INSERT INTO Users (login, password) 
<<<<<<< HEAD
        VALUES ('$name','$password')";
=======
        VALUES ('$login','$password')";
>>>>>>> 8c4ee0362290c6c67a8a048132071055cfd4d92d
  if(mysqli_query($con,$sql)){
    echo 'success';
  }
  else{
    echo 'failure';
  }
  mysqli_close($con);
?>
