package com.q18idc.sbms.demo.conf;

import com.q18idc.sbms.demo.filter.MyFormAuthenticationFilter;
import com.q18idc.sbms.demo.realm.MyRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Shiro配置
 * @author q18idc.com QQ993143799
 * @date 2018/5/8 11:55
 */
@Configuration
public class ShiroConf {
    @Bean
    public SimpleMappingExceptionResolver simpleMappingExceptionResolver(){
        SimpleMappingExceptionResolver simpleMappingExceptionResolver = new SimpleMappingExceptionResolver();
        Properties properties = new Properties();
        properties.setProperty("org.apache.shiro.authz.UnauthorizedException", "refuse");
        simpleMappingExceptionResolver.setExceptionMappings(properties);
        return simpleMappingExceptionResolver;
    }

    /**
     * shiroFilter
     * @return
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("redisConf")RedisConf redisConf){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager(redisConf));
        //loginUrl认证提交地址，如果没有认证将会请求此地址进行认证，请求此地址将由formAuthenticationFilter进行表单认证
        shiroFilterFactoryBean.setLoginUrl("/login/login.html");
        //设置未授权跳转的url
        shiroFilterFactoryBean.setUnauthorizedUrl("/refuse");
        //设置登录成功后的路径
        shiroFilterFactoryBean.setSuccessUrl("/index.html");

        //自定义过滤器
        Map<String, Filter> filterMap = new LinkedHashMap<>();

        //自定义form认证过虑器
        MyFormAuthenticationFilter formAuthenticationFilter = new MyFormAuthenticationFilter();
        formAuthenticationFilter.setUsernameParam("username");
        formAuthenticationFilter.setPasswordParam("password");
        formAuthenticationFilter.setRememberMeParam("rememberMe");

        filterMap.put("authc",formAuthenticationFilter);

        //设置自定义过滤器
        shiroFilterFactoryBean.setFilters(filterMap);

        //拦截器
        Map<String, String> map = new LinkedHashMap<String, String>();
        //退出拦截
        map.put("/login/logout.html", "logout");
        //放行druid
        map.put("/druid/**", "anon");
        //放行样式文件
        map.put("/script/**", "anon");
        // 放行验证码文件
        map.put("/login/code.jpg", "anon");
        //没有权限页面
        map.put("/refuse", "anon");

        map.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

        return shiroFilterFactoryBean;
    }

    /**
     * redisManager
     * @return
     */
    @Bean("redisManager")
    public RedisManager redisManager(@Qualifier("redisConf")RedisConf redisConf){
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(redisConf.getHost()+":"+redisConf.getPort());
        redisManager.setDatabase(Integer.parseInt(redisConf.getDatabase()));
        return redisManager;
    }

    /**
     * redisSessionDAO
     * @return
     */
    @Bean("redisSessionDAO")
    public RedisSessionDAO redisSessionDAO(@Qualifier("redisConf")RedisConf redisConf){
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager(redisConf));
        redisSessionDAO.setExpire(1800);
        redisSessionDAO.setKeyPrefix("shiro:session:");
        return redisSessionDAO;
    }

    /**
     * sessionManager
     * @return
     */
    @Bean("sessionManager")
    public DefaultWebSessionManager sessionManager(@Qualifier("redisConf")RedisConf redisConf){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO(redisConf));
        return sessionManager;
    }

    /**
     *cacheManager 缓存管理器
     * @return
     */
    @Bean("cacheManager")
    public RedisCacheManager cacheManager(@Qualifier("redisConf")RedisConf redisConf){
        RedisCacheManager cacheManager = new RedisCacheManager();
        cacheManager.setRedisManager(redisManager(redisConf));
        cacheManager.setExpire(1800);
        cacheManager.setKeyPrefix("shiro:cache:");
        return cacheManager;
    }

    /**
     * rememberMeManager
     * @return
     */
    @Bean(name = "rememberMeManager")
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
        rememberMeManager.setCipherKey(Base64.decode("kPH+bIxk5D2deZiIxcaaaA=="));
        rememberMeManager.setCookie(rememberMeCookie());
        return rememberMeManager;
    }

    @Bean(name = "rememberMeCookie")
    public SimpleCookie rememberMeCookie(){
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setMaxAge(604800);
        return simpleCookie;
    }

    @Bean("securityManager")
    public DefaultWebSecurityManager  securityManager(@Qualifier("redisConf")RedisConf redisConf){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //会话管理器
        securityManager.setSessionManager(sessionManager(redisConf));
        //缓存管理器
        securityManager.setCacheManager(cacheManager(redisConf));
        //记住我管理器
        securityManager.setRememberMeManager(rememberMeManager());

        //设置自定义realm
        securityManager.setRealm(myRealm());

        return securityManager;
    }

    /**
     * 凭证匹配器
     * @return
     */
    @Bean(name = "credentialsMatcher")
    public HashedCredentialsMatcher credentialsMatcher(){
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        /**
         * 设置散列算法
         */
        credentialsMatcher.setHashAlgorithmName("md5");
        /**
         * 散列次数
         */
        credentialsMatcher.setHashIterations(1024);

        return credentialsMatcher;
    }

    /**
     * 自定义realm
     * @return
     */
    @Bean("myRealm")
    public MyRealm myRealm(){
        MyRealm myRealm = new MyRealm();
        //设置凭证匹配器
        myRealm.setCredentialsMatcher(credentialsMatcher());
        //开启缓存
        myRealm.setCachingEnabled(true);
        return myRealm;
    }

    /**
     * 下面的代码是添加注解支持
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("redisConf")RedisConf redisConf){
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager(redisConf));
        return advisor;
    }
}
