package com.accusterltapp.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.accusterltapp.adapter.PatientAdapter;
import com.accusterltapp.model.Patient;
import com.accusterltapp.R;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;


public class PatientDetailNew extends Fragment implements View.OnClickListener{


    public PatientDetailNew() {
        // Required empty public constructor
    }
    private DatePickerDialog essueDatePickerDialog;

    private DatePickerDialog essueDatePickerdob;
    private java.text.SimpleDateFormat dateFormatter;
    public static final int REQCODE = 100;
    private final int CAMERA_REQUEST = 20;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    InputMethodManager imm;

    ArrayList<Patient> contact_data ;
    // Contact_Adapter cAdapter;
    RecyclerView recyclerView;
    PatientAdapter mAdapter;
    Spinner org_name;
    Spinner sp_gender,sp_id,sp_diet,sp_acute_disease,sp_chronic_disease,sp_fever,sp_patient,sp_rapidtest;
    String[] typeid={"Select ","Adhar Card ","Voter Card" ,"Pan Card","Other"};
    String[] package1={"Package","Package1","Package2","Package3"};
    String[] gender={"Gender","Male","Female","Other"};
    String[] diet={"Select","Fasting","PP","Random"};
    String[] fever={"fever"};
    String[] patient={"By Mobile","By Name","By Token"};
    String[] acute_disease={"Acute/Cronic","Fever","Cough/cool","Vomiting/diarrhoea","Pain during micturition/frequency","Other"};
    String[] chronic_disease={"Acute/Cronic ","Tuberculosis","Diabetes","Hypertesion","Hypo/hyperthyrodism","Previous history jaundice"};
    TextView tv_id_no,tv_medicalhistory;
    ImageView bt_plus;
    Button bt_addtest;
    String[] medicalhistory={"M.History","A.disease","C.disease"};
    String[] organisation={"Organization name","African Union ","African Risk Capacity ","Benelux Union","Council of Europe ","Other"};
    EditText et_patient_id,et_mobile_no,et_age,et_register_date,et_dob,sp_id_number,et_address,et_label_id,et_email;
    String patientId1,patientRegisterDate,patientContact,patientDob,patientAge,patientGender,patientIdType,patientIdNumber,
            patientAddress,patientDiet,patientIdLabel,patientEmail,patieMedicalHistory,patientMedicalType;
    Random myRandom;
    Button bt_update;
    Context cantext;
    TextView tv_patientid,et_gender,ed_dob,mhistory,diseaseac,txt_diet,package_name;
    ImageView bt_refresh;
    String email;
    TextView txt_orgname;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        View view= inflater.inflate(R.layout.fragment_patient_detail_new, container, false);
        et_patient_id= view.findViewById(R.id.et_patient_id);
        et_gender = view.findViewById(R.id.et_gender);
        txt_orgname = view.findViewById(R.id.txt_orgname);
        ed_dob = view.findViewById(R.id.ed_dob);
        mhistory = view.findViewById(R.id.mhistory);
        diseaseac = view.findViewById(R.id.diseaseac);
        txt_diet = view.findViewById(R.id.txt_diet);
        et_mobile_no= view.findViewById(R.id.et_mobile_no);
        et_dob= view.findViewById(R.id.et_dob);
        et_age= view.findViewById(R.id.et_age);
        sp_gender= view.findViewById(R.id.sp_gender);
        org_name= view.findViewById(R.id.org_name);
        sp_rapidtest= view.findViewById(R.id.sp_package);
        sp_id= view.findViewById(R.id.et_id);
        sp_id_number= view.findViewById(R.id.sp_id_number);
        et_address= view.findViewById(R.id.et_address);
        sp_diet= view.findViewById(R.id.sp_diet);
        et_label_id= view.findViewById(R.id.et_label_id);
        et_email= view.findViewById(R.id.et_email);
        sp_acute_disease= view.findViewById(R.id.sp_acute_disease);
        sp_chronic_disease= view.findViewById(R.id.sp_chronic_disease);
        bt_plus= view.findViewById(R.id.bt_plus);
        tv_patientid= view.findViewById(R.id.tv_patientid);
        tv_id_no= view.findViewById(R.id.tv_id_no);
        bt_refresh= view.findViewById(R.id.bt_refresh);
        package_name= view.findViewById(R.id.package_name);
        bt_addtest= view.findViewById(R.id.bt_addtest);
        bt_update= view.findViewById(R.id.bt_update);

        //get realm instance

//        Number currentIdNum = realm.where(RegisterPatient.class).maximumInt("id");
//        System.out.println("currentIdNum"+currentIdNum);
        int nextId;
        // nextId = 3;
//        if(currentIdNum == null) {
//            nextId = 1;
//            et_label_id.setText( String.valueOf(nextId));
//            et_label_id.setEnabled(false);
//        } else {
//            nextId = currentIdNum.intValue() + 1;
//            String.valueOf(nextId);
//            et_label_id.setText( String.valueOf(nextId));
//        }

