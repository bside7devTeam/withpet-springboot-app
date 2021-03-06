package org.gig.withpet.core.utils.properties;

import lombok.Getter;
import lombok.Setter;
import org.gig.withpet.core.utils.YamlPropertySourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author : JAKE
 * @date : 2022/07/08
 */
@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "aws.s3")
@PropertySource(value="classpath:/application-credentials.yml", factory = YamlPropertySourceFactory.class)
public class S3Properties {
    String endPoint;
    String regionName;
    String bucketName;
    String accessKey;
    String secretKey;
    String prefixUrl;
}
