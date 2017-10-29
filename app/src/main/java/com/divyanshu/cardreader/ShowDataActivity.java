package com.divyanshu.cardreader;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.divyanshu.cardreader.databinding.ActivityShowDataBinding;

public class ShowDataActivity extends AppCompatActivity {

    public static final String KEY_DATA = "key_data";
    private static final String TEXT_PLAIN = "text/plain";

    private ActivityShowDataBinding binding;
    private String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_data);

        data = getIntent().getStringExtra(KEY_DATA);
        binding.tvData.setText(data);
    }

    public void share(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, data);
        sendIntent.setType(TEXT_PLAIN);
        startActivity(sendIntent);
    }
}
