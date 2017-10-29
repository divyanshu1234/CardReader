package com.divyanshu.cardreader;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;

import com.divyanshu.cardreader.databinding.ActivityMainBinding;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    public void openImage(View view) {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    handleResult(resultUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void handleResult(Uri resultUri) throws IOException {
        imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri);
        binding.ivImage.setImageBitmap(imageBitmap);
    }

    public void readText(View view) {
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();

        if (!textRecognizer.isOperational() || imageBitmap == null)
            return;

        Frame frame = new Frame.Builder().setBitmap(imageBitmap).build();
        SparseArray<TextBlock> items = textRecognizer.detect(frame);
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < items.size(); ++i) {
            stringBuilder.append(items.valueAt(i).getValue());
            stringBuilder.append("\n");
        }

        Intent intent = new Intent(MainActivity.this, ShowDataActivity.class);
        intent.putExtra(ShowDataActivity.KEY_DATA, stringBuilder.toString());
        startActivity(intent);
    }
}

