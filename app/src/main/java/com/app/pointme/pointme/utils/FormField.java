package com.app.pointme.pointme.utils;

import android.widget.TextView;

/**
 * Created by goparties on 29/1/16.
 */
public class FormField {

    public enum TYPE {
        WEBLINK, EMAIL, PASSWORD, SIMPLE
    }

    TextView textView;
    TYPE type;
    String errorMessage;
    boolean watcher;
    boolean optional;
    int minLength;

    public boolean isWatcher() {
        return watcher;
    }

    public FormField setWatcher(boolean watcher) {
        this.watcher = watcher;
        return this;
    }

    public boolean isOptional() {
        return optional;
    }

    public FormField setOptional(boolean optional) {
        this.optional = optional;
        return this;
    }

    public FormField setMinLength(int minLength) {
        this.minLength = minLength;
        return this;
    }

    public int getMinLength() {
        return minLength;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public FormField setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public String getErrorMessage() {
        if (errorMessage == null) {
            switch (type) {
                case WEBLINK:
                    return "Incorrect Web Address";
                case EMAIL:
                    return "Incorrect Email Address";
                case PASSWORD:
                    return "Field can't be left blank";
                case SIMPLE:
                    return "Field can't be left blank";
            }
        }
        return errorMessage;
    }

    public static FormField create(TextView textView, TYPE type) {
        return new FormField(textView, type);
    }

    private FormField(TextView textView, TYPE type) {
        this.textView = textView;
        this.type = type;
        minLength = -1;
    }
}
