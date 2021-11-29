package com.mcb.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * @Auther: Mcb
 * @Date: 2021/11/28 12:52
 * @Description:
 */
@Configuration
//开启授权服务
@EnableAuthorizationServer
public class Oauth2Config extends AuthorizationServerConfigurerAdapter {

    //授权管理器
    @Autowired
    private AuthenticationManager authenticationManager;

    private static final String CLIENT_ID = "cms";
    //{noop}表示不加密
    private static final String SECRET_CHAR_SEQUENCE = "{noop}secret";
    private static final String SCOPE_READ = "read";
    private static final String SCOPE_WRITE = "write";
    private static final String TRUST = "trust";
    private static final String USER ="user";
    private static final String ALL = "all";
    private static final int ACCESS_TOKEN_VALIDITY_SECONDS = 30*60;
    private static final int FREFRESH_TOKEN_VALIDITY_SECONDS = 30*60;
    // 密码模式授权模式
    private static final String GRANT_TYPE_PASSWORD = "password";
    //授权码模式
    private static final String AUTHORIZATION_CODE = "authorization_code";
    //refresh token模式
    private static final String REFRESH_TOKEN = "refresh_token";
    //简化授权模式
    private static final String IMPLICIT = "implicit";
    //客户端模式
    private static final String CLIENT_CREDENTIALS="client_credentials";
    //指定哪些资源是需要授权验证的
    private static final String RESOURCE_ID = "resource_id";

    /**
     * oauth2端点的权限配置
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                //配置/oath/token_key可以匿名访问 | ("isAuthenticated()")需认证访问
                .tokenKeyAccess("permitAll()")
                //开启令牌验证端点/oauth/check_token端点，匿名访问
                .checkTokenAccess("permitAll()")
                //允许表单认证
                .allowFormAuthenticationForClients();

    }

    /**
     * 配置客户端信息（获取授权方）
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .inMemory()
                //标记客户端ID
                .withClient(CLIENT_ID)
                //客户端密钥
                .secret(SECRET_CHAR_SEQUENCE)
                //设置为true，直接自动授权成功返回授权码
                .autoApprove(true)
                .redirectUris("http://127.0.0.1:8084/cms/login")
                //允许授权范围：read / write
                .scopes(ALL)
                //访问令牌时效
                .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS)
                //刷新令牌时效
                .refreshTokenValiditySeconds(FREFRESH_TOKEN_VALIDITY_SECONDS)
                //允许授权的类型，字符段固定 (添加REFRESH_TOKEN发放令牌时会返回刷新令牌)
                .authorizedGrantTypes(AUTHORIZATION_CODE);
    }

    /**
     * 令牌存储方式
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager).tokenStore(memoryTokenStore());
    }

    @Bean
    public TokenStore memoryTokenStore(){
        return new InMemoryTokenStore();
    }
}
