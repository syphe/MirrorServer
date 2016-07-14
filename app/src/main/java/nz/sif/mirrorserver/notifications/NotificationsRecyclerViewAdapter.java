package nz.sif.mirrorserver.notifications;

import android.app.Activity;
import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by SimonFi on 14/07/2016.
 */
public class NotificationsRecyclerViewAdapter
        extends RecyclerView.Adapter
        implements ObservableArrayList.OnListChangedCallback<String> {
    private ObservableArrayList<Object> items;
    private Activity context;

    public NotificationsRecyclerViewAdapter(Activity context, ObservableArrayList<Object> items) {
        this.context = context;
        this.items = items;
        this.items.addOnListChangedCallback(this);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new NotificationsRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Object item = this.items.get(position);
        ((NotificationsRecyclerViewHolder)holder).bind(item);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public void onChanged(String s) {
        this.notifyDataSetChanged();
    }

    @Override
    public void onItemRangeChanged(String s, int i, int i1) {
        this.notifyDataSetChanged();
    }

    @Override
    public void onItemRangeInserted(String s, int i, int i1) {
        this.notifyDataSetChanged();
    }

    @Override
    public void onItemRangeMoved(String s, int i, int i1, int i2) {
        this.notifyDataSetChanged();
    }

    @Override
    public void onItemRangeRemoved(String s, int i, int i1) {
        this.notifyDataSetChanged();
    }
}
