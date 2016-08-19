package com.bolnizar.memro.ui.activities;

import com.bolnizar.memro.R;
import com.bolnizar.memro.mvp.models.MemeTemplate;
import com.bolnizar.memro.mvp.presenters.MemeCaptionPresenter;
import com.bolnizar.memro.mvp.views.MemeCaptionView;
import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding.widget.RxTextView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;

public class CaptionMemeActivity extends AppCompatActivity implements MemeCaptionView {

    private static final String ARG_MEME_ID = "argmemeid";

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
