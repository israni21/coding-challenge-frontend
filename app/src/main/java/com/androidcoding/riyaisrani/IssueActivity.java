package com.androidcoding.riyaisrani;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import model.GitHubIssue;
import rest.ApiClient;
import rest.GitHubIssueEndPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IssueActivity extends AppCompatActivity {

    //Initialise
    ImageButton back_button;
    TextView projectName, title, body;
    String string1, string2, string3;
    int integer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue);

        //Assign value
        title = findViewById(R.id.title);
        body = findViewById(R.id.body);

        Bundle extras = getIntent().getExtras();
        string1 = extras.getString("ownerName");
        string2 = extras.getString("repoName");
        string3 = extras.getString("issueNum");
        integer = Integer.parseInt(string3);

        projectName = findViewById(R.id.projectName);
        projectName.setText("Project Name: " + string2);

        back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(this::onClick);

        //Function to load issue data
        loadIssueData();
    }

    //Perform onclick
    public void onClick(View view) {
        if (view.getId() == R.id.back_button) {
            finish();
        }
    }

    //Implement function to load issue data
    private void loadIssueData(){
        final GitHubIssueEndPoint apiService = ApiClient.getClient().create(GitHubIssueEndPoint.class);
        Call<GitHubIssue> call = apiService.getIssue(string1, string2, integer);

        call.enqueue(new Callback<GitHubIssue>() {
            @Override
            public void onResponse(Call<GitHubIssue> call, Response<GitHubIssue> response) {
                title.setText("Title: " + response.body().getTitle());
                body.setText("Body: " + response.body().getBody());
            }

            @Override
            public void onFailure(Call<GitHubIssue> call, Throwable t) {
                System.out.println("Failed!" + t.toString());
            }
        });
    }
}