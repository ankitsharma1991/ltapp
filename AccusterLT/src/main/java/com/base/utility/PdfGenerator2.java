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
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.google.gson.Gson;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
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
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.accusterltapp.R;
import com.accusterltapp.model.ApprovedReportDetailsList;
import com.accusterltapp.model.ApprovedReportTestDetails;
import com.accusterltapp.model.Heleprec;
import com.accusterltapp.model.RegisterPatient;
import com.accusterltapp.service.PageNumeration;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
public class PdfGenerator2 extends PdfPageEventHelper {
    private BaseFont bfBold;
    private File pdfFile;
    Document document;
    PdfPTable pdfPTable;
    PdfWriter docWriter;
    int header_vari = 0;
    PdfPTable pritable;
    HashMap<String, ArrayList<String>> pri_map;
    HashMap<String, ArrayList<String>> inti_map;
    int total_page, current_page = 1;
    ApprovedReportDetailsList data;
    HashMap<String, String> map;
    Bitmap bmp2;
    // Document document;
    ArrayList<String> pri = new ArrayList<>();
    ArrayList<String> inti = new ArrayList<>();
    HashMap<String, String> inter_prlist = new HashMap<>();
    ArrayList<ApprovedReportTestDetails> mSubTestDetails1;
    private final String filename = ".pdf";
    private final String filepath = "ReportPdf";
    private String labName = "Accuster Technologies Pvt. Ltd.";
    private String labAddressLine1 = "Plot No 41 Sec"
            + " 8 ";
    private final String labAddressLine2 = "IMT Manesar, Gurgaon (HR)";
    private String labLogo;
    private String drName;
    private String drQualification;
    private String mediaName;
    private String patientName;
    private String patientDoc;
    private String patientMobile;
    private String patientGender = "N/A";
    private String patientDOB;
    private String patientAddress;
    private String patientVillage;
    private String patientCity;
    private String patientPostalCode;
    private String patientEmail;
    private String appointmentID;
    private String patientID, genderPos;
    private final Context mContext;
    private boolean pdfStatus, selectedReportStatus;
    private HashMap<String,String> listTestHead;
    HashMap<String, String> testHeadPrecation = new HashMap<>();
    private boolean precause;
    public PdfGenerator2(Context context) {
        mContext = context;
    }
    public void generatePDF(ApprovedReportDetailsList list, boolean selectedReportStatus1) {
        float height, width;
        RegisterPatient registerPatients = list.getUserdetails().get(0);
        boolean tableType = false;
        data = list;
        selectedReportStatus = selectedReportStatus1;
        //create a new document
        document = new Document(PageSize.A4, 0f, 0, 20f, 200);
        /// document.setMargins()
        appointmentID = registerPatients.getUserregistration_code();
        patientID = registerPatients.getUserregistration_code();
        patientName = registerPatients.getUserregistration_complete_name();
        patientMobile = registerPatients.getUserregistration_mobile_number();
        patientDOB = registerPatients.getUserregistration_age();
        patientAddress = registerPatients.getUserregistration_address_line_1();
        patientEmail = registerPatients.getUserregistration_email_address();
        Log.d("AGE JS=", patientDOB);
        try {
            if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
                ToastUtils.showShortToastMessage(mContext, "External Storage not available or you"
                        + " don't have permission to write");
            } else {
                pdfFile = new File(Environment.getExternalStorageDirectory() + "/" + "AccusterLtReports");
                if (!pdfFile.exists()) {
                    pdfFile = new File(Environment.getExternalStorageDirectory() + "/" + "AccusterLtReports" + "/");
                    pdfFile.mkdirs();
                }
                pdfFile = new File(new File(Environment.getExternalStorageDirectory() + "/" + "AccusterLtReports" + "/"), patientName + "_" + patientID + filename);
                //path for the PDF file
                //pdfFile = new File(mContext.getExternalFilesDir(filepath), patientName + "_" + patientID + filename);
            }
            docWriter = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            PageNumeration no = new PageNumeration();
            // docWriter.setPageEvent(no);
            docWriter.setPageEvent(this);
            docWriter.setPageSize(PageSize.A4);
            height = docWriter.getPageSize().getHeight();
            width = docWriter.getPageSize().getWidth();
            document.open();
            PdfContentByte cb = docWriter.getDirectContent();
            //initialize fonts for text printing
            initializeFonts();
            if (Heleprec.update) {
                Log.e("s1", Heleprec.update + "");
                if (Heleprec.repostlistmap.get(Heleprec.current_camp_name)!=null){
                if (Heleprec.repostlistmap.get(Heleprec.current_camp_name).isHeader()) {
                    if (Heleprec.logo == null)
                        bmp2 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.acclogo);
                    else
                        bmp2 = Heleprec.logo;
                    if (bmp2 != null) {
                        ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
//                    Bitmap scaled = Bitmap.createScaledBitmap(bmp2, 150, 100, true);
                        // Log.d("BITMAP 2=",bmp2.toString());
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
                    Log.d("tets123", "");
                    ///// if (bmp2==null)
                    //  return;
                    document.add(addHeader(docWriter));
                }
                } else {
                    // document.setMarginMirroringTopBottom(true);
                    document.top(500.0f);
                    //  Toast.makeText(,"happen",Toast.LENGTH_LONG).show();
                    Log.e("add para ", "ho");
                    addEmptyLine(document, new Paragraph(), 5);
                }
            } else {
                if (Heleprec.logo == null)
                    bmp2 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.acclogo);
                else
                    bmp2 = Heleprec.logo;
                //f (bmp2 != null) {
                if (bmp2!=null) {
                    ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
                    bmp2.compress(Bitmap.CompressFormat.PNG, 100, stream2);
                    Image image2 = Image.getInstance(stream2.toByteArray());
                    image2.setAbsolutePosition(width - 500, height - 100);
                    image2.scaleToFit(100, 100);
                    image2.scaleAbsolute(100, 100);
                    // image2.scaleToFit(250f, 250f);
                    // image2.setAlignment(Image.LEFT);
                    //  document.add(image2);
                }
                Log.e("s2", Heleprec.update + "");
                // createHeadings(cb, width - 350, height - 70, labName);
                // createPrimary(cb, width - 350, height - 90, labAddressLine2);
                document.add(addHeader(docWriter));
                // createPrimary(cb, width - 250, height - 100, labAddressLine2);
            }
            addHorizontal_line(document);
//String my_pri="The text doesn’t appear to move from the desired position, instead, the text moves from some offset value. To solve this I have used two variable finalX and finaly. Now I am calculating the distance from the point to corner and adding the extra offset value to finalX and finalY. So this is how I achieve perfect image on text feature with point accuracy in Phimpme-Android";
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
            header_vari = 1;
            ArrayList<ApprovedReportTestDetails> mSubTestDetails = list.getTest_details();
            // TablePatientTest patientTest = new TablePatientTest(mContext);
            //  patientTest.getallPatientTest(mSubTestDetails, appointmentID);
            Gson g = new Gson();
            Log.e("test details gson", g.toJson(mSubTestDetails));
            mSubTestDetails1 = mSubTestDetails;
            map = new HashMap<>();
            pri_map = new HashMap<>();
            inti_map = new HashMap<>();
            // ArrayList<SubTestDetails> subtest=new ArrayList<>();
            for (ApprovedReportTestDetails list1 : mSubTestDetails1) {
                // subtest.add(list) ;
                map.put(list1.getTest_head(), list1.getTest_head());
                // else   Log.e("no","ho");
            }
            ArrayList<String> all_package = new ArrayList<>();
            for (String key : map.keySet()) {
                //Log.d("EXception  JS6767=", key.toString());
                all_package.add(key);
            }
            //Log.e("no of pakage ", all_package.size() + "");
            for (int i = 0; i < all_package.size(); i++) {
                for (int j = 0; j < mSubTestDetails1.size(); j++) {
                    if (all_package.get(i).equals(mSubTestDetails1.get(j).getTest_head())) {
                        if (!mSubTestDetails1.get(j).getTest_precautions().equals("")) {
                            pri.add(mSubTestDetails1.get(j).getTest_precautions());
                        }
//
// else pri.add(my_pri);
                        if (!mSubTestDetails1.get(j).getTest_interpretation().equals("")) {
                            inti.add(mSubTestDetails1.get(j).getTest_interpretation());
                        }
                        //  else inti.add(my_pri);
                    }
                }
                if (pri.size() > 0)
                    pri_map.put(all_package.get(i), pri);
                if (inti.size() > 0)
                    inti_map.put(all_package.get(i), inti);
                pri = new ArrayList<>();
                inti = new ArrayList<>();
            }
            //mSubTestDetails.clear();
            ArrayList<ApprovedReportTestDetails> li2 = new ArrayList<>();
            Set<String> key = map.keySet();
             listTestHead = new HashMap<>();
            for (String key1 : key) {
                //  map.
                //String current_pack=key.;

                for (int j = 0; j < mSubTestDetails.size(); j++) {
                    if (mSubTestDetails.get(j).getTest_head().equals(key1)) {
                        Log.d("EXception  JS2323=", key1.toString());
                        li2.add(mSubTestDetails.get(j));
                        if (!mSubTestDetails.get(j).getTest_precautions().equals(""))
                        testHeadPrecation.put(mSubTestDetails.get(j).getTest_head(),mSubTestDetails.get(j).getTest_precautions());
                        // mSubTestDetails.add(mSubTestDetails.get(j));
                    }
                }
            }
            mSubTestDetails = li2;
            Log.e("size of map", mSubTestDetails.size() + "");
            if (!mSubTestDetails.isEmpty()) {
                Paragraph p = new Paragraph();
                String packageName = mSubTestDetails.get(0).getTest_head();
                String subTestHead = mSubTestDetails.get(0).getSubTest_head();
                Log.d("PKG NAME =", packageName);
                addPackageName(packageName,subTestHead, document);

                addEmptyLine(document, new Paragraph(), 1);
                PdfPTable pdfPTable = null;
                if (packageName.contains("SEROLOGY TEST")) {
                    pdfPTable = createTwoColumnTable();
                } else {
                    Log.d("PKG Create TBL =", "CreateTAble");
                      pdfPTable = createTable();
/*
                    if (!tableType) {
                        Log.d("PKG NAME 90 =", packageName);
                        pdfPTable = createTable();
                    }
                    else {}*/
                }
                //Log.d("PKG NAME PDF TBL =", pdfPTable.toString());

                total_page = mSubTestDetails.size();
                for (ApprovedReportTestDetails subTestDetails : mSubTestDetails) {
                    Log.d("PKG NAME COUNT FOR =", "CUONT");
                    // pdfPTable = createTableBlank();

                    if (!subTestDetails.getTest_head().equalsIgnoreCase(packageName)) {
                        packageName = subTestDetails.getTest_head();
                        subTestHead = subTestDetails.getSubTest_head();
                        Log.d("PKG NAME TEST NAME =", subTestDetails.getTest_name());
                        document.add(pdfPTable);
//                        ArrayList<String> l1=pri_map.get(packageName);
//                        // if (ArrayList<String> l1=pri_map.put(packageName))
//                        if (l1!=null)
//                            document.add(addPri_inter(pri_map.get(packageName),0));
//                        ArrayList<String> l2=inti_map.get(packageName);
//                        if (l2!=null)
//                            document.add(addPri_inter(inti_map.get(packageName),1));
//                        // document.add(new AreaBrea(AreaBreakType.NEXT_PAGE));
                        document.newPage();
                        // document.add(addHeader(docWriter));
                        if (header_vari == 1) {
                            document.add(addHeader(docWriter));
                            addHorizontal_line(document);
                            LineSeparator separator11 = new LineSeparator();
                            //  separator.setDash(10);
                            // separator3.setGap(0);
                            separator11.setLineWidth(4);
                            separator11.setPercentage(80);
                            separator11.setAlignment(Element.ALIGN_CENTER);
                            separator11.setLineColor(BaseColor.DARK_GRAY.brighter());
                            Chunk linebreak11 = new Chunk(separator11);
                            document.add(linebreak11);
                            document.add(adduserDetails(registerPatients));
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
                        addPackageName( packageName, subTestHead,document);
                        addEmptyLine(document, new Paragraph(), 1);
                        if (packageName.contains("SEROLOGY TEST")) {
                            pdfPTable = createTwoColumnTable();
                        } else {
                            Log.d("PKG NAME TEST NAME22 =", "Create TAble called");
                            pdfPTable = createTable();

                        }
                    }

                    Log.d("PKG NAME TEST NAME22 =", subTestDetails.getTest_name());
                    if (packageName.contains("SEROLOGY TEST")) {
                        addCellTwoColumn(pdfPTable, subTestDetails);
                    } else {
                        //pdfPTable = createTableBlank();
                        //pdfPTable = createTable();
                        if (listTestHead.containsKey(subTestDetails.getTest_head())) {
                            Log.d("PKGJS 11=", subTestDetails.getTest_head());
                            if (!subTestDetails.getTest_precautions().equals("")) {
                                if (!listTestHead.containsValue(subTestDetails.getTest_id())) {
                                    pdfPTable = createTable();
                                    Log.d("PKG TEST ELSE44 =", subTestDetails.getTest_id());
                                    precause = true;
                                }

                            } else if (precause) {
                               // pdfPTable = createTable();
                                Log.d("PKGJS 44=", subTestDetails.getTest_head());
                            } else {
                                Log.d("PKGJS 55=", subTestDetails.getTest_head());
                                //pdfPTable = createTable();
                                precause = false;
                            }
                        }
                        else {
                            precause = false;
                            Log.d("PKG TEST ELSE33 =", subTestDetails.getTest_id());
                            listTestHead.put(subTestDetails.getTest_head(),subTestDetails.getTest_id());
                                //pdfPTable = createTable();
                                Log.d("PKGJS 33=", subTestDetails.getTest_head());
                        }
                        addCell(pdfPTable, subTestDetails, patientGender);
//                        if (tableType) {
//                            pdfPTable = createTableBlank();
//                            addCell(pdfPTable, subTestDetails, patientGender);
//                        }
//                        else {
//                            tableType = true;
//
//                            Log.d("PKG NAME TEST102 =", "TEst");
//                            addCell(pdfPTable, subTestDetails, patientGender);
//                            pdfPTable = createTableBlank();
//                        }
                    }
                }
                if (!precause) {

                    document.add(pdfPTable);
                    Log.e("PKGJS = no of time", "table");
                }

                addEmptyLine(document, new Paragraph(), 1);
                document.add(p);
            }
            Paragraph paragraph1 = new Paragraph();
            addEmptyLine(document, paragraph1, 1);
            document.add(paragraph1);
            // addPackageName("----END OF THE REPORT----",document);
            Font red = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.RED);
            document.close();
        } catch (Exception e) {
            Log.d("EXception  JS=", e.getMessage());
            //ToastUtils.showShortToastMessage(mContext, e.toString());
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
                ToastUtils.showShortToastMessage(mContext, "No Application found to open file");
            } catch (Exception e) {
                ToastUtils.showShortToastMessage(mContext, mContext.getString(R.string.server_error));
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
            Toast.makeText(mContext, "Can't read pdf file", Toast.LENGTH_SHORT).show();
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
            ToastUtils.showShortToastMessage(mContext, e.toString());
        }
    }
    public PdfPTable addPri_inter(ApprovedReportTestDetails list, int k) throws DocumentException {
        float[] columnWidths = {20f};
        PdfPTable table = new PdfPTable(columnWidths);
        //create PDF table with the given widths
        addPackageName("Precaution & Interpretations", "" , document);
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
        Paragraph para1 = new Paragraph(+1 + ":" + list.getTest_precautions(), font1);
        // Log.e(list.get(i),"name");
        cell.addElement(para1);
        table.addCell(cell);
        //}
        if (!list.getTest_interpretation().equals("")) {
            // Font font1 = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            Paragraph para2 = new Paragraph("interpretation :", font);
            PdfPCell cell2 = new PdfPCell();
            cell2.setBorder(Rectangle.NO_BORDER);
            cell2.addElement(para2);
            table.addCell(cell2);
            PdfPCell cell3 = new PdfPCell();
            cell3.setBorder(Rectangle.NO_BORDER);
            Font font2 = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL);
            Paragraph para3 = new Paragraph(+1 + ":" + list.getTest_interpretation(), font2);
            //  Log.e(list.get(i),"name");
            cell3.addElement(para3);
            table.addCell(cell3);
        }
        pritable = table;
        document.add(table);
        return table;
    }
    private PdfPTable adduserDetails(RegisterPatient registerPatients) {
        Gson g = new Gson();
        Log.e("user gson", g.toJson(registerPatients));
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
        Paragraph para1 = new Paragraph("MOBILE : ", f2);
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
        Paragraph p2 = new Paragraph(registerPatients.getUserregistration_mobile_number(), font2);
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
        // para.setLeading(0, 1);
        //p1.setFont(boldFont);
        PdfPCell c7 = new PdfPCell(para6);
        //c7.setBorderWidthBottom(0f);
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
        Paragraph para7 = new Paragraph("EMAIL ID : ", f8);
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
        Font font8 = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
        Paragraph p8 = new Paragraph(registerPatients.getUserregistration_email_address(), font8);
        //   p1.setFont(font);
        PdfPCell Cell8 = new PdfPCell(p8);
        Cell8.setBorder(Rectangle.NO_BORDER);
        Cell8.setBorderWidthLeft(0f);
        Cell8.setBorderWidthTop(0f);
        Cell8.setVerticalAlignment(Element.ALIGN_CENTER);
        Cell8.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(Cell8);
        return table;
    }

    private PdfPTable createTableBlank() {
        float[] columnWidths = {5f, 5f, 5f, 5f};
        //create PDF table with the given widths
        PdfPTable table = new PdfPTable(columnWidths);
        //table.(new SolidBorder(1));
        // set table width a percentage of the page width
        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        table.setTotalWidth(950f);
        Font font = new Font(Font.FontFamily.HELVETICA, 0, Font.BOLD);
        Paragraph para = new Paragraph("", font);
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
        //    p2.setFont(boldFont);
        para = new Paragraph("", font);
        cell = new PdfPCell(para);
        //  cell.addElement(para);
        cell.setBorder(Rectangle.NO_BORDER);
        // cell.setRightIndent(10);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell);
        //  Phrase p3=new Phrase("Units");
        // p3.setFont(boldFont);
        para = new Paragraph("", font);
        cell = new PdfPCell(para);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        // cell.addElement(para);
        cell.setBorder(Rectangle.NO_BORDER);
        //cell.setRightIndent(10);
        table.addCell(cell);
        //   Phrase p4=new Phrase("Normal Range");
        // p4.setFont(boldFont);
        para = new Paragraph("", font);
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
    private PdfPTable createTable() {
        float[] columnWidths = {5f, 5f, 5f, 5f};
        //create PDF table with the given widths

        PdfPTable table = new PdfPTable(columnWidths);
        //table.(new SolidBorder(1));
        // set table width a percentage of the page width
        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        table.setTotalWidth(950f);
        Font font = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        Paragraph para = new Paragraph("Test", font);
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
        Log.d("PKG NAME TEST NAME 101=", "");
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
    public void addCell(PdfPTable pdfPTable, ApprovedReportTestDetails subTestDetails, String patientGender) throws DocumentException {


        if (!subTestDetails.getTest_precautions().equals("")) {
            pdfPTable = createTable();
            Log.d("PKG TEST hello TEst =", subTestDetails.getTest_id());
        }
        else {
            if (precause){
                //Log.d("PKG TEST ELSE11 =", subTestDetails.getTest_id());
                if (!listTestHead.containsValue(subTestDetails.getTest_id())) {
                   // precause = false;
                    //pdfPTable = createTable();
                    Log.d("PKG TEST ELSE hello  =", subTestDetails.getTest_id());
                }
                // precause = false;
            }

            else {
            // for (int k = 0;k<testHeadPrecation.size();k++){
                 if (testHeadPrecation.containsKey(subTestDetails.getTest_head())){
                     pdfPTable = createTable();
                 }
             //}



            }
            }

        Font font = new Font(Font.FontFamily.HELVETICA, 8);
        Paragraph p1 = new Paragraph(subTestDetails.getTest_name(), font);
        Log.d("PKG_PDF_NAME=", subTestDetails.getTest_name());
        Log.d("PKG_PDF_ID=", subTestDetails.getTest_id());
        //   p1.setFont(font);
        PdfPCell Cell1 = new PdfPCell(p1);
        Cell1.setBorder(Rectangle.NO_BORDER);
        Cell1.setVerticalAlignment(Element.ALIGN_CENTER);
        Cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
        pdfPTable.addCell(Cell1);
        if (subTestDetails.is_manual_status()) {
            try {
                float result = Float.parseFloat(subTestDetails.getResult().replaceAll("[^0-9.]", ""));
                if (subTestDetails.getResult().contains("/")) {
                    Paragraph p2 = new Paragraph(subTestDetails.getResult(), font);
                    PdfPCell celltwo = new PdfPCell(p2);
                    celltwo.setHorizontalAlignment(Element.ALIGN_LEFT);
                    celltwo.setBorder(Rectangle.NO_BORDER);
                    celltwo.setVerticalAlignment(Element.ALIGN_CENTER);
                    celltwo.setRightIndent(10);
                    pdfPTable.addCell(celltwo);
                    Log.d("PKG NAME TEST NAME 33=", subTestDetails.getTest_name());
                } else {
                    Log.d("PKG NAME TEST NAME 44=", subTestDetails.getTest_name());

                    Paragraph p2 = new Paragraph(subTestDetails.getResult(), font);
                    PdfPCell celltwo = new PdfPCell(p2);
                    celltwo.setHorizontalAlignment(Element.ALIGN_LEFT);
                    celltwo.setVerticalAlignment(Element.ALIGN_CENTER);
                    celltwo.setBorder(Rectangle.NO_BORDER);
                    celltwo.setRightIndent(10);
                    pdfPTable.addCell(celltwo);
                    //addNumericReult(pdfPTable, subTestDetails);
                }
            } catch (Exception e) {
                Log.d("PKG NAME TEST NAME 55=", subTestDetails.getTest_name());
                Paragraph p2 = new Paragraph(subTestDetails.getResult(), font);
                PdfPCell celltwo = new PdfPCell(p2);
                celltwo.setHorizontalAlignment(Element.ALIGN_LEFT);
                celltwo.setVerticalAlignment(Element.ALIGN_CENTER);
                celltwo.setBorder(Rectangle.NO_BORDER);
                celltwo.setRightIndent(10);
                pdfPTable.addCell(celltwo);
            }
        } else {
            Log.d("PKG NAME TEST NAME 66=", subTestDetails.getTest_name());
            //addNumericReult(pdfPTable, subTestDetails);
            //  Log.e("not manual","you done sachin");


            Paragraph p2 = new Paragraph(subTestDetails.getResult(), font);
            PdfPCell celltwo = new PdfPCell(p2);
            celltwo.setHorizontalAlignment(Element.ALIGN_LEFT);
            celltwo.setVerticalAlignment(Element.ALIGN_CENTER);
            celltwo.setBorder(Rectangle.NO_BORDER);
            celltwo.setRightIndent(10);
            pdfPTable.addCell(celltwo);
        }
        p1 = new Paragraph(subTestDetails.getUnit(), font);
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
        PdfPCell gd1 = pdfPTable.addCell(Cell3);
        Log.d("PKG TEST=", subTestDetails.getTest_precautions());
        //document.add(pdfPTable);
        if (!subTestDetails.getTest_precautions().equals("")) {
            Log.d("PKG TEST IFFNN =", subTestDetails.getTest_precautions());
            precause = true;
            document.add(pdfPTable);
            addPri_inter(subTestDetails, 0);
           this.pdfPTable = createTable();
        }
        else {
            if (precause){
                //Log.d("PKG TEST ELSE11 =", subTestDetails.getTest_id());
                if (!listTestHead.containsValue(subTestDetails.getTest_id())) {
                    precause = false;
                   // document.add(pdfPTable);
                    Log.d("PKG TEST ELSE22  =", subTestDetails.getTest_id());
                }
               // precause = false;
            }

            else {
                if (testHeadPrecation.containsKey(subTestDetails.getTest_head())){
                    document.add(pdfPTable);
                }

            }
        }

    }
    public void addCellTwoColumn(PdfPTable pdfPTable, ApprovedReportTestDetails subTestDetails) throws DocumentException {
        Font font = new Font(Font.FontFamily.HELVETICA, 8);
        PdfPCell cell = new PdfPCell(new Paragraph(subTestDetails.getTest_name(), font));
        Log.d("PKG NAME TEST NAME 77=", subTestDetails.getTest_name());
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setRightIndent(10);
        pdfPTable.addCell(cell);
        String result = subTestDetails.getResult()
                .replaceAll("[^0-9.]", "");
        //  Log.e("result ram",result);
        PdfPCell celltwo = new PdfPCell(new Paragraph(subTestDetails.getResult(), font));
        Log.e(subTestDetails.getResult(), "result");
        celltwo.setHorizontalAlignment(Element.ALIGN_LEFT);
        celltwo.setVerticalAlignment(Element.ALIGN_CENTER);
        celltwo.setBorder(Rectangle.NO_BORDER);
       celltwo.setRightIndent(10);
        pdfPTable.addCell(celltwo);
        //document.add(pdfPTable);
        if (!subTestDetails.getTest_precautions().equals("")) {
            document.add(pdfPTable);
            addPri_inter(subTestDetails, 0);
            Log.d("PKG NAME 94 =", "packageName");
            this.pdfPTable = createTable();
        }
    }
    public void addPackageName( String packageName, String subTestHead, Document document) {
        Font f = new Font(Font.FontFamily.TIMES_ROMAN, 14.0f, Font.BOLD, BaseColor.BLACK);
        Paragraph pa = new Paragraph(packageName, f);
        Paragraph pa_sub = new Paragraph(subTestHead, f);
        pa.setAlignment(Paragraph.ALIGN_CENTER);
        pa_sub.setAlignment(Paragraph.ALIGN_CENTER);
        try {
            document.add(pa);
            document.add(pa_sub);
        } catch (DocumentException e) {
            ToastUtils.showShortToastMessage(mContext, e.toString());
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
            header.getDefaultCell().setFixedHeight(60);
            header.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            //  header.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);
            ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
//                    Bitmap scaled = Bitmap.createScaledBitmap(bmp2, 150, 100, true);
//Log.d("BITMAP VALUE+",""+bmp2);
            if (bmp2 != null) {
                bmp2.compress(Bitmap.CompressFormat.PNG, 100, stream2);
                Image image2 = Image.getInstance(stream2.toByteArray());
//                    image2.setAbsolutePosition(0, height - 100);
                image2.scaleToFit(100, 100);
                image2.scaleAbsolute(100, 100);
                image2.setPaddingTop(200f);
                header.addCell(image2);
            }
            PdfPCell text = new PdfPCell();
            text.setPaddingLeft(10);
            text.setPaddingTop(0);
            text.setHorizontalAlignment(Element.ALIGN_RIGHT);
            text.setBorder(Rectangle.NO_BORDER);
            text.setBorderColor(BaseColor.LIGHT_GRAY);
            text.addElement(new Paragraph(labName, new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD)));
            text.addElement(new Phrase(labAddressLine1, new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
            header.addCell(text);
            return header;
        } catch (DocumentException de) {
            throw new ExceptionConverter(de);
        } catch (MalformedURLException e) {
            throw new ExceptionConverter(e);
        } catch (IOException e) {
            throw new ExceptionConverter(e);
        }
    }
    public PdfPTable adddFooter() {
        float[] columnWidths = {5f, 15f};
        PdfPTable header = new PdfPTable(2);
        header.setHorizontalAlignment(Element.ALIGN_CENTER);
        try {
            // set defaults
            header.setWidths(new int[]{6, 24});
            header.getDefaultCell().setFixedHeight(60);
            header.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            //  header.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);
            ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
            bmp2.compress(Bitmap.CompressFormat.PNG, 100, stream2);
            Image image2 = Image.getInstance(stream2.toByteArray());
//                    image2.setAbsolutePosition(0, height - 100);
            image2.scaleToFit(100, 100);
            image2.scaleAbsolute(100, 100);
//
            header.addCell(image2);
            PdfPCell text = new PdfPCell();
            text.setPaddingLeft(10);
            text.setHorizontalAlignment(Element.ALIGN_RIGHT);
            text.setBorder(Rectangle.NO_BORDER);
            text.setBorderColor(BaseColor.LIGHT_GRAY);
            text.addElement(new Phrase(labName, new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD)));
            text.addElement(new Phrase(labAddressLine1, new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
            header.addCell(text);
            return header;
        } catch (DocumentException de) {
            throw new ExceptionConverter(de);
        } catch (MalformedURLException e) {
            throw new ExceptionConverter(e);
        } catch (IOException e) {
            throw new ExceptionConverter(e);
        }
    }
    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        super.onEndPage(writer, document);
        Paragraph footer1 = new Paragraph("----END OF THE REPORT----");
        //footer.Alignment = Element.ALIGN_RIGHT;
        footer1.setAlignment(Element.ALIGN_LEFT);
        PdfPTable footerTbl1 = new PdfPTable(1);
        footerTbl1.setTotalWidth(900f);
        footerTbl1.setHorizontalAlignment(Element.ALIGN_CENTER);
        PdfPCell cel = new PdfPCell();
        cel.addElement(new Phrase("----END OF THE REPORT----", new Font(Font.FontFamily.TIMES_ROMAN, 14.0f, Font.BOLD, BaseColor.BLACK)));
        // cell1.addElement(new Phrase(current_page + " of " + total_page, new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
        cel.setBorder(Rectangle.NO_BORDER);
        // cell.PaddingLeft = 10;
        cel.setPaddingLeft(10);
        footerTbl1.addCell(cel);
        footerTbl1.writeSelectedRows(0, -1, 200, 215, writer.getDirectContent());
        Paragraph footer = new Paragraph("");
        //footer.Alignment = Element.ALIGN_RIGHT;
        footer.setAlignment(Element.ALIGN_RIGHT);
        PdfPTable footerTbl = new PdfPTable(1);
        // footerTbl.se(6);
        //er.setWidths(new int[]{6, 24});
        footerTbl.setTotalWidth(100f);
        footerTbl.setLockedWidth(true);
        footerTbl.setHorizontalAlignment(Element.ALIGN_CENTER);
        // footerTbl.writeSelectedRows(0, -1, 0, 250, writer.getDirectContent());
        PdfPCell cell = new PdfPCell();
        ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
        Heleprec.sinature.compress(Bitmap.CompressFormat.PNG, 100, stream2);
        Image image2 = null;
        try {
            image2 = Image.getInstance(stream2.toByteArray());
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } // image2 = Image.getInstance(stream2.toByteArray());
        // image2.setAbsolutePosition(width-500, height - 100);
        image2.scaleToFit(50, 25);
        image2.scaleAbsolute(50, 25);
        PdfPCell cell1 = new PdfPCell();
        cell1.addElement(new Phrase("Pathalogist : "));
        //cell1.addElement(new Phrase("    "+writer.getPageNumber(), new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
        PdfPTable footerTbl3 = new PdfPTable(1);
        // footerTbl.se(6);
        //er.setWidths(new int[]{6, 24});
        footerTbl3.setTotalWidth(100f);
        footerTbl.setLockedWidth(true);
        footerTbl3.setHorizontalAlignment(Element.ALIGN_CENTER);
        PdfPCell cell2 = new PdfPCell();
        // cell2.setVerticalAlignment(Element.ALIGN_RIGHT);
        //cell2.addElement(new Phrase("       (--------------------)", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD)));
        // cell2.addElement(new Phrase(current_page +"", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
        // cell2.addElement(new Phrase("(------------------)", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD)));
        cell2.addElement(new Phrase(current_page + "", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
        //cell2.setPaddingBottom(10);
        Log.e("logo= PDF2=", "selectedReportStatus" + selectedReportStatus);
        if (selectedReportStatus)
            cell.addElement(image2);
        //cell2.addElement(new Phrase(""+current_page  , new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
        cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell2.setPaddingLeft(90);
        cell2.setPaddingBottom(90);
        cell2.setBorder(Rectangle.NO_BORDER);
        // cell1.setBorder(Rectangle.BOX);
        cell1.setBorderWidthTop(0);
        cell1.setBorderWidthRight(0);
        cell1.setBorderWidthLeft(0);
        cell1.setBorderWidthBottom(0);
        // cell1.setBorderWidthTop(1f);
        // cell1.addElement(new Phrase("(------------------------)", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD)));
        // cell.Border = 0;
        current_page = current_page + 1;
        cell.setPaddingLeft(10);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
        footerTbl.addCell(cell1);
        footerTbl.addCell(cell);
        footerTbl3.addCell(cell2);
        footerTbl.writeSelectedRows(0, -1, 415, 165, writer.getDirectContent());
        footerTbl3.writeSelectedRows(0, -1, 440, 130, writer.getDirectContent());
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
    public void addHorizontal_line(Document document) throws DocumentException {
        addEmptyLine(document, new Paragraph(), 1);
        LineSeparator separator3 = new LineSeparator();
        //  separator.setDash(10);
        // separator3.setGap(0);
        separator3.setLineWidth(2);
        separator3.setLineColor(BaseColor.DARK_GRAY.brighter());
        Chunk linebreak3 = new Chunk(separator3);
        document.add(linebreak3);
        Paragraph p1 = new Paragraph();
        p1.setAlignment(Element.ALIGN_CENTER);
        document.add(p1);
    }
    @Override
    public void onStartPage(PdfWriter writer, Document document) {
        super.onStartPage(writer, document);
//        if (header_vari==1) {
//            try {
//               // document.add(addHeader(docWriter));
//            } catch (DocumentException e) {
//                e.printStackTrace();
//            }
//        }
    }
    public void addNumericReult(PdfPTable pdfPTable, ApprovedReportTestDetails subTestDetails) {
        Font font = new Font(Font.FontFamily.HELVETICA, 8);
        try {
            boolean b = false;
            float result = 0;
            try {
                result = Float.parseFloat(subTestDetails.getResult().replaceAll("[^0-9.]", ""));
            } catch (Exception e) {
                result = 0;
                Log.e("it is exception", "alfhanumeric");
            }
//
            if (patientGender.equalsIgnoreCase("Male")) {
                String a = subTestDetails.getTest_upper_bound_male();
                if (a != null && !a.equals("")) {
                    float tub = Float.parseFloat(a);
                    if (result > tub)
                        b = true;
                }
            } else if (patientGender.equalsIgnoreCase("Female")) {
                String a = subTestDetails.getTest_upper_bound_female();
                String lower = subTestDetails.getTest_low_bound_female();
                if (a != null && !a.equals("") & lower != null && !lower.equals("")) {
                    float tub = Float.parseFloat(a);
                    float low = Float.parseFloat(lower);
                    if (result > tub || result < low)
                        b = true;
                }
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
                Font boldFont = new Font(Font.FontFamily.HELVETICA, 8);
                //  pdfPTable.addCell(Html.toHtml(Html.fromHtml(reult)));
                PdfPCell pdfWordCell = new PdfPCell();
                // Phrase firstLine = new Phrase(subTestDetails.getResult().replaceAll("[^0-9.]", ""), boldFont);
                Phrase firstLine = new Phrase(subTestDetails.getResult());
                pdfWordCell.setBorder(Rectangle.NO_BORDER);
                pdfWordCell.addElement(firstLine);
                pdfPTable.addCell(pdfWordCell);
                Log.e("TESTJK is exception", "alfhanumeric");
            } else {
                //Paragraph     p1 = new Paragraph(subTestDetails.getResult().replaceAll("[^0-9.]", ""), font);
                Paragraph p1 = new Paragraph(subTestDetails.getResult());
                p1.setFont(font);
                PdfPCell pdfWordCell = new PdfPCell(p1);
                pdfWordCell.setVerticalAlignment(Element.ALIGN_CENTER);
                pdfWordCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                pdfWordCell.setBorder(Rectangle.NO_BORDER);
                pdfPTable.addCell(pdfWordCell);
                Log.e("TESTJK 22 is exception", "alfhanumeric");
            }
        } catch (Exception e) {
            Log.e("error", e.getMessage());
        }
    }
}
