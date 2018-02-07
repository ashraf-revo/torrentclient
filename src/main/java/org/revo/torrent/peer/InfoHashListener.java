package org.revo.torrent.peer;

import org.revo.torrent.util.SHA1;

public interface InfoHashListener {
    void infoHashReceived(PeerTcpChannel tcpChannel, SHA1 infoHash);
}
