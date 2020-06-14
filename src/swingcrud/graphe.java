
package swingcrud;

import swingcrud.model.Region;
import java.awt.Color;
import java.awt.Paint;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.ui.TextAnchor;

/**
 *
 * @author arij
 */
public class graphe extends javax.swing.JFrame {

    Connection con;
    Statement st;
    ArrayList<Region> regions = new ArrayList<>();

    /**
     * Creates new form graphe
     */
    public graphe() {
        initComponents();

        final CategoryDataset dataset = createDataset();
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);
    }

    class CustomRenderer extends BarRenderer {

        private Paint[] colors;

        public CustomRenderer(final Paint[] colors) {
            this.colors = colors;
        }

        public Paint getItemPaint(final int row, final int column) {
            return this.colors[column % this.colors.length];
        }
    }

    private CategoryDataset createDataset() {
        /*double[][] data = new double[][]{
            {210, 300, 320, 265, 299, 200},
            {200, 304, 201, 201, 340, 300},};
        return DatasetUtilities.createCategoryDataset(
                "Team ", "Match", data);*/

        final String series1 = "Confirmés";
        final String series2 = "Morts";
        /* final String category1 = "Tunis";
        final String category2 = "Ariana";
        final String category3 = "Jendouba";
         */
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        regions.clear();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/covid", "root", "");
            String sql = "select * from regions";
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Region region = new Region(rs.getInt("id"), rs.getString("name"), rs.getString("longitude"), rs.getString("latitude"), rs.getInt("population"), rs.getInt("confirmes"), rs.getInt("morts"));
                regions.add(region);
            }

            for (Region region : regions) {

                dataset.addValue(region.getConfirmes(), series1, region.getName());
                dataset.addValue(region.getMorts(), series2, region.getName());

                //System.out.println(region.getName());
                //row[0] = region.getId();
                /* row[1] = region.getName();
                row[2] = region.getLongitude();
                row[3] = region.getLatitude();
                row[4] = region.getPop();
                row[5] = region.getConfirmes();
                row[6] = region.getMorts();*/
            }

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Crud.class.getName()).log(Level.SEVERE, null, ex);
        }

        /*        dataset.addValue(100, series1, category1);
        dataset.addValue(10, series2, category1);
        dataset.addValue(60, series1, category2);
        dataset.addValue(5, series2, category2);
        dataset.addValue(1, series1, category3);*/

 /* final double[][] data = new double[][]{{4.0, 3.0, 3.0, 6.0}};
        return DatasetUtilities.createCategoryDataset(
                "Series ",
                "categorie",
                data
        );*/
        return dataset;
    }

    private JFreeChart createChart(final CategoryDataset dataset) {

        /*    final JFreeChart chart = ChartFactory.createBarChart(
                "Courbe", // chart title
                "Région", // domain axis label
                "Nombre de cas", // range axis label
                dataset, // data
                PlotOrientation.HORIZONTAL, // the plot orientation
                false, // include legend
                true,
                false
        );*/
        final JFreeChart chart = ChartFactory.createStackedBarChart(
                "Courbe", // chart title
                "Région", // domain axis label
                "Nombre de cas", // range axis label
                dataset, PlotOrientation.HORIZONTAL, true, true, false);

        chart.setBackgroundPaint(Color.lightGray);

        CategoryPlot plot = chart.getCategoryPlot();
        plot.getRenderer().setSeriesPaint(0, new Color(0, 0, 255));
        plot.getRenderer().setSeriesPaint(1, new Color(128, 0, 0));


        /*  CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.white);
        StackedBarRenderer stackedbarrenderer = (StackedBarRenderer) plot.getRenderer();
        stackedbarrenderer.setDrawBarOutline(false);
        stackedbarrenderer.setItemLabelsVisible(true);
        stackedbarrenderer.setSeriesItemLabelGenerator(0, new StandardCategoryItemLabelGenerator());*/
        // get a reference to the plot for further customisation...
        //  final CategoryPlot plot = chart.getCategoryPlot();
        // plot.setNoDataMessage("NO DATA!");
        //  plot.getRenderer().setSeriesPaint(0, new Color(128, 0, 0));
        //  plot.getRenderer().setSeriesPaint(1, new Color(0, 0, 255));
        /*      final CategoryItemRenderer renderer = new CustomRenderer(
                new Paint[]{Color.red, Color.blue, Color.green,
                    Color.yellow, Color.orange, Color.cyan,
                    Color.magenta, Color.blue}
        );
//        renderer.setLabelGenerator(new StandardCategoryLabelGenerator());
        renderer.setItemLabelsVisible(true);
        final ItemLabelPosition p = new ItemLabelPosition(
                ItemLabelAnchor.CENTER, TextAnchor.CENTER, TextAnchor.CENTER, 45.0
        );
        renderer.setPositiveItemLabelPosition(p);
        plot.setRenderer(renderer);*/
        // change the margin at the top of the range axis...
        final ValueAxis rangeAxis = plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setLowerMargin(0.15);
        rangeAxis.setUpperMargin(0.15);

        return chart;

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 637, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 385, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(graphe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(graphe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(graphe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(graphe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new graphe().setVisible(true);

            }
        });
    }

    private void fetch() {

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
