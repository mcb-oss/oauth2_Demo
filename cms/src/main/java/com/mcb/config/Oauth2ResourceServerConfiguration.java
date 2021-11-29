package com.mcb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

/**
 * @Auther: Mcb
 * @Date: 2021/11/28 18:32
 * @Description:
 */
@Configuration
public class Oauth2ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    //授权服务器验证开发端点
    private static final String CHECK_TOKEN_URL = "http://localhost:8888/oauth/check_token";
    //标识
    private static final String CLIENT_ID = "cms";
    //密钥
    private static final String SECRET_CHAR_SEQUENCE = "secret";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
        remoteTokenServices. setCheckTokenEndpointUrl(CHECK_TOKEN_URL);
        remoteTokenServices.setClientId(CLIENT_ID);
        remoteTokenServices.setClientSecret(SECRET_CHAR_SEQUENCE);
        resources.tokenServices(remoteTokenServices);
    }
}
