package com.michaelmasai.cinemaconnnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.io.File;
import java.io.FileOutputStream;

public class  AdminDashboardActivity extends AppCompatActivity {

    TextView totalUsers, totalTickets, totalRevenue, byCinemaList, byMovieList, dailyRevenueList;
    FirebaseFirestore db;
    BarChart barChartDailyRevenue;
    BarChart barChartCinema, barChartMovie;



    // -------VARIABLES USED FOR PDF & CHARTS -------
    int totalUsersValue = 0;
    int totalTicketsValue = 0;
    int totalRevenueValue = 0;

    HashMap<String, Integer> cinemaCountMap = new HashMap<>();
    HashMap<String, Integer> movieCountMap = new HashMap<>();
    HashMap<String, Integer> dailyRevenueMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        totalUsers = findViewById(R.id.totalUsers);
        totalTickets = findViewById(R.id.totalTickets);
        totalRevenue = findViewById(R.id.totalRevenue);
        byCinemaList = findViewById(R.id.byCinemaList);
        byMovieList = findViewById(R.id.byMovieList);
        dailyRevenueList = findViewById(R.id.dailyRevenueList);
        Button btnExportPdf = findViewById(R.id.btnExportPdf);

        barChartDailyRevenue = findViewById(R.id.barChartDailyRevenue);
        barChartCinema = findViewById(R.id.barChartCinema);
        barChartMovie = findViewById(R.id.barChartMovie);



        db = FirebaseFirestore.getInstance();
        Button adminLogoutBtn = findViewById(R.id.adminLogoutBtn);

