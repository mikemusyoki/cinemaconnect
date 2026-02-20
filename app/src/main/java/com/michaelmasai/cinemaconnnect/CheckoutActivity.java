package com.michaelmasai.cinemaconnnect;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

public class CheckoutActivity extends AppCompatActivity {

    TextView seatInfo, snackInfo, totalAmount, txtScreen;
    Button btnConfirm;

    int seatTotal = 0;
    int snackTotal = 0;
    int grandTotal = 0;
    String selectedSeats = "";
    String selectedSnacks = "";
    String selectedMovie = "";
    String selectedCinema = "";
    String screenName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        seatInfo = findViewById(R.id.seatInfo);
        snackInfo = findViewById(R.id.snackInfo);
        totalAmount = findViewById(R.id.totalAmount);
        btnConfirm = findViewById(R.id.btnConfirm);
        txtScreen = findViewById(R.id.txtScreen);


        Intent intent = getIntent();
        screenName = intent.getStringExtra("screenName");
        selectedSeats = intent.getStringExtra("selectedSeats");
        seatTotal = intent.getIntExtra("totalSeatPrice", 0);
        snackTotal = intent.getIntExtra("totalSnackPrice", 0);
        selectedSnacks = intent.getStringExtra("selectedSnacks");
        selectedMovie = intent.getStringExtra("movieName");
        selectedCinema = intent.getStringExtra("cinemaName");
        grandTotal = seatTotal + snackTotal;

        txtScreen.setText("Screen: " + screenName);
        seatInfo.setText("Seats: " + selectedSeats + "\nSeat Total: KSh " + seatTotal);
        snackInfo.setText("Snacks: " + selectedSnacks + "\nSnack Total: KSh " + snackTotal);
        totalAmount.setText("Grand Total: KSh " + grandTotal);

        btnConfirm.setOnClickListener(v -> {
            Intent i = new Intent(CheckoutActivity.this, PaymentActivity.class);
            i.putExtra("movie", selectedMovie);
            i.putExtra("cinema", selectedCinema);
            i.putExtra("screen", screenName);
            i.putExtra("seats", selectedSeats);
            i.putExtra("snacks", selectedSnacks);
            i.putExtra("totalPrice", grandTotal);
            startActivity(i);
        });
    }
}
