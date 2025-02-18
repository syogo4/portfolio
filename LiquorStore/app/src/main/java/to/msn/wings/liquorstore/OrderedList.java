package to.msn.wings.liquorstore;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrderedList extends AppCompatActivity {
private ListView liOrdered;
private ArrayList<ListItem> odlist = new ArrayList<>();
private  OrderListadapter adapter;
private Button btTop;
private int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderd_list);

        liOrdered = findViewById(R.id.od_list);

        userId = getIntent().getIntExtra("userId",-1);
        //オーダーデータベースから受信
        Network ordereddl = new Network() {
            @Override
            public String accessURI() {
                return "http://10.1.1.10/orderedlist.php";
            }

            @Override
            public String onSend() {
                JSONObject job = new JSONObject();
                try{
                    job.put("user", userId);
                }catch (JSONException e){
                    e.printStackTrace();
                }
                return job.toString();
            }

            @Override
            public void onResponse(String result) {
                try{
                    JSONArray ary = new JSONArray(result);
                    for(int i = 0; i < ary.length(); i++){
                        JSONObject ln = ary.getJSONObject(i);
                        ListItem item = new ListItem();
                        item.setbName(ln.getString("b_name"));
                        item.setbPrice(ln.getInt("b_price"));
                        item.setO_quantity(ln.getInt("o_quantity"));
                        item.setO_data(ln.getString("o_data"));
                        odlist.add(item);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
                //リストビュー
                adapter = new OrderListadapter(OrderedList.this, odlist,R.layout.member_order_list);
                liOrdered.setAdapter(adapter);
            }
        };
        ordereddl.execute();

        //トップ画面に戻るボタン
        btTop = findViewById(R.id.btTop2);
        btTop.setOnClickListener(v->{
            finish();
        });
    }
}