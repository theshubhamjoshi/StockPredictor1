<?php
	
	$result = shell_exec("/usr/bin/python final1.py ".$_POST['company']);
	echo $result
?>
