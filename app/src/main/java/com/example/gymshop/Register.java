package com.example.gymshop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.appcompat.app.AlertDialog;

import com.example.gymshop.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.prefs.Preferences;


public class Register extends AppCompatActivity implements View.OnClickListener
{
    EditText etFirstName, etLastName, etPassword, etPhone, etAddress, etEmail;
    Button btnRegister;

    private FirebaseAuth mAuth;
    FirebaseDatabase database ;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.register);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (view, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        mAuth = FirebaseAuth.getInstance();
         database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");
    }

    private void initViews()
    {

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etPassword = findViewById(R.id.etPassword);
        etPhone = findViewById(R.id.etPhone);
        etAddress= findViewById(R.id.etAddress);
        etEmail = findViewById(R.id.etEmail);
        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
    }




    @Override
    public void onClick(View view)
    {
        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        String password = etPassword.getText().toString();
        String phone = etPhone.getText().toString();
        String address= etAddress.getText().toString();
        String email = etEmail.getText().toString();

        boolean isValid = true;

        if (firstName.isEmpty())
        {
            Log.w("onCreate:", firstName);
            etFirstName.setError("הכנס שם פרטי תקין");
            isValid = false;
        }

        if (lastName.isEmpty())
        {
            Log.w("onCreate: ", lastName);
            etLastName.setError("הכנס שם משפחה תקין");
            isValid = false;
        }

        if (phone.isEmpty())
        {
            Log.w("onCreate: ", phone);
            etPhone.setError("הכנס מספר טלפון תקין (05)");
            isValid = false;
        }

        if (address.isEmpty())
        {
            Log.w("onCreate: ", address);
            etAddress.setError("הכנס כתובת תקינה (רחוב, מספר בית, עיר)");
            isValid = false;
        }
        if (email.isEmpty())
        {
            Log.w("onCreate: ", email);
            etEmail.setError("הכנס אימייל תקין (@)");
            isValid = false;
        }

        if (password.isEmpty())
        {
            Log.w("onCreate: ", password);
            etPassword.setError("הכנס סיסמה תקינה (מעל 6 ספרות)");
            isValid = false;
        }

        if (isValid) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                Log.d("TAG", "createUserWithEmailAndPassword:success");

                                User newUser = new User(mAuth.getUid(), firstName, lastName, phone, address, email, password);

                                myRef.child(mAuth.getUid()).setValue(newUser);
                                new AlertDialog.Builder(Register.this)
                                        .setTitle("הרשמה הצליחה")
                                        .setMessage("ההרשמה בוצעה בהצלחה! כעת נעבור למסך ההתחברות.")
                                        .setPositiveButton("אישור", (dialog, which) ->
                                        {
                                            Intent intent = new Intent(Register.this, Login.class);
                                            startActivity(intent);
                                            finish();
                                        })
                                        .setCancelable(false)
                                        .show();
                            }
                            else
                            {
                                Log.w("TAG", "createUserWithEmailAndPassword:failure", task.getException());

                                new AlertDialog.Builder(Register.this)
                                        .setTitle("שגיאה בהרשמה")
                                        .setMessage("הרשמה נכשלה. בדוק את הפרטים ונסה שוב.")
                                        .setPositiveButton("אישור", (dialog, which) -> dialog.dismiss())
                                        .setCancelable(false)
                                        .show();
                            }
                        }
                    });
        }


    }
    public interface SharedPreferences
    {
        Object userName = ;
        String user = sharedPref.getString(userName, "");
        int score = sharedPref.getInt(score, 0);

        SharedPreferences sharedPref = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = sharedPref.edit();

        preferencesEditor.putString(getString(R.string.user_Key));
        preferencesEditor.putInt(getString(R.string.user_score), score);
        preferencesEditor.clear();
        preferencesEditor.apply();
        preferencesEditor.commit();
    }
}
