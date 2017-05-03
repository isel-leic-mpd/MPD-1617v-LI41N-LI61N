import util.Comparators;
import weather.domain.DailyInfo;

import java.util.ArrayList;
import java.util.List;

import static util.Comparators.comparing;
import static util.Comparators.comparingBy;

/**
 * Created by lfalcao on 26/04/2017.
 */
public class ComparatorsApp {

    public static void main(String[] args) {
        List<DailyInfo> dailyInfos  = new ArrayList<>();

        dailyInfos.sort((di1, d12) -> di1.getDate().compareTo(d12.getDate()) );
        dailyInfos.sort(comparing(DailyInfo::getDate).thenComparing(DailyInfo::getSunset));

        final Comparators.ComparatorBy<DailyInfo> cmp1 =
                comparingBy(DailyInfo::getDate)
                 .thenComparingLambda(DailyInfo::getSunset);

        final Comparators.ComparatorBy<DailyInfo> cmp2 =
                 cmp1.thenComparingLambda(DailyInfo::getMaxTemC);

        dailyInfos.sort((di1, d12) -> di1.getSunset().compareTo(d12.getSunset()) );
        //dailyInfos.sort(comparing(DailyInfo::getSunset));


    }
}
