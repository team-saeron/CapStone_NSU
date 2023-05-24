package togethers.togethers.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import togethers.togethers.Enum.Code;
import togethers.togethers.entity.AwsFileUrl;
import togethers.togethers.entity.Post;
import togethers.togethers.repository.AwsFileUrlRepository;
import togethers.togethers.repository.PostRepository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AwsS3Service  {

    private final AmazonS3 s3Client;

    private final PostRepository postRepository;

    private final AwsFileUrlRepository awsFileUrlRepository;
    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    @PostConstruct
    public AmazonS3Client amazonS3Client() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);
        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
    }

    public void upload(Long postId, List<MultipartFile> multipartFile) {

        Post post = postRepository.findById(postId).orElse(null);


        for (MultipartFile file : multipartFile) {
            String fileName = createFileName(file.getOriginalFilename());
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            try(InputStream inputStream = file.getInputStream()) {
                s3Client.putObject(new PutObjectRequest(bucket+"/post/image", fileName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
                AwsFileUrl awsFileUrl = new AwsFileUrl(post, s3Client.getUrl(bucket + "/post/image",fileName).toString());
                awsFileUrlRepository.save(awsFileUrl);

                if(post.getFileName()==null)
                {
                    post.setFileName(s3Client.getUrl(bucket + "/post/image",fileName).toString());
                    postRepository.flush();

                }
            } catch(IOException e) {
                throw new PrivateException(Code.IMAGE_UPLOAD_ERROR);
            }
        }

    }

    // 이미지파일명 중복 방지
    private String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }
    public void deleteFile(String fileName) {
        s3Client.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }
    private String getFileExtension(String fileName) {

        ArrayList<String> fileValidate = new ArrayList<>();
        fileValidate.add(".jpg");
        fileValidate.add(".jpeg");
        fileValidate.add(".png");
        fileValidate.add(".JPG");
        fileValidate.add(".JPEG");
        fileValidate.add(".PNG");
        String idxFileName = fileName.substring(fileName.lastIndexOf("."));

        return fileName.substring(fileName.lastIndexOf("."));
    }


    String getFileUrl(String fileName){
        return s3Client.getUrl(bucket, fileName).toString();
    }
    @Getter
    public class PrivateException extends RuntimeException {
        private Code code;

        public PrivateException(Code code) {
            super(code.getMsg());
            this.code = code;
        }
    }
}
