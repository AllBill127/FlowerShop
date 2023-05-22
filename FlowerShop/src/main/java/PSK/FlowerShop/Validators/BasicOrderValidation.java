package PSK.FlowerShop.Validators;

import PSK.FlowerShop.request.OrderRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("default")
public class BasicOrderValidation extends Validator<OrderRequest> {
    @Override
    public void validate(OrderRequest orderRequest) throws ValidatorException {
        emptyValidation(orderRequest);

        if (orderRequest.getCreatedAt() == null
                || orderRequest.getTotalPrice() == null
                || orderRequest.getStatus().isEmpty()
                || orderRequest.getPhoneNumber().isEmpty()
                || orderRequest.getCustomerName().isEmpty()
                || orderRequest.getPaymentMethod().isEmpty()
                || orderRequest.getShippingAddress().isEmpty()
                || orderRequest.getEmail().isEmpty())
            throw new ValidatorException("Order with missing properties");
    }
}
