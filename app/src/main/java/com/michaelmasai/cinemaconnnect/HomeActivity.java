package com.michaelmasai.cinemaconnnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    RecyclerView cinemaRecycler;
    CinemaAdapter cinemaAdapter;
    List<CinemaModel> cinemaList;

    TextView welcomeText;

    FirebaseAuth auth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        loadUserName();

        cinemaRecycler = findViewById(R.id.cinemaRecycler);
        cinemaRecycler.setLayoutManager(new LinearLayoutManager(this));

        cinemaList = new ArrayList<>();



        // 🌟 Sample cinema data (temporary static data)
        cinemaList.add(new CinemaModel("Century Cinemax", "Garden City, Nairobi", 4.5,R.drawable.century));
        cinemaList.add(new CinemaModel("Anga Diamond Cinema", "Diamond Plaza 2, Nairobi", 5, R.drawable.anga));
        cinemaList.add(new CinemaModel("Prestige Cinema", "Ngong Road, Nairobi", 4.7, R.drawable.prestige));
        cinemaList.add(new CinemaModel("Century Cinemax Sarit", "Sarit Centre, Nairobi", 4.6, R.drawable.century));
        cinemaList.add(new CinemaModel("Westgate Cinema", "Westlands,Nairobi", 4.2, R.drawable.westgate));
        cinemaList.add(new CinemaModel("Motion Cinema", "Greenspan Mall, Nairobi", 4.7, R.drawable.imax));

        cinemaAdapter = new CinemaAdapter(cinemaList, this::onCinemaSelected);
        cinemaRecycler.setAdapter(cinemaAdapter);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);

        bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_cinemas) {
                return true;
            }
            if (item.getItemId() == R.id.nav_profile) {
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                return true;
            }
            return false;
        });

    }

    private void loadUserName(){
        String userId = auth.getCurrentUser().getUid();
        db.collection("users").document(userId).get().addOnSuccessListener(doc -> {
            if(doc.exists()){
                String name = doc.getString("name");
                welcomeText = findViewById(R.id.welcomeText);
                welcomeText.setText("Welcome, " + name + "!");
            }
        });
    }

    private void onCinemaSelected(CinemaModel cinema) {
        Intent intent = new Intent(HomeActivity.this, MovieActivity.class);
        intent.putExtra("cinemaName", cinema.getName());
        startActivity(intent);

        Button btnMap = findViewById(R.id.btnMap);
        btnMap.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, MapActivity.class));
        });



    }
}
