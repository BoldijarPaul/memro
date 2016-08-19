package com.bolnizar.memro.mvp.presenters;

import com.bolnizar.memro.mvp.models.MemeTemplate;
import com.bolnizar.memro.mvp.views.AddTemplateView;
import com.orm.SugarRecord;

/**
 * Created by BoldijarPaul on 19/08/16.
 */
public class AddTemplatePresenter extends RxPresenter<AddTemplateView> {

    public AddTemplatePresenter(AddTemplateView view) {
        super(view);
    }

    public void saveTemplate(String imagePath, String name) {
        MemeTemplate memeTemplate = new MemeTemplate();
        memeTemplate.fromServer = false;
        memeTemplate.name = name;
        memeTemplate.tags = name;
        memeTemplate.imageUrl = imagePath;
        SugarRecord.save(memeTemplate);
        getView().memeSaved();
    }
}
