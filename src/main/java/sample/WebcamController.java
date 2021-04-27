package sample;

import com.github.sarxos.webcam.Webcam;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.platform.win32.Winspool;
import com.sun.jna.platform.win32.WinspoolUtil;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.Attribute;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.PrintServiceAttribute;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.PrinterState;
import javax.print.attribute.standard.PrinterStateReason;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class WebcamController implements Initializable {



    @FXML
    Button btnStartCamera;

    @FXML
    Button btnStopCamera;

    @FXML
    Button btnDisposeCamera;

    @FXML
    ComboBox<WebCamInfo> cbCameraOptions;

    @FXML
    BorderPane bpWebCamPaneHolder;

    @FXML
    FlowPane fpBottomPane;

    @FXML
    ImageView imgWebCamCapturedImage;

    private class WebCamInfo {

        private String webCamName;
        private int webCamIndex;

        public String getWebCamName() {
            return webCamName;
        }

        public void setWebCamName(String webCamName) {
            this.webCamName = webCamName;
        }

        public int getWebCamIndex() {
            return webCamIndex;
        }

        public void setWebCamIndex(int webCamIndex) {
            this.webCamIndex = webCamIndex;
        }

        @Override
        public String toString() {
            return webCamName;
        }
    }

    private BufferedImage grabbedImage;
    private Webcam selWebCam = null;
    private boolean stopCamera = false;
    private ObjectProperty<Image> imageProperty = new SimpleObjectProperty<Image>();

    private String cameraListPromptText = "Choose Camera";


    public static Set<Attribute> getAttributes(PrintService printer) {
        Set<Attribute> set = new LinkedHashSet<Attribute>();

        //get the supported docflavors, categories and attributes
        Class<? extends Attribute>[] categories = (Class<? extends Attribute>[]) printer.getSupportedAttributeCategories();
        DocFlavor[] flavors = printer.getSupportedDocFlavors();
        AttributeSet attributes = printer.getAttributes();

        //get all the avaliable attributes
        for (Class<? extends Attribute> category : categories) {
            for (DocFlavor flavor : flavors) {
                //get the value
                Object value = printer.getSupportedAttributeValues(category, flavor, attributes);

                //check if it's something
                if (value != null) {
                    System.out.println("printer = " + value);
                    //if it's a SINGLE attribute...
                    if (value instanceof Attribute)
                        set.add((Attribute) value); //...then add it

                        //if it's a SET of attributes...
                    else if (value instanceof Attribute[])
                        set.addAll(Arrays.asList((Attribute[]) value)); //...then add its childs
                }
            }
        }

        return set;
    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

//        PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
//        Set<Attribute> attributes = getAttributes(printService);
//        for(Attribute attr : attributes){
//            System.out.println("print:"+ attr.getName());
//        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    Winspool.PRINTER_INFO_4[] printers = WinspoolUtil.getPrinterInfo4();
                    Winspool.PRINTER_INFO_2[] printer_info_2s = WinspoolUtil.getPrinterInfo2();
                    Winspool.PRINTER_INFO_1[] printer_info_1s = WinspoolUtil.getPrinterInfo1();
                    System.out.println("Printers:");
                    for (Winspool.PRINTER_INFO_2 printer : printer_info_2s) {
                        if( printer.pPrinterName.equals("pdfFactory")) {
                            System.out.println("  Name: " + printer.pPrinterName + ", server: "
                                    + printer.pServerName + ",status:" + printer.Status);
                        }
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


//        System.out.println("Printers:");
//        for (Winspool.PRINTER_INFO_4 printer : printers) {
//            System.out.println("  Name: " + printer.pPrinterName + ", server: "
//                    + printer.pServerName + ",status:" + printer.Attributes);
//        }

//        Winspool.PRINTER_INFO_4[] printers = WinspoolUtil.getPrinterInfo4();
//// Just an example - get the first printer from the list
//        Winspool.PRINTER_INFO_4 printer = printers[4];
//
//        WinNT.HANDLEByReference phPrinter = new WinNT.HANDLEByReference();
//        boolean opened = Winspool.INSTANCE.OpenPrinter(
//                printer.pPrinterName,
//                phPrinter,
//                null
//        );
//
//        if (!opened) {
//            System.err.printf("Failed to open printer \"%s\"!", printer.pPrinterName);
//            return;
//        }
//
//        Winspool.JOB_INFO_1[] jobs = WinspoolUtil.getJobInfo1(phPrinter);
//        System.out.printf("Printer \"%s\" has %d jobs\n", printer.pPrinterName, jobs.length);
//        for (Winspool.JOB_INFO_1 job : jobs) {
//            System.out.printf(
//                    "  Document: \"%s\", owner: %s, status: %s, total pages: %d, pages printed: %d ...\n",
//                    job.pDocument,
//                    job.pUserName,
//                    job.pStatus != null ? job.pStatus : job.Status,
//                    job.TotalPages,
//                    job.PagesPrinted
//            );
//        }
//
//        Winspool.INSTANCE.ClosePrinter(phPrinter.getValue());

//        AttributeSet attributes = printService.getAttributes();
//        System.out.println("isSupport = " + printService.isAttributeCategorySupported(PrinterState.class));
//        String printerState = attributes.get(PrinterState.class).toString();
//        String printerStateReason = attributes.get(PrinterStateReason.class).toString();
//
//        System.out.println("printerState =" + printerState); // May be IDLE, PROCESSING, STOPPED or UNKNOWN
//        System.out.println("printerStateReason =" + printerStateReason); // If your printer state returns STOPPED, for example, you can identify the reason
//
//        if (printerState.equals(PrinterState.STOPPED.toString())) {
//
//            if (printerStateReason.equals(PrinterStateReason.TONER_LOW.toString())) {
//
//                System.out.println("Toner level is low.");
//            }
//        }
//
//        PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
//        PrinterJob printerJob = PrinterJob.createPrinterJob();
//        System.out.println("default status:" + printerJob.getJobStatus());
//        System.out.println("arg0 = " + PrinterJob.JobStatus.values() );
//        for(PrinterJob.JobStatus c : PrinterJob.JobStatus.values()){
//            System.out.println("status = " + c);
//        }
//        System.out.println("arg0 = " + defaultService );
        ObservableList<WebCamInfo> options = FXCollections.observableArrayList();
        int webCamCounter = 0;
        for (Webcam webcam : Webcam.getWebcams()) {
            WebCamInfo webCamInfo = new WebCamInfo();
            webCamInfo.setWebCamIndex(webCamCounter);
            webCamInfo.setWebCamName(webcam.getName());
            options.add(webCamInfo);
            webCamCounter++;
        }
        cbCameraOptions.setItems(options);
        cbCameraOptions.setPromptText(cameraListPromptText);
        cbCameraOptions.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<WebCamInfo>() {

            @Override
            public void changed(ObservableValue<? extends WebCamInfo> arg0, WebCamInfo arg1, WebCamInfo arg2) {
                if (arg2 != null) {
                    System.out.println("WebCam Index: " + arg2.getWebCamIndex() + ": WebCam Name:" + arg2.getWebCamName());
                    initializeWebCam(arg2.getWebCamIndex());
                }
            }
        });
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                setImageViewSize();
            }
        });

    }

    protected void setImageViewSize() {

        double height = bpWebCamPaneHolder.getHeight();
        double width = bpWebCamPaneHolder.getWidth();
        imgWebCamCapturedImage.setFitHeight(height);
        imgWebCamCapturedImage.setFitWidth(width);
        imgWebCamCapturedImage.prefHeight(height);
        imgWebCamCapturedImage.prefWidth(width);
        imgWebCamCapturedImage.setPreserveRatio(true);

    }

    protected void initializeWebCam(final int webCamIndex) {

        Task<Void> webCamIntilizer = new Task<Void>() {

            @Override
            protected Void call() throws Exception {

                if (selWebCam == null) {
                    selWebCam = Webcam.getWebcams().get(webCamIndex);
                    selWebCam.open();
                } else {
                    closeCamera();
                    selWebCam = Webcam.getWebcams().get(webCamIndex);
                    selWebCam.open();
                }
                startWebCamStream();
                return null;
            }

        };

        new Thread(webCamIntilizer).start();
        fpBottomPane.setDisable(false);
        btnStartCamera.setDisable(true);
    }

    protected void startWebCamStream() {

        stopCamera = false;
        Task<Void> task = new Task<Void>() {

            @Override
            protected Void call() throws Exception {

                while (!stopCamera) {
                    try {
                        if ((grabbedImage = selWebCam.getImage()) != null) {

                            Platform.runLater(new Runnable() {

                                @Override
                                public void run() {
                                    final Image mainiamge = SwingFXUtils
                                            .toFXImage(grabbedImage, null);
                                    imageProperty.set(mainiamge);
                                }
                            });

                            grabbedImage.flush();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                return null;
            }

        };
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
        imgWebCamCapturedImage.imageProperty().bind(imageProperty);

    }

    private void closeCamera() {
        if (selWebCam != null) {
            selWebCam.close();
        }
    }

    public void stopCamera(ActionEvent event) {
        stopCamera = true;
        btnStartCamera.setDisable(false);
        btnStopCamera.setDisable(true);
    }

    public void startCamera(ActionEvent event) {
        stopCamera = false;
        startWebCamStream();
        btnStartCamera.setDisable(true);
        btnStopCamera.setDisable(false);
    }

    public void disposeCamera(ActionEvent event) {
        stopCamera = true;
        closeCamera();
        btnStopCamera.setDisable(true);
        btnStartCamera.setDisable(true);
    }
}
