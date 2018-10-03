package com.dungnb.gem.dynamicloadmore;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  private static final int VIEW_TYPE_ITEM = 0;
  private static final int VIEW_TYPE_LOADING = 1;
  ILoadMore loadMore;
  Activity activity;
  List<Item> items;
  boolean isLoading;
  int visibleThreshold = 5;
  int lastVisibleItem, totalItemCount;

  public MyAdapter(RecyclerView recyclerView, Activity activity, List<Item> items) {
    this.activity = activity;
    this.items = items;

    final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        totalItemCount = linearLayoutManager.getItemCount();
        lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
        if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
          if (loadMore != null) {
            loadMore.onLoadMore();
          }
        }
        isLoading = true;
        super.onScrollStateChanged(recyclerView, newState);
      }
    });
  }

  @Override
  public int getItemViewType(int position) {
    return items.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
  }

  public void setLoadMore(ILoadMore loadMore) {
    this.loadMore = loadMore;
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
    if (viewType == VIEW_TYPE_ITEM) {
      View view = LayoutInflater.from(activity).inflate(R.layout.item_layout, viewGroup, false);
      return new ItemViewHolder(view);
    }
    if (viewType == VIEW_TYPE_LOADING) {
      View view = LayoutInflater.from(activity).inflate(R.layout.item_loading, viewGroup, false);
      return new LoadingViewHolder(view);
    }
    return null;
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
    if (viewHolder instanceof ItemViewHolder) {
      Item item = items.get(i);
      ((ItemViewHolder) viewHolder).txtName.setText(item.getName());
      ((ItemViewHolder) viewHolder).txtLength.setText(item.getLenght());
    } else if (viewHolder instanceof LoadingViewHolder) {
      ((LoadingViewHolder) viewHolder).progressBar.setIndeterminate(true);
    }
  }

  @Override
  public int getItemCount() {
    return 0;
  }

  class ItemViewHolder extends RecyclerView.ViewHolder {
    public TextView txtName, txtLength;

    public ItemViewHolder(@NonNull View itemView) {
      super(itemView);
      txtName = itemView.findViewById(R.id.txtName);
      txtLength = itemView.findViewById(R.id.txtLength);
    }
  }

  class LoadingViewHolder extends RecyclerView.ViewHolder {
    ProgressBar progressBar;

    public LoadingViewHolder(@NonNull View itemView) {
      super(itemView);
      progressBar = itemView.findViewById(R.id.progressBar);
    }
  }

  public void setLoaded() {
    isLoading = false;
  }
}
