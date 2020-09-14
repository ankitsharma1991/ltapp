package com.base.utility;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.SyncStateContract;
import android.support.v4.content.FileProvider;
import android.text.Html;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.accusterltapp.model.WidalTest;
import com.accusterltapp.table.TableWidalTest;
import com.google.gson.Gson;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.accusterltapp.R;
import com.accusterltapp.fragment.HelpInfo;
import com.accusterltapp.model.CampDetails1;
import com.accusterltapp.model.Heleprec;
import com.accusterltapp.model.RegisterPatient;
import com.accusterltapp.model.SubTestDetails;
import com.accusterltapp.service.ApiConstant;
import com.accusterltapp.service.PageNumeration;
import com.accusterltapp.table.TablePatientTest;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by pbadmin on 8/12/17.
 * tocreate Pdf Report
 */

public class PdfGenerator extends  PdfPageEventHelper {
    private BaseFont bfBold;
    private File pdfFile;
    Document document;
    boolean widal_flag=false;
    boolean footer = true;
    PdfPTable pdfPTable;
    PdfWriter docWriter;
    PdfPTable pritable;
    int total_page, current_page = 1;
    HashMap<String, SubTestDetails> map;
    Bitmap bmp2;
    HashMap<String, ArrayList<String>> pri_map;
    HashMap<String, ArrayList<String>> inti_map;
    CampDetails1 campdetails;
    int header_vari = 0;
    boolean pri_inter = false;
    RegisterPatient registerPatient;
    HashMap<String, String> inter_prlist = new HashMap<>();
    ArrayList<SubTestDetails> mSubTestDetails1;
    private String filename = ".pdf";
    private String filepath = "ReportPdf";
    private String labName = "Accuster Technologies Pvt. Ltd.", labAddressLine1 = "Plot No 41 Sec"
            + " 8 ",
            labAddressLine2 = "IMT Manesar, Gurgaon (HR)",
            labLogo, drName, drQualification,
            mediaName, patientName, patientDoc,
            patientMobile, patientGender = "N/A", patientDOB, patientAddress,
            patientVillage, patientCity, patientPostalCode, patientEmail, appointmentID;
    private String patientID, genderPos;
    private Context mContext;
    ArrayList<String> pri = new ArrayList<>();
    ArrayList<String> inti = new ArrayList<>();
    public PdfGenerator(Context context) {
        mContext = context;
    }
    public void generatePDF(RegisterPatient registerPatients) {
        float height, width;

        //create a new document
        this.registerPatient=registerPatients;
        document = new Document(PageSize.A4, 0f, 0, 0, 200);
        /// document.setMargins()
        appointmentID = registerPatients.getUserregistration_code();
        patientID = registerPatients.getUserregistration_code();
        patientName = registerPatients.getUserregistration_complete_name();
        patientMobile = registerPatients.getUserregistration_mobile_number();
        patientDOB = registerPatients.getUserregistration_age();
        patientAddress = registerPatients.getUserregistration_address_line_1();
        patientEmail = registerPatients.getUserregistration_email_address();


        try {
            if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
                //ToastUtils.showShortToastMessage(mContext, "External Storage not available or you"
                //   + " don't have permission to write");
            } else {
                pdfFile = new File(Environment.getExternalStorageDirectory() + "/" +"AccusterLtReports");

                if (!pdfFile.exists()) {
                    pdfFile = new File(Environment.getExternalStorageDirectory() + "/" + "AccusterLtReports" + "/");
                    pdfFile.mkdirs();
                }
                pdfFile = new File(new File(Environment.getExternalStorageDirectory() + "/" + "AccusterLtReports" + "/"), patientName + "_" + patientID + filename);

            }

            PdfWriter docWriter = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            PageNumeration no = new PageNumeration();
            // docWriter.setPageEvent(no);
            docWriter.setPageEvent(this);
            docWriter.setPageSize(PageSize.A4);
            height = docWriter.getPageSize().getHeight();
            width = docWriter.getPageSize().getWidth();
            this.docWriter = docWriter;
            document.open();


            PdfContentByte cb = docWriter.getDirectContent();
            //initialize fonts for text printing
            initializeFonts();
            if (Heleprec.update) {
                campdetails = Heleprec.repostlistmap.get(Heleprec.current_camp_name);
                if (campdetails!=null)
                    footer = campdetails.isFooter();
                Log.e("s1", Heleprec.update + "");
                if (campdetails!=null)
                    if (Heleprec.repostlistmap.get(Heleprec.current_camp_name).isHeader()) {

                        if (Heleprec.logo == null)
                            bmp2 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.acclogo);
                        else
                            bmp2 = Heleprec.logo;
                        if (bmp2 != null) {
                            ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
//                    Bitmap scaled = Bitmap.createScaledBitmap(bmp2, 150, 100, true);

                            bmp2.compress(Bitmap.CompressFormat.PNG, 100, stream2);

                            Image image2 = Image.getInstance(stream2.toByteArray());
                            image2.scaleToFit(100, 100);
                            image2.setAlignment(Image.ALIGN_JUSTIFIED);
                            image2.scaleAbsolute(100, 100);


                        }
                        labName = Heleprec.repostlistmap.get(Heleprec.current_camp_name).getOrganization_name();
                        //      labAddressLine1=
                        // createHeadings(cb, width - 350, height - 70, Heleprec.repostlistmap.get(Heleprec.current_camp_name).getOrganization_name());
                        String address = Heleprec.repostlistmap.get(Heleprec.current_camp_name).getCamp_address();
                        labAddressLine1 = address;
                        if (address.length() > 33) {
                            String line1 = address.substring(0, 33);
                            String line2 = address.substring(33, address.length() - 1);


                        }
                        document.add(addHeader(docWriter));
                    } else {
                        // document.setMarginMirroringTopBottom(true);
                        document.top(500.0f);
                        //  Toast.makeText(,"happen",Toast.LENGTH_LONG).show();

                        //  Log.e("add para ", "ho");
                        addEmptyLine(document, new Paragraph(), 5);

                    }
            } else {
                if (Heleprec.logo == null)
                    bmp2 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.acclogo);
                else
                    bmp2 = Heleprec.logo;
                if (bmp2 != null) {
                    ByteArrayOutputStream stream2 = new ByteArrayOutputStream();

                    bmp2.compress(Bitmap.CompressFormat.PNG, 100, stream2);

                    Image image2 = Image.getInstance(stream2.toByteArray());
                    image2.setAbsolutePosition(width - 500, height - 100);
                    image2.scaleToFit(100, 100);
                    image2.scaleAbsolute(100, 100);
                    document.add(addHeader(docWriter));

                }
                Log.e("s2", Heleprec.update + "");
            }
            addHorizontal_line(document);

            LineSeparator separator1 = new LineSeparator();
            //  separator.setDash(10);
            // separator3.setGap(0);
            separator1.setLineWidth(4);
            separator1.setPercentage(80);
            separator1.setAlignment(Element.ALIGN_CENTER);
            separator1.setLineColor(BaseColor.DARK_GRAY.brighter());
            Chunk linebreak1 = new Chunk(separator1);
            document.add(linebreak1);
            document.add(adduserDetails(registerPatients));
            LineSeparator separator = new LineSeparator();
            //  separator.setDash(10);
            // separator3.setGap(0);
            separator.setLineWidth(4);
            separator.setPercentage(80);
            separator.setAlignment(Element.ALIGN_CENTER);

            separator.setLineColor(BaseColor.DARK_GRAY.brighter());
            Chunk linebreak = new Chunk(separator);

            document.add(linebreak);
            addEmptyLine(document, new Paragraph(), 2);
            WidalTest widalTest=null;
            TableWidalTest tableWidalTest=new TableWidalTest(mContext);
            widalTest=tableWidalTest.getWidaltest(patientID);
            if (widalTest!=null)
                widal_flag=true;
            if (widal_flag) {
                addwidal_table(document);
                addPri_inter_for_widal(widalTest);
            }
            header_vari = 1;

            ArrayList<SubTestDetails> mSubTestDetails = new ArrayList<>();
            TablePatientTest patientTest = new TablePatientTest(mContext);
            patientTest.getallPatientTest(mSubTestDetails, appointmentID);
            Gson g = new Gson();
            Log.e("test details gson", g.toJson(mSubTestDetails));
            mSubTestDetails1 = mSubTestDetails;
            map = new HashMap<>();
            //  String package_name=mSubTestDetails1.get(0).getTest_type_name();
            pri_map = new HashMap<>();
            inti_map = new HashMap<>();

            // ArrayList<SubTestDetails> subtest=new ArrayList<>();
            for (SubTestDetails list : mSubTestDetails1) {
                // subtest.add(list) ;
                map.put(list.getTest_type_name(), list);

            }

            ArrayList<SubTestDetails> subtest = new ArrayList<>();
            for (SubTestDetails list : mSubTestDetails1) {
                if (!list.getTest_precautions().equals("")) {
                    subtest.add(list);
                    //  map.put(list.getTest_name(),list);
                }
            }
            if (subtest.size() > 0) {
                pri_inter = true;
            }
            if (!mSubTestDetails.isEmpty()) {
                if (widal_flag) {
                    document.newPage();
                    if (campdetails!=null)
                        if (campdetails.isHeader())
                    document.add(addHeader(docWriter));
                    addHorizontal_line(document);
                    document.add(linebreak1);
                    document.add(adduserDetails(registerPatients));
                    document.add(linebreak);
                }
                Paragraph p = new Paragraph();
                String packageName = mSubTestDetails.get(0).getTest_type_name();
                addPackageName(packageName, document);
                addEmptyLine(document, new Paragraph(), 1);
                //  PdfPTable pdfPTable;
                if (packageName.contains("SEROLOGY TEST")) {
                    pdfPTable = createTwoColumnTable();
                } else {
                    pdfPTable = createTable();
                }
                total_page = mSubTestDetails.size();
                int m=0;
                String first_pac=packageName;
                for (SubTestDetails subTestDetails : mSubTestDetails) {
                    if (!subTestDetails.getTest_type_name().equalsIgnoreCase(packageName)) {
                        document.add(pdfPTable);
                        packageName = subTestDetails.getTest_type_name();
                        document.newPage();
                        //document.add(addHeader(docWriter));
                        //  addHorizontal_line(document);
                        // addEmptyLine(document, new Paragraph(), 1);
                        if (header_vari==1) {
                            if (campdetails!=null)
                                if (campdetails.isHeader()) {
                                    document.add(addHeader(docWriter));
                                    addHorizontal_line(document);
                                }

                            LineSeparator separator11 = new LineSeparator();
                            //  separator.setDash(10);
                            // separator3.setGap(0);
                            separator11.setLineWidth(4);
                            separator11.setPercentage(80);
                            separator11.setAlignment(Element.ALIGN_CENTER);
                            separator11.setLineColor(BaseColor.DARK_GRAY.brighter());
                            Chunk linebreak11 = new Chunk(separator11);
                            document.add(linebreak11);
                            document.add(adduserDetails(registerPatient));
                            LineSeparator separator111 = new LineSeparator();
                            //  separator.setDash(10);
                            // separator3.setGap(0);
                            separator111.setLineWidth(4);
                            separator111.setPercentage(80);
                            separator111.setAlignment(Element.ALIGN_CENTER);

                            separator.setLineColor(BaseColor.DARK_GRAY.brighter());
                            Chunk linebreak111 = new Chunk(separator111);
                            //(current_page != 2)
                            document.add(linebreak111);
                        }
                        addPackageName(packageName, document);
                        addEmptyLine(document, new Paragraph(), 1);
                        if (packageName.contains("SEROLOGY TEST")) {
                            pdfPTable = createTwoColumnTable();
                        } else {
                            pdfPTable = createTable();
                        }
                    }
                    if (packageName.contains("SEROLOGY TEST")) {
                        addCellTwoColumn(pdfPTable, subTestDetails);
                    } else {
                        addCell(pdfPTable, subTestDetails, patientGender);
                    }


                }
                document.add(pdfPTable);
                addEmptyLine(document, new Paragraph(), 1);
                document.add(p);
            }
            Paragraph paragraph1 = new Paragraph();
            addEmptyLine(document, paragraph1, 1);
            document.add(paragraph1);
            // addPackageName("----END OF THE REPORT----", document);
            Font red = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.RED);
            document.close();
        } catch (Exception e) {
            // Log.e("exce", e.getMessage());
            e.printStackTrace();
            // ToastUtils.showShortToastMessage(mContext, e.toString());
        }


        boolean installed = appInstalledOrNot("com.adobe.reader");
        if (installed) {
            //This intent will help you to launch if the package is already installed

            Uri path;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                path = FileProvider.getUriForFile(mContext, mContext.getApplicationContext().getPackageName() + ".provider", pdfFile);
            } else {
                path = Uri.fromFile(pdfFile);
            }

            Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
            pdfOpenintent.setPackage("com.adobe.reader");
            List<ResolveInfo> resInfoList = mContext.getPackageManager().queryIntentActivities(pdfOpenintent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                mContext.grantUriPermission(packageName, path, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            pdfOpenintent.setDataAndType(path, "application/pdf");
            Intent intent = Intent.createChooser(pdfOpenintent, "Open File");
            try {
                mContext.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                // ToastUtils.showShortToastMessage(mContext, "No Application found to open file");
            } catch (Exception e) {
                //ToastUtils.showShortToastMessage(mContext, mContext.getString(R.string.server_error));
            }

            // System.out.println("App is already installed on your phone");
        } else {
            // System.out.println("App is not currently installed on your phone");

            android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(mContext).create();
            alertDialog.setTitle("Adobe reader application not installed.");
            alertDialog.setMessage("Adobe reader  is required to view report.");
//                    alertDialog.setMessage(sdf.format(date3) +" is after " + sdf.format(date2));
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setCancelable(false);
            alertDialog.setButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            alertDialog.show();
        }


        //PDF file is now ready to be sent to the bluetooth printer using PrintShare

    }

