package elkstech.com.sftpclient.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.List;

import elkstech.com.sftpclient.R;
import elkstech.com.sftpclient.model.SavedConnection;

/**
 * Created by zache on 11/25/2017.
 */

public class SavedConnectionAdapter extends ArrayAdapter<SavedConnection> {

    private Context context;
    private List<SavedConnection> savedConnections;

    public SavedConnectionAdapter(Context context, List<SavedConnection> savedConnections) {
        super(context, 0, savedConnections);
        this.context = context;
        this.savedConnections = savedConnections;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if(listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.saved_connection_item, parent, false);
        }

        SavedConnection connection = savedConnections.get(position);

        TextView textView = listItem.findViewById(R.id.connection_name);
        textView.setText(connection.getConnectionName());

        return listItem;
    }
}
