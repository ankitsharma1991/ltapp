package com.base.utility;

import android.text.SpannableString;
import android.text.style.StyleSpan;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by OnSpon on 22/10/16.
 */

public class StringUtils {

    public static  double finalTotal = 0;
    public SpannableString spannedTextFormatter(String text, StyleSpan styleSpan, int start, int end, int flag) {
        SpannableString content = new SpannableString(text);
        content.setSpan(styleSpan, start, end, flag);

        return content;
    }

    public static boolean isNullOrEmpty(final String pStr) {
        return pStr == null || pStr.trim().length() == 0
                || pStr.trim().equalsIgnoreCase("null");
    }

    static final String EMAIL_PATTERN = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";

    public static boolean isValidEmail(String pEmail) {
        if (isNullOrEmpty(pEmail)) {
            return false;
        }
        Pattern validRegexPattern = Pattern.compile(EMAIL_PATTERN);
        return validRegexPattern.matcher(pEmail).matches();
    }

    public static boolean isValidMobileNumber(String number) {
        if (StringUtils.isNullOrEmpty(number)) {
            return false;
        }
        Pattern pattern = Pattern.compile("[7-9][0-9]{9}");
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }

    public static String removeIndianFormat(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ',') {
                continue;
            }
            str = str + s.charAt(i);
        }
        return str;
    }

    // Formats a number in the Indian style
    public static String indianFormat(String amount) {
        try {
            BigDecimal decimal = new BigDecimal(amount);

            // DecimalFormat formatter = new DecimalFormat("#,###.00");
            DecimalFormat formatter = new DecimalFormat("#,###");
            // we never reach double digit grouping so return
            if (decimal.doubleValue() < 100000) {
                return formatter.format(decimal.setScale(2, 1).doubleValue());
            }
            StringBuffer returnValue = new StringBuffer();
            // Spliting integer part and decimal part
            String value = decimal.setScale(2, 1).toString();
            String intpart = value.substring(0, value.indexOf("."));
            // String decimalpart = value
            // .substring(value.indexOf("."), value.length());
            // switch to double digit grouping
            formatter.applyPattern("#,##");
            returnValue.append(
                    formatter.format(Math.floor(new BigDecimal(intpart)
                            .doubleValue() / 1000))).append(",");
            // appending last 3 digits and decimal part
            // returnValue.append(intpart.substring(intpart.length() - 3,
            // intpart.length())).append(decimalpart);
            returnValue.append(intpart.substring(intpart.length() - 3,
                    intpart.length()));
            // returning complete string
            return returnValue.toString();
        } catch (Exception e) {
            return "0";
        }
    }

    public static String getFormattedValue(String valueString) {
        Double value = 0.0;
        try {
            value = Double.valueOf(valueString);
        } catch (Exception e) {
            value = 0.0;
        }
        String suffix = "";
        double convertedAmount;
        if (value != 0) {
            if (value < 10000) {
                return doubleToIndianFormat(value);
            } else if (value >= 10000 && value < 100000) {
                convertedAmount = value / 1000;
                suffix = "k";
            } else if (value >= 100000 && value < 10000000) {
                convertedAmount = value / 100000;
                suffix = "lakh";
            } else if (value >= 10000000) {
                convertedAmount = value / 10000000;
                suffix = "cr";
            } else {
                convertedAmount = value;
            }
            return new DecimalFormat("#.##").format(convertedAmount) + " " + suffix;
        } else {
            return valueString;
        }
    }

    // Formats a number in the Indian style
    public static String doubleToIndianFormat(Double number) {

        BigDecimal n = new BigDecimal(number);
        DecimalFormat formatter = new DecimalFormat("#,###");
        // we never reach double digit grouping so return
        if (n.doubleValue() < 100000) {
            return formatter.format(n.setScale(2, 1).doubleValue());
        }
        StringBuilder returnValue = new StringBuilder();
        // Spliting integer part and decimal part
        String value = n.setScale(2, 1).toString();
        String intpart = value.substring(0, value.indexOf("."));
        // switch to double digit grouping
        formatter.applyPattern("#,##");
        returnValue.append(
                formatter.format(Math.floor(new BigDecimal(intpart)
                        .doubleValue() / 1000))).append(",");
        // appending last 3 digits and decimal part
        // returnValue.append(intpart.substring(intpart.length() - 3,
        // intpart.length())).append(decimalpart);
        returnValue.append(intpart.substring(intpart.length() - 3,
                intpart.length()));
        // returning complete string
        return returnValue.toString();
    }

    public static String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();

    }

}
