package com.example.gymshop.screens;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gymshop.R;
import com.example.gymshop.models.User;
import com.example.gymshop.services.DatabaseService;
import com.example.gymshop.utils.SharedPreferencesUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class changeDetails extends AppCompatActivity {

     EditText etFirstName, etLastName, etPhone, etAddress, etEmail, etPassword;
     Button btnSaveChanges;
     DatabaseService databaseService;
     String userId, isAdmin;
     FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_details);

        etFirstName = findViewById(R.id.etFirstNameUpdate);
        etLastName = findViewById(R.id.etLastNameUpdate);
        etPhone = findViewById(R.id.etPhoneUpdate);
        etAddress = findViewById(R.id.etAddressUpdate);
        etEmail = findViewById(R.id.etEmailUpdate);
        etPassword = findViewById(R.id.etPasswordUpdate);
        btnSaveChanges = findViewById(R.id.btnChange);

        userId = getIntent().getStringExtra("USER_ID");
        if (userId == null || userId.isEmpty()) {
            Toast.makeText(this, "שגיאה: לא נמצא מזהה משתמש", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("Users").child(userId);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    User user = dataSnapshot.getValue(User.class);
                    if(user != null){
                        etFirstName.setText(user.getfName());
                        etLastName.setText(user.getlName());
                        etPhone.setText(user.getPhone());
                        etAddress.setText(user.getAddress());
                        etEmail.setText(user.getEmail());
                        etPassword.setText(user.getPassword());
                        isAdmin = user.getIsAdmin();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(changeDetails.this, "שגיאה בקריאת הנתונים", Toast.LENGTH_SHORT).show();
            }
        });

        // שמירת הנתונים החדשים
        btnSaveChanges.setOnClickListener(v -> updateUserDetails ());
    }

    private void updateUserDetails()
    {
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(address) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"אנא מלא את כל השדות", Toast.LENGTH_SHORT).show();
            return;
        }

        User updatedUser = new User(userId, firstName, lastName, phone, address, email, password, isAdmin);

        DatabaseReference userRef = database.getReference("Users").child(userId);
        userRef.setValue(updatedUser).addOnCompleteListener(task ->
        {
            if (task.isSuccessful()){
                Toast.makeText(changeDetails.this, "פרטי המשתמש עודכנו בהצלחה", Toast.LENGTH_SHORT).show();
                finish();
            }
            else
            {
                Toast.makeText(changeDetails.this,"שגיאה בעדכון הנתונים",Toast.LENGTH_SHORT).show();
            }
        });
    }
}