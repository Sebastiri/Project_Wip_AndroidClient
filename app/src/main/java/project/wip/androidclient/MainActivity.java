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
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains all actions and algorithms used for the activity_main.xml.
 * There are two buttons used on this activity, so the activities of the clicks get described here.
 * There is also a RecyclerView that gets initialized here.
 * This is the main view for the user. It contains the transactions, balance, owner name and the
 * ability to start a transaction.
 * @author Sebastian Rieck
 */
public class MainActivity extends Activity {

    TextView textViewName;
    Account account;
    Context context;
    ServerConnection serverConnection = new ServerConnection();

    /**
     * Called when the activity is starting. The activity MainActivity gets initialised in this method.
     * The method ServerConnection.getAccount() gets called so the newest information gets pulled
     * from the server. The whole content of the activity gets set from here on.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut
     *                           down then this Bundle contains the data it most recently supplied in
     *                           onSaveInstanceState(Bundle). Note: Otherwise it is null.
     * @author Sebastian Rieck
     */
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

    /**
     * As this activity contains a lot of content that needs to be set by the server, all this
     * content gets loaded in a specific method called loadDynamicContent() which gets called in
     * the onCreate(). The result contains a headline, transactions and the balance.
     * @param account The object of type account that should be placed in the activity.
     * @author Sebastian Rieck
     */
    public void loadDynamicContent(Account account){

        // set headline
        textViewName.setText(String.format("Guten Tag, %s!", account.getOwner()));

        ArrayList<TransactionItem> transactionItems = new ArrayList<>();
        List<Transaction> transactions = account.getTransactions();

        // foreach loop to add transactions to RecyclerView and to calculate the balance
        BigDecimal balance = new BigDecimal("0.00");
        for (Transaction i:transactions) {

            // if-clause to differentiate between sender and receiver, then adds the transaction to
            // the RecyclerView
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

        // set balance
        ServerConnection.balance = balance.toString().replace(".", ",");
        TextView tvBalance = findViewById(R.id.textViewBalance);
        tvBalance.setText(String.format("%s €",ServerConnection.balance));

        // set transactions
        addRecyclerView(transactionItems);
    }

    /**
     * This method initializes the RecyclerView. This is a element comparable to a table.
     * @param transactionItems Array of transactions
     * @author Sebastian Rieck
     */
    public void addRecyclerView(ArrayList<TransactionItem> transactionItems){
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.Adapter adapter = new TransactionAdapter(transactionItems);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    /**
     * The method creates a listener for the buttons. The class variables get initialized with the
     * related xml element.
     * @author Sebastian Rieck
     */
    public void addListenerOnButton(){

        Button buttonTransactActivity = findViewById(R.id.buttonTransactionActivity);

        buttonTransactActivity.setOnClickListener(new View.OnClickListener() {
            /**
             * Starts the activity_transaction. The method creates an instance of View.OnClickListener and wires the listener to the
             * button using setOnClickListener(View.OnClickListener). As a result, the buttonTransactionActivity
             * is activated and can be used.
             * @param v the view of the activity
             * @author Sebastian Rieck
             */
            @Override
            public void onClick(View v) {
                Intent intentTransaction = new Intent(MainActivity.this, TransactionActivity.class);
                startActivity(intentTransaction);
            }
        });

        ImageButton buttonRefresh = findViewById(R.id.imageButtonRefresh);
        buttonRefresh.setOnClickListener(new View.OnClickListener(){
            /**
             * Refreshes the current activity. The method creates an instance of View.OnClickListener and wires the listener to the
             * button using setOnClickListener(View.OnClickListener). As a result, the imageButtonRefresh
             * is activated and can be used.
             * @param v the view of the activity
             * @author Sebastian Rieck
             */
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

        ImageButton buttonLogOut = findViewById(R.id.imageButtonLogOut);
        buttonLogOut.setOnClickListener(new View.OnClickListener(){
            /**
             * Returns to the activity_log_in. The method creates an instance of View.OnClickListener and wires the listener to the
             * button using setOnClickListener(View.OnClickListener). As a result, the imageButtonLogOut
             * is activated and can be used.
             * @param v the view of the activity
             * @author Sebastian Rieck
             */
            @Override
            public void onClick(View v) {
                Intent intentLogOut = new Intent(MainActivity.this,LogInActivity.class);
                startActivity(intentLogOut);
            }
        });
    }

    /**
     * The empty method overrides the normal funtion of the onBackPresses method. So nothing happens
     * when the back button gets pressed in the activity_main.
     * @author Sebastian Rieck
     */
    @Override
    public void onBackPressed() {}
}
