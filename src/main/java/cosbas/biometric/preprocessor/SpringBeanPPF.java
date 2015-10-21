package cosbas.biometric.preprocessor;

import cosbas.biometric.validators.AccessValidator;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * {@author Renette Ros}
 * This factory uses a SpringDI BeanFactory to create the validator beans.
 */

@Service
public class SpringBeanPPF extends PreprocessorFactory {
    @Autowired
    private BeanFactory beanFactory;

    @Override
    protected <T extends BiometricsPreprocessor> BiometricsPreprocessor getBean(Class<T> type) {
        return beanFactory.getBean(type);
    }
}
