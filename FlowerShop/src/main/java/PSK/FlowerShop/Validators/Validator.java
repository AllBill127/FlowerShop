package PSK.FlowerShop.Validators;

import PSK.FlowerShop.request.OrderRequest;

public abstract class Validator<T> {
    public abstract void validate(T object) throws ValidatorException;

    protected void emptyValidation(T object) throws ValidatorException {
        if (object == null)
            throw new ValidatorException("Object is null");
    }
}
