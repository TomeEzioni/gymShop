package com.example.gymshop.screens;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gymshop.R;

public class Payment extends AppCompatActivity
{
    EditText identityCardEditText, nameEditText, cardNumberEditText, cvvEditText;
    TextView priceTextView;
    Spinner monthSpinner, yearSpinner;
    Button payButton;
    ImageButton bit, payBox, payPal;
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
        priceTextView = findViewById(R.id.textViewPriceValue);
        monthSpinner = findViewById(R.id.spinnerMonth);
        yearSpinner = findViewById(R.id.spinnerYear);
        payButton = findViewById(R.id.buttonPay);
        bit = findViewById(R.id.ibBit);
        payPal = findViewById(R.id.ibPayPal);
        payBox = findViewById(R.id.ibPayBox);

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
                String identityCard = identityCardEditText.getText().toString();
                String fullName = nameEditText.getText().toString();
                String cardNumber = cardNumberEditText.getText().toString();
                String cvv = cvvEditText.getText().toString();
                String month = monthSpinner.getSelectedItem().toString();
                String year = yearSpinner.getSelectedItem().toString();

                // בדיקה שכל השדות מלאים
                if (identityCard.isEmpty() || fullName.isEmpty() || cardNumber.isEmpty() || cvv.isEmpty() || month.isEmpty() || year.isEmpty())
                {
                    Toast.makeText(Payment.this, "אנא מלא את כל השדות", Toast.LENGTH_SHORT).show();
                    return;
                }

                // בדיקת תעודת זהות - חייבת להיות 9 ספרות
                if (identityCard.length() != 9) {
                    Toast.makeText(Payment.this, "תעודת זהות חייבת להכיל 9 ספרות בדיוק", Toast.LENGTH_SHORT).show();
                    return;
                }

                // בדיקת מספר כרטיס אשראי - לא פחות מ-16 ספרות
                if (cardNumber.length() < 16) {
                    Toast.makeText(Payment.this, "מספר כרטיס האשראי קצר מדי, נדרשות לפחות 16 ספרות", Toast.LENGTH_SHORT).show();
                    return;
                }

                // בדיקת מספר כרטיס אשראי - לא מעל מ-16 ספרות
                if (cardNumber.length() > 16) {
                    Toast.makeText(Payment.this, "מספר כרטיס האשראי גדול מדי, נדרשות מינימום 16 ספרות", Toast.LENGTH_SHORT).show();
                    return;
                }

                // בדיקת CVV - חייב להיות בדיוק 3 ספרות
                if (cvv.length() != 3) {
                    Toast.makeText(Payment.this, "קוד CVV חייב להכיל 3 ספרות בדיוק", Toast.LENGTH_SHORT).show();
                    return;
                }

                // אם כל הבדיקות עברו בהצלחה
                Toast.makeText(Payment.this, "התשלום בוצע בהצלחה!", Toast.LENGTH_LONG).show();
            }
        });

        bit.setOnClickListener(v -> openApp("il.co.isracard.bit"));
        payPal.setOnClickListener(v -> openApp("com.paypal.android.p2pmobile"));
        payBox.setOnClickListener(v -> openApp("com.cal.paybox"));
    }

    private void openApp(String packageName) {
        PackageManager packageManager = getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(packageName);
        if (intent != null)
        {
            startActivity(intent);
        }
        else
        {
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName));
            startActivity(webIntent);
        }
    }
}