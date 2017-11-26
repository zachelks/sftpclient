package elkstech.com.sftpclient.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import elkstech.com.sftpclient.dao.intf.SavedConnectionsDAO;
import elkstech.com.sftpclient.model.SavedConnection;

/**
 * Created by zache on 11/25/2017.
 */

@Database(entities = {SavedConnection.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SavedConnectionsDAO savedConnectionsDAO();
}
