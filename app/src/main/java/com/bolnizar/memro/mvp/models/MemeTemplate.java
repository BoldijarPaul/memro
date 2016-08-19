package com.bolnizar.memro.mvp.models;

import com.google.gson.annotations.SerializedName;

import com.orm.dsl.Table;

/**
 * Created by BoldijarPaul on 18/08/16.
 */
@Table
public class MemeTemplate {
    public Long id;
    @SerializedName("id")
    public long memeServerId;
    public String name;
    public String imageUrl;
    public String tags;
    public boolean fromServer = true;

}
