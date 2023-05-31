package PSK.FlowerShop.Validators;

import PSK.FlowerShop.request.OrderRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("production")
public class SpecializedOrderValidator extends Validator<OrderRequest> {
    @Override
    public void validate(OrderRequest orderRequest) throws ValidatorException {
        emptyValidation(orderRequest);

        if (orderRequest.getOrderItems() == null
                || orderRequest.getOrderItems().isEmpty()) {
            throw new ValidatorException("Order has 0 order items");
        }

        if (orderRequest.getStatus().isEmpty())
            orderRequest.setStatus("Created");

        if (orderRequest.getPhoneNumber().isEmpty()
                || orderRequest.getCustomerName().isEmpty()
                || orderRequest.getPaymentMethod().isEmpty())
            throw new ValidatorException("Order with bad props");
    }
}
