package com.androidcoding.riyaisrani;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class Issues extends AppCompatActivity {

    //Initialise
    ImageButton back_button;
    EditText repositoryName, issueNum;
    Button issue;
    String ownerName;
    TextView users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issues);

        //Assign values
        Bundle extras = getIntent().getExtras();
        ownerName = extras.getString("issues");

        users = findViewById(R.id.userIssue);
        users.setText("User: " + ownerName);

        repositoryName = findViewById(R.id.repositoryName);
        issueNum = findViewById(R.id.issueNum);

        issue = findViewById(R.id.btn_issue);
        issue.setOnClickListener(this::onClick);

        back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(this::onClick);

    }

    //Perform onClick
    public void onClick(View view){
        if(view.getId() == R.id.btn_issue){
            Intent intent = new Intent(Issues.this, IssueActivity.class);

            intent.putExtra("ownerName", ownerName);
            intent.putExtra("repoName", repositoryName.getText().toString());
            intent.putExtra("issueNum", issueNum.getText().toString());

            if(repositoryName.getText().toString().equals("")) {
                repositoryName.setHint("Field cannot be empty");
                repositoryName.setHintTextColor(getResources().getColor(R.color.red));
            }

            else if(issueNum.getText().toString().equals("")){
                issueNum.setHint("Field cannot be empty");
                issueNum.setHintTextColor(getResources().getColor(R.color.red));
            }
            else{
                repositoryName.setHint("Name of Project");
                issueNum.setHint("Issue Number");
                startActivity(intent);
            }
        }
        else if(view.getId() == R.id.back_button){
            finish();
        }
    }
}