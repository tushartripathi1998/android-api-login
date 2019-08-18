package com.example.nextstepsession;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class AccountSettings extends Fragment {

    View view;
    Button upload;
    ImageView imageView;
    Intent intent = new Intent();
    private final int IMG_REQUEST = 1;
    Bitmap bitmap;

    AccountSetting accountSetting;
    public interface AccountSetting{

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_account_settings, container, false);
        upload = (Button)view.findViewById(R.id.upload);
        imageView = (ImageView)view.findViewById(R.id.image);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });



        return view;
    }

    private void selectImage(){
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null){
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path);
                TextView name = view.findViewById(R.id.name);
//                name.setText(bitmap); //wrong
                imageView.setImageBitmap(bitmap);
                imageView.setVisibility(view.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AccountSetting) {
            accountSetting = (AccountSetting) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
}
