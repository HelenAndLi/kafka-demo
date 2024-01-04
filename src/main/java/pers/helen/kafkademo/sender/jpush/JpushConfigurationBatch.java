package pers.helen.kafkademo.sender.jpush;

import lombok.SneakyThrows;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;
import java.util.Objects;


@Configuration
@EnableConfigurationProperties(JpushPropertiesBatch.class)
@Import(JpushConfigurationBatch.ImportConfig.class)
public class JpushConfigurationBatch {

    public static class ImportConfig implements ImportBeanDefinitionRegistrar, EnvironmentAware {

        private JpushPropertiesBatch beanProperties;

        @SneakyThrows
        @Override
        public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
            for (Map.Entry<String, JpushPropertiesBatch.Conf> entry : beanProperties.getConf().entrySet()) {
                // 注册bean
                RootBeanDefinition beanDefinition = new RootBeanDefinition();
                beanDefinition.setBeanClass(JpushConfig.class);
                MutablePropertyValues values = new MutablePropertyValues();
                values.addPropertyValue("appkey", entry.getValue().getAppkey());
                values.addPropertyValue("appsecret", entry.getValue().getAppsecret());
                values.addPropertyValue("environment", entry.getValue().getEnvironment());
                beanDefinition.setPropertyValues(values);
                beanDefinitionRegistry.registerBeanDefinition(entry.getKey()+"Jpush", beanDefinition);
            }
        }

        @Override
        public void setEnvironment(Environment environment) {
            beanProperties = Binder.get(environment).bind(getPropertiesPrefix(JpushPropertiesBatch.class),
                    JpushPropertiesBatch.class).get();
        }

        private String getPropertiesPrefix(Class<?> tClass) {
            return Objects.requireNonNull(AnnotationUtils.getAnnotation(tClass, ConfigurationProperties.class)).prefix();
        }
    }

}
