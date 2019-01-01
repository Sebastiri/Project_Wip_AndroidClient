package project.wip.androidclient;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This class is necessary for the activity_transaction.xml. It builds the content and starts the
 * activity.
 * @author Sebastian Rieck
 */
public class TransactionActivity extends Activity {

    ServerConnection serverConnection = new ServerConnection();
    TextView textViewBalance;

    /**
     * Called when the activity is starting. The activity TransactionActivity gets initialised in this method.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut
     *                           down then this Bundle contains the data it most recently supplied in
     *                           onSaveInstanceState(Bundle). Note: Otherwise it is null.
     * @author Sebastian Rieck
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        textViewBalance = findViewById(R.id.textViewBalance);
        textViewBalance.setText(String.format("Kontostand: %s €",ServerConnection.balance));
        addListenerOnButton();
    }

    /**
     * The method creates a listener for the buttons. The class variables get initialized with the
     * related xml element.
     * @author Sebastian Rieck
     */
    public void addListenerOnButton(){

        EditText eTReceiver = findViewById(R.id.editTextReceiverNumber);
        EditText eTAmount = findViewById(R.id.editTextAmount);
        EditText eTReference = findViewById(R.id.editTextReference);
        Button buttonTransact = findViewById(R.id.buttonTransaction);

        buttonTransact.setOnClickListener(new View.OnClickListener() {
            /**
             * Defines, what happens when the buttonTransaction gets clicked. Calls the method
             * ServerConnection.postTransaction() with all information from the editTexts.
             * @param v the view of the activity
             * @author Sebastian Rieck
             */
            @Override
            public void onClick(View v) {
                try {
                    String accountNumber = ServerConnection.getCurrentAccount().getNumber();
                    serverConnection.postTransaction(TransactionActivity.this,accountNumber,
                            eTReceiver.getText().toString(),eTAmount.getText().toString().replace(",", "."),eTReference.getText().toString());
                    serverConnection.getAccount(accountNumber,TransactionActivity.this,ServerConnection.mIpAddress);
                } catch (Exception e){
                    Toast.makeText(TransactionActivity.this,"Server nicht verfügbar",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}