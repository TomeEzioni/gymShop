package com.example.gymshop.screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gymshop.R;
import com.example.gymshop.models.User;
import com.example.gymshop.services.AuthenticationService;
import com.example.gymshop.services.DatabaseService;
import com.example.gymshop.utils.SharedPreferencesUtil;
import com.example.gymshop.utils.Validator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/// Activity for logging in the user
/// This activity is used to log in the user
/// It contains fields for the user to enter their email and password
/// It also contains a button to log in the user
/// When the user is logged in, they are redirected to the main activity
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";

    private EditText etEmail, etPassword;
    private Button btnLogin;

    private AuthenticationService authenticationService;
    private DatabaseService databaseService;
    private User user=null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        /// set the layout for the activity
         setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /// get the instance of the authentication service
        authenticationService = AuthenticationService.getInstance();
        /// get the instance of the database service
        databaseService = DatabaseService.getInstance();

        /// get the views
        etEmail = findViewById(R.id.etCemail);
        etPassword = findViewById(R.id.etCpassword);
        btnLogin = findViewById(R.id.btConnect);

        /// set the click listener
        btnLogin.setOnClickListener(this);

        user=SharedPreferencesUtil.getUser(LoginActivity.this);
        if(user!=null){

            etEmail.setText(user.getEmail());
            etPassword.setText(user.getPassword());
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnLogin.getId()) {
            Log.d(TAG, "onClick: Login button clicked");

            /// get the email and password entered by the user
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            /// log the email and password
            Log.d(TAG, "onClick: Email: " + email);
            Log.d(TAG, "onClick: Password: " + password);

            Log.d(TAG, "onClick: Validating input...");
            /// Validate input
            if (!checkInput(email, password)) {
                /// stop if input is invalid
                return;
            }

            Log.d(TAG, "onClick: Logging in user...");

            /// Login user
            loginUser(email, password);
        }
    }

    /// Method to check if the input is valid
    /// It checks if the email and password are valid
    /// @see Validator#isEmailValid(String)
    /// @see Validator#isPasswordValid(String)
    private boolean checkInput(String email, String password) {
        if (!Validator.isEmailValid(email)) {
            Log.e(TAG, "checkInput: Invalid email address");
            /// show error message to user
            etEmail.setError("Invalid email address");
            /// set focus to email field
            etEmail.requestFocus();
            return false;
        }

        if (!Validator.isPasswordValid(password)) {
            Log.e(TAG, "checkInput: Password must be at least 6 characters long");
            /// show error message to user
            etPassword.setError("Password must be at least 6 characters long");
            /// set focus to password field
            etPassword.requestFocus();
            return false;
        }

        return true;
    }

    private void loginUser(String email, String password) {
        authenticationService.signIn(email, password, new AuthenticationService.AuthCallback<String>() {
            /// Callback method called when the operation is completed
            /// @param uid the user ID of the user that is logged in
            @Override
            public void onCompleted(String uid)
            {
                Log.d(TAG, "onCompleted: User logged in successfully");
                /// get the user data from the database


                    Log.d("TAG", "signInWithEmail:success");




//                new AlertDialog.Builder(LoginActivity.this)
//                        .setTitle("התחברות בוצעה בהצלחה!")
//                        .setMessage("אימייל וסיסמה נקלטו בהצלחה.")
//                        .setPositiveButton("אישור", (dialog, which) -> dialog.dismiss())
//                        .show();




                databaseService.getUser(uid, new DatabaseService.DatabaseCallback<User>() {
                    @Override
                    public void onCompleted(User u) {
                        user = u;
                        Log.d(TAG, "onCompleted: User data retrieved successfully    "+ user.toString());
                        /// save the user data to shared preferences
                        SharedPreferencesUtil.saveUser(LoginActivity.this, user);

                        String userId = user.getId();

                        if(user.getIsAdmin().equals("true")){

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user != null)
                            {
                                userId = user.getUid();
                                Intent intent = new Intent(LoginActivity.this, Admin_Page.class);
                                intent.putExtra("USER_ID", userId);
                                startActivity(intent);
                                finish();
                            }
                        }
                       else {

                            Intent mainIntent = new Intent(LoginActivity.this, userHomeActivity.class);
                            mainIntent.putExtra("USER_ID", userId);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(mainIntent);

                        }

                    }

                    @Override
                    public void onFailed(Exception e) {
                        Log.e(TAG, "onFailed: Failed to retrieve user data", e);
                        /// Show error message to user
                        etPassword.setError("Invalid email or password");
                        etPassword.requestFocus();
                        /// Sign out the user if failed to retrieve user data
                        /// This is to prevent the user from being logged in again
                        authenticationService.signOut();

                    }
                });


            }



            @Override
            public void onFailed(Exception e) {
                Log.e(TAG, "onFailed: Failed to log in user", e);
                /// Show error message to user
                etPassword.setError("Invalid email or password");
                etPassword.requestFocus();

            }
        });
    }

    public void goToRegister(View view) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
}