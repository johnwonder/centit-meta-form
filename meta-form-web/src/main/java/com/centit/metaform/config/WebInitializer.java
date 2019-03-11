package com.centit.metaform.config;

import com.centit.framework.config.SystemSpringMvcConfig;
import com.centit.framework.config.WebConfig;
import com.centit.support.file.PropertiesReader;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.util.Properties;

/**
 * Created by zou_wy on 2017/3/29.
 */


public class WebInitializer implements WebApplicationInitializer {


    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        initializeSpringConfig(servletContext);
        initializeSystemSpringMvcConfig(servletContext);
        initializeNormalSpringMvcConfig(servletContext);
        WebConfig.registerRequestContextListener(servletContext);
        WebConfig.registerSingleSignOutHttpSessionListener(servletContext);
//        WebConfig.registerResponseCorsFilter(servletContext);
        WebConfig.registerCharacterEncodingFilter(servletContext);
        WebConfig.registerHttpPutFormContentFilter(servletContext);
        WebConfig.registerHiddenHttpMethodFilter(servletContext);
        WebConfig.registerRequestThreadLocalFilter(servletContext);
        WebConfig.registerSpringSecurityFilter(servletContext);
        //registerOpenSessionInViewFilter(servletContext);

        Properties properties = PropertiesReader.getClassPathProperties("/system.properties");
        String jdbcUrl = properties.getProperty("jdbc.url");

        if(jdbcUrl.startsWith("jdbc:h2")){
            WebConfig.initializeH2Console(servletContext);
        }
    }

    /**
     * 加载Spring 配置
     * @param servletContext ServletContext
     */
    private void initializeSpringConfig(ServletContext servletContext){
        AnnotationConfigWebApplicationContext springContext = new AnnotationConfigWebApplicationContext();
        springContext.register(ServiceConfig.class);
        servletContext.addListener(new ContextLoaderListener(springContext));
    }

    /**
     * 加载Servlet 配置
     * @param servletContext ServletContext
     */
    private void initializeSystemSpringMvcConfig(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(SystemSpringMvcConfig.class, SwaggerConfig.class);
        ServletRegistration.Dynamic system  = servletContext.addServlet("system", new DispatcherServlet(context));
        system.addMapping("/system/*");
        system.setLoadOnStartup(1);
        system.setAsyncSupported(true);
    }

    /**
     * 加载Servlet 项目配置
     * @param servletContext ServletContext
     */
    private void initializeNormalSpringMvcConfig(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(NormalSpringMvcConfig.class, SwaggerConfig.class);
        ServletRegistration.Dynamic system  = servletContext.addServlet("service", new DispatcherServlet(context));
        system.addMapping("/service/*");
        system.setLoadOnStartup(1);
        system.setAsyncSupported(true);
    }

    /*public void registerOpenSessionInViewFilter(ServletContext servletContext) {
        javax.servlet.FilterRegistration.Dynamic openSessionInViewFilter
                = servletContext.addFilter("openSessionInViewFilter", OpenSessionInViewFilter.class);
        openSessionInViewFilter.addMappingForUrlPatterns(null, false, "/service/*", "/system/*");
        openSessionInViewFilter.setAsyncSupported(true);
    }*/
}
