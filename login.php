<?php
//SQLサーバーへ接続
$s = new PDO("mysql:host=localhost; dbname=liquorstoredb", "root","root");
//データ取得
$re = $s -> query("SELECT m_id , m_name, m_root, m_pass FROM members");
//一行ずつ表示
$rows = array();
while($row = $re -> fetch(PDO::FETCH_ASSOC)){
    $rows[] = $row;
}

//jsonとして抽出
header('Context-type: application/json; charset=utf-8');
echo json_encode($rows);
?>