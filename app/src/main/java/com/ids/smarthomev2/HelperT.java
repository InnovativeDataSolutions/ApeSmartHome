package com.ids.smarthomev2;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;

/**
 * Created by Zaid on 8/16/17.
 */
public class HelperT {
    private Context context;
    Database db = new Database(context);
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor,cursor2;
    String model;
    String internalid,click;
//save the context recievied via constructor in a local variable

    public HelperT(Context context){
        this.context=context;
    }


    public static void showExplanationAlertDialog (final String message, final String title, Context context) {
        if (context != null && message != null && title != null){
            AlertDialog.Builder aBuilder = new AlertDialog.Builder(context);
            aBuilder.setTitle(title);
            aBuilder.setMessage(message);
            aBuilder.setCancelable(true);

            aBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog dialog = aBuilder.create();
            dialog.show();

        }
    }

    public static String hex2String(short in) {
        return "" + Integer.toHexString(in).toUpperCase();
    }

    public static String validProtocolStr(String msg) {

        String in = validProtocolSubStr(msg);
        if (in.length() > 44 && ("" + in.charAt(14) + in.charAt(15)).equals("40")) {

            String plc_length_binary = Integer.toBinaryString(Integer.parseInt("" + in.charAt(16) + in.charAt(17), 16));
            if (plc_length_binary.length() > 5) {
                plc_length_binary = plc_length_binary.substring(plc_length_binary.length() - 5);
            }
            int plc_length = Integer.parseInt(plc_length_binary, 2);

            int end_st = 42 + (plc_length * 2);
            if (in.length() >= (end_st + 1) && ("" + in.charAt(end_st + 1)).equals("3")) {
                return in;
            }
        }
        return "";
    }

    public static String validProtocolSubStr(String msg) {

        int srt_char = msg.indexOf("0237");
        if (srt_char >= 0) {
            return msg.substring(srt_char);
        }
        return "";
    }

    }
