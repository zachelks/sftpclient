package elkstech.com.sftpclient.service;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import elkstech.com.sftpclient.dao.AppDatabase;
import elkstech.com.sftpclient.dao.intf.SavedConnectionsDAO;
import elkstech.com.sftpclient.model.SavedConnection;

/**
 * Created by Zach on 11/25/2017.
 */

public class SavedConnectionsService implements Serializable {

    private AppDatabase db;

    private SavedConnectionsDAO dao;

    public SavedConnectionsService(SavedConnectionsDAO dao) {
       this.dao = dao;
    }

    public List<SavedConnection> getSavedConnections() {
        //ArrayList<SavedConnection> connections = new ArrayList<SavedConnection>();
        //SavedConnection connection = new SavedConnection("Zach's Server", "elkstech.com", 22, "zache", "zombies", false, null );
        //connections.add(connection);
        //List<SavedConnection> connections = dao.getAll();
        //return new ReadDbAsync(dao).execute();

        try {
            return new ReadDbAsync(dao).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void addSavedConnection(SavedConnection connection) {
        new WriteDbAsync(dao, connection).execute();
    }

    private static class ReadDbAsync extends AsyncTask<Void, Void, List<SavedConnection>> {

        private final SavedConnectionsDAO dao;

        public ReadDbAsync(SavedConnectionsDAO dao) {
            this.dao = dao;
        }

        @Override
        protected List<SavedConnection> doInBackground(final Void... params) {
            return dao.getAll();
        }
    }

    private static class WriteDbAsync extends AsyncTask<Void, Void, Void> {
        private final SavedConnectionsDAO dao;

        private SavedConnection connection;

        public WriteDbAsync(SavedConnectionsDAO dao, SavedConnection connection) {
            this.dao = dao;
            this.connection = connection;
        }

        @Override
        protected Void doInBackground(Void...params) {
            dao.insert(connection);
            return null;
        }
    }


}
