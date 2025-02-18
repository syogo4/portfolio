<?php
    $row = json_decode(file_get_contents('php://input'),true);
    //SQLに接続
    $s = new PDO("mysql:host=localhost; dbname=liquorstoredb", "root","root");

    $MId = $row['m_id'];

    //データ更新
    $re = $s -> query("UPDATE members SET m_rog = 1 WHERE m_id = $MId");
?>