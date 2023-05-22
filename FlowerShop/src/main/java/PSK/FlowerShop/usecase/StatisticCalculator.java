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
@Qualifier("SuperStatisticCalculator")
public class StatisticCalculator implements IStatistics{

    @Autowired
    private OrderService orderService;

    @Override
    public StatisticsRequest getStatistics(StatisticsRequest statisticsRequest) {
        List<Order> orders = orderService.getOrdersByDateRange(statisticsRequest.startDate,statisticsRequest.endDate);
        statisticsRequest.orders_count=0;
        statisticsRequest.orders_delivered=0;
        statisticsRequest.orders_waiting=0;
        statisticsRequest.total_money= new BigDecimal(0);
        for (Order order: orders) {
            if(order.getStatus().equalsIgnoreCase("created")) statisticsRequest.orders_waiting+=1;
            if(order.getStatus().equalsIgnoreCase("delivered")) statisticsRequest.orders_delivered+=1;
            statisticsRequest.orders_count +=1;
            statisticsRequest.total_money = statisticsRequest.total_money.add(order.getTotalPrice());
        }
        return statisticsRequest;
    }
}
