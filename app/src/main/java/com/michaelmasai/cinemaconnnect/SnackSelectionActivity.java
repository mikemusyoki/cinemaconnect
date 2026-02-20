package com.michaelmasai.cinemaconnnect;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class SnackSelectionActivity extends AppCompatActivity {

    // --- UI Components ---
    private RecyclerView snackRecycler;
    private TextView tvTotalSnackPrice; // Renamed to tv... for clarity
    private Button btnProceed;

    // --- Adapters & Lists ---
    private SnackAdapter snackAdapter;
    private List<SnackModel> snackList;
    private final List<String> selectedSnacks = new ArrayList<>();

    // --- State Variables ---
    private int totalSnackPriceValue = 0;
    private int totalSeatPrice = 0;

    // --- Intent Data Variables ---
    private String screenName = "";
    private String selectedSeats = "";
    private String selectedMovie = "";
    private String selectedCinema = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snack_selection);

        // 1. Initialize UI Views
        initViews();

        // 2. Get data passed from the previous activity
        retrieveIntentData();

        // 3. Setup the RecyclerView and Data
        setupSnackRecyclerView();

        // 4. Setup Button Listeners
        setupListeners();
    }

    /**
     * Finds views by their ID.
     */
    private void initViews() {
        snackRecycler = findViewById(R.id.snackRecycler);
        tvTotalSnackPrice = findViewById(R.id.totalSnackPrice);
        btnProceed = findViewById(R.id.btnProceed);
    }

    /**
     * Retrieves data passed via Intent from the seat selection screen.
     */
    private void retrieveIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            screenName = intent.getStringExtra("screenName");
            selectedSeats = intent.getStringExtra("selectedSeats");
            totalSeatPrice = intent.getIntExtra("totalSeatPrice", 0);
            selectedMovie = intent.getStringExtra("movieName");
            selectedCinema = intent.getStringExtra("cinemaName");
        }
    }

    /**
     * Configures the RecyclerView, populates data, and handles selection logic.
     */
    private void setupSnackRecyclerView() {
        // Set Layout Manager (Grid)
        snackRecycler.setLayoutManager(new GridLayoutManager(this, 2));

        // Populate Data
        snackList = getSnackData();

        // Initialize Adapter with Click Logic
        snackAdapter = new SnackAdapter(snackList, snack -> {
            updateSnackTotals(snack);
        });

        snackRecycler.setAdapter(snackAdapter);
    }

    /**
     * Generates the list of available snacks.
     */
    private List<SnackModel> getSnackData() {
        List<SnackModel> list = new ArrayList<>();
        list.add(new SnackModel("🍿", "Popcorn",250));
        list.add(new SnackModel("🥤", "Soda", 200));
        list.add(new SnackModel("🌭", "Hot dog", 300));
        list.add(new SnackModel("🧀", "Nachos", 350));
        list.add(new SnackModel("🍦", "Ice-cream", 250));
        list.add(new SnackModel("💧", "Water", 150));
        return list;
    }

    /**
     * Logic to handle adding/removing price and items when a snack is clicked.
     */
    private void updateSnackTotals(SnackModel snack) {
        if (snack.isSelected()) {
            totalSnackPriceValue += snack.getPrice();
            selectedSnacks.add(snack.getName());
        } else {
            totalSnackPriceValue -= snack.getPrice();
            selectedSnacks.remove(snack.getName());
        }
        tvTotalSnackPrice.setText("Total: KSh " + totalSnackPriceValue);
    }

    /**
     * Sets up the Proceed button click listener.
     */
    private void setupListeners() {
        btnProceed.setOnClickListener(v -> navigateToCheckout());
    }

    /**
     * Calculates totals and navigates to the Checkout Activity.
     */
    private void navigateToCheckout() {
        int grandTotal = totalSeatPrice + totalSnackPriceValue;
        String snacksSummary = selectedSnacks.isEmpty() ? "None" : String.join(", ", selectedSnacks);

        Intent checkoutIntent = new Intent(SnackSelectionActivity.this, CheckoutActivity.class);
        checkoutIntent.putExtra("screenName", screenName);
        checkoutIntent.putExtra("selectedSeats", selectedSeats);
        checkoutIntent.putExtra("totalSeatPrice", totalSeatPrice);
        checkoutIntent.putExtra("totalSnackPrice", totalSnackPriceValue);
        checkoutIntent.putExtra("grandTotal", grandTotal);
        checkoutIntent.putExtra("selectedSnacks", snacksSummary);
        checkoutIntent.putExtra("movieName", selectedMovie);
        checkoutIntent.putExtra("cinemaName", selectedCinema);

        startActivity(checkoutIntent);
    }
}