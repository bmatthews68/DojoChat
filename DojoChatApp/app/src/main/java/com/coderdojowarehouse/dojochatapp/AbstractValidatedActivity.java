package com.coderdojowarehouse.dojochatapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.Validator;

import butterknife.ButterKnife;

public abstract class AbstractValidatedActivity extends AppCompatActivity implements Validator.ValidationListener, TextWatcher {

    private Validator validator;

    protected void setupValidation() {

        ButterKnife.bind(this);

        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    protected void addValidation(final EditText view) {
        view.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(final CharSequence s,
                                  final int start,
                                  final int count,
                                  final int after) {

    }

    @Override
    public void onTextChanged(final CharSequence s,
                              final int start,
                              final int before,
                              final int count) {

    }

    @Override
    public void afterTextChanged(final Editable s) {
        validator.validate(true);
    }
}
