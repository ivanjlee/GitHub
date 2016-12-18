package com.ivan.github.debug;

import android.os.Bundle;
import android.widget.TextView;

import com.ivan.github.R;
import com.ivan.github.app.BaseActivity;

public class DebugActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);

        TextView tv1 = (TextView) findViewById(R.id.debug_text);
        TextView tv2 = (TextView) findViewById(R.id.debug_text2);

        String debugStr = getString(R.string.debug_text);

        tv1.setText(debugStr);
        tv2.setText(debugStr);
    }

}
