package nz.sif.mirrorserver.notifications;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by SimonFi on 14/07/2016.
 */
public class NotificationsRecyclerViewHolder extends RecyclerView.ViewHolder {
    private Object item;

    public NotificationsRecyclerViewHolder(View itemView) {
        super(itemView);
    }

    public void bind(Object item) {
        this.item = item;
    }
}
