package code;

import org.jfree.*;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.TimeSeries;

public class Main {
    public static void main(String[] args) {
        var db = new DataBase();
        db.open();

        //Парсим csv и заносим его в бд
        //Parser.Parse(db);

        averageTotalFundingsByYears(db);
        averageTotalFundingsBy2012(db);
        System.out.println();
        maxTotalFunding(db);
        db.close();
    }

    private static void averageTotalFundingsByYears(DataBase db)
    {
        var resultSet = db.select("select endBuiltYear, avg(totalFunding) from Buildings where endBuiltYear" +
                " is not \"\" and totalFunding is not 0 group by endBuiltYear;");
        LineChart_AWT.main(resultSet);
    }

    private static void averageTotalFundingsBy2012(DataBase db)
    {
        var resultSet = db.select("select avg(totalFunding) from Buildings where beginBuiltYear is \"2012\";");
        try {
            resultSet.next();
            System.out.println(String.format("Average funding by 2012 is %s",
                    resultSet.getLong(1)));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void maxTotalFunding(DataBase db)
    {
        var resultSet = db.select("select name, max(totalFunding)" +
                " from Buildings where buildingType is \"стадион\" or buildingType is \"многофункицональный спортивный комплекс\";)");
        try {
            resultSet.next();
            var name = resultSet.getString(1);
            var totalFunding = resultSet.getLong(2);
            System.out.println(String.format("\"%s\" is the most funded stadium or polyfunctional sport complex", name));
            System.out.println("Total found is " + totalFunding);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
