package su.opencode.digsigcik.specification.autowiring;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

@Deprecated
public class SpecificationBeanRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
//        AnnotationAttributes attributes = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(EnableSpecificationHandling.class.getName()));
//        String value = (attributes != null) ? attributes.getString("value") : "default-value";

        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(SpecificationControllerAdvice.class);
//        beanDefinition.getPropertyValues().addPropertyValue("configValue", value);

        registry.registerBeanDefinition("specificationControllerAdvice", beanDefinition);
    }
}
