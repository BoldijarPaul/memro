package com.bolnizar.memro.mvp.models;

import com.orm.dsl.Table;

/**
 * Created by BoldijarPaul on 18/08/16.
 */
@Table
public class MemeTemplate {
    public Long id;
    public String name;
    public String imageUrl;
    public String tags;
}
