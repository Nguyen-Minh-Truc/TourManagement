package com.J2EE.TourManagement.Util;

import java.text.NumberFormat;
import java.util.Locale;

public class Utils {
    public static String formatVND(double amount) {
        NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));
        return nf.format(amount);
    }
}
