package iits.mamager;

import android.content.Context;
import iits.common.HTTPWeb;
import org.apache.http.HttpResponse;

public class LoginManager {
    private static final String TAG = "SettingManager";

    public static HttpResponse login(Context context, String url, String userName, String password, String deviceID) {
        try {
            StringBuilder builder = new StringBuilder();
            builder.append(url);
            builder.append("?username=" + userName);
            builder.append("&password=" + password);
            builder.append("&deviceid=" + deviceID);
            return HTTPWeb.sendGetMethod(builder.toString());
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on login function: " + e.toString());
            return null;
        }
    }
}
