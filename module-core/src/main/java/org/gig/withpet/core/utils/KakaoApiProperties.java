package org.gig.withpet.core.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author : JAKE
 * @date : 2022/05/21
 */
@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "data.kakao")
@PropertySource(value="classpath:/application-credentials.yml", factory = YamlPropertySourceFactory.class)
public class KakaoApiProperties {
    String url;
    String restApiKey;
}