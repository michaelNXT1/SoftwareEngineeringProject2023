package BusinessLayer.ExternalSystems;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.client.HttpClient;


import java.io.*;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class PaymentSystem implements IPaymentSystem {

    private final String url = "https://external-systems.000webhostapp.com/";

    HttpClient httpClient = HttpClientBuilder.create().build();

    HttpPost httpPost = new HttpPost(url);

    public static boolean loseContact = false;

    @Override
    public boolean handshake() {
        if (loseContact) return false;

        List<NameValuePair> params = new ArrayList<>(1);
        params.add(new BasicNameValuePair("action_type", "handshake"));
        String response = send(params);
        return response != null && response.equals("OK");
    }

    @Override
    public int pay(String cardNumber, String expirationMonth, String expirationYear, String holder, String ccv, String cardId) {
        if (loseContact) return -1;

        List<NameValuePair> params = new ArrayList<>(7);
        params.add(new BasicNameValuePair("action_type", "pay"));
        params.add(new BasicNameValuePair("card_number", cardNumber));
        params.add(new BasicNameValuePair("month", expirationMonth));
        params.add(new BasicNameValuePair("year", expirationYear));
        params.add(new BasicNameValuePair("holder", holder));
        params.add(new BasicNameValuePair("ccv", ccv));
        params.add(new BasicNameValuePair("id", cardId));

        String response = null;
        try {
             response = send(params);
        }catch(Exception e) {
            return -1;
            
        }
        int transactionId;
        try{
            transactionId = Integer.parseInt(response);
        }catch(NumberFormatException e){
            transactionId = -1;
        }
        if (response == null)
            return -1;
        return transactionId;
    }

    @Override
    public int cancelPay(int transactionId) {
        if (loseContact) return -1;

        List<NameValuePair> params = new ArrayList<>(2);
        params.add(new BasicNameValuePair("action_type", "cancel_pay"));
        params.add(new BasicNameValuePair("transaction_id", Integer.toString(transactionId)));

        String response = send(params);
        if (response == null) return -1;
        return response.equals("1") ? 1 : -1;
    }

    private String send(List<NameValuePair> parameters) {
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));

            // Set the request timeout to 60 minutes (3600000 milliseconds)
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(3600000)
                    .setConnectTimeout(3600000)
                    .build();
            httpPost.setConfig(requestConfig);

            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                try (InputStream inputStream = entity.getContent()) {
                    StringBuilder sb = new StringBuilder();
                    int c;
                    while ((c = inputStream.read()) >= 0) {
                        sb.append((char) c);
                    }
                    return sb.toString();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}