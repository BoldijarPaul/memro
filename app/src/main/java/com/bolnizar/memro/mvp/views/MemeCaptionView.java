package com.bolnizar.memro.mvp.views;

import com.bolnizar.memro.mvp.models.MemeTemplate;

import rx.Observable;

/**
 * Created by BoldijarPaul on 18/08/16.
 */
public interface MemeCaptionView {

    Observable<CharSequence> getTopTextChange();

    Observable<CharSequence> getBottomTextChange();

    void topTextChanged(CharSequence charSequence);

    void bottomTextChanged(CharSequence charSequence);

    void showMeme(MemeTemplate memeTemplate);
}
