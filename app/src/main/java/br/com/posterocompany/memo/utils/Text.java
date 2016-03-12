package br.com.posterocompany.memo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Francisco on 12/03/2016.
 */
public class Text {
    public static String reduceTextIn(String text, int length) {

        if (text.length() > length) {

            text = text.substring(0, length);

            String[] words = text.split("\\s+");

            if (words.length > 0) {
                String lastWord = words[words.length - 1];
                text = text.substring(0, text.length() - lastWord.length() - 1);
            }

            text = text + "...";
        }

        return text;
    }

    public static String toDateTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return dateFormat.format(date);
    }

    public static String toDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }
    public static String toTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        return dateFormat.format(date);
    }
}
