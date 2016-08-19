package com.bolnizar.memro.mvp.presenters;

import com.bolnizar.memro.data.rx.BaseObserver;
import com.bolnizar.memro.mvp.models.MemeTemplate;
import com.bolnizar.memro.mvp.views.MemeCaptionView;
import com.orm.SugarRecord;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by BoldijarPaul on 18/08/16.
 */
public class MemeCaptionPresenter extends RxPresenter<MemeCaptionView> {

    public MemeCaptionPresenter(MemeCaptionView view) {
        super(view);
    }

    @Override
    public void wakeUp() {
        super.wakeUp();

        Subscription topSubscription = getView().getTopTextChange()
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<CharSequence>() {
                    @Override
                    public void onNext(CharSequence charSequence) {
                        getView().topTextChanged(charSequence);
                    }
                });

        Subscription bottomSubscription = getView().getBottomTextChange()
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<CharSequence>() {
                    @Override
                    public void onNext(CharSequence charSequence) {
                        getView().bottomTextChanged(charSequence);
                    }
                });
        manageViewSubscription(topSubscription);
        manageViewSubscription(bottomSubscription);
    }

    public void loadMemeById(Long memeId) {
        List<MemeTemplate> memeTemplates = SugarRecord.find(MemeTemplate.class, "meme_server_id = ?", memeId + "");
        if (memeTemplates == null || memeTemplates.size() == 0) {
            return;
        }
        getView().showMeme(memeTemplates.get(0));
    }
}
