package com.ivan.github.app.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ivan.github.R;
import com.ivan.github.account.Account;
import com.ivan.github.app.AppSettings;
import com.ivan.github.app.BaseFragment;
import com.ivan.github.app.login.LoginActivity;
import com.ivan.github.app.login.SplashActivity;
import com.ivan.github.app.main.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 * @author Ivan
 */
public class SettingsFragment extends BaseFragment implements View.OnClickListener {

    private Button mBtnLogout;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        mBtnLogout = rootView.findViewById(R.id.btn_logout);
        mBtnLogout.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_logout) {
            doLogout();
        }
    }

    private void doLogout() {
        if (getActivity() == null) {
            return;
        }
        AppSettings.setFirstLogin(true);
        Account.getInstance().logout();
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }
}
