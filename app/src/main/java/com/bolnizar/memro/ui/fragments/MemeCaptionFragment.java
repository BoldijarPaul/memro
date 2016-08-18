package com.bolnizar.memro.ui.fragments;

import com.bolnizar.memro.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MemeCaptionFragment extends Fragment {

    private static final String ARG_MEME_ID = "argmemeid";

    @BindView(R.id.meme_caption_bottom_edittext)
    EditText mBottomEditText;
    @BindView(R.id.meme_caption_top_edittext)
    EditText mTopEditText;

    private Unbinder mUnbinder;

    public static MemeCaptionFragment newInstance(int memeId) {

        Bundle args = new Bundle();
        args.putInt(ARG_MEME_ID, memeId);
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
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }
}
