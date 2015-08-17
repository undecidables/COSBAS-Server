package cosbas.biometric.validators;

/**
 * @author Renette
 * This factory class provides the template for creating an access validator from the BiometricType
 * Design patterns: Factory and Template Methiods
 */
public abstract class ValidatorFactory {

    public Object getValidator(BiometricTypes bioType) {
        return getBean(bioType.validatorClass);
    }
    protected abstract AccessValidator getBean(Class c);
}
