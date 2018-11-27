package project.wip.androidclient;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TransactionActivity extends Activity {

    //Intent intentActivityBack = new Intent(TransactionActivity.this, MainActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        post(findViewById(android.R.id.content));

        addListenerOnButton();
    }

    public void addListenerOnButton(){

        Button buttonTransact = findViewById(R.id.buttonTransaction);

        buttonTransact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentActivityBack = new Intent(TransactionActivity.this, MainActivity.class);
                //Intent switchIntent = new Intent(LogInActivity.this, MainActivity.class);
                //LogInActivity.this.startActivity(switchIntent);
                //get(v);
                //intent.putExtra(); hier sollten Objekte von Account übergeben werden
                startActivity(intentActivityBack);
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    public void post(View view){
        final String value; // = editInput.getText().toString();

        AsyncTask<String, Void, HttpResponse> info = new AsyncTask<String, Void, HttpResponse>() {
            @Override
            protected HttpResponse doInBackground(String... strings) {
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(strings[0]);
                List<NameValuePair> parameterList = new ArrayList<>();
                parameterList.add(new BasicNameValuePair("senderNumber", "1000"));
                parameterList.add(new BasicNameValuePair("receiverNumber", "1001"));
                parameterList.add(new BasicNameValuePair("amount", "1.00"));
                parameterList.add(new BasicNameValuePair("reference", "Bitteschön!"));
                try {

                    UrlEncodedFormEntity form = new UrlEncodedFormEntity(parameterList,"UTF-8");

                    post.setEntity(form);
                    return client.execute(post);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(HttpResponse httpResponse) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                String entityMsg = null;
                if(statusCode != HttpStatus.SC_NO_CONTENT){
                    try {
                        entityMsg = EntityUtils.toString(httpResponse.getEntity(),"UTF-8");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // Bedingung ? true : false
                    String errorMsg = " (Fehler " + httpResponse.getStatusLine().getStatusCode() + ")";
                    Toast.makeText(TransactionActivity.this, entityMsg + errorMsg, Toast.LENGTH_SHORT).show();
                }


                /*String msg = " (Fehler " + (stringIntegerPair != null ?
                        stringIntegerPair.second : "null") + ")";
                Toast.makeText(LogInActivity.this, errorMessage + msg, Toast.LENGTH_SHORT).show();*/
            }
        }.execute("http://10.0.2.2:9998/rest/transaction");// In Konstante speichern
    }
}
