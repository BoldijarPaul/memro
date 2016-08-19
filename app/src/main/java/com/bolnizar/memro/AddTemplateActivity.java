package com.bolnizar.memro;

import com.bolnizar.memro.mvp.presenters.AddTemplatePresenter;
import com.bolnizar.memro.mvp.views.AddTemplateView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddTemplateActivity extends AppCompatActivity implements AddTemplateView {

    private static final int PICK_REQUEST_CODE = 1;
    private static final int PERMISSION_REQUEST_CODE = 2;
    @BindView(R.id.add_template_image)
    ImageView mImage;
    @BindView(R.id.add_template_edittext)
    EditText mNameEditText;

    private String mImagePath;
    private AddTemplatePresenter mAddTemplatePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_template);
        ButterKnife.bind(this);
        mAddTemplatePresenter = new AddTemplatePresenter(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_template_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_template_action_done) {
            if (mImagePath == null || TextUtils.isEmpty(mNameEditText.getText())) {
                Toast.makeText(this, R.string.choose_image_and_name, Toast.LENGTH_SHORT).show();
            } else {
                saveTemplate();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveTemplate() {
        mAddTemplatePresenter.saveTemplate(mImagePath, mNameEditText.getText().toString());
    }

    @OnClick(R.id.add_template_choose_image)
    void chooseImage() {
        if (!storagePermissionGranted()) {
            requestStoragePermission();
            return;
        }
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_REQUEST_CODE);
    }

    private void requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_CODE);
        }

    }

    private boolean storagePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    chooseImage();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_REQUEST_CODE && resultCode == RESULT_OK
                && null != data) {
            // Get the Image from data

            try {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mImagePath = cursor.getString(columnIndex);
                cursor.close();
                // Set the Image in ImageView after decoding the String
                mImage.setImageBitmap(BitmapFactory
                        .decodeFile(mImagePath));
            } catch (Exception e) {
                loadImageFailed();
            }

        }
    }

    private void loadImageFailed() {
        Toast.makeText(this, R.string.image_load_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void memeSaved() {
        Toast.makeText(this, R.string.template_saved, Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }
}
