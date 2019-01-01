package project.wip.androidclient;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Pair;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class connects to the server.
 * @author Sebastian Rieck
 */
public class ServerConnection {

    private String errorMessage;
    public static String mIpAddress;
    private static Account account;
    public static String balance;
    private Boolean transactionSuccessful = true;
    private Boolean logInSuccessful = true;

    /**
     * The method getAccount() connects to the server to get an object of type Account.
     * @param accountNumber should be four unique characters that identify the account to get
     * @param context Interface to global information about the application environment
     * @param ipAddress The IP where the server can be found
     * @author Sebastian Rieck
     */
    @SuppressLint("StaticFieldLeak")
    public void getAccount(String accountNumber, final Context context, String ipAddress){
        mIpAddress = ipAddress;

        new AsyncTask<String, Void, Pair<String, Integer>>() {

            /**
             * An async task that commits an HttpGet.
             * @param strings arbitrary number of arguments to pass for the async task
             * @return a pair of json information for the Account object and a status code of the
             * committed HttpGet.
             * @author Sebastian Rieck
             */
            @Override
            protected Pair<String, Integer> doInBackground(String... strings) {
                final HttpParams httpParams = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 2000);
                DefaultHttpClient client = new DefaultHttpClient(httpParams);
                //HttpClient client = new DefaultHttpClient();
                HttpGet get = new HttpGet(strings[0]);

                try {
                    HttpResponse response = client.execute(get);
                    if(response == null) return null;
                    int statusCode = response.getStatusLine().getStatusCode();
                    String json = null;

                    if(statusCode == HttpStatus.SC_OK){
                        json = EntityUtils.toString(response.getEntity(),"UTF-8");
                        logInSuccessful = true;
                    } else {
                        errorMessage = EntityUtils.toString(response.getEntity());
                        logInSuccessful = false;
                    }

                    return Pair.create(json, statusCode);
                } catch (IOException e) {
                    logInSuccessful = false;
                    e.printStackTrace();
                }
                return null;
            }

            /**
             * Gets called in the end of the async background calculation
             * @param stringIntegerPair the result of the async task
             * @author Sebastian Rieck
             */
            @Override
            protected void onPostExecute(Pair<String, Integer> stringIntegerPair) {
                if(stringIntegerPair != null && stringIntegerPair.first != null){
                    String json = stringIntegerPair.first;
                    Gson gson = new GsonBuilder().create();
                    account = gson.fromJson(json, Account.class);
                }
                else{
                    if(stringIntegerPair == null){
                        Toast.makeText(context,"Server nicht verfügbar",Toast.LENGTH_SHORT).show();
                    } else {
                        String msg = " (Fehler " + (stringIntegerPair != null ?
                                stringIntegerPair.second : "null") + ")";
                        Toast.makeText(context,errorMessage + msg,Toast.LENGTH_SHORT).show();
                    }
                }
                if(transactionSuccessful && logInSuccessful){
                    Intent intent = new Intent(context,MainActivity.class);
                    context.startActivity(intent);
                }
            }

        }.execute(String.format("http://%s/rest/account/%s",mIpAddress,accountNumber));
    }

    public static Account getCurrentAccount(){
        return account;
    }

    /**
     * A transaction gets posted.
     * @param context Interface to global information about the application environment
     * @param senderNumber the bank account of the sender
     * @param receiverNumber the bank account of the receiver
     * @param amount the amount of money
     * @param reference a text linked to the transaction
     * @author Sebastian Rieck
     */
    @SuppressLint("StaticFieldLeak")
    public void postTransaction(final Context context,String senderNumber,String receiverNumber,
                                String amount,String reference){

        AsyncTask<String, Void, HttpResponse> info = new AsyncTask<String, Void, HttpResponse>() {
            /**
             * An async task that executes an http post.
             * @param strings arbitrary number of arguments to pass for the async task
             * @return the result of the request
             * @author Sebastian Rieck
             */
            @Override
            protected HttpResponse doInBackground(String... strings) {
                final HttpParams httpParams = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 2000);
                DefaultHttpClient client = new DefaultHttpClient(httpParams);

                //HttpClient client = new DefaultHttpClient();

                HttpPost post = new HttpPost(strings[0]);
                List<NameValuePair> parameterList = new ArrayList<>();
                parameterList.add(new BasicNameValuePair("senderNumber", senderNumber));
                parameterList.add(new BasicNameValuePair("receiverNumber", receiverNumber));
                parameterList.add(new BasicNameValuePair("amount", amount));
                parameterList.add(new BasicNameValuePair("reference", reference));
                try {

                    UrlEncodedFormEntity form = new UrlEncodedFormEntity(parameterList,"UTF-8");

                    post.setEntity(form);
                    return client.execute(post);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            /**
             * Gets called in the end of the async background calculation
             * @param httpResponse the result of the async task
             * @author Sebastian Rieck
             */
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
                    String errorMsg = String.format(" (Fehler %s ",httpResponse.getStatusLine()
                            .getStatusCode());
                    Toast.makeText(context, String.format("%s%s",entityMsg,errorMsg),
                            Toast.LENGTH_SHORT).show();
                    transactionSuccessful = false;
                } else {
                    transactionSuccessful = true;
                }
            }
        }.execute(String.format("http://%s/rest/transaction",mIpAddress));
    }
}