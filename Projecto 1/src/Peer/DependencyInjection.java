package Peer;

import Peer.BackupInfo;
import Peer.ChunkManager;
import Utils.Channels;

/**
 * Created by atduarte on 23-03-2014.
 */
public class DependencyInjection
{
    ChunkManager chunkManager;
    BackupInfo backupInfo;
    Channels channels;

    public Channels getChannels() {
        return channels;
    }

    public void setChannels(Channels channels) {
        this.channels = channels;
    }

    public ChunkManager getChunkManager() {
        return chunkManager;
    }

    public void setChunkManager(ChunkManager chunkManager) {
        this.chunkManager = chunkManager;
    }

    public BackupInfo getBackupInfo() {
        return backupInfo;
    }

    public void setBackupInfo(BackupInfo backupInfo) {
        this.backupInfo = backupInfo;
    }

}
