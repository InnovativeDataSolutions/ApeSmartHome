package com.ids.smarthomev2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
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
import java.util.Map;

public class Home extends AppCompatActivity implements View.OnTouchListener {

    private RelativeLayout someLayout, rl1, rl2, rl3, rl4, rl5, rlplug, rlfan,rlbell;
    Button on_5g, on2_5g, on3_5g, on4_5g, on5_5g;
    Button on_4g, on2_4g, on3_4g, on4_4g;
    Button on_3g, on2_3g, on3_3g;
    Button btnon1_2g,btnon2_2g;
    Button on_1g, on_1plug, off_1plug, btnonfan ;
    ImageView bell,btnofffan, btnplusfan, btnminusfan,btnoff1_2g,btnoff2_2g,off_1g, off_3g, off2_3g, off3_3g, off_4g, off2_4g, off3_4g, off4_4g, off_5g, off2_5g, off3_5g, off4_5g, off5_5g;
    Switch backlight;
    ImageButton lockopen,lockclosed,durationinfo,intervalinfo;
    Spinner sp1, sp2;
    HelperT helperT;
    EditText interval,duration;
    ProgressBar progressbarfan;
    TextView tvinfo, tvfanspeed;
    private static final String TAG = "motion";
    GestureDetector gestureDetector;
    private String SERVER_IP; //server IP address192.168.1.9 // port = 8081
    public static final int SERVER_PORT = 8081;
    int v = 0, j = 0, point = 0;
    int count = 0;
    boolean bgthread=false,backlightbool =false;
    ArrayAdapter<String> dataAdapter2;
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
    String pidfkDB, contrlidDB,manualup,intervalET,durationET,areaslctd, internalidDB, contrlnameDB, cntrlstatusDB, v4c, v10c, fanintid, buttonstate,clientrply, devicestatus = null, switchstatusid,on="1",off="2";
    boolean dbcheck1, dbcheck2;
    int i, fan = 1;
    Handler UIhandler;
    Socket socket = null;
    Thread Thread1 = null;
    Thread Thread14G = null;
    Thread Thread13G = null;
    Thread Thread1OTHER = null;
    Thread Thread2 = null;
    Thread Thread2bg = null;
    Thread Thread1bg = null;
    String btnclick = null;
    String protocolON, protocolOFF, protocolON2G, protocolON4G, protocolOFF4G, protocolON1G, protocolOFF1G, protocolONPLUG, protocolOFFPLUG, protocolOFF2G, protocolON3G, protocolOFF3G, protocolONM, protocolOFFM, protocolOFFFAN, protocolONFAN, protocolMINUSFAN, protocolPLUSFAN,protocolBL,protcollockopen,protcollockclosed,protcolduration,protcolinterval;
    List<String> devicenameAR = new ArrayList<String>();
    List<String> areaDUPLICATE = new ArrayList<String>();
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

    //List<String> cntrlstatusinfo = new ArrayList<String>();
    HashMap<String, String> cntrlstatusinfo = new HashMap<String, String>();
    HashMap<String, String> area_devname = new HashMap<String, String>();
    HashMap<String, String> blstatusinfo = new HashMap<String, String>();

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
        rlbell = (RelativeLayout) findViewById(R.id.rlbell);
        sp1 = (Spinner) findViewById(R.id.sp1);
        sp2 = (Spinner) findViewById(R.id.sp2);

        on_5g = (Button) findViewById(R.id.btnon5g);
        off_5g = (ImageView) findViewById(R.id.btnoff5g);
        on2_5g = (Button) findViewById(R.id.btn1on5g);
        off2_5g = (ImageView) findViewById(R.id.btn1off5g);
        on3_5g = (Button) findViewById(R.id.btn2on5g);
        off3_5g = (ImageView) findViewById(R.id.btn2off5g);
        on4_5g = (Button) findViewById(R.id.btn3on5g);
        off4_5g = (ImageView) findViewById(R.id.btn3off5g);
        on5_5g = (Button) findViewById(R.id.btn4on5g);
        off5_5g = (ImageView) findViewById(R.id.btn4off5g);

        on_4g = (Button) findViewById(R.id.btnon4g);
        off_4g = (ImageView) findViewById(R.id.btnoff4g);
        on2_4g = (Button) findViewById(R.id.btn1on4g);
        off2_4g = (ImageView) findViewById(R.id.btn1off4g);
        on3_4g = (Button) findViewById(R.id.btn2on4g);
        off3_4g = (ImageView) findViewById(R.id.btn2off4g);
        on4_4g = (Button) findViewById(R.id.btn3on4g);
        off4_4g = (ImageView) findViewById(R.id.btn3off4g);

        on_3g = (Button) findViewById(R.id.btnon3g);
        off_3g = (ImageView) findViewById(R.id.btnoff3g);
        on2_3g = (Button) findViewById(R.id.btn1on3g);
        off2_3g = (ImageView) findViewById(R.id.btn1off3g);
        on3_3g = (Button) findViewById(R.id.btn2on3g);
        off3_3g = (ImageView) findViewById(R.id.btn2off3g);

        btnon1_2g = (Button) findViewById(R.id.btnon2g);
        btnoff1_2g = (ImageView) findViewById(R.id.btnoff2g);
        btnon2_2g = (Button) findViewById(R.id.btn1on2g);
        btnoff2_2g = (ImageView) findViewById(R.id.btn1off2g);
        btnplusfan = (ImageView) findViewById(R.id.btnplusfan);
        btnminusfan = (ImageView) findViewById(R.id.btnminusfan);
        btnonfan = (Button) findViewById(R.id.btnonfan);
        btnofffan = (ImageView) findViewById(R.id.btnofffan);
        backlight = (Switch) findViewById(R.id.backlight);

        lockclosed = (ImageButton) findViewById(R.id.lockclosed);
        lockopen = (ImageButton) findViewById(R.id.lockopen);
        durationinfo = (ImageButton) findViewById(R.id.sq_explain_duration);
        intervalinfo = (ImageButton) findViewById(R.id.sq_explain_interval);
        duration = (EditText) findViewById(R.id.duration);
        interval = (EditText) findViewById(R.id.interval);
        bell = (ImageView) findViewById(R.id.bell);
        backlight.setChecked(false);
        progressbarfan = (ProgressBar) findViewById(R.id.progressbarfan);
        progressbarfan.setScaleY(3.5f);
        progressbarfan.setMax(5);
        progressbarfan.setProgress(fan);
        btnplusfan.setEnabled(true);
        btnminusfan.setEnabled(true);

        on_1g = (Button) findViewById(R.id.btn1on1g);
        off_1g = (ImageView) findViewById(R.id.btn1off1g);

        on_1plug = (Button) findViewById(R.id.btn1onplug);
        off_1plug = (Button) findViewById(R.id.btn1offplug);

        mDrawerList = (ListView) findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        tvinfo = (TextView) findViewById(R.id.tvinfo);
        tvfanspeed = (TextView) findViewById(R.id.faninfotv);

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

        for (String obj : areaAR) {
            System.out.println("areafilter : " + obj);
            areaDUPLICATE.add(obj); //create duplicate array for area copying values from original area array
        }

        for(int s=0;s<areaDUPLICATE.size();s++)
        {
            for(int m=1;m<areaDUPLICATE.size();m++)
            {

                if(areaDUPLICATE.get(s) != null && areaDUPLICATE.get(s).equals(areaDUPLICATE.get(m)))
                {
                    areaDUPLICATE.remove(m); //remove duplicate values
                }
            }
        }

        for(int s=0;s<areaDUPLICATE.size();s++)
        {
            for(int m=1;m<areaDUPLICATE.size();m++)
            {

                if(areaDUPLICATE.get(s) != null && areaDUPLICATE.get(s).equals(areaDUPLICATE.get(m)))
                {
                    areaDUPLICATE.remove(m); //remove duplicate values
                }
            }
        }

        for (String obj2 : areaDUPLICATE) {
            System.out.println("areafilter2 : " + obj2);
        }
// Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, areaDUPLICATE);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        sp1.setAdapter(dataAdapter);

        dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, devicemodelAR);
// Specify the layout to use when the list of choices appears
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        sp2.setAdapter(dataAdapter2);

        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

