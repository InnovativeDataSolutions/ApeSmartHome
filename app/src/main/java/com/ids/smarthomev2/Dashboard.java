package com.ids.smarthomev2;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dashboard extends AppCompatActivity {

    ListView listView;
    Spinner spdash;
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    int i;
    ArrayAdapter<String> adapter;
    String devname,status,siteid = "H001",selected,itemTouched,statusOfDevice,powerlineid,internalid;
    List<String> devicenameAR = new ArrayList<String>(); //stores all devices
    List<String> devicenameAR_ON = new ArrayList<String>(); //stores devices which are only on
    List<String> devicenameAR_OFF = new ArrayList<String>(); //stores devices which are only off
    List<String> statusAR = new ArrayList<String>(); //stores statuses of devices example: on or off , 01= ON // 02=OFF
    Map<String, String> deviceandstatus = new HashMap<String, String>(); //hashmap which stroes both device name and status
    Map<String, String> deviceandPowerlineid = new HashMap<String, String>();
    Map<String, String> deviceandInternalid = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
//        The dashboard page is used to control appliances of the home from outside using mobile data or wifi,
//        so that user can check the current status of the devices at home
        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        spdash = (Spinner)findViewById(R.id.spdash);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        if (devicenameAR.size()>0 ||devicenameAR_ON.size()>0 ||devicenameAR_OFF.size()>0 ||statusAR.size()>0 ||deviceandstatus.size()>0 ||deviceandPowerlineid.size()>0 ||deviceandInternalid.size()>0){
            devicenameAR.clear();
            devicenameAR_ON.clear();
            devicenameAR_OFF.clear();
            statusAR.clear();
            deviceandstatus.clear();
            deviceandPowerlineid.clear();
            deviceandInternalid.clear();
            System.out.println("All data arrays cleared!");
        }

        checkfordevice cfd = new checkfordevice();
        cfd.execute(siteid);


        listView = (ListView) findViewById(R.id.dashboardlv);

            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, devicenameAR);
            listView.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.spdash, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spdash.setAdapter(adapter2);

        spdash.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position==1){
                    adapter = new ArrayAdapter<String>(Dashboard.this, android.R.layout.simple_list_item_1, devicenameAR_ON);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    selected = "ON";
                }else if (position ==2){
                    adapter = new ArrayAdapter<String>(Dashboard.this, android.R.layout.simple_list_item_1, devicenameAR_OFF);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    selected = "OFF";
                }else if (position ==3){
                    adapter = new ArrayAdapter<String>(Dashboard.this, android.R.layout.simple_list_item_1, devicenameAR);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    selected = "ALL";
                }else if (position ==0){
                    selected = "nil";
                }

            } @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }



        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View view, final int position, long id) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Dashboard.this);
                switch (selected){
                    case "ON" : //if selecting device from ON section
                        alertDialogBuilder.setMessage("This device is currently ON,do you want to OFF this device?");
                        break;
                    case "OFF" : //if selecting device from OFF section
                        alertDialogBuilder.setMessage("This device is currently OFF,do you want to ON this device?");
                        break;
                    default: //if selecting device from SHOW ALL section we first check if its on or off to produce suitable message
                        String deviceIdentifier = devicenameAR.get(position);
                        statusOfDevice = deviceandstatus.get(deviceIdentifier);
                        switch (statusOfDevice){
                            case "1" :
                                alertDialogBuilder.setMessage("This device is currently ON,do you want to OFF this device?");
                                break;
                            case "2" :
                                alertDialogBuilder.setMessage("This device is currently OFF,do you want to ON this device?");
                                break;
                            default:
                                alertDialogBuilder.setMessage("This device status cannot be changed");
                                break;
                        }

                }
                    alertDialogBuilder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    if (selected.equals("ON")){
                                        itemTouched = devicenameAR_ON.get(position);
                                        String itemTouchedPowerlineid = deviceandPowerlineid.get(itemTouched);
                                        String itemTouchedInternalid = deviceandInternalid.get(itemTouched);
                                        devicenameAR_OFF.add(itemTouched);
                                        devicenameAR_ON.remove(position);
                                        SendCmnd sc = new SendCmnd();
                                        sc.execute(siteid,itemTouchedPowerlineid,itemTouchedInternalid,"02");
                                        adapter.notifyDataSetChanged();
                                    }else if (selected.equals("OFF")){
                                        itemTouched = devicenameAR_OFF.get(position);
                                        String itemTouchedPowerlineid = deviceandPowerlineid.get(itemTouched);
                                        String itemTouchedInternalid = deviceandInternalid.get(itemTouched);
                                        devicenameAR_ON.add(itemTouched);
                                        devicenameAR_OFF.remove(position);
                                        SendCmnd sc = new SendCmnd();
                                        sc.execute(siteid,itemTouchedPowerlineid,itemTouchedInternalid,"01");
                                        adapter.notifyDataSetChanged();
                                    }else{
                                        if (statusOfDevice.equals("1")){
                                            itemTouched = devicenameAR_ON.get(position);
                                            String itemTouchedPowerlineid = deviceandPowerlineid.get(itemTouched);
                                            String itemTouchedInternalid = deviceandInternalid.get(itemTouched);
                                            devicenameAR_OFF.add(itemTouched);
                                            devicenameAR_ON.remove(position);
                                            devicenameAR.remove(position);
                                            SendCmnd sc = new SendCmnd();
                                            sc.execute(siteid,itemTouchedPowerlineid,itemTouchedInternalid,"02");
                                            adapter.notifyDataSetChanged();
                                        }else if (statusOfDevice.equals("2")){
                                            itemTouched = devicenameAR_OFF.get(position);
                                            String itemTouchedPowerlineid = deviceandPowerlineid.get(itemTouched);
                                            String itemTouchedInternalid = deviceandInternalid.get(itemTouched);
                                            devicenameAR_ON.add(itemTouched);
                                            devicenameAR_OFF.remove(position);
                                            devicenameAR.remove(position);
                                            SendCmnd sc = new SendCmnd();
                                            sc.execute(siteid,itemTouchedPowerlineid,itemTouchedInternalid,"01");
                                            adapter.notifyDataSetChanged();
                                        }
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
            }

        });

    }

    class checkfordevice extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String homeid = params[0];
            String data = "";
            int tmp;

            try {
                URL url = new URL("http://centraserv.idsworld.solutions:50/v1/Ape_srv/RawDeviceList/"); //SERVICE USED FOR TESTING PURPOSE
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
                        powerlineid = oneObject.getString("powerline_id");
                        internalid = oneObject.getString("internal_id");
                        devname = oneObject.getString("dev_name");
                        status = oneObject.getString("current_status");

                        deviceandPowerlineid.put(devname,powerlineid);
                        deviceandInternalid.put(devname,internalid);
                        deviceandstatus.put(devname,status);
                        devicenameAR.add(devname);
                        statusAR.add(status);
                        if (status.equals("2")){
                            devicenameAR_OFF.add(devname);
                            System.out.println("status of OFF devices : " + devname + ":" + status);
                        }else if (status.equals("1")){
                            devicenameAR_ON.add(devname);
                            System.out.println("status of ON devices : " + devname + ":" + status);
                        }
                        System.out.println("status of devices : " + devname + ":" + status + ":" + powerlineid + ":" + internalid);
                    } catch (JSONException e) {
                        // Oops
                    }
                }
                adapter.notifyDataSetChanged();

                System.out.println("Raw device list : " + s);
            } catch (JSONException e) {
                e.printStackTrace();
                err = "Exception: " + e.getMessage();
            }

        }

    }

    class SendCmnd extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String homeid = params[0];
            String powerline_id = params[0];
            String internal_id = params[0];
            String event = params[0];
            String data = "";
            int tmp;

            try {
                URL url = new URL("http://centraserv.idsworld.solutions:50/v1/Ape_srv/DeviceEvent/");
                String urlParams = "HomeID="+homeid+"&powerline_id ="+powerline_id+"&internal_id ="+internal_id+"&action_event="+event;

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
                JSONObject user_data = new JSONObject(s);
                String statusvalidateinfo = user_data.getString("STATUS");
                String validateip = user_data.getString("DESC");
                System.out.println("Staus of validate info : " + statusvalidateinfo + validateip);
            } catch (JSONException e) {
                e.printStackTrace();
                err = "Exception: " + e.getMessage();
            }

        }

    }


    private void addDrawerItems() {
        String[] osArray = { "Home", "Dashboard"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                        Intent go = new Intent(Dashboard.this, Home.class); //if home already exist proceed to controller page
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
            return true;
        }

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
