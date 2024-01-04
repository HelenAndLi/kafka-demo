package pers.helen.kafkademo.sender.voicemessage;

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
@EnableConfigurationProperties(VmPropertiesBatch.class)
@Import(VmConfigurationBatch.ImportConfig.class)
public class VmConfigurationBatch {

    public static class ImportConfig implements ImportBeanDefinitionRegistrar, EnvironmentAware {

        private VmPropertiesBatch beanProperties;

        @SneakyThrows
        @Override
        public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
            for (Map.Entry<String, VmPropertiesBatch.Access> entry : beanProperties.getAccess().entrySet()) {
                // 注册bean
                RootBeanDefinition beanDefinition = new RootBeanDefinition();
                beanDefinition.setBeanClass(VmConfig.class);
                MutablePropertyValues values = new MutablePropertyValues();
                values.addPropertyValue("aliyun_key", entry.getValue().getAliyun_key());
                values.addPropertyValue("aliyun_secret", entry.getValue().getAliyun_secret());
                values.addPropertyValue("aliyun_url", entry.getValue().getAliyun_url());
                values.addPropertyValue("huyi_key", entry.getValue().getHuyi_key());
                values.addPropertyValue("huyi_secret", entry.getValue().getHuyi_secret());
                values.addPropertyValue("huyi_url", entry.getValue().getHuyi_url());
                beanDefinition.setPropertyValues(values);
                beanDefinitionRegistry.registerBeanDefinition(entry.getKey()+"Vm", beanDefinition);
            }
        }

        @Override
        public void setEnvironment(Environment environment) {
            beanProperties = Binder.get(environment).bind(getPropertiesPrefix(VmPropertiesBatch.class),
                    VmPropertiesBatch.class).get();
        }

        private String getPropertiesPrefix(Class<?> tClass) {
            return Objects.requireNonNull(AnnotationUtils.getAnnotation(tClass, ConfigurationProperties.class)).prefix();
        }
    }

}
