package com.bolnizar.memro.mvp.presenters;

import com.bolnizar.memro.data.ApiService;
import com.bolnizar.memro.data.dagger.qualifiers.MemeTemplatesVersion;
import com.bolnizar.memro.data.prefs.IntegerPreference;
import com.bolnizar.memro.data.rx.BaseObserver;
import com.bolnizar.memro.mvp.models.MemeTemplate;
import com.bolnizar.memro.mvp.models.MemeTemplateResponse;
import com.bolnizar.memro.mvp.models.Version;
import com.bolnizar.memro.mvp.views.MemeTemplatesUpdateView;
import com.bolnizar.memro.ui.BaseApp;
import com.orm.SugarRecord;

import android.content.Context;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by BoldijarPaul on 18/08/16.
 */
public class MemeTemplatesUpdatePresenter extends RxPresenter<MemeTemplatesUpdateView> {

    @Inject
    ApiService mApiService;

    @MemeTemplatesVersion
    @Inject
    IntegerPreference mMemeTemplatesVersion;

    public MemeTemplatesUpdatePresenter(Context context, MemeTemplatesUpdateView memeTemplatesUpdateView) {
        super(memeTemplatesUpdateView);
        BaseApp.get(context).graph().inject(this);
    }

    @Override
    public void wakeUp() {
        super.wakeUp();

        Subscription subscription =
                mApiService.getTemplatesVersion()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseObserver<Version>() {
                            @Override
                            public void onNext(Version version) {
                                if (version == null) {
                                    onError(new Exception("Version is null"));
                                    return;
                                }
                                if (mMemeTemplatesVersion.get() < version.number) {
                                    Timber.d("Trying to get more recent memes");
                                    getLatestTemplates();
                                } else {
                                    Timber.d("Already having the most recent memes");
                                }
                            }
                        });
        manageViewSubscription(subscription);
    }

    private void getLatestTemplates() {
        Subscription subscription =
                mApiService.getTemplates().
                        subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseObserver<MemeTemplateResponse>() {
                            @Override
                            public void onNext(MemeTemplateResponse memeTemplateResponse) {
                                if (memeTemplateResponse == null || memeTemplateResponse.memeTemplates == null) {
                                    onError(new Exception("Templates are null"));
                                    return;
                                }
                                List<MemeTemplate> memeTemplates = memeTemplateResponse.memeTemplates;
                                for (int i = 0, memeTemplatesSize = memeTemplates.size(); i < memeTemplatesSize; i++) {
                                    MemeTemplate memeTemplate = memeTemplates.get(i);
                                    SugarRecord.save(memeTemplate);
                                }
                                mMemeTemplatesVersion.set(memeTemplateResponse.version);
                                getView().gotLatestMemeTemplates();
                                Timber.d("Saved latest templates");
                            }
                        });
        manageViewSubscription(subscription);
    }
}
