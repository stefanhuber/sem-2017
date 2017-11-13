package fhku.bitcoinpriceticker;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    protected TextView textView;
    protected ResponseReader responseReader = new ResponseReader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.ticker);
        retrieveBitcoinPrice();
    }

    public void getPrice(View view) {
        retrieveBitcoinPrice();
    }

    public JsonReader sendRequest() {
        try {
            URL url = new URL("https://blockchain.info/ticker");
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            if (connection.getResponseCode() == 200) {

                // Response als InputStream
                InputStream response = connection.getInputStream();

                // JsonReader zur Verarbeitung des Response
                return new JsonReader(new InputStreamReader(response));
            }
        } catch (Exception e) {
            Log.e("HTTPS", "ERROR: " + e.getMessage());
        }

        return null;
    }

    public void retrieveBitcoinPrice() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                JsonReader jsonReader = sendRequest();
                if (jsonReader != null) {
                    final double rate = responseReader.parseResponse(jsonReader);

                    textView.post(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText("1 BTC in EUR: " + rate);
                        }
                    });
                }
            }
        });
    }




}
