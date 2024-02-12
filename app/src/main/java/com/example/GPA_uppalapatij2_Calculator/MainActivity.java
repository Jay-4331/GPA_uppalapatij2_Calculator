package com.example.GPA_uppalapatij2_Calculator;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText Grade1, Grade2, Grade3, Grade4, Grade5;
    TextView gpaResult;
    Button compute;
    ConstraintLayout bg_color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Grade1 = findViewById(R.id.Grade1);
        Grade2 = findViewById(R.id.Grade2);
        Grade3 = findViewById(R.id.Grade3);
        Grade4 = findViewById(R.id.Grade4);
        Grade5 = findViewById(R.id.Grade5);

        gpaResult = findViewById(R.id.gpaResult);
        compute = findViewById(R.id.compute);
        bg_color = findViewById(R.id.backgroundColor);

        compute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (compute.getText().equals("Clear Form")) {
                    clearForm();
                    return;
                }

                if (!validateInput()) {
                    return;
                }

                double gpa = calculateGPA();
                gpaResult.setText(String.format("%.2f", gpa));

                // This will change background color based on GPA
                if (gpa <= 60) {
                    bg_color.setBackgroundColor(Color.RED);
                } else if (gpa >= 61 && gpa <= 79) {
                    bg_color.setBackgroundColor(Color.YELLOW);
                } else {
                    bg_color.setBackgroundColor(Color.GREEN);
                }

                // Change button text to "Clear Form" after Computing the GPA
                compute.setText("Clear Form");
            }
        });

        EditText[] gradeFields = {Grade1, Grade2, Grade3, Grade4, Grade5};

        for (int i = 0; i < gradeFields.length; i++) {
            final EditText gradeField = gradeFields[i];
            gradeField.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    compute.setText("Compute GPA");
                    return false;
                }
            });
        }

    }
             //Actual Calculation of GPA
        private double calculateGPA() {
            double totalGradePoints = 0;
            double totalCredits = 0;

            totalGradePoints += getGradePoints(Grade1.getText().toString());
            totalGradePoints += getGradePoints(Grade2.getText().toString());
            totalGradePoints += getGradePoints(Grade3.getText().toString());
            totalGradePoints += getGradePoints(Grade4.getText().toString());
            totalGradePoints += getGradePoints(Grade5.getText().toString());

            totalCredits += 5; // assuming each course is 1 credit

            return totalGradePoints / totalCredits;
        }

    private int getGradePoints(String score) {
        try {
            return Integer.parseInt(score);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0; // This Return 0 if score is not a valid integer
        }
    }

     //This will generate the error message based on the input of user
    private boolean validateInput() {
        boolean isValid = true;

        EditText[] gradeFields = {Grade1, Grade2, Grade3, Grade4, Grade5};

        for (int i = 0; i < gradeFields.length; i++) {
            EditText gradeField = gradeFields[i];
            if (TextUtils.isEmpty(gradeField.getText().toString())) {
                gradeField.setError("Please enter a grade.");
                isValid = false;
            } else {
                int grade = Integer.parseInt(gradeField.getText().toString());
                if (grade < 0 || grade > 100) {
                    gradeField.setError("Grade must be between 0 and 100.");
                    isValid = false;
                }
            }
        }

        return isValid;
    }


    private void clearForm() {
        Grade1.setText("");
        Grade2.setText("");
        Grade3.setText("");
        Grade4.setText("");
        Grade5.setText("");
        gpaResult.setText("");
        compute.setText("Compute GPA");
        bg_color.setBackgroundColor(Color.WHITE);
    }
}
