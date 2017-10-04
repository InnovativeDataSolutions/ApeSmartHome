package com.ids.smarthomev2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Home extends AppCompatActivity implements View.OnTouchListener {

    private RelativeLayout someLayout, rl1, rl2, rl3, rl4, rl5, rlplug, rlfan;
    Button on_5g, off_5g, on2_5g, off2_5g, on3_5g, off3_5g, on4_5g, off4_5g, on5_5g, off5_5g;
    Button on_4g, off_4g, on2_4g, off2_4g, on3_4g, off3_4g, on4_4g, off4_4g;
    Button on_3g, off_3g, on2_3g, off2_3g, on3_3g, off3_3g;
    Button btnon1_2g, btnoff1_2g, btnon2_2g, btnoff2_2g;
    Button on_1g, off_1g, on_1plug, off_1plug, btnplusfan, btnminusfan, btnonfan, btnofffan;
    Spinner sp1, sp2;
    ProgressBar spinner;
    TextView resp;
    ProgressBar progressbarfan;
    TextView tvinfo, tvfanspeed;
    private static final String TAG = "motion";
    GestureDetector gestureDetector;
    private String SERVER_IP; //server IP address192.168.1.9 // port = 8081
    public static final int SERVER_PORT = 8081;
    int v = 0, j = 0, point = 0;
    int count = 0;
    boolean bgthread=false;
    String click = null, cntrlstatus = null;
    Context ctx = this;
    Database db = new Database(ctx);
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor, cursor2, cursor3, cursor4;
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    String homeidVAR, usernameVAR, gatewayVAR, ipaddressVAR, areanameVAR, devicenameVAR, devicemodelVAR, powerlineidVAR, cmmndidVAR, masteridVAR, devicecodeVAR, physicalidVAR, contridVAR, internalidVAR, contrnameVAR, contrtypeVAR, contrstatusVAR, pidfk;
    String pidfkDB, contrlidDB, internalidDB, contrlnameDB, cntrlstatusDB, v4c, v10c, fanintid, buttonstate,clientrply, devicestatus = null, switchstatusid,on="1",off="2";
    int i, fan;
    Handler UIhandler;
    Socket socket = null;
    Thread Thread1 = null;
    Thread Thread14G = null;
    Thread Thread13G = null;
    Thread Thread1OTHER = null;
    Thread Thread2 = null;
    Thread Thread1bg = null;
    String btnclick = null;
    String protocolON, protocolOFF, protocolON2G, protocolON4G, protocolOFF4G, protocolON1G, protocolOFF1G, protocolONPLUG, protocolOFFPLUG, protocolOFF2G, protocolON3G, protocolOFF3G, protocolONM, protocolOFFM, protocolOFFFAN, protocolONFAN, protocolMINUSFAN, protocolPLUSFAN;
    List<String> devicenameAR = new ArrayList<String>();
    List<String> areaAR = new ArrayList<String>();
    List<String> masteridAR = new ArrayList<String>();
    List<String> physicalidAR = new ArrayList<String>();
    List<String> powerlineidAR = new ArrayList<String>();
    List<String> commandidAR = new ArrayList<String>();
    List<String> devicecodeAR = new ArrayList<String>();
    List<String> devicemodelAR = new ArrayList<String>();
    List<String> ipaddress = new ArrayList<String>();

    List<String> devicenameARDB = new ArrayList<String>();
    List<String> areaARDB = new ArrayList<String>();
    List<String> masteridARDB = new ArrayList<String>();
    List<String> physicalidARDB = new ArrayList<String>();
    List<String> powerlineidARDB = new ArrayList<String>();
    List<String> commandidARDB = new ArrayList<String>();
    List<String> devicecodeARDB = new ArrayList<String>();
    List<String> devicemodelARDB = new ArrayList<String>();

    List<String> cntrlstatusinfo = new ArrayList<String>();

    HashMap<String, String> area_devname = new HashMap<String, String>();

    //Contrller:[
    List<String> pidfkAR = new ArrayList<String>();
    List<String> contrlidAR = new ArrayList<String>();
    List<String> internalidAR = new ArrayList<String>();
    List<String> cntrlnameAR = new ArrayList<String>();
    List<String> contrlstatusAR = new ArrayList<String>();
    List<String> contrltypeAR = new ArrayList<String>();

    List<String> pidfkARDB = new ArrayList<String>();
    List<String> contrlidARDB = new ArrayList<String>();
    List<String> internalidARDB = new ArrayList<String>();
    List<String> cntrlnameARDB = new ArrayList<String>();
    List<String> contrlstatusARDB = new ArrayList<String>();
    List<String> contrltypeARDB = new ArrayList<String>();

    // ]
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        someLayout = (RelativeLayout) findViewById(R.id.rl);

        rl1 = (RelativeLayout) findViewById(R.id.rl5g);
        rl2 = (RelativeLayout) findViewById(R.id.rl4g);
        rl3 = (RelativeLayout) findViewById(R.id.rl3g);
        rl4 = (RelativeLayout) findViewById(R.id.rl2g);
        rl5 = (RelativeLayout) findViewById(R.id.rl1g);
        rlplug = (RelativeLayout) findViewById(R.id.rlplug);
        rlfan = (RelativeLayout) findViewById(R.id.rlfan);
        sp1 = (Spinner) findViewById(R.id.sp1);
        sp2 = (Spinner) findViewById(R.id.sp2);

        on_5g = (Button) findViewById(R.id.btnon5g);
        off_5g = (Button) findViewById(R.id.btnoff5g);
        on2_5g = (Button) findViewById(R.id.btn1on5g);
        off2_5g = (Button) findViewById(R.id.btn1off5g);
        on3_5g = (Button) findViewById(R.id.btn2on5g);
        off3_5g = (Button) findViewById(R.id.btn2off5g);
        on4_5g = (Button) findViewById(R.id.btn3on5g);
        off4_5g = (Button) findViewById(R.id.btn3off5g);
        on5_5g = (Button) findViewById(R.id.btn4on5g);
        off5_5g = (Button) findViewById(R.id.btn4off5g);

        on_4g = (Button) findViewById(R.id.btnon4g);
        off_4g = (Button) findViewById(R.id.btnoff4g);
        on2_4g = (Button) findViewById(R.id.btn1on4g);
        off2_4g = (Button) findViewById(R.id.btn1off4g);
        on3_4g = (Button) findViewById(R.id.btn2on4g);
        off3_4g = (Button) findViewById(R.id.btn2off4g);
        on4_4g = (Button) findViewById(R.id.btn3on4g);
        off4_4g = (Button) findViewById(R.id.btn3off4g);

        on_3g = (Button) findViewById(R.id.btnon3g);
        off_3g = (Button) findViewById(R.id.btnoff3g);
        on2_3g = (Button) findViewById(R.id.btn1on3g);
        off2_3g = (Button) findViewById(R.id.btn1off3g);
        on3_3g = (Button) findViewById(R.id.btn2on3g);
        off3_3g = (Button) findViewById(R.id.btn2off3g);

        btnon1_2g = (Button) findViewById(R.id.btnon2g);
        btnoff1_2g = (Button) findViewById(R.id.btnoff2g);
        btnon2_2g = (Button) findViewById(R.id.btn1on2g);
        btnoff2_2g = (Button) findViewById(R.id.btn1off2g);
        btnplusfan = (Button) findViewById(R.id.btnplusfan);
        btnminusfan = (Button) findViewById(R.id.btnminusfan);
        btnonfan = (Button) findViewById(R.id.btnonfan);
        btnofffan = (Button) findViewById(R.id.btnofffan);
        progressbarfan = (ProgressBar) findViewById(R.id.progressbarfan);
        progressbarfan.setScaleY(3.5f);
        progressbarfan.setMax(5);
        btnplusfan.setEnabled(false);
        btnminusfan.setEnabled(false);

        on_1g = (Button) findViewById(R.id.btn1on1g);
        off_1g = (Button) findViewById(R.id.btn1off1g);

        on_1plug = (Button) findViewById(R.id.btn1onplug);
        off_1plug = (Button) findViewById(R.id.btn1offplug);

        resp = (TextView)findViewById(R.id.resp);
        spinner = (ProgressBar)findViewById(R.id.progressBar1);

        mDrawerList = (ListView) findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        tvinfo = (TextView) findViewById(R.id.tvinfo);
        tvfanspeed = (TextView) findViewById(R.id.faninfotv);
        System.out.println("ip & port : " + SERVER_IP + " " + SERVER_PORT);

        cursor = db.gethomeinfo();
        try {

            cursor.moveToFirst();
            do {
                homeidVAR = cursor.getString(0);
                usernameVAR = cursor.getString(1);
                gatewayVAR = cursor.getString(2);
                ipaddressVAR = cursor.getString(3);
                physicalidVAR = cursor.getString(4);
                powerlineidVAR = cursor.getString(5);
                devicenameVAR = cursor.getString(6);
                areanameVAR = cursor.getString(7);
                devicemodelVAR = cursor.getString(8);
                devicecodeVAR = cursor.getString(9);
                cmmndidVAR = cursor.getString(10);
                masteridVAR = cursor.getString(11);
                System.out.println("db info HOME - HOME : " + homeidVAR + " " + usernameVAR + " " + gatewayVAR + " " + ipaddressVAR + " " + physicalidVAR + " " + powerlineidVAR + " " + devicenameVAR + " " + areanameVAR + " " + devicemodelVAR + " " + devicecodeVAR + " " + cmmndidVAR + " " + masteridVAR);
                devicenameAR.add(devicemodelVAR);
                areaAR.add(areanameVAR);
                area_devname.put(areanameVAR, devicemodelVAR);
                ipaddress.add(ipaddressVAR);
                physicalidAR.add(physicalidVAR);
                powerlineidAR.add(powerlineidVAR);
                devicecodeAR.add(devicecodeVAR);
                commandidAR.add(cmmndidVAR);
                masteridAR.add(masteridVAR);
                devicemodelAR.add(devicenameVAR);
            }
            while (cursor.moveToNext());
        } catch (Exception e) {
            System.out.println("Home exception : " + e);
        }

//        SERVER_IP = ipaddressVAR;

        cursor = db.getcontrollerinfo();
        try {
            cursor.moveToFirst();
            do {
                pidfk = cursor.getString(0);
                contridVAR = cursor.getString(1);
                internalidVAR = cursor.getString(2);
                contrnameVAR = cursor.getString(3);
                contrtypeVAR = cursor.getString(4);
                contrstatusVAR = cursor.getString(5);
                System.out.println("db info CONTROLLER - HOME : " + pidfk + " " + contridVAR + " " + internalidVAR + " " + contrnameVAR + " " + contrtypeVAR + " " + contrstatusVAR);
                pidfkAR.add(pidfk);
                contrlidAR.add(contridVAR);
                internalidAR.add(internalidVAR);
                cntrlnameAR.add(contrnameVAR);
                contrltypeAR.add(contrtypeVAR);
                contrlstatusAR.add(contrstatusVAR);
            }
            while (cursor.moveToNext());
        } catch (Exception e) {
            System.out.println("CONTROLLER exception : " + e);
        }

        this.Thread1bg = new Thread(new Thread1bg());
        this.Thread1bg.start();
        UIhandler = new Handler();
// Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, areaAR);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        sp1.setAdapter(dataAdapter);

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, devicemodelAR);
// Specify the layout to use when the list of choices appears
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        sp2.setAdapter(dataAdapter2);

        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                String value = devicenameAR.get(position);
                String value2 = areaAR.get(position);
                String value3 = devicemodelAR.get(position);
                tvinfo.setText("[Device : " + value3 + "] [Area :" + value2 + "]");
                sp2.setSelection(position);
                v = position;
                //j = position;
                //get values from array based on value of v and assign them to the protocol.
                if (value.contains("TS1G")) {
                    getcntrlstatus();
                    rl1.setVisibility(View.GONE);
                    rl2.setVisibility(View.GONE);
                    rl3.setVisibility(View.GONE);
                    rl4.setVisibility(View.GONE);
                    rl5.setVisibility(View.VISIBLE);
                    rlplug.setVisibility(View.GONE);
                    rlfan.setVisibility(View.GONE);
                } else if (value.contains("TS2G")) {
                    getcntrlstatus();
                    rl1.setVisibility(View.GONE);
                    rl2.setVisibility(View.GONE);
                    rl3.setVisibility(View.GONE);
                    rl4.setVisibility(View.VISIBLE);
                    rl5.setVisibility(View.GONE);
                    rlplug.setVisibility(View.GONE);
                    rlfan.setVisibility(View.GONE);
                } else if (value.contains("TS3G")) {
                    getcntrlstatus();
                    rl1.setVisibility(View.GONE);
                    rl2.setVisibility(View.GONE);
                    rl3.setVisibility(View.VISIBLE);
                    rl4.setVisibility(View.GONE);
                    rl5.setVisibility(View.GONE);
                    rlplug.setVisibility(View.GONE);
                    rlfan.setVisibility(View.GONE);
                } else if (value.contains("TS4G")) {
                    getcntrlstatus();
                    rl1.setVisibility(View.GONE);
                    rl2.setVisibility(View.VISIBLE);
                    rl3.setVisibility(View.GONE);
                    rl4.setVisibility(View.GONE);
                    rl5.setVisibility(View.GONE);
                    rlplug.setVisibility(View.GONE);
                    rlfan.setVisibility(View.GONE);
                } else if (value.contains("Dimmer")) {
                    getcntrlstatus();
                    rl1.setVisibility(View.GONE);
                    rl2.setVisibility(View.GONE);
                    rl3.setVisibility(View.VISIBLE);
                    rl4.setVisibility(View.GONE);
                    rl5.setVisibility(View.GONE);
                    rlplug.setVisibility(View.GONE);
                    rlfan.setVisibility(View.GONE);
                } else if (value.contains("BC")) {
                    getcntrlstatus();
                    rl1.setVisibility(View.GONE);
                    rl2.setVisibility(View.VISIBLE);
                    rl3.setVisibility(View.GONE);
                    rl4.setVisibility(View.GONE);
                    rl5.setVisibility(View.GONE);
                    rlplug.setVisibility(View.GONE);
                    rlfan.setVisibility(View.GONE);
                } else if (value.contains("TS5G")) {
                    getcntrlstatus();
                    rl1.setVisibility(View.VISIBLE);
                    rl2.setVisibility(View.GONE);
                    rl3.setVisibility(View.GONE);
                    rl4.setVisibility(View.GONE);
                    rl5.setVisibility(View.GONE);
                    rlplug.setVisibility(View.GONE);
                    rlfan.setVisibility(View.GONE);
                } else if (value.contains("PS")) {
                    getcntrlstatus();
                    rl1.setVisibility(View.GONE);
                    rl2.setVisibility(View.GONE);
                    rl3.setVisibility(View.GONE);
                    rl4.setVisibility(View.GONE);
                    rl5.setVisibility(View.GONE);
                    rlplug.setVisibility(View.VISIBLE);
                    rlfan.setVisibility(View.GONE);
                } else if (value.contains("METER")) {
                    getcntrlstatus();
                    rl1.setVisibility(View.GONE);
                    rl2.setVisibility(View.GONE);
                    rl3.setVisibility(View.GONE);
                    rl4.setVisibility(View.GONE);
                    rl5.setVisibility(View.VISIBLE);
                    rlplug.setVisibility(View.GONE);
                    rlfan.setVisibility(View.GONE);
                } else if (value.contains("FC")) {
                    getcntrlstatus();
                    rl1.setVisibility(View.GONE);
                    rl2.setVisibility(View.GONE);
                    rl3.setVisibility(View.GONE);
                    rl4.setVisibility(View.GONE);
                    rl5.setVisibility(View.GONE);
                    rlplug.setVisibility(View.GONE);
                    rlfan.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }


        });

        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                String value = devicenameAR.get(position);
                String value2 = areaAR.get(position);
                String value3 = devicemodelAR.get(position);
                tvinfo.setText("[Device : " + value3 + "] [Area :" + value2 + "]");
                sp1.setSelection(position);
                v = position;
                //j = position;
                //get values from array based on value of v and assign them to the protocol.
                if (value.contains("TS1G")) {
                    getcntrlstatus();
                    rl1.setVisibility(View.GONE);
                    rl2.setVisibility(View.GONE);
                    rl3.setVisibility(View.GONE);
                    rl4.setVisibility(View.GONE);
                    rl5.setVisibility(View.VISIBLE);
                    rlplug.setVisibility(View.GONE);
                    rlfan.setVisibility(View.GONE);
                } else if (value.contains("TS2G")) {
                    getcntrlstatus();
                    rl1.setVisibility(View.GONE);
                    rl2.setVisibility(View.GONE);
                    rl3.setVisibility(View.GONE);
                    rl4.setVisibility(View.VISIBLE);
                    rl5.setVisibility(View.GONE);
                    rlplug.setVisibility(View.GONE);
                    rlfan.setVisibility(View.GONE);
                } else if (value.contains("TS3G")) {
                    getcntrlstatus();
                    rl1.setVisibility(View.GONE);
                    rl2.setVisibility(View.GONE);
                    rl3.setVisibility(View.VISIBLE);
                    rl4.setVisibility(View.GONE);
                    rl5.setVisibility(View.GONE);
                    rlplug.setVisibility(View.GONE);
                    rlfan.setVisibility(View.GONE);
                } else if (value.contains("TS4G")) {
                    getcntrlstatus();
                    rl1.setVisibility(View.GONE);
                    rl2.setVisibility(View.VISIBLE);
                    rl3.setVisibility(View.GONE);
                    rl4.setVisibility(View.GONE);
                    rl5.setVisibility(View.GONE);
                    rlplug.setVisibility(View.GONE);
                    rlfan.setVisibility(View.GONE);
                } else if (value.contains("2")) {
                    getcntrlstatus();
                    rl1.setVisibility(View.GONE);
                    rl2.setVisibility(View.GONE);
                    rl3.setVisibility(View.GONE);
                    rl4.setVisibility(View.VISIBLE);
                    rl5.setVisibility(View.GONE);
                    rlplug.setVisibility(View.GONE);
                } else if (value.contains("Dimmer")) {
                    getcntrlstatus();
                    rl1.setVisibility(View.GONE);
                    rl2.setVisibility(View.GONE);
                    rl3.setVisibility(View.VISIBLE);
                    rl4.setVisibility(View.GONE);
                    rl5.setVisibility(View.GONE);
                    rlplug.setVisibility(View.GONE);
                    rlfan.setVisibility(View.GONE);
                } else if (value.contains("BC")) {
                    getcntrlstatus();
                    rl1.setVisibility(View.GONE);
                    rl2.setVisibility(View.VISIBLE);
                    rl3.setVisibility(View.GONE);
                    rl4.setVisibility(View.GONE);
                    rl5.setVisibility(View.GONE);
                    rlplug.setVisibility(View.GONE);
                    rlfan.setVisibility(View.GONE);
                } else if (value.contains("TS5G")) {
                    getcntrlstatus();
                    rl1.setVisibility(View.VISIBLE);
                    rl2.setVisibility(View.GONE);
                    rl3.setVisibility(View.GONE);
                    rl4.setVisibility(View.GONE);
                    rl5.setVisibility(View.GONE);
                    rlplug.setVisibility(View.GONE);
                    rlfan.setVisibility(View.GONE);
                } else if (value.contains("PS")) {
                    getcntrlstatus();
                    rl1.setVisibility(View.GONE);
                    rl2.setVisibility(View.GONE);
                    rl3.setVisibility(View.GONE);
                    rl4.setVisibility(View.GONE);
                    rl5.setVisibility(View.GONE);
                    rlplug.setVisibility(View.VISIBLE);
                    rlfan.setVisibility(View.GONE);
                } else if (value.contains("METER")) {
                    getcntrlstatus();
                    rl1.setVisibility(View.GONE);
                    rl2.setVisibility(View.GONE);
                    rl3.setVisibility(View.GONE);
                    rl4.setVisibility(View.GONE);
                    rl5.setVisibility(View.VISIBLE);
                    rlplug.setVisibility(View.GONE);
                    rlfan.setVisibility(View.GONE);
                } else if (value.contains("FC")) {
                    getcntrlstatus();
                    rl1.setVisibility(View.GONE);
                    rl2.setVisibility(View.GONE);
                    rl3.setVisibility(View.GONE);
                    rl4.setVisibility(View.GONE);
                    rl5.setVisibility(View.GONE);
                    rlplug.setVisibility(View.GONE);
                    rlfan.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }


    public String bytesToHex(byte[] in) {
        final StringBuilder builder = new StringBuilder();
        for (byte b : in) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    public byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public void getcntrlstatus() {

        String v4 = devicenameAR.get(v);

        if (devicestatus == null) {
            System.out.println("devicestatus null");
        } else {
            if (devicestatus.equals("")) {
//                off_1g.setVisibility(View.VISIBLE);
//                on_1g.setVisibility(View.GONE);
                System.out.println(devicestatus + " -1- " + v4 + " " + switchstatusid);
            } else if (devicestatus == v4) {
                System.out.println(devicestatus + " -2- " + v4 + " " + switchstatusid);
                if (click == "1GoneOFF") {
                    off_1g.setVisibility(View.GONE);
                    on_1g.setVisibility(View.VISIBLE);
                } else if (click == "1PLUGoneOFF") {
                    off_1plug.setVisibility(View.GONE);
                    on_1plug.setVisibility(View.VISIBLE);
                } else if (click == "2GoneOFF") {
                    btnoff1_2g.setVisibility(View.GONE);
                    btnon1_2g.setVisibility(View.VISIBLE);
                } else if (click == "2GtwoOFF") {
                    btnoff2_2g.setVisibility(View.GONE);
                    btnon2_2g.setVisibility(View.VISIBLE);
                } else if (click == "3GoneOFF") {
                    off_3g.setVisibility(View.GONE);
                    on_3g.setVisibility(View.VISIBLE);
                } else if (click == "3GtwoOFF") {
                    off2_3g.setVisibility(View.GONE);
                    on2_3g.setVisibility(View.VISIBLE);
                } else if (click == "3GthreeOFF") {
                    off3_3g.setVisibility(View.GONE);
                    on3_3g.setVisibility(View.VISIBLE);
                } else if (click == "4GoneOFF") {
                    off_4g.setVisibility(View.GONE);
                    on_4g.setVisibility(View.VISIBLE);
                } else if (click == "4GtwoOFF") {
                    off2_4g.setVisibility(View.GONE);
                    on2_4g.setVisibility(View.VISIBLE);
                } else if (click == "4GthreeOFF") {
                    off3_4g.setVisibility(View.GONE);
                    on3_4g.setVisibility(View.VISIBLE);
                } else if (click == "4GfourOFF") {
                    off4_4g.setVisibility(View.GONE);
                    on4_4g.setVisibility(View.VISIBLE);
                } else if (click == "5GoneOFF") {
                    off_5g.setVisibility(View.GONE);
                    on_5g.setVisibility(View.VISIBLE);
                } else if (click == "5GtwoOFF") {
                    off2_5g.setVisibility(View.GONE);
                    on2_5g.setVisibility(View.VISIBLE);
                } else if (click == "5GthreeOFF") {
                    off3_5g.setVisibility(View.GONE);
                    on3_5g.setVisibility(View.VISIBLE);
                } else if (click == "5GfourOFF") {
                    off4_5g.setVisibility(View.GONE);
                    on4_5g.setVisibility(View.VISIBLE);
                } else if (click == "5GfiveOFF") {
                    off5_5g.setVisibility(View.GONE);
                    on5_5g.setVisibility(View.VISIBLE);
                }
            } else if (!(devicestatus == v4)) {
                off_1g.setVisibility(View.VISIBLE);
                on_1g.setVisibility(View.GONE);
                System.out.println(devicestatus + " -3- " + v4 + " " + switchstatusid);
            }
        }
    }

    public String dectohex(String dec) {
        int i = Integer.valueOf(dec);
        String hex = Integer.toHexString(i);
        System.out.println("Hex value is " + hex);
        return hex;
    }

    public void getDeviceModel() {

        if (devicemodelARDB.size() > 0) {
            devicemodelARDB.clear();
            devicenameARDB.clear();
            areaARDB.clear();
            physicalidARDB.clear();
            powerlineidARDB.clear();
            devicecodeARDB.clear();
            commandidARDB.clear();
            masteridARDB.clear();
        }

        sqLiteDatabase = db.getReadableDatabase();
        String v4 = devicenameAR.get(v);
        cursor4 = db.getDevModels(v4, sqLiteDatabase);

        cursor4.moveToFirst();
        do {
            String homeidVAR = cursor4.getString(0);
            String usernameVAR = cursor4.getString(1);
            String gatewayVAR = cursor4.getString(2);
            String ipaddressVAR = cursor4.getString(3);
            String physicalidVAR = cursor4.getString(4);
            String powerlineidVAR = cursor4.getString(5);
            String devicenameVAR = cursor4.getString(6);
            String areanameVAR = cursor4.getString(7);
            String devicemodelVAR = cursor4.getString(8);
            String devicecodeVAR = cursor4.getString(9);
            String cmmndidVAR = cursor4.getString(10);
            String masteridVAR = cursor4.getString(11);
            System.out.println("get device model info DB: " + homeidVAR + " " + usernameVAR + " " + gatewayVAR + " " + ipaddressVAR + " " + physicalidVAR + " " + powerlineidVAR + " " + devicenameVAR + " " + areanameVAR + " " + devicemodelVAR + " " + devicecodeVAR + " " + cmmndidVAR + " " + masteridVAR);

            devicemodelARDB.add(devicemodelVAR);
            devicenameARDB.add(devicenameVAR);
            areaARDB.add(areanameVAR);
            physicalidARDB.add(physicalidVAR);
            powerlineidARDB.add(powerlineidVAR);
            devicecodeARDB.add(devicecodeVAR);
            commandidARDB.add(cmmndidVAR);
            masteridARDB.add(masteridVAR);

        } while (cursor4.moveToNext());
    }

    @Override
    public void onBackPressed() {
    }

    class Thread1bg implements Runnable {
        public void run() {
            //Socket socket = null;
            try {
                //here you must put your computer's IP address.
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                socket = new Socket(serverAddr, SERVER_PORT);
                Thread2 commThread2 = new Thread2(socket);
                new Thread(commThread2).start();
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void topToBtm(View view) {
        Animation animation1 =
                AnimationUtils.loadAnimation(getApplicationContext(), R.anim.uptobtm);
        view.startAnimation(animation1);
    }

    public void btmToUp(View view) {
        Animation animation1 =
                AnimationUtils.loadAnimation(getApplicationContext(), R.anim.btmtoup);
        view.startAnimation(animation1);
    }

    public void leftToRight(View view) {
        Animation animation1 =
                AnimationUtils.loadAnimation(getApplicationContext(), R.anim.lefttoright);
        view.startAnimation(animation1);
    }

    public void rightToLeft(View view) {
        Animation animation1 =
                AnimationUtils.loadAnimation(getApplicationContext(), R.anim.righttoleft);
        view.startAnimation(animation1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        gestureDetector = new GestureDetector(this, new OnSwipeListener() {

            @Override
            public boolean onSwipe(Direction direction) {
                if (direction == Direction.up) {

                    int devicemodelARszie = devicemodelAR.size();
                    System.out.println("check size :" + devicemodelARszie);

                    if (v >= devicemodelARszie - 1) {
                        Toast.makeText(Home.this, "No more Devices!", Toast.LENGTH_SHORT).show();
                    } else {
                        ++v;
                    }
                    System.out.println(v);
                    try {
                        String value = devicenameAR.get(v);
                        String value2 = areaAR.get(v);
                        String value3 = contrlstatusAR.get(v);
                        String value4 = devicemodelAR.get(v);
                        tvinfo.setText("[Device : " + value4 + "] [Area :" + value2 + "]");
                        sp1.setSelection(v);
                        sp2.setSelection(v);
                        //get values from array based on value of v and assign them to the protocol.
                        if (value.contains("TS1G")) {
                            getcntrlstatus();
                            rl1.setVisibility(View.GONE);
                            rl2.setVisibility(View.GONE);
                            rl3.setVisibility(View.GONE);
                            rl4.setVisibility(View.GONE);
                            rl5.setVisibility(View.VISIBLE);
                            rlplug.setVisibility(View.GONE);
                            rlfan.setVisibility(View.GONE);
                            btmToUp(rl5);
                        } else if (value.contains("TS2G")) {
                            getcntrlstatus();
                            rl1.setVisibility(View.GONE);
                            rl2.setVisibility(View.GONE);
                            rl3.setVisibility(View.GONE);
                            rl4.setVisibility(View.VISIBLE);
                            rl5.setVisibility(View.GONE);
                            rlplug.setVisibility(View.GONE);
                            rlfan.setVisibility(View.GONE);
                            btmToUp(rl4);
                        } else if (value.contains("TS3G")) {
                            getcntrlstatus();
                            rl1.setVisibility(View.GONE);
                            rl2.setVisibility(View.GONE);
                            rl3.setVisibility(View.VISIBLE);
                            rl4.setVisibility(View.GONE);
                            rl5.setVisibility(View.GONE);
                            rlplug.setVisibility(View.GONE);
                            rlfan.setVisibility(View.GONE);
                            btmToUp(rl3);
                        } else if (value.contains("TS4G")) {
                            getcntrlstatus();
                            rl1.setVisibility(View.GONE);
                            rl2.setVisibility(View.VISIBLE);
                            rl3.setVisibility(View.GONE);
                            rl4.setVisibility(View.GONE);
                            rl5.setVisibility(View.GONE);
                            rlplug.setVisibility(View.GONE);
                            rlfan.setVisibility(View.GONE);
                            btmToUp(rl2);
                        } else if (value.contains("2")) {
                            getcntrlstatus();
                            rl1.setVisibility(View.GONE);
                            rl2.setVisibility(View.GONE);
                            rl3.setVisibility(View.GONE);
                            rl4.setVisibility(View.VISIBLE);
                            rl5.setVisibility(View.GONE);
                            rlplug.setVisibility(View.GONE);
                            rlfan.setVisibility(View.GONE);
                            btmToUp(rl4);
                        } else if (value.contains("Dimmer")) {
                            getcntrlstatus();
                            rl1.setVisibility(View.GONE);
                            rl2.setVisibility(View.GONE);
                            rl3.setVisibility(View.VISIBLE);
                            rl4.setVisibility(View.GONE);
                            rl5.setVisibility(View.GONE);
                            rlplug.setVisibility(View.GONE);
                            rlfan.setVisibility(View.GONE);
                            btmToUp(rl3);
                        } else if (value.contains("BC")) {
                            getcntrlstatus();
                            rl1.setVisibility(View.GONE);
                            rl2.setVisibility(View.VISIBLE);
                            rl3.setVisibility(View.GONE);
                            rl4.setVisibility(View.GONE);
                            rl5.setVisibility(View.GONE);
                            rlplug.setVisibility(View.GONE);
                            btmToUp(rl2);
                        } else if (value.contains("TS5G")) {
                            getcntrlstatus();
                            rl1.setVisibility(View.VISIBLE);
                            rl2.setVisibility(View.GONE);
                            rl3.setVisibility(View.GONE);
                            rl4.setVisibility(View.GONE);
                            rl5.setVisibility(View.GONE);
                            rlplug.setVisibility(View.GONE);
                            btmToUp(rl1);
                        } else if (value.contains("PS")) {
                            getcntrlstatus();
                            rl1.setVisibility(View.GONE);
                            rl2.setVisibility(View.GONE);
                            rl3.setVisibility(View.GONE);
                            rl4.setVisibility(View.GONE);
                            rl5.setVisibility(View.GONE);
                            rlplug.setVisibility(View.VISIBLE);
                            rlfan.setVisibility(View.GONE);
                            btmToUp(rlplug);
                        } else if (value.contains("METER")) {
                            getcntrlstatus();
                            rl1.setVisibility(View.GONE);
                            rl2.setVisibility(View.GONE);
                            rl3.setVisibility(View.GONE);
                            rl4.setVisibility(View.GONE);
                            rl5.setVisibility(View.VISIBLE);
                            rlplug.setVisibility(View.GONE);
                            rlfan.setVisibility(View.GONE);
                            btmToUp(rl5);
                        } else if (value.contains("FC")) {
                            getcntrlstatus();
                            rl1.setVisibility(View.GONE);
                            rl2.setVisibility(View.GONE);
                            rl3.setVisibility(View.GONE);
                            rl4.setVisibility(View.GONE);
                            rl5.setVisibility(View.GONE);
                            rlplug.setVisibility(View.GONE);
                            rlfan.setVisibility(View.VISIBLE);
                            btmToUp(rlfan);
                        }
                        System.out.println("Page B: " + v);
                        Log.d(TAG, "onSwipe: up" + v);
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println(String.valueOf(e));
                    }
                }


                if (direction == Direction.down) {
                    System.out.println(v);
                    int devicemodelARszie = devicemodelAR.size();
                    System.out.println("check size :" + devicemodelARszie);

                    if (v <= 0) {
                        Toast.makeText(Home.this, "No more Devices!", Toast.LENGTH_SHORT).show();
                    } else {
                        --v;
                    }
                    try {
                        String value = devicenameAR.get(v);
                        String value2 = areaAR.get(v);
                        String value4 = devicemodelAR.get(v);
                        tvinfo.setText("[Device : " + value4 + "] [Area :" + value2 + "]");
                        sp1.setSelection(v);
                        sp2.setSelection(v);
                        //get values from array based on value of v and assign them to the protocol.
                        if (value.contains("TS1G")) {
                            getcntrlstatus();
                            rl1.setVisibility(View.GONE);
                            rl2.setVisibility(View.GONE);
                            rl3.setVisibility(View.GONE);
                            rl4.setVisibility(View.GONE);
                            rl5.setVisibility(View.VISIBLE);
                            rlplug.setVisibility(View.GONE);
                            rlfan.setVisibility(View.GONE);
                            topToBtm(rl5);
                        } else if (value.contains("TS2G")) {
                            getcntrlstatus();
                            rl1.setVisibility(View.GONE);
                            rl2.setVisibility(View.GONE);
                            rl3.setVisibility(View.GONE);
                            rl4.setVisibility(View.VISIBLE);
                            rl5.setVisibility(View.GONE);
                            rlplug.setVisibility(View.GONE);
                            rlfan.setVisibility(View.GONE);
                            topToBtm(rl4);
                        } else if (value.contains("TS3G")) {
                            getcntrlstatus();
                            rl1.setVisibility(View.GONE);
                            rl2.setVisibility(View.GONE);
                            rl3.setVisibility(View.VISIBLE);
                            rl4.setVisibility(View.GONE);
                            rl5.setVisibility(View.GONE);
                            rlplug.setVisibility(View.GONE);
                            rlfan.setVisibility(View.GONE);
                            topToBtm(rl3);
                        } else if (value.contains("TS4G")) {
                            getcntrlstatus();
                            rl1.setVisibility(View.GONE);
                            rl2.setVisibility(View.VISIBLE);
                            rl3.setVisibility(View.GONE);
                            rl4.setVisibility(View.GONE);
                            rl5.setVisibility(View.GONE);
                            rlplug.setVisibility(View.GONE);
                            rlfan.setVisibility(View.GONE);
                            topToBtm(rl2);
                        } else if (value.contains("2")) {
                            getcntrlstatus();
                            rl1.setVisibility(View.GONE);
                            rl2.setVisibility(View.GONE);
                            rl3.setVisibility(View.GONE);
                            rl4.setVisibility(View.VISIBLE);
                            rl5.setVisibility(View.GONE);
                            rlplug.setVisibility(View.GONE);
                            rlfan.setVisibility(View.GONE);
                            topToBtm(rl4);
                        } else if (value.contains("Dimmer")) {
                            getcntrlstatus();
                            rl1.setVisibility(View.GONE);
                            rl2.setVisibility(View.GONE);
                            rl3.setVisibility(View.VISIBLE);
                            rl4.setVisibility(View.GONE);
                            rl5.setVisibility(View.GONE);
                            rlplug.setVisibility(View.GONE);
                            rlfan.setVisibility(View.GONE);
                            topToBtm(rl3);
                        } else if (value.contains("BC")) {
                            getcntrlstatus();
                            rl1.setVisibility(View.GONE);
                            rl2.setVisibility(View.VISIBLE);
                            rl3.setVisibility(View.GONE);
                            rl4.setVisibility(View.GONE);
                            rl5.setVisibility(View.GONE);
                            rlplug.setVisibility(View.GONE);
                            rlfan.setVisibility(View.GONE);
                            topToBtm(rl2);
                        } else if (value.contains("TS5G")) {
                            getcntrlstatus();
                            rl1.setVisibility(View.VISIBLE);
                            rl2.setVisibility(View.GONE);
                            rl3.setVisibility(View.GONE);
                            rl4.setVisibility(View.GONE);
                            rl5.setVisibility(View.GONE);
                            rlplug.setVisibility(View.GONE);
                            rlfan.setVisibility(View.GONE);
                            topToBtm(rl1);
                        } else if (value.contains("PS")) {
                            getcntrlstatus();
                            rl1.setVisibility(View.GONE);
                            rl2.setVisibility(View.GONE);
                            rl3.setVisibility(View.GONE);
                            rl4.setVisibility(View.GONE);
                            rl5.setVisibility(View.GONE);
                            rlplug.setVisibility(View.VISIBLE);
                            rlfan.setVisibility(View.GONE);
                            topToBtm(rlplug);
                        } else if (value.contains("METER")) {
                            getcntrlstatus();
                            rl1.setVisibility(View.GONE);
                            rl2.setVisibility(View.GONE);
                            rl3.setVisibility(View.GONE);
                            rl4.setVisibility(View.GONE);
                            rl5.setVisibility(View.VISIBLE);
                            rlplug.setVisibility(View.GONE);
                            rlfan.setVisibility(View.GONE);
                            topToBtm(rl5);
                        } else if (value.contains("FC")) {
                            getcntrlstatus();
                            rl1.setVisibility(View.GONE);
                            rl2.setVisibility(View.GONE);
                            rl3.setVisibility(View.GONE);
                            rl4.setVisibility(View.GONE);
                            rl5.setVisibility(View.GONE);
                            rlplug.setVisibility(View.GONE);
                            rlfan.setVisibility(View.VISIBLE);
                            topToBtm(rlfan);
                        }
                        System.out.println("Page A down: " + v);

                        Log.d(TAG, "onSwipe: down" + v);
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println(String.valueOf(e));
                    }


                }

                if (direction == Direction.left) {
                    getDeviceModel();
                    if (devicemodelARDB.size() <= 1) { //check to see if device of same model exists
                        Toast.makeText(Home.this, "No more device of same model!", Toast.LENGTH_SHORT).show();
                    }else { //if yes
                        if (j >= devicemodelARDB.size() - 1){ //allow only to swipe to maximum value of array list
                            Toast.makeText(Home.this, "No more device of same model!", Toast.LENGTH_SHORT).show();
                        }else {
                            ++j; //add j
                        }
                        System.out.println(j + " device model size : " + devicemodelARDB.size());
                        try {
                            String value = devicemodelARDB.get(j); //get associated values for j from temporary array list which contains infomation of devices of same type only when swiped left or right
                            String value2 = areaARDB.get(j);
                            String value4 = devicenameARDB.get(j);
                            int indexofsecnddevice = devicemodelAR.indexOf(value4); //assign value of j to main array to get protocol info.
                            v=indexofsecnddevice;//assign value to v..
                            tvinfo.setText("[Device : " + value4 + "] [Area :" + value2 + "]");
                            System.out.println("indexOf ardb : " + indexofsecnddevice);
                            sp1.setSelection(indexofsecnddevice);
                            sp2.setSelection(indexofsecnddevice);
                            //get values from array based on value of v and assign them to the protocol.
                            if (value.contains("TS1G")) {
                                getcntrlstatus();
                                rl1.setVisibility(View.GONE);
                                rl2.setVisibility(View.GONE);
                                rl3.setVisibility(View.GONE);
                                rl4.setVisibility(View.GONE);
                                rl5.setVisibility(View.VISIBLE);
                                rlplug.setVisibility(View.GONE);
                                rlfan.setVisibility(View.GONE);
                                rightToLeft(rl5);
                            } else if (value.contains("TS2G")) {
                                getcntrlstatus();
                                rl1.setVisibility(View.GONE);
                                rl2.setVisibility(View.GONE);
                                rl3.setVisibility(View.GONE);
                                rl4.setVisibility(View.VISIBLE);
                                rl5.setVisibility(View.GONE);
                                rlplug.setVisibility(View.GONE);
                                rlfan.setVisibility(View.GONE);
                                rightToLeft(rl4);
                            } else if (value.contains("TS3G")) {
                                getcntrlstatus();
                                rl1.setVisibility(View.GONE);
                                rl2.setVisibility(View.GONE);
                                rl3.setVisibility(View.VISIBLE);
                                rl4.setVisibility(View.GONE);
                                rl5.setVisibility(View.GONE);
                                rlplug.setVisibility(View.GONE);
                                rlfan.setVisibility(View.GONE);
                                rightToLeft(rl3);
                            } else if (value.contains("TS4G")) {
                                getcntrlstatus();
                                rl1.setVisibility(View.GONE);
                                rl2.setVisibility(View.VISIBLE);
                                rl3.setVisibility(View.GONE);
                                rl4.setVisibility(View.GONE);
                                rl5.setVisibility(View.GONE);
                                rlplug.setVisibility(View.GONE);
                                rlfan.setVisibility(View.GONE);
                                rightToLeft(rl2);
                            } else if (value.contains("2")) {
                                getcntrlstatus();
                                rl1.setVisibility(View.GONE);
                                rl2.setVisibility(View.GONE);
                                rl3.setVisibility(View.GONE);
                                rl4.setVisibility(View.VISIBLE);
                                rl5.setVisibility(View.GONE);
                                rlplug.setVisibility(View.GONE);
                                rlfan.setVisibility(View.GONE);
                                rightToLeft(rl4);
                            } else if (value.contains("Dimmer")) {
                                getcntrlstatus();
                                rl1.setVisibility(View.GONE);
                                rl2.setVisibility(View.GONE);
                                rl3.setVisibility(View.VISIBLE);
                                rl4.setVisibility(View.GONE);
                                rl5.setVisibility(View.GONE);
                                rlplug.setVisibility(View.GONE);
                                rlfan.setVisibility(View.GONE);
                                rightToLeft(rl3);
                            } else if (value.contains("BC")) {
                                getcntrlstatus();
                                rl1.setVisibility(View.GONE);
                                rl2.setVisibility(View.VISIBLE);
                                rl3.setVisibility(View.GONE);
                                rl4.setVisibility(View.GONE);
                                rl5.setVisibility(View.GONE);
                                rlplug.setVisibility(View.GONE);
                                rlfan.setVisibility(View.GONE);
                                rightToLeft(rl2);
                            } else if (value.contains("TS5G")) {
                                getcntrlstatus();
                                rl1.setVisibility(View.VISIBLE);
                                rl2.setVisibility(View.GONE);
                                rl3.setVisibility(View.GONE);
                                rl4.setVisibility(View.GONE);
                                rl5.setVisibility(View.GONE);
                                rlplug.setVisibility(View.GONE);
                                rlfan.setVisibility(View.GONE);
                                rightToLeft(rl1);
                            } else if (value.contains("PS")) {
                                getcntrlstatus();
                                rl1.setVisibility(View.GONE);
                                rl2.setVisibility(View.GONE);
                                rl3.setVisibility(View.GONE);
                                rl4.setVisibility(View.GONE);
                                rl5.setVisibility(View.GONE);
                                rlplug.setVisibility(View.VISIBLE);
                                rlfan.setVisibility(View.GONE);
                                rightToLeft(rlplug);
                            } else if (value.contains("METER")) {
                                getcntrlstatus();
                                rl1.setVisibility(View.GONE);
                                rl2.setVisibility(View.GONE);
                                rl3.setVisibility(View.GONE);
                                rl4.setVisibility(View.GONE);
                                rl5.setVisibility(View.VISIBLE);
                                rlplug.setVisibility(View.GONE);
                                rlfan.setVisibility(View.GONE);
                                rightToLeft(rl5);
                            } else if (value.contains("FC")) {
                                getcntrlstatus();
                                rl1.setVisibility(View.GONE);
                                rl2.setVisibility(View.GONE);
                                rl3.setVisibility(View.GONE);
                                rl4.setVisibility(View.GONE);
                                rl5.setVisibility(View.GONE);
                                rlplug.setVisibility(View.GONE);
                                rlfan.setVisibility(View.VISIBLE);
                                rightToLeft(rlfan);
                            }
                            System.out.println("page left swipe : " + j);
                            Log.d(TAG, "onSwipe: left" + j);
                        } catch (IndexOutOfBoundsException e) {
                            System.out.println(String.valueOf(e));
                        }
                    }
                }

                if (direction == Direction.right) {
                    //do your stuff
                    getDeviceModel();
                    if (devicemodelARDB.size() <= 1) {
                        Toast.makeText(Home.this, "No more device of same model!", Toast.LENGTH_SHORT).show();
                    }else {
                        System.out.println(j);
                        if (j == 0) {
                            Toast.makeText(Home.this, "No more device of same model!", Toast.LENGTH_SHORT).show();
                        }else{
                            --j;
                        }
                        System.out.println(j + " device model size : " + devicemodelARDB.size());
                        try {
                            String value = devicemodelARDB.get(j);
                            String value2 = areaARDB.get(j);
                            String value4 = devicenameARDB.get(j);
                            int indexofsecnddevice = devicemodelAR.indexOf(value4);
                            v=indexofsecnddevice;
                            tvinfo.setText("[Device : " + value4 + "] [Area :" + value2 + "]");
                            System.out.println("indexOf ardb : " + indexofsecnddevice);
                            sp1.setSelection(indexofsecnddevice);
                            sp2.setSelection(indexofsecnddevice);
                            //get values from array based on value of v and assign them to the protocol.
                            if (value.contains("TS1G")) {
                                getcntrlstatus();
                                rl1.setVisibility(View.GONE);
                                rl2.setVisibility(View.GONE);
                                rl3.setVisibility(View.GONE);
                                rl4.setVisibility(View.GONE);
                                rl5.setVisibility(View.VISIBLE);
                                rlplug.setVisibility(View.GONE);
                                rlfan.setVisibility(View.GONE);
                                leftToRight(rl5);
                            } else if (value.contains("TS2G")) {
                                getcntrlstatus();
                                rl1.setVisibility(View.GONE);
                                rl2.setVisibility(View.GONE);
                                rl3.setVisibility(View.GONE);
                                rl4.setVisibility(View.VISIBLE);
                                rl5.setVisibility(View.GONE);
                                rlplug.setVisibility(View.GONE);
                                rlfan.setVisibility(View.GONE);
                                leftToRight(rl4);
                            } else if (value.contains("TS3G")) {
                                getcntrlstatus();
                                rl1.setVisibility(View.GONE);
                                rl2.setVisibility(View.GONE);
                                rl3.setVisibility(View.VISIBLE);
                                rl4.setVisibility(View.GONE);
                                rl5.setVisibility(View.GONE);
                                rlplug.setVisibility(View.GONE);
                                rlfan.setVisibility(View.GONE);
                                leftToRight(rl3);
                            } else if (value.contains("TS4G")) {
                                getcntrlstatus();
                                rl1.setVisibility(View.GONE);
                                rl2.setVisibility(View.VISIBLE);
                                rl3.setVisibility(View.GONE);
                                rl4.setVisibility(View.GONE);
                                rl5.setVisibility(View.GONE);
                                rlplug.setVisibility(View.GONE);
                                rlfan.setVisibility(View.GONE);
                                leftToRight(rl2);
                            } else if (value.contains("2")) {
                                getcntrlstatus();
                                rl1.setVisibility(View.GONE);
                                rl2.setVisibility(View.GONE);
                                rl3.setVisibility(View.GONE);
                                rl4.setVisibility(View.VISIBLE);
                                rl5.setVisibility(View.GONE);
                                rlplug.setVisibility(View.GONE);
                                rlfan.setVisibility(View.GONE);
                                leftToRight(rl4);
                            } else if (value.contains("Dimmer")) {
                                getcntrlstatus();
                                rl1.setVisibility(View.GONE);
                                rl2.setVisibility(View.GONE);
                                rl3.setVisibility(View.VISIBLE);
                                rl4.setVisibility(View.GONE);
                                rl5.setVisibility(View.GONE);
                                rlplug.setVisibility(View.GONE);
                                rlfan.setVisibility(View.GONE);
                                leftToRight(rl3);
                            } else if (value.contains("BC")) {
                                getcntrlstatus();
                                rl1.setVisibility(View.GONE);
                                rl2.setVisibility(View.VISIBLE);
                                rl3.setVisibility(View.GONE);
                                rl4.setVisibility(View.GONE);
                                rl5.setVisibility(View.GONE);
                                rlplug.setVisibility(View.GONE);
                                rlfan.setVisibility(View.GONE);
                                leftToRight(rl2);
                            } else if (value.contains("TS5G")) {
                                getcntrlstatus();
                                rl1.setVisibility(View.VISIBLE);
                                rl2.setVisibility(View.GONE);
                                rl3.setVisibility(View.GONE);
                                rl4.setVisibility(View.GONE);
                                rl5.setVisibility(View.GONE);
                                rlplug.setVisibility(View.GONE);
                                rlfan.setVisibility(View.GONE);
                                leftToRight(rl1);
                            } else if (value.contains("PS")) {
                                getcntrlstatus();
                                rl1.setVisibility(View.GONE);
                                rl2.setVisibility(View.GONE);
                                rl3.setVisibility(View.GONE);
                                rl4.setVisibility(View.GONE);
                                rl5.setVisibility(View.GONE);
                                rlplug.setVisibility(View.VISIBLE);
                                rlfan.setVisibility(View.GONE);
                                leftToRight(rlplug);
                            } else if (value.contains("METER")) {
                                getcntrlstatus();
                                rl1.setVisibility(View.GONE);
                                rl2.setVisibility(View.GONE);
                                rl3.setVisibility(View.GONE);
                                rl4.setVisibility(View.GONE);
                                rl5.setVisibility(View.VISIBLE);
                                rlplug.setVisibility(View.GONE);
                                rlfan.setVisibility(View.GONE);
                                leftToRight(rl5);
                            } else if (value.contains("FC")) {
                                getcntrlstatus();
                                rl1.setVisibility(View.GONE);
                                rl2.setVisibility(View.GONE);
                                rl3.setVisibility(View.GONE);
                                rl4.setVisibility(View.GONE);
                                rl5.setVisibility(View.GONE);
                                rlplug.setVisibility(View.GONE);
                                rlfan.setVisibility(View.VISIBLE);
                                leftToRight(rlfan);
                            }
                            Log.d(TAG, "onSwipe: right" + j);
                        } catch (IndexOutOfBoundsException e) {
                            System.out.println(String.valueOf(e));
                        }
                    }

                }
                return true;
            }


        });
        someLayout.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d(TAG, "onTouch: ");
        gestureDetector.onTouchEvent(event);
        return true;
    }

    class Thread1 implements Runnable {
        public void run() {
            //Socket socket = null;
            try {
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                socket = new Socket(serverAddr, SERVER_PORT);
                OutputStream out = socket.getOutputStream();
                if (click.contains("5GoneOFF") || click.contains("5GtwoOFF") || click.contains("5GthreeOFF") || click.contains("5GfourOFF") || click.contains("5GfiveOFF")) {
                    byte[] by = hexStringToByteArray(protocolOFF.toUpperCase().replaceAll(" ", ""));
                    out.write(by, 0, by.length);
                    out.flush();
                } else if (click.contains("5GoneON") || click.contains("5GtwoON") || click.contains("5GthreeON") || click.contains("5GfourON") || click.contains("5GfiveON")) {
                    byte[] by = hexStringToByteArray(protocolON.toUpperCase().replaceAll(" ", ""));
                    out.write(by, 0, by.length);
                    out.flush();
                } else {
                    Toast.makeText(Home.this, "Something went wrong,check connection", Toast.LENGTH_SHORT).show();
                }
                Thread2 commThread = new Thread2(socket);
                new Thread(commThread).start();
                return;
            } catch (Exception e) {
                UIhandler.post(new UpdateButtonState(click.toUpperCase()));
                int pid = Integer.parseInt(v4c, 16); //taking powerlineid(hex) converting to integer
                String pid2 = String.valueOf(pid); // converting hex powerlineid to deciaml
                int iid = Integer.parseInt(v10c, 16); // " internal id
                String iid2 = String.valueOf(iid); // converting internal to decimal
                if (click.contains("OFF")) {
                    SendCmnd sc = new SendCmnd();
                    sc.execute(homeidVAR,pid2,iid2,on);
                    System.out.println("overTheNet : " + pid2 + iid2);
                } else {
                    SendCmnd sc = new SendCmnd();
                    sc.execute(homeidVAR,pid2,iid2,off);
                    System.out.println("overTheNet : " + pid2 + iid2);
                }
                e.printStackTrace();
            }
        }
    }

    class Thread14G implements Runnable {
        public void run() {
            //Socket socket = null;
            try {
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                socket = new Socket(serverAddr, SERVER_PORT);
                OutputStream out = socket.getOutputStream();
                if (click.contains("4GoneOFF") || click.contains("4GtwoOFF") || click.contains("4GthreeOFF") || click.contains("4GfourOFF") || click.contains("4GfiveOFF")) {
                    byte[] by = hexStringToByteArray(protocolOFF4G.replaceAll(" ", ""));
                    out.write(by, 0, by.length);
                    out.flush();
                } else if (click.contains("4GoneON") || click.contains("4GtwoON") || click.contains("4GthreeON") || click.contains("4GfourON") || click.contains("4GfiveON")) {
                    byte[] by = hexStringToByteArray(protocolON4G.replaceAll(" ", ""));
                    out.write(by, 0, by.length);
                    out.flush();
                } else {
                    Toast.makeText(Home.this, "Something went wrong,check connection", Toast.LENGTH_SHORT).show();
                }

                Thread2 commThread = new Thread2(socket);
                new Thread(commThread).start();
                return;
            } catch (Exception e) {
                UIhandler.post(new UpdateButtonState(click.toUpperCase()));
                int pid = Integer.parseInt(v4c, 16); //taking powerlineid(hex) converting to integer
                String pid2 = String.valueOf(pid); // converting hex powerlineid to deciaml
                int iid = Integer.parseInt(v10c, 16); // " internal id
                String iid2 = String.valueOf(iid); // converting internal to decimal
                if (click.contains("OFF")) {
                    SendCmnd sc = new SendCmnd();
                    sc.execute(homeidVAR,pid2,iid2,on);
                    System.out.println("overTheNet : " + pid2 + iid2);
                } else {
                    SendCmnd sc = new SendCmnd();
                    sc.execute(homeidVAR,pid2,iid2,off);
                    System.out.println("overTheNet : " + pid2 + iid2);
                }
                e.printStackTrace();
            }
        }
    }

    class Thread13G implements Runnable {
        public void run() {
            //Socket socket = null;
            try {
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                socket = new Socket(serverAddr, SERVER_PORT);
                OutputStream out = socket.getOutputStream();
                if (click.contains("3GoneOFF") || click.contains("3GtwoOFF") || click.contains("3GthreeOFF") || click.contains("3GfourOFF") || click.contains("3GfiveOFF")) {
                    byte[] by = hexStringToByteArray(protocolOFF3G.replaceAll(" ", ""));
                    out.write(by, 0, by.length);
                    out.flush();
                } else if (click.contains("3GoneON") || click.contains("3GtwoON") || click.contains("3GthreeON") || click.contains("3GfourON") || click.contains("3GfiveON")) {
                    byte[] by = hexStringToByteArray(protocolON3G.replaceAll(" ", ""));
                    out.write(by, 0, by.length);
                    out.flush();
                } else {
                    Toast.makeText(Home.this, "Something went wrong,check connection", Toast.LENGTH_SHORT).show();
                }

                Thread2 commThread = new Thread2(socket);
                new Thread(commThread).start();
                return;
            } catch (Exception e) {
                UIhandler.post(new UpdateButtonState(click.toUpperCase()));
                int pid = Integer.parseInt(v4c, 16); //taking powerlineid(hex) converting to integer
                String pid2 = String.valueOf(pid); // converting hex powerlineid to deciaml
                int iid = Integer.parseInt(v10c, 16); // " internal id
                String iid2 = String.valueOf(iid); // converting internal to decimal
                if (click.contains("OFF")) {
                    SendCmnd sc = new SendCmnd();
                    sc.execute(homeidVAR,pid2,iid2,on);
                    System.out.println("overTheNet : " + pid2 + iid2);
                } else {
                    SendCmnd sc = new SendCmnd();
                    sc.execute(homeidVAR,pid2,iid2,off);
                    System.out.println("overTheNet : " + pid2 + iid2);
                }
                e.printStackTrace();
            }
        }
    }

    class Thread1OTHER implements Runnable {
        public void run() {
            //Socket socket = null;
            try {
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                socket = new Socket(serverAddr, SERVER_PORT);
                OutputStream out = socket.getOutputStream();
                if (click.contains("2GoneOFF") || click.contains("2GtwoOFF")) {
                    byte[] by = hexStringToByteArray(protocolOFF2G.replaceAll(" ", ""));
                    out.write(by, 0, by.length);
                    out.flush();
                } else if (click.contains("2GoneON") || click.contains("2GtwoON")) {
                    byte[] by = hexStringToByteArray(protocolON2G.replaceAll(" ", ""));
                    out.write(by, 0, by.length);
                    out.flush();
                } else if (click.contains("1GoneON")) {
                    byte[] by = hexStringToByteArray(protocolON1G.replaceAll(" ", ""));
                    out.write(by, 0, by.length);
                    out.flush();
                } else if (click.contains("1GoneOFF")) {
                    byte[] by = hexStringToByteArray(protocolOFF1G.replaceAll(" ", ""));
                    out.write(by, 0, by.length);
                    out.flush();
                } else if (click.contains("1PLUGoneON")) {
                    byte[] by = hexStringToByteArray(protocolONPLUG.replaceAll(" ", ""));
                    out.write(by, 0, by.length);
                    out.flush();
                } else if (click.contains("1PLUGoneOFF")) {
                    byte[] by = hexStringToByteArray(protocolOFFPLUG.replaceAll(" ", ""));
                    out.write(by, 0, by.length);
                    out.flush();
                } else if (click.contains("btnonfan")) {
                    byte[] by = hexStringToByteArray(protocolONFAN.replaceAll(" ", ""));
                    out.write(by, 0, by.length);
                    out.flush();
                } else if (click.contains("btnofffan")) {
                    byte[] by = hexStringToByteArray(protocolOFFFAN.replaceAll(" ", ""));
                    out.write(by, 0, by.length);
                    out.flush();
                } else if (click.contains("btnplusfan")) {
                    byte[] by = hexStringToByteArray(protocolPLUSFAN.replaceAll(" ", ""));
                    out.write(by, 0, by.length);
                    out.flush();
                } else if (click.contains("btnminusfan")) {
                    byte[] by = hexStringToByteArray(protocolMINUSFAN.replaceAll(" ", ""));
                    out.write(by, 0, by.length);
                    out.flush();
                } else {
                    Toast.makeText(Home.this, "Something went wrong,check connection", Toast.LENGTH_SHORT).show();
                }
                Thread2 commThread = new Thread2(socket);
                new Thread(commThread).start();
                return;
            } catch (Exception e) {
                UIhandler.post(new UpdateButtonState(click.toUpperCase()));
                int pid = Integer.parseInt(v4c, 16); //taking powerlineid(hex) converting to integer
                String pid2 = String.valueOf(pid); // converting hex powerlineid to deciaml
                int iid = Integer.parseInt(v10c, 16); // " internal id
                String iid2 = String.valueOf(iid); // converting internal to decimal
                if (click.contains("OFF") || click.contains("off")) {
                    SendCmnd sc = new SendCmnd();
                    sc.execute(homeidVAR,pid2,iid2,on);
                    System.out.println("overTheNet : " + pid2 + iid2);
                } else if (click.contains("ON") || click.contains("on")) {
                    SendCmnd sc = new SendCmnd();
                    sc.execute(homeidVAR,pid2,iid2,off);
                    System.out.println("overTheNet : " + pid2 + iid2);
                } else if (click.contains("plusfan")) {
                    SendCmnd sc = new SendCmnd();
                    sc.execute(homeidVAR,pid2,fanintid,on);
                    System.out.println("overTheNet : " + pid2 + iid2);
                } else if (click.contains("minusfan")) {
                    SendCmnd sc = new SendCmnd();
                    sc.execute(homeidVAR,pid2,fanintid,on);
                    System.out.println("overTheNet : " + pid2 + iid2);
                }
                e.printStackTrace();
            }
        }
    }

    class UpdateButtonState implements Runnable {
        /*
        This method is used activate the button which was deactivated onclick while message was still pending from the device,
        this method shuld be used in exceptions. this method is used to combat the delay wen button is pressed.
         so the user doesnt press button many times.
         */
        String state;

        public UpdateButtonState(String str) {
            this.state = str;
        }

        @Override
        public void run() {
            if (click.equals("2GoneON")) {
                btnon1_2g.setEnabled(true);
            } else if (click.equals("2GoneOFF")) {
                btnoff1_2g.setEnabled(true);
            } else if (click.equals("2GtwoON")) {
                btnon2_2g.setEnabled(true);
            } else if (click.equals("2GtwoOFF")) {
                btnoff2_2g.setEnabled(true);
            } else if (click.equals("1PLUGoneON")) {
                on_1plug.setEnabled(true);
            } else if (click.equals("1PLUGoneOFF")) {
                off_1plug.setEnabled(true);
            }else if (click.equals("1GoneOFF")) {
                off_1g.setEnabled(true);
            } else if (click.equals("1GoneON")) {
                on_1g.setEnabled(true);
            } else if (click.equals("3GoneOFF")) {
                off_3g.setEnabled(true);
            } else if (click.equals("3GoneON")) {
                on_3g.setEnabled(true);
            } else if (click.equals("3GtwoOFF")) {
                off2_3g.setEnabled(true);
            } else if (click.equals("3GtwoON")) {
                on2_3g.setEnabled(true);
            } else if (click.equals("3GthreeOFF")) {
                off3_3g.setEnabled(true);
            } else if (click.equals("3GthreeON")) {
                on3_3g.setEnabled(true);
            }else if (click.equals("4GoneOFF")) {
                off_4g.setEnabled(true);
            } else if (click.equals("4GoneON")) {
                on_4g.setEnabled(true);
            } else if (click.equals("4GtwoOFF")) {
                off2_4g.setEnabled(true);
            } else if (click.equals("4GtwoON")) {
                on2_4g.setEnabled(true);
            } else if (click.equals("4GthreeOFF")) {
                off3_4g.setEnabled(true);
            } else if (click.equals("4GthreeON")) {
                on3_4g.setEnabled(true);
            } else if (click.equals("4GfourOFF")) {
                off4_4g.setEnabled(true);
            } else if (click.equals("4GfourON")) {
                on4_4g.setEnabled(true);
            } else if (click.equals("5GoneOFF")) {
                off_5g.setEnabled(true);
            } else if (click.equals("5GoneON")) {
                on_5g.setEnabled(true);
            } else if (click.equals("5GtwoOFF")) {
                off2_5g.setEnabled(true);
            } else if (click.equals("5GtwoON")) {
                on2_5g.setEnabled(true);
            } else if (click.equals("5GthreeOFF")) {
                off3_5g.setEnabled(true);
            } else if (click.equals("5GthreeON")) {
                on3_5g.setEnabled(true);
            } else if (click.equals("5GfourOFF")) {
                off4_5g.setEnabled(true);
            } else if (click.equals("5GfourON")) {
                on4_5g.setEnabled(true);
            } else if (click.equals("5GfiveOFF")) {
                off5_5g.setEnabled(true);
            } else if (click.equals("5GfiveON")) {
                on5_5g.setEnabled(true);
            }
        }
    }

    class Thread2 implements Runnable {

        public Thread2(Socket socket) {
            InputStream in = null;
            try {
                socket.setSoTimeout(4000);
                in = socket.getInputStream();
            } catch (IOException ex) {
                System.out.println("Can't get socket input stream. ");
            }

            System.out.println("Waiting Socket events ....");
            byte[] bytes = new byte[1];
            int count;

            String input_str = "";
            String temp_prev = "";
            String temp_curr = "";
            try {
                while ((count = in.read(bytes)) > 0) {
                    temp_curr = bytesToHex(bytes).toUpperCase();
                    if ((temp_prev + temp_curr).equals("0003") || (temp_prev + temp_curr).equals("AB03")) {
                        input_str += temp_curr;

                        System.out.print(input_str.toUpperCase());
                        clientrply = input_str;
                        UIhandler.post(new updateUIThread(input_str.toUpperCase()));
                        input_str += temp_curr;
                        input_str = "";
                        System.out.println();
                    } else {
                        input_str += temp_curr;
                    }
                    temp_prev = temp_curr;
                }

            } catch (Exception ex) {
                try {
                    if (clientrply == null) {
                        socket.close();
                        System.out.println("Socket Closed [in]");
                        runOnUiThread(new Runnable() {
                            public void run() {
                                //spinner.setVisibility(View.GONE);
                                //resp.setText("");
                                Toast.makeText(getApplicationContext(), "Something wrong with your device!", Toast.LENGTH_LONG).show();
                            }
                        });
                        UIhandler.post(new UpdateButtonState(click.toUpperCase()));
                    }
                } catch (IOException ex1) {
                    System.exit(0);
                }
            }
        }

        public void run() {
            //do nothing
        }
    }

    class updateUIThread implements Runnable {
        private String message;
        String val = powerlineidAR.get(v);//get powerline id to match with message recieved from device acknowledgment
        String val2 = powerlineidAR.get(j);//get powerline id to match with message recieved from device acknowledgment
        String val3 = dectohex(val);//convert powerline to hex value
        String val4 = dectohex(val2);//convert powerline to hex value
        String pwline = ("00" + val3).substring(val3.length());//substring powerline to 2 values if its originally only 1
        String pwlinej = ("00" + val4).substring(val4.length());//substring powerline to 2 values if its originally only 1

        public updateUIThread(String str) {
            this.message = str;
        }

        @Override
        public void run() {
            //spinner.setVisibility(View.GONE);
            //resp.setText("");
            System.out.println("updateUIThread reply :" + message + " " + val + ":" + pwline); //used for checking

            if (message.matches("(.*)" + pwline.toUpperCase() + "(.*)") || message.matches("(.*)" + pwlinej.toUpperCase() + "(.*)")) {
                System.out.println("thread reply :" + click);
                if (click.equals("2GoneON")) {
                    btnon1_2g.setVisibility(View.GONE);
                    btnoff1_2g.setVisibility(View.VISIBLE);
                } else if (click.equals("2GoneOFF")) {
                    btnon1_2g.setVisibility(View.VISIBLE);
                    btnoff1_2g.setVisibility(View.GONE);
                } else if (click.equals("2GtwoON")) {
                    btnon2_2g.setVisibility(View.GONE);
                    btnoff2_2g.setVisibility(View.VISIBLE);
                } else if (click.equals("2GtwoOFF")) {
                    btnon2_2g.setVisibility(View.VISIBLE);
                    btnoff2_2g.setVisibility(View.GONE);
                } else if (click.equals("1PLUGoneON")) {
                    off_1plug.setVisibility(View.VISIBLE);
                    on_1plug.setVisibility(View.GONE);
                } else if (click.equals("1PLUGoneOFF")) {
                    off_1plug.setVisibility(View.GONE);
                    on_1plug.setVisibility(View.VISIBLE);
                } else if (click.equals("1GoneOFF")) {
                    on_1g.setVisibility(View.VISIBLE);
                    off_1g.setVisibility(View.GONE);
                } else if (click.equals("1GoneON")) {
                    on_1g.setVisibility(View.GONE);
                    off_1g.setVisibility(View.VISIBLE);
                } else if (click.equals("3GoneOFF")) {
                    on_3g.setVisibility(View.VISIBLE);
                    off_3g.setVisibility(View.GONE);
                } else if (click.equals("3GoneON")) {
                    on_3g.setVisibility(View.GONE);
                    off_3g.setVisibility(View.VISIBLE);
                } else if (click.equals("3GtwoOFF")) {
                    on2_3g.setVisibility(View.VISIBLE);
                    off2_3g.setVisibility(View.GONE);
                } else if (click.equals("3GtwoON")) {
                    on2_3g.setVisibility(View.GONE);
                    off2_3g.setVisibility(View.VISIBLE);
                } else if (click.equals("3GthreeOFF")) {
                    on3_3g.setVisibility(View.VISIBLE);
                    off3_3g.setVisibility(View.GONE);
                } else if (click.equals("3GthreeON")) {
                    on3_3g.setVisibility(View.GONE);
                    off3_3g.setVisibility(View.VISIBLE);
                } else if (click.equals("4GoneOFF")) {
                    off_4g.setVisibility(View.GONE);
                    on_4g.setVisibility(View.VISIBLE);
                } else if (click.equals("4GoneON")) {
                    off_4g.setVisibility(View.VISIBLE);
                    on_4g.setVisibility(View.GONE);
                } else if (click.equals("4GtwoOFF")) {
                    off2_4g.setVisibility(View.GONE);
                    on2_4g.setVisibility(View.VISIBLE);
                } else if (click.equals("4GtwoON")) {
                    off2_4g.setVisibility(View.VISIBLE);
                    on2_4g.setVisibility(View.GONE);
                } else if (click.equals("4GthreeOFF")) {
                    off3_4g.setVisibility(View.GONE);
                    on3_4g.setVisibility(View.VISIBLE);
                } else if (click.equals("4GthreeON")) {
                    off3_4g.setVisibility(View.VISIBLE);
                    on3_4g.setVisibility(View.GONE);
                } else if (click.equals("4GfourOFF")) {
                    off4_4g.setVisibility(View.GONE);
                    on4_4g.setVisibility(View.VISIBLE);
                } else if (click.equals("4GfourON")) {
                    off4_4g.setVisibility(View.VISIBLE);
                    on4_4g.setVisibility(View.GONE);
                } else if (click.equals("5GoneOFF")) {
                    on_5g.setVisibility(View.VISIBLE);
                    off_5g.setVisibility(View.GONE);
                } else if (click.equals("5GoneON")) {
                    on_5g.setVisibility(View.GONE);
                    off_5g.setVisibility(View.VISIBLE);
                } else if (click.equals("5GtwoOFF")) {
                    on2_5g.setVisibility(View.VISIBLE);
                    off2_5g.setVisibility(View.GONE);
                } else if (click.equals("5GtwoON")) {
                    on2_5g.setVisibility(View.GONE);
                    off2_5g.setVisibility(View.VISIBLE);
                } else if (click.equals("5GthreeOFF")) {
                    on3_5g.setVisibility(View.VISIBLE);
                    off3_5g.setVisibility(View.GONE);
                } else if (click.equals("5GthreeON")) {
                    on3_5g.setVisibility(View.GONE);
                    off3_5g.setVisibility(View.VISIBLE);
                } else if (click.equals("5GfourOFF")) {
                    on4_5g.setVisibility(View.VISIBLE);
                    off4_5g.setVisibility(View.GONE);
                } else if (click.equals("5GfourON")) {
                    on4_5g.setVisibility(View.GONE);
                    off4_5g.setVisibility(View.VISIBLE);
                } else if (click.equals("5GfiveOFF")) {
                    on5_5g.setVisibility(View.VISIBLE);
                    off5_5g.setVisibility(View.GONE);
                } else if (click.equals("5GfiveON")) {
                    on5_5g.setVisibility(View.GONE);
                    off5_5g.setVisibility(View.VISIBLE);
                }
                UIhandler.post(new UpdateButtonState(click.toUpperCase()));
            }
        }
    }

    public void getcontroller() {

        if (pidfkARDB.size() > 0) {
            pidfkARDB.clear();
            contrlidARDB.clear();
            internalidARDB.clear();
            cntrlnameARDB.clear();
            contrltypeARDB.clear();
            contrlstatusARDB.clear();
        }

        sqLiteDatabase = db.getReadableDatabase();
        String v4 = powerlineidAR.get(v);
        SERVER_IP = ipaddress.get(v);
        cursor3 = db.getController(v4, sqLiteDatabase);

        cursor3.moveToFirst();
        do {
            pidfkDB = cursor3.getString(0);
            contrlidDB = cursor3.getString(1);
            internalidDB = cursor3.getString(2);
            contrlnameDB = cursor3.getString(3);
            cntrlstatusDB = cursor3.getString(4);
            String v1 = cursor3.getString(5);

            System.out.println("getcontroller info through PID : " + pidfkDB + " " + contrlidDB + " " + internalidDB + " " + contrlnameDB + " " + cntrlstatusDB + " " + v1);
            pidfkARDB.add(pidfkDB);
            contrlidARDB.add(contrlidDB);
            internalidARDB.add(internalidDB);
            cntrlnameARDB.add(contrlnameDB);
            contrltypeARDB.add(contrtypeVAR);
            contrlstatusARDB.add(cntrlstatusDB);
        } while (cursor3.moveToNext());
    }

    public void btnoff5g(View view) {
//        on_5g.setVisibility(View.VISIBLE);
        off_5g.setEnabled(false);
        System.out.println("5g1off");
        point = v;
        int point2 = 1;
        click = "5GoneOFF";
        getcontroller();
        // Device : [
        String v1 = devicenameAR.get(point);
        String v2 = areaAR.get(point);
        String v3 = physicalidAR.get(point);
        String v4 = powerlineidAR.get(point);
        String v5 = devicecodeAR.get(point);
        String v6 = commandidAR.get(point);
        String v7 = masteridAR.get(point);
        // ]

        //Controller : [
        String v8 = pidfkARDB.get(point2);
        String v9 = contrlidARDB.get(point2);
        String v10 = internalidARDB.get(point2);
        String v11 = contrltypeARDB.get(point2);
        String v12 = contrlstatusARDB.get(point2);
        // ] 02 03 00 00 00 83 03 48 00 00 00 00 00 00 00 34 06 01 AB 03
        String v4c1 = dectohex(v4);
        String v7c1 = dectohex(v7);
        String v6c1 = dectohex(v6);
        String v10c1 = dectohex(v10);

        v4c = ("00" + v4c1).substring(v4c1.length());
        String v7c = ("00" + v7c1).substring(v7c1.length());
        String v6c = ("00" + v6c1).substring(v6c1.length());
        v10c = ("00" + v10c1).substring(v10c1.length());
        protocolOFF = String.format("02 %s 00 00 00 83 03 %s 00 00 00 00 00 00 00 %s %s 01 AB 03", v7c, v4c, v6c, v10c);
        System.out.println(protocolOFF);
        devicestatus = v1;

        this.Thread1 = new Thread(new Thread1());
        this.Thread1.start();
    }

    public void btnon5g(View view) {
//        off_5g.setVisibility(View.VISIBLE);
        on_5g.setEnabled(false);
        System.out.println("5g1on");
        click = "5GoneON";
        int point = v;
        int point2 = 1;
        getcontroller();
        // Device : [
        String v1 = devicenameAR.get(point);
        String v2 = areaAR.get(point);
        String v3 = physicalidAR.get(point);
        String v4 = powerlineidAR.get(point);
        String v5 = devicecodeAR.get(point);
        String v6 = commandidAR.get(point);
        String v7 = masteridAR.get(point);
        // ]

        //Controller : [
        String v8 = pidfkARDB.get(point2);
        String v9 = contrlidARDB.get(point2);
        String v10 = internalidARDB.get(point2);
        String v11 = contrltypeARDB.get(point2);
        String v12 = contrlstatusARDB.get(point2);
        // ] 02 03 00 00 00 83 03 48 00 00 00 00 00 00 00 34 06 01 AB 03
        String v4c1 = dectohex(v4);
        String v7c1 = dectohex(v7);
        String v6c1 = dectohex(v6);
        String v10c1 = dectohex(v10);

        v4c = ("00" + v4c1).substring(v4c1.length());
        String v7c = ("00" + v7c1).substring(v7c1.length());
        String v6c = ("00" + v6c1).substring(v6c1.length());
        v10c = ("00" + v10c1).substring(v10c1.length());
        protocolON = String.format("02 %s 00 00 00 83 03 %s 00 00 00 00 00 00 00 %s %s 02 AB 03", v7c, v4c, v6c, v10c);
        System.out.println(protocolON);
        devicestatus = v1;

        this.Thread1 = new Thread(new Thread1());
        this.Thread1.start();
    }

    public void btn1off5g(View view) {
//        on2_5g.setVisibility(View.VISIBLE);
        off2_5g.setEnabled(false);
        System.out.println("5g2off");
        click = "5GtwoOFF";
        int point = v;
        int point2 = 2;
        getcontroller();
        // Device : [
        String v1 = devicenameAR.get(point);
        String v2 = areaAR.get(point);
        String v3 = physicalidAR.get(point);
        String v4 = powerlineidAR.get(point);
        String v5 = devicecodeAR.get(point);
        String v6 = commandidAR.get(point);
        String v7 = masteridAR.get(point);
        // ]

        //Controller : [
        String v8 = pidfkARDB.get(point2);
        String v9 = contrlidARDB.get(point2);
        String v10 = internalidARDB.get(point2);
        String v11 = contrltypeARDB.get(point2);
        String v12 = contrlstatusARDB.get(point2);
        // ] 02 03 00 00 00 83 03 48 00 00 00 00 00 00 00 34 06 01 AB 03
        String v4c1 = dectohex(v4);
        String v7c1 = dectohex(v7);
        String v6c1 = dectohex(v6);
        String v10c1 = dectohex(v10);

        v4c = ("00" + v4c1).substring(v4c1.length());
        String v7c = ("00" + v7c1).substring(v7c1.length());
        String v6c = ("00" + v6c1).substring(v6c1.length());
        v10c = ("00" + v10c1).substring(v10c1.length());
        protocolOFF = String.format("02 %s 00 00 00 83 03 %s 00 00 00 00 00 00 00 %s %s 01 AB 03", v7c, v4c, v6c, v10c);
        System.out.println(protocolOFF);
        devicestatus = v1;

        this.Thread1 = new Thread(new Thread1());
        this.Thread1.start();
    }

    public void btn1on5g(View view) {
//        off2_5g.setVisibility(View.VISIBLE);
        on2_5g.setEnabled(false);
        System.out.println("5g2on");
        click = "5GtwoON";
        int point = v;
        int point2 = 2;
        getcontroller();
        // Device : [
        String v1 = devicenameAR.get(point);
        String v2 = areaAR.get(point);
        String v3 = physicalidAR.get(point);
        String v4 = powerlineidAR.get(point);
        String v5 = devicecodeAR.get(point);
        String v6 = commandidAR.get(point);
        String v7 = masteridAR.get(point);
        // ]

        //Controller : [
        String v8 = pidfkARDB.get(point2);
        String v9 = contrlidARDB.get(point2);
        String v10 = internalidARDB.get(point2);
        String v11 = contrltypeARDB.get(point2);
        String v12 = contrlstatusARDB.get(point2);
        // ] 02 03 00 00 00 83 03 48 00 00 00 00 00 00 00 34 06 01 AB 03
        String v4c1 = dectohex(v4);
        String v7c1 = dectohex(v7);
        String v6c1 = dectohex(v6);
        String v10c1 = dectohex(v10);

        v4c = ("00" + v4c1).substring(v4c1.length());
        String v7c = ("00" + v7c1).substring(v7c1.length());
        String v6c = ("00" + v6c1).substring(v6c1.length());
        v10c = ("00" + v10c1).substring(v10c1.length());
        protocolON = String.format("02 %s 00 00 00 83 03 %s 00 00 00 00 00 00 00 %s %s 02 AB 03", v7c, v4c, v6c, v10c);
        System.out.println(protocolON);
        devicestatus = v1;

        this.Thread1 = new Thread(new Thread1());
        this.Thread1.start();
    }

    public void btn2off5g(View view) {
//        on3_5g.setVisibility(View.VISIBLE);
        off3_5g.setEnabled(false);
        System.out.println("5g3off");
        click = "5GthreeOFF";
        int point = v;
        int point2 = 3;
        getcontroller();
        // Device : [
        String v1 = devicenameAR.get(point);
        String v2 = areaAR.get(point);
        String v3 = physicalidAR.get(point);
        String v4 = powerlineidAR.get(point);
        String v5 = devicecodeAR.get(point);
        String v6 = commandidAR.get(point);
        String v7 = masteridAR.get(point);
        // ]

        //Controller : [
        String v8 = pidfkARDB.get(point2);
        String v9 = contrlidARDB.get(point2);
        String v10 = internalidARDB.get(point2);
        String v11 = contrltypeARDB.get(point2);
        String v12 = contrlstatusARDB.get(point2);
        // ] 02 03 00 00 00 83 03 48 00 00 00 00 00 00 00 34 06 01 AB 03
        String v4c1 = dectohex(v4);
        String v7c1 = dectohex(v7);
        String v6c1 = dectohex(v6);
        String v10c1 = dectohex(v10);

        v4c = ("00" + v4c1).substring(v4c1.length());
        String v7c = ("00" + v7c1).substring(v7c1.length());
        String v6c = ("00" + v6c1).substring(v6c1.length());
        v10c = ("00" + v10c1).substring(v10c1.length());
        protocolOFF = String.format("02 %s 00 00 00 83 03 %s 00 00 00 00 00 00 00 %s %s 01 AB 03", v7c, v4c, v6c, v10c);
        System.out.println(protocolOFF);
        devicestatus = v1;

        this.Thread1 = new Thread(new Thread1());
        this.Thread1.start();
    }

    public void btn2on5g(View view) {
//        off3_5g.setVisibility(View.VISIBLE);
        on3_5g.setEnabled(false);
        System.out.println("5g3on");
        click = "5GthreeON";
        int point = v;
        int point2 = 3;
        getcontroller();
        // Device : [
        String v1 = devicenameAR.get(point);
        String v2 = areaAR.get(point);
        String v3 = physicalidAR.get(point);
        String v4 = powerlineidAR.get(point);
        String v5 = devicecodeAR.get(point);
        String v6 = commandidAR.get(point);
        String v7 = masteridAR.get(point);
        // ]

        //Controller : [
        String v8 = pidfkARDB.get(point2);
        String v9 = contrlidARDB.get(point2);
        String v10 = internalidARDB.get(point2);
        String v11 = contrltypeARDB.get(point2);
        String v12 = contrlstatusARDB.get(point2);
        // ] 02 03 00 00 00 83 03 48 00 00 00 00 00 00 00 34 06 01 AB 03
        String v4c1 = dectohex(v4);
        String v7c1 = dectohex(v7);
        String v6c1 = dectohex(v6);
        String v10c1 = dectohex(v10);

        v4c = ("00" + v4c1).substring(v4c1.length());
        String v7c = ("00" + v7c1).substring(v7c1.length());
        String v6c = ("00" + v6c1).substring(v6c1.length());
        v10c = ("00" + v10c1).substring(v10c1.length());
        protocolOFF = String.format("02 %s 00 00 00 83 03 %s 00 00 00 00 00 00 00 %s %s 02 AB 03", v7c, v4c, v6c, v10c);
        System.out.println(protocolOFF);

        this.Thread1 = new Thread(new Thread1());
        this.Thread1.start();
    }

    public void btn3off5g(View view) {
//        on4_5g.setVisibility(View.VISIBLE);
        off4_5g.setEnabled(false);
        System.out.println("5g4off");
        click = "5GfourOFF";
        int point = v;
        int point2 = 4;
        getcontroller();
        // Device : [
        String v1 = devicenameAR.get(point);
        String v2 = areaAR.get(point);
        String v3 = physicalidAR.get(point);
        String v4 = powerlineidAR.get(point);
        String v5 = devicecodeAR.get(point);
        String v6 = commandidAR.get(point);
        String v7 = masteridAR.get(point);
        // ]

        //Controller : [
        String v8 = pidfkARDB.get(point2);
        String v9 = contrlidARDB.get(point2);
        String v10 = internalidARDB.get(point2);
        String v11 = contrltypeARDB.get(point2);
        String v12 = contrlstatusARDB.get(point2);
        // ] 02 03 00 00 00 83 03 48 00 00 00 00 00 00 00 34 06 01 AB 03
        String v4c1 = dectohex(v4);
        String v7c1 = dectohex(v7);
        String v6c1 = dectohex(v6);
        String v10c1 = dectohex(v10);

        v4c = ("00" + v4c1).substring(v4c1.length());
        String v7c = ("00" + v7c1).substring(v7c1.length());
        String v6c = ("00" + v6c1).substring(v6c1.length());
        v10c = ("00" + v10c1).substring(v10c1.length());
        protocolOFF = String.format("02 %s 00 00 00 83 03 %s 00 00 00 00 00 00 00 %s %s 01 AB 03", v7c, v4c, v6c, v10c);
        System.out.println(protocolOFF);
        devicestatus = v1;

        this.Thread1 = new Thread(new Thread1());
        this.Thread1.start();
    }

    public void btn3on5g(View view) {
//        off4_5g.setVisibility(View.VISIBLE);
        on4_5g.setEnabled(false);
        System.out.println("5g4on");
        click = "5GfourON";
        int point = v;
        int point2 = 4;
        getcontroller();
        // Device : [
        String v1 = devicenameAR.get(point);
        String v2 = areaAR.get(point);
        String v3 = physicalidAR.get(point);
        String v4 = powerlineidAR.get(point);
        String v5 = devicecodeAR.get(point);
        String v6 = commandidAR.get(point);
        String v7 = masteridAR.get(point);
        // ]

        //Controller : [
        String v8 = pidfkARDB.get(point2);
        String v9 = contrlidARDB.get(point2);
        String v10 = internalidARDB.get(point2);
        String v11 = contrltypeARDB.get(point2);
        String v12 = contrlstatusARDB.get(point2);
        // ] 02 03 00 00 00 83 03 48 00 00 00 00 00 00 00 34 06 01 AB 03
        String v4c1 = dectohex(v4);
        String v7c1 = dectohex(v7);
        String v6c1 = dectohex(v6);
        String v10c1 = dectohex(v10);

        v4c = ("00" + v4c1).substring(v4c1.length());
        String v7c = ("00" + v7c1).substring(v7c1.length());
        String v6c = ("00" + v6c1).substring(v6c1.length());
        v10c = ("00" + v10c1).substring(v10c1.length());
        protocolON = String.format("02 %s 00 00 00 83 03 %s 00 00 00 00 00 00 00 %s %s 02 AB 03", v7c, v4c, v6c, v10c);
        System.out.println(protocolON);
        devicestatus = v1;

        this.Thread1 = new Thread(new Thread1());
        this.Thread1.start();
    }

    public void btn4off5g(View view) {
//        on5_5g.setVisibility(View.VISIBLE);
        off5_5g.setEnabled(false);
        System.out.println("5g5off");
        click = "5GfiveOFF";
        int point = v;
        int point2 = 5;
        getcontroller();
        // Device : [
        String v1 = devicenameAR.get(point);
        String v2 = areaAR.get(point);
        String v3 = physicalidAR.get(point);
        String v4 = powerlineidAR.get(point);
        String v5 = devicecodeAR.get(point);
        String v6 = commandidAR.get(point);
        String v7 = masteridAR.get(point);
        // ]

        //Controller : [
        String v8 = pidfkARDB.get(point2);
        String v9 = contrlidARDB.get(point2);
        String v10 = internalidARDB.get(point2);
        String v11 = contrltypeARDB.get(point2);
        String v12 = contrlstatusARDB.get(point2);
        // ] 02 03 00 00 00 83 03 48 00 00 00 00 00 00 00 34 06 01 AB 03
        String v4c1 = dectohex(v4);
        String v7c1 = dectohex(v7);
        String v6c1 = dectohex(v6);
        String v10c1 = dectohex(v10);

        v4c = ("00" + v4c1).substring(v4c1.length());
        String v7c = ("00" + v7c1).substring(v7c1.length());
        String v6c = ("00" + v6c1).substring(v6c1.length());
        v10c = ("00" + v10c1).substring(v10c1.length());
        protocolOFF = String.format("02 %s 00 00 00 83 03 %s 00 00 00 00 00 00 00 %s %s 01 AB 03", v7c, v4c, v6c, v10c);
        System.out.println(protocolOFF);
        devicestatus = v1;

        this.Thread1 = new Thread(new Thread1());
        this.Thread1.start();
    }

    public void btn4on5g(View view) {
//        off5_5g.setVisibility(View.VISIBLE);
        on5_5g.setEnabled(false);
        System.out.println("5g5on");
        click = "5GfiveON";
        int point = v;
        int point2 = 5;
        getcontroller();
        // Device : [
        String v1 = devicenameAR.get(point);
        String v2 = areaAR.get(point);
        String v3 = physicalidAR.get(point);
        String v4 = powerlineidAR.get(point);
        String v5 = devicecodeAR.get(point);
        String v6 = commandidAR.get(point);
        String v7 = masteridAR.get(point);
        // ]

        //Controller : [
        String v8 = pidfkARDB.get(point2);
        String v9 = contrlidARDB.get(point2);
        String v10 = internalidARDB.get(point2);
        String v11 = contrltypeARDB.get(point2);
        String v12 = contrlstatusARDB.get(point2);
        // ] 02 03 00 00 00 83 03 48 00 00 00 00 00 00 00 34 06 01 AB 03
        String v4c1 = dectohex(v4);
        String v7c1 = dectohex(v7);
        String v6c1 = dectohex(v6);
        String v10c1 = dectohex(v10);

        v4c = ("00" + v4c1).substring(v4c1.length());
        String v7c = ("00" + v7c1).substring(v7c1.length());
        String v6c = ("00" + v6c1).substring(v6c1.length());
        v10c = ("00" + v10c1).substring(v10c1.length());
        protocolON = String.format("02 %s 00 00 00 83 03 %s 00 00 00 00 00 00 00 %s %s 02 AB 03", v7c, v4c, v6c, v10c);
        System.out.println(protocolON);
        devicestatus = v1;

        this.Thread1 = new Thread(new Thread1());
        this.Thread1.start();
    }

    public void btnoff4g(View view) {
//        on_4g.setVisibility(View.VISIBLE);
        off_4g.setEnabled(false);
        int point = v;
        int point2 = 1;
        System.out.println("4g1off");
        click = "4GoneOFF";
        getcontroller();
        // Device : [
        String v1 = devicenameAR.get(point);
        String v2 = areaAR.get(point);
        String v3 = physicalidAR.get(point);
        String v4 = powerlineidAR.get(point);
        String v5 = devicecodeAR.get(point);
        String v6 = commandidAR.get(point);
        String v7 = masteridAR.get(point);
        // ]

        //Controller : [
        String v8 = pidfkARDB.get(point2);
        String v9 = contrlidARDB.get(point2);
        String v10 = internalidARDB.get(point2);
        String v11 = contrltypeARDB.get(point2);
        String v12 = contrlstatusARDB.get(point2);
        // ] 02 03 00 00 00 83 03 48 00 00 00 00 00 00 00 34 06 01 AB 03
        String v4c1 = dectohex(v4);
        String v7c1 = dectohex(v7);
        String v6c1 = dectohex(v6);
        String v10c1 = dectohex(v10);

        v4c = ("00" + v4c1).substring(v4c1.length());
        String v7c = ("00" + v7c1).substring(v7c1.length());
        String v6c = ("00" + v6c1).substring(v6c1.length());
        v10c = ("00" + v10c1).substring(v10c1.length());
        protocolOFF4G = String.format("02 %s 00 00 00 83 03 %s 00 00 00 00 00 00 00 %s %s 01 AB 03", v7c, v4c, v6c, v10c);
        System.out.println(protocolOFF4G);
        devicestatus = v1;

        this.Thread14G = new Thread(new Thread14G());
        this.Thread14G.start();
    }

    public void btnon4g(View view) {
//        off_4g.setVisibility(View.VISIBLE);
        on_4g.setEnabled(false);
        int point = v;
        System.out.println("4g1on");
        click = "4GoneON";
        getcontroller();
        // Device : [
        String v1 = devicenameAR.get(point);
        String v2 = areaAR.get(point);
        String v3 = physicalidAR.get(point);
        String v4 = powerlineidAR.get(point);
        String v5 = devicecodeAR.get(point);
        String v6 = commandidAR.get(point);
        String v7 = masteridAR.get(point);
        // ]

        int point2 = 1;
        String v8 = pidfkARDB.get(point2);
        String v9 = contrlidARDB.get(point2);
        String v10 = internalidARDB.get(point2);
        String v11 = contrltypeARDB.get(point2);
        String v12 = contrlstatusARDB.get(point2);
        // ] 02 03 00 00 00 83 03 48 00 00 00 00 00 00 00 34 06 01 AB 03
        String v4c1 = dectohex(v4);
        String v7c1 = dectohex(v7);
        String v6c1 = dectohex(v6);
        String v10c1 = dectohex(v10);

        v4c = ("00" + v4c1).substring(v4c1.length());
        String v7c = ("00" + v7c1).substring(v7c1.length());
        String v6c = ("00" + v6c1).substring(v6c1.length());
        v10c = ("00" + v10c1).substring(v10c1.length());
        protocolON4G = String.format("02 %s 00 00 00 83 03 %s 00 00 00 00 00 00 00 %s %s 02 AB 03", v7c, v4c, v6c, v10c);
        System.out.println(protocolON4G);
        devicestatus = v1;

        this.Thread14G = new Thread(new Thread14G());
        this.Thread14G.start();
    }

    public void btn1off4g(View view) {
//        on2_4g.setVisibility(View.VISIBLE);
        off2_4g.setEnabled(false);
        int point = v;
        System.out.println("4g2off");
        click = "4GtwoOFF";
        getcontroller();
        // Device : [
        String v1 = devicenameAR.get(point);
        String v2 = areaAR.get(point);
        String v3 = physicalidAR.get(point);
        String v4 = powerlineidAR.get(point);
        String v5 = devicecodeAR.get(point);
        String v6 = commandidAR.get(point);
        String v7 = masteridAR.get(point);
        // ]

        //Controller : [
        int point2 = 2;
        String v8 = pidfkARDB.get(point2);
        String v9 = contrlidARDB.get(point2);
        String v10 = internalidARDB.get(point2);
        String v11 = contrltypeARDB.get(point2);
        String v12 = contrlstatusARDB.get(point2);
        // ] 02 03 00 00 00 83 03 48 00 00 00 00 00 00 00 34 06 01 AB 03
        String v4c1 = dectohex(v4);
        String v7c1 = dectohex(v7);
        String v6c1 = dectohex(v6);
        String v10c1 = dectohex(v10);

        v4c = ("00" + v4c1).substring(v4c1.length());
        String v7c = ("00" + v7c1).substring(v7c1.length());
        String v6c = ("00" + v6c1).substring(v6c1.length());
        v10c = ("00" + v10c1).substring(v10c1.length());
        protocolOFF4G = String.format("02 %s 00 00 00 83 03 %s 00 00 00 00 00 00 00 %s %s 01 AB 03", v7c, v4c, v6c, v10c);
        System.out.println(protocolOFF4G);
        devicestatus = v1;
        this.Thread14G = new Thread(new Thread14G());
        this.Thread14G.start();
    }

    public void btn1on4g(View view) {
//        off2_4g.setVisibility(View.VISIBLE);
        on2_4g.setEnabled(false);
        int point = v;
        System.out.println("4g2on");
        click = "4GtwoON";
        getcontroller();
        // Device : [
        String v1 = devicenameAR.get(point);
        String v2 = areaAR.get(point);
        String v3 = physicalidAR.get(point);
        String v4 = powerlineidAR.get(point);
        String v5 = devicecodeAR.get(point);
        String v6 = commandidAR.get(point);
        String v7 = masteridAR.get(point);
        // ]

        //Controller : [
        int point2 = 2;
        String v8 = pidfkARDB.get(point2);
        String v9 = contrlidARDB.get(point2);
        String v10 = internalidARDB.get(point2);
        String v11 = contrltypeARDB.get(point2);
        String v12 = contrlstatusARDB.get(point2);
        // ] 02 03 00 00 00 83 03 48 00 00 00 00 00 00 00 34 06 01 AB 03
        String v4c1 = dectohex(v4);
        String v7c1 = dectohex(v7);
        String v6c1 = dectohex(v6);
        String v10c1 = dectohex(v10);

        v4c = ("00" + v4c1).substring(v4c1.length());
        String v7c = ("00" + v7c1).substring(v7c1.length());
        String v6c = ("00" + v6c1).substring(v6c1.length());
        v10c = ("00" + v10c1).substring(v10c1.length());
        protocolON4G = String.format("02 %s 00 00 00 83 03 %s 00 00 00 00 00 00 00 %s %s 02 AB 03", v7c, v4c, v6c, v10c);
        System.out.println(protocolON4G);
        devicestatus = v1;

        this.Thread14G = new Thread(new Thread14G());
        this.Thread14G.start();
    }

    public void btn2off4g(View view) {
//        on3_4g.setVisibility(View.VISIBLE);
        off3_4g.setEnabled(false);
        int point = v;
        System.out.println("4g3off");
        click = "4GthreeOFF";
        getcontroller();
        // Device : [
        String v1 = devicenameAR.get(point);
        String v2 = areaAR.get(point);
        String v3 = physicalidAR.get(point);
        String v4 = powerlineidAR.get(point);
        String v5 = devicecodeAR.get(point);
        String v6 = commandidAR.get(point);
        String v7 = masteridAR.get(point);
        // ]

        //Controller : [
        int point2 = 3;
        String v8 = pidfkARDB.get(point2);
        String v9 = contrlidARDB.get(point2);
        String v10 = internalidARDB.get(point2);
        String v11 = contrltypeARDB.get(point2);
        String v12 = contrlstatusARDB.get(point2);
        // ] 02 03 00 00 00 83 03 48 00 00 00 00 00 00 00 34 06 01 AB 03
        String v4c1 = dectohex(v4);
        String v7c1 = dectohex(v7);
        String v6c1 = dectohex(v6);
        String v10c1 = dectohex(v10);

        v4c = ("00" + v4c1).substring(v4c1.length());
        String v7c = ("00" + v7c1).substring(v7c1.length());
        String v6c = ("00" + v6c1).substring(v6c1.length());
        v10c = ("00" + v10c1).substring(v10c1.length());
        protocolOFF4G = String.format("02 %s 00 00 00 83 03 %s 00 00 00 00 00 00 00 %s %s 01 AB 03", v7c, v4c, v6c, v10c);
        System.out.println(protocolOFF4G);
        devicestatus = v1;

        this.Thread14G = new Thread(new Thread14G());
        this.Thread14G.start();
    }

    public void btn2on4g(View view) {
//        off3_4g.setVisibility(View.VISIBLE);
        on3_4g.setEnabled(false);
        int point = v;
        System.out.println("4g3on");
        click = "4GthreeON";
        getcontroller();
        // Device : [
        String v1 = devicenameAR.get(point);
        String v2 = areaAR.get(point);
        String v3 = physicalidAR.get(point);
        String v4 = powerlineidAR.get(point);
        String v5 = devicecodeAR.get(point);
        String v6 = commandidAR.get(point);
        String v7 = masteridAR.get(point);
        // ]

        //Controller : [
        int point2 = 3;
        String v8 = pidfkARDB.get(point2);
        String v9 = contrlidARDB.get(point2);
        String v10 = internalidARDB.get(point2);
        String v11 = contrltypeARDB.get(point2);
        String v12 = contrlstatusARDB.get(point2);
        // ] 02 03 00 00 00 83 03 48 00 00 00 00 00 00 00 34 06 01 AB 03
        String v4c1 = dectohex(v4);
        String v7c1 = dectohex(v7);
        String v6c1 = dectohex(v6);
        String v10c1 = dectohex(v10);

        v4c = ("00" + v4c1).substring(v4c1.length());
        String v7c = ("00" + v7c1).substring(v7c1.length());
        String v6c = ("00" + v6c1).substring(v6c1.length());
        v10c = ("00" + v10c1).substring(v10c1.length());
        protocolON4G = String.format("02 %s 00 00 00 83 03 %s 00 00 00 00 00 00 00 %s %s 02 AB 03", v7c, v4c, v6c, v10c);
        System.out.println(protocolON4G);
        devicestatus = v1;

        this.Thread14G = new Thread(new Thread14G());
        this.Thread14G.start();
    }

    public void btn3off4g(View view) {
//        on4_4g.setVisibility(View.VISIBLE);
        off4_4g.setEnabled(false);
        int point = v;
        System.out.println("4g4off");
        click = "4GfourOFF";
        getcontroller();
        // Device : [
        String v1 = devicenameAR.get(point);
        String v2 = areaAR.get(point);
        String v3 = physicalidAR.get(point);
        String v4 = powerlineidAR.get(point);
        String v5 = devicecodeAR.get(point);
        String v6 = commandidAR.get(point);
        String v7 = masteridAR.get(point);
        // ]

        //Controller : [
        int point2 = 4;
        String v8 = pidfkARDB.get(point2);
        String v9 = contrlidARDB.get(point2);
        String v10 = internalidARDB.get(point2);
        String v11 = contrltypeARDB.get(point2);
        String v12 = contrlstatusARDB.get(point2);
        // ] 02 03 00 00 00 83 03 48 00 00 00 00 00 00 00 34 06 01 AB 03
        String v4c1 = dectohex(v4);
        String v7c1 = dectohex(v7);
        String v6c1 = dectohex(v6);
        String v10c1 = dectohex(v10);

        v4c = ("00" + v4c1).substring(v4c1.length());
        String v7c = ("00" + v7c1).substring(v7c1.length());
        String v6c = ("00" + v6c1).substring(v6c1.length());
        v10c = ("00" + v10c1).substring(v10c1.length());
        protocolOFF4G = String.format("02 %s 00 00 00 83 03 %s 00 00 00 00 00 00 00 %s %s 01 AB 03", v7c, v4c, v6c, v10c);
        System.out.println(protocolOFF4G);
        devicestatus = v1;

        this.Thread14G = new Thread(new Thread14G());
        this.Thread14G.start();
    }

    public void btn3on4g(View view) {
//        off4_4g.setVisibility(View.VISIBLE);
        on4_4g.setEnabled(false);
        int point = v;
        System.out.println("4g4on");
        click = "4GfourON";
        getcontroller();
        // Device : [
        String v1 = devicenameAR.get(point);
        String v2 = areaAR.get(point);
        String v3 = physicalidAR.get(point);
        String v4 = powerlineidAR.get(point);
        String v5 = devicecodeAR.get(point);
        String v6 = commandidAR.get(point);
        String v7 = masteridAR.get(point);
        // ]

        //Controller : [
        int point2 = 4;
        String v8 = pidfkARDB.get(point2);
        String v9 = contrlidARDB.get(point2);
        String v10 = internalidARDB.get(point2);
        String v11 = contrltypeARDB.get(point2);
        String v12 = contrlstatusARDB.get(point2);
        // ] 02 03 00 00 00 83 03 48 00 00 00 00 00 00 00 34 06 01 AB 03
        String v4c1 = dectohex(v4);
        String v7c1 = dectohex(v7);
        String v6c1 = dectohex(v6);
        String v10c1 = dectohex(v10);

        v4c = ("00" + v4c1).substring(v4c1.length());
        String v7c = ("00" + v7c1).substring(v7c1.length());
        String v6c = ("00" + v6c1).substring(v6c1.length());
        v10c = ("00" + v10c1).substring(v10c1.length());
        protocolON4G = String.format("02 %s 00 00 00 83 03 %s 00 00 00 00 00 00 00 %s %s 02 AB 03", v7c, v4c, v6c, v10c);
        System.out.println(protocolON4G);
        devicestatus = v1;

        this.Thread14G = new Thread(new Thread14G());
        this.Thread14G.start();
    }

    public void btnoff3g(View view) {
//        on_3g.setVisibility(View.VISIBLE);
        off_3g.setEnabled(false);
        int point = v;
        System.out.println("3g1off");
        click = "3GoneOFF";
        getcontroller();
        // Device : [
        String v1 = devicenameAR.get(point);
        String v2 = areaAR.get(point);
        String v3 = physicalidAR.get(point);
        String v4 = powerlineidAR.get(point);
        String v5 = devicecodeAR.get(point);
        String v6 = commandidAR.get(point);
        String v7 = masteridAR.get(point);
        // ]

        //Controller : [
        int point2 = 1;
        String v8 = pidfkARDB.get(point2);
        String v9 = contrlidARDB.get(point2);
        String v10 = internalidARDB.get(point2);
        String v11 = contrltypeARDB.get(point2);
        String v12 = contrlstatusARDB.get(point2);
        // ] 02 03 00 00 00 83 03 48 00 00 00 00 00 00 00 34 06 01 AB 03
        String v4c1 = dectohex(v4);
        String v7c1 = dectohex(v7);
        String v6c1 = dectohex(v6);
        String v10c1 = dectohex(v10);

        v4c = ("00" + v4c1).substring(v4c1.length());
        String v7c = ("00" + v7c1).substring(v7c1.length());
        String v6c = ("00" + v6c1).substring(v6c1.length());
        v10c = ("00" + v10c1).substring(v10c1.length());
        protocolOFF3G = String.format("02 %s 00 00 00 83 03 %s 00 00 00 00 00 00 00 %s %s 01 AB 03", v7c, v4c, v6c, v10c);
        System.out.println(protocolOFF3G);
        devicestatus = v1;

        this.Thread13G = new Thread(new Thread13G());
        this.Thread13G.start();
    }

    public void btnon3g(View view) {
//        off_3g.setVisibility(View.VISIBLE);
        on_3g.setEnabled(false);
        int point = v;
        System.out.println("3g1oN");
        click = "3GoneON";
        getcontroller();
        // Device : [
        String v1 = devicenameAR.get(point);
        String v2 = areaAR.get(point);
        String v3 = physicalidAR.get(point);
        String v4 = powerlineidAR.get(point);
        String v5 = devicecodeAR.get(point);
        String v6 = commandidAR.get(point);
        String v7 = masteridAR.get(point);
        // ]

        //Controller : [
        int point2 = 1;
        String v8 = pidfkARDB.get(point2);
        String v9 = contrlidARDB.get(point2);
        String v10 = internalidARDB.get(point2);
        String v11 = contrltypeARDB.get(point2);
        String v12 = contrlstatusARDB.get(point2);
        // ] 02 03 00 00 00 83 03 48 00 00 00 00 00 00 00 34 06 01 AB 03
        String v4c1 = dectohex(v4);
        String v7c1 = dectohex(v7);
        String v6c1 = dectohex(v6);
        String v10c1 = dectohex(v10);

        v4c = ("00" + v4c1).substring(v4c1.length());
        String v7c = ("00" + v7c1).substring(v7c1.length());
        String v6c = ("00" + v6c1).substring(v6c1.length());
        v10c = ("00" + v10c1).substring(v10c1.length());
        protocolON3G = String.format("02 %s 00 00 00 83 03 %s 00 00 00 00 00 00 00 %s %s 02 AB 03", v7c, v4c, v6c, v10c);
        System.out.println(protocolON3G);
        devicestatus = v1;

        this.Thread13G = new Thread(new Thread13G());
        this.Thread13G.start();
    }

    public void btn1off3g(View view) {
//        on2_3g.setVisibility(View.VISIBLE);
        off2_3g.setEnabled(false);
        int point = v;
        System.out.println("3g2off");
        click = "3GtwoOFF";
        getcontroller();
        // Device : [
        String v1 = devicenameAR.get(point);
        String v2 = areaAR.get(point);
        String v3 = physicalidAR.get(point);
        String v4 = powerlineidAR.get(point);
        String v5 = devicecodeAR.get(point);
        String v6 = commandidAR.get(point);
        String v7 = masteridAR.get(point);
        // ]

        //Controller : [
        int point2 = 2;
        String v8 = pidfkARDB.get(point2);
        String v9 = contrlidARDB.get(point2);
        String v10 = internalidARDB.get(point2);
        String v11 = contrltypeARDB.get(point2);
        String v12 = contrlstatusARDB.get(point2);
        // ] 02 03 00 00 00 83 03 48 00 00 00 00 00 00 00 34 06 01 AB 03
        String v4c1 = dectohex(v4);
        String v7c1 = dectohex(v7);
        String v6c1 = dectohex(v6);
        String v10c1 = dectohex(v10);

        v4c = ("00" + v4c1).substring(v4c1.length());
        String v7c = ("00" + v7c1).substring(v7c1.length());
        String v6c = ("00" + v6c1).substring(v6c1.length());
        v10c = ("00" + v10c1).substring(v10c1.length());
        protocolOFF3G = String.format("02 %s 00 00 00 83 03 %s 00 00 00 00 00 00 00 %s %s 01 AB 03", v7c, v4c, v6c, v10c);
        System.out.println(protocolOFF3G);
        devicestatus = v1;

        this.Thread13G = new Thread(new Thread13G());
        this.Thread13G.start();
    }

    public void btn1on3g(View view) {
//        off2_3g.setVisibility(View.VISIBLE);
        on2_3g.setEnabled(false);
        int point = v;
        System.out.println("3g2oN");
        click = "3GtwoON";
        getcontroller();
        // Device : [
        String v1 = devicenameAR.get(point);
        String v2 = areaAR.get(point);
        String v3 = physicalidAR.get(point);
        String v4 = powerlineidAR.get(point);
        String v5 = devicecodeAR.get(point);
        String v6 = commandidAR.get(point);
        String v7 = masteridAR.get(point);
        // ]

        //Controller : [
        int point2 = 2;
        String v8 = pidfkARDB.get(point2);
        String v9 = contrlidARDB.get(point2);
        String v10 = internalidARDB.get(point2);
        String v11 = contrltypeARDB.get(point2);
        String v12 = contrlstatusARDB.get(point2);
        // ] 02 03 00 00 00 83 03 48 00 00 00 00 00 00 00 34 06 01 AB 03
        String v4c1 = dectohex(v4);
        String v7c1 = dectohex(v7);
        String v6c1 = dectohex(v6);
        String v10c1 = dectohex(v10);

        v4c = ("00" + v4c1).substring(v4c1.length());
        String v7c = ("00" + v7c1).substring(v7c1.length());
        String v6c = ("00" + v6c1).substring(v6c1.length());
        v10c = ("00" + v10c1).substring(v10c1.length());
        protocolON3G = String.format("02 %s 00 00 00 83 03 %s 00 00 00 00 00 00 00 %s %s 02 AB 03", v7c, v4c, v6c, v10c);
        System.out.println(protocolON3G);
        devicestatus = v1;

        this.Thread13G = new Thread(new Thread13G());
        this.Thread13G.start();
    }

    public void btn2off3g(View view) {
//        on3_3g.setVisibility(View.VISIBLE);
        off3_3g.setEnabled(false);
        int point = v;
        System.out.println("3g3off");
        click = "3GthreeOFF";
        getcontroller();
        // Device : [
        String v1 = devicenameAR.get(point);
        String v2 = areaAR.get(point);
        String v3 = physicalidAR.get(point);
        String v4 = powerlineidAR.get(point);
        String v5 = devicecodeAR.get(point);
        String v6 = commandidAR.get(point);
        String v7 = masteridAR.get(point);
        // ]

        //Controller : [
        int point2 = 3;
        String v8 = pidfkARDB.get(point2);
        String v9 = contrlidARDB.get(point2);
        String v10 = internalidARDB.get(point2);
        String v11 = contrltypeARDB.get(point2);
        String v12 = contrlstatusARDB.get(point2);
        // ] 02 03 00 00 00 83 03 48 00 00 00 00 00 00 00 34 06 01 AB 03
        String v4c1 = dectohex(v4);
        String v7c1 = dectohex(v7);
        String v6c1 = dectohex(v6);
        String v10c1 = dectohex(v10);

        v4c = ("00" + v4c1).substring(v4c1.length());
        String v7c = ("00" + v7c1).substring(v7c1.length());
        String v6c = ("00" + v6c1).substring(v6c1.length());
        v10c = ("00" + v10c1).substring(v10c1.length());
        protocolOFF3G = String.format("02 %s 00 00 00 83 03 %s 00 00 00 00 00 00 00 %s %s 01 AB 03", v7c, v4c, v6c, v10c);
        System.out.println(protocolOFF3G);
        devicestatus = v1;

        this.Thread13G = new Thread(new Thread13G());
        this.Thread13G.start();
    }

    public void btn2on3g(View view) {
//        off3_3g.setVisibility(View.VISIBLE);
        on3_3g.setEnabled(false);
        int point = v;
        System.out.println("3g3oN");
        click = "3GthreeON";
        getcontroller();
        // Device : [
        String v1 = devicenameAR.get(point);
        String v2 = areaAR.get(point);
        String v3 = physicalidAR.get(point);
        String v4 = powerlineidAR.get(point);
        String v5 = devicecodeAR.get(point);
        String v6 = commandidAR.get(point);
        String v7 = masteridAR.get(point);
        // ]

        //Controller : [
        int point2 = 3;
        String v8 = pidfkARDB.get(point2);
        String v9 = contrlidARDB.get(point2);
        String v10 = internalidARDB.get(point2);
        String v11 = contrltypeARDB.get(point2);
        String v12 = contrlstatusARDB.get(point2);
        // ] 02 03 00 00 00 83 03 48 00 00 00 00 00 00 00 34 06 01 AB 03
        String v4c1 = dectohex(v4);
        String v7c1 = dectohex(v7);
        String v6c1 = dectohex(v6);
        String v10c1 = dectohex(v10);

        v4c = ("00" + v4c1).substring(v4c1.length());
        String v7c = ("00" + v7c1).substring(v7c1.length());
        String v6c = ("00" + v6c1).substring(v6c1.length());
        v10c = ("00" + v10c1).substring(v10c1.length());
        protocolON3G = String.format("02 %s 00 00 00 83 03 %s 00 00 00 00 00 00 00 %s %s 02 AB 03", v7c, v4c, v6c, v10c);
        System.out.println(protocolON3G);
        devicestatus = v1;

        this.Thread13G = new Thread(new Thread13G());
        this.Thread13G.start();
    }

    public void btnon2g(View view) {
        btnon1_2g.setEnabled(false);
//        btnoff1_2g.setVisibility(View.VISIBLE);
        int point = v;
        System.out.println("2g1oN");
        click = "2GoneON";
        getcontroller();
        // Device : [
        String v1 = devicenameAR.get(point);
        String v2 = areaAR.get(point);
        String v3 = physicalidAR.get(point);
        String v4 = powerlineidAR.get(point);
        String v5 = devicecodeAR.get(point);
        String v6 = commandidAR.get(point);
        String v7 = masteridAR.get(point);
        // ]

        //Controller : [
        int point2 = 1;
        String v8 = pidfkARDB.get(point2);
        String v9 = contrlidARDB.get(point2);
        String v10 = internalidARDB.get(point2);
        String v11 = contrltypeARDB.get(point2);
        String v12 = contrlstatusARDB.get(point2);
        // ] 02 03 00 00 00 83 03 48 00 00 00 00 00 00 00 34 06 01 AB 03
        String v4c1 = dectohex(v4);
        String v7c1 = dectohex(v7);
        String v6c1 = dectohex(v6);
        String v10c1 = dectohex(v10);

        v4c = ("00" + v4c1).substring(v4c1.length());
        String v7c = ("00" + v7c1).substring(v7c1.length());
        String v6c = ("00" + v6c1).substring(v6c1.length());
        v10c = ("00" + v10c1).substring(v10c1.length());
        protocolON2G = String.format("02 %s 00 00 00 83 03 %s 00 00 00 00 00 00 00 %s %s 02 AB 03", v7c, v4c, v6c, v10c);
        System.out.println(protocolON2G);
        devicestatus = v1;

        this.Thread1OTHER = new Thread(new Thread1OTHER());
        this.Thread1OTHER.start();
    }

    public void btnoff2g(View view) {
//        btnon1_2g.setVisibility(View.VISIBLE);
        btnoff1_2g.setEnabled(false);
        int point = v;
        System.out.println("2g1off");
        click = "2GoneOFF";
        getcontroller();
        // Device : [
        String v1 = devicenameAR.get(point);
        String v2 = areaAR.get(point);
        String v3 = physicalidAR.get(point);
        String v4 = powerlineidAR.get(point);
        String v5 = devicecodeAR.get(point);
        String v6 = commandidAR.get(point);
        String v7 = masteridAR.get(point);
        // ]

        //Controller : [
        int point2 = 1;
        String v8 = pidfkARDB.get(point2);
        String v9 = contrlidARDB.get(point2);
        String v10 = internalidARDB.get(point2);
        String v11 = contrltypeARDB.get(point2);
        String v12 = contrlstatusARDB.get(point2);
        // ] 02 03 00 00 00 83 03 48 00 00 00 00 00 00 00 34 06 01 AB 03
        String v4c1 = dectohex(v4);
        String v7c1 = dectohex(v7);
        String v6c1 = dectohex(v6);
        String v10c1 = dectohex(v10);

        v4c = ("00" + v4c1).substring(v4c1.length());
        String v7c = ("00" + v7c1).substring(v7c1.length());
        String v6c = ("00" + v6c1).substring(v6c1.length());
        v10c = ("00" + v10c1).substring(v10c1.length());
        protocolOFF2G = String.format("02 %s 00 00 00 83 03 %s 00 00 00 00 00 00 00 %s %s 01 AB 03", v7c, v4c, v6c, v10c);
        System.out.println(protocolOFF2G);
        devicestatus = v1;

        this.Thread1OTHER = new Thread(new Thread1OTHER());
        this.Thread1OTHER.start();
    }

    public void btn1on2g(View view) {
        btnon2_2g.setEnabled(false);
//        btnoff2_2g.setVisibility(View.VISIBLE);
        int point = v;
        System.out.println("2g2oN");
        click = "2GtwoON";
        getcontroller();
        // Device : [
        String v1 = devicenameAR.get(point);
        String v2 = areaAR.get(point);
        String v3 = physicalidAR.get(point);
        String v4 = powerlineidAR.get(point);
        String v5 = devicecodeAR.get(point);
        String v6 = commandidAR.get(point);
        String v7 = masteridAR.get(point);
        // ]

        //Controller : [
        int point2 = 2;
        String v8 = pidfkARDB.get(point2);
        String v9 = contrlidARDB.get(point2);
        String v10 = internalidARDB.get(point2);
        String v11 = contrltypeARDB.get(point2);
        String v12 = contrlstatusARDB.get(point2);
        String v4c1 = dectohex(v4);
        String v7c1 = dectohex(v7);
        String v6c1 = dectohex(v6);
        String v10c1 = dectohex(v10);

        v4c = ("00" + v4c1).substring(v4c1.length());
        String v7c = ("00" + v7c1).substring(v7c1.length());
        String v6c = ("00" + v6c1).substring(v6c1.length());
        v10c = ("00" + v10c1).substring(v10c1.length());
        // ] 02 03 00 00 00 83 03 48 00 00 00 00 00 00 00 34 06 01 AB 03
        protocolON2G = String.format("02 %s 00 00 00 83 03 %s 00 00 00 00 00 00 00 %s %s 02 AB 03", v7c, v4c, v6c, v10c);
        System.out.println(protocolON2G);
        devicestatus = v1;

        this.Thread1OTHER = new Thread(new Thread1OTHER());
        this.Thread1OTHER.start();
    }

    public void btn1off2g(View view) {
//        btnon2_2g.setVisibility(View.VISIBLE);
        btnoff2_2g.setEnabled(false);
        int point = v;
        System.out.println("2g2off");
        click = "2GtwoOFF";
        getcontroller();
        // Device : [
        String v1 = devicenameAR.get(point);
        String v2 = areaAR.get(point);
        String v3 = physicalidAR.get(point);
        String v4 = powerlineidAR.get(point);
        String v5 = devicecodeAR.get(point);
        String v6 = commandidAR.get(point);
        String v7 = masteridAR.get(point);
        // ]

        //Controller : [
        int point2 = 2;
        String v8 = pidfkARDB.get(point2);
        String v9 = contrlidARDB.get(point2);
        String v10 = internalidARDB.get(point2);
        String v11 = contrltypeARDB.get(point2);
        String v12 = contrlstatusARDB.get(point2);

        String v4c1 = dectohex(v4);
        String v7c1 = dectohex(v7);
        String v6c1 = dectohex(v6);
        String v10c1 = dectohex(v10);

        v4c = ("00" + v4c1).substring(v4c1.length());
        String v7c = ("00" + v7c1).substring(v7c1.length());
        String v6c = ("00" + v6c1).substring(v6c1.length());
        v10c = ("00" + v10c1).substring(v10c1.length());
        // ] 02 03 00 00 00 83 03 48 00 00 00 00 00 00 00 34 06 01 AB 03
        protocolOFF2G = String.format("02 %s 00 00 00 83 03 %s 00 00 00 00 00 00 00 %s %s 01 AB 03", v7c, v4c, v6c, v10c);
        System.out.println(protocolOFF2G);
        devicestatus = v1;

        this.Thread1OTHER = new Thread(new Thread1OTHER());
        this.Thread1OTHER.start();
    }

    public void btn1off1g(View view) {
        off_1g.setEnabled(false);
//        on_1g.setVisibility(View.VISIBLE);
        int point = v;
        System.out.println("1g1off");
        click = "1GoneOFF";
        getcontroller();
        // Device : [
        String v1 = devicenameAR.get(point);
        String v2 = areaAR.get(point);
        String v3 = physicalidAR.get(point);
        String v4 = powerlineidAR.get(point);
        String v5 = devicecodeAR.get(point);
        String v6 = commandidAR.get(point);
        String v7 = masteridAR.get(point);
        // ]

        //Controller : [
        int point2 = 1;
        String switch_num = "one";
        String v8 = pidfkARDB.get(point2);
        String v9 = contrlidARDB.get(point2);
        String v10 = internalidARDB.get(point2);
        String v11 = contrltypeARDB.get(point2);
        String v12 = contrlstatusARDB.get(point2);

        cntrlstatus = "on";
        // ] 02 03 00 00 00 83 03 48 00 00 00 00 00 00 00 34 06 01 AB 03
        String v4c1 = dectohex(v4);
        String v7c1 = dectohex(v7);
        String v6c1 = dectohex(v6);
        String v10c1 = dectohex(v10);

        v4c = ("00" + v4c1).substring(v4c1.length());
        String v7c = ("00" + v7c1).substring(v7c1.length());
        String v6c = ("00" + v6c1).substring(v6c1.length());
        v10c = ("00" + v10c1).substring(v10c1.length());
        protocolOFF1G = String.format("02 %s 00 00 00 83 03 %s 00 00 00 00 00 00 00 %s %s 01 AB 03", v7c, v4c, v6c, v10c);
        System.out.println(protocolOFF1G);

//        boolean reply = db.insertcontrollerstatus(v4,v1,switch_num);
//        if (reply ==true){
//            System.out.println("insertcontrollerstatus inserted" + v4 + v1 + switch_num);
//        }

        devicestatus = v1;
        switchstatusid = "oneON";

        this.Thread1OTHER = new Thread(new Thread1OTHER());
        this.Thread1OTHER.start();


    }

    public void btn1on1g(View view) {
//        off_1g.setVisibility(View.VISIBLE);
        on_1g.setEnabled(false);
        int point = v;
        System.out.println("1g1on");
        click = "1GoneON";
        getcontroller();
        // Device : [
        String v1 = devicenameAR.get(point);
        String v2 = areaAR.get(point);
        String v3 = physicalidAR.get(point);
        String v4 = powerlineidAR.get(point);
        String v5 = devicecodeAR.get(point);
        String v6 = commandidAR.get(point);
        String v7 = masteridAR.get(point);
        // ]

        //Controller : [
        int point2 = 1;
        String v8 = pidfkARDB.get(point2);
        String v9 = contrlidARDB.get(point2);
        String v10 = internalidARDB.get(point2);
        String v11 = contrltypeARDB.get(point2);
        String v12 = contrlstatusARDB.get(point2);
        cntrlstatus = "off";
        // ] 02 03 00 00 00 83 03 48 00 00 00 00 00 00 00 34 06 01 AB 03
        String v4c1 = dectohex(v4);
        String v7c1 = dectohex(v7);
        String v6c1 = dectohex(v6);
        String v10c1 = dectohex(v10);

        v4c = ("00" + v4c1).substring(v4c1.length());
        String v7c = ("00" + v7c1).substring(v7c1.length());
        String v6c = ("00" + v6c1).substring(v6c1.length());
        v10c = ("00" + v10c1).substring(v10c1.length());
        protocolON1G = String.format("02 0%s 00 00 00 83 03 %s 00 00 00 00 00 00 00 %s 0%s 02 AB 03", v7c, v4c, v6c, v10c);
        System.out.println(protocolON1G);

//        sqLiteDatabase = db.getReadableDatabase();
//        db.deleteCntrlstatus(v4,sqLiteDatabase);

        devicestatus = v1;
        switchstatusid = "oneOFF";

        this.Thread1OTHER = new Thread(new Thread1OTHER());
        this.Thread1OTHER.start();

    }

    public void btn1offplug(View view) {

        off_1plug.setEnabled(false);
//        on_1plug.setVisibility(View.VISIBLE);
        int point = v;
        click = "1PLUGoneOFF";
        getcontroller();
        System.out.println("plug1off " + SERVER_IP);
        // Device : [
        String v1 = devicenameAR.get(point);
        String v2 = areaAR.get(point);
        String v3 = physicalidAR.get(point);
        String v4 = powerlineidAR.get(point);
        String v5 = devicecodeAR.get(point);
        String v6 = commandidAR.get(point);
        String v7 = masteridAR.get(point);
        // ]

        //Controller : [
        int point2 = 0;
        String v8 = pidfkARDB.get(point2);
        String v9 = contrlidARDB.get(point2);
        String v10 = internalidARDB.get(point2);
        String v11 = contrltypeARDB.get(point2);
        String v12 = contrlstatusARDB.get(point2);

        String v4c1 = dectohex(v4);
        String v7c1 = dectohex(v7);
        String v6c1 = dectohex(v6);
        String v10c1 = dectohex(v10);

        v4c = ("00" + v4c1).substring(v4c1.length());
        String v7c = ("00" + v7c1).substring(v7c1.length());
        String v6c = ("00" + v6c1).substring(v6c1.length());
        v10c = ("00" + v10c1).substring(v10c1.length());
        protocolOFFPLUG = String.format("02 %s 00 00 00 83 03 %s 00 00 00 00 00 00 00 %s %s 01 AB 03", v7c, v4c, v6c, v10c);
        System.out.println(protocolOFFPLUG);
        devicestatus = v1;

        this.Thread1OTHER = new Thread(new Thread1OTHER());
        this.Thread1OTHER.start();


    }

    public void btn1onplug(View view) {
//        off_1plug.setVisibility(View.VISIBLE);
        on_1plug.setEnabled(false);
        int point = v;
        click = "1PLUGoneON";
        getcontroller();
        System.out.println("1plug1on " + SERVER_IP);
        // Device : [
        String v1 = devicenameAR.get(point);
        String v2 = areaAR.get(point);
        String v3 = physicalidAR.get(point);
        String v4 = powerlineidAR.get(point);
        String v5 = devicecodeAR.get(point);
        String v6 = commandidAR.get(point);
        String v7 = masteridAR.get(point);
        // ]

        //Controller : [
        int point2 = 0;
        String v8 = pidfkARDB.get(point2);
        String v9 = contrlidARDB.get(point2);
        String v10 = internalidARDB.get(point2);
        String v11 = contrltypeARDB.get(point2);
        String v12 = contrlstatusARDB.get(point2);
        // ] 02 03 00 00 00 83 03 48 00 00 00 00 00 00 00 34 06 01 AB 03
        String v4c1 = dectohex(v4);
        String v7c1 = dectohex(v7);
        String v6c1 = dectohex(v6);
        String v10c1 = dectohex(v10);

        v4c = ("00" + v4c1).substring(v4c1.length());
        String v7c = ("00" + v7c1).substring(v7c1.length());
        String v6c = ("00" + v6c1).substring(v6c1.length());
        v10c = ("00" + v10c1).substring(v10c1.length());
        protocolONPLUG = String.format("02 %s 00 00 00 83 03 %s 00 00 00 00 00 00 00 %s %s 02 AB 03", v7c, v4c, v6c, v10c);
        System.out.println(protocolONPLUG);
        devicestatus = v1;

        this.Thread1OTHER = new Thread(new Thread1OTHER());
        this.Thread1OTHER.start();

    }

    public void btnonfan(View view) {
        btnofffan.setVisibility(View.VISIBLE);
        btnonfan.setVisibility(View.GONE);
        btnplusfan.setEnabled(false);
        btnminusfan.setEnabled(false);
        progressbarfan.setProgress(0);
        fan = 0;
        tvfanspeed.setText("");

        int point = v;
        System.out.println("btnonfan");
        click = "btnonfan";
        getcontroller();
        // Device : [
        String v1 = devicenameAR.get(point);
        String v2 = areaAR.get(point);
        String v3 = physicalidAR.get(point);
        String v4 = powerlineidAR.get(point);
        String v5 = devicecodeAR.get(point);
        String v6 = commandidAR.get(point);
        String v7 = masteridAR.get(point);
        // ]

        //Controller : [
        int point2 = 6;
        String v8 = pidfkARDB.get(point2);
        String v9 = contrlidARDB.get(point2);
        String v10 = internalidARDB.get(point2);
        String v11 = contrltypeARDB.get(point2);
        String v12 = contrlstatusARDB.get(point2);
        // ] 02 03 00 00 00 83 03 48 00 00 00 00 00 00 00 34 06 01 AB 03
        String v4c1 = dectohex(v4);
        String v7c1 = dectohex(v7);
        String v6c1 = dectohex(v6);
        String v10c1 = dectohex(v10);

        v4c = ("00" + v4c1).substring(v4c1.length());
        String v7c = ("00" + v7c1).substring(v7c1.length());
        String v6c = ("00" + v6c1).substring(v6c1.length());
        v10c = ("00" + v10c1).substring(v10c1.length());
        protocolONFAN = String.format("02 %s 00 00 00 83 03 %s 00 00 00 00 00 00 00 %s %s 02 AB 03", v7c, v4c, v6c, v10c);
        System.out.println(protocolONFAN);
        devicestatus = v1;

        this.Thread1OTHER = new Thread(new Thread1OTHER());
        this.Thread1OTHER.start();

    }

    public void btnplusfan(View view) {

        int point = v;
        System.out.println("btnplusfan" + fan);
        click = "btnplusfan";
        getcontroller();
        if (fan == 5) {
            Toast.makeText(Home.this, "Fan at Maximum speed", Toast.LENGTH_SHORT).show();
        } else {
            ++fan;
            progressbarfan.setProgress(fan);
            tvfanspeed.setText("Fan speed : " + fan);
            //Device : [
            String v1 = devicenameAR.get(point);
            String v2 = areaAR.get(point);
            String v3 = physicalidAR.get(point);
            String v4 = powerlineidAR.get(point);
            String v5 = devicecodeAR.get(point);
            String v6 = commandidAR.get(point);
            String v7 = masteridAR.get(point);
            // ]

            //Controller : [
            int point2 = 0;
            String v8 = pidfkARDB.get(fan);
            String v9 = contrlidARDB.get(fan);
            String v10 = internalidARDB.get(fan);
            String v11 = contrltypeARDB.get(fan);
            String v12 = contrlstatusARDB.get(fan);
            // ] 02 03 00 00 00 83 03 48 00 00 00 00 00 00 00 34 06 01 AB 03
            String v4c1 = dectohex(v4);
            String v7c1 = dectohex(v7);
            String v6c1 = dectohex(v6);
            String v10c1 = dectohex(v10);

            v4c = ("00" + v4c1).substring(v4c1.length());
            String v7c = ("00" + v7c1).substring(v7c1.length());
            String v6c = ("00" + v6c1).substring(v6c1.length());
            v10c = ("00" + v10c1).substring(v10c1.length());
            fanintid = String.format("%02d", fan);
            protocolPLUSFAN = String.format("02 %s 00 00 00 83 03 %s 00 00 00 00 00 00 00 %s %s 02 AB 03", v7c, v4c, v6c, fanintid);
            System.out.println(protocolPLUSFAN);
            devicestatus = v1;

            this.Thread1OTHER = new Thread(new Thread1OTHER());
            this.Thread1OTHER.start();
        }

    }

    public void btnminusfan(View view) {

        int point = v;
        System.out.println("btnminusfan" + fan);
        click = "btnminusfan";
        getcontroller();

        if (fan == 1) {
            Toast.makeText(Home.this, "Fan at Minimum speed", Toast.LENGTH_SHORT).show();
        } else {
            --fan;
            progressbarfan.setProgress(fan);
            tvfanspeed.setText("Fan speed : " + fan);
            // Device : [
            String v1 = devicenameAR.get(point);
            String v2 = areaAR.get(point);
            String v3 = physicalidAR.get(point);
            String v4 = powerlineidAR.get(point);
            String v5 = devicecodeAR.get(point);
            String v6 = commandidAR.get(point);
            String v7 = masteridAR.get(point);
            // ]

            //Controller : [
            int point2 = 0;
            String v8 = pidfkARDB.get(fan);
            String v9 = contrlidARDB.get(fan);
            String v10 = internalidARDB.get(fan);
            String v11 = contrltypeARDB.get(fan);
            String v12 = contrlstatusARDB.get(fan);
            // ] 02 03 00 00 00 83 03 48 00 00 00 00 00 00 00 34 06 01 AB 03
            String v4c1 = dectohex(v4);
            String v7c1 = dectohex(v7);
            String v6c1 = dectohex(v6);
            String v10c1 = dectohex(v10);

            v4c = ("00" + v4c1).substring(v4c1.length());
            String v7c = ("00" + v7c1).substring(v7c1.length());
            String v6c = ("00" + v6c1).substring(v6c1.length());
            v10c = ("00" + v10c1).substring(v10c1.length());
            fanintid = String.format("%02d", fan);
            protocolMINUSFAN = String.format("02 %s 00 00 00 83 03 %s 00 00 00 00 00 00 00 %s %s 02 AB 03", v7c, v4c, v6c, fanintid);
            System.out.println(protocolMINUSFAN);
            devicestatus = v1;

            this.Thread1OTHER = new Thread(new Thread1OTHER());
            this.Thread1OTHER.start();

        }
    }

    public void btnofffan(View view) {
        btnofffan.setVisibility(View.GONE);
        btnonfan.setVisibility(View.VISIBLE);
        btnplusfan.setEnabled(true);
        btnminusfan.setEnabled(true);
        tvfanspeed.setText("");

        int point = v;
        System.out.println("btnofffan");
        click = "btnofffan";
        getcontroller();
        // Device : [
        String v1 = devicenameAR.get(point);
        String v2 = areaAR.get(point);
        String v3 = physicalidAR.get(point);
        String v4 = powerlineidAR.get(point);
        String v5 = devicecodeAR.get(point);
        String v6 = commandidAR.get(point);
        String v7 = masteridAR.get(point);
        // ]

        //Controller : [
        int point2 = 6;
        String v8 = pidfkARDB.get(point2);
        String v9 = contrlidARDB.get(point2);
        String v10 = internalidARDB.get(point2);
        String v11 = contrltypeARDB.get(point2);
        String v12 = contrlstatusARDB.get(point2);
        // ] 02 03 00 00 00 83 03 48 00 00 00 00 00 00 00 34 06 01 AB 03
        String v4c1 = dectohex(v4);
        String v7c1 = dectohex(v7);
        String v6c1 = dectohex(v6);
        String v10c1 = dectohex(v10);

        v4c = ("00" + v4c1).substring(v4c1.length());
        String v7c = ("00" + v7c1).substring(v7c1.length());
        String v6c = ("00" + v6c1).substring(v6c1.length());
        v10c = ("00" + v10c1).substring(v10c1.length());
        protocolOFFFAN = String.format("02 %s 00 00 00 83 03 %s 00 00 00 00 00 00 00 %s %s 01 AB 03", v7c, v4c, v6c, v10c);
        System.out.println(protocolOFFFAN);
        devicestatus = v1;

        this.Thread1OTHER = new Thread(new Thread1OTHER());
        this.Thread1OTHER.start();

    }

    private void addDrawerItems() {
        String[] osArray = {"Home", "Dashboard"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent go = new Intent(Home.this, Home.class); //if home already exist proceed to controller page
                    startActivity(go);
                } else if (position == 1) {
                    Intent go = new Intent(Home.this, Dashboard.class); //if home already exist proceed to controller page
                    startActivity(go);
                }
            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Menu");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Home.this);
            alertDialogBuilder.setMessage("All data will be lost for this Home and will be reset/update to new data on the server,are you sure you want to continue?");
            alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    boolean deleteall = db.deleteAll();
                    if (deleteall == true){
                        Intent i = new Intent(Home.this,Welcome.class);
                        startActivity(i);
                    }else{
                        Toast.makeText(Home.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            return true;
        }

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class SendCmnd extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String homeid = params[0];
            String powerline_id = params[1];
            String internal_id = params[2];
            String event = params[3];
            String data = "";
            int tmp;

            try {
                System.out.println("running send cmnd" + homeidVAR + " : " + v4c + " : " + v10c);
                URL url = new URL("http://centraserv.idsworld.solutions:50/v1/Ape_srv/DeviceEvent/");
                String urlParams = "HomeID="+homeid+"&powerline_id="+powerline_id+"&internal_id="+internal_id+"&action_event="+event;

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                os.write(urlParams.getBytes());
                os.flush();
                os.close();
                InputStream is = httpURLConnection.getInputStream();
                while ((tmp = is.read()) != -1) {
                    data += (char) tmp;
                }
                is.close();
                httpURLConnection.disconnect();

                return data;

            } catch (MalformedURLException e) {
                UIhandler.post(new UpdateButtonState(click.toUpperCase()));
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            } catch (IOException e) {
                UIhandler.post(new UpdateButtonState(click.toUpperCase()));
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            String err = null;
            if (s.equals("")) {
                s = "Data saved successfully.";
            }
            try {
                JSONObject user_data = new JSONObject(s);
                String statusvalidateinfo = user_data.getString("STATUS");
                String validateip = user_data.getString("DESC");
                System.out.println("Staus of validate info : " + s);
                UIhandler.post(new UpdateButtonState(click.toUpperCase())); //to update state of button (ex:disbaled or enabled) by sending click value
                UIhandler.post(new updateUIThread(v4c.toUpperCase())); //to update value of button(ex: on or off) by sending powerlineid
            } catch (JSONException e) {
                runOnUiThread(new Runnable(){
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Connection lost! Please connect to the internet or try again later!",Toast.LENGTH_LONG).show();
                    }
                });
                UIhandler.post(new UpdateButtonState(click.toUpperCase()));
                e.printStackTrace();
                err = "Exception: " + e.getMessage();
            }

        }

    }

    public void getsamedevcontrols(){

    }


}


