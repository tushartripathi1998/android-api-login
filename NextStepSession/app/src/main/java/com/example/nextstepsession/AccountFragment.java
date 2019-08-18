package com.example.nextstepsession;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

public class AccountFragment extends Fragment {

    SharedPreferences prf;// = this.getActivity().getSharedPreferences("user_details",getActivity().MODE_PRIVATE);
    int userId;
    String server_url = new String("http://192.168.43.85:5000/fetchBankDetails");
    String getServer_url_money_send = new String("http://192.168.43.85:5000/moneySend");
    String add_url;// = "?userId="+userId;
    FetchData fetchData;
    View view;
    Intent intent;
    float amount;

    public interface FetchData{
//        public void showAccountData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        intent = new Intent(this.getActivity(),MainActivity.class);
        prf = this.getActivity().getSharedPreferences("user_details",getActivity().MODE_PRIVATE);
        userId = prf.getInt("userId",-1);
        add_url= "?userId="+userId;
        view = inflater.inflate(R.layout.fragment_account, container, false);
        fetchAccountDetails(userId, add_url);
        final Button sendMoney = (Button) view.findViewById(R.id.sendMoney);
        Button logout = (Button)view.findViewById(R.id.logout);
        sendMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMoneyDialog();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = prf.edit();
                editor.clear();
                editor.commit();
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;

        try{
            fetchData = (FetchData)activity;
        }catch(ClassCastException e){
            throw new ClassCastException(activity.toString() + "must ovverride method");
        }
    }

    public void sendMoneyDialog(){
        View promptsView = getLayoutInflater().inflate(R.layout.balance_popup, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptsView);

                final EditText recieverAccountObject = (EditText)promptsView.findViewById(R.id.accountNumber);
                final EditText amountToSendObject = (EditText)promptsView.findViewById(R.id.amount);
//
//                final String recieverAccount = recieverAccountObject.getText().toString();//Integer.parseInt(recieverAccountObject.getText().toString());
//                final String amountToSend = amountToSendObject.getText().toString();//Integer.parseInt(amountToSendObject.getText().toString());

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                TextView responseValue= (TextView)view.findViewById(R.id.response);
//                                responseValue.setText(Integer.toString(userId));
                                final int recieverAccount =Integer.parseInt(recieverAccountObject.getText().toString());
                                final int amountToSend = Integer.parseInt(amountToSendObject.getText().toString());
                                add_url= "?userId="+userId;
//                                        responseValue.setText(recieverAccount);
                                if(amount > amountToSend) {
                                    sendMoney(userId, recieverAccount, amountToSend, add_url);
//                                    fetchAccountDetails(userId, add_url);
                                }
                                else{
                                    dialog.cancel();
                                }
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }
                );
        fetchAccountDetails(userId, add_url);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void fetchAccountDetails(int userId,String add_url){
        final TextView responseValue= (TextView)view.findViewById(R.id.response);
        final TextView accNumber = (TextView)view.findViewById(R.id.accNumber);
        final TextView amt = (TextView)view.findViewById(R.id.amt);
        final TextView currencyType = (TextView)view.findViewById(R.id.currency);
        final TextView accType = (TextView)view.findViewById(R.id.accType);
        //    public void fetchAccountDetails(final int userId, String add_url){
        server_url = server_url+add_url;
//        responseValue.setText(server_url);
        final RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());

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
                            amount = balance.getInt("amount");
                            String currency = balance.getString("currency");

                            String accountType = jsonObject.getString("type");

//                            if(result.equals("credit")){
                            responseValue.setText("Hi "+prf.getString("username","null")+", your account details are :- ");
                            accNumber.setText(Integer.toString(accountNumber));

//                            Contact contact = new Contact(amount);
//
//                            detailsBinding = DataBindingUtil.setContentView(a,R.layout.details);
//                            detailsBinding.setContact(contact);

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

    public void sendMoney(int userId, int recieverAccount, int amount, String add_url){
        getServer_url_money_send = "http://192.168.43.85:5000/moneySend";
        getServer_url_money_send = getServer_url_money_send + add_url;

        final TextView status = view.findViewById(R.id.status);;

        JSONArray jsonArray=new JSONArray();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("recieverAccount", recieverAccount);
            jsonObject.put("amount",amount);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        jsonArray.put(jsonObject);
        final RequestQueue queue = Volley.newRequestQueue(this.getActivity().getApplicationContext());
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.PUT, getServer_url_money_send, jsonArray,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject jsonObject= (JSONObject)response.get(0);
                            status.setText(jsonObject.get("status").toString());//+Integer.toString(amount));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        getServer_url_money_send = "http://192.168.43.85:5000/moneySend";
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        queue.add(getRequest);
    }
}
