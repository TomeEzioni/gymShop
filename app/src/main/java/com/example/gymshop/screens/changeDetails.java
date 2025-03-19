package com.example.gymshop.screens;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gymshop.R;
import com.example.gymshop.models.User;
import com.example.gymshop.utils.SharedPreferencesUtil;

public class changeDetails extends AppCompatActivity {

    private EditText etFirstName, etLastName, etPhone, etAddress, etEmail, etPassword;
    private Button btnSaveChanges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_details);

        //etFirstName = findViewById(R.id.etFirstName);
        //etLastName = findViewById(R.id.etLastName);
        //etPhone = findViewById(R.id.etPhone);
        //etAddress = findViewById(R.id.etAddress);
        //etEmail = findViewById(R.id.etEmail);
       // etPassword = findViewById(R.id.etPassword);
        btnSaveChanges = findViewById(R.id.btnChange);

        // טעינת נתוני המשתמש
        loadUserData();

        // שמירת הנתונים החדשים
        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserData();
            }
        });
    }

    private void loadUserData() {
        User user = SharedPreferencesUtil.getUser(this);
        if (user != null) {
            etFirstName.setText(user.getfName());
            etLastName.setText(user.getlName());
            etPhone.setText(user.getPhone());
            etAddress.setText(user.getAddress());
            etEmail.setText(user.getEmail());
            etPassword.setText(user.getPassword());
        }
    }

    private void saveUserData() {
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty() || address.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "יש למלא את כל השדות!", Toast.LENGTH_SHORT).show();
            return;
        }

        // עדכון הנתונים ושמירה
        User updatedUser = new User(
                SharedPreferencesUtil.getUser(this).getId(),
                firstName, lastName, phone, address, email, password,false
        );

        SharedPreferencesUtil.saveUser(this, updatedUser);
        Toast.makeText(this, "הפרטים נשמרו בהצלחה!", Toast.LENGTH_SHORT).show();
        finish(); // סוגר את המסך לאחר השמירה
    }
}