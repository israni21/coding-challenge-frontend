package com.androidcoding.riyaisrani;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import android.widget.Button;
import android.widget.Toast;

import adapter.ReposAdapter;
import model.GitHubRepo;
import rest.ApiClient;
import rest.GitHubRepoEndPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repositories extends AppCompatActivity {

    //Initialise
    ImageButton back_button;
    String newString;
    TextView users, noRepoFound;
    Button btn_issues;
    RecyclerView recyclerView;
    Bundle extras;
    ArrayList<GitHubRepo> gitHubRepoList = new ArrayList<>();
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositories);

        //Assign values
        extras = getIntent().getExtras();
        newString = extras.getString("username");

        users = findViewById(R.id.users);
        users.setText("User: " + newString);

        noRepoFound = findViewById(R.id.noRepoFound);

        recyclerView = findViewById(R.id.repositories);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ReposAdapter(gitHubRepoList, R.layout.list_item_repo, getApplicationContext());
        recyclerView.setAdapter(adapter);

        btn_issues = findViewById(R.id.btn_issues);
        btn_issues.setOnClickListener(this::onClick);

        back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(this::onClick);

        //Function to load repositories
        loadRepositories();

    }

    //Perform onClick
    public void onClick(View view) {
        if (view.getId() == R.id.btn_issues){
            Intent intent = new Intent(Repositories.this, Issues.class);
            intent.putExtra("issues", newString);
            startActivity(intent);
        }
        else if (view.getId() == R.id.back_button) {
            finish();
        }
    }

    //Implement function to load repositories
    private void loadRepositories(){
        GitHubRepoEndPoint apiService = ApiClient.getClient().create(GitHubRepoEndPoint.class);
        Call<ArrayList<GitHubRepo>> call = apiService.getRepo(newString);
        call.enqueue(new Callback<ArrayList<GitHubRepo>>() {
            @Override
            public void onResponse(Call<ArrayList<GitHubRepo>> call, Response<ArrayList<GitHubRepo>> response) {

                    gitHubRepoList.clear();
                    gitHubRepoList.addAll(response.body());
                    adapter.notifyDataSetChanged();


            }

            @Override
            public void onFailure(Call<ArrayList<GitHubRepo>> call, Throwable t) {

                Log.d("Repos", t.toString());
            }
        });
    }
}