package api.pietunes.snoopy.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import api.pietunes.snoopy.clients.PieTunesDomainFeignClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@AllArgsConstructor
public class SpotDLDownloadService {

    private final PieTunesDomainFeignClient domainFeignClient;

    public Mono<String> download(String query) {
        // String[] command = { "spotdl", "/path/to/your/script.sh" };
        if (query != null && !query.isEmpty()) {
            try {
                String home = "/Users/glebchanskiy/workflow/pie-tunes/pie-tunes-snoopy-scripts/";
                var dir = UUID.randomUUID().toString();

                String[] command = { "spotdl", "download", "'" + query + "'", "--format=mp3", "--output=" + home + dir };

                log.info("command: {}", Arrays.toString(command));
                // Start the process builder
                ProcessBuilder processBuilder = new ProcessBuilder(command);

                // Set any environment variables if needed
                // processBuilder.environment().put("VAR_NAME", "VAR_VALUE");

                // Redirect error stream to output stream
                processBuilder.redirectErrorStream(true);

                // Start the process
                Process process = processBuilder.start();

                // Read output from the process
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }

                // Wait for the process to finish
                int exitCode = process.waitFor();
                if (exitCode == 0) {

                    uploadFilesFromDirectory(home + dir);
                    log.info("SUCCESSFULLY");
                } else {
                    log.info("FAILED");
                }
                return Mono.just("OK");
            } catch (Exception ex) {
                return Mono.just("ERR");
            }

        }

        return Mono.empty();
    }

    public void uploadFilesFromDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.isDirectory()) {
            log.info("Specified path is not a directory.");
            return;
        }

        File[] files = directory.listFiles();
        if (files == null || files.length == 0) {
            log.info("No files found in the directory.");
            return;
        }

        for (File file : files) {
            try {
                MultipartFile multipartFile = new MultipartFile() {

                    @Override
                    public String getName() {
                        return file.getName();
                    }

                    @Override
                    public String getOriginalFilename() {
                        return file.getName();
                    }

                    @Override
                    public String getContentType() {
                        try {
                            return Files.probeContentType(file.toPath());
                        } catch (IOException e) {
                            e.printStackTrace();
                            return "application/octet-stream";
                        }
                    }

                    @Override
                    public boolean isEmpty() {
                        return file.length() == 0;
                    }

                    @Override
                    public long getSize() {
                        return file.length();
                    }

                    @Override
                    public byte[] getBytes() throws IOException {
                        return Files.readAllBytes(file.toPath());
                    }

                    @Override
                    public InputStream getInputStream() throws IOException {
                        return Files.newInputStream(file.toPath());
                    }

                    @Override
                    public void transferTo(File dest) throws IOException, IllegalStateException {
                        Files.copy(file.toPath(), dest.toPath());
                    }
                };

                // File file = new File("src/test/resources/validation.txt");
                // DiskFileItem fileItem = new DiskFileItem("file", "text/plain", false, file.getName(), (int) file.length() , file.getParentFile());
                // fileItem.getOutputStream();
                // MultipartFile multipa/rtFile = new MultipartFile()

                domainFeignClient.uploadFile(multipartFile);
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
