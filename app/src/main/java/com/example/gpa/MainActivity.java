package com.example.gpa;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.BreakIterator;
import java.text.DecimalFormat;



public class MainActivity extends AppCompatActivity {
    RadioButton Grade, Exams;
    EditText InputField, EdtCreditHrs;
    Button Add, Calculate, New, Exit;
    String Operation="grade";
    TextView Gpa,TxtGrade;

//    Grade Variabbles
    double qualityPoints,gpa, num = 0;
    int creditHrs, TcreditHrs;
    String Grades;

//    Exams Score Variables
    double Qpoints, exams, marks = 0;
    int creditHours, TcreditHours;
    double examGpa;
    DecimalFormat df = new DecimalFormat("0.00");
    int marksLength = 2;

    public void CheckFocus(){
        InputField.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus){
                String value = InputField.getText().toString();
                if (value.equalsIgnoreCase("A") || value.equalsIgnoreCase("B") || value.equalsIgnoreCase("A+")
                        || value.equalsIgnoreCase("B+") || value.equalsIgnoreCase("C+") || value.equalsIgnoreCase("C")
                        || value.equalsIgnoreCase("D+") || value.equalsIgnoreCase("D") || value.equalsIgnoreCase("F")) {
                } else {
                    InputField.getText().clear();
                    Toast.makeText(MainActivity.this, "Grade ranges from A+ to D and F", Toast.LENGTH_LONG).show();
                }
            }
        });
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Grade = findViewById(R.id.rbtGrade);
        Exams = findViewById(R.id.rbtExams);

        InputField = findViewById(R.id.edtGrade);
        EdtCreditHrs = findViewById(R.id.edtCreditH);

        Add = findViewById(R.id.btnAdd);
        Calculate = findViewById(R.id.btnCalc);
        New = findViewById(R.id.btnNew);
        Exit = findViewById(R.id.btnExit);

        Gpa = findViewById(R.id.txtGpa);
        TxtGrade = findViewById(R.id.txtGrade);

        Grade.setChecked(true);
        Calculate.setVisibility(View.INVISIBLE);
        New.setEnabled(false);
        Gpa.setEnabled(false);

        InputField.setFilters(new InputFilter[]{new InputFilter.LengthFilter(marksLength)});
        CheckFocus();

        Add.setBackgroundColor(Color.DKGRAY);
        Calculate.setBackgroundColor(Color.DKGRAY);
        New.setBackgroundColor(Color.DKGRAY);
        New.setTextColor(Color.WHITE);
        Exit.setBackgroundColor(Color.DKGRAY);

    }



    public void gradeClick(View view){
        marksLength = 2;
        InputField.setFilters(new InputFilter[]{new InputFilter.LengthFilter(marksLength)});
        CheckFocus();
        TxtGrade.setText("Grade:");
        InputField.setHint("Grade");
        InputField.setInputType(InputType.TYPE_CLASS_TEXT);
        InputField.setText(null);
        EdtCreditHrs.setText(null);
        Operation = "grade";

    }

    public void examsClick(View view){
        marksLength = 3;
        InputField.setFilters(new InputFilter[]{new InputFilter.LengthFilter(marksLength)});
        try {
            InputField.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() != 0){
                        try {
                           int examMarks = Integer.parseInt(InputField.getText().toString());
                           if (examMarks < 0 || examMarks > 100){
                               InputField.getText().clear();
                               Toast.makeText(MainActivity.this, "Enter Marks within 0 and 100",Toast.LENGTH_LONG).show();
                           }else {

                           }
                        }catch (NumberFormatException nf){
                            nf.printStackTrace();
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }catch (NumberFormatException nf ){
            nf.printStackTrace();
        }
        TxtGrade.setText("Exams Score:");
        InputField.setHint("Exams Score");
        InputField.setInputType(InputType.TYPE_CLASS_NUMBER);
        InputField.setText(null);
        EdtCreditHrs.setText(null);
        Operation = "score";

    }
    private void calcGrade() {
        creditHrs = Integer.parseInt(EdtCreditHrs.getText().toString());
        Grades = InputField.getText().toString();
        switch (Grades){
            case "a+":
            case "A+":
            num = 5.0 * creditHrs;
            break;
            case "a":
            case "A":
            num = 4.5 * creditHrs;
            break;
            case "b+":
            case "B+":
            num = 4.0 * creditHrs;
            break;
            case "b":
            case "B":
            num = 3.5 * creditHrs;
            break;
            case "c+":
            case "C+":
            num = 3.0 * creditHrs;
            break;
            case "c":
            case "C":
            num = 2.5 * creditHrs;
            break;
            case "d+":
            case "D+":
            num = 2.0 * creditHrs;
            break;
            case "d":
            case "D":
            num = 1.5 * creditHrs;
            break;
            case "f":
            case "F":
            num = 0 ;
            break;
        }
        TcreditHrs += creditHrs;
        qualityPoints += num;
    }

    public void calcExams(){
        exams = Double.parseDouble(InputField.getText().toString());
        creditHours = Integer.parseInt(EdtCreditHrs.getText().toString());

        if (exams >=85){
            marks = 5.0 * creditHours;
        }else if (exams >= 80 && exams <= 84){
            marks = 4.5 * creditHours;
        }else if (exams >= 75 && exams <= 79){
            marks = 4.0 * creditHours;
        }else if (exams >= 70 && exams <= 74){
            marks = 3.5 * creditHours;
        }else if (exams >= 65 && exams <= 69){
            marks = 3.0 * creditHours;
        }else if (exams >= 60 && exams <= 64){
            marks = 2.5 * creditHours;
        }else if (exams >= 55 && exams <= 59){
            marks = 2.0 * creditHours;
        }else if (exams >= 50 && exams <= 54){
            marks = 1.5 * creditHours;
        }else {
            marks = 0;
        }

        TcreditHours += creditHours;
        Qpoints += marks;
    }

    public void addClick(View view){
        if(Operation != null && Operation.equalsIgnoreCase("grade")) {
            if (InputField.getText().toString().isEmpty() || EdtCreditHrs.getText().toString().isEmpty()){
                Toast.makeText(this, "Enter Grade and Credit Hours",Toast.LENGTH_LONG).show();
            } else {
                calcGrade();
                Calculate.setVisibility(View.VISIBLE);
                InputField.setText(null);
                EdtCreditHrs.setText(null);
                New.setEnabled(true);

            }
        } else if (Operation != null && Operation.equalsIgnoreCase("score")) {
            if (InputField.getText().toString().isEmpty() || EdtCreditHrs.getText().toString().isEmpty()){
                Toast.makeText(this, "Enter Exams Scores and Credit Hours",Toast.LENGTH_LONG).show();
            } else {
                calcExams();
                Calculate.setVisibility(View.VISIBLE);
                InputField.setText(null);
                EdtCreditHrs.setText(null);
                New.setEnabled(true);

            }
        }
    }

    public void calcClick(View view){
        if(Operation != null && Operation.equalsIgnoreCase("grade")){
            gpa = qualityPoints / TcreditHrs;
            String ans;
            ans = df.format(gpa);
            Gpa.setText(ans);
        }
        else if (Operation != null && Operation.equalsIgnoreCase("score")){
            examGpa = Qpoints / TcreditHours;
            String result;
            result = df.format(examGpa);
            Gpa.setText(result);
        }

    }

    public void newClick(View view){
        Calculate.setVisibility(View.INVISIBLE);
        InputField.setText(null);
        EdtCreditHrs.setText(null);

        gpa = 0;
        TcreditHrs= 0;
        creditHrs = 0;
        qualityPoints = 0;
        examGpa = 0;
        Qpoints = 0;
        TcreditHours = 0;
        creditHours = 0;
        Gpa.setText("0.00");
        New.setEnabled(false);

    }

    public void exitClick(View view){
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setMessage("Do you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog xx = alert.create();
        xx.setTitle("Exit Application");
        xx.show();
    }


}