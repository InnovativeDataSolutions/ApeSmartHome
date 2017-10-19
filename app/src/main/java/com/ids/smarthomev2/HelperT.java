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

//    public String decodemanualupdate(String powerln,String internalid2){
//        cursor = db.getController(powerln, sqLiteDatabase);
//
//        String getinternalid = internalid2.substring(42,44);
//
//        System.out.println("substring internal id :" + getinternalid);
//
//        cursor.moveToFirst();
//        do {
//            String pidfkDB = cursor.getString(0);
//            String contrlidDB = cursor.getString(1);
//            internalid = cursor.getString(2);
//            String contrlnameDB = cursor.getString(3);
//            String contrltype = cursor.getString(4);
//            String cntrlstatusDB = cursor.getString(5);
//
//            System.out.println("getcontroller info through PID : " + pidfkDB + " " + contrlidDB + " " + internalid+ " " + contrlnameDB + " " + cntrlstatusDB + " " + contrltype);
//
//        } while (cursor.moveToNext());
//
//        cursor2 = db.getdevmodel(powerln, sqLiteDatabase);
//
//        cursor2.moveToFirst();
//        do {
//            model = cursor2.getString(8);
//
//            System.out.println("get dev model info through PID : " + model);
//
//        } while (cursor2.moveToNext());
//
//            if (model.equals("PS")) {
//                click = "1PLUGoneOFF";
//            }else if(model.equals("TS2G")){
//                switch (getinternalid){
//                    case "01":
//                        click = "2GoneOFF";
//                        break;
//                    case "02":
//                        click = "2GtwoOFF";
//                        break;
//                }
//            }
//        return click;
//    }

    }
