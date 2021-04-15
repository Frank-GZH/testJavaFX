package sample;

import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimplePrintServiceExporterConfiguration;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import java.util.HashMap;
import java.util.Map;


public class testController {


    @FXML
    private Pane printPane;

    public void print() {
        System.out.println("开始打印");
        HashAttributeSet hs = new HashAttributeSet();
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(DocFlavor.SERVICE_FORMATTED.PAGEABLE, hs);
//        JRPrintServiceExporter exporter = new JRPrintServiceExporter();
        JRPrintServiceExporter exporter = new JRPrintServiceExporter();
        SimplePrintServiceExporterConfiguration cfg = new SimplePrintServiceExporterConfiguration();
        cfg.setPrintService(printServices[0]);
        cfg.setDisplayPageDialog(false);    // 页面设置
        cfg.setDisplayPrintDialog(false);   // 打印机设置

        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        printRequestAttributeSet.add(new Copies(1));    // 打印张数
        cfg.setPrintRequestAttributeSet(printRequestAttributeSet);

        exporter.setConfiguration(cfg);

        JasperReport jasperReport = null;
        try {
            jasperReport = (JasperReport) JRLoader.loadObject(JRLoader.getResource("ireports/report4.jasper"));
        } catch (JRException e) {
            e.printStackTrace();
        }
        Map<String, Object> stringMap = new HashMap<>(1);
        JasperPrint jasperPrint = null;
        try {
            jasperPrint = JasperFillManager.fillReport(jasperReport, stringMap, new JREmptyDataSource());
        } catch (JRException e) {
            e.printStackTrace();
        }
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        try {
            exporter.exportReport();
        } catch (JRException e) {
            e.printStackTrace();
        }


    }
}
