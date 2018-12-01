package project.wip.androidclient;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServerConnection {

    private String errorMessage;
    private static Account account;

    @SuppressLint("StaticFieldLeak")
    public void getAccount(String accountNumber, final Context context){

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
                    System.out.print("Hallo I Bims und ich funze");

                    Intent intent = new Intent(context, MainActivity.class);
                    //intent.putExtra("accountNumber", editTextAccountNumber.getText().toString());
                    context.startActivity(intent);
                }
                else{
                    String msg = " (Fehler " + (stringIntegerPair != null ?
                            stringIntegerPair.second : "null") + ")";
                    Toast.makeText(context, errorMessage + msg, Toast.LENGTH_SHORT).show();
                }
            }

        }.execute("http://10.0.2.2:9998/rest/account/" + accountNumber); // In Konstante speichern
    }

    public static Account getCurrentAccount(){
        return account;
    }

    @SuppressLint("StaticFieldLeak")
    public void postTransaction(View view, final Context context){
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
                parameterList.add(new BasicNameValuePair("reference", "Bitteschoen"));
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
                String entityMsg = "";
                if(statusCode != HttpStatus.SC_NO_CONTENT){
                    try {
                        entityMsg = EntityUtils.toString(httpResponse.getEntity(),"UTF-8");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // Bedingung ? true : false
                    String errorMsg = " (Fehler " + httpResponse.getStatusLine().getStatusCode() + ")";
                    Toast.makeText(context, entityMsg + errorMsg, Toast.LENGTH_SHORT).show();
                }

                //else hier noch rein
                /*String msg = " (Fehler " + (stringIntegerPair != null ?
                        stringIntegerPair.second : "null") + ")";
                Toast.makeText(LogInActivity.this, errorMessage + msg, Toast.LENGTH_SHORT).show();*/
            }
        }.execute("http://10.0.2.2:9998/rest/transaction");// In Konstante speichern
    }
}
