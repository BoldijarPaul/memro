package com.bolnizar.memro.ui.fragments;

import com.bolnizar.memro.R;
import com.bolnizar.memro.mvp.presenters.MemeTemplatesUpdatePresenter;
import com.bolnizar.memro.mvp.views.MemeTemplatesUpdateView;
import com.bolnizar.memro.ui.adapters.SearchAdapter;
import com.bolnizar.memro.ui.interfaces.SearchAdapterChangedListener;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SearchFragment extends Fragment implements SearchView.OnQueryTextListener, MemeTemplatesUpdateView, SearchAdapterChangedListener {

    @BindView(R.id.search_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.search_empty_text)
    TextView mEmptyText;

    private Unbinder mUnbinder;
    private MemeTemplatesUpdatePresenter mMemeTemplatesUpdatePresenter;
    private SearchAdapter mSearchAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mMemeTemplatesUpdatePresenter = new MemeTemplatesUpdatePresenter(getContext(), this);
        mSearchAdapter = new SearchAdapter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
        mMemeTemplatesUpdatePresenter.wakeUp();
        setupList();
    }

    private void setupList() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mSearchAdapter);
        mSearchAdapter.updateToLatestTemplates();
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem item = menu.add(R.string.search_title);
        item.setIcon(R.drawable.ic_search_white_24dp);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);
        SearchView searchView = new SearchView(((AppCompatActivity) getActivity()).getSupportActionBar().getThemedContext());
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, searchView);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        mMemeTemplatesUpdatePresenter.sleep();
        super.onDestroy();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mSearchAdapter.filter(newText);
        return true;
    }

    @Override
    public void gotLatestMemeTemplates() {
        mSearchAdapter.updateToLatestTemplates();
    }

    @Override
    public void onChange() {
        boolean adapterEmpty = mSearchAdapter.isEmpty();
        mEmptyText.setVisibility(adapterEmpty ? View.VISIBLE : View.INVISIBLE);
    }
}
