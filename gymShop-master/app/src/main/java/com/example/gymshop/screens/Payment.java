package com.example.gymshop.screens;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gymshop.R;

public class Payment extends AppCompatActivity
{

    EditText identityCardEditText, nameEditText, cardNumberEditText, cvvEditText;
    Spinner monthSpinner, yearSpinner;
    Button payButton;
    ArrayAdapter<CharSequence>monthAdapter, yearAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        identityCardEditText = findViewById(R.id.editTextIdentityCard);
        nameEditText = findViewById(R.id.editTextName);
        cardNumberEditText = findViewById(R.id.editTextCardNumber);
        cvvEditText = findViewById(R.id.editTextCVV);
        monthSpinner = findViewById(R.id.spinnerMonth);
        yearSpinner = findViewById(R.id.spinnerYear);
        payButton = findViewById(R.id.buttonPay);
        monthAdapter = ArrayAdapter.createFromResource(this, R.array.months_array, android.R.layout.simple_spinner_item);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(monthAdapter);
        monthSpinner.setSelection(0);

        yearAdapter = ArrayAdapter.createFromResource(this, R.array.years_array, android.R.layout.simple_spinner_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);
        yearSpinner.setSelection(0);

        payButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String name = nameEditText.getText().toString();
                String cardNumber = cardNumberEditText.getText().toString();
                String expiryDate = monthSpinner.getSelectedItem().toString() + "/" + yearSpinner.getSelectedItem().toString();
                String cvv = cvvEditText.getText().toString();

                if (name.isEmpty() || cardNumber.isEmpty() || cvv.isEmpty())
                {
                    Toast.makeText(Payment.this, "אנא מלא את כל השדות", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(Payment.this, "התשלום בוצע בהצלחה!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}