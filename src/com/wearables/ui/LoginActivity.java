package com.wearables.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.wearables.R;
import com.wearables.networking.NetworkConstants;
import com.wearables.utils.PopUp;
import com.wearables.utils.SharedPrefs;

public class LoginActivity extends Activity {
	private EditText mUserNameEdt, mPasswordEdt, mPatientUserNameEdt;
	private Button mSubmitBtn;
	private Spinner mRoleSpinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.login_layout);

		if (!TextUtils.isEmpty(SharedPrefs.getInstance(this).getParameters(
				NetworkConstants.CURRENT_USER_NAME))) {
			switchToUI();
		}

		mPatientUserNameEdt = (EditText) findViewById(R.id.patientUserNameEdt);
		mUserNameEdt = (EditText) findViewById(R.id.usernameEdt);
		mPasswordEdt = (EditText) findViewById(R.id.passwordEdt);
		mSubmitBtn = (Button) findViewById(R.id.submitBtn);
		mRoleSpinner = (Spinner) findViewById(R.id.roleSpinner);
		mRoleSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {

				switch (position) {
				case 0:
					mPatientUserNameEdt.setVisibility(View.GONE);
					System.out.println(mRoleSpinner.getSelectedItem());
					break;
				case 1:
					mPatientUserNameEdt.setVisibility(View.VISIBLE);
					System.out.println(mRoleSpinner.getSelectedItem());
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		mSubmitBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (mRoleSpinner.getSelectedItemPosition() == 1
						&& TextUtils.isEmpty(mPatientUserNameEdt.getText()
								.toString())) {
					PopUp popup = new PopUp(LoginActivity.this, null,
							getString(R.string.tag_patient_username_msg),
							getString(R.string.tag_ok));
					popup.show();
				} else {
					SharedPrefs.getInstance(LoginActivity.this).setParameters(
							NetworkConstants.CURRENT_USER_NAME,
							mUserNameEdt.getText().toString());
					if(mRoleSpinner.getSelectedItemPosition() == 1)
					SharedPrefs.getInstance(LoginActivity.this).setParameters(
							NetworkConstants.REQ_PARAM_UNAME,
							mPatientUserNameEdt.getText().toString().trim());
					else
						SharedPrefs.getInstance(LoginActivity.this).setParameters(
								NetworkConstants.REQ_PARAM_UNAME,
								mUserNameEdt.getText().toString().trim());
					
					SharedPrefs.getInstance(LoginActivity.this).setParameters(NetworkConstants.USER_ROLE, mRoleSpinner.getSelectedItem().toString());
					switchToUI();
					
				}

			}
		});
	}

	private void switchToUI() {
		Intent intent = new Intent(LoginActivity.this, MainActivity.class);
		startActivity(intent);
		finish();
	}
}
