package project.wip.androidclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TransactionActivity extends Activity {

    ServerConnection serverConnection = new ServerConnection();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        addListenerOnButton();
    }

    public void addListenerOnButton(){

        Button buttonTransact = findViewById(R.id.buttonTransaction);

        buttonTransact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                serverConnection.postTransaction(v,TransactionActivity.this);
                Intent intentActivityBack = new Intent(TransactionActivity.this, MainActivity.class);
                startActivity(intentActivityBack);
            }
        });
    }
}