        bt_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Validation()) {
                    SetRealmPatientDetail();
                }
            }
        });

        bt_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh_data();
            }
        });


        try {
            contact_data = new ArrayList<>();
            recyclerView = view.findViewById(R.id.recyclerViewpatient);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            ////////////get Method call |||||||||||||||||||\
//            contact_data = mDataBaseHelper.getAllPatient();
            System.out.println("dataregister"+contact_data);
            mAdapter = new PatientAdapter(getContext(), contact_data);
            recyclerView.setAdapter(mAdapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            setupRecycler();

//            setRealmAdapter(RealmController.with(getActivity()).getPatients());

        }

        catch (Exception e){
            e.fillInStackTrace();
        }

        try {


            String result = "";
            myRandom = new Random();

            for (int i = 0; i < 1; i++) {
                result += String.valueOf(myRandom.nextInt()) + "\n";
            }

            tv_patientid.setText(result);

        }
        catch (Exception e){
            e.fillInStackTrace();
        }

        dateFormatter = new java.text.SimpleDateFormat("dd/MM/yyyy", Locale.US);
        java.util.Calendar newCalendar = java.util.Calendar.getInstance();

        et_dob.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                essueDatePickerdob.show();

                return false;
            }
        });

        essueDatePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


                java.util.Calendar newDate = java.util.Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);


            }

        },newCalendar.get(java.util.Calendar.YEAR), newCalendar.get(java.util.Calendar.MONTH), newCalendar.get(java.util.Calendar.DAY_OF_MONTH));

        essueDatePickerdob = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


                java.util.Calendar newDate = java.util.Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                ed_dob.setText(dateFormatter.format(newDate.getTime()));

            }

        },newCalendar.get(java.util.Calendar.YEAR), newCalendar.get(java.util.Calendar.MONTH), newCalendar.get(java.util.Calendar.DAY_OF_MONTH));

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, package1);
        sp_rapidtest.setAdapter(adapter);

        sp_rapidtest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String items=sp_rapidtest.getSelectedItem().toString().trim();
                package_name.setText(items);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final ArrayAdapter<String> adaptero = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, organisation);
        org_name.setAdapter(adaptero);

        org_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String items=org_name.getSelectedItem().toString().trim();
                txt_orgname.setText(items);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, gender);
        sp_gender.setAdapter(adapter1);

        sp_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String items=sp_gender.getSelectedItem().toString().trim();

                if (items.equalsIgnoreCase("Male")){

                    et_gender.setText("Male");

                }
                else if (items.equalsIgnoreCase("Female")){
                    et_gender.setText("Female");

                }
                else if (items.equalsIgnoreCase("Other")){
                    et_gender.setText("Other");

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, typeid);
        sp_id.setAdapter(adapter2);

        final ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, diet);
        sp_diet.setAdapter(adapter3);

        sp_diet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String items=sp_diet.getSelectedItem().toString().trim();

                if (items.equalsIgnoreCase("Fasting")){

                    txt_diet.setText("Fasting");

                }
                else if (items.equalsIgnoreCase("PP")){
                    txt_diet.setText("PP");

                }
                else if (items.equalsIgnoreCase("Random")){
                    txt_diet.setText("Random");

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, acute_disease);
        sp_chronic_disease.setAdapter(adapter4);

        final ArrayAdapter<String> adapter8= new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, medicalhistory);
        sp_acute_disease.setAdapter(adapter8);


        sp_acute_disease.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String items=sp_acute_disease.getSelectedItem().toString().trim();
                mhistory.setText(items);

                if (items.equalsIgnoreCase("Acute disease")){
                    mhistory.setText("Acute disease");
                    diseaseac.setText("Acute disease");

                    final ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, acute_disease);
                    sp_chronic_disease.setAdapter(adapter4);

                }
                else if (items.equalsIgnoreCase("Chronic disease")){
                    mhistory.setText("Chronic disease");
                    diseaseac.setText("Chronic disease");
                    final ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, chronic_disease);
                    sp_chronic_disease.setAdapter(adapter5);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        sp_chronic_disease.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String items=sp_chronic_disease.getSelectedItem().toString().trim();
                diseaseac.setText(items);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp_id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String items=sp_id.getSelectedItem().toString().trim();

                if (items.equalsIgnoreCase("Adhar Card")){

                    tv_id_no.setText("Adhar.CN");

                }
                else if (items.equalsIgnoreCase("Voter Card")){
                    tv_id_no.setText("Voter.CN");

                }
                else if (items.equalsIgnoreCase("Pan Card")){
                    tv_id_no.setText("Pan.CN");

                }
                else if (items.equalsIgnoreCase("Other")){
                    tv_id_no.setText("Id Number");

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });





        return view;
    }

    //refresh patientList
    public void  refresh_data(){
        et_gender.setText("Gender");
        ed_dob.setText("DOB");
        tv_id_no.setText("ID");
        mhistory.setText("M.History");
        diseaseac.setText("Acute/Cronic");
        txt_diet.setText("Diet");
        package_name.setText("Package");
        et_patient_id.setText("");
        et_age.setText("");
        et_mobile_no.setText("");
        et_address.setText("");
        et_email.setText("");
        sp_id_number.setText("");
        et_label_id.setText("");

    }

    private void  selectImage(){

        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Add Photo!");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo"))

                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMERA_REQUEST);
                }

                else if (options[item].equals("Choose from Gallery"))

                {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, REQCODE);
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();

                }

            }

        });

        builder.show();


    }


    private void SetRealmPatientDetail(){

//        RegisterPatient patient = new RegisterPatient();
//
//
//        Number currentIdNum = realm.where(RegisterPatient.class).maximumInt("id");
//        System.out.println("currentIdNum"+currentIdNum);
//        int nextId;
//        // nextId = 3;
//        if(currentIdNum == null) {
//            nextId = 1;
//        } else {
//            nextId = currentIdNum.intValue() + 1;
//        }
//        patient.setId(nextId);
//        try {
//            patient.setpName(et_patient_id.getText().toString().trim());
//            patient.setUserregistration_mobile_number(et_mobile_no.getText().toString().trim());
//            patient.setUserregistration_age(et_age.getText().toString().trim());
//            patient.setUserregistration_gender_id(sp_gender.getSelectedItem().toString().trim());
//            patient.setUserregistration_Id_type(sp_id.getSelectedItem().toString().trim());
//            patient.setUserregistration_address_line_1(et_address.getText().toString().trim());
//            patient.setUserregistration_diet(sp_diet.getSelectedItem().toString().trim());
//            patient.setpLabelId(et_label_id.getText().toString().trim());
//            patient.setUserregistration_email_address(et_email.getText().toString().trim());
//            patient.setpPatientId(tv_patientid.getText().toString().trim());
//            patient.setUserregistration_history_type(sp_acute_disease.getSelectedItem().toString().trim());
//            patient.setUserregistration_history_type_detail(sp_chronic_disease.getSelectedItem().toString().trim());
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            realm.beginTransaction();
//            realm.copyToRealm(patient);
//            realm.commitTransaction();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        setupRecycler();
//
//        setRealmAdapter(RealmController.with(getActivity()).getPatients());
//
//        refreshPage();

    }

    private void setupRecycler() {
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager since the cards are vertically scrollable
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        // create an empty adapter and ButtonAddPatient it to the recycler view
        mAdapter = new PatientAdapter(getActivity());
        recyclerView.setAdapter(mAdapter);
    }
