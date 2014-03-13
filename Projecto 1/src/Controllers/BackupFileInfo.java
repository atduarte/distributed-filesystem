package Controllers;

public class BackupFileInfo
{
    private String name;
    private String hash;

    BackupFileInfo(String _name, String _hash) {
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
}
