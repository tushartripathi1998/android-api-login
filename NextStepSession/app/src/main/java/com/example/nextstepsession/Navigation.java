package com.example.nextstepsession;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Navigation extends Fragment {

    View view;
    Button account;
    Button profile;

    NavigateOptions navigateOptions;

    public interface NavigateOptions{
        public void showProfileSettings();
        public void showAccountData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_navigation, container, false);

        account = (Button)view.findViewById(R.id.account);
        profile = (Button)view.findViewById(R.id.profile);

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateOptions.showAccountData();
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateOptions.showProfileSettings();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;

        try{
            navigateOptions = (NavigateOptions) activity;
        }catch(ClassCastException e){
            throw new ClassCastException(activity.toString() + "must ovverride method");
        }
    }
}
