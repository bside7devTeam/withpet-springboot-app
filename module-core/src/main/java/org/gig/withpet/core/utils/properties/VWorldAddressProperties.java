package org.gig.withpet.core.utils.properties;

import lombok.Getter;
import lombok.Setter;
import org.gig.withpet.core.utils.YamlPropertySourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author : JAKE
 * @date : 2022/06/19
 */
@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "data.v-world-address")
@PropertySource(value="classpath:/application-credentials.yml", factory = YamlPropertySourceFactory.class)
public class VWorldAddressProperties {
    String url;
    String serviceKey;
}
