package cosbas.biometric.validators;


import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Renette
 * This factory class returns a Validator bean based on request type.
 */
@Service
public class ValidatorFactory {

    @Autowired
    private BeanFactory beanFactory;

    public Object getValidator(BiometricTypes bioType) {
            return beanFactory.getBean (bioType.validatorClass);
    }
}
