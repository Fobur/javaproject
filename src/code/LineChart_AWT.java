package code;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.sql.ResultSet;

public class LineChart_AWT extends ApplicationFrame {

    public LineChart_AWT( String applicationTitle , String chartTitle, ResultSet resultSet) {
        super(applicationTitle);
        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle,
                "Years","Total fundings",
                createDataset(resultSet),
                PlotOrientation.VERTICAL,
                true,true,false);

        ChartPanel chartPanel = new ChartPanel( lineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 1000 , 500 ) );
        setContentPane( chartPanel );
    }

    private DefaultCategoryDataset createDataset(ResultSet resultSet) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        try {
            while (resultSet.next())
            {
                var endBuiltYear = resultSet.getString("endBuiltYear");
                var averageTotalFundings = resultSet.getLong("avg(totalFunding)");
                dataset.addValue(averageTotalFundings , "fundings" , endBuiltYear );
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return dataset;
    }

    public static void main(ResultSet resultSet) {
        LineChart_AWT chart = new LineChart_AWT(
                "Graph" ,
                "Общий объем финансирования по годам завершения строительства", resultSet);

        chart.pack( );
        RefineryUtilities.centerFrameOnScreen( chart );
        chart.setVisible( true );
    }
}
