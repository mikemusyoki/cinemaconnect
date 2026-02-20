package com.michaelmasai.cinemaconnnect;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.os.Handler;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PaymentActivity extends AppCompatActivity {

    LinearLayout mpesaOption, visaOption;
    Button btnPay;
    TextView summaryDetails, summaryTotal;

    String movie, cinema, screen, paymentMethod;
    List<String> seats;
    List<String> snacks;
    int totalPrice;

    FirebaseFirestore db;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        mpesaOption = findViewById(R.id.mpesaOption);
        visaOption = findViewById(R.id.visaOption);
        btnPay = findViewById(R.id.btnPay);
        summaryDetails = findViewById(R.id.summaryDetails);
        summaryTotal = findViewById(R.id.summaryTotal);

        // --- GET DATA ---
        movie = getIntent().getStringExtra("movie");
        cinema = getIntent().getStringExtra("cinema");
        screen = getIntent().getStringExtra("screen");

        String seatStr = getIntent().getStringExtra("seats");
        seats = Arrays.asList(seatStr.split(", "));

        String snackStr = getIntent().getStringExtra("snacks");
        snacks = Arrays.asList(snackStr.split(", "));

        totalPrice = getIntent().getIntExtra("totalPrice", 0);

        summaryDetails.setText(
                "- Movie: " + movie +
                        "\n- Cinema: " + cinema +
                        "\n- Screen: " + screen +
                        "\n- Seats: " + seatStr +
                        "\n- Snacks: " + snackStr
        );

        summaryTotal.setText("Total: KES " + totalPrice);

        // SELECT METHOD
        mpesaOption.setOnClickListener(v -> {
            paymentMethod = "MPESA";
            Toast.makeText(this, "M-PESA selected", Toast.LENGTH_SHORT).show();
        });

        visaOption.setOnClickListener(v -> {
            paymentMethod = "VISA";
            Toast.makeText(this, "VISA selected", Toast.LENGTH_SHORT).show();
        });

        // PAY BTN
        btnPay.setOnClickListener(v -> {
            if (paymentMethod == null) {
                Toast.makeText(this, "Select a payment method", Toast.LENGTH_SHORT).show();
                return;
            }

            if (paymentMethod.equals("MPESA")) showMpesaDialog();
            else showVisaDialog();
        });
    }

    // --- MPESA ---
    private void showMpesaDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_mpesa, null);
        builder.setView(view);

        EditText phoneInput = view.findViewById(R.id.phoneInput);
        Button btnConfirm = view.findViewById(R.id.btnConfirmMpesa);

        AlertDialog dialog = builder.create();
        dialog.show();

        btnConfirm.setOnClickListener(v -> {
            String phone = phoneInput.getText().toString().trim();
            if (phone.length() < 10) {
                phoneInput.setError("Enter valid phone number");
                return;
            }
            dialog.dismiss();
            simulateMpesaPayment();
        });
    }

    // --- VISA ---
    private void showVisaDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_visa, null);
        builder.setView(view);

        EditText cardNumber = view.findViewById(R.id.cardNumber);
        EditText expiry = view.findViewById(R.id.cardExpiry);
        EditText cvv = view.findViewById(R.id.cardCvv);
        EditText cardName = view.findViewById(R.id.cardName);
        Button btnPayVisa = view.findViewById(R.id.btnPayVisa);

        AlertDialog dialog = builder.create();
        dialog.show();

        btnPayVisa.setOnClickListener(v -> {

            if (cardNumber.getText().toString().trim().length() < 16) {
                cardNumber.setError("Invalid card number");
                return;
            }
            if (!expiry.getText().toString().contains("/")) {
                expiry.setError("Use MM/YY format");
                return;
            }
            if (cvv.getText().toString().length() < 3) {
                cvv.setError("Invalid CVV");
                return;
            }
            if (cardName.getText().toString().isEmpty()) {
                cardName.setError("Required");
                return;
            }

            dialog.dismiss();
            simulateVisaPayment();
        });
    }

    private void simulateMpesaPayment() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_processing, null);
        builder.setView(view);

        AlertDialog processing = builder.create();
        processing.show();

        new Handler().postDelayed(() -> {
            processing.dismiss();
            Toast.makeText(this, "M-PESA Payment Successful!", Toast.LENGTH_SHORT).show();
            saveTicket();
        }, 3000);
    }

    private void simulateVisaPayment() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_processing, null);
        builder.setView(view);

        AlertDialog processing = builder.create();
        processing.show();

        new Handler().postDelayed(() -> {
            processing.dismiss();
            Toast.makeText(this, "VISA Payment Successful!", Toast.LENGTH_SHORT).show();
            saveTicket();
        }, 3000);
    }

    // SAVE TICKET TO FIRESTORE
    private void saveTicket() {
        String ticketId = UUID.randomUUID().toString();
        String userId = auth.getCurrentUser().getUid();

        HashMap<String, Object> ticket = new HashMap<>();
        ticket.put("ticketId", ticketId);
        ticket.put("userId", userId);
        ticket.put("movie", movie);
        ticket.put("cinema", cinema);
        ticket.put("screen", screen);
        ticket.put("seats", seats);
        ticket.put("snacks", snacks);
        ticket.put("totalPrice", totalPrice);
        ticket.put("paymentMethod", paymentMethod);
        ticket.put("timestamp", System.currentTimeMillis());

        db.collection("tickets").document(ticketId).set(ticket)
                .addOnSuccessListener(unused -> openTicket(ticketId))
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }

    private void openTicket(String ticketId) {
        Intent i = new Intent(PaymentActivity.this, TicketActivity.class);
        i.putExtra("ticketId", ticketId);
        i.putExtra("cinemaName", cinema);
        i.putExtra("movieName", movie);
        i.putExtra("screenName", screen);
        i.putExtra("seats", String.join(", ", seats));
        i.putExtra("snacks", String.join(", ", snacks));
        i.putExtra("totalPrice", totalPrice);

        startActivity(i);
        finish();
    }
}
