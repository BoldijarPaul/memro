package com.bolnizar.memro.ui.fragments;

import com.bolnizar.memro.R;
import com.bolnizar.memro.mvp.models.MemeTemplate;
import com.bolnizar.memro.mvp.presenters.MemeCaptionPresenter;
import com.bolnizar.memro.mvp.views.MemeCaptionView;
import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding.widget.RxTextView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.grantland.widget.AutofitHelper;
import rx.Observable;

public class MemeCaptionFragment extends Fragment implements MemeCaptionView {

    private static final String ARG_MEME_ID = "argmemeid";

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

    private Unbinder mUnbinder;
    private MemeCaptionPresenter mMemeCaptionPresenter;

    public static MemeCaptionFragment newInstance(Long memeId) {

        Bundle args = new Bundle();
        args.putLong(ARG_MEME_ID, memeId);
        MemeCaptionFragment fragment = new MemeCaptionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meme_caption, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!getArguments().containsKey(ARG_MEME_ID)) {
            throw new IllegalStateException("missing argument for meme id");
        }
        mUnbinder = ButterKnife.bind(this, view);
        mMemeCaptionPresenter = new MemeCaptionPresenter(this);
        mMemeCaptionPresenter.wakeUp();
        mMemeCaptionPresenter.loadMemeById(getArguments().getLong(ARG_MEME_ID, 0));
        AutofitHelper.create(mImageTopText);
        AutofitHelper.create(mImageBottomText);
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        mMemeCaptionPresenter.sleep();
        super.onDestroyView();
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
        Glide.with(getContext()).load(memeTemplate.imageUrl).into(mMemeImage);
    }
}
