package project.wip.androidclient;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private  RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<TransactionItem> transactionItems = new ArrayList<>();
        transactionItems.add(new TransactionItem("Hallo1","Hallo2"));
        transactionItems.add(new TransactionItem("Hallo3","Hallo4"));
        transactionItems.add(new TransactionItem("Hallo5","Hallo6"));
        transactionItems.add(new TransactionItem("Hallo7","Hallo8"));

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new TransactionAdapter(transactionItems);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
