package com.example.nextstepsession;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity implements AccountFragment.FetchData, Navigation.NavigateOptions, AccountSettings.AccountSetting{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);

        if(findViewById(R.id.fragment_container)!=null){
            if(savedInstanceState!=null){
                return;
            }
            Navigation navigation = new Navigation();
            FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, navigation, null);
            fragmentTransaction1.commit();
        }

        if(findViewById(R.id.content_container)!=null){
            if(savedInstanceState!=null){
                return;
            }

            AccountFragment accountFragment = new AccountFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().add(R.id.content_container, accountFragment, null );
            fragmentTransaction.commit();
        }
    }

    @Override
    public void showAccountData() {
        AccountFragment accountFragment = new AccountFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.content_container, accountFragment, null );
        fragmentTransaction.commit();
    }

    @Override
    public void showProfileSettings(){
        AccountSettings accountSettings = new AccountSettings();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.content_container, accountSettings, null );
        fragmentTransaction.commit();
    }

}
