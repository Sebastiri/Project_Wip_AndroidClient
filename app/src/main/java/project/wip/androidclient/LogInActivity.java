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

/**
 * This class contains all actions and algorithms used for the activity_log_in.xml.
 * There are two buttons used on this activity, so the activities of the clicks get described here.
 * The user should insert the IP of his server and log in with an account in this activity.
 * @author Sebastian Rieck
 */
public class LogInActivity extends Activity {

    EditText editTextAccountNumber;
    ImageButton buttonSettings;
    Button buttonLogIn;
    TextView textViewIP;
    EditText editTextIP;
    ServerConnection serverConnection = new ServerConnection();

    /**
     * Called when the activity is starting. The activity log_in_activity gets initialised in this
     * method.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut
     *                           down then this Bundle contains the data it most recently supplied
     *                           in onSaveInstanceState(Bundle). Note: Otherwise it is null.
     * @author Sebastian Rieck
     */
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

    /**
     * The method creates a listener for the buttons. The class variables get initialized with the
     * related xml element.
     * @author Sebastian Rieck
     */
    public void addListenerOnButton(){

        buttonSettings = findViewById(R.id.imageButtonSettings);
        buttonLogIn = findViewById(R.id.buttonLogIn);
        textViewIP = findViewById(R.id.textViewIP);
        editTextIP = findViewById(R.id.editTextIP);

        buttonSettings.setOnClickListener(new OnClickListener() {

            /**
             * The method creates an instance of View.OnClickListener and wires the listener to the
             * button using setOnClickListener(View.OnClickListener). As a result, the
             * buttonSettings is activated and can be used. What happens, is that the method checks
             * whether the editTextIP is visible or not. If not, the editText becomes visible so the
             * user can set the IP.
             * @param v The user interface component
             * @author Sebastian Rieck
             */
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

            /**
             * The method creates an instance of View.OnClickListener and wires the listener to the
             * button using setOnClickListener(View.OnClickListener). As a result, the buttonLogIn
             * is activated and can be used. What happens, is that the editTextAccountNumber gets
             * checked, whether it contains something. If this is the case, the method
             * serverConnection.getAccount() is called, which gets an account from the server and
             * switches the activity to activity_main.
             * @param v The user interface component
             * @author Sebastian Rieck
             */
            @Override
            public void onClick(View v) {
                if(editTextAccountNumber.getText().toString().length() == 0){
                    Toast.makeText(LogInActivity.this,"Bitte Kontonummer eingeben",
                            Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        serverConnection.getAccount(editTextAccountNumber.getText().toString(),
                                LogInActivity.this,editTextIP.getText().toString());
                    } catch (Exception e){
                        Toast.makeText(LogInActivity.this,"Server nicht verfügbar",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}