package com.bolnizar.memro.data;

import com.bolnizar.memro.mvp.models.MemeTemplateResponse;
import com.bolnizar.memro.mvp.models.Version;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by BoldijarPaul on 18/08/16.
 */
public interface ApiService {

    @GET("templates.json")
    Observable<MemeTemplateResponse> getTemplates();

    @GET("version.json")
    Observable<Version> getTemplatesVersion();
}
