package elkstech.com.sftpclient.dao.intf;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import elkstech.com.sftpclient.model.SavedConnection;
import elkstech.com.sftpclient.service.SavedConnectionsService;

/**
 * Created by zache on 11/26/2017.
 */

@Dao
public interface SavedConnectionsDAO {

    @Query("SELECT * FROM savedconnections")
    List<SavedConnection> getAll();

    @Insert
    void insert(SavedConnection connection);

    @Update
    void update(SavedConnection connection);

    @Delete
    void delete(SavedConnection connection);

}
