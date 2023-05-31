package PSK.FlowerShop.request;

import java.math.BigDecimal;
import java.sql.Date;

public class StatisticsRequest {
    public Date startDate;
    public Date endDate;
    public int orders_delivered;
    public int orders_count;
    public int orders_waiting;
    public BigDecimal total_money;

    public StatisticsRequest(Date startDate, Date endDate) {
        this.endDate= endDate;
        this.startDate= startDate;
    }
}
