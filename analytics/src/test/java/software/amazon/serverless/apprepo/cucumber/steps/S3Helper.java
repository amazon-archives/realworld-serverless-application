package software.amazon.serverless.apprepo.cucumber.steps;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.paginators.ListObjectsV2Iterable;

@RequiredArgsConstructor
@Slf4j
public class S3Helper {
  private final S3Client s3Client;

  public void emptyBucket(String bucketName){
    log.info("Emptying bucket {}", bucketName);

    ListObjectsV2Iterable listObjectsV2Responses = s3Client.listObjectsV2Paginator(ListObjectsV2Request.builder()
        .bucket(bucketName)
        .build());

    listObjectsV2Responses.stream()
        .flatMap(r -> r.contents().stream())
        .forEach(s3Object -> {
          log.info("deleting {}", s3Object.key());
          s3Client.deleteObject(DeleteObjectRequest.builder()
              .bucket(bucketName)
              .key(s3Object.key())
              .build());
        });
  }
}
