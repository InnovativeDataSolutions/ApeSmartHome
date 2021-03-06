package com.ids.smarthomev2;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Registration extends AppCompatActivity {
    EditText unET, pwET, homeidET, gatewayET, ipET;
    TextView tv;
    boolean dbcheck1, dbcheck2;
    LinearLayout progressLL;
    Button regBUT, submitBUT,enterBUT;
    Context ctx = this;
    long count;
    ImageButton sq_explain_ip;
    Database db = new Database(ctx);
    String homeid, un, pw, oneObjectsItem, oneObjectsItem12, oneObjectsItem2, oneObjectsItem3, oneObjectsItem4, oneObjectsItem5, oneObjectsItem6, oneObjectsItem7, oneObjectsItem8, oneObjectsItem9, oneObjectsItem10, oneObjectsItem11, oneObjectsItem13, oneObjectsItem14, oneObjectsItem15, oneObjectsItem16, ip, gateway, statussubmitinfo, statusvalidateinfo, validateip;
    int i, j;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        homeidET = (EditText) findViewById(R.id.homeidET);
        unET = (EditText) findViewById(R.id.usernameET);
        pwET = (EditText) findViewById(R.id.passwordET);
        gatewayET = (EditText) findViewById(R.id.gatewayET);
        ipET = (EditText) findViewById(R.id.ipaddressET);
        regBUT = (Button) findViewById(R.id.regbtn);
        submitBUT = (Button) findViewById(R.id.submitbtn);
        enterBUT = (Button) findViewById(R.id.enter);
        tv = (TextView) findViewById(R.id.tvinfo);
        sq_explain_ip = (ImageButton)findViewById(R.id.sq_explain_ip);
        progressLL = (LinearLayout)findViewById(R.id.progressLL);

        count = db.check2(); //check if home already registered

        if (count > 0) {
            Intent go = new Intent(ctx, Home.class); //if home already exist proceed to controller page
            startActivity(go);
        }

    }

    class submitinfo extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String homeid = params[0];
            String username = params[1];
            String password = params[2];
            String gateway = params[3];
            String data = "";
            int tmp;

            try {
                URL url = new URL("http://centraserv.idsworld.solutions:50/v1/Ape_srv/Register/"); //service to register home
                //URL url = new URL("http://1.186.45.172:85/Hotel/Srv_Reservation/SRV_iCOMM_DeviceStatus/");
                String urlParams = "HomeID="+homeid+"&Username="+username+"&Password="+password+"&GatewayIP="+gateway;

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
                statussubmitinfo = user_data.getString("STATUS");
                ip = user_data.getString("MODEM_IP");
                System.out.println("Status of submit info : " + ip + statussubmitinfo);
            } catch (JSONException e) {
                e.printStackTrace();
                err = "Exception: " + e.getMessage();
            }

            if(statussubmitinfo == null){
                Toast.makeText(Registration.this, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
                progressLL.setVisibility(View.GONE);
            } else if (statussubmitinfo.equals("AVAILABLE")) {
                Toast.makeText(Registration.this, "Registration Succesful!", Toast.LENGTH_SHORT).show();
                progressLL.setVisibility(View.GONE);
                tv.setText("Corresponding IP for your gateway recieved please verify if necessary");
                tv.setTextSize(16);
                submitBUT.setVisibility(View.GONE);
                homeidET.setVisibility(View.GONE);
                unET.setVisibility(View.GONE);
                pwET.setVisibility(View.GONE);
                ipET.setVisibility(View.VISIBLE);
                sq_explain_ip.setVisibility(View.VISIBLE);
                regBUT.setVisibility(View.VISIBLE);
                ipET.setText(ip);
            } else {
                Toast.makeText(Registration.this, "Something went wrong please try again", Toast.LENGTH_SHORT).show();
                progressLL.setVisibility(View.GONE);
            }
        }


    }

    class validateinfo extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String homeid = params[0];
            String ip = params[1];
            String gateway = params[2];
            String data = "";
            int tmp;

            try {
                URL url = new URL("http://centraserv.idsworld.solutions:50/v1/Ape_srv/HomeValidate/"); //validate home with gateway address
                //URL url = new URL("http://1.186.45.172:85/Hotel/Srv_Reservation/SRV_iCOMM_DeviceStatus/");
                String urlParams = "HomeID="+homeid+"&MODEM_IP="+ip+"&GatewayIP="+gateway;

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
                statusvalidateinfo = user_data.getString("STATUS");
                validateip = user_data.getString("MODEM_IP");
                System.out.println("Staus of validate info : " + statusvalidateinfo + validateip);
            } catch (JSONException e) {
                e.printStackTrace();
                err = "Exception: " + e.getMessage();
            }

            //Toast.makeText(Registration.this, s, Toast.LENGTH_SHORT).show();

            if (statusvalidateinfo.contains("AVAILABLE")) {
                checkfordevice cfd = new checkfordevice();
                cfd.execute(homeid);
                tv.setText("Home IP & Gateway succesfully verified!");
                progressLL.setVisibility(View.GONE);
                gatewayET.setEnabled(false);
                ipET.setEnabled(false);
                regBUT.setVisibility(View.GONE);
                enterBUT.setVisibility(View.VISIBLE);
            }else if(statusvalidateinfo == null){
                progressLL.setVisibility(View.GONE);
                Toast.makeText(Registration.this, "Please check your internet connection or try again!", Toast.LENGTH_SHORT).show();
            }

        }


    }

    class checkfordevice extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String homeid = params[0];
            String data = "";
            int tmp;

            try {
                URL url = new URL("http://centraserv.idsworld.solutions:50/v1/Ape_srv/DeviceList/"); //get device list of home frm service
                //URL url = new URL("http://1.186.45.172:85/Hotel/Srv_Reservation/SRV_iCOMM_DeviceStatus/"); + "&ip=" + ip + "&homeid=" + homeid
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

            //System.out.println("json array" + s);

            try {
                JSONObject jObject = new JSONObject(s);

                final JSONArray jArray = jObject.getJSONArray("DEVICES");
                for (i = 0; i < jArray.length(); i++) {
                    try {
                        JSONObject oneObject = jArray.getJSONObject(i);
                        // Pulling items from the array
                        oneObjectsItem = oneObject.getString("physical_id");
                        oneObjectsItem2 = oneObject.getString("powerline_id");
                        oneObjectsItem3 = oneObject.getString("name");
                        oneObjectsItem4 = oneObject.getString("area");
                        oneObjectsItem5 = oneObject.getString("device_model");
                        oneObjectsItem6 = oneObject.getString("device_code");
                        oneObjectsItem7 = oneObject.getString("command_id");
                        oneObjectsItem8 = oneObject.getString("master_id");

                        final JSONArray jArray2 = oneObject.getJSONArray("Controllers");
                        for (j = 0; j < jArray2.length(); j++) {
                            try {
                                JSONObject oneObject2 = jArray2.getJSONObject(j);

                                oneObjectsItem12 = oneObject2.getString("device_id");
                                oneObjectsItem13 = oneObject2.getString("internal_id");
                                oneObjectsItem14 = oneObject2.getString("dev_name");
                                oneObjectsItem15 = oneObject2.getString("control_type");
                                oneObjectsItem16 = oneObject2.getString("current_status");

                            }catch (Exception e){

                            }
                            System.out.println("Controller info : " + oneObjectsItem12 + oneObjectsItem13 + oneObjectsItem14 + oneObjectsItem15 + oneObjectsItem16);
                            dbcheck1 = db.insertcontrollerinfo(oneObjectsItem2, oneObjectsItem12, oneObjectsItem13, oneObjectsItem14, oneObjectsItem15, oneObjectsItem16);
                            if (dbcheck1 == true) {
                                System.out.println("controller info DB inserted " + i + " " + j + " " + oneObjectsItem2 + " " + oneObjectsItem12 + " " + oneObjectsItem13 + " " + oneObjectsItem14 + " " + oneObjectsItem15 + " " + oneObjectsItem16);
                            } else {
                                System.out.println("controller db failure");
                            }
                        }



                    } catch (JSONException e) {
                        // Oops
                    }

                    System.out.println("check for status of devices : " + oneObjectsItem + " " + oneObjectsItem2 + " " + oneObjectsItem3 + " " + oneObjectsItem4 + " " + oneObjectsItem5 + " " + oneObjectsItem6 + " " + oneObjectsItem7 + " " + oneObjectsItem8);

                    dbcheck2 = db.inserthomeinfo(homeid, un, gateway, ip, oneObjectsItem, oneObjectsItem2, oneObjectsItem3, oneObjectsItem4, oneObjectsItem5, oneObjectsItem6, oneObjectsItem7, oneObjectsItem8);
                    if (dbcheck2 == true) {
                        System.out.println("home info DB inserted " + i);
                    } else {
                        System.out.println("home info db failure");
                    }

                    //Toast.makeText(Registration.this, s, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                err = "Exception: " + e.getMessage();
            }

        }

    }

    public void regbtn(View view) {
        ip = ipET.getText().toString();
        if (ip == "" || gateway == "") {
            Toast.makeText(Registration.this, "Please enter all infomation", Toast.LENGTH_SHORT).show();
        } else{
            validateinfo vi = new validateinfo();
            vi.execute(homeid, ip, gateway);
            progressLL.setVisibility(View.VISIBLE);

        }

    }

    public void subbtn(View view) {
        homeid = homeidET.getText().toString();
        un = unET.getText().toString();
        pw = pwET.getText().toString();
        gateway = gatewayET.getText().toString();
        if (homeid == "" || un == "" || pw == "" || gateway == "") {
            Toast.makeText(Registration.this, "Please enter all infomation", Toast.LENGTH_SHORT).show();
        } else if (un.length() < 4) {
            Toast.makeText(Registration.this, "Username should be more than 3 characters", Toast.LENGTH_SHORT).show();
        } else if (pw.length() < 4) {
            Toast.makeText(Registration.this, "Password should be more than 3 characters", Toast.LENGTH_SHORT).show();
        } else if (gateway.matches(".*[a-zA-Z]+.*")) {
            Toast.makeText(Registration.this, "Please enter only numeric values for gateway", Toast.LENGTH_SHORT).show();
        } else {

            submitinfo si = new submitinfo();
            si.execute(homeid, un, pw, gateway);
            progressLL.setVisibility(View.VISIBLE);
        }
        //c.execute(homeid,un,pw,gateway);
    }
    @Override
    public void onBackPressed() {
    }

    public void enter(View view) {
        Intent i = new Intent(Registration.this,Home.class);
        //i.putExtra("ip",ip);
        startActivity(i);
    }

    public void explainSequnces (View view) {

        String title = "Gateway Address";
        String message = "Used for the device to connect to the internet";

        HelperT.showExplanationAlertDialog(message, title, Registration.this);
    }

    public void explainSequncesIP(View view) {

        String title = "IP Address";
        String message = "This is the address of your device";

        HelperT.showExplanationAlertDialog(message, title, Registration.this);
    }
}