//                areaslctd = areaDUPLICATE.get(position);
//                System.out.println("running spinner 1" + areaslctd);
//                db.deletecurrentinfo();//
//                filterareadevices cds = new filterareadevices();
//                cds.execute(homeidVAR);

                String value = devicenameAR.get(position);
                String value2 = areaAR.get(v);
                String value3 = devicemodelAR.get(position);
                tvinfo.setText("[Device : " + value3 + "] [Area :" + value2 + "]");
                sp2.setSelection(position);
                if (value.equals("PS")){
                    backlight.setVisibility(View.GONE);
                }else{
                    backlight.setVisibility(View.VISIBLE);
                }
                //v = position;
                //j = position;
                //get values from array based on value of v and assign them to the protocol.
                if (value.contains("TS1G")) {
                    setLayoutVisibililty(rl5);
                } else if (value.contains("TS2G")) {
                    setLayoutVisibililty(rl4);
                } else if (value.contains("TS3G")) {
                    setLayoutVisibililty(rl3);
                } else if (value.contains("TS4G")) {
                    setLayoutVisibililty(rl2);
                } else if (value.contains("Dimmer")) {
                    setLayoutVisibililty(rl3);
                } else if (value.contains("BC")) {
                    setLayoutVisibililty(rlbell);
                } else if (value.contains("TS5G")) {
                    setLayoutVisibililty(rl1);
                } else if (value.contains("PS")) {
                    setLayoutVisibililty(rlplug);
                } else if (value.contains("METER")) {
                    setLayoutVisibililty(rl5);
                } else if (value.contains("FC")) {
                    setLayoutVisibililty(rlfan);
                }
                getcntrlstate(value3);

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
                //if (devicenameAR.size()>0 && devicemodelAR.size()>0) {
                    String value = devicenameAR.get(position);
                    String value2 = areaAR.get(position);
                    String value3 = devicemodelAR.get(position);
                    tvinfo.setText("[Device : " + value3 + "] [Area :" + value2 + "]");
                    if (value.equals("PS")) {
                        backlight.setVisibility(View.GONE);
                    } else {
                        backlight.setVisibility(View.VISIBLE);
                    }
                    //sp1.setSelection(position);
                    v = position;
                    //j = position;
                    //get values from array based on value of v and assign them to the protocol.
                    if (value.contains("TS1G")) {
                        setLayoutVisibililty(rl5);
                    } else if (value.contains("TS2G")) {
                        setLayoutVisibililty(rl4);
                    } else if (value.contains("TS3G")) {
                        setLayoutVisibililty(rl3);
                    } else if (value.contains("TS4G")) {
                        setLayoutVisibililty(rl2);
                    } else if (value.contains("Dimmer")) {
                        setLayoutVisibililty(rl3);
                    } else if (value.contains("BC")) {
                        setLayoutVisibililty(rlbell);
                    } else if (value.contains("TS5G")) {
                        setLayoutVisibililty(rl1);
                    } else if (value.contains("PS")) {
                        setLayoutVisibililty(rlplug);
                    } else if (value.contains("METER")) {
                        setLayoutVisibililty(rl5);
                    } else if (value.contains("FC")) {
                        setLayoutVisibililty(rlfan);
                    }
                    getcntrlstate(value3);
                }
                //}

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        //if (devicenameAR.size()>0 && devicemodelAR.size()>0) {
            checkdevstatus cds = new checkdevstatus();
            cds.execute(homeidVAR);
       // }
        helperT = new HelperT(ctx);

        backlightcmnd();

        duration.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    durationET = v.getText().toString();
                    sendbellduration(durationET);
                    handled = true;
                }
                return handled;
            }
        });

        interval.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    intervalET = v.getText().toString();
                    sendbellinterval(intervalET);
                    handled = true;
                }
                return handled;
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
                System.out.println("Background thread running.... " + SERVER_PORT + ipaddressVAR);
                InetAddress serverAddr = InetAddress.getByName(ipaddressVAR);
                socket = new Socket(serverAddr, SERVER_PORT);
                Thread2bg commThread2 = new Thread2bg(socket);
                new Thread(commThread2).start();
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class Thread2bg implements Runnable {

        public Thread2bg(Socket socket) {
            InputStream in = null;
            try {
                in = socket.getInputStream();
            } catch (IOException ex) {
                System.out.println("Can't get socket input stream. ");
            }

            System.out.println("Waiting Socket events bg ....");
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
                        System.out.println("Socket Closed [in] thread2bg");
                        runOnUiThread(new Runnable() {
                            public void run() {
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
                Thread1bg = new Thread(new Thread1bg());
                Thread1bg.start();
                    checkdevstatus cds = new checkdevstatus();
                    cds.execute(homeidVAR);
                if (direction == Direction.up) {
                    j=0;
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
                        if (value.equals("PS")){
                            backlight.setVisibility(View.GONE);
                        }else{
                            backlight.setVisibility(View.VISIBLE);
                        }
                        //sp1.setSelection(v);
                        sp2.setSelection(v);
                        checkbacklightstate(value4,value);
                        //get values from array based on value of v and assign them to the protocol.
                        if (value.contains("TS1G")) {
                            getcntrlstate(value4);
                            setLayoutVisibililty(rl5);
                            btmToUp(rl5);
                        } else if (value.contains("TS2G")) {
                            getcntrlstate(value4);
                            setLayoutVisibililty(rl4);
                            btmToUp(rl4);
                        } else if (value.contains("TS3G")) {
                            getcntrlstate(value4);
                            setLayoutVisibililty(rl3);
                            btmToUp(rl3);
                        } else if (value.contains("TS4G")) {
                            getcntrlstate(value4);
                            setLayoutVisibililty(rl2);
                            btmToUp(rl2);
                        } else if (value.contains("2")) {
                            getcntrlstate(value4);
                            setLayoutVisibililty(rl4);
                            btmToUp(rl4);
                        } else if (value.contains("Dimmer")) {
                            getcntrlstate(value4);
                            setLayoutVisibililty(rl3);
                            btmToUp(rl3);
                        } else if (value.contains("BC")) {
                            getcntrlstate(value4);
                            setLayoutVisibililty(rlbell);
                            btmToUp(rlbell);
                        } else if (value.contains("TS5G")) {
                            getcntrlstate(value4);
                            setLayoutVisibililty(rl1);
                            btmToUp(rl1);
                        } else if (value.contains("PS")) {
                            getcntrlstate(value4);
                            setLayoutVisibililty(rlplug);
                            btmToUp(rlplug);
                        } else if (value.contains("METER")) {
                            getcntrlstate(value4);
                            setLayoutVisibililty(rl5);
                            btmToUp(rl5);
                        } else if (value.contains("FC")) {
                            getcntrlstate(value4);
                            setLayoutVisibililty(rlfan);
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
                    j=0;
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
                        if (value.equals("PS")){
                            backlight.setVisibility(View.GONE);
                        }else{
                            backlight.setVisibility(View.VISIBLE);
                        }
                        //sp1.setSelection(v);
                        sp2.setSelection(v);
                        checkbacklightstate(value4,value);
                        //get values from array based on value of v and assign them to the protocol.
                        if (value.contains("TS1G")) {
                            getcntrlstate(value4);
                            setLayoutVisibililty(rl5);
                            topToBtm(rl5);
                        } else if (value.contains("TS2G")) {
                            getcntrlstate(value4);
                            setLayoutVisibililty(rl4);
                            topToBtm(rl4);
                        } else if (value.contains("TS3G")) {
                            getcntrlstate(value4);
                            setLayoutVisibililty(rl3);
                            topToBtm(rl3);
                        } else if (value.contains("TS4G")) {
                            getcntrlstate(value4);
                            setLayoutVisibililty(rl2);
                            topToBtm(rl2);
                        } else if (value.contains("2")) {
                            getcntrlstate(value4);
                            setLayoutVisibililty(rl4);
                            topToBtm(rl4);
                        } else if (value.contains("Dimmer")) {
                            getcntrlstate(value4);
                            setLayoutVisibililty(rl3);
                            topToBtm(rl3);
                        } else if (value.contains("BC")) {
                            getcntrlstate(value4);
                            setLayoutVisibililty(rlbell);
                            topToBtm(rlbell);
                        } else if (value.contains("TS5G")) {
                            getcntrlstate(value4);
                            setLayoutVisibililty(rl1);
                            topToBtm(rl1);
                        } else if (value.contains("PS")) {
                            getcntrlstate(value4);
                            setLayoutVisibililty(rlplug);
                            topToBtm(rlplug);
                        } else if (value.contains("METER")) {
                            getcntrlstate(value4);
                            setLayoutVisibililty(rl5);
                            topToBtm(rl5);
                        } else if (value.contains("FC")) {
                            getcntrlstate(value4);
                            setLayoutVisibililty(rlfan);
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
                            //sp1.setSelection(indexofsecnddevice);
                            sp2.setSelection(indexofsecnddevice);
                            checkbacklightstate(value,value4);
                            if (value.equals("PS")){
                                backlight.setVisibility(View.GONE);
                            }else{
                                backlight.setVisibility(View.VISIBLE);
                            }
                            //get values from array based on value of v and assign them to the protocol.
                            if (value.contains("TS1G")) {
                                getcntrlstate(value4);
                                setLayoutVisibililty(rl5);
                                rightToLeft(rl5);
                            } else if (value.contains("TS2G")) {
                                getcntrlstate(value4);
                                setLayoutVisibililty(rl4);
                                rightToLeft(rl4);
                            } else if (value.contains("TS3G")) {
                                getcntrlstate(value4);
                                setLayoutVisibililty(rl3);
                                rightToLeft(rl3);
                            } else if (value.contains("TS4G")) {
                                getcntrlstate(value4);
                                setLayoutVisibililty(rl2);
                                rightToLeft(rl2);
                            } else if (value.contains("2")) {
                                getcntrlstate(value4);
                                setLayoutVisibililty(rl4);
                                rightToLeft(rl4);
                            } else if (value.contains("Dimmer")) {
                                getcntrlstate(value4);
                                setLayoutVisibililty(rl3);
                                rightToLeft(rl3);
                            } else if (value.contains("BC")) {
                                getcntrlstate(value4);
                                setLayoutVisibililty(rlbell);
                                rightToLeft(rlbell);
                            } else if (value.contains("TS5G")) {
                                getcntrlstate(value4);
                                setLayoutVisibililty(rl1);
                                rightToLeft(rl1);
                            } else if (value.contains("PS")) {
                                getcntrlstate(value4);
                                setLayoutVisibililty(rlplug);
                                rightToLeft(rlplug);
                            } else if (value.contains("METER")) {
                                getcntrlstate(value4);
                                setLayoutVisibililty(rl5);
                                rightToLeft(rl5);
                            } else if (value.contains("FC")) {
                                getcntrlstate(value4);
                                setLayoutVisibililty(rlfan);
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
                            //sp1.setSelection(indexofsecnddevice);
                            sp2.setSelection(indexofsecnddevice);
                            checkbacklightstate(value,value4);
                            if (value.equals("PS")){
                                backlight.setVisibility(View.GONE);
                            }else{
                                backlight.setVisibility(View.VISIBLE);
                            }
                            //get values from array based on value of v and assign them to the protocol.
                            if (value.contains("TS1G")) {
                                getcntrlstate(value4);
                                setLayoutVisibililty(rl5);
                                leftToRight(rl5);
                            } else if (value.contains("TS2G")) {
                                getcntrlstate(value4);
                                setLayoutVisibililty(rl4);
                                leftToRight(rl4);
                            } else if (value.contains("TS3G")) {
                                getcntrlstate(value4);
                                setLayoutVisibililty(rl3);
                                leftToRight(rl3);
                            } else if (value.contains("TS4G")) {
                                getcntrlstate(value4);
                                setLayoutVisibililty(rl2);
                                leftToRight(rl2);
                            } else if (value.contains("2")) {
                                getcntrlstate(value4);
                                setLayoutVisibililty(rl4);
                                leftToRight(rl4);
                            } else if (value.contains("Dimmer")) {
                                getcntrlstate(value4);
                                setLayoutVisibililty(rl3);
                                leftToRight(rl3);
                            } else if (value.contains("BC")) {
                                getcntrlstate(value4);
                                setLayoutVisibililty(rlbell);
                                leftToRight(rlbell);
                            } else if (value.contains("TS5G")) {
                                getcntrlstate(value4);
                                setLayoutVisibililty(rl1);
                                leftToRight(rl1);
                            } else if (value.contains("PS")) {
                                getcntrlstate(value4);
                                setLayoutVisibililty(rlplug);
                                leftToRight(rlplug);
                            } else if (value.contains("METER")) {
                                getcntrlstate(value4);
                                setLayoutVisibililty(rl5);
                                leftToRight(rl5);
                            } else if (value.contains("FC")) {
                                getcntrlstate(value4);
                                setLayoutVisibililty(rlfan);
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
        activateallbuttons();
        gestureDetector.onTouchEvent(event);
        return true;
    }

    private void setLayoutVisibililty(RelativeLayout relativeLayout) {
        rl1.setVisibility(View.GONE);
        rl2.setVisibility(View.GONE);
        rl3.setVisibility(View.GONE);
        rl4.setVisibility(View.GONE);
        rl5.setVisibility(View.GONE);
        rlplug.setVisibility(View.GONE);
        rlfan.setVisibility(View.GONE);
        rlbell.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.VISIBLE);
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
                    if (!usernameVAR.equals("infotel")) {
                        SendCmnd sc = new SendCmnd();
                        sc.execute(homeidVAR, pid2, iid2, on);
                        System.out.println("overTheNet : " + pid2 + iid2);
                    }
                } else {
                    if (!usernameVAR.equals("infotel")) {
                    SendCmnd sc = new SendCmnd();
                    sc.execute(homeidVAR,pid2,iid2,off);
                    System.out.println("overTheNet : " + pid2 + iid2);}
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
                if (!usernameVAR.equals("infotel")) {
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
                }}
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
                if (!usernameVAR.equals("infotel")) {
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
                }}
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
                }else if (click.contains("btnbacklight")) {
                    byte[] by = hexStringToByteArray(protocolBL.replaceAll(" ", ""));
                    out.write(by, 0, by.length);
                    out.flush();
                }else if (click.contains("lockopen")) {
                    byte[] by = hexStringToByteArray(protcollockopen.replaceAll(" ", ""));
                    out.write(by, 0, by.length);
                    out.flush();
                }else if (click.contains("lockclosed")) {
                    byte[] by = hexStringToByteArray(protcollockclosed.replaceAll(" ", ""));
                    out.write(by, 0, by.length);
                    out.flush();
                }else if (click.contains("duration")) {
                    byte[] by = hexStringToByteArray(protcolduration.replaceAll(" ", ""));
                    out.write(by, 0, by.length);
                    out.flush();
                }else if (click.contains("interval")) {
                    byte[] by = hexStringToByteArray(protcolinterval.replaceAll(" ", ""));
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
                if (!usernameVAR.equals("infotel")) {
                int pid = Integer.parseInt(v4c, 16); //taking powerlineid(hex) converting to integer
                String pid2 = String.valueOf(pid); // converting hex powerlineid to deciaml
                int iid = Integer.parseInt(v10c, 16); // " internal id
                String iid2 = String.valueOf(iid); // converting internal to decimal
                if (click.contains("OFF") || click.contains("off") || click.contains("lockclosed")) {
                    SendCmnd sc = new SendCmnd();
                    sc.execute(homeidVAR,pid2,iid2,on);
                    System.out.println("overTheNet : " + pid2 + iid2);
                } else if (click.contains("ON") || click.contains("on") || click.contains("lockopen")) {
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
                }else if (click.contains("duration")) {
                    SendCmnd sc = new SendCmnd();
                    sc.execute(homeidVAR,pid2,iid2,durationET);
                    System.out.println("overTheNet : " + pid2 + iid2 + durationET);
                }else if (click.contains("duration") || click.contains("interval")) {
                    SendCmnd sc = new SendCmnd();
                    sc.execute(homeidVAR,pid2,iid2,intervalET);
                    System.out.println("overTheNet : " + pid2 + iid2 + intervalET);
                }}
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
                socket.setSoTimeout(5000);
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
                        System.out.println("Socket Closed [in] thread2");
                        runOnUiThread(new Runnable() {
                            public void run() {
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
        String devname = devicemodelAR.get(v); //to get what device name was activated
        String devmodel = devicenameAR.get(v);

        public updateUIThread(String str) {
            this.message = str;
        }

        @Override
        public void run() {
            System.out.println("updateUIThread reply :" + message + " " + val + ":" + pwline); //used for checking

            if (message.matches("(.*)" + pwline.toUpperCase() + "(.*)") || message.matches("(.*)" + pwlinej.toUpperCase() + "(.*)")) {

                if (message.length() == 50) { //if message > 48 check if manual update else it must be reply frm server
                    manualup = message.substring(46, 48);
                }
                if (message.length()>50){
                    int messagelength = message.length();
                    if (messagelength == 54) {
                        manualup = message.substring(50, 52);
                    }else if (messagelength == 58) {
                        manualup = message.substring(54, 56);
                    }else if (messagelength == 62) {
                        manualup = message.substring(58, 60);
                    }else if (messagelength == 66) {
                        manualup = message.substring(62, 64);
                    }else if (messagelength == 70) {
                        manualup = message.substring(66, 68);
                    }
                }
                if (manualup == null){ //if manual update digit is null then its global response
                    System.out.println("Server update Initiated : " + devname + click);
                    changebuttonfunc(devname,click); //change btn func accordingly
                }
                else if (manualup.equals("00")) {
                    if(message.length() == 50){
                        //if manual update check for 00
                        String getinternalid = message.substring(42, 44); //get internal id to recognize the device number
                        String status = message.substring(44, 46); //get status of that device
                        System.out.println("Manual Update Initiated '50' : " + val + devmodel + getinternalid + status + " " + manualup);
                        searchfordev(devmodel,status,devname,getinternalid);
                    }
                    else if (message.length()>50){
                        int messagelength = message.length();
                        if (messagelength == 54) {
                            String getinternalid = message.substring(42, 44); //get internal id to recognize the device number
                            String status = message.substring(44, 46); //get status of that device
                            String getinternalid2 = message.substring(46, 48); //get internal id to recognize the device number
                            String status2 = message.substring(48, 50); //get status of that device
                            System.out.println("Manual Update Initiated '54': " + val + devmodel + getinternalid + status + " " + manualup);
                            searchfordev(devmodel,status,devname,getinternalid);
                            searchfordev(devmodel,status2,devname,getinternalid2);
                        }else if (messagelength == 58) {
                            String getinternalid = message.substring(42, 44); //get internal id to recognize the device number
                            String status = message.substring(44, 46); //get status of that device
                            String getinternalid2 = message.substring(46, 48); //get internal id to recognize the device number
                            String status2 = message.substring(48, 50); //get status of that device
                            String getinternalid3 = message.substring(50, 52); //get internal id to recognize the device number
                            String status3 = message.substring(52, 54); //get status of that device
                            System.out.println("Manual Update Initiated '58' : " + val + devmodel + getinternalid + status + " " + manualup);
                            searchfordev(devmodel,status,devname,getinternalid);
                            searchfordev(devmodel,status2,devname,getinternalid2);
                            searchfordev(devmodel,status3,devname,getinternalid3);
                        }else if (messagelength == 62) {
                            String getinternalid = message.substring(42, 44); //get internal id to recognize the device number
                            String status = message.substring(44, 46); //get status of that device
                            String getinternalid2 = message.substring(46, 48); //get internal id to recognize the device number
                            String status2 = message.substring(48, 50); //get status of that device
                            String getinternalid3 = message.substring(50, 52); //get internal id to recognize the device number
                            String status3 = message.substring(52, 54); //get status of that device
                            String getinternalid4 = message.substring(54, 56); //get internal id to recognize the device number
                            String status4 = message.substring(56, 58); //get status of that device
                            System.out.println("Manual Update Initiated '62': " + val + devmodel + getinternalid + status + " " + manualup);
                            searchfordev(devmodel,status,devname,getinternalid);
                            searchfordev(devmodel,status2,devname,getinternalid2);
                            searchfordev(devmodel,status3,devname,getinternalid3);
                            searchfordev(devmodel,status4,devname,getinternalid4);
                        }else if (messagelength == 66) {
                            String getinternalid = message.substring(42, 44); //get internal id to recognize the device number
                            String status = message.substring(44, 46); //get status of that device
                            String getinternalid2 = message.substring(46, 48); //get internal id to recognize the device number
                            String status2 = message.substring(48, 50); //get status of that device
                            String getinternalid3 = message.substring(50, 52); //get internal id to recognize the device number
                            String status3 = message.substring(52, 54); //get status of that device
                            String getinternalid4 = message.substring(54, 56); //get internal id to recognize the device number
                            String status4 = message.substring(56, 58); //get status of that device
                            String getinternalid5 = message.substring(58, 60); //get internal id to recognize the device number
                            String status5 = message.substring(60, 62); //get status of that device
                            System.out.println("Manual Update Initiated '66' : " + val + devmodel + getinternalid + status + " " + manualup);
                            searchfordev(devmodel,status,devname,getinternalid);
                            searchfordev(devmodel,status2,devname,getinternalid2);
                            searchfordev(devmodel,status3,devname,getinternalid3);
                            searchfordev(devmodel,status4,devname,getinternalid4);
                            searchfordev(devmodel,status5,devname,getinternalid5);
                        }else if (messagelength == 70) {
                            String getinternalid = message.substring(42, 44);
                            String status = message.substring(44, 46);
                            String getinternalid2 = message.substring(46, 48);
                            String status2 = message.substring(48, 50); //get status of that device
                            String getinternalid3 = message.substring(50, 52); //get internal id to recognize the device number
                            String status3 = message.substring(52, 54); //get status of that device
                            String getinternalid4 = message.substring(54, 56); //get internal id to recognize the device number
                            String status4 = message.substring(56, 58); //get status of that device
                            String getinternalid5 = message.substring(58, 60); //get internal id to recognize the device number
                            String status5 = message.substring(60, 62); //get status of that device
                            String getinternalid6 = message.substring(62, 64); //get internal id to recognize the device number
                            String status6 = message.substring(64, 66); //get status of that device
                            System.out.println("Manual Update Initiated '70' : " + val + devmodel + getinternalid + status + " " + manualup);
                            searchfordev(devmodel,status,devname,getinternalid);
                            searchfordev(devmodel,status2,devname,getinternalid2);
                            searchfordev(devmodel,status3,devname,getinternalid3);
                            searchfordev(devmodel,status4,devname,getinternalid4);
                            searchfordev(devmodel,status5,devname,getinternalid5);
                            searchfordev(devmodel,status6,devname,getinternalid6);
                        }
                    }
                    } else {
                    System.out.println("App update initiated :" + devname + click); //not manaul update not server update,ordinary update with AB in the end
                    String getinternalid = message.substring(42, 44);
                    String status = message.substring(44, 46);
                    if (click == null) { //if click null can mean that another device tried to control the switch u are in.
                        searchfordev(devmodel, status, devname, getinternalid);
                    }
                    if (click.equals("btnbacklight")) {
                        if (status.equals("01")) {
                            backlightbool = true;
                        } else {
                            backlightbool = false;
                        }
                    }
                    if (getinternalid.equals("08")) {
                        if (getinternalid.equals("08") && status.equals("01") || getinternalid.equals("8") && status.equals("1")) {
                            String event = "ON";
                            groupmessage(event);
                        } else if (getinternalid.equals("08") && status.equals("02") || getinternalid.equals("8") && status.equals("2")) {
                            String event = "OFF";
                            groupmessage(event);
                        }
                    } else {
                        if (!click.equals("btnbacklight")) {
                            changebuttonfunc(devname, click); //else normal update
                        }
                        UIhandler.post(new UpdateButtonState(click.toUpperCase()));
                    }
                }

                    activateallbuttons();
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

    public void backlight(){
        System.out.println("btnbacklight");
        point = v;
        int point2 = 0;
        click = "btnbacklight";
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
        if (backlightbool==false) {
            protocolBL = String.format("02 %s 00 00 00 83 03 %s 00 00 00 00 00 00 00 %s %s 01 AB 03", v7c, v4c, v6c, v10c);
        }else{
            protocolBL = String.format("02 %s 00 00 00 83 03 %s 00 00 00 00 00 00 00 %s %s 02 AB 03", v7c, v4c, v6c, v10c);
        }
        System.out.println(protocolBL);
        devicestatus = v1;

        this.Thread1OTHER = new Thread(new Thread1OTHER());
        this.Thread1OTHER.start();
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
        progressbarfan.setProgress(fan);
        //tvfanspeed.setText("");

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
//        btnplusfan.setEnabled(true);
//        btnminusfan.setEnabled(true);
        //tvfanspeed.setText("");

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

    public void lockopen(View view){
        lockclosed.setVisibility(View.VISIBLE);
        lockopen.setVisibility(View.GONE);
        int point = v;
        click = "lockopen";
        getcontroller();
        System.out.println("lockopen " + SERVER_IP);
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

        String v4c1 = dectohex(v4);
        String v7c1 = dectohex(v7);
        String v6c1 = dectohex(v6);
        String v10c1 = dectohex(v10);

        v4c = ("00" + v4c1).substring(v4c1.length());
        String v7c = ("00" + v7c1).substring(v7c1.length());
        String v6c = ("00" + v6c1).substring(v6c1.length());
        v10c = ("00" + v10c1).substring(v10c1.length());
        protcollockopen = String.format("02 %s 00 00 00 83 03 %s 00 00 00 00 00 00 00 %s %s 02 AB 03", v7c, v4c, v6c, v10c);
        System.out.println(protcollockopen);
        devicestatus = v1;

        this.Thread1OTHER = new Thread(new Thread1OTHER());
        this.Thread1OTHER.start();
    }

    public void lockclosed(View view){
        lockopen.setVisibility(View.VISIBLE);
        lockclosed.setVisibility(View.GONE);
        int point = v;
        click = "lockclosed";
        getcontroller();
        System.out.println("lockclosed " + SERVER_IP);
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

        String v4c1 = dectohex(v4);
        String v7c1 = dectohex(v7);
        String v6c1 = dectohex(v6);
        String v10c1 = dectohex(v10);

        v4c = ("00" + v4c1).substring(v4c1.length());
        String v7c = ("00" + v7c1).substring(v7c1.length());
        String v6c = ("00" + v6c1).substring(v6c1.length());
        v10c = ("00" + v10c1).substring(v10c1.length());
        protcollockclosed = String.format("02 %s 00 00 00 83 03 %s 00 00 00 00 00 00 00 %s %s 01 AB 03", v7c, v4c, v6c, v10c);
        System.out.println(lockclosed);
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
                if (statusvalidateinfo.equals("FAILED")) {
                    Toast.makeText(Home.this, "Unable to connect with your device!", Toast.LENGTH_SHORT).show();
                }else{
                    UIhandler.post(new UpdateButtonState(click.toUpperCase())); //to update state of button (ex:disbaled or enabled) by sending click value
                    UIhandler.post(new updateUIThread(v4c.toUpperCase())); //to update value of button(ex: on or off) by sending powerlineid
                }
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

    public void getsamedevcontrols(String devname,String status){

        String checkdev = cntrlstatusinfo.get(devname);
        String checkstate = cntrlstatusinfo.get(status);

        if (checkdev==null && checkstate==null){
            cntrlstatusinfo.put(devname, status);
            System.out.println("both null");
        }else {
            if (devname.matches(checkdev) && status.matches(checkstate)) { // check if dev name and status already exist before entering to hash map
                System.out.println("both true so dont input already exist");
            }else {
                cntrlstatusinfo.put(devname, status);
                System.out.println("values to cntrlstatusinfo : " + devname + " " + status);
            }
        }
        for (Map.Entry<String, String> entry : cntrlstatusinfo.entrySet()) {  //checking in hash map if data has been input
            String key = entry.getKey();
            String click = entry.getValue();
            System.out.println("getsamedevcontrols : " + "(Key : " + key + ") (Click :" + click + ")");
        }
    }

    public void getcntrlstate(String devname){
        for ( Map.Entry<String, String> entry : cntrlstatusinfo.entrySet()) {
            String key = entry.getKey();
            String click = entry.getValue();
            System.out.println("getcntrlstate : " + "Key :" + key + " Devname :" + devname +  " Click :" + click) ;
            if (cntrlstatusinfo.containsKey(devname)||cntrlstatusinfo.containsKey(devname+10)||cntrlstatusinfo.containsKey(devname+11)||cntrlstatusinfo.containsKey(devname+12)||cntrlstatusinfo.containsKey(devname+13)||cntrlstatusinfo.containsKey(devname+14)||cntrlstatusinfo.containsKey(devname+15)||cntrlstatusinfo.containsKey(devname+16)||cntrlstatusinfo.containsKey(devname+17)||cntrlstatusinfo.containsKey(devname+1)||cntrlstatusinfo.containsKey(devname+18)||cntrlstatusinfo.containsKey(devname+2)||cntrlstatusinfo.containsKey(devname+3)||cntrlstatusinfo.containsKey(devname+4)||cntrlstatusinfo.containsKey(devname+5)||cntrlstatusinfo.containsKey(devname+6)||cntrlstatusinfo.containsKey(devname+7)||cntrlstatusinfo.containsKey(devname+8)||cntrlstatusinfo.containsKey(devname+9)) {
                if (click == "1GoneOFF") {
                    off_1g.setVisibility(View.GONE);
                    on_1g.setVisibility(View.VISIBLE);
                }else if(click.equals("fcOFF")){
                    btnofffan.setVisibility(View.GONE);
                    btnonfan.setVisibility(View.VISIBLE);
                }else if (click == "1PLUGoneOFF") {
                    off_1plug.setVisibility(View.GONE);
                    on_1plug.setVisibility(View.VISIBLE);
                }else if (click == "1PLUGoneON") {
                    off_1plug.setVisibility(View.VISIBLE);
                    on_1plug.setVisibility(View.GONE);
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
            }
            else{
                System.out.println("Reset to ealier");
                if (click == "1GoneOFF") {
                    off_1g.setVisibility(View.VISIBLE);
                    on_1g.setVisibility(View.GONE);
                }else if(click.equals("fcOFF")){
                    btnofffan.setVisibility(View.VISIBLE);
                    btnonfan.setVisibility(View.GONE);
                } else if (click == "1PLUGoneOFF") {
                    off_1plug.setVisibility(View.VISIBLE);
                    on_1plug.setVisibility(View.GONE);
                } else if (click == "2GoneOFF") {
                    btnoff1_2g.setVisibility(View.VISIBLE);
                    btnon1_2g.setVisibility(View.GONE);
                } else if (click == "2GtwoOFF") {
                    btnoff2_2g.setVisibility(View.VISIBLE);
                    btnon2_2g.setVisibility(View.GONE);
                } else if (click == "3GoneOFF") {
                    off_3g.setVisibility(View.VISIBLE);
                    on_3g.setVisibility(View.GONE);
                } else if (click == "3GtwoOFF") {
                    off2_3g.setVisibility(View.VISIBLE);
                    on2_3g.setVisibility(View.GONE);
                } else if (click == "3GthreeOFF") {
                    off3_3g.setVisibility(View.VISIBLE);
                    on3_3g.setVisibility(View.GONE);
                } else if (click == "4GoneOFF") {
                    off_4g.setVisibility(View.VISIBLE);
                    on_4g.setVisibility(View.GONE);
                } else if (click == "4GtwoOFF") {
                    off2_4g.setVisibility(View.VISIBLE);
                    on2_4g.setVisibility(View.GONE);
                } else if (click == "4GthreeOFF") {
                    off3_4g.setVisibility(View.VISIBLE);
                    on3_4g.setVisibility(View.GONE);
                } else if (click == "4GfourOFF") {
                    off4_4g.setVisibility(View.VISIBLE);
                    on4_4g.setVisibility(View.GONE);
                } else if (click == "5GoneOFF") {
                    off_5g.setVisibility(View.VISIBLE);
                    on_5g.setVisibility(View.GONE);
                } else if (click == "5GtwoOFF") {
                    off2_5g.setVisibility(View.VISIBLE);
                    on2_5g.setVisibility(View.GONE);
                } else if (click == "5GthreeOFF") {
                    off3_5g.setVisibility(View.VISIBLE);
                    on3_5g.setVisibility(View.GONE);
                } else if (click == "5GfourOFF") {
                    off4_5g.setVisibility(View.VISIBLE);
                    on4_5g.setVisibility(View.GONE);
                } else if (click == "5GfiveOFF") {
                    off5_5g.setVisibility(View.VISIBLE);
                    on5_5g.setVisibility(View.GONE);
                }
            }
        }

    }

    class checkdevstatus extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String homeid = params[0];
            String data = "";
            int tmp;

            try {
                URL url = new URL("http://centraserv.idsworld.solutions:50/v1/Ape_srv/RawDeviceList/"); //to get device list
                String urlParams = "HomeID="+homeid;

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
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            } catch (IOException e) {
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
                JSONObject jObject = new JSONObject(s);

                final JSONArray jArray = jObject.getJSONArray("DEVICES");
                for (i = 0; i < jArray.length(); i++) {
                    try {
                        JSONObject oneObject = jArray.getJSONObject(i);
                        // Pulling items from the array
                        String devname = oneObject.getString("name");
                        String status = oneObject.getString("current_status");
                        String internalid = oneObject.getString("internal_id");
                        String devmodel = oneObject.getString("device_model");
                        System.out.println("Raw device list : " + devname + " " + status + " " + internalid);

                        if (status.equals("1")){
                            if (!devmodel.equals("PS")) {
                                updatecntrlstatusarray(devmodel, devname+i, internalid);
                                System.out.println(devname+i);
                            }else{
                                updatecntrlstatusarray(devmodel, devname, internalid);
                                System.out.println(devname);
                            }
                        }
                        else{
                            System.out.println("Move on");
                        }
                    } catch (JSONException e) {
                        // Oops
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
                err = "Exception: " + e.getMessage();
            }
           // if (devicenameAR.size()>0 && devicemodelAR.size()>0) {
                String model = devicemodelAR.get(v);
                getcntrlstate(model);
                System.out.println(model);
           // }
            for (Map.Entry<String, String> entry : cntrlstatusinfo.entrySet()) {  //checking in hash map if data has been input
                String key = entry.getKey();
                String click = entry.getValue();
                System.out.println("getsamedevcontrols : " + "(Key : " + key + ") (Click :" + click + ")");
            }

        }

    }

    public void activateallbuttons(){
        btnon1_2g.setEnabled(true);
        btnoff1_2g.setEnabled(true);
        btnon2_2g.setEnabled(true);
        btnoff2_2g.setEnabled(true);
        on_1plug.setEnabled(true);
        off_1plug.setEnabled(true);
        off_1g.setEnabled(true);
        on_1g.setEnabled(true);
        off_3g.setEnabled(true);
        on_3g.setEnabled(true);
        off2_3g.setEnabled(true);
        on2_3g.setEnabled(true);
        off3_3g.setEnabled(true);
        on3_3g.setEnabled(true);
        off_4g.setEnabled(true);
        on_4g.setEnabled(true);
        off2_4g.setEnabled(true);
        on2_4g.setEnabled(true);
        off3_4g.setEnabled(true);
        on3_4g.setEnabled(true);
        off4_4g.setEnabled(true);
        on4_4g.setEnabled(true);
        off_5g.setEnabled(true);
        on_5g.setEnabled(true);
        off2_5g.setEnabled(true);
        on2_5g.setEnabled(true);
        off3_5g.setEnabled(true);
        on3_5g.setEnabled(true);
        off4_5g.setEnabled(true);
        on4_5g.setEnabled(true);
        off5_5g.setEnabled(true);
        on5_5g.setEnabled(true);
        }

    public void changebuttonfunc(String devname,String click){
        String powerlinid = dectohex(powerlineidAR.get(v));
        String devmodel = devicenameAR.get(v);
        System.out.println("CBF : " + powerlinid + devmodel);
        getsamedevcontrols(devname,click);
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
        }else if(click.equals("fcOFF")){
            btnofffan.setVisibility(View.GONE);
            btnonfan.setVisibility(View.VISIBLE);
        }else if(click.equals("fcON")){
            btnofffan.setVisibility(View.VISIBLE);
            btnonfan.setVisibility(View.GONE);
        }
    }

    public void searchfordev(String devmodel,String status,String devname,String getinternalid){
//            String click;
        if (devmodel.equals("PS")) {
            switch (status) {
                case "01":
                    click = "1PLUGoneOFF";
                    changebuttonfunc(devname, click);
                    break;
                case "02":
                    click = "1PLUGoneON";
                    changebuttonfunc(devname, click);
                    break;
            }

        }else if (devmodel.equals("TS1G")) {
            switch (getinternalid) {
                case "01":
                    if (status.equals("01")) {
                        click = "1GoneOFF";
                        changebuttonfunc(devname, click);
                    } else if (status.equals("02")) {
                        click = "1GoneON";
                        changebuttonfunc(devname, click);
                    }
                    break;
            }
        } else if (devmodel.equals("TS2G")) {
            switch (getinternalid) {
                case "01":
                    if (status.equals("01")) {
                        click = "2GoneOFF";
                        changebuttonfunc(devname, click);
                    } else if (status.equals("02")) {
                        click = "2GoneON";
                        changebuttonfunc(devname, click);
                    }
                    break;
                case "02":
                    if (status.equals("01")) {
                        click = "2GtwoOFF";
                        changebuttonfunc(devname, click);
                    } else if (status.equals("02")) {
                        click = "2GtwoON";
                        changebuttonfunc(devname, click);
                    }
                    break;
            }
        }else if (devmodel.equals("FC")){
            if (status.equals("01")) {
                click = "fcOFF";
                System.out.println("Fan controls :" + devname + click);
                changebuttonfunc(devname, click);
            }else if (status.equals("02")) {
                click = "fcON";
                System.out.println("Fan controls :" + devname + click);
                changebuttonfunc(devname, click);
            }
        }else if (devmodel.equals("TS3G")) {
            switch (getinternalid) {
                case "01":
                    if (status.equals("01")) {
                        click = "3GoneOFF";
                        changebuttonfunc(devname, click);
                    } else if (status.equals("02")) {
                        click = "3GoneON";
                        changebuttonfunc(devname, click);
                    }
                    break;
                case "02":
                    if (status.equals("01")) {
                        click = "3GtwoOFF";
                        changebuttonfunc(devname, click);
                    } else if (status.equals("02")) {
                        click = "3GtwoON";
                        changebuttonfunc(devname, click);
                    }
                    break;
                case "03":
                    if (status.equals("01")) {
                        click = "3GthreeOFF";
                        changebuttonfunc(devname, click);
                    } else if (status.equals("02")) {
                        click = "3GthreeON";
                        changebuttonfunc(devname, click);
                    }
                    break;
            }

    }else if (devmodel.equals("TS4G")) {
            switch (getinternalid) {
                case "01":
                    if (status.equals("01")) {
                        click = "4GoneOFF";
                        changebuttonfunc(devname, click);
                    } else if (status.equals("02")) {
                        click = "4GoneON";
                        changebuttonfunc(devname, click);
                    }
                    break;
                case "02":
                    if (status.equals("01")) {
                        click = "4GtwoOFF";
                        changebuttonfunc(devname, click);
                    } else if (status.equals("02")) {
                        click = "4GtwoON";
                        changebuttonfunc(devname, click);
                    }
                    break;
                case "03":
                    if (status.equals("01")) {
                        click = "4GthreeOFF";
                        changebuttonfunc(devname, click);
                    } else if (status.equals("02")) {
                        click = "4GthreeON";
                        changebuttonfunc(devname, click);
                    }
                    break;
                case "04":
                    if (status.equals("01")) {
                        click = "4GfourOFF";
                        changebuttonfunc(devname, click);
                    } else if (status.equals("02")) {
                        click = "4GfourON";
                        changebuttonfunc(devname, click);
                    }
                    break;
            }

        }else if (devmodel.equals("TS5G")) {
            switch (getinternalid) {
                case "01":
                    if (status.equals("01")) {
                        click = "5GoneOFF";
                        changebuttonfunc(devname, click);
                    } else if (status.equals("02")) {
                        click = "5GoneON";
                        changebuttonfunc(devname, click);
                    }
                    break;
                case "02":
                    if (status.equals("01")) {
                        click = "5GtwoOFF";
                        changebuttonfunc(devname, click);
                    } else if (status.equals("02")) {
                        click = "5GtwoON";
                        changebuttonfunc(devname, click);
                    }
                    break;
                case "03":
                    if (status.equals("01")) {
                        click = "5GthreeOFF";
                        changebuttonfunc(devname, click);
                    } else if (status.equals("02")) {
                        click = "5GthreeON";
                        changebuttonfunc(devname, click);
                    }
                    break;
                case "04":
                    if (status.equals("01")) {
                        click = "5GfourOFF";
                        changebuttonfunc(devname, click);
                    } else if (status.equals("02")) {
                        click = "5GfourON";
                        changebuttonfunc(devname, click);
                    }
                    break;
                case "05":
                    if (status.equals("01")) {
                        click = "5GfiveOFF";
                        changebuttonfunc(devname, click);
                    } else if (status.equals("02")) {
                        click = "5GfiveON";
                        changebuttonfunc(devname, click);
                    }
                    break;
            }

        }

}

    public void updatecntrlstatusarray(String devmodel,String devname,String getinternalid){
        System.out.println("updatecntrlstatusarray : " + devmodel + devname + getinternalid);
        if (devmodel.equals("PS")) {
                    click = "1PLUGoneOFF";
            cntrlstatusinfo.put(devname, click);

        }else if (devmodel.equals("TS1G")) {
            switch (getinternalid) {
                case "1":
                    click = "1GoneOFF";
                    cntrlstatusinfo.put(devname, click);
                    break;
            }
        } else if (devmodel.equals("TS2G")) {
            switch (getinternalid) {
                case "1":
                    click = "2GoneOFF";
                    cntrlstatusinfo.put(devname, click);
                    break;
                case "2":
                        click = "2GtwoOFF";
                    cntrlstatusinfo.put(devname, click);
                    break;
            }
        }else if (devmodel.equals("FC")){
            switch (getinternalid) {
                case "6":
                click = "fcOFF";
            cntrlstatusinfo.put(devname, click);
                    break;}
        }else if (devmodel.equals("TS3G")) {
            switch (getinternalid) {
                case "1":
                        click = "3GoneOFF";
                    cntrlstatusinfo.put(devname, click);
                    break;
                case "2":
                        click = "3GtwoOFF";
                    cntrlstatusinfo.put(devname, click);
                    break;
                case "3":
                        click = "3GthreeOFF";
                    cntrlstatusinfo.put(devname, click);
                    break;
            }
        }else if (devmodel.equals("TS4G")) {
            switch (getinternalid) {
                case "1":
                        click = "4GoneOFF";
                    cntrlstatusinfo.put(devname, click);
                    break;
                case "2":
                        click = "4GtwoOFF";
                    cntrlstatusinfo.put(devname, click);
                    break;
                case "3":
                        click = "4GthreeOFF";
                    cntrlstatusinfo.put(devname, click);
                    break;
                case "4":
                        click = "4GfourOFF";
                    cntrlstatusinfo.put(devname, click);
                    break;
            }

        }else if (devmodel.equals("TS5G")) {
            switch (getinternalid) {
                case "1":
                        click = "5GoneOFF";
                        cntrlstatusinfo.put(devname, click);
                    break;
                case "2":
                        click = "5GtwoOFF";
                    cntrlstatusinfo.put(devname, click);
                    break;
                case "3":
                        click = "5GthreeOFF";
                    cntrlstatusinfo.put(devname, click);;
                    break;
                case "4":
                        click = "5GfourOFF";
                    cntrlstatusinfo.put(devname, click);
                    break;
                case "5":
                        click = "5GfiveOFF";
                    cntrlstatusinfo.put(devname, click);
                    break;
            }

        }
    }

    public void backlightcmnd(){
        backlight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String device = devicenameAR.get(v);
                String name = devicemodelAR.get(v);
                String pid = powerlineidAR.get(v);
                if (isChecked) {
                    System.out.println("ischeck " + name);
                    backlight();
                    blstatusinfo.put(name,"btnbacklight");
                    switch (device){
                        case "TS1G":
                            off_1g.setColorFilter(Color.BLUE);
                        case "TS2G":
                            btnoff1_2g.setColorFilter(Color.BLUE);
                            btnoff2_2g.setColorFilter(Color.BLUE);
                        case "TS3G":
                            off_3g.setColorFilter(Color.BLUE);
                            off2_3g.setColorFilter(Color.BLUE);
                            off3_3g.setColorFilter(Color.BLUE);
                        case "TS4G":
                            off_4g.setColorFilter(Color.BLUE);
                            off2_4g.setColorFilter(Color.BLUE);
                            off3_4g.setColorFilter(Color.BLUE);
                            off4_4g.setColorFilter(Color.BLUE);
                        case "TS5G":
                            off_5g.setColorFilter(Color.BLUE);
                            off2_5g.setColorFilter(Color.BLUE);
                            off3_5g.setColorFilter(Color.BLUE);
                            off4_5g.setColorFilter(Color.BLUE);
                            off5_5g.setColorFilter(Color.BLUE);
                        case "FC":
                            btnofffan.setColorFilter(Color.BLUE);
                            btnplusfan.setColorFilter(Color.BLUE);
                            btnminusfan.setColorFilter(Color.BLUE);
                        case "BC":
                            bell.setColorFilter(Color.BLUE);
                    }

                } else {
                    System.out.println("nocheck " + name);
                    backlight();
                    blstatusinfo.remove(name);
                    switch (device){
                        case "TS1G":
                            off_1g.setColorFilter(Color.WHITE);
                        case "TS2G":
                            btnoff1_2g.setColorFilter(Color.WHITE);
                            btnoff2_2g.setColorFilter(Color.WHITE);
                        case "TS3G":
                            off_3g.setColorFilter(Color.WHITE);
                            off2_3g.setColorFilter(Color.WHITE);
                            off3_3g.setColorFilter(Color.WHITE);
                        case "TS4G":
                            off_4g.setColorFilter(Color.WHITE);
                            off2_4g.setColorFilter(Color.WHITE);
                            off3_4g.setColorFilter(Color.WHITE);
                            off4_4g.setColorFilter(Color.WHITE);
                        case "TS5G":
                            off_5g.setColorFilter(Color.WHITE);
                            off2_5g.setColorFilter(Color.WHITE);
                            off3_5g.setColorFilter(Color.WHITE);
                            off4_5g.setColorFilter(Color.WHITE);
                            off5_5g.setColorFilter(Color.WHITE);
                        case "FC":
                            btnofffan.setColorFilter(Color.WHITE);
                            btnplusfan.setColorFilter(Color.WHITE);
                            btnminusfan.setColorFilter(Color.WHITE);
                        case "BC":
                            bell.setColorFilter(Color.WHITE);
                    }

                }
            }
        });
    }

    public void sendbellduration(String duration){
        int dur = Integer.parseInt(duration);
        if (dur>10){
            Toast.makeText(Home.this, "Duration should be less than 10", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(Home.this, duration, Toast.LENGTH_SHORT).show();
            int point = v;
            click = "duration";
            getcontroller();
            System.out.println("duration " + SERVER_IP);
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
            protcolduration = String.format("02 %s 00 00 00 83 03 %s 00 00 00 00 00 00 00 %s %s %s AB 03", v7c, v4c, v6c, v10c,duration);
            System.out.println(protcolduration);
            devicestatus = v1;

            this.Thread1OTHER = new Thread(new Thread1OTHER());
            this.Thread1OTHER.start();
        }
    }

    public void sendbellinterval(String interval){
        int intrvl = Integer.parseInt(interval);
        if (intrvl>10 || interval.equals("")){
            Toast.makeText(Home.this, "Interval should be less than 10", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(Home.this, interval, Toast.LENGTH_SHORT).show();
            int point = v;
            click = "interval";
            getcontroller();
            System.out.println("interval " + SERVER_IP);
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

            String v4c1 = dectohex(v4);
            String v7c1 = dectohex(v7);
            String v6c1 = dectohex(v6);
            String v10c1 = dectohex(v10);

            v4c = ("00" + v4c1).substring(v4c1.length());
            String v7c = ("00" + v7c1).substring(v7c1.length());
            String v6c = ("00" + v6c1).substring(v6c1.length());
            v10c = ("00" + v10c1).substring(v10c1.length());
            protcolinterval = String.format("02 %s 00 00 00 83 03 %s 00 00 00 00 00 00 00 %s %s %s AB 03", v7c, v4c, v6c, v10c,interval);
            System.out.println(protcolinterval);
            devicestatus = v1;

            this.Thread1OTHER = new Thread(new Thread1OTHER());
            this.Thread1OTHER.start();
        }

    }

    public void explainduration(View view) {

        String title = "Duration?";
        String message = "Used to customize bells ringing tone,A number between 1-10";

        HelperT.showExplanationAlertDialog(message, title, Home.this);
    }

    public void explainintrvl(View view) {

        String title = "Interval?";
        String message = "Used to customize wait time until next ring,A number between 10";

        HelperT.showExplanationAlertDialog(message, title, Home.this);
    }

    public void checkbacklightstate(String devname,String model){

        for ( Map.Entry<String, String> entry : blstatusinfo.entrySet()) {
            String key = entry.getKey();
            String click = entry.getValue();
            System.out.println("getBLstate : " + "Key :" + key + "( Devname :" + devname + " Devname :" + model +  ") Click :" + click) ;
            if (blstatusinfo.containsKey(devname)) {
                if (click.equals("btnbacklight")) {
                    System.out.println("getBLstate : SUCCESS");
                    switch (model){
                        case "TS1G":
                            off_1g.setColorFilter(Color.BLUE);
                        case "TS2G":
                            btnoff1_2g.setColorFilter(Color.BLUE);
                            btnoff2_2g.setColorFilter(Color.BLUE);
                        case "TS3G":
                            off_3g.setColorFilter(Color.BLUE);
                            off2_3g.setColorFilter(Color.BLUE);
                            off3_3g.setColorFilter(Color.BLUE);
                        case "TS4G":
                            off_4g.setColorFilter(Color.BLUE);
                            off2_4g.setColorFilter(Color.BLUE);
                            off3_4g.setColorFilter(Color.BLUE);
                            off4_4g.setColorFilter(Color.BLUE);
                        case "TS5G":
                            off_5g.setColorFilter(Color.BLUE);
                            off2_5g.setColorFilter(Color.BLUE);
                            off3_5g.setColorFilter(Color.BLUE);
                            off4_5g.setColorFilter(Color.BLUE);
                            off5_5g.setColorFilter(Color.BLUE);
                        case "FC":
                            btnofffan.setColorFilter(Color.BLUE);
                            btnplusfan.setColorFilter(Color.BLUE);
                            btnminusfan.setColorFilter(Color.BLUE);
                        case "BC":
                            bell.setColorFilter(Color.BLUE);
                    }
                    backlight.setChecked(true);
                }}
                else{
                System.out.println("getBLstate : off");
                switch (model){
                    case "TS1G":
                        off_1g.setColorFilter(Color.WHITE);
                    case "TS2G":
                        btnoff1_2g.setColorFilter(Color.WHITE);
                        btnoff2_2g.setColorFilter(Color.WHITE);
                    case "TS3G":
                        off_3g.setColorFilter(Color.WHITE);
                        off2_3g.setColorFilter(Color.WHITE);
                        off3_3g.setColorFilter(Color.WHITE);
                    case "TS4G":
                        off_4g.setColorFilter(Color.WHITE);
                        off2_4g.setColorFilter(Color.WHITE);
                        off3_4g.setColorFilter(Color.WHITE);
                        off4_4g.setColorFilter(Color.WHITE);
                    case "TS5G":
                        off_5g.setColorFilter(Color.WHITE);
                        off2_5g.setColorFilter(Color.WHITE);
                        off3_5g.setColorFilter(Color.WHITE);
                        off4_5g.setColorFilter(Color.WHITE);
                        off5_5g.setColorFilter(Color.WHITE);
                    case "FC":
                        btnofffan.setColorFilter(Color.WHITE);
                        btnplusfan.setColorFilter(Color.WHITE);
                        btnminusfan.setColorFilter(Color.WHITE);
                    case "BC":
                        bell.setColorFilter(Color.WHITE);
                }
                backlight.setChecked(false);
            }
            }


}

    public void groupmessage(String event){
            if (event.equals("ON")){
                    btnon1_2g.setVisibility(View.VISIBLE);
                    btnoff1_2g.setVisibility(View.GONE);
                    btnon2_2g.setVisibility(View.VISIBLE);
                    btnoff2_2g.setVisibility(View.GONE);
                    off_1plug.setVisibility(View.GONE);
                    on_1plug.setVisibility(View.VISIBLE);
                    on_1g.setVisibility(View.VISIBLE);
                    off_1g.setVisibility(View.GONE);
                    on_3g.setVisibility(View.VISIBLE);
                    off_3g.setVisibility(View.GONE);
                    on2_3g.setVisibility(View.VISIBLE);
                    off2_3g.setVisibility(View.GONE);
                    on3_3g.setVisibility(View.VISIBLE);
                    off3_3g.setVisibility(View.GONE);
                    off_4g.setVisibility(View.GONE);
                    on_4g.setVisibility(View.VISIBLE);
                    off2_4g.setVisibility(View.GONE);
                    on2_4g.setVisibility(View.VISIBLE);
                    off3_4g.setVisibility(View.GONE);
                    on3_4g.setVisibility(View.VISIBLE);
                    off4_4g.setVisibility(View.GONE);
                    on4_4g.setVisibility(View.VISIBLE);
                    on_5g.setVisibility(View.VISIBLE);
                    off_5g.setVisibility(View.GONE);
                    on2_5g.setVisibility(View.VISIBLE);
                    off2_5g.setVisibility(View.GONE);
                    on3_5g.setVisibility(View.VISIBLE);
                    off3_5g.setVisibility(View.GONE);
                    on4_5g.setVisibility(View.VISIBLE);
                    off4_5g.setVisibility(View.GONE);
                    on5_5g.setVisibility(View.VISIBLE);
                    off5_5g.setVisibility(View.GONE);
                    btnofffan.setVisibility(View.GONE);
                    btnonfan.setVisibility(View.VISIBLE);
            }else if (event.equals("OFF")){
                    btnon1_2g.setVisibility(View.GONE);
                    btnoff1_2g.setVisibility(View.VISIBLE);
                    btnon2_2g.setVisibility(View.GONE);
                    btnoff2_2g.setVisibility(View.VISIBLE);
                    off_1plug.setVisibility(View.VISIBLE);
                    on_1plug.setVisibility(View.GONE);
                    on_1g.setVisibility(View.GONE);
                    off_1g.setVisibility(View.VISIBLE);
                    on_3g.setVisibility(View.GONE);
                    off_3g.setVisibility(View.VISIBLE);
                    on2_3g.setVisibility(View.GONE);
                    off2_3g.setVisibility(View.VISIBLE);
                    on3_3g.setVisibility(View.GONE);
                    off3_3g.setVisibility(View.VISIBLE);
                    off_4g.setVisibility(View.VISIBLE);
                    on_4g.setVisibility(View.GONE);
                    off2_4g.setVisibility(View.VISIBLE);
                    on2_4g.setVisibility(View.GONE);
                    off3_4g.setVisibility(View.VISIBLE);
                    on3_4g.setVisibility(View.GONE);
                    off4_4g.setVisibility(View.VISIBLE);
                    on4_4g.setVisibility(View.GONE);
                    on_5g.setVisibility(View.GONE);
                    off_5g.setVisibility(View.VISIBLE);
                    on2_5g.setVisibility(View.GONE);
                    off2_5g.setVisibility(View.VISIBLE);
                    on3_5g.setVisibility(View.GONE);
                    off3_5g.setVisibility(View.VISIBLE);
                    on4_5g.setVisibility(View.GONE);
                    off4_5g.setVisibility(View.VISIBLE);
                    on5_5g.setVisibility(View.GONE);
                    off5_5g.setVisibility(View.VISIBLE);
                    btnofffan.setVisibility(View.VISIBLE);
                    btnonfan.setVisibility(View.GONE);
            }
    }
}


