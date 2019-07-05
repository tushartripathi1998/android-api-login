package com.example.nextstepsession;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView responseText;
    EditText username;
    EditText password;
    Button button;
    Intent intent;
    SharedPreferences pref;
    String server_url = new String("http://192.168.43.85:5000/userAuthenticate1");
    StringBuilder appendString = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        button = (Button)findViewById(R.id.button);
        responseText = (TextView)findViewById(R.id.responseText);
        pref = getSharedPreferences("user_details",MODE_PRIVATE);
        intent = new Intent(MainActivity.this, DetailActivity.class);
        if(pref.contains("username") && pref.contains("password")){
            startActivity(intent);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String uname = username.getText().toString();
                final String pass = password.getText().toString();
                appendString.append("?username=").append(uname).append("&password=").append(pass);
                server_url = server_url+appendString;

                final RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

                JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, server_url, null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                // display response
//                                String responseVal = response.toString();
//                                responseText.setText(response.toString());
                                try {
                                    JSONObject jsonObject= (JSONObject)response.get(0);
//                                    responseText.setText(jsonObject.get("message").toString());
                                    final String result = jsonObject.get("message").toString();
                                    if(result.equals("authenticated")){
                                        SharedPreferences.Editor editor = pref.edit();
                                        editor.putString("username",uname);
                                        editor.putString("password",pass);
                                        editor.commit();
                                        startActivity(intent);
                                    }
                                    else{
                                        responseText.setText(jsonObject.get("message").toString());
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
//
//                                {
//                                    SharedPreferences.Editor editor = pref.edit();
//                                    editor.putString("username",uname);
//                                    editor.putString("password",pass);
//                                    editor.commit();
//                                    startActivity(intent); //trigers the login dashboard
//                                }
//                                Log.d("Response", response.toString());
                                queue.stop();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                responseText.setText(error.toString());
                                queue.stop();
                            }
                        }
                );
                queue.add(getRequest);
            }
        });
    }
}
