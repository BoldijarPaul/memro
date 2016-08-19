package com.bolnizar.memro.ui.interfaces;

/**
 * Created by BoldijarPaul on 18/08/16.
 */
public interface SearchAdapterListener {
    void onChange();

    void onUseClicked(long memeId);

    void onDeleteTemplate(Long id);
}
