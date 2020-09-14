package com.accusterltapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.accusterltapp.database.AppPreference;
import com.accusterltapp.fragment.PatientPreview;
import com.accusterltapp.login.LoginActivity;
import com.accusterltapp.table.TableCamp;
import com.accusterltapp.table.TablePatient;
import com.accusterltapp.table.TablePatientTest;
import com.base.activity.BaseActivity;
import com.accusterltapp.R;

public class UnsynReportView extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unsyn_report_view);
        setActionBar(R.id.include_toolbar);
        setToolBarTittle("Syn Report");
        PatientPreview patient_preview = new PatientPreview();
        patient_preview.setArguments(getIntent().getBundleExtra("userData"));
        //Inflate the fragment
        setFragment(patient_preview, PatientPreview.class.getSimpleName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.force_logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.force_logout:
                AppPreference.clearData(this);
                new TableCamp(this).deleteTableContent();
                new TablePatient(this).deleteTableContent();
                new TablePatientTest(this).deleteTableContent();
                startActivity(new Intent(this, LoginActivity.class));
                return false;
            case android.R.id.home:
                onBackPressed();
                return false;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStackImmediate();
        } else {
            finish();
        }
    }
}
