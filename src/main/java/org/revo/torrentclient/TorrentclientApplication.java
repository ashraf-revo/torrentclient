package org.revo.torrentclient;

import org.revo.torrent.bencode.BencodeDecode;
import org.revo.torrent.bencode.BencodeException;
import org.revo.torrent.torrent.Torrent;
import org.revo.torrent.torrent.torrentdata.TorrentData;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Map;
import java.util.UUID;

@SpringBootApplication
@RestController
public class TorrentclientApplication {

    public static void main(String[] args) {
        SpringApplication.run(TorrentclientApplication.class, args);
    }

    @PostMapping()
    public void index(@RequestPart("file") MultipartFile multipartFile) throws IOException {
        Path base = Files.createTempDirectory("temp");
        Path temp = Paths.get(base.toString(), UUID.randomUUID().toString().replace("-", "") + ".torrent");
        System.out.println(base);
        Files.copy(multipartFile.getInputStream(), temp);
        try {
            Torrent torrent = new Torrent(base.toString(), getTorrentData(temp.toFile().toString()));
            torrent.startTorrent();

            Flux.interval(Duration.ofSeconds(1)).subscribe(it -> {
                System.out.println(torrent.getDownloaded());
            });


        } catch (IOException | BencodeException ignored) {
            System.out.println(ignored.getMessage());
        }

    }

    static TorrentData getTorrentData(String torrent) throws IOException, BencodeException {
        return new TorrentData((Map<String, Object>) BencodeDecode.bDecodeStream(new FileInputStream(torrent)));
    }

}
