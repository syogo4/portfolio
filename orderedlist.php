<?php
$recv = json_decode(file_get_contents('php://input'), true);
$user = isset($recv['user']) ? $recv['user'] : -1;
//SQLサーバーへ接続
$s = new PDO("mysql:host=localhost; dbname=liquorstoredb", "root","root");
//データ取得
$re = $s -> query(
    "SELECT b_name, b_price, o_quantity, o_data FROM orders as o 
    INNER JOIN beers AS b ON o.o_b_id = b.b_id WHERE o_m_id = $user 
    UNION SELECT '合計' AS b_name,sum(b_price * o_quantity) AS b_price, 0 AS  o_quantity, '' AS  o_data FROM orders AS o 
    INNER JOIN beers AS b ON o.o_b_id = b.b_id WHERE o_m_id = $user"
);

//一行ずつ表示
$rows = array();
while($row = $re -> fetch(PDO::FETCH_ASSOC)){
    $rows[] = $row;
}

//jsonとして抽出
header('Context-type: application/json; charset=utf-8');
echo json_encode($rows);
?>