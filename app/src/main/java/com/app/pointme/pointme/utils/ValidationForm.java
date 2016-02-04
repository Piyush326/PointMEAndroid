package com.app.pointme.pointme.utils;

import android.util.Patterns;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by goparties on 29/1/16.
 */
public class ValidationForm {

    ArrayList<FormField> formFields;

    private ValidationForm() {
        formFields = new ArrayList<FormField>();
    }

    public static ValidationForm create() {
        return new ValidationForm();
    }

    public void add(FormField formField) {
        formFields.add(formField);
    }

    public FormField getFormField(TextView textView) {
        for (FormField formField : formFields) {
            if (formField.getTextView() == textView) {
                return formField;
            }
        }
        return null;
    }

    public boolean validate() {
        boolean validate = true;
        for (FormField formField : formFields) {
            String value = formField.getTextView().getText().toString();
            // if optional
            if (formField.isOptional() && value.equals("")) {
                continue;
            }
            boolean v = false;
            switch (formField.getType()) {
                case WEBLINK:
                    v = Patterns.WEB_URL.matcher(formField.getTextView().getText()
                            .toString())
                            .matches();
                    break;
                case EMAIL:
                    v = Patterns.EMAIL_ADDRESS.matcher(formField.getTextView().getText()
                            .toString())
                            .matches();
                    break;
                case PASSWORD:
                case SIMPLE:
                    v = !formField.getTextView().getText().toString().equals("");
                    if (formField.getMinLength() != -1) {
                        v = formField.getTextView().getText().toString().length() >= formField.getMinLength();
                        formField.setErrorMessage("Minimum of " + formField.getMinLength() + " characters required");
                    }
                    break;
            }
            if (v) {
                formField.getTextView().setError(null);
            } else {
                validate = false;
                formField.getTextView().setError(formField.getErrorMessage());
            }
        }
        return validate;
    }
}
