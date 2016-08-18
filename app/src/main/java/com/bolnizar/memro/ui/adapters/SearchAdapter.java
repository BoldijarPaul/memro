package com.bolnizar.memro.ui.adapters;

import com.bolnizar.memro.R;
import com.bolnizar.memro.mvp.models.MemeTemplate;
import com.bumptech.glide.Glide;
import com.orm.SugarRecord;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by BoldijarPaul on 18/08/16.
 */
public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_MEME_TEMPLATE = 1;

    private List<MemeTemplate> mMemeTemplateList = new ArrayList<>();

    public void updateToLatestTemplates() {
        mMemeTemplateList = SugarRecord.listAll(MemeTemplate.class);
        notifyDataSetChanged();
    }

    private MemeTemplate getMemeTemplate(int position) {
        return mMemeTemplateList.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_MEME_TEMPLATE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_MEME_TEMPLATE) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meme_template, parent, false);
            return new MemeTemplateHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MemeTemplateHolder memeTemplateHolder = (MemeTemplateHolder) holder;
        MemeTemplate memeTemplate = getMemeTemplate(position);
        Glide.with(memeTemplateHolder.image.getContext()).load(memeTemplate.imageUrl).into(memeTemplateHolder.image);
        memeTemplateHolder.name.setText(memeTemplate.name);
    }

    @Override
    public int getItemCount() {
        return mMemeTemplateList.size();
    }

    static class MemeTemplateHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.meme_template_image)
        ImageView image;
        @BindView(R.id.meme_template_name)
        TextView name;

        MemeTemplateHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}