        adminLogoutBtn.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(AdminDashboardActivity.this, LoginActivity.class));
            finish();
        });


        btnExportPdf.setOnClickListener(v -> generatePdfReport());

        loadTotalUsers();
        loadTotalTicketsAndRevenue();
        loadTicketsByCinema();
        loadTicketsByMovie();
        loadDailyRevenue();
    }

    // ------------------ TOTAL USERS -------------------
    private void loadTotalUsers() {
        db.collection("users").get()
                .addOnSuccessListener(query -> {
                    totalUsersValue = query.size();
                    totalUsers.setText("Total Users: " + totalUsersValue);
                });
    }

    // ------------------ TICKETS + REVENUE -------------------
    private void loadTotalTicketsAndRevenue() {
        db.collection("tickets").get()
                .addOnSuccessListener(query -> {
                    totalTicketsValue = query.size();
                    int revenue = 0;

                    for (com.google.firebase.firestore.DocumentSnapshot doc : query.getDocuments()) {
                        Long price = doc.getLong("totalPrice");
                        if (price != null) revenue += price.intValue();
                    }

                    totalRevenueValue = revenue;

                    totalTickets.setText("Total Tickets Sold: " + totalTicketsValue);
                    totalRevenue.setText("Total Revenue: KSh " + totalRevenueValue);
                });
    }

    // ------------------ TICKETS BY CINEMA -------------------
    private void loadTicketsByCinema() {
        db.collection("tickets").get()
                .addOnSuccessListener(query -> {

                    cinemaCountMap.clear();
                    HashMap<String, Integer> cinemaRevenue = new HashMap<>();

                    StringBuilder output = new StringBuilder();

                    for (com.google.firebase.firestore.DocumentSnapshot doc : query.getDocuments()) {
                        String cinema = doc.getString("cinema");
                        Long priceLong = doc.getLong("totalPrice");

                        if (cinema == null) continue;

                        int price = (priceLong != null) ? priceLong.intValue() : 0;

                        // COUNT
                        if (cinemaCountMap.containsKey(cinema)) {
                            cinemaCountMap.put(cinema, cinemaCountMap.get(cinema) + 1);
                        } else {
                            cinemaCountMap.put(cinema, 1);
                        }

                        // REVENUE
                        if (cinemaRevenue.containsKey(cinema)) {
                            cinemaRevenue.put(cinema, cinemaRevenue.get(cinema) + price);
                        } else {
                            cinemaRevenue.put(cinema, price);
                        }
                    }

                    for (String c : cinemaCountMap.keySet()) {
                        output.append(c)
                                .append(" – ")
                                .append(cinemaCountMap.get(c))
                                .append(" tickets – KSh ")
                                .append(cinemaRevenue.get(c))
                                .append("\n");
                    }


                    byCinemaList.setText(output.length() == 0 ? "No ticket data." : output.toString());

                    drawCinemaChart();

                });
    }

    // ------------------ TICKETS BY MOVIE -------------------
    private void loadTicketsByMovie() {
        db.collection("tickets").get()
                .addOnSuccessListener(query -> {

                    movieCountMap.clear();
                    HashMap<String, Integer> movieRevenue = new HashMap<>();

                    StringBuilder output = new StringBuilder();

                    for (com.google.firebase.firestore.DocumentSnapshot doc : query.getDocuments()) {
                        String movie = doc.getString("movie");
                        Long priceLong = doc.getLong("totalPrice");

                        if (movie == null) continue;

                        int price = (priceLong != null) ? priceLong.intValue() : 0;

                        // COUNT
                        if (movieCountMap.containsKey(movie)) {
                            movieCountMap.put(movie, movieCountMap.get(movie) + 1);
                        } else {
                            movieCountMap.put(movie, 1);
                        }

                        // REVENUE
                        if (movieRevenue.containsKey(movie)) {
                            movieRevenue.put(movie, movieRevenue.get(movie) + price);
                        } else {
                            movieRevenue.put(movie, price);
                        }
                    }

                    for (String name : movieCountMap.keySet()) {
                        output.append(name)
                                .append(" – ")
                                .append(movieCountMap.get(name))
                                .append(" tickets – KSh ")
                                .append(movieRevenue.get(name))
                                .append("\n");
                    }

                    byMovieList.setText(output.length() == 0 ? "No ticket data." : output.toString());

                    drawMovieChart();
                });
    }

    // ------------------ DAILY REVENUE + CHART -------------------
    private void loadDailyRevenue() {
        db.collection("tickets").get()
                .addOnSuccessListener(query -> {

                    dailyRevenueMap.clear();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                    StringBuilder output = new StringBuilder();

                    for (com.google.firebase.firestore.DocumentSnapshot doc : query.getDocuments()) {
                        Long timestamp = doc.getLong("timestamp");
                        Long priceLong = doc.getLong("totalPrice");

                        if (timestamp == null || priceLong == null) continue;

                        int price = priceLong.intValue();
                        String day = sdf.format(new Date(timestamp));

                        if (dailyRevenueMap.containsKey(day)) {
                            dailyRevenueMap.put(day, dailyRevenueMap.get(day) + price);
                        } else {
                            dailyRevenueMap.put(day, price);
                        }
                    }

                    for (String day : dailyRevenueMap.keySet()) {
                        output.append(day)
                                .append(" – KSh ")
                                .append(dailyRevenueMap.get(day))
                                .append("\n");
                    }

                    dailyRevenueList.setText(output.length() == 0 ? "No daily revenue yet." : output.toString());

                    //  draw chart after data is ready
                    drawDailyRevenueChart();
                });
    }

    private void drawDailyRevenueChart() {
        if (dailyRevenueMap.isEmpty()) {
            barChartDailyRevenue.clear();
            return;
        }

        List<BarEntry> entries = new ArrayList<>();
        final List<String> labels = new ArrayList<>();

        int index = 0;
        for (Map.Entry<String, Integer> entry : dailyRevenueMap.entrySet()) {
            labels.add(entry.getKey());
            entries.add(new BarEntry((float) index, entry.getValue().floatValue()));
            index++;
        }

        BarDataSet dataSet = new BarDataSet(entries, "Revenue (KSh)");
        // Use a built-in Android color to avoid resource errors
        dataSet.setColor(getResources().getColor(android.R.color.holo_purple));

        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.9f);

        barChartDailyRevenue.setData(barData);
        barChartDailyRevenue.setFitBars(true);

        XAxis xAxis = barChartDailyRevenue.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);

        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                int i = (int) value;
                if (i >= 0 && i < labels.size()) {
                    return labels.get(i);
                }
                return "";
            }
        });

        barChartDailyRevenue.getDescription().setEnabled(false);
        barChartDailyRevenue.animateY(800);
        barChartDailyRevenue.invalidate();
    }
    private void drawCinemaChart() {
        if (cinemaCountMap.isEmpty()) {
            barChartCinema.clear();
            return;
        }

        List<BarEntry> entries = new ArrayList<>();
        final List<String> labels = new ArrayList<>();

        int index = 0;
        for (Map.Entry<String, Integer> entry : cinemaCountMap.entrySet()) {
            labels.add(entry.getKey());
            entries.add(new BarEntry(index, entry.getValue()));
            index++;
        }

        BarDataSet dataSet = new BarDataSet(entries, "Tickets Sold");
        dataSet.setColor(getResources().getColor(android.R.color.holo_blue_bright));

        BarData data = new BarData(dataSet);
        data.setBarWidth(0.9f);

        barChartCinema.setData(data);
        barChartCinema.setFitBars(true);

        XAxis xAxis = barChartCinema.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);

        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                int i = (int) value;
                if (i >= 0 && i < labels.size()) return labels.get(i);
                return "";
            }
        });

        barChartCinema.getDescription().setEnabled(false);
        barChartCinema.animateY(900);
        barChartCinema.invalidate();
    }
    private void drawMovieChart() {
        if (movieCountMap.isEmpty()) {
            barChartMovie.clear();
            return;
        }

        List<BarEntry> entries = new ArrayList<>();
        final List<String> labels = new ArrayList<>();

        int index = 0;
        for (Map.Entry<String, Integer> entry : movieCountMap.entrySet()) {
            labels.add(entry.getKey());
            entries.add(new BarEntry(index, entry.getValue()));
            index++;
        }

        BarDataSet dataSet = new BarDataSet(entries, "Tickets Sold");
        dataSet.setColor(getResources().getColor(android.R.color.holo_green_dark));

        BarData data = new BarData(dataSet);
        data.setBarWidth(0.9f);

        barChartMovie.setData(data);
        barChartMovie.setFitBars(true);

        XAxis xAxis = barChartMovie.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);

        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                int i = (int) value;
                if (i >= 0 && i < labels.size()) return labels.get(i);
                return "";
            }
        });

        barChartMovie.getDescription().setEnabled(false);
        barChartMovie.animateY(900);
        barChartMovie.invalidate();
    }



    // ------------------ PDF REPORT GENERATION -------------------
    private void generatePdfReport() {

        PdfDocument pdf = new PdfDocument();
        Paint paint = new Paint();

        PdfDocument.PageInfo pageInfo =
                new PdfDocument.PageInfo.Builder(595, 2000, 1).create();
        PdfDocument.Page page = pdf.startPage(pageInfo);
        Canvas canvas = page.getCanvas();

        paint.setTextSize(20f);
        paint.setFakeBoldText(true);
        canvas.drawText("CinemaConnect Admin Report", 40, 50, paint);

        paint.setFakeBoldText(false);
        paint.setTextSize(14f);

        int y = 100;

        // SUMMARY VALUES
        canvas.drawText("Total Users: " + totalUsersValue, 40, y, paint); y += 25;
        canvas.drawText("Total Tickets Sold: " + totalTicketsValue, 40, y, paint); y += 25;
        canvas.drawText("Total Revenue: KSh " + totalRevenueValue, 40, y, paint); y += 40;

        // -----------------------------------
        // ADD CHART IMAGES TO PDF
        // -----------------------------------

        // 1️⃣ DAILY REVENUE CHART
        if (barChartDailyRevenue != null) {
            Bitmap bmpDaily = barChartDailyRevenue.getChartBitmap();
            Bitmap scaledDaily = Bitmap.createScaledBitmap(bmpDaily, 500, 250, true);

            canvas.drawText("Daily Revenue Chart:", 40, y, paint);
            y += 20;
            canvas.drawBitmap(scaledDaily, 40, y, null);
            y += 270;
        }

        // 2️⃣ TICKETS BY CINEMA CHART
        if (barChartCinema != null) {
            Bitmap bmpCinema = barChartCinema.getChartBitmap();
            Bitmap scaledCinema = Bitmap.createScaledBitmap(bmpCinema, 500, 250, true);

            canvas.drawText("Tickets by Cinema:", 40, y, paint);
            y += 20;
            canvas.drawBitmap(scaledCinema, 40, y, null);
            y += 270;
        }

        // 3️⃣ TICKETS BY MOVIE CHART
        if (barChartMovie != null) {
            Bitmap bmpMovie = barChartMovie.getChartBitmap();
            Bitmap scaledMovie = Bitmap.createScaledBitmap(bmpMovie, 500, 250, true);

            canvas.drawText("Tickets by Movie:", 40, y, paint);
            y += 20;
            canvas.drawBitmap(scaledMovie, 40, y, null);
            y += 270;
        }

        pdf.finishPage(page);

        try {
            File downloads = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File file = new File(downloads, "Admin_Report.pdf");

            FileOutputStream fos = new FileOutputStream(file);
            pdf.writeTo(fos);
            fos.close();
            pdf.close();

            Toast.makeText(this, "PDF saved in Downloads/Admin_Report.pdf", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
