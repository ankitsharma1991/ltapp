package com.accusterltapp.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;

import com.accusterltapp.activity.DeviceListActivity;
import com.accusterltapp.activity.EditTestActivity;
import com.accusterltapp.activity.Splash;
import com.accusterltapp.adapter.PatientTableViewAdapter;
import com.accusterltapp.database.AppPreference;
import com.accusterltapp.model.Heleprec;
import com.accusterltapp.model.RegisterPatient;
import com.accusterltapp.model.SubTestDetails;
import com.accusterltapp.model.TestDetails;
import com.accusterltapp.model.TestListResponse;
import com.accusterltapp.service.ApiConstant;
import com.accusterltapp.service.NetworkCallContext;
import com.accusterltapp.table.TableCamp;
import com.accusterltapp.table.TablePackageList;
import com.accusterltapp.table.TablePackageTestDetail;
import com.accusterltapp.table.TablePatient;
import com.accusterltapp.table.TablePatientTest;
import com.base.fragment.BaseFragment;
import com.base.listener.RecyclerViewListener;
import com.base.model.CampDetails;
import com.base.model.CampList;
import com.base.utility.DateTimeUtils;
import com.base.utility.StringUtils;
import com.base.utility.ToastUtils;
import com.accusterltapp.R;

import java.util.ArrayList;
import java.util.HashMap;

import custom.CustomLablledSpinner;
import custom.CustomSpinnerAdapter;
import custom.CustomSpinnerItemListener;


public class PatientRegistrationFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, NetworkCallContext, RecyclerViewListener, SearchView.OnQueryTextListener, CustomSpinnerItemListener {
    private String[] typeid = {"ID", "Adhar Card ", "Voter Card", "Pan Card", "Other"};
    private String[] gender = {"Sex", "Male", "Female", "Other"};
    private String[] selectdiet = {"Select Diet", "Fasting", "PP", "Random"};


    private String[] acute_disease = {"select", "Fever", "Cough/Cold", "Vomiting/diarrhoea", "Pain during micturition/frequency", "Other"};
    private String[] chronic_disease = {"select", "Cronic", "Tuberculosis", "Diabetes", "Hypertesion", "Hypo/hyperthyrodism", "Previous history jaundice"};
    private String[] medicalhistory = {"History", "Acute", "Chronic"};
    private int disease;

    private custom.AvenirRomanEditText patientname, age, mobile, emaile, id_number, address, edtCampCode,bmi,bp;
    private Spinner sex, id, history, accurate, diet;
    private CustomLablledSpinner spnCampName, spn_package;
    private custom.AvenirRomanTextView labelid;
    private String patientID;
    private String selectedPatientId;
    private ArrayList<RegisterPatient> patientList, tempatientList;
    private String newDateStr;
    private TablePatient mPatient;
    private PatientTableViewAdapter patientTableDataAdapter;
    private SearchView mSearchView;
    private RecyclerView patientTableView;
    private String campCode = "", campName = "", packageName = "", OrgId = "";
    private ArrayList<CampDetails> camp_list;
    private ArrayList<TestDetails> mArrayList;
    private TableCamp tableCamp;
    private TablePackageList packageList;
    private RegisterPatient selectedPatient;
    private ArrayAdapter<String> adapterSex, typeId, dietAdapter, accurateAdapter, historyAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = inflater.inflate(R.layout.fragment_patient_registration, container, false);
        tableCamp = new TableCamp(getActivity());
        packageList = new TablePackageList(getActivity());
        view.setFocusableInTouchMode(true);
        view.requestFocus();

        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                ;
                // Log.i(tag, "keyCode: " + keyCode);
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                    Log.e("listener work", "onKey Back listener is working!!!");
                    if (AppPreference.getBoolean(getActivity(), AppPreference.TEST_TABLE_UPDATE)) {
                        mPatient = new TablePatient(getActivity());
                        if (!mPatient.isTableEmpty()) {
                            patientList.clear();
                            if (tempatientList != null && !tempatientList.isEmpty())
                                tempatientList.clear();
                            // tempatientList=mPatient.getallPatient(tempatientList)
                            Log.e("update", campCode);
                            tempatientList = mPatient.getallPatientByCamp(tempatientList, campCode);
                            patientList = mPatient.getallPatientByCamp(patientList, campCode);
                            patientTableDataAdapter = new PatientTableViewAdapter(PatientRegistrationFragment.this.getContext(), patientList, PatientRegistrationFragment.this);
                            patientTableView.setAdapter(patientTableDataAdapter);
                        }
                    }
                    // getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    // Showing Alert Message
                    // if(Heleprec.headerchange.size()>0)
                    // savedata();
                    //    alertDialog.show();

