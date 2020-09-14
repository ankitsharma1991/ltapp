package com.accusterltapp.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.accusterltapp.database.AppPreference;
import com.accusterltapp.model.PathlogistDetails;
import com.accusterltapp.table.TableCamp;
import com.base.model.CampDetails;
import com.base.utility.DateTimeUtils;
import com.base.utility.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.accusterltapp.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

import custom.CustomLablledSpinner;
import custom.CustomSpinnerAdapter;
import custom.CustomSpinnerItemListener;


public class CreateCampFragment extends Fragment implements View.OnClickListener, CustomSpinnerItemListener {

    private DatePickerDialog essueDatePickerDialog;
    private DatePickerDialog essueDatePickerdob;
    private java.text.SimpleDateFormat dateFormatter;
    private EditText organisation_name, camp_name, address, description;
    private TextView startdate, enddate, starttime, endtime;
    private CustomLablledSpinner pythologistName;
    private ArrayList<PathlogistDetails> product_list;
    private String pathologistId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragmentcreatecamp, container, false);
        startdate = view.findViewById(R.id.et_startdate);
        enddate = view.findViewById(R.id.et_enddate);
        camp_name = view.findViewById(R.id.et_camp_name);
        address = view.findViewById(R.id.et_address);
        starttime = view.findViewById(R.id.et_starttime);
        endtime = view.findViewById(R.id.et_endtime);
        description = view.findViewById(R.id.et_venuename);
        pythologistName = view.findViewById(R.id.sp_product_list);
        pythologistName.hideHint(true);
        pythologistName.setClickListener(this);

        product_list = new Gson().fromJson(AppPreference.getString(getActivity(), AppPreference.PATHOLOGIST_LIST)
                , new TypeToken<ArrayList<PathlogistDetails>>() {
                }.getType());
        CustomSpinnerAdapter campListAdapter = new CustomSpinnerAdapter(getContext(),
                android.R.layout.simple_spinner_dropdown_item, product_list);
        campListAdapter.setShowDefaultText(false);
        pythologistName.setAdapter(campListAdapter);

        //get realm instance
        dateFormatter = new java.text.SimpleDateFormat("yyyy/MM/dd", Locale.US);
        Calendar newCalendar = Calendar.getInstance();
        Button bt_save = view.findViewById(R.id.bt_save);
        organisation_name = view.findViewById(R.id.sp_create_organisation_name);
        organisation_name.setText(AppPreference.getString(getActivity(), AppPreference.ORGANISATION_NAME));
        bt_save.setOnClickListener(this);
        starttime.setOnClickListener(this);
        endtime.setOnClickListener(this);
        startdate.setOnClickListener(this);


        essueDatePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                startdate.setText(dateFormatter.format(newDate.getTime()));

            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        try {
            //essueDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

        } catch (Exception e) {
            e.fillInStackTrace();
        }


        ////////// End Date \\\\\\\\\\\\\

        enddate.setOnClickListener(this);

        essueDatePickerdob = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                enddate.setText(dateFormatter.format(newDate.getTime()));

            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        try {

          //  essueDatePickerdob.getDatePicker().setMinDate(System.currentTimeMillis());
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return view;
    }


    //////////  Validation \\\\\\\\\\\\\
    private boolean Validation() {

//        if (organisation_name.getText().toString().isEmpty()) {
//            Toast.makeText(getContext(), "Please enter the Organization name", Toast.LENGTH_SHORT).show();
//            return false;
//        } else
        if (camp_name.getText().toString().isEmpty()) {
            camp_name.setError("Please enter the Camp name");
            return false;
        } else if (address.getText().toString().isEmpty()) {
            address.setError("Please enter the Address name");
            return false;
        } else if (startdate.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Please enter the To Date", Toast.LENGTH_SHORT).show();
            return false;
        } else if (enddate.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Please enter the From Date", Toast.LENGTH_SHORT).show();
            return false;
        } else if (starttime.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Please enter the To Time", Toast.LENGTH_SHORT).show();
            return false;
        } else if (endtime.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Please enter the From Time", Toast.LENGTH_SHORT).show();
            return false;
        } else if (description.getText().toString().isEmpty()) {
            description.setError("Please enter the Venue Name");
            return false;
        }
//        else if (TextUtils.isEmpty(pathologistId)) {
//            Toast.makeText(getContext(), "Please enter the Pathologist Name", Toast.LENGTH_SHORT).show();
//            return false;
//        }
        return true;
    }


    public void refreshPage() {
        camp_name.setText("");
        address.setText("");
        startdate.setText("");
        enddate.setText("");
        starttime.setText("");
        endtime.setText("");
        description.setText("");
    }

    @Override
    public void onClick(View v) {
        int hour;
        int minute;
        if (v.getId() == R.id.et_starttime) {
            try {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                hour = c.get(Calendar.HOUR_OF_DAY);
                minute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                starttime.setText(hourOfDay > 12 ? (hourOfDay - 12 + " : " + minute + " PM")
                                        : (hourOfDay + " : " + minute) + " AM");
                            }
                        }, hour, minute, false);
                timePickerDialog.show();

            } catch (Exception e) {
                e.fillInStackTrace();
            }
        } else if (v.getId() == R.id.et_endtime) {
            try {
                // Get Current Time

                final Calendar c = Calendar.getInstance();
                hour = c.get(Calendar.HOUR_OF_DAY);
                minute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                endtime.setText(hourOfDay > 12 ? (hourOfDay - 12 + " : " + minute + " PM")
                                        : (hourOfDay + " : " + minute) + " AM");
                            }
                        }, hour, minute, false);
                timePickerDialog.show();


            } catch (Exception e) {
                e.fillInStackTrace();
            }
        } else if (v.getId() == R.id.bt_save) {
            if (Validation()) {
                saveCampDetails();
            }
        } else if (v.getId() == R.id.et_startdate) {
            essueDatePickerDialog.show();
        } else if (v.getId() == R.id.et_enddate) {
            essueDatePickerdob.show();
        }
    }

    private void saveCampDetails() {
//        orgId,createTime
        CampDetails campDetails = new CampDetails();
        campDetails.setOrgName(organisation_name.getText().toString());
        campDetails.setCamp_organization_id(AppPreference.getString(getActivity(), AppPreference.ORGANISATION_ID));
        campDetails.setCampName(camp_name.getText().toString());
        campDetails.setCamp_code(getSaltString());
        campDetails.setAddress(address.getText().toString());
        campDetails.setStartDate(startdate.getText().toString());
        campDetails.setEndDate(enddate.getText().toString());
        campDetails.setStartTime(starttime.getText().toString());
        campDetails.setEndTime(endtime.getText().toString());
        campDetails.setCamp_description(description.getText().toString());
        campDetails.setCamp_pathlogist_Id(pathologistId);
        campDetails.setCamp_created_date_time(DateTimeUtils.getCurrentDate(DateTimeUtils.INPUT_DATE_YYYY_MM_DD_TS_HYPHON));
        campDetails.setCamp_created_user_id(AppPreference.getString(getActivity(), AppPreference.USER_ID));
        TableCamp tableCamp = new TableCamp(getActivity());
        tableCamp.insertCamp(campDetails);
        ToastUtils.showShortToastMessage(getActivity(), "Camp Saved Successfully");
        getActivity().getSupportFragmentManager().popBackStackImmediate();

    }


    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();

    }


    @Override
    public void onItemSelection(Spinner spinnerView, int position) {
        try {
            if (position >= 0) {
                pathologistId = product_list.get(position).getUserregistration_id();
            } else {
                pathologistId = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}