package com.ids.smarthomev2;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by Zaid on 8/16/17.
 */
public class HelperT {
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