public void addwidal_table(Document document)
    {
        float[] columnWidths = {7f, 2f, 2f, 2f,2f,2f};
        WidalTest widalTest=null;
        TableWidalTest tableWidalTest=new TableWidalTest(mContext);
        widalTest=tableWidalTest.getWidaltest(patientID);

       // Log.e("the widal data",gson.toJson(tableWidalTest.getWidaltest(patient_id))+" data");
        if (widalTest!=null)
        try {
            addPackageName("Widal Test", document);
            addEmptyLine(document, new Paragraph(), 1);
            PdfPTable pdfPTable = new PdfPTable(columnWidths);
            Font font = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            Paragraph para = new Paragraph("", font);
            PdfPCell cell1 = new PdfPCell();
            cell1.setVerticalAlignment(Element.ALIGN_CENTER);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
          //  c1.setBorderWidthBottom(0f);

            cell1.addElement(para);

            pdfPTable.addCell(cell1);
            para = new Paragraph(widalTest.getWidal_test_head1(), font);
            cell1 = new PdfPCell();
            cell1.setVerticalAlignment(Element.ALIGN_CENTER);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.addElement(para);
            pdfPTable.addCell(cell1);
            para = new Paragraph(widalTest.getWidal_test_head2(), font);
            cell1 = new PdfPCell();
            cell1.setVerticalAlignment(Element.ALIGN_CENTER);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.addElement(para);
            pdfPTable.addCell(cell1);
            para = new Paragraph(widalTest.getWidal_test_head3(), font);
            cell1 = new PdfPCell();
            cell1.setVerticalAlignment(Element.ALIGN_CENTER);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.addElement(para);
            pdfPTable.addCell(cell1);
            para = new Paragraph(widalTest.getWidal_test_head4(), font);
            cell1 = new PdfPCell();
            cell1.setVerticalAlignment(Element.ALIGN_CENTER);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.addElement(para);
            pdfPTable.addCell(cell1);
            para = new Paragraph(widalTest.getWidal_test_head5(), font);
            cell1 = new PdfPCell();
            cell1.setVerticalAlignment(Element.ALIGN_CENTER);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.addElement(para);
            pdfPTable.addCell(cell1);
            para = new Paragraph(widalTest.getWidal_test_name1(), font);
            cell1 = new PdfPCell();
            cell1.setVerticalAlignment(Element.ALIGN_CENTER);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.addElement(para);
            pdfPTable.addCell(cell1);

            para = new Paragraph(widalTest.getWidal_result11(), font);
            cell1 = new PdfPCell();
            cell1.setVerticalAlignment(Element.ALIGN_CENTER);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.addElement(para);
            pdfPTable.addCell(cell1);

            para = new Paragraph(widalTest.getWidal_result12(), font);
            cell1 = new PdfPCell();
            cell1.setVerticalAlignment(Element.ALIGN_CENTER);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.addElement(para);
            pdfPTable.addCell(cell1);

            para = new Paragraph(widalTest.getWidal_result13(), font);
            cell1 = new PdfPCell();
            cell1.setVerticalAlignment(Element.ALIGN_CENTER);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.addElement(para);
            pdfPTable.addCell(cell1);

            para = new Paragraph(widalTest.getWidal_result14(), font);
            cell1 = new PdfPCell();
            cell1.setVerticalAlignment(Element.ALIGN_CENTER);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.addElement(para);
            pdfPTable.addCell(cell1);
            para = new Paragraph(widalTest.getWidal_result15(), font);
            cell1 = new PdfPCell();
            cell1.setVerticalAlignment(Element.ALIGN_CENTER);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.addElement(para);
            pdfPTable.addCell(cell1);
            para = new Paragraph(widalTest.getWidal_test_name2(), font);
            cell1 = new PdfPCell();
            cell1.setVerticalAlignment(Element.ALIGN_CENTER);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.addElement(para);
            pdfPTable.addCell(cell1);

            para = new Paragraph(widalTest.getWidal_result21(), font);
            cell1 = new PdfPCell();
            cell1.setVerticalAlignment(Element.ALIGN_CENTER);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.addElement(para);
            pdfPTable.addCell(cell1);

            para = new Paragraph(widalTest.getWidal_result22(), font);
            cell1 = new PdfPCell();
            cell1.setVerticalAlignment(Element.ALIGN_CENTER);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.addElement(para);
            pdfPTable.addCell(cell1);

            para = new Paragraph(widalTest.getWidal_result23(), font);
            cell1 = new PdfPCell();
            cell1.setVerticalAlignment(Element.ALIGN_CENTER);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.addElement(para);
            pdfPTable.addCell(cell1);

            para = new Paragraph(widalTest.getWidal_result24(), font);
            cell1 = new PdfPCell();
            cell1.setVerticalAlignment(Element.ALIGN_CENTER);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.addElement(para);
            pdfPTable.addCell(cell1);
            para = new Paragraph(widalTest.getWidal_result25(), font);
            cell1 = new PdfPCell();
            cell1.setVerticalAlignment(Element.ALIGN_CENTER);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.addElement(para);
            pdfPTable.addCell(cell1);
            para = new Paragraph(widalTest.getWidal_test_name3(), font);
            cell1 = new PdfPCell();
            cell1.setVerticalAlignment(Element.ALIGN_CENTER);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.addElement(para);
            pdfPTable.addCell(cell1);

            para = new Paragraph(widalTest.getWidal_result31(), font);
            cell1 = new PdfPCell();
            cell1.setVerticalAlignment(Element.ALIGN_CENTER);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.addElement(para);
            pdfPTable.addCell(cell1);

            para = new Paragraph(widalTest.getWidal_result32(), font);
            cell1 = new PdfPCell();
            cell1.setVerticalAlignment(Element.ALIGN_CENTER);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.addElement(para);
            pdfPTable.addCell(cell1);

            para = new Paragraph(widalTest.getWidal_result33(), font);
            cell1 = new PdfPCell();
            cell1.setVerticalAlignment(Element.ALIGN_CENTER);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.addElement(para);
            pdfPTable.addCell(cell1);

            para = new Paragraph(widalTest.getWidal_result34(), font);
            cell1 = new PdfPCell();
            cell1.setVerticalAlignment(Element.ALIGN_CENTER);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.addElement(para);
            pdfPTable.addCell(cell1);
            para = new Paragraph(widalTest.getWidal_result35(), font);
            cell1 = new PdfPCell();
            cell1.setVerticalAlignment(Element.ALIGN_CENTER);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.addElement(para);
            pdfPTable.addCell(cell1);
            para = new Paragraph(widalTest.getWidal_test_name4(), font);
            cell1 = new PdfPCell();
            cell1.setVerticalAlignment(Element.ALIGN_CENTER);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.addElement(para);
            pdfPTable.addCell(cell1);

            para = new Paragraph(widalTest.getWidal_result41(), font);
            cell1 = new PdfPCell();
            cell1.setVerticalAlignment(Element.ALIGN_CENTER);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.addElement(para);
            pdfPTable.addCell(cell1);

            para = new Paragraph(widalTest.getWidal_result42(), font);
            cell1 = new PdfPCell();
            cell1.setVerticalAlignment(Element.ALIGN_CENTER);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.addElement(para);
            pdfPTable.addCell(cell1);

            para = new Paragraph(widalTest.getWidal_result43(), font);
            cell1 = new PdfPCell();
            cell1.setVerticalAlignment(Element.ALIGN_CENTER);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.addElement(para);
            pdfPTable.addCell(cell1);
            para = new Paragraph(widalTest.getWidal_result44(), font);
            cell1 = new PdfPCell();
            cell1.setVerticalAlignment(Element.ALIGN_CENTER);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.addElement(para);
            pdfPTable.addCell(cell1);
            para = new Paragraph(widalTest.getWidal_result45(), font);
            cell1 = new PdfPCell();
            cell1.setVerticalAlignment(Element.ALIGN_CENTER);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.addElement(para);
            pdfPTable.addCell(cell1);
            document.add(pdfPTable);
            Log.e("createid","table");
        }
        catch (Exception e)
        {
            Log.e("createid","table"+e.getMessage());
        }
    }

    private void initializeFonts() {
        try {
            bfBold = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method for opening a pdf file
    private void viewPdf(String file, String directory) {

        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/" + directory + "/" + file);
        Uri path = Uri.fromFile(pdfFile);

        // Setting the intent for pdf reader
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            mContext.startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            // Toast.makeText(mContext, "Can't read pdf file", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = mContext.getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    private void createHeadings(PdfContentByte cb, float x, float y, String text) {
        cb.beginText();
        cb.setFontAndSize(bfBold, 16);
        cb.setTextMatrix(x, y);
        cb.showText(text.trim());
        cb.endText();
    }

    private void createPrimary(PdfContentByte cb, float x, float y, String text) {
        cb.beginText();
        cb.setFontAndSize(bfBold, 10);
        cb.setTextMatrix(x, y);
        cb.showText(text.trim());
        cb.endText();
    }

    private void createTitle(PdfContentByte cb, float x, float y, String text) {
        cb.beginText();
        cb.setFontAndSize(bfBold, 12);
        cb.setTextMatrix(x, y);
        cb.showText(text.trim());
        cb.endText();
    }


    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState);
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(extStorageState);
    }

    private void addEmptyLine(Document document, Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
        try {
            document.add(paragraph);
        } catch (DocumentException e) {
            //ToastUtils.showShortToastMessage(mContext, e.toString());
        }
    }

    public PdfPTable addPri_inter(SubTestDetails list, int k) throws DocumentException {
        float[] columnWidths = {20f};
        PdfPTable table = new PdfPTable(columnWidths);
        //create PDF table with the given widths

        addPackageName("Precaution & Interpretations", document);
        addEmptyLine(document, new Paragraph(), 1);
        Font font = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        Paragraph para = new Paragraph("Precaution :", font);
        PdfPCell cell1 = new PdfPCell();
        cell1.setBorder(Rectangle.NO_BORDER);
        cell1.addElement(para);
        table.addCell(cell1);

        PdfPCell cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);

        Font font1 = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL);
        Paragraph para1 = new Paragraph(+ 1 + ":" + list.getTest_precautions(), font1);
        // Log.e(list.get(i),"name");

        cell.addElement(para1);


        table.addCell(cell);
        //}
        if (!list.getTest_interpretation().equals(""))
        {
            // Font font1 = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            Paragraph para2 = new Paragraph("interpretation :", font);
            PdfPCell cell2 = new PdfPCell();
            cell2.setBorder(Rectangle.NO_BORDER);
            cell2.addElement(para2);
            table.addCell(cell2);

            PdfPCell cell3 = new PdfPCell();
            cell3.setBorder(Rectangle.NO_BORDER);

            Font font2 = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL);
            Paragraph para3 = new Paragraph( + 1 + ":" + list.getTest_interpretation(), font2);
            //  Log.e(list.get(i),"name");
            cell3.addElement(para3);

            table.addCell(cell3);
        }

        pritable = table;
        document.add(table);



        return table;

    }
    public PdfPTable addPri_inter_for_widal(WidalTest list) throws DocumentException {
        float[] columnWidths = {20f};
        PdfPTable table = new PdfPTable(columnWidths);
        //create PDF table with the given widths
        addPackageName("Precaution & Interpretations", document);
        addEmptyLine(document, new Paragraph(), 1);
        Font font = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        Paragraph para = new Paragraph("Precaution :", font);
        PdfPCell cell1 = new PdfPCell();
        cell1.setBorder(Rectangle.NO_BORDER);
        cell1.addElement(para);
        table.addCell(cell1);
        PdfPCell cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        Font font1 = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL);
        Paragraph para1 = new Paragraph(+ 1 + ":" + list.getWidal_test_precaution(), font1);
        // Log.e(list.get(i),"name");
        cell.addElement(para1);
        table.addCell(cell);
        if (!list.getWidal_test_interpretation().equals(""))
        {
            // Font font1 = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            Paragraph para2 = new Paragraph("interpretation :", font);
            PdfPCell cell2 = new PdfPCell();
            cell2.setBorder(Rectangle.NO_BORDER);
            cell2.addElement(para2);
            table.addCell(cell2);

            PdfPCell cell3 = new PdfPCell();
            cell3.setBorder(Rectangle.NO_BORDER);

            Font font2 = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL);
            Paragraph para3 = new Paragraph( + 1 + ":" + list.getWidal_test_interpretation(), font2);
            //  Log.e(list.get(i),"name");
            cell3.addElement(para3);
            table.addCell(cell3);
        }
        pritable = table;
        document.add(table);
        return table;
    }
    private PdfPTable adduserDetails(RegisterPatient registerPatients)
    {
        Gson g = new Gson();
        //Log.e("user gson", g.toJson(registerPatients));
        float[] columnWidths = {7f, 7f, 7f, 7f};
        PdfPTable table = new PdfPTable(columnWidths);
        table.getDefaultCell().setFixedHeight(10);
        //table.(new SolidBorder(1));
        // set table width a percentage of the page width
        table.getDefaultCell().setBorder(Rectangle.BOX);
        table.setTotalWidth(1250f);
        Font f1 = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        Paragraph para = new Paragraph("PATIENT ID : ", f1);
        PdfPCell c1 = new PdfPCell(para);
        //  c1.addElement(para);
        c1.setVerticalAlignment(Element.ALIGN_CENTER);
        c1.setHorizontalAlignment(Element.ALIGN_LEFT);
        c1.setBorderWidthBottom(0f);
//        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorder(Rectangle.NO_BORDER);
        c1.setBorderWidthRight(0f);

        // cell.setRightIndent(10);

        table.addCell(c1);
        Font font1 = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
        Paragraph p1 = new Paragraph(registerPatients.getUserregistration_code(), font1);
        //   p1.setFont(font);
        PdfPCell Cell1 = new PdfPCell(p1);
        Cell1.setBorder(Rectangle.NO_BORDER);
        Cell1.setBorderWidthLeft(0f);
        Cell1.setBorderWidthRight(0f);
        Cell1.setBorderWidthBottom(0f);
        Cell1.setBorderWidthTop(0f);

        Cell1.setVerticalAlignment(Element.ALIGN_CENTER);
        Cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(Cell1);
        Font f2 = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        Paragraph para1 = new Paragraph("S. COLLECTION DATE : ", f2);
        PdfPCell c2 = new PdfPCell(para1);
        //  c2.addElement(para);
        c2.setVerticalAlignment(Element.ALIGN_CENTER);
        c2.setHorizontalAlignment(Element.ALIGN_LEFT);
        c2.setBorderWidthBottom(0f);
//        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        c2.setBorderWidthRight(0f);
        c2.setBorderWidthLeft(0f);
        c2.setBorder(Rectangle.NO_BORDER);
        // cell.setRightIndent(10);

        table.addCell(c2);
        Font font2 = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
        DateTimeUtils dateTimeUtils=new DateTimeUtils();
        DateTimeUtils.getCurrentDay();
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMM");
        String month_name = month_date.format(cal.getTime());
        Paragraph p2 = new Paragraph(DateTimeUtils.getCurrentDay()+"th "+month_name+" "+Calendar.getInstance().get(Calendar.YEAR), font2);
      //  Paragraph p2 = new Paragraph(registerPatients.getUserregistration_mobile_number(), font2);
        //   p1.setFont(font);
        PdfPCell Cell2 = new PdfPCell(p2);
        Cell2.setBorder(Rectangle.NO_BORDER);

        Cell2.setBorderWidthLeft(0f);
        Cell2.setBorderWidthBottom(0f);
        Cell2.setVerticalAlignment(Element.ALIGN_CENTER);
        Cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(Cell2);
        Font f3 = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        Paragraph para2 = new Paragraph("NAME : ", f3);
        PdfPCell c3 = new PdfPCell(para2);
        c3.setBorderWidthRight(0f);
        c3.setBorderWidthBottom(0f);
        c3.setBorderWidthTop(0f);
        // c3.addElement(para2);
        c3.setVerticalAlignment(Element.ALIGN_CENTER);
        c3.setHorizontalAlignment(Element.ALIGN_LEFT);
//        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        c3.setBorder(Rectangle.NO_BORDER);
        // cell.setRightIndent(10);

        table.addCell(c3);
        Font font3 = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
        Paragraph p3 = new Paragraph(registerPatients.getUserregistration_complete_name(), font3);
        //   p1.setFont(font);
        PdfPCell Cell3 = new PdfPCell(p3);
        //Cell3.setBorderWidthTop(0f);
        Cell3.setBorder(Rectangle.NO_BORDER);
        Cell3.setBorderWidthRight(0f);
        Cell3.setBorderWidthLeft(0f);
        //  Cell3.setBorderWidthBottom(0f);
        Cell3.setBorderWidthTop(0f);
        Cell3.setBorderWidthBottom(0f);
        Cell3.setVerticalAlignment(Element.ALIGN_CENTER);
        Cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(Cell3);
        Font f4 = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        Paragraph para3 = new Paragraph("AGE : ", f4);
        // para.setLeading(0, 1);
        //p1.setFont(boldFont);
        PdfPCell c4 = new PdfPCell(para3);
        c4.setBorderWidthRight(0f);
        c4.setBorderWidthLeft(0f);
        c4.setBorderWidthBottom(0f);
        c4.setBorderWidthTop(0f);
        // c4.addElement(para3);
        c4.setVerticalAlignment(Element.ALIGN_CENTER);
        c4.setHorizontalAlignment(Element.ALIGN_LEFT);
//        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        c4.setBorder(Rectangle.NO_BORDER);
        // cell.setRightIndent(10);

        table.addCell(c4);
        Font font4 = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
        Paragraph p4 = new Paragraph(registerPatients.getUserregistration_age(), font4);
        //   p1.setFont(font);
        PdfPCell Cell4 = new PdfPCell(p4);
        Cell4.setBorder(Rectangle.NO_BORDER);
        Cell4.setBorderWidthLeft(0f);
        Cell4.setBorderWidthBottom(0f);
        Cell4.setBorderWidthTop(0f);

        Cell4.setVerticalAlignment(Element.ALIGN_CENTER);
        Cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(Cell4);
        Font f5 = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        Paragraph para4 = new Paragraph("SEX : ", f5);
        // para.setLeading(0, 1);
        //p1.setFont(boldFont);
        PdfPCell c5 = new PdfPCell(para4);
        c5.setBorderWidthBottom(0f);
        c5.setBorderWidthTop(0f);
        //c5.addElement(para4);
        c5.setBorderWidthRight(0f);
        c5.setVerticalAlignment(Element.ALIGN_CENTER);
        c5.setHorizontalAlignment(Element.ALIGN_LEFT);
//        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        c5.setBorder(Rectangle.NO_BORDER);
        String gender = TextUtils.isEmpty(registerPatients.getUserregistration_gender_id()) ? ""
                : registerPatients.getUserregistration_gender_id();
        if (gender.equalsIgnoreCase("0")) {
            patientGender = "Male";
        } else if (gender.equalsIgnoreCase("1")) {
            patientGender = "Female";
        } else if (gender.equalsIgnoreCase("2")) {
            patientGender = "Other";
        }
        // cell.setRightIndent(10);

        table.addCell(c5);
        Font font5 = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
        Paragraph p5 = new Paragraph(patientGender, font5);
        //   p1.setFont(font);
        PdfPCell Cell5 = new PdfPCell(p5);
        Cell5.setBorder(Rectangle.NO_BORDER);
        Cell5.setBorderWidthRight(0f);
        Cell5.setBorderWidthBottom(0f);
        Cell5.setBorderWidthTop(0f);
        Cell5.setBorderWidthLeft(0f);
        Cell5.setVerticalAlignment(Element.ALIGN_CENTER);
        Cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(Cell5);
        Font f6 = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        Paragraph para5 = new Paragraph("ADDRESS : ", f1);
        // para.setLeading(0, 1);
        //p1.setFont(boldFont);
        PdfPCell c6 = new PdfPCell(para5);
        c6.setBorderWidthBottom(0f);
        c6.setBorderWidthTop(0f);

        //  c6.addElement(para5);
        c6.setBorderWidthRight(0f);
        c6.setBorderWidthLeft(0f);
        c6.setVerticalAlignment(Element.ALIGN_CENTER);
        c6.setHorizontalAlignment(Element.ALIGN_LEFT);
//        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        c6.setBorder(Rectangle.NO_BORDER);
        // cell.setRightIndent(10);

        table.addCell(c6);
        Font font6 = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
        Paragraph p6 = new Paragraph(registerPatients.getUserregistration_address_line_1(), font6);
        //   p1.setFont(font);
        PdfPCell Cell6 = new PdfPCell(p6);
        Cell6.setBorder(Rectangle.NO_BORDER);
        Cell6.setBorderWidthLeft(0f);
        Cell6.setVerticalAlignment(Element.ALIGN_CENTER);
        Cell6.setHorizontalAlignment(Element.ALIGN_LEFT);
        Cell6.setBorderWidthBottom(0f);
        Cell6.setBorderWidthTop(0f);
        table.addCell(Cell6);
        Font f7 = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        Paragraph para6 = new Paragraph("DIET : ", f7);

        PdfPCell c7 = new PdfPCell(para6);
        c7.setBorderWidthTop(0f);
        c7.setBorderWidthRight(0f);
        // c7.addElement(para);
        c7.setVerticalAlignment(Element.ALIGN_CENTER);
        c7.setHorizontalAlignment(Element.ALIGN_LEFT);
//        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        c7.setBorder(Rectangle.NO_BORDER);
        //c7.setBorderWidthBottom(4f);
        // cell.setRightIndent(10);

        table.addCell(c7);
        Font font7 = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
        Paragraph p7 = new Paragraph(registerPatients.getUserregistration_diet(), font7);
        //   p1.setFont(font);
        PdfPCell Cell7 = new PdfPCell(p7);
        Cell7.setBorder(Rectangle.NO_BORDER);
        Cell7.setBorderWidthRight(0f);
        Cell7.setBorderWidthLeft(0f);
        Cell7.setBorderWidthTop(0f);
        // Cell7.setBorderWidthBottom(4f);

        Cell7.setVerticalAlignment(Element.ALIGN_CENTER);
        Cell7.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(Cell7);
        Font f8 = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        Paragraph para7 = new Paragraph("MOBILE : ", f8);
        // para.setLeading(0, 1);
        //p1.setFont(boldFont);
        PdfPCell c8 = new PdfPCell(para7);
        c8.setBorderWidthRight(0f);
        c8.setBorderWidthLeft(0f);
        c8.setBorderWidthTop(0f);

        // c8.addElement(para7);
        c8.setVerticalAlignment(Element.ALIGN_CENTER);
        c8.setHorizontalAlignment(Element.ALIGN_LEFT);
//        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        c8.setBorder(Rectangle.NO_BORDER);
        // cell.setRightIndent(10);
        table.addCell(c8);
        Font font9 = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
        Paragraph p9 = new Paragraph(registerPatients.getUserregistration_mobile_number(), font9);
        //   p1.setFont(font);
        PdfPCell Cell9 = new PdfPCell(p9);
        Cell9.setBorder(Rectangle.NO_BORDER);
        Cell9.setBorderWidthLeft(0f);
        Cell9.setBorderWidthTop(0f);
        Cell9.setVerticalAlignment(Element.ALIGN_CENTER);
        Cell9.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(Cell9);
//        Font f9 = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
//        Paragraph para9 = new Paragraph("BMI : ", f9);
//
//        PdfPCell c9 = new PdfPCell(para9);
//        c9.setBorderWidthTop(0f);
//        c9.setBorderWidthRight(0f);
//        // c7.addElement(para);
//        c9.setVerticalAlignment(Element.ALIGN_CENTER);
//        c9.setHorizontalAlignment(Element.ALIGN_LEFT);
////        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        c9.setBorder(Rectangle.NO_BORDER);
//        //c7.setBorderWidthBottom(4f);
//        // cell.setRightIndent(10);
//
//        table.addCell(c9);
//        Font font10 = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
//        Paragraph p10 = new Paragraph(registerPatients.getUserregistration_bmi(), font10);
//        //   p1.setFont(font);
//        PdfPCell Cell10 = new PdfPCell(p10);
//        Cell10.setBorder(Rectangle.NO_BORDER);
//        Cell10.setBorderWidthRight(0f);
//        Cell10.setBorderWidthLeft(0f);
//        Cell10.setBorderWidthTop(0f);
//        // Cell7.setBorderWidthBottom(4f);
//
//        Cell10.setVerticalAlignment(Element.ALIGN_CENTER);
//        Cell10.setHorizontalAlignment(Element.ALIGN_LEFT);
//        table.addCell(Cell10);
//        Font f10 = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
//        Paragraph para10 = new Paragraph("BP : ", f10);
//        // para.setLeading(0, 1);
//        //p1.setFont(boldFont);
//        PdfPCell c10 = new PdfPCell(para10);
//        c10.setBorderWidthRight(0f);
//        c10.setBorderWidthLeft(0f);
//        c10.setBorderWidthTop(0f);
//
//        // c8.addElement(para7);
//        c10.setVerticalAlignment(Element.ALIGN_CENTER);
//        c10.setHorizontalAlignment(Element.ALIGN_LEFT);
////        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        c10.setBorder(Rectangle.NO_BORDER);
//        // cell.setRightIndent(10);
//
//        table.addCell(c10);
//        Font font11 = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
//        Paragraph p11 = new Paragraph(registerPatients.getUserregistration_bp(), font11);
//        //   p1.setFont(font);
//        PdfPCell Cell11 = new PdfPCell(p11);
//        Cell11.setBorder(Rectangle.NO_BORDER);
//        Cell11.setBorderWidthLeft(0f);
//        Cell11.setBorderWidthTop(0f);
//        Cell11.setVerticalAlignment(Element.ALIGN_CENTER);
//        Cell11.setHorizontalAlignment(Element.ALIGN_LEFT);
//        table.addCell(Cell11);


        return table;
    }


    private PdfPTable createTable() {
        float[] columnWidths = {5f, 5f, 5f, 5f};
        //create PDF table with the given widths
        PdfPTable table = new PdfPTable(columnWidths);
        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        table.setTotalWidth(950f);
        Font font = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        Paragraph para = new Paragraph("Test", font);
        PdfPCell cell = new PdfPCell(para);
        // cell.addElement(para);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(Rectangle.NO_BORDER);
        // cell.setRightIndent(10);

        table.addCell(cell);

        //    p2.setFont(boldFont);
        para = new Paragraph("Result", font);
        cell = new PdfPCell(para);
        //  cell.addElement(para);
        cell.setBorder(Rectangle.NO_BORDER);
        // cell.setRightIndent(10);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell);
        //  Phrase p3=new Phrase("Units");
        // p3.setFont(boldFont);
        para = new Paragraph("Units", font);
        cell = new PdfPCell(para);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        // cell.addElement(para);
        cell.setBorder(Rectangle.NO_BORDER);
        //cell.setRightIndent(10);
        table.addCell(cell);
        //   Phrase p4=new Phrase("Normal Range");
        // p4.setFont(boldFont);
        para = new Paragraph("Normal Range", font);

        cell = new PdfPCell(para);
        //  cell.addElement(para);
//        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        // cell.setRightIndent(10);
        table.addCell(cell);
        table.setHeaderRows(1);
        return table;
    }

    private PdfPTable createTwoColumnTable() {
        float[] columnWidths = {5f, 5f};
        //create PDF table with the given widths
        PdfPTable table = new PdfPTable(columnWidths);
        // set table width a percentage of the page width
        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        table.setTotalWidth(950f);
        Font font = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        Paragraph para = new Paragraph("Test Name", font);
        // para.setLeading(0, 1);
        //p1.setFont(boldFont);
        PdfPCell cell = new PdfPCell(para);
        // cell.addElement(para);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(Rectangle.NO_BORDER);
        // cell.setRightIndent(10);

        table.addCell(cell);
        para = new Paragraph("Result", font);
        cell = new PdfPCell(para);
        //  cell.addElement(para);
        cell.setBorder(Rectangle.NO_BORDER);
        // cell.setRightIndent(10);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell);
        table.setHeaderRows(1);
        return table;
    }

    public void addCell(PdfPTable pdfPTable, SubTestDetails subTestDetails, String patientGender) throws DocumentException {


        Font font = new Font(Font.FontFamily.HELVETICA, 8);
        Paragraph p1 = new Paragraph(subTestDetails.getTest_name(), font);
        //   p1.setFont(font);
        PdfPCell Cell1 = new PdfPCell(p1);
        Cell1.setBorder(Rectangle.NO_BORDER);

        Cell1.setVerticalAlignment(Element.ALIGN_CENTER);
        Cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
        pdfPTable.addCell(Cell1);
        String result1 = subTestDetails.getTest_result()
                .replaceAll("[^0-9.]", "");


        //    Log.e("manual value is ",subTestDetails.is_manual_status())
        if (subTestDetails.is_manual_status()) {
            try {

                float result = Float.parseFloat(subTestDetails.getTest_result().replaceAll("[^0-9.]", ""));
                if (subTestDetails.getTest_result().contains("/"))
                {
                    Paragraph p2 = new Paragraph(subTestDetails.getTest_result(), font);
                    PdfPCell celltwo = new PdfPCell(p2);

                    celltwo.setHorizontalAlignment(Element.ALIGN_LEFT);
                    celltwo.setBorder(Rectangle.NO_BORDER);
                    celltwo.setRightIndent(10);
                    pdfPTable.addCell(celltwo);
                }
                else
                    addNumericReult(pdfPTable,subTestDetails);

            }
            catch (Exception e)
            {
                Paragraph p2 = new Paragraph(subTestDetails.getTest_result(), font);
                PdfPCell celltwo = new PdfPCell(p2);

                celltwo.setHorizontalAlignment(Element.ALIGN_LEFT);
                celltwo.setBorder(Rectangle.NO_BORDER);
                celltwo.setRightIndent(10);
                pdfPTable.addCell(celltwo);

            }

        }
        else {
            addNumericReult(pdfPTable,subTestDetails);
            //  Log.e("not manual","you done sachin");

        }


        p1 = new Paragraph(subTestDetails.getTest_unit(), font);
        // p1.setFont(font);
        PdfPCell Cell2 = new PdfPCell(p1);
        Cell2.setBorder(Rectangle.NO_BORDER);
        Cell2.setVerticalAlignment(Element.ALIGN_CENTER);
        Cell2.setHorizontalAlignment(Element.ALIGN_LEFT);

        pdfPTable.addCell(Cell2);
        PdfPCell Cell3 = null;
        if (patientGender.equalsIgnoreCase("Male")) {
            p1 = new Paragraph(subTestDetails.getTest_low_bound_male() + "-" + subTestDetails.getTest_upper_bound_male(), font);

            //  p1.setFont(font);
            Cell3 = new PdfPCell(p1);

            //pdfPTable.addCell(subTestDetails.getTest_low_bound_male() + "-" + subTestDetails.getTest_upper_bound_male());
        } else if (patientGender.equalsIgnoreCase("Female")) {

            p1 = new Paragraph(subTestDetails.getTest_low_bound_female() + "-" + subTestDetails.getTest_upper_bound_female(), font);
            //  p1.setFont(font);
            Cell3 = new PdfPCell(p1);//est_upper_bound_female());
        } else {
            p1 = new Paragraph(subTestDetails.getTest_low_bound_male() + "-" + subTestDetails.getTest_upper_bound_male(), font);
            // p1.setFont(font);
            Cell3 = new PdfPCell(p1);
            //pdfPTable.addCell(subTestDetails.getTest_low_bound_male() + "-" + subTestDetails.getTest_upper_bound_male());
        }
        Cell3.setBorder(Rectangle.NO_BORDER);
        Cell3.setVerticalAlignment(Element.ALIGN_CENTER);
        Cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
        pdfPTable.addCell(Cell3);
        if(!subTestDetails.getTest_precautions().equals(""))
        {
            document.add(pdfPTable);
            addPri_inter(subTestDetails,0);
            this.pdfPTable=createTable();
        }
    }

    public void addCellTwoColumn(PdfPTable pdfPTable, SubTestDetails subTestDetails) throws DocumentException {
        Font font = new Font(Font.FontFamily.HELVETICA, 8);


        PdfPCell cell = new PdfPCell(new Paragraph(subTestDetails.getTest_name(),font));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setRightIndent(10);
        pdfPTable.addCell(cell);
        String result = subTestDetails.getTest_result()
                .replaceAll("[^0-9.]", "");
        //  Log.e("result ram",result);

        PdfPCell celltwo = new PdfPCell(new Paragraph(subTestDetails.getTest_result(),font));
        /// Log.e(subTestDetails.getTest_result(), "result");
        celltwo.setHorizontalAlignment(Element.ALIGN_LEFT);
        celltwo.setBorder(Rectangle.NO_BORDER);
        celltwo.setRightIndent(10);
        PdfPCell gd1 = pdfPTable.addCell(celltwo);
        if(!subTestDetails.getTest_precautions().equals(""))
        {
            document.add(pdfPTable);
            document.add( addPri_inter(subTestDetails,0));
            this.pdfPTable=createTwoColumnTable();
        }



    }

    public void addPackageName(String packageName, Document document) {
        if (packageName.contains("+"))
        {  try {

            Font f = new Font(Font.FontFamily.TIMES_ROMAN, 14.0f, Font.BOLD, BaseColor.BLACK);
            String package_list[]=packageName.split("\\+");
            Paragraph pa = new Paragraph(package_list[0], f);
            Paragraph pa_sub = new Paragraph(package_list[1], f);
            pa.setAlignment(Paragraph.ALIGN_CENTER);
            pa_sub.setAlignment(Paragraph.ALIGN_CENTER);




                document.add(pa);
                document.add(pa_sub);
            } catch (DocumentException e) {
                //  ToastUtils.showShortToastMessage(mContext, e.toString());
            }
        }
        else {
            Font f = new Font(Font.FontFamily.TIMES_ROMAN, 14.0f, Font.BOLD, BaseColor.BLACK);
            Paragraph pa = new Paragraph(packageName, f);
            pa.setAlignment(Paragraph.ALIGN_CENTER);
            try {

                document.add(pa);
            } catch (DocumentException e) {
                //  ToastUtils.showShortToastMessage(mContext, e.toString());
            }
        }


    }
    public Bitmap createBitmap(Drawable drawable) {
        try {
            Bitmap bitmap;
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable
                    .getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (OutOfMemoryError e) {
            // Handle the error
            return null;
        }
    }

    public boolean conectivity() {
        ConnectivityManager connectivity = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
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

    public PdfPTable addHeader(PdfWriter writer) {
        float[] columnWidths = {5f, 15f};
        PdfPTable header = new PdfPTable(2);
        header.setHorizontalAlignment(Element.ALIGN_CENTER);
        try {
            // set defaults
            header.setWidths(new int[]{6, 24});
            // header.setTotalWidth(527);
            // header.setLockedWidth(true);
            header.getDefaultCell().setFixedHeight(60);
            header.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            //  header.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);
            ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
//                    Bitmap scaled = Bitmap.createScaledBitmap(bmp2, 150, 100, true);

            bmp2.compress(Bitmap.CompressFormat.PNG, 100, stream2);

            Image image2 = Image.getInstance(stream2.toByteArray());
//                    image2.setAbsolutePosition(0, height - 100);
            image2.scaleToFit(100, 100);
            image2.scaleAbsolute(100, 100);
//                    image2.setAlignment(Image.LEFT);
            // document.add(image2);
            // add image
            //Image logo = Image.getInstance(HeaderFooterPageEven.class.getResource("/memorynotfound-logo.jpg"));
            header.addCell(image2);
            // add text
            PdfPCell text = new PdfPCell();
            // text.setPaddingBottom(15);
            // text.setPaddingTop(5);
            text.setPaddingLeft(10);
            text.setPaddingTop(0f);
            text.setVerticalAlignment(Element.ALIGN_TOP);
            text.setHorizontalAlignment(Element.ALIGN_RIGHT);
            text.setBorder(Rectangle.NO_BORDER);
            text.setBorderColor(BaseColor.LIGHT_GRAY);

            //createHeadings(cb, width - 250, height - 70, Heleprec.repostlistmap.get(Heleprec.current_camp_name).getOrganization_name());
            //String address=Heleprec.repostlistmap.get(Heleprec.current_camp_name).getCamp_address();
            text.addElement(new Phrase(labName, new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD)));
            text.addElement(new Phrase(labAddressLine1, new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
            header.addCell(text);
            // write content
            //  header.writeSelectedRows(0, -1, 34, 803, writer.getDirectContent());
            // addEmptyLine(do);
            return header;
        } catch (DocumentException de) {
            throw new ExceptionConverter(de);
        } catch (MalformedURLException e) {
            throw new ExceptionConverter(e);
        } catch (IOException e) {
            throw new ExceptionConverter(e);
        } catch (Exception e) {
            throw new ExceptionConverter(e);
        }
    }
    public PdfPTable adddFooter() {
        // if
        float[] columnWidths = {5f, 15f};
        PdfPTable header = new PdfPTable(2);
        header.setHorizontalAlignment(Element.ALIGN_CENTER);
        try {

            header.setWidths(new int[]{6, 24});
            header.getDefaultCell().setFixedHeight(60);
            header.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            //  header.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);
            ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
//                    Bitmap scaled = Bitmap.createScaledBitmap(bmp2, 150, 100, true);

            bmp2.compress(Bitmap.CompressFormat.PNG, 100, stream2);

            Image image2 = Image.getInstance(stream2.toByteArray());
//                    image2.setAbsolutePosition(0, height - 100);
            image2.scaleToFit(100, 100);
            image2.scaleAbsolute(100, 100);
//
            //Image logo = Image.getInstance(HeaderFooterPageEven.class.getResource("/memorynotfound-logo.jpg"));
            header.addCell(image2);

            // add text
            PdfPCell text = new PdfPCell();
            // text.setPaddingBottom(15);
            // text.setPaddingTop(5);
            text.setPaddingLeft(10);
            text.setHorizontalAlignment(Element.ALIGN_RIGHT);
            text.setBorder(Rectangle.NO_BORDER);
            text.setBorderColor(BaseColor.LIGHT_GRAY);

            //createHeadings(cb, width - 250, height - 70, Heleprec.repostlistmap.get(Heleprec.current_camp_name).getOrganization_name());
            //String address=Heleprec.repostlistmap.get(Heleprec.current_camp_name).getCamp_address();
            text.addElement(new Phrase(labName, new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD)));
            text.addElement(new Phrase(labAddressLine1, new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
            header.addCell(text);

            // write content
            //    header.writeSelectedRows(0, -1, 34, 803, writer.getDirectContent());
            // addEmptyLine(do);
            //header.set
            return header;
        } catch (DocumentException de) {
            throw new ExceptionConverter(de);
        } catch (MalformedURLException e) {
            throw new ExceptionConverter(e);
        } catch (IOException e) {
            throw new ExceptionConverter(e);
        } catch (Exception e) {
            throw new ExceptionConverter(e);
        }
    }
    @Override
    public void onEndPage(PdfWriter writer, Document document) {

        super.onEndPage(writer, document);
//        try {
//            Paragraph paragraph1 = new Paragraph();
//            addEmptyLine(document, paragraph1, 1);
//            document.add(paragraph1);
        // addPackageName("----END OF THE REPORT----", document);
//        }
//        catch (Exception e)
//        {
//
//        }
        Paragraph footer1 = new Paragraph("----END OF THE REPORT----");
        //footer.Alignment = Element.ALIGN_RIGHT;
        footer1.setAlignment(Element.ALIGN_LEFT);
        PdfPTable footerTbl1 = new PdfPTable(1);
        footerTbl1.setTotalWidth(900f);
        footerTbl1.setHorizontalAlignment(Element.ALIGN_CENTER);
        PdfPCell cell1 = new PdfPCell();
        cell1.addElement(new Phrase("----END OF THE REPORT----", new Font(Font.FontFamily.TIMES_ROMAN, 14.0f, Font.BOLD, BaseColor.BLACK)));
        // cell1.addElement(new Phrase(current_page + " of " + total_page, new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
        cell1.setBorder(Rectangle.NO_BORDER);
        // cell.PaddingLeft = 10;
        cell1.setPaddingLeft(10);
        footerTbl1.addCell(cell1);
        footerTbl1.writeSelectedRows(0, -1, 200, 200, writer.getDirectContent());
        if (footer) {

            Paragraph footer = new Paragraph("");
            //footer.Alignment = Element.ALIGN_RIGHT;
            footer.setAlignment(Element.ALIGN_RIGHT);
            PdfPTable footerTbl = new PdfPTable(1);
            footerTbl.setTotalWidth(900f);
            footerTbl.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell cell = new PdfPCell();
            int total_page=0;
            if (widal_flag)
             total_page = map.size()+1;
            else
                total_page = map.size();
            if (pri_inter) {
                total_page = total_page;
            }
            cell.addElement(new Phrase("(-----------------------)", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD)));
            cell.addElement(new Phrase(current_page + " of " + total_page, new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));

            // cell.Border = 0;
            current_page = current_page + 1;
            cell.setBorder(Rectangle.NO_BORDER);
            // cell.PaddingLeft = 10;
            cell.setPaddingLeft(10);
            footerTbl.addCell(cell);
            footerTbl.writeSelectedRows(0, -1, 415, 150, writer.getDirectContent());
            PdfPTable footerTbl2 = new PdfPTable(2);

            footerTbl2.setWidthPercentage(80);
            PdfPCell p1 = new PdfPCell();
            try {
                footerTbl2.setTotalWidth(writer.getPageSize().getWidth());
                footerTbl2.setWidths(new int[]{3, 2});
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            footerTbl2.setLockedWidth(true);
            footerTbl2.setHorizontalAlignment(Element.ALIGN_CENTER);
            p1.addElement(new Phrase("1 - If results are alarming or unexpected then clients/patients For are advised to contact the laboratory immediately.", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
            p1.addElement(new Phrase("2 - This is only a professional opinion,it may be kindly correlated clinically whenever required.", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
            p1.addElement(new Phrase("3 - Partial reproduction of this report is not permitted.", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
            p1.setBorder(Rectangle.BOX);
            p1.setBorderWidthTop(2f);
            p1.setPaddingLeft(70f);
            p1.setBorderWidthBottom(0f);
            p1.setPaddingRight(0f);
            p1.setBorderWidthLeft(0f);
            p1.setHorizontalAlignment(Element.ALIGN_LEFT);
            footerTbl2.addCell(p1);
            p1.setPaddingRight(100);
            PdfPCell p2 = new PdfPCell();
            p2.addElement(new Phrase("For Any Queries contact:", new Font(Font.FontFamily.HELVETICA, 15, Font.BOLD)));
            p2.addElement(new Phrase("E-mail: support@accuster.com.", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
            p2.addElement(new Phrase("Website: www.accuster.com", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
            p2.setBorder(Rectangle.BOX);
            p2.setBorderWidthTop(2f);
            p2.setBorderWidthBottom(0f);
            p2.setBorderWidthRight(0f);
            p2.setHorizontalAlignment(Element.ALIGN_RIGHT);
            p2.setPaddingLeft(10f);
            p2.setPaddingTop(0f);
            p2.setPaddingRight(50f);
            footerTbl2.addCell(p2);


            footerTbl2.writeSelectedRows(0, 1, 0, 100, writer.getDirectContent());
        }
    }

    public void addHorizontal_line(Document document) throws DocumentException {
        addEmptyLine(document, new Paragraph(), 1);
        LineSeparator separator3 = new LineSeparator();
        separator3.setLineWidth(2);
        separator3.setLineColor(BaseColor.DARK_GRAY.brighter());
        Chunk linebreak3 = new Chunk(separator3);
        document.add(linebreak3);
        Paragraph p1 = new Paragraph();
        p1.setAlignment(Element.ALIGN_CENTER);
        document.add(p1);

        addEmptyLine(document, new Paragraph(), 2);
    }

    @Override
    public void onStartPage(PdfWriter writer, Document document) {
        super.onStartPage(writer, document);
        if (header_vari == 1) {
            //  try {
            // if (campdetails!=null)
            //  if (campdetails.isHeader())
//                document.add(addHeader(docWriter));
//                addHorizontal_line(document);
//
//                LineSeparator separator1 = new LineSeparator();
//                //  separator.setDash(10);
//                // separator3.setGap(0);
//                separator1.setLineWidth(4);
//                separator1.setPercentage(80);
//                separator1.setAlignment(Element.ALIGN_CENTER);
//                separator1.setLineColor(BaseColor.DARK_GRAY.brighter());
//                Chunk linebreak1 = new Chunk(separator1);
//                document.add(linebreak1);
//                document.add(adduserDetails(registerPatient));
//                LineSeparator separator = new LineSeparator();
//                //  separator.setDash(10);
//                // separator3.setGap(0);
//                separator.setLineWidth(4);
//                separator.setPercentage(80);
//                separator.setAlignment(Element.ALIGN_CENTER);
//
//                separator.setLineColor(BaseColor.DARK_GRAY.brighter());
//                Chunk linebreak = new Chunk(separator);
//if (current_page!=2)
//                document.add(linebreak);
            // addEmptyLine(document, new Paragraph(), 2);

//            } catch (DocumentException e) {
//                e.printStackTrace();
//            }

        }
    }
    boolean isAlphanumeric(String str) {
        for (int i=0; i<str.length(); i++) {
            char c = str.charAt(i);
            if (c < 0x30 || (c >= 0x3a && c <= 0x40) || (c > 0x5a && c <= 0x60) || c > 0x7a)
                return false;
        }

        return true;
    }
    public void addNumericReult(PdfPTable pdfPTable,SubTestDetails subTestDetails)
    {
        Font font = new Font(Font.FontFamily.HELVETICA, 12);
        try {
            boolean b = false;
            float result = 0;
            try {
                result = Float.parseFloat(subTestDetails.getTest_result().replaceAll("[^0-9.]", ""));
            } catch (Exception e) {
                result = 0;

            }
//                //.replaceAll("[^0-9.]", ""
            if (patientGender.equalsIgnoreCase("Male")) {
                String a = subTestDetails.getTest_upper_bound_male();
                if (a != null && !a.equals("")) {
                    float tub = Float.parseFloat(a);

                    if (result > tub)
                        b = true;
                }
                // pdfPTable.addCell(subTestDetails.getTest_low_bound_male() + "-" + subTestDetails.getTest_upper_bound_male());
            } else if (patientGender.equalsIgnoreCase("Female")) {
                String a = subTestDetails.getTest_upper_bound_female();
                String lower = subTestDetails.getTest_low_bound_female();
                if (a != null && !a.equals("") & lower != null && !lower.equals("")) {
                    float tub = Float.parseFloat(a);
                    float low = Float.parseFloat(lower);

                    if (result > tub || result < low)
                        b = true;
                }
                // pdfPTable.addCell(subTestDetails.getTest_low_bound_female() + "-" + subTestDetails.getTest_upper_bound_female());
            } else {
                String a = subTestDetails.getTest_upper_bound_male();
                String lower = subTestDetails.getTest_low_bound_male();
                if (a != null && !a.equals("") & lower != null && !lower.equals("")) {
                    float tub = Float.parseFloat(a);
                    float low = Float.parseFloat(lower);

                    if (result > tub || result < low)
                        b = true;


                }// pdfPTable.addCell(subTestDetails.getTest_low_bound_male() + "-" + subTestDetails.getTest_upper_bound_male());
            }

            if (b) {
                Font boldFont = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);
                //  pdfPTable.addCell(Html.toHtml(Html.fromHtml(reult)));

                PdfPCell pdfWordCell = new PdfPCell();
                Phrase firstLine = new Phrase(subTestDetails.getTest_result().replaceAll("[^0-9.]", ""), boldFont);
                pdfWordCell.setBorder(Rectangle.NO_BORDER);

                pdfWordCell.addElement(firstLine);
                pdfPTable.addCell(pdfWordCell);
            } else {
                Paragraph     p1 = new Paragraph(subTestDetails.getTest_result().replaceAll("[^0-9.]", ""), font);
                p1.setFont(font);
                PdfPCell pdfWordCell = new PdfPCell(p1);
                pdfWordCell.setVerticalAlignment(Element.ALIGN_CENTER);
                pdfWordCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                pdfWordCell.setBorder(Rectangle.NO_BORDER);

                pdfPTable.addCell(pdfWordCell);
            }
        } catch (Exception e) {
            //Log.e("error", e.getMessage());
        }
    }

}


