package com.example.gymshop.utils;

import static android.content.Context.MODE_PRIVATE;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.gymshop.models.User;
import com.example.gymshop.screens.Admin_Page;
import com.example.gymshop.screens.userHomeActivity;


/// Utility class for shared preferences operations
/// Contains methods for saving and retrieving data from shared preferences
/// Also contains methods for clearing and removing data from shared preferences
/// @see SharedPreferences
public class SharedPreferencesUtil {

    /// The name of the shared preferences file
    /// @see Context#getSharedPreferences(String, int)
    private static final String PREF_NAME = "com.example.testapp.PREFERENCE_FILE_KEY";

    /// Save a string to shared preferences
    /// @param context The context to use
    /// @param key The key to save the string with
    /// @param value The string to save
    /// @see SharedPreferences.Editor#putString(String, String)
    private static void saveString(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /// Get a string from shared preferences
    /// @param context The context to use
    /// @param key The key to get the string with
    /// @param defaultValue The default value to return if the key is not found
    /// @return The string value stored in shared preferences
    /// @see SharedPreferences#getString(String, String)
    private static String getString(Context context, String key, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        return sharedPreferences.getString(key, defaultValue);
    }

    /// Save an integer to shared preferences
    /// @param context The context to use
    /// @param key The key to save the integer with
    /// @param value The integer to save
    /// @see SharedPreferences.Editor#putInt(String, int)
    private static void saveInt(Context context, String key, int value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /// Get an integer from shared preferences
    /// @param context The context to use
    /// @param key The key to get the integer with
    /// @param defaultValue The default value to return if the key is not found
    /// @return The integer value stored in shared preferences
    /// @see SharedPreferences#getInt(String, int)
    private static int getInt(Context context, String key, int defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        return sharedPreferences.getInt(key, defaultValue);
    }

    // Add more methods for other data types as needed

    /// Clear all data from shared preferences
    /// @param context The context to use
    /// @see SharedPreferences.Editor#clear()
    public static void clear(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    /// Remove a specific key from shared preferences
    /// @param context The context to use
    /// @param key The key to remove
    /// @see SharedPreferences.Editor#remove(String)
    private static void remove(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }

    /// Check if a key exists in shared preferences
    /// @param context The context to use
    /// @param key The key to check
    /// @return true if the key exists, false otherwise
    /// @see SharedPreferences#contains(String)
    private static boolean contains(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        return sharedPreferences.contains(key);
    }

    // Add more utility methods as needed

    /// Save a user object to shared preferences
    /// @param context The context to use
    /// @param user The user object to save
    /// @see User
    public static void saveUser(Context context, User user) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("uid", user.getId());
        editor.putString("email", user.getEmail());
        editor.putString("password", user.getPassword());
        editor.putString("fName", user.getfName());
        editor.putString("lName", user.getlName());
        editor.putString("phone", user.getPhone());
        editor.apply();
    }

    /// Get the user object from shared preferences
    /// @param context The context to use
    /// @return The user object stored in shared preferences
    /// @see User
    /// @see #isUserLoggedIn(Context)
    public static User getUser(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        // check if user is logged in
        if (!isUserLoggedIn(context)) {
            return null;
        }
        String uid = sharedPreferences.getString("uid", "");
        String email = sharedPreferences.getString("email", "");
        String password = sharedPreferences.getString("password", "");
        String fName = sharedPreferences.getString("fName", "");
        String lName = sharedPreferences.getString("lName", "");
        String phone = sharedPreferences.getString("phone", "");
        String address = sharedPreferences.getString("address", "");
        return new User(uid, fName, lName, phone,address,email, password);
    }

    /// Sign out the user by removing user data from shared preferences
    /// @param context The context to use
    public static void signOutUser(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("uid");
        editor.remove("email");
        editor.remove("password");
        editor.remove("fName");
        editor.remove("lName");
        editor.remove("phone");

        editor.apply();
    }

    /// Check if a user is logged in by checking if the user id is present in shared preferences
    /// @param context The context to use
    /// @return true if the user is logged in, false otherwise
    /// @see #contains(Context, String)
    public static boolean isUserLoggedIn(Context context) {
        return contains(context, "uid");
    }


    public static void saveUserRole(Context context, String role) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("role", role);
        editor.apply();
    }

    // פונקציה לבדיקה האם המשתמש הוא מנהל
    public static boolean isAdmin(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String role = sharedPreferences.getString("role", "user"); // ברירת מחדל: משתמש רגיל
        return "admin".equals(role);
    }

    // פונקציה לנווט את המשתמש לעמוד המתאים לפי התפקיד שלו
    public static void redirectUser(Context context) {
        Intent intent;
        if (isAdmin(context)) {
            intent = new Intent(context, Admin_Page.class);
        } else {
            intent = new Intent(context, userHomeActivity.class);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
