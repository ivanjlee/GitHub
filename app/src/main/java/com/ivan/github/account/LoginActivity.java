package com.ivan.github.account;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.design.widget.CompoundDrawablesTextView;
import com.github.utils.KeyboardUtils;
import com.github.utils.L;
import com.ivan.github.GitHub;
import com.ivan.github.R;
import com.ivan.github.app.BaseActivity;
import com.ivan.github.app.activity.MainActivity;
import com.ivan.github.web.UrlConst;
import com.ivan.github.web.WebActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * A login screen that offers login via email/password.
 *
 * @author  Ivan on 2017/10/6 19:45.
 * @version v0.1
 * @since   v0.1
 */

public class LoginActivity extends BaseActivity implements LoaderCallbacks<Cursor>, OnClickListener {

    private static final String TAG = LoginActivity.class.getSimpleName();
    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private Toolbar mToolbar;
    private CompoundDrawablesTextView mTvMessage;
    private AutoCompleteTextView mTVUsername;
    private EditText mTVPassword;
    private Button mBtnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupActionBar();
        // Set up the login form.
        initView();
        populateAutoComplete();
        initLinks();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAuthTask != null) {
            mAuthTask.cancel(true);
            mAuthTask = null;
        }
    }

    private void initView() {
        mTVUsername = findViewById(R.id.tv_username);
        mTvMessage = findViewById(R.id.tv_msg);
        mTvMessage.setDrawableRightClickListener(view -> mTvMessage.setVisibility(View.GONE));
        mTVPassword = findViewById(R.id.tv_password);
        mTVPassword.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == R.id.btn_sign_in || id == EditorInfo.IME_ACTION_DONE) {
                attemptLogin();
                KeyboardUtils.hideSoftKeyboard(mTVPassword);
                return true;
            }
            return false;
        });

        mBtnSignIn = findViewById(R.id.btn_sign_in);
        mBtnSignIn.setOnClickListener(view -> attemptLogin());
    }

    private void populateAutoComplete() {
        getLoaderManager().initLoader(0, null, this);
    }

    private void initLinks() {
        findViewById(R.id.tv_forget_password).setOnClickListener(this);
        findViewById(R.id.tv_create_an_account).setOnClickListener(this);
        findViewById(R.id.tv_terms).setOnClickListener(this);
        findViewById(R.id.tv_privacy).setOnClickListener(this);
        findViewById(R.id.tv_security).setOnClickListener(this);
        findViewById(R.id.tv_contact_github).setOnClickListener(this);
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
    }

    private void setupActionBar() {
        mToolbar = findViewById(R.id.toolbar);
        initToolbar(mToolbar);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }
        // Reset errors.
        mTVUsername.setError(null);
        mTVPassword.setError(null);
        setErrorMsg(null);

        // Store values at the time of the login attempt.
        String username = mTVUsername.getText().toString();
        String password = mTVPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            setErrorMsg(getString(R.string.error_incorrect_password));
            focusView = mTVPassword;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(username)) {
            setErrorMsg(getString(R.string.error_incorrect_password));
            focusView = mTVUsername;
            cancel = true;
        } else if (!isEmailValid(username)) {
            setErrorMsg(getString(R.string.error_invalid_email));
            focusView = mTVUsername;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(username, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    private void setErrorMsg(String msg) {
        if (TextUtils.isEmpty(msg)) {
            mTvMessage.setVisibility(View.GONE);
        } else {
            mTvMessage.setText(msg);
            mTvMessage.setVisibility(View.VISIBLE);
        }
    }

    private void showProgress(final boolean show) {
        if (show) {
            mBtnSignIn.setText(R.string.action_sign_in_in_progress);
            mBtnSignIn.setEnabled(false);
        } else {
            mBtnSignIn.setText(R.string.action_sign_in);
            mBtnSignIn.setEnabled(true);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        if (cursorLoader == null || cursor == null) {
            return;
        }
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }
        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mTVUsername.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.tv_forget_password:
                WebActivity.start(this, UrlConst.GITHUB_FORGET_PASSWORD, getString(R.string.reset_your_password));
                break;
            case R.id.tv_create_an_account:
                WebActivity.start(this, UrlConst.GITHUB_REGISTER, getString(R.string.join_github));
                break;
            case R.id.tv_terms:
                WebActivity.start(this, UrlConst.GITHUB_TERMS, getString(R.string.github_terms_of_service));
                break;
            case R.id.tv_privacy:
                WebActivity.start(this, UrlConst.GITHUB_PRIVACY, getString(R.string.github_privacy_statement));
                break;
            case R.id.tv_security:
                WebActivity.start(this, UrlConst.GITHUB_SECURITY, getString(R.string.github_security));
                break;
            case R.id.tv_contact_github:
                WebActivity.start(this, UrlConst.GITHUB_CONTACT_GITHUB, getString(R.string.get_help_with_github));
                break;
        }
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    private class UserLoginTask extends AsyncTask<Void, Void, Pair<Boolean, Throwable>> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Pair<Boolean, Throwable> doInBackground(Void... params) {
            final String auth = Credentials.basic(mEmail, mPassword);
            Call<List<Authorization>> call = GitHub.appComponent().githubService().listAuthorizations(auth);
            try {
                Response<List<Authorization>> response = call.execute();
                if (response.isSuccessful()) {
                    Call<User> userCall = GitHub.appComponent().githubService().getAuthorizedUser(auth);
                    Response<User> userResponse = userCall.execute();
                    if (userResponse.isSuccessful()) {
                        Account.getInstance().init(userResponse.body(), auth);
                        return new Pair<>(true, null);
                    } else {
                        return new Pair<>(false, new HttpException(userResponse));
                    }
                } else {
                    return new Pair<>(false, new HttpException(response));
                }
            } catch (IOException e) {
                L.e(TAG, e);
                return new Pair<>(false, e);
            }
        }

        @Override
        protected void onPostExecute(final Pair<Boolean, Throwable> result) {
            mAuthTask = null;
            showProgress(false);
            if (result.first == null) {
                setErrorMsg(getString(R.string.error_network_error));
                mTVPassword.requestFocus();
            } else if (result.first) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_LONG).show();
                LoginActivity.this.finish();
            } else if (result.second instanceof HttpException) {
                if (((HttpException) result.second).code() == 401) {
                    setErrorMsg(getString(R.string.error_incorrect_password));
                    mTVPassword.requestFocus();
                } else {
                    setErrorMsg(((HttpException) result.second).message());
                    mTVPassword.requestFocus();
                }
            } else {
                if (result.second != null) {
                    setErrorMsg(result.second.getLocalizedMessage());
                    mTVPassword.requestFocus();
                } else {
                    setErrorMsg(getString(R.string.error_network_error));
                    mTVPassword.requestFocus();
                }
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}