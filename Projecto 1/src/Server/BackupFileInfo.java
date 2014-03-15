package Server;

public class BackupFileInfo
{
    private String name;
    private String hash;
    private Integer replicationDegree;

    public BackupFileInfo(String _name, String _hash) {
        setName(_name);
        setHash(_hash);
    }

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
}
