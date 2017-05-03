package com.example.pojo;

/**
 * @author mchidambaranathan 4/30/2017
 */
public class MessageUpdater {
    private String pageName;
    private String errorText="";
    private String message;

    public MessageUpdater(final String message) {
        this.message = message;
    }

    public MessageUpdater() {
    }

    public MessageUpdater(final String pageName, final String errorText) {
        this.pageName = pageName;
        this.errorText = errorText;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
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
