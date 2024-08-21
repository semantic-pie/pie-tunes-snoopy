package api.pietunes.snoopy.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import api.pietunes.snoopy.config.PieTunesClientFeignConfiguration;
import api.pietunes.snoopy.model.TrackLoaderResponse;

@FeignClient(name = "${snoopy.client.pie-tunes-uploader.name}", url = "${snoopy.client.pie-tunes-uploader.url.base}", configuration = PieTunesClientFeignConfiguration.class)
public interface PieTunesDomainFeignClient {

    @PostMapping(value = "${snoopy.client.pie-tunes-uploader.url.endpoints.upload-one}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    TrackLoaderResponse uploadFile(@RequestPart("file") MultipartFile file);
}