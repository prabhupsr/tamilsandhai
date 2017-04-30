package com.example.pojo;

/**
 * @author mchidambaranathan 4/30/2017
 */
public class LoginValidator {
    private String pageName;
    private String errorText="";

    public LoginValidator() {
    }


    public LoginValidator(final String pageName, final String errorText) {
        this.pageName = pageName;
        this.errorText = errorText;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(final String pageName) {
        this.pageName = pageName;
    }

    public String getErrorText() {
        return errorText;
    }

    public void setErrorText(final String errorText) {
        this.errorText = errorText;
    }
}
