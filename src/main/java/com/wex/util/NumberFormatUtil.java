package com.wex.util;

import java.text.DecimalFormat;
import java.util.Locale;

public class NumberFormatUtil {

    public static double doubleDecimalFormat(Double value){
        DecimalFormat df = new DecimalFormat("0.00");
        return Double.parseDouble(df.format(value).replace(",","."));
    }
}
