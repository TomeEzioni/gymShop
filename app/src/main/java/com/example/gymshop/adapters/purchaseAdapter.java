package com.example.gymshop.adapters;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymshop.R;
import com.example.gymshop.models.Order;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class purchaseAdapter extends AppCompatActivity {

    private RecyclerView purchasesRecyclerView;
    private purchaseAdapter purchaseAdapter;
    private List<Order> purchasesList;
    private FloatingActionButton addPurchaseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchases);

        // אתחול הרשימה
        purchasesList = new ArrayList<>();

        // הוספת נתוני דוגמה
        loadSampleData();

        // אתחול RecyclerView
        purchasesRecyclerView = findViewById(R.id.purchases_recycler_view);
        purchasesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // אתחול Adapter
        //purchaseAdapter = new purchaseAdapter(purchasesList, this);
        //purchasesRecyclerView.setAdapter(purchaseAdapter);

        // הגדרת כפתור הוספת רכישה חדשה
        addPurchaseButton = findViewById(R.id.add_purchase_button);
        addPurchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // כאן יש להפעיל את המסך להוספת רכישה חדשה
                Toast.makeText(purchaseAdapter.this, "הוספת רכישה חדשה", Toast.LENGTH_SHORT).show();
                // לדוגמה: Intent intent = new Intent(PurchasesActivity.this, AddPurchaseActivity.class);
                // startActivity(intent);
            }
        });
    }

    /**
     * טעינת נתוני דוגמה לרשימה
     */
    private void loadSampleData() {
        // יצירת תאריכים לדוגמה
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date yesterday = calendar.getTime();

        calendar.add(Calendar.DAY_OF_MONTH, -2);
        Date twoDaysAgo = calendar.getTime();

        calendar.add(Calendar.DAY_OF_MONTH, -4);
        Date lastWeek = calendar.getTime();

        // הוספת הזמנות לדוגמה
        //purchasesList.add(new purchase("123456", today, 450.90, 3, purchaseStatus.PENDING));
        //purchasesList.add(new Purchase("123455", yesterday, 1250.00, 7, PurchaseStatus.SHIPPED));
       // purchasesList.add(new Purchase("123454", twoDaysAgo, 120.50, 1, PurchaseStatus.DELIVERED));
        //purchasesList.add(new Purchase("123453", lastWeek, 789.99, 4, PurchaseStatus.CANCELLED));
        //purchasesList.add(new Purchase("123452", lastWeek, 349.90, 2, PurchaseStatus.DELIVERED));
    }

    //@Override
    //public void onPurchaseClick(purchase purchase) {
        // טיפול בלחיצה על פריט ברשימה
        //Toast.makeText(this, "לחצת על הזמנה מספר " + purchase.getId(), Toast.LENGTH_SHORT).show();

        // לדוגמה: פתיחת מסך פרטי הזמנה
        // Intent intent = new Intent(this, PurchaseDetailsActivity.class);
        // intent.putExtra("PURCHASE_ID", purchase.getId());
        // startActivity(intent);
   // }

    /**
     * רענון נתוני הרשימה
     */
    private void refreshPurchasesList() {
        // כאן יש לטעון נתונים מהשרת או ממסד הנתונים
        // לדוגמה:
        // purchasesList = purchaseRepository.getAllPurchases();
       //purchaseAdapter.updatePurchases(purchasesList);
    }
}
