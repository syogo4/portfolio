package to.msn.wings.liquorstore;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    private EditText username;
    private EditText userpass;
    private Button btLogin;
    private  Button btNew;
    private Members members;
    private  int loginposition = -1;
    private ArrayList<Members> mlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        username = findViewById(R.id.edName);
        userpass = findViewById(R.id.edPass);
        btLogin = findViewById(R.id.btLogin);
        btNew = findViewById(R.id.btNow);

        //memberテーブルの受信
        Network logdl = new Network() {
            @Override
            public String accessURI() {
                return "http://10.1.1.10/login.php";
            }

            @Override
            public String onSend() {
                return "";
            }

            @Override
            public void onResponse(String result) {
                try {
                    JSONArray ary = new JSONArray(result);
                    for (int i = 0; i < ary.length(); i++) {
                        JSONObject ln = ary.getJSONObject(i);
                        members = new Members();
                        members.setmId(ln.getInt("m_id"));
                        members.setmName(ln.getString("m_name"));
                        members.setmPass(ln.getString("m_pass"));
                        members.setmRoot(ln.getString("m_root"));
                        mlist.add(members);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        logdl.execute();
        //ログイン時データアップデート
        Network logUp = new Network() {
            @Override
            public String accessURI() {
                return "http://10.1.1.10/loginup.php";
            }

            @Override
            public String onSend() {
                JSONObject job = new JSONObject();
                try {
                    job.put("m_id", mlist.get(loginposition).getmId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return job.toString();
            }

            @Override
            public void onResponse(String result) {

            }
        };
        //membersテーブルに新規登録
        Network memberUp = new Network() {
            @Override
            public String accessURI() {
                return "http://10.1.1.10/members_detail.php";
            }

            @Override
            public String onSend() {
                JSONArray ary = new JSONArray();
                try{
                        JSONObject job = new JSONObject();
                        job.put("m_name",username.getText().toString());
                        job.put("m_pass",userpass.getText().toString());
                        ary.put(job);
                }catch (JSONException e){
                    e.printStackTrace();
                }
                return ary.toString();
            }

            @Override
            public void onResponse(String result) {

            }
        };

        //ログインボタン
        btLogin.setOnClickListener(v -> {
            for (int i = 0; i < mlist.size(); i++) {
                if (username.getText().toString().equals(mlist.get(i).getmName()) && userpass.getText().toString().equals(mlist.get(i).getmPass())) {
                    logUp.execute();
                    loginposition = i;
                    Intent main = new Intent(this,MainActivity.class);
                    main.putExtra("userName", username.getText().toString());
                    main.putExtra("userId",mlist.get(loginposition).getmId());
                    main.putExtra("login", true);
                    startActivity(main);
                    break;
                }
            }
            if (loginposition < 0) {
                Toast.makeText(this, R.string.notpass, Toast.LENGTH_LONG).show();
            }
        });
        //新規登録ボタン
        btNew.setOnClickListener(v->{
            for (int i = 0; i < mlist.size(); i++) {
                if (username.getText().toString().equals(mlist.get(i).getmName()) && userpass.getText().toString().equals(mlist.get(i).getmPass())) {
                    loginposition = i;
                    break;
                }
            }
            if (loginposition >= 0) {
                Toast.makeText(this, R.string.notNew, Toast.LENGTH_LONG).show();
            }else{
                memberUp.execute();
                Intent main = new Intent(this,MainActivity.class);
                main.putExtra("login", true);
                startActivity(main);
            }

        });
    }
}
