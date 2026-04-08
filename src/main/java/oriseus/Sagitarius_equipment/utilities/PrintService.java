package oriseus.Sagitarius_equipment.utilities;

import javafx.print.PrinterJob;
import javafx.scene.layout.Pane;

public class PrintService {
    public static void print(Pane pane) {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(null)) {
            job.printPage(pane);
            job.endJob();
        }
    }
}
