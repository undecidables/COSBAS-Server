package cosbas.biometric.validators;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * {@author Renette Ros}
 * This factory uses a SpringDI BeanFactory to create the validator beans.
 */

@Service
public class SpringBeanVF extends ValidatorFactory {
    @Autowired
    private BeanFactory beanFactory;

    protected AccessValidator getBean(Class bioType) {
        return (AccessValidator) beanFactory.getBean (bioType);
    }

}
