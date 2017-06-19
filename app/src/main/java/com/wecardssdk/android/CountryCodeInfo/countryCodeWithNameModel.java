package com.wecardssdk.android.CountryCodeInfo;

/**
 * Created by mauliksantoki on 31/1/17.
 */

public class countryCodeWithNameModel {
    private String dialingCode="";
    private String isoCode="";
    private String coutryName="";
    private String header="";


    public countryCodeWithNameModel() {
    }

    public countryCodeWithNameModel(String dialingCode, String isoCode, String coutryName) {
        this.dialingCode = dialingCode;
        this.isoCode = isoCode;
        this.coutryName = coutryName;
    }

    public String getDialingCode() {
        return dialingCode;
    }

    public void setDialingCode(String dialingCode) {
        this.dialingCode = dialingCode;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public String getCoutryName() {
        return coutryName;
    }

    public void setCoutryName(String coutryName) {
        this.coutryName = coutryName;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}
