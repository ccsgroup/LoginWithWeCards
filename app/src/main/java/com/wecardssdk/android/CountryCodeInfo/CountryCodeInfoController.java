package com.wecardssdk.android.CountryCodeInfo;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.wecardssdk.android.R;
import com.wecardssdk.android.Utils.AppContacts;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.wecardssdk.android.Utils.errorHandler.showErrorHandler;


/**
 * Created by mauliksantoki on 31/1/17.
 */

public class CountryCodeInfoController {
    private Activity context;
    private List<countryCodeWithNameModel> countryCodeInfoControllerListArray = new ArrayList<countryCodeWithNameModel>();
    private String[] info;

    public CountryCodeInfoController(Activity con) {
        context = con;
        intArray();
    }

    public void intArray() {
        String[] rl = context.getResources().getStringArray(R.array.fullCountryInfo);
        for (String s : rl) {
            info = s.split(",", 3);
            countryCodeInfoControllerListArray.add(new countryCodeWithNameModel(
                    info[0],
                    info[1],
                    info[2]
            ));
        }
    }

    public List<countryCodeWithNameModel> getCountryArrayList(Activity con) {
        if (context == null) {
            context = con;
        }

        if (countryCodeInfoControllerListArray == null || countryCodeInfoControllerListArray.size() <= 0) {
            intArray();
        }
        return countryCodeInfoControllerListArray;
    }

    public countryCodeWithNameModel getData(String value) {
        if (value != null) {
            for (countryCodeWithNameModel model : countryCodeInfoControllerListArray) {
                if (model.getDialingCode().equalsIgnoreCase(value) || model.getIsoCode().equalsIgnoreCase(value)) {
                    return model;
                }
            }
        }
        return new countryCodeWithNameModel();
    }


    public countryCodeWithNameModel getUserCountryCode() {
        return getData(getUserCountry());
    }

    private String getUserCountry() {
        try {
            final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            final String simCountry = tm.getSimCountryIso();
            if (simCountry != null && simCountry.length() == 2) { // SIM country
                // code is available
                return simCountry.toUpperCase(Locale.US);
            } else if (tm.getPhoneType() != TelephonyManager.PHONE_TYPE_CDMA) { // device
                // is not 3G(would be unreliable)
                String networkCountry = tm.getNetworkCountryIso();
                if (networkCountry != null && networkCountry.length() == 2) { // network
                    // country code is available
                    return networkCountry.toUpperCase(Locale.US);
                }
            } else {
                return Locale.getDefault().getCountry().toUpperCase(Locale.US);

            }
        } catch (Exception e) {
            showErrorHandler(context, AppContacts.CONTACT_US);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return context.getResources().getConfiguration().getLocales().get(0).getCountry();
        } else {
            return context.getResources().getConfiguration().locale.getCountry();
        }

    }


}
