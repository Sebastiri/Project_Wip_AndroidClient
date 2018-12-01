package project.wip.androidclient;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import java.io.IOException;

public class LogInActivity extends Activity {

    EditText editTextAccountNumber;
    String errorMessage;
    Account account = new Account();
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
        //serverConnection.getAccount("1000",LogInActivity.this);
    }

    public void startIntentFromLogIn(){
        Intent intent = new Intent(LogInActivity.this, MainActivity.class);
        //intent.putExtra("accountNumber", editTextAccountNumber.getText().toString());
        startActivity(intent);
    }

    @SuppressLint("StaticFieldLeak")
    public void get(View view){
        new AsyncTask<String, Void, Pair<String, Integer>>() {
            @Override
            protected Pair<String, Integer> doInBackground(String... strings) {
                HttpClient client = new DefaultHttpClient();
                HttpGet get = new HttpGet(strings[0]);
                try {
                    HttpResponse response = client.execute(get);
                    if(response == null) return null;
                    int statusCode = response.getStatusLine().getStatusCode();
                    String json = null;
                    if(statusCode == HttpStatus.SC_OK){
                        json = EntityUtils.toString(response.getEntity(),"UTF-8");
                    } else {
                        errorMessage = EntityUtils.toString(response.getEntity());
                    }
                    return Pair.create(json, statusCode);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Pair<String, Integer> stringIntegerPair) {
                if(stringIntegerPair != null && stringIntegerPair.first != null){
                    String json = stringIntegerPair.first;
                    Gson gson = new GsonBuilder().create();
                    account = gson.fromJson(json, Account.class);
                    textViewIP.setText(account.getOwner());
                    System.out.println(account.getOwner());
                }
                else{
                    String msg = " (Fehler " + (stringIntegerPair != null ?
                            stringIntegerPair.second : "null") + ")";
                    Toast.makeText(LogInActivity.this, errorMessage + msg, Toast.LENGTH_SHORT).show();
                }
            }
        }.execute("http://10.0.2.2:9998/rest/account/" + editTextAccountNumber.getText()); // In Konstante speichern
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
                serverConnection.getAccount(editTextAccountNumber.getText().toString(),LogInActivity.this);
            }
        });
    }
}