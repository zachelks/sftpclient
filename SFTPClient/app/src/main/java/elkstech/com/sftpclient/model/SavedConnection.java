package elkstech.com.sftpclient.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by zache on 11/25/2017.
 */

@Entity(tableName = "savedconnections")
public class SavedConnection implements Serializable {

    @PrimaryKey
    @NonNull
    private String connectionName;
    @ColumnInfo(name="hostname")
    private String hostname;
    @ColumnInfo(name="portNumber")
    private int portNumber;
    @ColumnInfo(name="userName")
    private String userName;
    @ColumnInfo(name="password")
    private String password;
    @ColumnInfo(name="keyAuth")
    private boolean keyAuth;
    @ColumnInfo(name="rsaKey")
    private String rsaKey;

    public SavedConnection(String connectionName, String hostname, int portNumber, String userName, String password, boolean keyAuth, String rsaKey) {
        this.connectionName = connectionName;
        this.hostname = hostname;
        this.portNumber = portNumber;
        this.userName = userName;
        this.password = password;
        this.keyAuth = keyAuth;
        this.rsaKey = rsaKey;
    }

    public SavedConnection() {

    }

    public String getConnectionName() {
        return connectionName;
    }

    public String getHostname() {
        return hostname;
    }

    public int getPortNumber() {
        return portNumber;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public boolean isKeyAuth() {
        return keyAuth;
    }

    public String getRsaKey() {
        return rsaKey;
    }

    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setKeyAuth(boolean keyAuth) {
        this.keyAuth = keyAuth;
    }

    public void setRsaKey(String rsaKey) {
        this.rsaKey = rsaKey;
    }

    @Override
    public String toString() {
        return this.connectionName.toString();
    }
}
