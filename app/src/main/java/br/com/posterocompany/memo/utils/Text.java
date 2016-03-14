package br.com.posterocompany.memo.utils;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    public static Spannable getDiffText(String oldText, String newText) {
        Spannable spannable;

        String[] oldWords = oldText.split("\\s");
        String[] newWords = newText.split("\\s");
        ArrayList<String> diffWords = new ArrayList<>();
        ArrayList<Integer> diffWordsIndex = new ArrayList<>();
        ArrayList<Boolean> diffWordsTypeNew = new ArrayList<>();

        String resultText = "";


        for (int i = 0; i < oldWords.length || i < newWords.length; i++) {
            String oldWord;
            String newWord;
            if (oldWords.length <= i) {
                newWord = newWords[i];
                diffWords.add(newWord);
                diffWordsIndex.add(resultText.length());
                diffWordsTypeNew.add(true);
                resultText += newWord + " ";
            } else if (newWords.length <= i) {
                oldWord = oldWords[i];
                diffWords.add(oldWord);
                diffWordsIndex.add(resultText.length());
                diffWordsTypeNew.add(false);
                resultText += oldWord + " ";
            } else {
                newWord = newWords[i];
                oldWord = oldWords[i];
                if (oldWord.equals(newWord)) {
                    resultText += newWord + " ";
                } else {
                    diffWords.add(oldWord);
                    diffWordsIndex.add(resultText.length());
                    diffWordsTypeNew.add(false);
                    resultText += oldWord + " ";

                    diffWords.add(newWord);
                    diffWordsIndex.add(resultText.length());
                    diffWordsTypeNew.add(true);
                    resultText += newWord + " ";
                }
            }
        }

        /*//Limpar repetições de mudanças
        int diffWordsCount = diffWords.size();

        ArrayList<String> dw = new ArrayList<>();
        ArrayList<Integer> dwi = new ArrayList<>();
        ArrayList<Boolean> dwtn = new ArrayList<>();

        for (int i = 0; i < diffWordsCount; i++) {
            if (diffWords.size() > i) {
                String diffWord = diffWords.get(i);

                int index = resultText.indexOf(diffWord);
                int newIndex = resultText.indexOf(diffWord, index + 1);

                if (index + diffWord.length() == newIndex - 1) {

                    String diffPart = resultText.substring(index, index + (diffWord.length() * 2 + 1));

                    resultText = resultText.replace(diffPart, diffWord);


                    if(!dw.contains(diffWord)){
                        dw.add(diffWord);
                        dwi.add(index);
                        dwtn.add(diffWordsTypeNew.get(i));
                    }
//                    diffWords.remove(diffWord);
//                    diffWords.remove(diffWord);
//                    diffWordsIndex.remove(Integer.valueOf(index));
//                    diffWordsIndex.remove(Integer.valueOf(index));
//                    diffWordsTypeNew.remove(i);
//                    diffWordsTypeNew.remove(i);
                }
                else{
                }

            } else {
                break;
            }
        }

        diffWords = dw;
        diffWordsIndex = dwi;
        diffWordsTypeNew = dwtn;

        //Acaba limpeza*/

        spannable = new SpannableString(resultText);
        String diffWord = "";
        for (int i = 0; i < diffWords.size(); i++) {
            diffWord = diffWords.get(i);

            if (diffWordsTypeNew.get(i)) {
                spannable.setSpan(new BackgroundColorSpan(Colors.GREEN), diffWordsIndex.get(i), diffWordsIndex.get(i) + diffWord.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                spannable.setSpan(new BackgroundColorSpan(Colors.RED), diffWordsIndex.get(i), diffWordsIndex.get(i) + diffWord.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return spannable;
    }
}
