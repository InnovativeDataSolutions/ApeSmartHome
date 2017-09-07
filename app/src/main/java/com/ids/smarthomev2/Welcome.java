package com.ids.smarthomev2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Welcome extends AppCompatActivity {
    Button enter;
    Context ctx = this;
    Database db = new Database(ctx);
    long count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        enter = (Button) findViewById(R.id.enter);
        count = db.check2(); //check if home already registered

        if (count > 0) {
            Intent go = new Intent(ctx, Home.class); //if home already exist proceed to controller page
            startActivity(go);
        }
    }

    public void enter(View view){
        Intent go = new Intent(Welcome.this,Registration.class);
        startActivity(go);
    }

}
