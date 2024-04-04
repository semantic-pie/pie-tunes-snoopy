package api.pietunes.snoopy.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import api.pietunes.snoopy.config.PieTunesClientFeignConfiguration;
import api.pietunes.snoopy.models.TrackLoaderResponse;

@FeignClient(name = "fileUploadClient", url = "${snoopy.uploader_url}", configuration = PieTunesClientFeignConfiguration.class)
public interface PieTunesDomainFeignClient {

    @PostMapping(value = "/upload-one", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    TrackLoaderResponse uploadFile(@RequestPart("file") MultipartFile file);
}