//
//    public void setRealmAdapter(RealmResults<RegisterPatient> patients) {
//
////        RealmPatientsAdapter realmAdapter = new RealmPatientsAdapter(getActivity(), patients, true);
////        // Set the patientList and tell the RecyclerView to draw
////        mAdapter.setRealmAdapter(realmAdapter);
////        mAdapter.notifyDataSetChanged();
//    }

    void Refreshpage(){

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, package1);
        sp_rapidtest.setAdapter(adapter);


        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, gender);
        sp_gender.setAdapter(adapter1);

        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, typeid);
        sp_id.setAdapter(adapter2);

        final ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, diet);
        sp_diet.setAdapter(adapter3);

        final ArrayAdapter<String> adapter8= new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, medicalhistory);
        sp_acute_disease.setAdapter(adapter8);

        et_patient_id.setText("");
        et_mobile_no.setText("");
        ed_dob.setText("");
        et_age.setText("");
        sp_id_number.setText("");
        et_address.setText("");
        et_label_id.setText("");
        et_email.setText("");

    }

    //Validation
    private boolean Validation() {
        email=et_email.getText().toString().trim();

        if(et_patient_id.getText().toString().isEmpty()){
            et_patient_id.setError("Please enter the patient name");
            return false;
        }

        else if (et_mobile_no.getText().toString().isEmpty()) {
            et_mobile_no.setError("Please enter contact number");
            return false;
        }

        else  if (ed_dob.getText().toString().isEmpty()) {

            Toast.makeText(getContext(), "Please select DOB", Toast.LENGTH_SHORT).show();
            return false;
        }
        else   if (et_age.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Please enter age", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (sp_gender.getSelectedItem().toString().isEmpty()) {
            Toast.makeText(getContext(), "Please select Gender", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (sp_id.getSelectedItem().toString().isEmpty()) {
            Toast.makeText(getContext(), "Please select IDProof", Toast.LENGTH_SHORT).show();
            return false;
        }
        else  if (sp_id_number.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Please enter the id number", Toast.LENGTH_SHORT).show();
            return false;
        }
        else  if (et_address.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Please enter the camp_address", Toast.LENGTH_SHORT).show();
            return false;
        }
        else  if (et_email.getText().toString().isEmpty()) {
            et_email.setError("Please enter email id");
            return false;
        }

        else if (sp_rapidtest.getSelectedItem().toString().isEmpty()) {
            Toast.makeText(getContext(), "Please select rapid test", Toast.LENGTH_SHORT).show();
            return false;
        }

        else  if (sp_diet.getSelectedItem().toString().isEmpty()) {
            Toast.makeText(getContext(), "Please select diet", Toast.LENGTH_SHORT).show();
            return false;
        }

        else  if (sp_acute_disease.getSelectedItem().toString().isEmpty()) {
            Toast.makeText(getContext(), "Please select disease", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    @Override
    public void onClick(View view) {

    }
}
