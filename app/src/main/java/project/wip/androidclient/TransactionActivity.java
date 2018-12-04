package project.wip.androidclient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TransactionActivity extends Activity {

    ServerConnection serverConnection = new ServerConnection();
    TextView textViewBalance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        textViewBalance = findViewById(R.id.textViewBalance);
        textViewBalance.setText(String.format("Kontostand: %s €",ServerConnection.balance));
        addListenerOnButton();
    }

    public void addListenerOnButton(){

        EditText eTReceiver = findViewById(R.id.editTextReceiverNumber);
        EditText eTAmount = findViewById(R.id.editTextAmount);
        EditText eTReference = findViewById(R.id.editTextReference);
        Button buttonTransact = findViewById(R.id.buttonTransaction);

        buttonTransact.setOnClickListener(new View.OnClickListener() {
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