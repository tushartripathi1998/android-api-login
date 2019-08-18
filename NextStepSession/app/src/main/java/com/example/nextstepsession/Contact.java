package com.example.nextstepsession;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

public class Contact extends BaseObservable {
    private float amount;

    public Contact(float amount){
        amount = this.amount;
    }

    @Bindable
    public float getAmount(){
        return this.amount;
    }

    public void setAmount(float amount){
        this.amount = amount;
        notifyPropertyChanged(BR.amount);
    }
}
