package com.example.gymshop.screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gymshop.R;
import com.example.gymshop.models.User;
import com.example.gymshop.services.AuthenticationService;
import com.example.gymshop.services.DatabaseService;
import com.example.gymshop.utils.SharedPreferencesUtil;


public class Register extends AppCompatActivity implements View.OnClickListener
{
    EditText etFirstName, etLastName, etPassword, etPhone, etAddress, etEmail;
    Button btnRegister;



    private static final String TAG = "RegisterActivity";


    private AuthenticationService authenticationService;
    private DatabaseService databaseService;

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

        /// get the instance of the authentication service
        authenticationService = AuthenticationService.getInstance();
        /// get the instance of the database service
        databaseService = DatabaseService.getInstance();

        initViews();

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
    public void onClick(View view) {
        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        String password = etPassword.getText().toString();
        String phone = etPhone.getText().toString();
        String address = etAddress.getText().toString();
        String email = etEmail.getText().toString();

        boolean isValid = true;

        if (firstName.isEmpty()) {
            Log.w("onCreate:", firstName);
            etFirstName.setError("הכנס שם פרטי תקין");
            isValid = false;
        }

        if (lastName.isEmpty()) {
            Log.w("onCreate: ", lastName);
            etLastName.setError("הכנס שם משפחה תקין");
            isValid = false;
        }

        if (phone.isEmpty()) {
            Log.w("onCreate: ", phone);
            etPhone.setError("הכנס מספר טלפון תקין (05)");
            isValid = false;
        }

        if (address.isEmpty()) {
            Log.w("onCreate: ", address);
            etAddress.setError("הכנס כתובת תקינה (רחוב, מספר בית, עיר)");
            isValid = false;
        }
        if (email.isEmpty()) {
            Log.w("onCreate: ", email);
            etEmail.setError("הכנס אימייל תקין (@)");
            isValid = false;
        }

        if (password.isEmpty()) {
            Log.w("onCreate: ", password);
            etPassword.setError("הכנס סיסמה תקינה (מעל 6 ספרות)");
            isValid = false;
        }

        if (isValid) {
            /// Register the user

            registerUser( firstName, lastName, phone,  email,  password,  address);
        }
    }



            private void registerUser(String fname, String lname, String phone, String email, String password, String address) {
                Log.d(TAG, "registerUser: Registering user...");

                /// call the sign up method of the authentication service
                authenticationService.signUp(email, password, new AuthenticationService.AuthCallback<String>() {

                    @Override
                    public void onCompleted(String uid) {
                        Log.d(TAG, "onCompleted: User registered successfully");
                        /// create a new user object
                        User user = new User();
                        user.setId(uid);
                        user.setEmail(email);
                        user.setPassword(password);
                        user.setfName(fname);
                        user.setlName(lname);
                        user.setPhone(phone);
                        user.setAddress(address);
                        user.setisAdmin(true);

                        /// call the createNewUser method of the database service
                        databaseService.createNewUser(user, new DatabaseService.DatabaseCallback<Void>() {

                            @Override
                            public void onCompleted(Void object) {
                                Log.d(TAG, "onCompleted: User registered successfully");
                                /// save the user to shared preferences
                                SharedPreferencesUtil.saveUser(Register.this, user);
                                Log.d(TAG, "onCompleted: Redirecting to MainActivity");
                                /// Redirect to MainActivity and clear back stack to prevent user from going back to register screen
                                Intent mainIntent = new Intent(Register.this, MainActivity.class);
                                /// clear the back stack (clear history) and start the MainActivity
                                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(mainIntent);
                            }

                            @Override
                            public void onFailed(Exception e) {
                                Log.e(TAG, "onFailed: Failed to register user", e);
                                /// show error message to user
                                Toast.makeText(Register.this, "Failed to register user", Toast.LENGTH_SHORT).show();
                                /// sign out the user if failed to register
                                /// this is to prevent the user from being logged in again
                                authenticationService.signOut();
                            }
                        });
                    }

                    @Override
                    public void onFailed(Exception e) {
                        Log.e(TAG, "onFailed: Failed to register user", e);
                        /// show error message to user
                        Toast.makeText(Register.this, "Failed to register user", Toast.LENGTH_SHORT).show();
                    }
                });




            }
    }

