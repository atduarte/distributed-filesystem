package Peer;

import java.util.HashMap;
import java.util.Map;

public class BackupFileInfo
{
    private String name;
    private String hash;
    private Integer replicationDegree;
    private Map<Integer, Integer> realReplicationDegree = new HashMap<Integer, Integer>();
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

    public Integer getRealReplicationDegree(Integer chunkNo) {
        Integer result = realReplicationDegree.get(chunkNo);
        return result != null ? result : 0;
    }

    public void incrementRealReplicationDegree(Integer chunkNo) {
        if (realReplicationDegree.containsKey(chunkNo)) {
            Integer newValue = realReplicationDegree.get(chunkNo) + 1;
            realReplicationDegree.put(chunkNo, newValue);
        } else {
            realReplicationDegree.put(chunkNo, 1);
        }
    }

    public void decrementRealReplicationDegree(Integer chunkNo) {
        if (realReplicationDegree.containsKey(chunkNo)) {
            Integer newValue = realReplicationDegree.get(chunkNo) - 1;
            realReplicationDegree.put(chunkNo, newValue);
        }
    }
}
