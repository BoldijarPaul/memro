package com.bolnizar.memro.ui.activities;

import com.bolnizar.memro.R;
import com.bolnizar.memro.mvp.models.MemeTemplate;
import com.bolnizar.memro.mvp.presenters.MemeCaptionPresenter;
import com.bolnizar.memro.mvp.views.MemeCaptionView;
import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding.widget.RxTextView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;

public class CaptionMemeActivity extends AppCompatActivity implements MemeCaptionView {

    private static final String ARG_MEME_ID = "argmemeid";
    private static final int PERMISSION_REQUEST_CODE = 12;

    public static Intent createIntent(long memeId, Context context) {
        Intent intent = new Intent(context, CaptionMemeActivity.class);
        intent.putExtra(ARG_MEME_ID, memeId);
        return intent;
    }

    @BindView(R.id.meme_caption_bottom_edittext)
    EditText mBottomEditText;
    @BindView(R.id.meme_caption_top_edittext)
    EditText mTopEditText;
    @BindView(R.id.meme_caption_image_top_text)
    TextView mImageTopText;
    @BindView(R.id.meme_caption_image_bottom_text)
    TextView mImageBottomText;
    @BindView(R.id.meme_caption_image)
    ImageView mMemeImage;
    @BindView(R.id.image_caption_meme)
    RelativeLayout mMemeLayout;

    private MemeCaptionPresenter mMemeCaptionPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meme_caption);

        long memeId = getIntent().getLongExtra(ARG_MEME_ID, -1);
        if (memeId == -1) {
            throw new IllegalStateException("missing argument for meme memeServerId");
        }
        ButterKnife.bind(this);
        mMemeCaptionPresenter = new MemeCaptionPresenter(this);
        mMemeCaptionPresenter.wakeUp();
        mMemeCaptionPresenter.loadMemeById(memeId);
    }

    @Override
    protected void onDestroy() {
        mMemeCaptionPresenter.sleep();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.caption_meme_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.caption_meme_share_action) {
            shareMeme();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void shareMeme() {
        if (!storagePermissionGranted()) {
            requestStoragePermission();
            return;
        }
        mMemeLayout.setDrawingCacheEnabled(true);
        Bitmap meme = mMemeLayout.getDrawingCache();
        String pathofBmp = MediaStore.Images.Media.insertImage(getContentResolver(), meme, "cache", null);
        Uri bmpUri = Uri.parse(pathofBmp);
        final Intent sendIntent = new Intent(android.content.Intent.ACTION_SEND);
        sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sendIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
        sendIntent.setType("image/png");
        startActivity(sendIntent);
    }

    private void requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_CODE);
        }

    }

    private boolean storagePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
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
                    shareMeme();
                }
            }
        }
    }

    @Override
    public Observable<CharSequence> getTopTextChange() {
        return RxTextView.textChanges(mTopEditText);
    }

    @Override
    public Observable<CharSequence> getBottomTextChange() {
        return RxTextView.textChanges(mBottomEditText);
    }

    @Override
    public void topTextChanged(CharSequence charSequence) {
        mImageTopText.setText(charSequence);
    }

    @Override
    public void bottomTextChanged(CharSequence charSequence) {
        mImageBottomText.setText(charSequence);
    }

    @Override
    public void showMeme(MemeTemplate memeTemplate) {
        Glide.with(this).load(memeTemplate.imageUrl).into(mMemeImage);
    }
}
