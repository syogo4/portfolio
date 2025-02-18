<?php
//SQLサーバーへ接続
$s = new PDO("mysql:host=localhost; dbname=liquorstoredb", "root","root");
//データ取得
$re = $s -> query("SELECT b_id, b_name, b_price FROM beers");
//一行ずつ表示
$rows = array();
while($row = $re -> fetch(PDO::FETCH_ASSOC)){
    $rows[] = $row;
}

//jsonとして抽出
header('Context-type: application/json; charset=utf-8');
echo json_encode($rows);
?>