                    Log.e("size ", Heleprec.headerchange.size() + "");
                    //return rdat;
                }
                return false;
            }
        });

        patientname = view.findViewById(R.id.patientname);
        bmi=view.findViewById(R.id.bmi);
        bp=view.findViewById(R.id.bp);
        age = view.findViewById(R.id.age);
        mobile = view.findViewById(R.id.mobile);
        emaile = view.findViewById(R.id.emaile);
        id_number = view.findViewById(R.id.id_number);
        address = view.findViewById(R.id.address);
        labelid = view.findViewById(R.id.labelid);
        edtCampCode = view.findViewById(R.id.campCode);
        Button selecttest = view.findViewById(R.id.select_Test);

        sex = view.findViewById(R.id.sex);
        id = view.findViewById(R.id.id);
        history = view.findViewById(R.id.history);
        accurate = view.findViewById(R.id.accurate);
        diet = view.findViewById(R.id.diet);
        spnCampName = view.findViewById(R.id.spnCampName);
        spnCampName.hideHint(true);
        spnCampName.setClickListener(this);

        spn_package = view.findViewById(R.id.spn_package);
        spn_package.hideHint(true);
        spn_package.setClickListener(this);

        mSearchView = view.findViewById(R.id.sp_search);
        mSearchView.setQueryHint("Name, Id");
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setOnClickListener(this);


        Button buttonAddPatient = view.findViewById(R.id.add);
//        buttonAddPatient.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.e("user click","click work");
//
//            }
//        });
        Button addNewPatient = view.findViewById(R.id.newPatient);
        patientTableView = view.findViewById(R.id.rv_patient_view);
        patientTableView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //get realm instance

        buttonAddPatient.setOnClickListener(this);
        addNewPatient.setOnClickListener(this);

        adapterSex = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, gender);
        sex.setAdapter(adapterSex);
        sex.setOnItemSelectedListener(this);


        typeId = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, typeid);
        id.setAdapter(typeId);
        id.setOnItemSelectedListener(this);

        dietAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, selectdiet);
        diet.setAdapter(dietAdapter);
        diet.setOnItemSelectedListener(this);


        accurateAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, acute_disease);
        accurate.setAdapter(accurateAdapter);
        accurate.setOnItemSelectedListener(this);

        historyAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, medicalhistory);
        history.setAdapter(historyAdapter);
        history.setOnItemSelectedListener(this);
        selecttest.setOnClickListener(this);

        patientList = new ArrayList<>();
        tempatientList = new ArrayList<>(0);
        camp_list = new ArrayList<>();
        mArrayList = new ArrayList<>();
        TestDetails details = new TestDetails();
        details.setPackage_name("Select");
        mArrayList.add(details);
//        getActivity().getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
//            @Override
//            public void onBackStackChanged() {
//                if (AppPreference.getBoolean(getActivity(), AppPreference.TEST_TABLE_UPDATE)) {
//                    setPatientId();
//                    patientTableDataAdapter.notifyDataSetChanged();
//                }
//                Toast.makeText(PatientRegistrationFragment.this.getContext(),"happen 2",Toast.LENGTH_LONG).show();
//
//                // Do your logic here. Probably want to remove the
//                // listener at this point too.
//            }
//        });

//        if (NetworkUtil.checkInternetConnection(getActivity())) {
//            HashMap<String, String> bodyMap = new HashMap<>();
//            bodyMap.put("userId", AppPreference.getString(getActivity(), AppPreference.USER_ID));
//            BaseNetworkRequest<CampList> baseNetworkRequest = new BaseNetworkRequest<>(getActivity(),
//                    ApiConstant.CAMP_LIST,
//                    this, bodyMap, true,
//                    CampList.class);
//
//            LabTechnicianApplication.getController(ApiConstant.BASE_URL).enqueueCall(baseNetworkRequest);
//
//        } else {
        setupRecycler(tableCamp.getCampList(camp_list));
