package BusinessLayer.ExternalSystems;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class SupplySystem implements ISupplySystem {

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
    public int supply(String name, String address, String city, String country, String zip) {
        if (loseContact) return -1;

        List<NameValuePair> params = new ArrayList<>(6);
        params.add(new BasicNameValuePair("action_type", "supply"));
        params.add(new BasicNameValuePair("name", name));
        params.add(new BasicNameValuePair("address", address));
        params.add(new BasicNameValuePair("city", city));
        params.add(new BasicNameValuePair("country", country));
        params.add(new BasicNameValuePair("zip", zip));

        String response = send(params);
        int transactionId;
        try{
            transactionId = Integer.parseInt(response);
        }catch(NumberFormatException e){
            transactionId = -1;
        }
        if (response == null) return -1;
        return transactionId;
    }

    @Override
    public int cancelSupply(int transactionId) {
        if (loseContact) return -1;

        List<NameValuePair> params = new ArrayList<>(2);
        params.add(new BasicNameValuePair("action_type", "cancel_supply"));
        params.add(new BasicNameValuePair("transaction_id", Integer.toString(transactionId)));

        String response = send(params);
        if (response == null) return -1;
        return response.equals("1") ? 1 : -1;
    }


    private String send(List<NameValuePair> parameters) {
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (response == null) return null;

        HttpEntity entity = response.getEntity();
        if (entity != null) {
            try (InputStream inputStream = entity.getContent()) {
                StringBuilder sb = new StringBuilder();
                int c;
                while((c = inputStream.read()) >= 0) {
                    sb.append((char) c);
                }
                return sb.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
