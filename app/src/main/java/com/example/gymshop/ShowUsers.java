package com.example.gymshop;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gymshop.models.User;
import com.example.gymshop.services.AuthenticationService;
import com.example.gymshop.services.DatabaseService;

import java.util.ArrayList;

public class ShowUsers extends AppCompatActivity {

    DatabaseService databaseService;
    private AuthenticationService authenticationService;
    private String uid;

    User user;

    ListView lvMembers;
    ArrayList<User> users=new ArrayList<>();
    /// UserNamAdapter<User> adapter;

    ArrayList<User> usersSelected=new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_users);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        databaseService = DatabaseService.getInstance();
        authenticationService = AuthenticationService.getInstance();
        uid=authenticationService.getCurrentUserId();





    }


//    users=new ArrayList<>();
//
//    adapter = new UserNamAdapter<>(AddNewEvent.this, 0,0,users);
//        lvMembers.setAdapter(adapter);
//        lvMembers.setOnItemClickListener(this);

//      databaseService.getUsers(new DatabaseService.DatabaseCallback<List<User>>() {
//        @Override
//        public void onCompleted(List<User> object) {
//            Log.d("TAG", "onCompleted: " + object);
//            users.clear();
//            users.addAll(object);
    /// notify the adapter that the data has changed
    /// this specifies that the data has changed
    /// and the adapter should update the view
    /// @see FoodSpinnerAdapter#notifyDataSetChanged()
    // foodSpinnerAdapter.notifyDataSetChanged();

//            adapter.notifyDataSetChanged();
//
//        }
//
//
//
//
//        @Override
//        public void onFailed(Exception e) {
//            Log.e("TAG", "onFailed: ", e);
//        }
//    });
//
//        databaseService.getUser(uid, new DatabaseService.DatabaseCallback<User>() {
//        @Override
//        public void onCompleted(User u) {
//            user = u;
//
//        }
//
//        @Override
//        public void onFailed(Exception e) {
//
//        }
//    });

}