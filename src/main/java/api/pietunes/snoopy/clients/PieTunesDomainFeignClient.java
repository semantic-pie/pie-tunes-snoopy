package api.pietunes.snoopy.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import api.pietunes.snoopy.config.PieTunesClientFeignConfiguration;

@FeignClient(name = "fileUploadClient", url = "http://192.168.192.70:8080/api/track-loader", configuration = PieTunesClientFeignConfiguration.class)
public interface PieTunesDomainFeignClient {

    @PostMapping(value = "/upload-one", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String uploadFile(@RequestPart("file") MultipartFile file);
}