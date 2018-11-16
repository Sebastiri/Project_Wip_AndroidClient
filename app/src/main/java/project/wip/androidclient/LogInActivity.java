package project.wip.androidclient;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Pair;
import android.view.inputmethod.InputMethodManager;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

public class LogInActivity extends Activity {

    EditText editTextBalanceID;
    String errorMessage;
    Account account = new Account();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE))
                .toggleSoftInput(InputMethodManager.SHOW_FORCED,
                        InputMethodManager.HIDE_IMPLICIT_ONLY);
        setContentView(R.layout.activity_log_in);

        editTextBalanceID = findViewById(R.id.editTextBalanceID);


        addListenerOnButton();
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
        }.execute("http://10.0.2.2:9998/rest/account/" + editTextBalanceID.getText()); // In Konstante speichern
    }

    ImageButton buttonSettings;
    Button buttonLogIn;
    TextView textViewIP;
    EditText editTextIP;

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
                //Intent switchIntent = new Intent(LogInActivity.this, MainActivity.class);
                //LogInActivity.this.startActivity(switchIntent);
                //get(v);
                Intent intent = new Intent(LogInActivity.this, TransactionActivity.class);
                //intent.putExtra(); hier sollten Objekte von Account übergeben werden
                startActivity(intent);
            }
        });
    }
}