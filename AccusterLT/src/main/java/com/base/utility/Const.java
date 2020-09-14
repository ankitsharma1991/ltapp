package com.base.utility;

/**
 * Created by appideas on 30/8/16.
 */
public class Const {

    public static String PREF_NAME = "Accuster";
    public static final String ERROR_CODE_PREFIX = "error_";
    public static final String MANUAL = "manual";
    public static final String URL = "url";
    public static final int CHOOSE_PHOTO = 112;
    public static final int TAKE_PHOTO = 113;
    public static final int REQUEST_CODE_DOC = 114;

    public class ServiceType {

        private static final String HOST_URL = "http://lob.the-appideas.in/";
        private static final String BASE_URL = HOST_URL + "api/";
        public static final String REGISTER = BASE_URL + "Signup_api";
        public static final String LOGIN = BASE_URL + "Signup_api/login";
        public static final String IMAGE_PATH = HOST_URL + "uploads/";
        public static final String CREATE_A_CAMP = BASE_URL + "Add_camp";
        public static final String ADD_PATIENT = BASE_URL + "Add_patient";

    }

    public class ServiceCode {
        public static final int REGISTER = 1;
        public static final int LOGIN = 2;
        public static final int CREATE_A_CAMP = 3;
        public static final int ADD_PATIENT = 4;

    }


    public class Params {

        public static final String MOBILE = "phone_number";
        public static final String EMAIL = "email";
        public static final String NAME = "name";
        public static final String USER_TYPE = "user_type";
        public static final String PROFILE_NAME = "profile_img";
        public static final String PASSWORD = "password";
        public static final String ORGANIZATION_NAME = "organization_name";
        public static final String CAMP_NAME = "camp_name";
        public static final String CAMP_ADDRESS = "camp_address";
        public static final String CAMP_START_DATE = "start_date";
        public static final String CAMP_END_DATE = "end_date";
        public static final String CAMP_LOGO = "logo_image";
        public static final String CAMP_VENUE = "camp_description";
        public static final String CAMP_PRODUCT_NAME = "product_details";
        public static final String CAMP_PATH_NAME = "pathologist_name";
        public static final String CAMP_DEGREE = "degree";
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";

        //Add patient
        public static final String LT_ID = "LT_id";
        public static final String PATIENT_UID = "patient_uid";
        public static final String CAMP_ID = "camp_id";
        public static final String GENDER = "gender";
        public static final String PREGNANT_STATUS = "pregnant_status";
        public static final String AGE = "age";
        public static final String ID_PROOF = "id_proof";
        public static final String ID_NUMBER = "id_number";
        public static final String DIET = "diet";
        public static final String MEDICAL_HISTORY = "medical_history";
        public static final String MEDICAL_HISTORY_TYPE = "medical_history_type";
        public static final String LEVEL_ID = "level_id";
        public static final String PATIENT_TESTS = "patient_tests";
        public static final String STATUS = "status";


    }
}
