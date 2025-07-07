package su.opencode.digsigcik.specification.autowiring;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.Bean;
import su.opencode.digsigcik.specification.service.SpecificationService;
import su.opencode.digsigcik.specification.service.SpecificationServiceImpl;

public class SpecificationConfig {

    @Bean
    public SpecificationService specificationService(DefaultListableBeanFactory factory) {

        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(SpecificationControllerAdvice.class);
        factory.registerBeanDefinition("specificationControllerAdvice", beanDefinition);

        return new SpecificationServiceImpl();
    }
}
