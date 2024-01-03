package pers.helen.kafkademo.sender.sms;

import lombok.SneakyThrows;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;


@Configuration
@EnableConfigurationProperties(SmsPropertiesBatch.class)
@Import(SmsConfigurationBatch.ImportConfig.class)
public class SmsConfigurationBatch {

    @Resource(name = "c1")
    private Config c1Config;

    @Resource(name = "c2")
    private Config c2Config;

    @Bean(name = "smsClient1")
    public SmsClient smsClient1(){
        P1Provider p1Provider = new P1Provider(c1Config.getAppkey(),
                c1Config.getAppsecret());
        return new DefaultSmsClient(p1Provider);
    }

    @Bean(name = "smsClient2")
    public SmsClient smsClient2(){
        P1Provider p1Provider = new P1Provider(c2Config.getAppkey(),
                c2Config.getAppsecret());
        return new DefaultSmsClient(p1Provider);
    }
    public static class ImportConfig implements ImportBeanDefinitionRegistrar, EnvironmentAware {

        private SmsPropertiesBatch beanProperties;

        @SneakyThrows
        @Override
        public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
            for (Map.Entry<String, SmsPropertiesBatch.Verification> entry : beanProperties.getVerification().entrySet()) {
                // 注册bean
                RootBeanDefinition beanDefinition = new RootBeanDefinition();
                beanDefinition.setBeanClass(Config.class);
                MutablePropertyValues values = new MutablePropertyValues();
                values.addPropertyValue("appkey", entry.getValue().getAppkey());
                values.addPropertyValue("appsecret", entry.getValue().getAppsecret());
                values.addPropertyValue("provider", entry.getValue().getProvider());
                beanDefinition.setPropertyValues(values);
                beanDefinitionRegistry.registerBeanDefinition(entry.getKey(), beanDefinition);
            }
        }

        @Override
        public void setEnvironment(Environment environment) {
            beanProperties = Binder.get(environment).bind(getPropertiesPrefix(SmsPropertiesBatch.class), SmsPropertiesBatch.class).get();
        }

        private String getPropertiesPrefix(Class<?> tClass) {
            return Objects.requireNonNull(AnnotationUtils.getAnnotation(tClass, ConfigurationProperties.class)).prefix();
        }
    }

}
