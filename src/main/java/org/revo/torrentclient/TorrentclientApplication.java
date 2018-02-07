package org.revo.torrentclient;

import bt.Bt;
import bt.data.Storage;
import bt.data.file.FileSystemStorage;
import bt.runtime.BtClient;
import bt.torrent.TorrentSessionState;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootApplication
@RestController
public class TorrentclientApplication {

    public static void main(String[] args) {
        SpringApplication.run(TorrentclientApplication.class, args);
    }

    @PostMapping()
    public void index(@RequestBody Torrent torrent) throws IOException {
        Path temp = Files.createTempDirectory("temp");
        System.out.println(temp);
                Storage storage = new FileSystemStorage(temp);
        BtClient client = Bt.client()
                .storage(storage)
                .magnet(torrent.getMurl())
                .autoLoadModules()
                .stopWhenDownloaded()
                .build();
        Flux<TorrentSessionState> from = Flux.from(it -> client.startAsync(it::onNext, 1000));
        from.map(TorrentSessionState::getDownloaded).log().subscribe();
    }
}
