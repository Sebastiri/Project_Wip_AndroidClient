package project.wip.androidclient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    TextView textViewName;
    Account account;
    Context context;
    ServerConnection serverConnection = new ServerConnection();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewName = findViewById(R.id.textViewName);
        context = MainActivity.this;

        account = ServerConnection.getCurrentAccount();
        loadDynamicContent(account);

        addListenerOnButton();
    }

    public void loadDynamicContent(Account account){

        //Setze Überschrift
        textViewName.setText(String.format("Guten Tag, %s!", account.getOwner()));

        ArrayList<TransactionItem> transactionItems = new ArrayList<>();
        List<Transaction> transactions = account.getTransactions();

        //foreach Schleife um Transaktionen in RecyclerViews hinzuzufügen und den Kontostand zu berechnen
        BigDecimal balance = new BigDecimal("0.00");
        for (Transaction i:transactions) {

            //if-Abfrage zum Unterscheiden ob User der Empfänger oder der Sender der Transaktion ist
            if(account.getNumber().equals(i.getReceiver().getNumber())){
                transactionItems.add(new TransactionItem(i.getDayFromDate(),i.getMonthFromDate(),i.getSender().getOwner(),
                        i.getReference(),String.format("%s €",i.getAmount().toString().replace(".",","))));
                balance = balance.add(i.getAmount());
            } else {
                transactionItems.add(new TransactionItem(i.getDayFromDate(),i.getMonthFromDate(),i.getReceiver().getOwner(),
                        i.getReference(),String.format("-%s €",i.getAmount().toString().replace(".",","))));
                balance = balance.subtract(i.getAmount());
            }
        }

        //Setze Kontostand
        String balanceResult = balance.toString().replace(".", ",");
        TextView tvBalance = findViewById(R.id.textViewBalance);
        tvBalance.setText(String.format("%s €",balanceResult));

        //Setze Transaktionen
        addRecyclerView(transactionItems);
    }

    public void addRecyclerView(ArrayList<TransactionItem> transactionItems){
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.Adapter adapter = new TransactionAdapter(transactionItems);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void addListenerOnButton(){

        Button buttonTransactActivity = findViewById(R.id.buttonTransactionActivity);

        buttonTransactActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTransaction = new Intent(MainActivity.this, TransactionActivity.class);
                startActivity(intentTransaction);
            }
        });

        ImageButton buttonRefresh = findViewById(R.id.imageButtonRefresh);
        buttonRefresh.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    Toast.makeText(MainActivity.this,"Account wird geladen",Toast.LENGTH_SHORT).show();
                    serverConnection.getAccount(ServerConnection.getCurrentAccount().getNumber(),MainActivity.this,ServerConnection.mIpAddress);
                } catch (Exception e){
                    Toast.makeText(MainActivity.this,"Server nicht verfügbar",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {}
}
