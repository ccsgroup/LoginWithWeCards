package com.wecardssdk.android.UI;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.wecardssdk.android.API.Handler.APIResponse;
import com.wecardssdk.android.API.Handler.APIResponseHandler;
import com.wecardssdk.android.API.Handler.DataHandler;
import com.wecardssdk.android.API.RestClient;
import com.wecardssdk.android.CountryCodeInfo.CountryCodeInfoController;
import com.wecardssdk.android.CustomView.DialogProgress;
import com.wecardssdk.android.Model.Handler.LoginRequestHandler;
import com.wecardssdk.android.Model.LoginRequest;
import com.wecardssdk.android.Model.RequestData;
import com.wecardssdk.android.R;
import com.wecardssdk.android.UI.Handler.ScreenHandler;
import com.wecardssdk.android.Utils.AppContacts;
import com.wecardssdk.android.Utils.AppTypeface;
import com.wecardssdk.android.Utils.Deprecation;
import com.wecardssdk.android.Utils.Validate;
import com.wecardssdk.android.Utils.appInstalledOrNot;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.wecardssdk.android.API.jsonKeys.API_KEY;
import static com.wecardssdk.android.API.jsonKeys.PACKAGENAME;
import static com.wecardssdk.android.Utils.AppContacts.COUNTRY_CODE_LIMIT;
import static com.wecardssdk.android.Utils.AppContacts.PHONE_NUMBER_LIMIT;
import static com.wecardssdk.android.Utils.AppContacts.Tag;
import static com.wecardssdk.android.Utils.AppContacts.loginActvity;
import static com.wecardssdk.android.Utils.AppContacts.loginWithAppIcon;
import static com.wecardssdk.android.Utils.AppContacts.loginWithAppName;
import static com.wecardssdk.android.Utils.AppContacts.loginWithAppPackageName;
import static com.wecardssdk.android.Utils.AppContacts.loginWithWecardKey;
import static com.wecardssdk.android.Utils.AppContacts.wecardsAppPackage;
import static com.wecardssdk.android.Utils.Deprecation.getColor;
import static com.wecardssdk.android.Utils.errorHandler.showErrorHandler;
import static com.wecardssdk.android.Utils.isNetworkAvailable.isNetworkAvailable;
import static com.wecardssdk.android.Utils.setDrawableBackGround.setDrawableBackGround;


/**
 * Created by mauliksantoki on 25/4/17.
 */

public class LoginScreen extends Dialog implements ScreenHandler {

    private Activity activity;

    private ImageView ivClose;
    private ImageView ivTitleAppIcon;
    private ImageView ivLoginAppIcon;
    private ImageView ivConfirmAppIcon;

    private TextView tvAppText;
    private TextView tvTitle;
    private TextView tvMessage;

    private LinearLayout llLogin;
    private RelativeLayout rlconfirm;

    private EditText etCountryCode;
    private EditText etPhoneNumber;
    private EditText etPasseord;

    private Button btnLogin;
    private Button btnCreateAccount;
    private Button btnCancel;
    private Button btnOK;

    private AppTypeface appTypeface;

    private APIResponse apiResponseHandler;

    private String apiResult = "";

    private ScrollView svLogin;

    private DialogProgress progressDialogCustom;

    /*other variable*/
    private CountryCodeInfoController countryCodeInfoController;

    private LoginRequestHandler loginRequestHandler;


    private int bottomScrollHeight = 0;

