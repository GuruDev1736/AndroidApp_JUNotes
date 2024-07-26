package com.gurudev.junotes.User.Activities.TrackProgress;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.graphics.Color;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.gurudev.junotes.R;


public class track_performance extends AppCompatActivity {

    private TextInputEditText ct1Input;
    private ProgressBar progressBar;
    private TextView percentageText, percentageSign, commentText, statusText;
    private MaterialButton trackPerformanceButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.track_performance_item);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Initialize the views
        ct1Input = findViewById(R.id.ct1);
        progressBar = findViewById(R.id.performance_progress);
        percentageText = findViewById(R.id.percentageText);
        percentageSign = findViewById(R.id.percentagesign);
        commentText = findViewById(R.id.commentText);
        statusText = findViewById(R.id.statusText);
        trackPerformanceButton = findViewById(R.id.tp_btn);
       // Set progressbar min value to 0 and max value to 100
        progressBar.setMin(0);
        progressBar.setMax(100);

        // Set button click listener
        trackPerformanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculatePerformance();
            }
        });
    }
    private void calculatePerformance() {
        String inputString = ct1Input.getText().toString().trim();

        if (inputString.isEmpty()) {
            ct1Input.setError("Required");
            return;
        }

//        double ct1Marks = Double.parseDouble(ct1String);
//
//        double totalMarks = ct1Marks;
//        double percentage = (totalMarks / 150) * 100;

        double percentage;
        if (inputString.startsWith("CGPA:")) {
            // Process CGPA input
            String cgpaString = inputString.replace("CGPA:", "").trim();
            double cgpa = Double.parseDouble(cgpaString);
            percentage = (cgpa / 10) * 100;
        } else {
            // Process CT1 marks input
            double ct1Marks = Double.parseDouble(inputString);
            percentage = (ct1Marks / 150) * 100;
        }

        // Update the percentage text
        percentageText.setText(String.format("%.0f", percentage));
        percentageSign.setText("%");

        //update the progress bar progress according to percentage

        // Update the status text
        if (percentage >= 40) {
            statusText.setText("PASS");
            statusText.setTextColor(Color.GREEN);
        } else if (percentage == 0) {
            statusText.setText(" ");
            statusText.setTextColor(Color.WHITE);
        } else {
            statusText.setText("FAIL");
            statusText.setTextColor(Color.RED);
        }

        // Update the comment text
        if (percentage > 90) {
            commentText.setText("Excellent");
        } else if (percentage > 80) {
            commentText.setText("Awesome");
        } else if (percentage > 70) {
            commentText.setText("Very Good");
        } else if (percentage > 60) {
            commentText.setText("Good");
        } else if (percentage > 50) {
            commentText.setText("Nice");
        } else if (percentage == 0){
            commentText.setText(" ");
        } else {
            commentText.setText("Need To Work Hard");
        }

        // Update the progress bar
        progressBar.setProgress((int) percentage);
        if (percentage >= 90) {
            progressBar.getProgressDrawable().setColorFilter(Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN);
        } else if (percentage >= 80) {
            progressBar.getProgressDrawable().setColorFilter(Color.YELLOW, android.graphics.PorterDuff.Mode.SRC_IN);
        } else if (percentage >= 70) {
            progressBar.getProgressDrawable().setColorFilter(Color.parseColor("#FFA500"), android.graphics.PorterDuff.Mode.SRC_IN); // Orange
        } else if (percentage >= 60) {
            progressBar.getProgressDrawable().setColorFilter(Color.BLUE, android.graphics.PorterDuff.Mode.SRC_IN);
        } else if (percentage >= 50) {
            progressBar.getProgressDrawable().setColorFilter(Color.parseColor("#00BCD4"), android.graphics.PorterDuff.Mode.SRC_IN); // Cyan
        } else {
            progressBar.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
        }
    }
}