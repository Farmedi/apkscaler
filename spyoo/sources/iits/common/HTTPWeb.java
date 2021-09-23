package iits.common;

import android.util.Log;
import iits.mamager.FileManager;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpProtocolParams;

public class HTTPWeb {
    private static final String TAG = "HTTPWeb";

    public static HttpResponse sendGetMethod(String link) {
        try {
            Log.e("test", link);
            FileManager.WriteLog(1, TAG, link);
            HttpGet method = new HttpGet();
            HttpClient client = new DefaultHttpClient();
            HttpProtocolParams.setUseExpectContinue(client.getParams(), false);
            method.setURI(new URI(link));
            return client.execute(method);
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on sendGetMethod function: " + e.toString());
            return null;
        }
    }

    public static HttpResponse sendPostMethod(String url, ArrayList<String> arrKey, ArrayList<String> arrValue) {
        try {
            HttpClient client = new DefaultHttpClient();
            HttpProtocolParams.setUseExpectContinue(client.getParams(), false);
            HttpPost post = new HttpPost(url);
            List<NameValuePair> params = new ArrayList<>();
            for (int i = 0; i < arrKey.size(); i++) {
                params.add(new BasicNameValuePair(arrKey.get(i), arrValue.get(i)));
            }
            post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            return client.execute(post);
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on sendPostMethod function: " + e.toString());
            return null;
        }
    }
}
