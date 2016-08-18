package com.bolnizar.memro.data;

import com.bolnizar.memro.mvp.models.MemeTemplate;
import com.bolnizar.memro.mvp.models.Version;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by BoldijarPaul on 18/08/16.
 */
public interface ApiService {

    @GET("/templates.json")
    Observable<List<MemeTemplate>> getTemplates();

    @GET("/version.json")
    Observable<Version> getTemplatesVersion();
}
