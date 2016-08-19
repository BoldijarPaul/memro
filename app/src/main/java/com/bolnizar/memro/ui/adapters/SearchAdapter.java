package com.bolnizar.memro.ui.adapters;

import com.bolnizar.memro.R;
import com.bolnizar.memro.mvp.models.MemeTemplate;
import com.bolnizar.memro.ui.interfaces.SearchAdapterListener;
import com.bumptech.glide.Glide;
import com.orm.SugarRecord;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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

    private List<MemeTemplate> mDefaultMemeTemplateList = new ArrayList<>();
    private List<MemeTemplate> mVisibleMemeTemplateList = new ArrayList<>();

    private String mLastFilterString = "";
    private SearchAdapterListener mSearchAdapterListener;

    public void setSearchAdapterListener(SearchAdapterListener searchAdapterListener) {
        mSearchAdapterListener = searchAdapterListener;
    }

    public void updateToLatestTemplates() {
        mDefaultMemeTemplateList = SugarRecord.listAll(MemeTemplate.class);
        filter(mLastFilterString);
    }

    private MemeTemplate getMemeTemplate(int position) {
        return mVisibleMemeTemplateList.get(position);
    }

    private void notifySearchDataSetChanged() {
        notifyDataSetChanged();
        if (mSearchAdapterListener != null) {
            mSearchAdapterListener.onChange();
        }
    }

    public void filter(String text) {
        mLastFilterString = text;

        if (TextUtils.isEmpty(text)) {
            mVisibleMemeTemplateList = new ArrayList<>(mDefaultMemeTemplateList);

            notifySearchDataSetChanged();
            return;
        }
        mVisibleMemeTemplateList.clear();
        for (MemeTemplate memeTemplate : mDefaultMemeTemplateList) {
            if (memeTemplate.tags != null && memeTemplate.tags.contains(text)) {
                mVisibleMemeTemplateList.add(memeTemplate);
            }
        }
        notifySearchDataSetChanged();
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
        final MemeTemplate memeTemplate = getMemeTemplate(position);
        Glide.with(memeTemplateHolder.image.getContext()).load(memeTemplate.imageUrl).into(memeTemplateHolder.image);
        memeTemplateHolder.name.setText(memeTemplate.name);
        memeTemplateHolder.use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSearchAdapterListener != null) {
                    mSearchAdapterListener.onUseClicked(memeTemplate.id);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mVisibleMemeTemplateList.size();
    }

    public boolean isEmpty() {
        return mVisibleMemeTemplateList.isEmpty();
    }

    static class MemeTemplateHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.meme_template_image)
        ImageView image;
        @BindView(R.id.meme_template_name)
        TextView name;
        @BindView(R.id.meme_template_use)
        View use;

        MemeTemplateHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
