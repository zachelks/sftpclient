package elkstech.com.sftpclient.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.arch.persistence.room.Room;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

import elkstech.com.sftpclient.R;
import elkstech.com.sftpclient.dao.AppDatabase;
import elkstech.com.sftpclient.dummy.DummyContent;
import elkstech.com.sftpclient.model.SavedConnection;
import elkstech.com.sftpclient.service.SavedConnectionsService;
import elkstech.com.sftpclient.fragments.ConnectionFormFragment;
import elkstech.com.sftpclient.fragments.SavedConnectionsFragment;

public class SavedSessions extends AppCompatActivity implements SavedConnectionsFragment.OnListFragmentInteractionListener, ConnectionFormFragment.OnFragmentInteractionListener {

    private SavedConnectionsService savedConnectionsService;

    private SavedConnectionsFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_sessions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "sftp-client").build();
        this.savedConnectionsService = new SavedConnectionsService(db.savedConnectionsDAO());
        //this.savedConnectionsService = new SavedConnectionsService();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Fragment connectionForm = ConnectionFormFragment.newInstance("Test", "Test");
                FragmentTransaction transaction=getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_frame, connectionForm); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });

     //   listView = findViewById(R.id.savedSessions);

      //  ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, savedConnectionsService.getSavedConnections());
       // listView.setAdapter(listAdapter);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putSerializable("savedConnectionsService", this.savedConnectionsService);
        this.fragment = SavedConnectionsFragment.newInstance();
        fragment.setArguments(bundle);
        fragmentTransaction.add(R.id.fragment_frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_saved_sessions, menu);
        return true;
    }

    public void saveConnection(View view) throws IOException {
        EditText connectionName = findViewById(R.id.connectionName);
        EditText hostname = findViewById(R.id.hostName);
        EditText port = findViewById(R.id.port);
        EditText userName = findViewById(R.id.userName);
        EditText password = findViewById(R.id.password);
        CheckBox keyBasedAuth = findViewById(R.id.keyBasedAuth);

        SavedConnection connection = new SavedConnection(connectionName.getText().toString(), hostname.getText().toString(), Integer.parseInt(port.getText().toString()), userName.getText().toString(), password.getText().toString(), keyBasedAuth.isChecked(), null);
//        Gson gson = new Gson();
//        String connectionJson = gson.toJson(connection);
//        File file = new File(getExternalFilesDir(null), "connections.json");
//        BufferedWriter writer = new BufferedWriter(new FileWriter("connections.json"));
//        writer.append(connectionJson);
//        writer.close();
        savedConnectionsService.addSavedConnection(connection);
        FragmentTransaction transaction = this.getFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//        SavedConnectionsFragment fragment = SavedConnectionsFragment.newInstance();
        transaction.replace(R.id.fragment_frame, this.fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
