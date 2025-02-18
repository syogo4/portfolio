package to.msn.wings.liquorstore;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView tvLoginName;
    private  TextView tvSelected;
    private  TextView tvSelectedP;
    private TextView tvSumPrice;
    private ListView slist;
    private ListView olist;
    private Spinner spQuantity;
    private  Button btLog;
    private  Button btOrder;
    private Button btAdd;
    private final ArrayList<ListItem> listBeer = new ArrayList<>();
    private final ArrayList<ListItem> listOrder = new ArrayList<>();
    private BeerListadapter Blistadapter;
    private OrderListadapter Olistadapter;
    private int Sposition;
    private int userId;
    private boolean login = false;
    private  int total = 0;
    //ログアウト
    private  Network logoutdl = new Network() {
        @Override
        public String accessURI() {
            return "http://10.1.1.10/logout.php";
        }

        @Override
        public String onSend() {
            return "";
        }

        @Override
        public void onResponse(String result) {
            login = false;
            btLog.setText(R.string.btLogin);
            tvLoginName.setText("");
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvLoginName = findViewById(R.id.tvLoginName);
        tvSelected = findViewById(R.id.tvSelected);
        tvSelectedP = findViewById(R.id.tvSelectedP);
        tvSumPrice = findViewById(R.id.tvSumprice);
        slist = findViewById(R.id.s_list);
        olist = findViewById(R.id.o_list);
        spQuantity = findViewById(R.id.spQuantity);
        btLog = findViewById(R.id.btLog);
        btOrder = findViewById(R.id.tbOrder);
        btAdd = findViewById(R.id.tbAdd);

        //beerテーブルデータダウンロード
        Network beerdl = new Network() {
            @Override
            public String accessURI() {
                return "http://10.1.1.10/beers.php";
            }

            @Override
            public String onSend() {
                return "";
            }

            @Override
            public void onResponse(String result) {
                try{
                    JSONArray ary = new JSONArray(result);
                    for(int i = 0; i < ary.length(); i++){
                        JSONObject ln = ary.getJSONObject(i);
                        ListItem item = new ListItem();
                        item.setbId(ln.getInt("b_id"));
                        item.setbName(ln.getString("b_name"));
                        item.setbPrice(ln.getInt("b_price"));
                        listBeer.add(item);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
                //商品リストビュー
                Blistadapter = new BeerListadapter(MainActivity.this, listBeer, R.layout.member_order_list);
                slist.setAdapter(Blistadapter);
            }
        };
        beerdl.execute();
        //orderテーブルへデータ追加
        Network orderdl = new Network() {
            @Override
            public String accessURI() {
                return "http://10.1.1.10/order_detail.php";
            }

            @Override
            public String onSend() {
                JSONArray ary = new JSONArray();
                try{
                    for(ListItem item : listOrder){
                        JSONObject job = new JSONObject();
                        job.put("o_m_id",item.getO_m_id());
                        job.put("o_b_id",item.getO_b_id());
                        job.put("o_quantity", item.getO_quantity());
                        ary.put(job);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
                return ary.toString();
            }

            @Override
            public void onResponse(String result) {
                listOrder.clear();
                tvSumPrice.setText("");
                btOrder.setText(R.string.liOrdered);
                Olistadapter.notifyDataSetChanged();
            }
        };

        //TextViewにデータを取得、追加
        login = getIntent().getBooleanExtra("login", false);
        if(login) {
          tvLoginName.setText(getIntent().getStringExtra("userName") + "  様");
          userId = getIntent().getIntExtra("userId",-1);
          btLog.setText(R.string.btLogout);
        }else{
            logoutdl.execute();
        }

        //商品リストビューをタップ時の処理
        slist.setOnItemClickListener((av,view,position,id)->{
            tvSelected.setText(listBeer.get(position).getbName());
            tvSelectedP.setText(listBeer.get(position).getbPrice() + "円");
            Sposition = position;
        });

        //ログイン、ログアウトボタン
        btLog.setOnClickListener(v->{
            if(login){
                   logoutdl.execute();
                   Toast.makeText(this,R.string.btLogout,Toast.LENGTH_LONG).show();
            }else {
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
            }
        });

        //注文ボタン
        if(listOrder.size() == 0){btOrder.setText(R.string.liOrdered);}
        btOrder.setOnClickListener(v->{
            if(!login){
                Toast.makeText(this,"ログインされていません",Toast.LENGTH_LONG).show();
            }else {
                if(btOrder.getText().equals(getString(R.string.tbOrder))) {
                    orderdl.execute();
                    Intent i = new Intent(this, MemberOrderedActivity.class);
                    startActivity(i);
                }
                else {
                    Intent i = new Intent(this, OrderedList.class);
                    i.putExtra("userId",userId);
                    startActivity(i);
                }
            }
        });
        //追加ボタン
        btAdd.setOnClickListener(v->{
            if(!login){
                Toast.makeText(this,"ログインされていません",Toast.LENGTH_LONG).show();
            }else {
                ListItem item = new ListItem();
                item.setO_m_id(userId);
                item.setO_b_id(listBeer.get(Sposition).getbId());
                item.setbName(listBeer.get(Sposition).getbName());
                item.setbPrice(listBeer.get(Sposition).getbPrice());
                item.setO_quantity(spQuantity.getSelectedItemPosition());
                listOrder.add(item);
                Olistadapter.notifyDataSetChanged();
                spQuantity.setSelection(1);

                //合計
                total += item.getbPrice() * item.getO_quantity();
                tvSumPrice.setText("合計　　　" + total + "円");
                btOrder.setText(R.string.tbOrder);
            }
        });

        //注文リストビュー
        Olistadapter = new OrderListadapter(this, listOrder, R.layout.member_order_list);
        olist.setAdapter(Olistadapter);
        //注文リスト長押しタップ時
        olist.setOnItemLongClickListener((av,view,position,id)-> {
            listOrder.remove(position);
            Olistadapter.notifyDataSetChanged();
            return true;
        });

        //スピナー生成
        createQuantitySpinner();

    }
    private void createQuantitySpinner(){
        ArrayList<String> list = new ArrayList<>();
        for(int i = 0; i <= 24; i++){
            list.add("" + i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,list);
        spQuantity.setAdapter(adapter);
        spQuantity.setSelection(1);
    }
}