<?php
$recv = json_decode(file_get_contents('php://input'), true);
//SQLサーバーへ接続
$s = new PDO("mysql:host=localhost; dbname=liquorstoredb", "root","root");

//データの追加
foreach($recv as $rows){
    $omId = $rows['o_m_id'];
    $obId = $rows['o_b_id'];
    $oqt = $rows['o_quantity'];
    
    $re = $s -> query(
        "INSERT INTO orders(o_m_id,o_b_id, o_quantity, o_data)
        VALUE($omId,$obId, $oqt, now())"
    );
}
?>