//        }


//        if (NetworkUtil.checkInternetConnection(getActivity())) {
//            BaseNetworkRequest<TestListResponse> baseNetworkRequest = new BaseNetworkRequest<>(getActivity(),
//                    ApiConstant.PACKAGE_LIST,
//                    this, null, true,
//                    TestListResponse.class);
//
//            LabTechnicianApplication.getController(ApiConstant.BASE_URL).enqueueCall(baseNetworkRequest);
//        } else {
        setPackageList(packageList.getPackageList(mArrayList));
//        }

        setPatientId();

        newDateStr = DateTimeUtils.getCurrentDate(DateTimeUtils.OUTPUT_HYPHEN_DATE_DD_MM_YYYY);
        patientTableDataAdapter = new PatientTableViewAdapter(getActivity(), tempatientList, this);
        patientTableView.setAdapter(patientTableDataAdapter);


        return view;
    }

    private void setupRecycler(ArrayList<CampDetails> campList) {
        if (getActivity() != null && isAdded()) {
            CustomSpinnerAdapter campListAdapter = new CustomSpinnerAdapter(getContext(),
                    android.R.layout.simple_spinner_dropdown_item, campList);
            campListAdapter.setShowDefaultText(false);
            spnCampName.setAdapter(campListAdapter);
            spnCampName.setSelection(AppPreference.getInt(getActivity(), AppPreference.CAMP_POSITION));
        }
    }

    private void setPackageList(ArrayList<TestDetails> packageList) {
        if (getActivity() != null && isAdded()) {
            CustomSpinnerAdapter campListAdapter = new CustomSpinnerAdapter(getContext(),
                    android.R.layout.simple_spinner_dropdown_item, packageList);
            campListAdapter.setShowDefaultText(false);
            spn_package.setAdapter(campListAdapter);
        }
    }


    public void setPatientId() {
        try {
            mPatient = new TablePatient(getActivity());
            if (!mPatient.isTableEmpty()) {
                patientList.clear();
                if (tempatientList != null && !tempatientList.isEmpty())
                    tempatientList.clear();
                tempatientList = mPatient.getallPatientByCamp(tempatientList, campCode);
                patientList = mPatient.getallPatientByCamp(patientList, campCode);
                Log.d("size ", tempatientList.size() + "");

            }
            patientID = StringUtils.getSaltString() + AppPreference.getString(getActivity(), AppPreference.USER_ID);

        } catch (Exception e) {
        }
    }


    private void SetRealmPatientDetail() {
        Log.d("add user ", "add user ");
        RegisterPatient patient = new RegisterPatient();
        try {
            patient.setUserregistration_complete_name(patientname.getText().toString().trim());
            patient.setUserregistration_code(patientID);
            if (bp.getText().toString()!=null)
            patient.setUserregistration_bp(bp.getText().toString());
            if (bmi.getText().toString()!=null)
                patient.setUserregistration_bmi(bmi.getText().toString());
            patient.setUserregistration_mobile_number(mobile.getText().toString().trim());
            patient.setUserregistration_age(age.getText().toString().trim());
            patient.setUserregistration_gender_id(sex.getSelectedItemPosition() > 0 ?
                    String.valueOf(sex.getSelectedItemPosition() - 1) : "null");
            if (id.getSelectedItemPosition() > 0) {
                patient.setUserregistration_Id_type(id.getSelectedItem().toString().trim());
                patient.setUserregistration_Id_no(id_number.getText().toString().trim());
            }
            patient.setUserregistration_address_line_1(address.getText().toString().trim());
            if (diet.getSelectedItemPosition() > 0) {
                patient.setUserregistration_diet(diet.getSelectedItem().toString().trim());
            }
            patient.setUserregistration_email_address(emaile.getText().toString().trim());
            patient.setDate(newDateStr);
            patient.setUserregistration_created_time(DateTimeUtils.getCurrentDate());
            patient.setCampName(campName);
            patient.setUserregistration_org_id(OrgId);
            patient.setUserregistration_camp_code(campCode);
            if (history.getSelectedItemPosition() > 0) {
                patient.setUserregistration_history_type(history.getSelectedItem().toString().trim());
            }
            if (accurate.getSelectedItemPosition() > 0) {
                patient.setUserregistration_history_type_detail(accurate.getSelectedItem().toString().trim());
            }
            // Log.e("name of pack",packageName+"hi");
            patient.setUserregistration_Lt_id(AppPreference.getString(getActivity(), AppPreference.USER_ID));
            mPatient.insertSinglePatient(patient);

            TablePatientTest patientTest = new TablePatientTest(getActivity());
            TablePackageTestDetail packageList = new TablePackageTestDetail(getActivity());
            ArrayList<SubTestDetails> subTestDetails = new ArrayList<>();
            Log.e("name of pack", packageName + "hi");

            patientTest.insertSinglePatient(packageList.getAllSubTestList(subTestDetails, packageName), patientID, campCode,
                    mPatient.getLabelId(patientID));
            setPatientId();
            Refreshpage();
            patientTableDataAdapter.notifyDataSetChanged();
        } catch (NullPointerException e) {
            e.printStackTrace();
            ToastUtils.showShortToastMessage(getActivity(), getString(R.string.server_error));
        }
    }


    void Refreshpage() {
        mobile.setText("");
        age.setText("");
        id_number.setText("");
        address.setText("");
        labelid.setText("");
        emaile.setText("");
        patientname.setText("");
        sex.setSelection(0);
        id.setSelection(0);
        diet.setSelection(0);
        history.setSelection(0);
        accurate.setSelection(0);
        spn_package.setSelection(0);
    }


    public void updatePatient() {
        Log.d("update  user ", "update  user ");
        try {
            if (selectedPatient != null) {
                selectedPatient.setUserregistration_complete_name(patientname.getText().toString().trim());
                selectedPatient.setUserregistration_mobile_number(mobile.getText().toString().trim());
                selectedPatient.setUserregistration_age(age.getText().toString().trim());
                selectedPatient.setUserregistration_bmi(bmi.getText().toString());
                selectedPatient.setUserregistration_bp(bp.getText().toString());
                selectedPatient.setUserregistration_gender_id(sex.getSelectedItemPosition() > 0 ?
                        String.valueOf(sex.getSelectedItemPosition() - 1) : "null");
                if (id.getSelectedItemPosition() > 0) {
                    selectedPatient.setUserregistration_Id_type(id.getSelectedItem().toString().trim());
                    selectedPatient.setUserregistration_Id_no(id_number.getText().toString().trim());
                }
                selectedPatient.setUserregistration_address_line_1(address.getText().toString().trim());
                if (diet.getSelectedItemPosition() > 0) {
                    selectedPatient.setUserregistration_diet(diet.getSelectedItem().toString().trim());
                }
                selectedPatient.setUserregistration_email_address(emaile.getText().toString().trim());
                selectedPatient.setDate(newDateStr);
                selectedPatient.setUserregistration_created_time(DateTimeUtils.getCurrentDate());
                selectedPatient.setCampName(campName);
                selectedPatient.setUserregistration_org_id(OrgId);
                selectedPatient.setUserregistration_camp_code(campCode);
                if (history.getSelectedItemPosition() > 0) {
                    selectedPatient.setUserregistration_history_type(history.getSelectedItem().toString().trim());
                }
                if (accurate.getSelectedItemPosition() > 0) {
                    selectedPatient.setUserregistration_history_type_detail(accurate.getSelectedItem().toString().trim());
                }
                selectedPatient.setUserregistration_Lt_id(AppPreference.getString(getActivity(), AppPreference.USER_ID));

                //  mPatient.upadteSinglePatient(selectedPatient);
                TablePatientTest patientTest = new TablePatientTest(getActivity());
                TablePackageTestDetail packageList = new TablePackageTestDetail(getActivity());
                ArrayList<SubTestDetails> subTestDetails = new ArrayList<>();
                Log.e("name of pack", packageName + "hi");

                patientTest.insertSinglePatient(packageList.getAllSubTestList(subTestDetails, packageName), selectedPatient.getUserregistration_code(), selectedPatient.getUserregistration_camp_code(),
                        selectedPatient.getpLabelId());

                setPatientId();
                Refreshpage();
                patientTableDataAdapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Validation
    private boolean Validation() {
        if (TextUtils.isEmpty(patientname.getText().toString())) {
            ToastUtils.showShortToastMessage(getContext(), "Please enter the patient name");
            return false;
        } else if (mobile.getText().toString().isEmpty()) {
            ToastUtils.showShortToastMessage(getContext(), "Please enter contact number");
            return false;
        }
//        else if (!TextUtils.isEmpty(labelid.getText())) {
//            ToastUtils.showShortToastMessage(getContext(), "Please clear old patient details");
//            return false;
//        }
//        else if (age.getText().toString().isEmpty()) {
//            ToastUtils.showShortToastMessage(getContext(), "Please enter age");
//            return false;
//        } else if (TextUtils.isEmpty(emaile.getText().toString()) || !StringUtils.isValidEmail(emaile.getText().toString())) {
//            ToastUtils.showShortToastMessage(getContext(), "Please enter valid mail id");
//            return false;
//        } else if (sex.getSelectedItem().toString().isEmpty() || sex.getSelectedItem().toString().equalsIgnoreCase("sex")) {
//            ToastUtils.showShortToastMessage(getContext(), "Please select Gender");
//            return false;
//        } else if (id.getSelectedItem().toString().isEmpty() || id.getSelectedItem().toString().equalsIgnoreCase("ID")) {
//            ToastUtils.showShortToastMessage(getContext(), "Please select IDProof");
//            return false;
//        } else if (id_number.getText().toString().isEmpty() || id.getSelectedItem().toString().equalsIgnoreCase("ID")) {
//            ToastUtils.showShortToastMessage(getContext(), "Please enter the id number");
//            return false;
//        } else if (address.getText().toString().isEmpty()) {
//            ToastUtils.showShortToastMessage(getContext(), "Please enter the camp_address");
//            return false;
//        } else if (history.getSelectedItem().toString().isEmpty() || history.getSelectedItem().toString().equalsIgnoreCase("History")) {
//            ToastUtils.showShortToastMessage(getContext(), "Please select History");
//            return false;
//        } else if (disease <= 0) {
//            ToastUtils.showShortToastMessage(getContext(), "Please select disease");
//            return false;
//        } else if (diet.getSelectedItem().toString().isEmpty() || diet.getSelectedItem().toString().equalsIgnoreCase("Select Diet")) {
//            ToastUtils.showShortToastMessage(getContext(), "Please select Diet");
//            return false;
//        } else if (TextUtils.isEmpty(campCode)) {
//            ToastUtils.showShortToastMessage(getContext(), "Please select Camp");
//            return false;
//        }
        return true;
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        if (adapterView.getId() == R.id.history) {
            if (position == 2) {
                accurateAdapter = new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_spinner_dropdown_item, chronic_disease);
                accurate.setAdapter(accurateAdapter);
            } else {
                accurateAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, acute_disease);
                accurate.setAdapter(accurateAdapter);
            }
        } else if (adapterView.getId() == R.id.accurate) {
            disease = position;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void handleResponse(Object response, String type) {
        if (type.equalsIgnoreCase(ApiConstant.CAMP_LIST)) {
            CampList campList = (CampList) response;
            if (campList != null && campList.getStatus().equalsIgnoreCase(ApiConstant.SUCCESS)
                    && campList.getCamplist() != null && !campList.getCamplist().isEmpty()) {
                tableCamp.insertCampListFormServer(campList.getCamplist());
            }
            setupRecycler(tableCamp.getCampList(camp_list));


        } else if (type.equalsIgnoreCase(ApiConstant.PACKAGE_LIST)) {
            TablePackageList packageList = new TablePackageList(getActivity());
            TestListResponse testListResponse = (TestListResponse) response;
            if (testListResponse != null && testListResponse.getStatus().equalsIgnoreCase(ApiConstant.SUCCESS)
                    && testListResponse.getPackage_list() != null && !testListResponse.getPackage_list().isEmpty()) {
                packageList.insertPackageList(testListResponse.getPackage_list());
            }
            setPackageList(packageList.getPackageList(mArrayList));
        }
    }

    @Override
    public void handleServerError(Object response, String type) {
        if (type.equalsIgnoreCase(ApiConstant.CAMP_LIST)) {
            setupRecycler(tableCamp.getCampList(camp_list));
        } else if (type.equalsIgnoreCase(ApiConstant.PACKAGE_LIST)) {
            setPackageList(packageList.getPackageList(mArrayList));
        }
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.select_Test) {
            if (!TextUtils.isEmpty(selectedPatientId)) {
                Intent intent = new Intent(getActivity(), EditTestActivity.class);
                intent.putExtra("plabelId", selectedPatientId);
                intent.putExtra("campCode", campCode);
                intent.putExtra("labelId", labelid.getText().toString());
                startActivity(intent);
            } else {
                ToastUtils.showShortToastMessage(getActivity(), "Select Patient from list");
            }
        } else if (view.getId() == R.id.add) {
            if (Validation()) {
                if (!TextUtils.isEmpty(labelid.getText())) {

                    if (tableCamp.getCampList(camp_list) != null)
                        if (tableCamp.getCampList(camp_list).size() > 0)
                            updatePatient();
                        else {
                            final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                            alertDialog.setCancelable(true);
                            alertDialog.setMessage("Sorry Camp not selected");
                            alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                   alertDialog.dismiss();

                                }
                            });
                            alertDialog.show();
                        }
                } else {
                    if (tableCamp.getCampList(camp_list) != null)
                        if (tableCamp.getCampList(camp_list).size() > 0)
                    SetRealmPatientDetail();
                        else {
                            final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                            alertDialog.setCancelable(true);
                            alertDialog.setMessage("Sorry Camp not selected");
                            alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    alertDialog.dismiss();

                                }
                            });
                            alertDialog.show();
                        }
                }
            }
        } else if (view.getId() == R.id.newPatient) {
            Refreshpage();
        } else if (view.getId() == R.id.sp_search) {
            mSearchView.setIconified(false);
        }
    }
    @Override
    public void onItemClickListener(View view, int position, HashMap<String, String> hashMap) {
        selectedPatient = patientList.get(position);
        if (hashMap.get("action").equalsIgnoreCase("show")) {
            PatientPreview patient_preview = new PatientPreview();
            Bundle args = new Bundle();
            args.putString("camp_name", campName);
            args.putString("patient_id", patientList.get(position).getUserregistration_code());
            args.putString("label_id", patientList.get(position).getpLabelId());
            args.putString("process_date", patientList.get(position).getDate());
            args.putString("time", patientList.get(position).getUserregistration_created_time());
            args.putParcelable("patientDetail", patientList.get(position));
            patient_preview.setArguments(args);
            Heleprec.current_camp_name = campName;
            //Inflate the fragment
            addFragment(patient_preview, PatientPreview.class.getSimpleName());
        } else if (hashMap.get("action").equalsIgnoreCase("set")) {
            setPatientDetails(position);
        }
    }

    public void setPatientDetails(int position) {

        campCode = patientList.get(position).getUserregistration_camp_code();
        selectedPatientId = patientList.get(position).getUserregistration_code();
        mobile.setText(patientList.get(position).getUserregistration_mobile_number());
        age.setText(patientList.get(position).getUserregistration_age());
        id_number.setText(patientList.get(position).getUserregistration_Id_no());
        address.setText(patientList.get(position).getUserregistration_address_line_1());
        labelid.setText(patientList.get(position).getpLabelId());
        emaile.setText(patientList.get(position).getUserregistration_email_address());
        patientname.setText(patientList.get(position).getUserregistration_complete_name());
        if (!TextUtils.isEmpty(patientList.get(position).getUserregistration_gender_id())) {
            try {
                sex.setSelection(Integer.parseInt(patientList.get(position).getUserregistration_gender_id()) + 1);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        if (!TextUtils.isEmpty(patientList.get(position).getUserregistration_diet())) {
            diet.setSelection(dietAdapter.getPosition(patientList.get(position).getUserregistration_diet()));
        }

        if (!TextUtils.isEmpty(patientList.get(position).getUserregistration_diet())) {
            id.setSelection(typeId.getPosition(patientList.get(position).getUserregistration_diet()));
        }

        if (!TextUtils.isEmpty(patientList.get(position).getUserregistration_history_type())) {
            history.setSelection(historyAdapter.getPosition(patientList.get(position).getUserregistration_history_type()));
        }

        if (!TextUtils.isEmpty(patientList.get(position).getUserregistration_history_type_detail())) {
            accurate.setSelection(accurateAdapter.getPosition(patientList.get(position).getUserregistration_history_type_detail()));
        }
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String query = newText.toLowerCase().trim();
        final ArrayList<RegisterPatient> filteredModelList = new ArrayList<>();
        if (patientList != null && !patientList.isEmpty()) {
            for (RegisterPatient details : patientList) {
                String name = details.getUserregistration_complete_name().toLowerCase();
                String id = String.valueOf(details.getUserregistration_code()).toLowerCase();
                String emailId = details.getUserregistration_email_address().toLowerCase();
                try {
                    if (name.contains(query) || id.contains(query) || emailId.contains(query)) {
                        filteredModelList.add(details);
                    }
                } catch (Exception e) {
                    if (name.contains(query) || id.contains(query)) {
                        filteredModelList.add(details);
                    }
                }


            }
            patientTableDataAdapter.animateTo(filteredModelList);
            patientTableView.scrollToPosition(0);

        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.e("onresume callled", "resujme");
        if (AppPreference.getBoolean(getActivity(), AppPreference.TEST_TABLE_UPDATE)) {
            mPatient = new TablePatient(getActivity());
            // if (!mPatient.isTableEmpty()) {
            patientList.clear();
            //    if (tempatientList != null && !tempatientList.isEmpty())
            tempatientList.clear();
            // tempatientList=mPatient.getallPatient(tempatientList)
            Log.e("update", campCode);
            tempatientList = mPatient.getallPatientByCamp(tempatientList, campCode);
            patientList = mPatient.getallPatientByCamp(patientList, campCode);
            patientTableDataAdapter.notifyDataSetChanged();

            //  }
        }
        //}
        // Toast.makeText(this.getContext(),"happen",Toast.LENGTH_LONG).show();

    }


    @Override
    public void onItemSelection(Spinner spinnerView, int position) {
        if (spinnerView == spnCampName.spinner) {
            if (position >= 0) {
                campCode = camp_list.get(position).getCamp_code();
                campName = camp_list.get(position).getCampName();
//                Log.d("camp code",campCode);
                OrgId = camp_list.get(position).getCamp_organization_id();
                AppPreference.setInt(getActivity(), AppPreference.CAMP_POSITION, position);
                mPatient = new TablePatient(getActivity());
                if (!mPatient.isTableEmpty()) {
                    patientList.clear();
                    if (tempatientList != null && !tempatientList.isEmpty())
                        tempatientList.clear();
                    // tempatientList=mPatient.getallPatient(tempatientList)
                    tempatientList = mPatient.getallPatientByCamp(tempatientList, campCode);
                    patientList = mPatient.getallPatientByCamp(patientList, campCode);
                    patientTableDataAdapter.notifyDataSetChanged();
                }
            } else {
                campCode = "";
            }
        } else {
            if (position > 0) {
                packageName = mArrayList.get(position).getPackage_name();
                Log.e("the package name ","name");
            } else {
                packageName = "";
            }
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (Splash.btSocket == null || !Splash.btSocket.isConnected()) {
            inflater.inflate(R.menu.bluetooth_menu, menu);
        }
        if (!Heleprec.con)
            inflater.inflate(R.menu.bluetooth_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(getActivity(), DeviceListActivity.class));
        tableCamp.getCampList(camp_list);
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {

        Log.e("onresu callled my work", "resujme");


        Log.e("listener work", "onKey Back listener is working!!!");
        if (AppPreference.getBoolean(getActivity(), AppPreference.TEST_TABLE_UPDATE)) {
            mPatient = new TablePatient(getContext());
            if (!mPatient.isTableEmpty()) {
                patientList.clear();
                if (tempatientList != null && !tempatientList.isEmpty())
                    tempatientList.clear();
                // tempatientList=mPatient.getallPatient(tempatientList)
                Log.e("update", campCode);
                tempatientList = mPatient.getallPatientByCamp(tempatientList, campCode);
                patientList = mPatient.getallPatientByCamp(patientList, campCode);
                patientTableDataAdapter = new PatientTableViewAdapter(PatientRegistrationFragment.this.getContext(), patientList, PatientRegistrationFragment.this);
                patientTableView.setAdapter(patientTableDataAdapter);
            }


        }
    }
}

