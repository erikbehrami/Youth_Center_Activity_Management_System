package utils;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import javafx.scene.chart.PieChart;
import services.ProfServices.ProfDashboardService;

import java.io.FileOutputStream;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;
import java.io.File;

public class PDFGenerator {

    public void generateDashboardReport(ProfDashboardService service, BarChart<String, Number> courseChart, LineChart<String, Number> studentChart) {
        // File path for PDF generation
        String dest = "Professor_Dashboard_Report.pdf";

        try {
            // Initialize the writer and document
            PdfWriter writer = new PdfWriter(new FileOutputStream(dest));
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Title
            Paragraph title = new Paragraph("Professor Dashboard Report")
                    .setFontSize(18)
                    .setBold()
                    .setMarginBottom(10);
            document.add(title);

            // Professor Info
            document.add(new Paragraph("Professor Name: " + service.getProfName()).setMarginBottom(5));
            document.add(new Paragraph("Total Courses: " + service.getTotalCourses()).setMarginBottom(5));
            document.add(new Paragraph("Total Students: " + service.getTotalStudents()).setMarginBottom(5));
            document.add(new Paragraph("Date: " + service.getDate().toString()).setMarginBottom(10));

            // Assistant Tip and Motivational Quote
            document.add(new Paragraph("Assistant Tip:").setBold().setMarginTop(10));
            document.add(new Paragraph(service.getAssistantTip()).setMarginBottom(10));
            document.add(new Paragraph("Motivational Quote:").setBold().setMarginTop(10));
            document.add(new Paragraph(service.getMotivationalQuote()).setMarginBottom(15));

            // Gender Distribution Table
            document.add(new Paragraph("Gender Distribution:").setFontSize(14).setBold().setMarginTop(15));

            float[] columnWidths = {200F, 200F};
            Table table = new Table(columnWidths);

            table.addCell(new Cell().add(new Paragraph("Gender")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
            table.addCell(new Cell().add(new Paragraph("Count")).setBackgroundColor(ColorConstants.LIGHT_GRAY));

            for (PieChart.Data data : service.getGenderDistribution()) {
                table.addCell(new Cell().add(new Paragraph(data.getName())));
                table.addCell(new Cell().add(new Paragraph(String.valueOf((int) data.getPieValue()))));
            }

            document.add(table);

            // CHART EXPORTING
            exportChartToImage(courseChart, "course_chart.png");
            exportChartToImage(studentChart, "student_chart.png");

            // Add Images to PDF
            Image courseImage = new Image(ImageDataFactory.create("course_chart.png"));
            Image studentImage = new Image(ImageDataFactory.create("student_chart.png"));

            document.add(new Paragraph("Courses Distribution:").setBold().setMarginTop(20));
            document.add(courseImage.setAutoScale(true).setMarginBottom(10));

            document.add(new Paragraph("Students Distribution:").setBold().setMarginTop(20));
            document.add(studentImage.setAutoScale(true).setMarginBottom(10));

            // Close the document
            document.close();
            System.out.println("PDF Report generated successfully at " + dest);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper method to export charts as images
    private void exportChartToImage(javafx.scene.chart.Chart chart, String fileName) {
        WritableImage image = chart.snapshot(new SnapshotParameters(), null);
        File file = new File(fileName);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
