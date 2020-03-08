package com.ocean.conf;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import static com.ocean.model.Constant.CLIENT_ID.dataManager;
import static com.ocean.model.Constant.DATA_MANAGER_SECRET;
import static com.ocean.model.Constant.AUTH_TYPE;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private RedisConnectionFactory connectionFactory;

    @Autowired
    private UserApprovalHandler userApprovalHandler;

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;


    @Bean
    public RedisTokenStore redisTokenStore() {
        return  new RedisTokenStore(connectionFactory);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(dataManager.toString())//客户端ID
                .authorizedGrantTypes(AUTH_TYPE.authorization_code.toString(), AUTH_TYPE.implicit.toString(), AUTH_TYPE.password.toString(), AUTH_TYPE.refresh_token.toString())
                //授权模式
                .secret(DATA_MANAGER_SECRET)
                .scopes("all")
                .accessTokenValiditySeconds(60 * 60 * 12).//token有效期为12小时
                refreshTokenValiditySeconds(600);//刷新token有效期为600秒
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.tokenStore(redisTokenStore()).
                userApprovalHandler(userApprovalHandler)
                .authenticationManager(authenticationManager);
                //.pathMapping("/oauth/token", "/dataManager/user/login");
    }


    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer.allowFormAuthenticationForClients().tokenKeyAccess("isAuthenticated()")
                .checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients();
    }
}
