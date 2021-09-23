package iits.service;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import iits.code.bus.Setting_BUS;
import iits.code.dto.Setting_DTO;
import iits.mamager.FileManager;

public class GPSService extends Service {
    private static final String TAG = "GPSService";
    private LocationManager lm;
    private GPSListener locationListener;
    private Setting_DTO setting;

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        startGPS();
    }

    public void onDestroy() {
        super.onDestroy();
        stopGPS();
    }

    public void startGPS() {
        try {
            this.setting = Setting_BUS.get(getApplicationContext());
            long minTime = (long) (this.setting.getGPSInterval() * 60 * 1000);
            this.lm = (LocationManager) getSystemService("location");
            this.locationListener = new GPSListener(this, null);
            this.lm.requestLocationUpdates("gps", minTime, (float) this.setting.getHorizontal(), this.locationListener);
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

    /* access modifiers changed from: private */
    public class GPSListener implements LocationListener {
        private GPSListener() {
        }

        /* synthetic */ GPSListener(GPSService gPSService, GPSListener gPSListener) {
            this();
        }

        public void onLocationChanged(Location loc) {
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }
}
