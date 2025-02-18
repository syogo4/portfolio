<?php
    //SQLに接続
    $s = new PDO("mysql:host=localhost; dbname=liquorstoredb", "root","root");

    //データ更新
    $re = $s -> query("UPDATE members SET m_rog = 0 WHERE m_rog = 1");
?>