    public LoginScreen(Activity activity) {
        super(activity);
        this.activity = activity;
        show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.loginscreen);
        initControl();
        initDailogHeight();
        initlayouts();
        initTypeFace();
        initClickListener();


    }

    @Override
    public void initControl() {
        appTypeface = new AppTypeface(activity);
        countryCodeInfoController = new CountryCodeInfoController(activity);
        apiResponseHandler = new APIResponseHandler();
        loginRequestHandler = new RequestData();
    }


    @Override
    public void initDailogHeight() {

        // retrieve display dimensions
        Rect displayRectangle = new Rect();
        Window window = getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setCanceledOnTouchOutside(false);


    }

    @Override
    public void initlayouts() {
        ivClose = (ImageView) findViewById(R.id.ivClose);
        ivTitleAppIcon = (ImageView) findViewById(R.id.ivTitleAppIcon);
        ivLoginAppIcon = (ImageView) findViewById(R.id.ivLoginAppIcon);
        ivConfirmAppIcon = (ImageView) findViewById(R.id.ivConfirmAppIcon);

        tvAppText = (TextView) findViewById(R.id.tvAppText);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvMessage = (TextView) findViewById(R.id.tvMessage);


        etCountryCode = (EditText) findViewById(R.id.etCountryCode);
        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        etPasseord = (EditText) findViewById(R.id.etPasseord);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnCreateAccount = (Button) findViewById(R.id.btnCreateAccount);

        tvAppText.setText(activity.getResources().getString(R.string.loginwithmessage).replace("####", loginWithAppName));

        etCountryCode.setBackground(setDrawableBackGround(getColor(activity, R.color.Gray97), 0, 0, 8, 0, 0, 8));
        etPhoneNumber.setBackground(setDrawableBackGround(getColor(activity, R.color.Gray97), 0, 0, 0, 8, 8, 0));


        etCountryCode.setText("+" + countryCodeInfoController.getUserCountryCode().getDialingCode());
        etCountryCode.setSelection(etCountryCode.getText().toString().trim().length());

        etCountryCode.setFilters(new InputFilter[]{new InputFilter.LengthFilter(COUNTRY_CODE_LIMIT)});
        etPhoneNumber.setFilters(new InputFilter[]{new InputFilter.LengthFilter(PHONE_NUMBER_LIMIT)});

        ivLoginAppIcon.setImageDrawable(loginWithAppIcon);
        ivConfirmAppIcon.setImageDrawable(loginWithAppIcon);
        ivTitleAppIcon.setImageDrawable(Deprecation.getDrawable(activity, R.mipmap.logo));

        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnOK = (Button) findViewById(R.id.btnOK);

        llLogin = (LinearLayout) findViewById(R.id.llLogin);
        rlconfirm = (RelativeLayout) findViewById(R.id.rlconfirm);

        svLogin = (ScrollView) findViewById(R.id.svLogin);


        btnCreateAccount.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                btnCreateAccount.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                bottomScrollHeight = btnCreateAccount.getHeight();
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) btnCreateAccount.getLayoutParams();
                bottomScrollHeight = bottomScrollHeight + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
            }
        });
    }

    @Override
    public void initTypeFace() {
        tvAppText.setTypeface(appTypeface.getRegularFont());
        etCountryCode.setTypeface(appTypeface.getRegularFont());
        etPhoneNumber.setTypeface(appTypeface.getRegularFont());
        etPasseord.setTypeface(appTypeface.getRegularFont());
        btnLogin.setTypeface(appTypeface.getRegularFont());
        btnCreateAccount.setTypeface(appTypeface.getRegularFont());
        tvTitle.setTypeface(appTypeface.getRegularFont());
        tvMessage.setTypeface(appTypeface.getRegularFont());
        btnCancel.setTypeface(appTypeface.getRegularFont());
        btnOK.setTypeface(appTypeface.getRegularFont());

    }

    @Override
    public void initClickListener() {

           /*thid method for user can not remove + from edittext*/
        etCountryCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!(s.length() > 0)) {
                    etCountryCode.setText("+");

                } else {
                    if (!s.toString().startsWith("+")) {
                        etCountryCode.setText("+" + s.toString().trim().replace("+", ""));
                    }
                }
                etCountryCode.setSelection(etCountryCode.getText().length());
            }
        });

        etCountryCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    etPhoneNumber.requestFocus();
                    return true;
                }
                return false;
            }
        });


        etPasseord.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    btnLogin.performClick();
                    return true;
                }
                return false;
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()) {
                    APICall();
                }

            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginRequestHandler.loginCompleted(activity, apiResult);
                dismiss();

            }
        });

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAppInPlayStore();
            }
        });



          /*when click password field then scrool up to next button*/


        etPhoneNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, final boolean hasFocus) {
                if (hasFocus) {
                    scrollUpDialog();
                }
            }
        });
    }

    @Override
    public void scrollUpDialog() {
        svLogin.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                svLogin.post(new Runnable() {
                    public void run() {
                        ObjectAnimator scrollup = ObjectAnimator.ofInt(svLogin, "scrollY", (svLogin.getBottom() - bottomScrollHeight));
                        scrollup.setDuration(1000);
                        scrollup.start();
                    }
                });
            }
        });

    }


    @Override
    public void APICall() {
        hideSoftInput();
        showProgress();

        RestClient.GitApiInterface service = RestClient.getClient();

        LoginRequest loginRequest = loginRequestHandler.getRequestData();
        loginRequest.setCountry_code(etCountryCode.getText().toString().trim().replace("+", ""));
        loginRequest.setPassword(etPasseord.getText().toString().trim());
        loginRequest.setPhone_number(etPhoneNumber.getText().toString().trim());

        Call<ResponseBody> call = service.login(loginRequest);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    apiResult = apiResponseHandler.getResult(response);
                    apiResponseHandler.getData(activity, apiResult, new DataHandler() {
                        @Override
                        public void getData(String data, String message) {
                            apiResult = data;
                            llLogin.setVisibility(View.GONE);
                            rlconfirm.setVisibility(View.VISIBLE);
                            tvTitle.setText(activity.getResources().getString(R.string.confirmlogin));

                        }
                    });
                } else {
                    showErrorHandler(activity, AppContacts.CONTACT_US);
                }

                hideProgress();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(Tag, t.getMessage());
            }
        });
    }

    @Override
    public void hideSoftInput() {
        InputMethodManager inputManager =
                (InputMethodManager) activity.
                        getSystemService(activity.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(
                getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void openAppInPlayStore() {
        if (appInstalledOrNot.appInstalledOrNot(wecardsAppPackage, activity)) {
            /*
            * if application  install that time this message call
            */
            Intent loginWithWecards = new Intent(loginActvity);
            loginWithWecards.putExtra(API_KEY, loginWithWecardKey);
            loginWithWecards.putExtra(PACKAGENAME, loginWithAppPackageName);
            activity.startActivity(loginWithWecards);
        } else {
           /*
            * if application  not install that time this message call
            */
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + wecardsAppPackage)));
        }
    }

    @Override
    public void showProgress() {
        if (progressDialogCustom == null) {
            progressDialogCustom = new DialogProgress(activity);
        }
        progressDialogCustom.show();
    }

    @Override
    public void hideProgress() {
        if (progressDialogCustom != null) {
            progressDialogCustom.dismiss();
        }
    }

    @Override
    public boolean validation() {
        if (!isNetworkAvailable(activity)) {
            error(activity.getResources().getString(R.string.noInternetConnection));
            return false;
        } else if (Validate.isEmpty(etCountryCode.getText().toString().replace("+", "")) &&
                Validate.isEmpty(etPhoneNumber.getText().toString()) &&
                Validate.isEmpty(etPasseord.getText().toString())) {
            error(activity.getResources().getString(R.string.enterRequiredInformation));
            return false;
        } else if (Validate.isEmpty(etCountryCode.getText().toString().replace("+", ""))) {
            error(activity.getResources().getString(R.string.enterCoutryCode));
            return false;
        } else if (Validate.isEmpty(etPhoneNumber.getText().toString())) {
            error(activity.getResources().getString(R.string.enterPhoneNumber));
            return false;
        } else if (Validate.isEmpty(etPasseord.getText().toString())) {
            error(activity.getResources().getString(R.string.enterPassword));
            return false;
        }
        return true;
    }


    @Override
    public void error(String message) {
        showErrorHandler(activity, message);
    }

}
