package org.revo.torrent.torrent.torrentdata;

import org.revo.torrent.bencode.BencodeEncode;
import org.revo.torrent.util.SHA1;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TorrentData {
    private InfoData info;
    private SHA1 infoHash;
    private String announce;
    private List<List<String>> announceList;
    private Date creationDate;
    private String comment;
    private String createdBy;
    private String encoding;

    public TorrentData(Map<String, Object> baseMap) {
        Map<String, Object> infoMap = (Map<String, Object>) baseMap.get("info");
        info = infoMap.containsKey("files") ? new MultiFileInfoData(infoMap) : new SingleFileInfoData(infoMap);

        announceList = (List<List<String>>) baseMap.get("announce-list");
        announce = (String) baseMap.get("announce");
        creationDate = new Date(Long.valueOf(baseMap.get("creation date").toString()));
        comment = (String) baseMap.get("comment");
        createdBy = (String) baseMap.get("created by");
        encoding = (String) baseMap.get("encoding");

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            BencodeEncode.bEncodeObject(infoMap, byteArrayOutputStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        infoHash = SHA1.getHash(byteArrayOutputStream.toByteArray());
    }

    public InfoData getInfo() {
        return info;
    }

    public SHA1 getInfoHash() {
        return infoHash;
    }

    public String getAnnounce() {
        return announce;
    }

    public List<List<String>> getAnnounceList() {
        return announceList;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getComment() {
        return comment;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getEncoding() {
        return encoding;
    }
}
