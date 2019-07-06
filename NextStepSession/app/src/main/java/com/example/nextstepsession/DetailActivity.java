package com.example.nextstepsession;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    SharedPreferences prf;
    Intent intent;
    String requestString;
    String server_url = new String("http://192.168.43.85:5000/fetchBankDetails");
    TextView responseValue;
    TextView accNumber;
    TextView amt;
    TextView currencyType;
    TextView accType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
//        response = (TextView)findViewById(R.id.response);
        Button logout = (Button)findViewById(R.id.logout);
        prf = getSharedPreferences("user_details",MODE_PRIVATE);
        responseValue = (TextView)findViewById(R.id.response);
        accNumber = (TextView)findViewById(R.id.accNumber);
        amt = (TextView)findViewById(R.id.amt);
        currencyType = (TextView)findViewById(R.id.currency);
        accType = (TextView)findViewById(R.id.accType);
        final int userId = prf.getInt("userId",-1);
        String add_url = "?userId="+userId;
        fetchAccountDetails(userId, add_url);
        intent = new Intent(DetailActivity.this,MainActivity.class);

//        responseValue.setText(add_url);
        //"Hello, "+prf.getString("username",null));
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

    public void fetchAccountDetails(final int userId, String add_url){
        server_url = server_url+add_url;
//        responseValue.setText(server_url);
        final RequestQueue queue = Volley.newRequestQueue(DetailActivity.this);

        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, server_url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
//                         display response
//                                String responseVal = response.toString();
//                                responseText.setText(response.toString());
                        try {
                            JSONObject jsonObject= (JSONObject)response.get(0);
//                                    responseText.setText(jsonObject.get("message").toString());


                            int accountNumber = jsonObject.getInt("account number");

                            JSONObject balance = (JSONObject)jsonObject.get("balance");
                            float amount = balance.getInt("amount");
                            String currency = balance.getString("currency");

                            String accountType = jsonObject.getString("type");

//                            if(result.equals("credit")){
                            responseValue.setText("Hi "+prf.getString("username","null")+", your account details are :- ");
                            accNumber.setText(Integer.toString(accountNumber));
                            amt.setText(Float.toString(amount));
                            currencyType.setText(currency);
                            accType.setText(accountType);

                                //jsonObject.get("message").toString());
                                server_url = "http://192.168.43.85:5000/fetchBankDetails";
//                            }
//                            else{
//                                server_url = "http://192.168.43.85:5001/fetchBankDetails";
//                                responseValue.setText(jsonObject.get("message").toString());
//                            }
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
                        responseValue.setText(error.toString());
                        queue.stop();
                    }
                }
        );
        queue.add(getRequest);
    }
}
