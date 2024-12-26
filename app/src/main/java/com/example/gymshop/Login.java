package com.example.gymshop;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRef;
    private FirebaseAuth mAuth;
    Button btnSignIn;
    EditText editTextEmail, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
    }

    private void initViews()
    {
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");

        editTextEmail = findViewById(R.id.etCemail);
        editTextPassword = findViewById(R.id.etCpassword);
        btnSignIn = findViewById(R.id.btConnect);

        btnSignIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                handleLogin();
            }
        });
    }

    private void handleLogin()
    {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        boolean isValid = true;

        if (email.isEmpty() || !email.contains("@"))
        {
            editTextEmail.setError("הכנס אימייל תקין (@)");
            isValid = false;
        }

        if (password.isEmpty() || password.length() < 6)
        {
            editTextPassword.setError("הכנס סיסמה תקינה (מעל 6 ספרות)");
            isValid = false;
        }

        if (!isValid) return;

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            Log.d("TAG", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            Intent go = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(go);

                            new AlertDialog.Builder(Login.this)
                                    .setTitle("התחברות בוצעה בהצלחה!")
                                    .setMessage("אימייל וסיסמה נקלטו בהצלחה.")
                                    .setPositiveButton("אישור", (dialog, which) -> dialog.dismiss())
                                    .show();
                        }
                        else
                        {
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
