package com.accusterltapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.accusterltapp.activity.DeviceListActivity;
import com.accusterltapp.activity.MainActivity;
import com.accusterltapp.activity.Splash;
import com.accusterltapp.activity.SynDataStatus;
import com.accusterltapp.database.AppPreference;
import com.accusterltapp.login.LoginActivity;
import com.accusterltapp.model.CampDetails1;
import com.accusterltapp.model.Heleprec;
import com.accusterltapp.table.TableCamp;
import com.accusterltapp.table.TablePackageList;
import com.accusterltapp.table.TablePatient;
import com.accusterltapp.table.TablePatientTest;
import com.base.fragment.BaseFragment;
import com.accusterltapp.R;

import java.util.ArrayList;


public class HomeFragment extends BaseFragment {

    private ImageView imge_create_and_camp, image_register_patient,
            image_package_list, image_aboutus, image_edit_profile, image_logout;
    ArrayList<CampDetails1> list;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_fragment, container, false);

        imge_create_and_camp = view.findViewById(R.id.imge_create_and_camp);
        image_register_patient = view.findViewById(R.id.image_register_patient);
        image_package_list = view.findViewById(R.id.image_package_list);
        image_aboutus = view.findViewById(R.id.image_aboutus);
        image_edit_profile = view.findViewById(R.id.image_edit_profile);
        image_logout = view.findViewById(R.id.image_logout);
        imge_create_and_camp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.navItemIndex = 3;
                ((MainActivity)getActivity()).setToolbarTitle();
                CreateCampFragment paying = new CreateCampFragment();
                setFragment(paying, CreateCampFragment.class.getSimpleName());

            }
        });

        image_register_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MainActivity.navItemIndex = 1;
                ((MainActivity)getActivity()).setToolbarTitle();
                PatientRegistrationFragment patient_detail = new PatientRegistrationFragment();
                setFragment(patient_detail, PatientRegistrationFragment.class.getSimpleName());
            }
        });


        image_package_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.navItemIndex = 5;
                ((MainActivity)getActivity()).setToolbarTitle();
                PackageListFragment package_list = new PackageListFragment();
                setFragment(package_list, PackageListFragment.class.getSimpleName());

            }
        });


        image_aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.navItemIndex = 2;
                 ((MainActivity)getActivity()).setToolbarTitle();
               //// AboutUs about_us = new AboutUs();
               // setFragment(about_us, AboutUs.class.getSimpleName());

                QcDataFragment qcdata=new QcDataFragment();
                setFragment(qcdata,QcDataFragment.class.getSimpleName());

            }
        });

        image_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SynDataStatus.class));
            }
        });

        image_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              /*  if (new TableCamp(getActivity()).getSynStatus() && new TablePatient(getActivity()).getallPatientSynStatus()
                        && new TablePatientTest(getActivity()).getPatientTestSynStatus()) {
                    AppPreference.clearData(getActivity());
                    new TableCamp(getActivity()).deleteTableContent();
                    new TablePatient(getActivity()).deleteTableContent();
                    new TablePatientTest(getActivity()).deleteTableContent();
                    new TablePackageList(getActivity()).deleteTableContent();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().finish();
                } else {
                    startActivity(new Intent(getActivity(), SynDataStatus.class));
                }*/

                MainActivity. navItemIndex=11;
                CampListFragment1 mAboutU1 = new CampListFragment1();
                setFragment(mAboutU1, CampListFragment1.class.getSimpleName());
                ((MainActivity)getActivity()).setToolbarTitle();
                Heleprec.avroverreport=true;
            }
        });

        ((MainActivity) getActivity()).selectNavMenu();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Home");
    }
    public boolean conectivity() {

        ConnectivityManager connectivity = (ConnectivityManager)this.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (Splash.btSocket == null || !Splash.btSocket.isConnected()) {
            inflater.inflate(R.menu.bluetooth_menu, menu);
        }
        if(!Heleprec.con)
            inflater.inflate(R.menu.bluetooth_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(getActivity(), DeviceListActivity.class));
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
}


