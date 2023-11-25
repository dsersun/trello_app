package com.sersun.trello_app.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "localhost-configuration")
@Data
@Getter
@Setter
public class DBConnectionSetting {
}
