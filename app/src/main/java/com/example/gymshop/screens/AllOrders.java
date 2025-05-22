package com.example.gymshop.screens;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.gymshop.R;
import com.example.gymshop.adapters.OrderAdapter;
import com.example.gymshop.models.Order;
import com.example.gymshop.services.DatabaseService;

import java.util.ArrayList;
import java.util.List;

public class AllOrders extends AppCompatActivity {

    RecyclerView rcAllordera;
    ArrayList<Order>orders=new ArrayList<>();
    OrderAdapter orderAdapter;

    DatabaseService databaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_all_orders);

        databaseService=DatabaseService.getInstance();
        rcAllordera=findViewById(R.id.rcAllorders);

        rcAllordera.setLayoutManager(new LinearLayoutManager(this));

        rcAllordera.setFadingEdgeLength(50);

        orderAdapter=new OrderAdapter(AllOrders.this,orders);
        rcAllordera.setAdapter(orderAdapter);
        databaseService.getAllOrders(new DatabaseService.DatabaseCallback<List<Order>>() {
            @Override
            public void onCompleted(List<Order> object) {
                orders.clear();
                orders.addAll(object);
                orderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(Exception e) {

            }
        });
    }
}