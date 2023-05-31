package PSK.FlowerShop.controller;

import PSK.FlowerShop.request.StatisticsRequest;
import PSK.FlowerShop.usecase.IStatistics;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {
    private final IStatistics statisticsCalculator;

    public StatisticsController(@Qualifier("SuperStatisticCalculator") IStatistics statisticsCalculator) {
        this.statisticsCalculator = statisticsCalculator;
    }
    @GetMapping()
    public StatisticsRequest getStatistics(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate
    ) {
        if (endDate == null) {
            endDate = LocalDate.now();
        }
        if (startDate == null) {
            startDate = LocalDate.now().minusYears(1);
        }
        return statisticsCalculator.getStatistics(new StatisticsRequest(java.sql.Date.valueOf(startDate), java.sql.Date.valueOf(endDate)));
    }

}
