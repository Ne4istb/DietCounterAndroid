package com.ne4istb.my.dietcounter.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class StringHelper {

    public static final boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public static final String formatMoney(Double amount, String currency) {

        String currencySymbol = Currency.getInstance(currency).getSymbol();

        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator('.');
        decimalFormatSymbols.setGroupingSeparator(',');
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", decimalFormatSymbols);

        return String.format("%s %s", currencySymbol, decimalFormat.format(amount));
    }

    public static final String formatDate(Date date) {
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }

    public static final String formatCountry(String code) {
        return new Locale("", code).getDisplayCountry();
    }
}
