import weather.domain.DailyInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by lfalcao on 26/04/2017.
 */
public class ComparatorsApp {

    public static void main(String[] args) {
        List<DailyInfo> dailyInfos  = new ArrayList<>();

        dailyInfos.sort((di1, d12) -> di1.getDate().compareTo(d12.getDate()) );
        dailyInfos.sort(Comparators.comparing(DailyInfo::getDate).thenComparing(DailyInfo::getSunset));

        final Comparators.ComparatorBy<DailyInfo> cmp1 =
                Comparators.comparingBy(DailyInfo::getDate)
                 .thenComparingLambda(DailyInfo::getSunset);

        final Comparators.ComparatorBy<DailyInfo> cmp2 =
                 cmp1.thenComparingLambda(DailyInfo::getMaxTemC);

        dailyInfos.sort((di1, d12) -> di1.getSunset().compareTo(d12.getSunset()) );
        //dailyInfos.sort(comparing(DailyInfo::getSunset));


    }
}
