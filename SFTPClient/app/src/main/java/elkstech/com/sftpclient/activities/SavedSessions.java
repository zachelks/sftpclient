package elkstech.com.sftpclient.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.arch.persistence.room.Room;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableRow;

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

    private static final int PICKFILE_REQUEST_CODE=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_sessions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "sftp-client").build();
        this.savedConnectionsService = new SavedConnectionsService(db.savedConnectionsDAO());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment connectionForm = ConnectionFormFragment.newInstance("Test", "Test");
                FragmentTransaction transaction=getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_frame, connectionForm); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
                findViewById(R.id.fab).setVisibility(View.GONE);
            }
        });

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        this.fragment = SavedConnectionsFragment.newInstance();
        fragmentTransaction.add(R.id.fragment_frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_saved_sessions, menu);
        return true;
    }

    public void editConnection(View view) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        
        //Bundle bundle = new Bundle();
        //bundle.put
        this.fragment = SavedConnectionsFragment.newInstance();
        fragmentTransaction.add(R.id.fragment_frame, fragment);
        fragmentTransaction.commit();
    }

    public void saveConnection(View view) throws IOException {
        EditText connectionName = findViewById(R.id.connectionName);
        EditText hostname = findViewById(R.id.hostName);
        EditText port = findViewById(R.id.port);
        EditText userName = findViewById(R.id.userName);
        EditText password = findViewById(R.id.password);
        CheckBox keyBasedAuth = findViewById(R.id.keyBasedAuth);
        EditText keyPath = findViewById(R.id.keyPath);

        SavedConnection connection = new SavedConnection(connectionName.getText().toString(), hostname.getText().toString(),
                Integer.parseInt(port.getText().toString()), userName.getText().toString(), password.getText().toString(),
                keyBasedAuth.isChecked(), keyPath.getText().toString());

        savedConnectionsService.addSavedConnection(connection);
        FragmentTransaction transaction = this.getFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.replace(R.id.fragment_frame, this.fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void selectKey(View view) {
        Intent fileintent = new Intent(Intent.ACTION_GET_CONTENT);
        fileintent.setType("*/*");
        try {
            startActivityForResult(fileintent, PICKFILE_REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            Log.e("tag", "No activity can handle picking a file. Showing alternatives.");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == PICKFILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                EditText text = findViewById(R.id.keyPath);
                String fileName = uri.getPath();
                //int cut = fileName.lastIndexOf('/');
                //fileName = fileName.substring(cut + 1);
                text.setText(fileName);
            }
        }

    }

    public void onKeyBasedAuthClick(View view) {
        CheckBox keyBasedAuth = findViewById(R.id.keyBasedAuth);
        TableRow row = findViewById(R.id.keyOptions);
        EditText password = findViewById(R.id.password);
        if(keyBasedAuth.isChecked()) {
            password.setText("");
            password.setFocusable(false);
            row.setVisibility(View.VISIBLE);
        } else {
            password.setFocusable(true);
            row.setVisibility(View.GONE);
        }
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
