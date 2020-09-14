package com.accusterltapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.accusterltapp.database.AppPreference;
import com.accusterltapp.fragment.UnSynCampList;
import com.accusterltapp.fragment.UnSynPatientList;
import com.accusterltapp.login.LoginActivity;
import com.accusterltapp.table.TableCamp;
import com.accusterltapp.table.TablePackageList;
import com.accusterltapp.table.TablePatient;
import com.accusterltapp.table.TablePatientTest;
import com.base.activity.BaseActivity;
import com.base.adapter.ViewPagerAdapter;
import com.accusterltapp.R;

/**
 * Created by sqyuser on 4/1/18.
 */

public class SynDataStatus extends BaseActivity {
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.activity_tab);
        setActionBar(R.id.include_toolbar);
        setToolBarTittle("Un-Synced Data");
        viewPager = (ViewPager) findViewById(R.id.mViewPqager);
        viewPager.setOffscreenPageLimit(3);
        setupViewPagerAdapter(viewPager);
    }

    private void setupViewPagerAdapter(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new UnSynCampList(), "Camps");
        adapter.addFragment(new UnSynPatientList(), "Patients");
        viewPager.setAdapter(adapter);
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
                new TablePackageList(this).deleteTableContent();
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
