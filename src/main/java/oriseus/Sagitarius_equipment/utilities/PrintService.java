package oriseus.Sagitarius_equipment.utilities;

import javafx.print.PageLayout;
import javafx.print.PrinterJob;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;

public class PrintService {
    public static void print(Pane pane) {
        
        PrinterJob job = PrinterJob.createPrinterJob();
        WritableImage snapshot = pane.snapshot(new SnapshotParameters(), null);
        ImageView imageView = new ImageView(snapshot);

        if (job != null && job.showPrintDialog(pane.getScene().getWindow())) {

            PageLayout pageLayout = job.getJobSettings().getPageLayout();

            imageView.setFitWidth(pageLayout.getPrintableWidth());
            imageView.setPreserveRatio(true);

            job.printPage(imageView);
            job.endJob();
        }
    }
}
