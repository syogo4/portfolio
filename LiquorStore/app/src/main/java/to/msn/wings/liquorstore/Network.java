package to.msn.wings.liquorstore;

import  static android.os.Looper.getMainLooper;

import androidx.core.os.HandlerCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;

public interface Network {
    default void execute() {
        StringBuilder result = new StringBuilder();
        Executors.newSingleThreadExecutor().execute(() -> {
            try{
                //アドレスにアクセス
                String uri = accessURI();
                URL url = new URL(uri);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Context-type", "text/plain; charset=utf-8");
                con.setDoOutput(true);

                //リクエスト本体にデータ抽出
                OutputStream os = con.getOutputStream();
                PrintStream ps = new PrintStream(os);
                String msg = onSend();
                ps.print(msg);
                ps.close();

                //レスポンス結果取得
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        con.getInputStream(), StandardCharsets.UTF_8));
                String line;
                while((line = reader.readLine()) != null){
                    result.append(line);
                }
                HandlerCompat.createAsync(getMainLooper()).post(()->{
                    onResponse(result.toString());
                });
            }catch (IOException e){
                e.printStackTrace();
            }
        });
    }
    String accessURI();
    String onSend();
    void onResponse(String result);

}
