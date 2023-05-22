package PSK.FlowerShop.usecase;


import PSK.FlowerShop.entities.Order;
import PSK.FlowerShop.request.StatisticsRequest;
import PSK.FlowerShop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@Qualifier("SmallStatisticCalculator")
public class SmallStatisticCalculator implements IStatistics{

    @Autowired
    private OrderService orderService;

    @Override
    public StatisticsRequest getStatistics(StatisticsRequest statisticsRequest) {
        List<Order> orders = orderService.getAllOrders();
        statisticsRequest.orders_count=0;
        statisticsRequest.total_money= new BigDecimal(0);
        for (Order order: orders) {
            statisticsRequest.orders_count +=1;
            statisticsRequest.total_money = statisticsRequest.total_money.add(order.getTotalPrice());
        }
        return statisticsRequest;
    }
}
