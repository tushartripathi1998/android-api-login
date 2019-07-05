package com.example.nextstepsession;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    SharedPreferences prf;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        TextView response = (TextView)findViewById(R.id.response);
        Button logout = (Button)findViewById(R.id.logout);
        prf = getSharedPreferences("user_details",MODE_PRIVATE);
        intent = new Intent(DetailActivity.this,MainActivity.class);
        response.setText("Hello, "+prf.getString("username",null));
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = prf.edit();
                editor.clear();
                editor.commit();
                startActivity(intent);
            }
        });
    }
}
