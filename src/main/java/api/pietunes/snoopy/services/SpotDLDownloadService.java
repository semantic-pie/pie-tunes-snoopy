package api.pietunes.snoopy.services;

import java.io.File;
import java.util.Arrays;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import api.pietunes.snoopy.clients.PieTunesDomainFeignClient;
import api.pietunes.snoopy.models.TrackLoaderResponse;
import api.pietunes.snoopy.utils.MultipartFileInMem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpotDLDownloadService {

    @Value("${snoopy.buffer-directory}")
    private String bufferDirectory;

    private final PieTunesDomainFeignClient domainFeignClient;

    public Mono<TrackLoaderResponse> download(String query) {
        if (query != null && !query.isEmpty()) {
            try {
                // every track has his own directory, (костыль, to avoid determine the name of
                // the final file)
                var uniquePath = bufferDirectory + '/' + UUID.randomUUID().toString().replace('-', '_');

                // process query via spotdl download command
                var ok = createSpotDLProcess(query, uniquePath);

                if (ok) {
                    log.info("query: [{}] [downloading] executed successfully", query);
                    // upload via pie-tunes-domain uploader
                    return uploadFilesFromDirectory(query, uniquePath).map(d -> {
                        log.info("query: [{}] [uploading] executed successfully", query);
                        clearDirectory(uniquePath);
                        return d;
                    });
                    
                } else {
                    clearDirectory(uniquePath);
                    log.info("query: [{}] [downloading] execution failed", query);
                    return Mono.error(new RuntimeException("Download failed"));
                }
            } catch (Exception ex) {
                return Mono.error(ex);
            }
        } else {
            return Mono.error(new RuntimeException());
        }
    }

    public Mono<TrackLoaderResponse> uploadFilesFromDirectory(String query, String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.isDirectory()) {
            log.error("specified path is not a directory [{}]", directoryPath);
            return Mono.error(new RuntimeException());
        }

        File[] files = directory.listFiles();
        if (files == null || files.length == 0) {
            log.error("no files found in the directory");
            return Mono.error(new RuntimeException());
        }

        for (File file : files) {
            try {
                return Mono.just(domainFeignClient.uploadFile(new MultipartFileInMem(file)));
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                log.info("query: [{}] [uploading] execution failed", query);
            }
        }
        return Mono.empty();
    }

    private boolean createSpotDLProcess(String query, String bufferDirectory) {
        try {
            String[] downloadCommand = { "spotdl", "download", "'" + query + "'", "--format=mp3",
                    "--output=" + bufferDirectory };

            log.info("query: [{}] generated command: [{}]", query, Arrays.toString(downloadCommand));

            ProcessBuilder processBuilder = new ProcessBuilder(downloadCommand);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            // Read output from the process
            // BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            // String line;
            // while ((line = reader.readLine()) != null) {
            //     System.out.println(line);
            // }

            var status = process.waitFor();
            log.info("query: [{}] status: {}", query, status);

            return true; // successfully executed
        } catch (Exception ex) {
            log.info("query: [{}] error: {}", ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    private boolean clearDirectory(String path) {
        try {
            String[] clearCommand = { "rm", "-rf", path };

            ProcessBuilder processBuilder = new ProcessBuilder(clearCommand);
            Process process = processBuilder.start();

            return process.waitFor() == 0; // successfully executed
        } catch (Exception ex) {
            return false;
        }
    }

}
