package com.androidcoding.riyaisrani;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    //Initialise
    private EditText userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assign value
        userName = findViewById(R.id.enter_username);
        Button login = findViewById(R.id.btn_login);

        //Implement OnClickListener
        login.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, UserActivity.class);
            intent.putExtra("username_String", userName.getText().toString());
            if(userName.getText().toString().equals("")) {
                userName.setHint("Field cannot be empty");
                userName.setHintTextColor(getResources().getColor(R.color.red));
            }

            else{
                userName.setHint("Username");
                startActivity(intent);
            }
        });

    }

    public void ClickLogOut(View view){
        //Close app
        Logout(this);
    }

    public static void Logout(final Activity activity){
        //Initialise alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        //Set title
        builder.setTitle("Logout");
        //Set message
        builder.setMessage("Are you sure you want to logout?");
        //Positive yes button
        builder.setPositiveButton("YES", (dialog, which) -> {
            //Finish activity
            activity.finishAffinity();
            //Exit app
            System.exit(0);
        });
        //Negative no button
        builder.setNegativeButton("NO", (dialog, which) -> {
            //Dismiss dialog
            dialog.dismiss();
        });
        //Show dialog
        builder.show();
    }

}