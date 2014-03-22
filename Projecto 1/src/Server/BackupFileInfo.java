package Server;

import java.util.ArrayList;

public class BackupFileInfo
{
    private String name;
    private String hash;
    private Integer replicationDegree;
    private Integer[][] effectiveReplicationDegree;
    private boolean receiving = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Integer getReplicationDegree() {
        return replicationDegree;
    }

    public void setReplicationDegree(Integer replicationDegree) {
        this.replicationDegree = replicationDegree;
    }

    public boolean isReceiving() {
        return receiving;
    }

    public void setReceiving(boolean receiving) {
        this.receiving = receiving;
    }

    public Integer[][] getEffectiveReplicationDegree() {
        return effectiveReplicationDegree;
    }

    public void setEffectiveReplicationDegree(Integer[][] effectiveReplicationDegree) {
        this.effectiveReplicationDegree = effectiveReplicationDegree;
    }
}
