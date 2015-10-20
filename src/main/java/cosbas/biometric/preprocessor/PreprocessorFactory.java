package cosbas.biometric.preprocessor;

import cosbas.biometric.BiometricTypes;
import cosbas.biometric.validators.AccessValidator;

/**
 * {@author Renette Ros}
 * This factory class provides the template for creating an access validator from the BiometricType.
 * Design patterns: Factory and Template Methods
 */
public abstract class PreprocessorFactory {

    /**
     * The template method to get the validator for a specific type. THis method chooses the class from {@link BiometricTypes} and delegates the actual bean creation to its subclasses.
     * @param bioType The type of the data that needs to be validated.
     * @return The correct {@link AccessValidator}
     */
    public BiometricsPreprocessor  getValidator(BiometricTypes bioType) {
        return getBean(bioType.preprocessorClass);
    }
    protected abstract <T extends BiometricsPreprocessor> BiometricsPreprocessor getBean(Class<T> type);
}
