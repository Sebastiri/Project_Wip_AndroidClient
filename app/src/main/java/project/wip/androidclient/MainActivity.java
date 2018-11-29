package project.wip.androidclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<TransactionItem> transactionItems = new ArrayList<>();
        transactionItems.add(new TransactionItem("10","Nov","Alina Liedtke","Zum Geburtstag <3","300,00 €"));
        transactionItems.add(new TransactionItem("07","Okt","Bertelsmann","Gehalt","7000,00 €"));
        transactionItems.add(new TransactionItem("06","Okt","Nadin Janßen","Für die Schuhe","-35,99 €"));
        transactionItems.add(new TransactionItem("22","Aug","Sebastian Rieck","einfach so","1003,99 €"));
        transactionItems.add(new TransactionItem("20","Aug","Hubertus Heil","Für die gute Lobbyarbeit","179823429,00 €"));
        transactionItems.add(new TransactionItem("20","Aug","Bank","Finanzierungskredit","10000,00 €"));
        transactionItems.add(new TransactionItem("19","Aug","Mama Weber","Frohe Weihnacht","20,57 €"));
        transactionItems.add(new TransactionItem("01","Aug","Oma Weber","Zum essen","309,48 €"));
        transactionItems.add(new TransactionItem("01","Aug","Sebastian Scholz","Kauf eures Banking-Systems und noch viel mehr und so tralalala","500,00 €"));
        transactionItems.add(new TransactionItem("30","Jul","Alina Liedtke","Zum Geburtstag <3","300,01 €"));
        transactionItems.add(new TransactionItem("28","Jul","Bank","STARTGUTHABEN","10000,00 €"));

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.Adapter adapter = new TransactionAdapter(transactionItems);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        // Set focus to the textview

        addListenerOnButton();
    }

    public void addListenerOnButton(){

        Button buttonTransactActivity = findViewById(R.id.buttonTransactionActivity);

        buttonTransactActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTransaction = new Intent(MainActivity.this, TransactionActivity.class);
                //Intent switchIntent = new Intent(LogInActivity.this, MainActivity.class);
                //LogInActivity.this.startActivity(switchIntent);
                //get(v);
                //intent.putExtra(); hier sollten Objekte von Account übergeben werden
                startActivity(intentTransaction);
            }
        });
    }

    @Override
    public void onBackPressed() {}
}
