package to.msn.wings.liquorstore;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MemberOrderedActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_ordered);

        Button btTop = findViewById(R.id.btTop);
        btTop.setOnClickListener(v->{
            finish();
        });
    }
}
