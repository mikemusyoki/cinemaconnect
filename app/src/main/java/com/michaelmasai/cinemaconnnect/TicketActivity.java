package com.michaelmasai.cinemaconnnect;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.content.Intent;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;

public class TicketActivity extends AppCompatActivity {

    TextView tvCinema, tvMovie, tvSeats, tvSnacks, tvTotal, ticketScreen;
    ImageView qrCodeImage;
    Button btnBackHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        tvCinema = findViewById(R.id.tvCinema);
        tvMovie = findViewById(R.id.tvMovie);
        ticketScreen = findViewById(R.id.ticketScreen);
        tvSeats = findViewById(R.id.tvSeats);
        tvSnacks = findViewById(R.id.tvSnacks);
        tvTotal = findViewById(R.id.tvTotal);
        qrCodeImage = findViewById(R.id.qrCodeImage);
        btnBackHome = findViewById(R.id.btnBackHome);

        Intent intent = getIntent();
        String cinema = intent.getStringExtra("cinemaName");
        String movie = intent.getStringExtra("movieName");
        String screenName = intent.getStringExtra("screenName");
        String seats = intent.getStringExtra("seats");
        String snacks = intent.getStringExtra("snacks");
        int total = intent.getIntExtra("totalPrice", 0);

        tvCinema.setText("Cinema: " + cinema);
        tvMovie.setText("Movie: " + movie);
        ticketScreen.setText("Screen: " + screenName);
        tvSeats.setText("Seats: " + seats);
        tvSnacks.setText("Snacks: " + snacks);
        tvTotal.setText("Total: KSh " + total);

        String qrData =
                "Cinema: " + cinema + "\n" +
                        "Movie: " + movie + "\n" +
                        "Screen: " + screenName + "\n" +
                        "Seats: " + seats + "\n" +
                        "Snacks: " + snacks + "\n" +
                        "Total: KSh " + total;

        generateQRCode(qrData);

        btnBackHome.setOnClickListener(v -> {
            Intent i = new Intent(TicketActivity.this, HomeActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        });
    }

    private void generateQRCode(String data) {
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 600, 600);
            Bitmap bmp = Bitmap.createBitmap(600, 600, Bitmap.Config.RGB_565);

            for (int x = 0; x < 600; x++) {
                for (int y = 0; y < 600; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? android.graphics.Color.BLACK : android.graphics.Color.WHITE);
                }
            }

            qrCodeImage.setImageBitmap(bmp);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
