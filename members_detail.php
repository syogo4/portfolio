<?php
$recv = json_decode(file_get_contents('php://input'), true);
//SQLサーバーへ接続
$s = new PDO("mysql:host=localhost; dbname=liquorstoredb", "root","root");

//データの追加
foreach($recv as $rows){
    $mName = $rows['m_name'];
    $mPass = $rows['m_pass'];
    
    $re = $s -> query(
        "INSERT INTO members(m_name,m_pass,m_rog)
        VALUE('$mName','$mPass',1)"
    );
}
?>