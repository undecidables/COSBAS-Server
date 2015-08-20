package cosbas.biometric.validators;

import cosbas.biometric.validators.AccessValidator;
import cosbas.biometric.validators.BiometricTypes;

/**
 * @author Renette
 * This factory class provides the template for creating an access validator from the BiometricType
 * Design patterns: Factory and Template Methods
 */
public abstract class ValidatorFactory {

    public AccessValidator getValidator(BiometricTypes bioType) {
        return getBean(bioType.validatorClass);
    }
    protected abstract AccessValidator getBean(Class c);
}
