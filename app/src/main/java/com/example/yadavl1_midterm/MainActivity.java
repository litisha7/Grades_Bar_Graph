package com.example.yadavl1_midterm;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity implements DialogInterface.OnClickListener {

    // Class members
    EditText etTotal;
    Button btnCompute;
    ArrayList<EditText> etList;
    int total;
    double[] ratios;
    Iterator<EditText> iter;

    // Define the alphabets array
    static char[] alphabets = {'A','B','C','D','F'};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ratios = new double[5];

        // Initialize views from the layout
        etTotal = findViewById(R.id.etTotal);
        btnCompute = findViewById(R.id.btnCompute);

        // Form array list of EditTexts for easier iteration
        etList = new ArrayList<>();
        etList.add(findViewById(R.id.etA));
        etList.add(findViewById(R.id.etB));
        etList.add(findViewById(R.id.etC));
        etList.add(findViewById(R.id.etD));
        etList.add(findViewById(R.id.etE));

        iter = etList.iterator();
    }

    private boolean validateInput(ArrayList<EditText> etList) {

        // Local variables for this function
        boolean isValid = true;
        String valueEntered;
        iter = etList.iterator();

        if (etTotal.getText().toString().isEmpty()) {
            isValid = false;
            etTotal.setError("Please enter a value!");
        }
        while (iter.hasNext()) {

            EditText currentET = iter.next();

            // Check if all the fields have valid values
            valueEntered = currentET.getText().toString();
            if (valueEntered.isEmpty()) {
                // The field is empty
                isValid = false;
                currentET.setError("Please enter a value!");
            } else if (!etTotal.getText().toString().isEmpty()) {

                // Variable total initialized
                total = Integer.parseInt(etTotal.getText().toString());

                if (total < Integer.parseInt(valueEntered)) {

                    // Value entered is greater than the total points, hence invalid
                    isValid = false;
                    currentET.setError("Value should be less than total!");
                }
            } else {
                currentET.setError("");
            }
        }
        return isValid;
    }

    public void Compute(View view) {
        // Validate if all inputs are valid
        if (validateInput(etList)) {
            iter = etList.iterator();
            double temp;
            int i = 0;
            while (iter.hasNext()) {
                EditText current = iter.next();
                temp = Double.parseDouble(current.getText().toString());
                ratios[i++] = (temp / total) * 100.0;
            }
            showAlertDialog();
        }
    }

    private void showAlertDialog() {
        // Create the dialog fragment with the supplied title, message and buttons
        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.lblPopUpMsg)).append("\n");

        for (int i = 0; i < ratios.length; i++) {
            sb.append(alphabets[i]).append("s : ").append(ratios[i]).append(" %\n");
        }
        String msg_ratios = sb.toString();

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(msg_ratios);
        alertDialogBuilder.setPositiveButton(R.string.btnPlot, this);
        alertDialogBuilder.create().show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        // Go back to activity to show bar charts
        Intent i = new Intent(this, GraphActivity.class);
        Bundle bundle = new Bundle();
        bundle.putDoubleArray("ratios", ratios);
        i.putExtras(bundle);
        startActivity(i);
    }
}
