package com.accusterltapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.accusterltapp.R;


public class CampPreview extends Fragment {


    public CampPreview() {
        // Required empty public constructor
    }
    public TextView orgname,campnametxt,address,startdate,enddate,starttime,endtime,venue,productdetails,pathname,degree;
    String orgname2="",campname2="",campAddress="",campstartdate="",campenddate="",campstarttime="",campendtime="",campvenue="",campprodetail="",camppathname="",campdegree="";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_camp_preview, container, false);
        orgname = view.findViewById(R.id.orgname);
        campnametxt = view.findViewById(R.id.campname);
        address = view.findViewById(R.id.address);
        startdate = view.findViewById(R.id.startdate);
        enddate = view.findViewById(R.id.enddate);
        starttime = view.findViewById(R.id.starttime);
        endtime = view.findViewById(R.id.endtime);
//        venue = view.findViewById(R.id.venue);
        productdetails = view.findViewById(R.id.product_details);
        pathname = view.findViewById(R.id.pathname);
        degree = view.findViewById(R.id.degree);

        // Set patientList
        setData();
        return view;
    }

    public void setData(){

        orgname2 = getArguments().getString("OrgName");

        campname2 = getArguments().getString("CampName");

        System.out.println("campname2"+campname2);

        campnametxt.setText(campname2);

        campAddress = getArguments().getString("CampAddress");
        address.setText(campAddress);

        campstartdate = getArguments().getString("CampStartDate");
        startdate.setText(campstartdate);

        campenddate = getArguments().getString("CampEndDate");
        enddate.setText(campenddate);

        campstarttime = getArguments().getString("CampStartTime");
        starttime.setText(campstarttime);

        campendtime = getArguments().getString("CampEndTime");
        endtime.setText(campendtime);

        campvenue = getArguments().getString("CampVenue");
        venue.setText(campvenue);

        campprodetail = getArguments().getString("CampProductDetail");

        camppathname = getArguments().getString("CampPathName");
        pathname.setText(camppathname);

        campdegree = getArguments().getString("CampDegree");
        degree.setText(campdegree);
    }

}
