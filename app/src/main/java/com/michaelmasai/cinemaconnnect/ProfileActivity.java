package com.michaelmasai.cinemaconnnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    TextView userName, userEmail;
    Button logoutBtn;
    RecyclerView ticketsRecycler;

    FirebaseAuth auth;
    FirebaseFirestore db;
    RecyclerView ticketRecycler;
    TicketAdapter ticketAdapter;
    List<TicketModel> ticketList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);
        logoutBtn = findViewById(R.id.logoutBtn);

        ticketRecycler = findViewById(R.id.ticketRecycler);
        ticketRecycler.setLayoutManager(new LinearLayoutManager(this));

        ticketList = new ArrayList<>();
        ticketAdapter = new TicketAdapter(this, ticketList);
        ticketRecycler.setAdapter(ticketAdapter);

        loadUserTickets();


        String uid = auth.getCurrentUser().getUid();

        db.collection("users").document(uid).get()
                .addOnSuccessListener(doc -> {
                    userName.setText(doc.getString("name"));
                    userEmail.setText(doc.getString("email"));
                });

        logoutBtn.setOnClickListener(v -> {
            auth.signOut();
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void loadUserTickets() {
        String uid = auth.getCurrentUser().getUid();

        db.collection("tickets")
                .whereEqualTo("userId", uid)
                .get()
                .addOnSuccessListener(snap -> {
                    ticketList.clear();
                    for (var d : snap.getDocuments()) {
                        TicketModel t = d.toObject(TicketModel.class);
                        ticketList.add(t);
                    }
                    ticketAdapter.notifyDataSetChanged();
                });
    }

}
