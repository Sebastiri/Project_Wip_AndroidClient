package project.wip.androidclient;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class LogInActivity extends Activity {

    EditText editTextAccountNumber;
    ImageButton buttonSettings;
    Button buttonLogIn;
    TextView textViewIP;
    EditText editTextIP;
    ServerConnection serverConnection = new ServerConnection();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE))
                .toggleSoftInput(InputMethodManager.SHOW_FORCED,
                        InputMethodManager.HIDE_IMPLICIT_ONLY);
        setContentView(R.layout.activity_log_in);

        editTextAccountNumber = findViewById(R.id.editTextNumber);

        addListenerOnButton();
    }

    public void addListenerOnButton(){

        buttonSettings = findViewById(R.id.imageButtonSettings);
        buttonLogIn = findViewById(R.id.buttonLogIn);
        textViewIP = findViewById(R.id.textViewIP);
        editTextIP = findViewById(R.id.editTextIP);

        buttonSettings.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editTextIP.getVisibility() == View.INVISIBLE){
                    editTextIP.setVisibility(View.VISIBLE);
                    textViewIP.setVisibility(View.VISIBLE);
                } else {
                    editTextIP.setVisibility(View.INVISIBLE);
                    textViewIP.setVisibility(View.INVISIBLE);
                }
            }
        });

        buttonLogIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextAccountNumber.getText().toString().length() == 0){
                    Toast.makeText(LogInActivity.this,"Bitte Kontonummer eingeben",Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        serverConnection.getAccount(editTextAccountNumber.getText().toString(),
                                LogInActivity.this,editTextIP.getText().toString());
                    } catch (Exception e){
                        Toast.makeText(LogInActivity.this,"Server nicht verfügbar",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}