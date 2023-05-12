package PSK.FlowerShop.Validators;

import PSK.FlowerShop.request.OrderRequest;

public class Validator {
    public static void ValidateOrderRequest(OrderRequest orderRequest) throws ValidatorException {
        if (orderRequest.getOrderItems() == null || orderRequest.getOrderItems().isEmpty()) {
            throw new ValidatorException("Order has 0 order items");
        }
        if (orderRequest.getStatus().isEmpty()) orderRequest.setStatus("Created");
        if (orderRequest.getPhoneNumber().isEmpty() || orderRequest.getCustomerName().isEmpty() ||
                orderRequest.getPaymentMethod().isEmpty()) throw new ValidatorException("Order with bad props");
    }
}
