package com.accusterltapp.service;

import retrofit2.Call;


/**
 * Created by Sumit on 12-03-2016.
 */
@SuppressWarnings("unchecked")
public class ServiceSelector {
    public Call selectMethod(Controller controller, BaseNetworkRequest baseNetworkRequest) {
        if (baseNetworkRequest.requestType.equalsIgnoreCase(ApiConstant.CAMP_LIST)) {
            return controller.mNetworkInterface.campList();
        } else if (baseNetworkRequest.requestType.equalsIgnoreCase(ApiConstant.REPORT_LIST)) {
            return controller.mNetworkInterface.reportList();
        } else if (baseNetworkRequest.requestType.equalsIgnoreCase(ApiConstant.PATIENT_LIST)) {
            return controller.mNetworkInterface.userList();
        } else if (baseNetworkRequest.requestType.equalsIgnoreCase(ApiConstant.REPORT_LIST_BYUSER)) {
            return controller.mNetworkInterface.reportListUser(baseNetworkRequest.bodyParams);
        } else if (baseNetworkRequest.requestType.equalsIgnoreCase(ApiConstant.PATIENT_LIST_BYCAMP)) {
            return controller.mNetworkInterface.userListCamp(baseNetworkRequest.bodyParams);
        } else if (baseNetworkRequest.requestType.equalsIgnoreCase(ApiConstant.USER_LOGIN)) {
            return controller.mNetworkInterface.userLogin(baseNetworkRequest.bodyParams);
        } else if (baseNetworkRequest.requestType.equalsIgnoreCase(ApiConstant.REPORT_UPDATE_BY_USER_ID)) {
            return controller.mNetworkInterface.updateReport(baseNetworkRequest.bodyParams);
        } else if (baseNetworkRequest.requestType.equalsIgnoreCase(ApiConstant.ADD_PATIENT)) {
            return controller.mNetworkInterface.addPatient(baseNetworkRequest.bodyParams);
        } else if (baseNetworkRequest.requestType.equalsIgnoreCase(ApiConstant.GET_TEST_LIST)) {
            return controller.mNetworkInterface.getAllTest();
        } else if (baseNetworkRequest.requestType.equalsIgnoreCase(ApiConstant.CREATE_CAMP)) {
            return controller.mNetworkInterface.createCamp(baseNetworkRequest.bodyParams);
        } else if (baseNetworkRequest.requestType.equalsIgnoreCase(ApiConstant.PACKAGE_LIST)) {
            return controller.mNetworkInterface.getAllPackage(baseNetworkRequest.bodyParams);
        } else if (baseNetworkRequest.requestType.equalsIgnoreCase(ApiConstant.CAMP_SYN)) {
            return controller.mNetworkInterface.synCamp(baseNetworkRequest.bodyParams);
        } else if (baseNetworkRequest.requestType.equalsIgnoreCase(ApiConstant.PATIENT_SYN)) {
            return controller.mNetworkInterface.patientSyn(baseNetworkRequest.bodyParams);
        } else if (baseNetworkRequest.requestType.equalsIgnoreCase(ApiConstant.REPORT_SYN)) {
            return controller.mNetworkInterface.reportSyn(baseNetworkRequest.bodyParams);
        } else if (baseNetworkRequest.requestType.equalsIgnoreCase(ApiConstant.PACKAGE_SYNC)) {
            return controller.mNetworkInterface.packageSyn(baseNetworkRequest.bodyParams);
        }else if (baseNetworkRequest.requestType.equalsIgnoreCase(ApiConstant.IMAGE_SYNC)) {
            return controller.mNetworkInterface.uploadAllFiles(baseNetworkRequest.mParts);
        }
        return null;
    }
}