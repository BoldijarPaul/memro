package com.bolnizar.memro.mvp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by BoldijarPaul on 18/08/16.
 */
public class MemeTemplateResponse {
    public int version;
    @SerializedName("templates")
    public List<MemeTemplate> memeTemplates;
}
