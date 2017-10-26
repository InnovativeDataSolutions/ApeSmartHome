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

    }
