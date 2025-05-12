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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PDFGenerator {

    public void generateDashboardReport(ProfDashboardService service, BarChart<String, Number> courseChart, LineChart<String, Number> studentChart) {
        String folderPath = "src/main/resources/PDF";
        Path path = Paths.get(folderPath);

        if (!Files.exists(path)) {
            try {
                Files.createDirectory(path);
                System.out.println("Directory 'PDF' created successfully.");
            } catch (IOException e) {
                System.out.println("Failed to create directory 'PDF'");
                e.printStackTrace();
            }
        }

        String dest = folderPath + "/Professor_Dashboard_Report.pdf";
        String courseChartPath = folderPath + "/course_chart.png";
        String studentChartPath = folderPath + "/student_chart.png";

        deleteOldChart(courseChartPath);
        deleteOldChart(studentChartPath);

        try {
            PdfWriter writer = new PdfWriter(new FileOutputStream(dest));
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            Paragraph title = new Paragraph("Professor Dashboard Report")
                    .setFontSize(18)
                    .setBold()
                    .setMarginBottom(10);
            document.add(title);

            document.add(new Paragraph("Professor UserName: " + service.getProfName()).setMarginBottom(5));
            document.add(new Paragraph("Total Courses: " + service.getTotalCourses()).setMarginBottom(5));
            document.add(new Paragraph("Total Students: " + service.getTotalStudents()).setMarginBottom(5));
            document.add(new Paragraph("Date: " + service.getDate().toString()).setMarginBottom(10));

            document.add(new Paragraph("Assistant Tip:").setBold().setMarginTop(10));
            document.add(new Paragraph(service.getAssistantTip()).setMarginBottom(10));
            document.add(new Paragraph("Motivational Quote:").setBold().setMarginTop(10));
            document.add(new Paragraph(service.getMotivationalQuote()).setMarginBottom(15));

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

            exportChartToImage(courseChart, courseChartPath);
            exportChartToImage(studentChart, studentChartPath);

            Image courseImage = new Image(ImageDataFactory.create(courseChartPath));
            Image studentImage = new Image(ImageDataFactory.create(studentChartPath));

            document.add(new Paragraph("Courses Distribution:").setBold().setMarginTop(20));
            document.add(courseImage.setAutoScale(true).setMarginBottom(10));

            document.add(new Paragraph("Students Distribution:").setBold().setMarginTop(20));
            document.add(studentImage.setAutoScale(true).setMarginBottom(10));

            document.close();
            System.out.println("PDF Report generated successfully at " + dest);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void exportChartToImage(javafx.scene.chart.Chart chart, String fileName) {
        WritableImage image = chart.snapshot(new SnapshotParameters(), null);
        File file = new File(fileName);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void deleteOldChart(String path) {
        File file = new File(path);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Old chart image deleted: " + path);
            } else {
                System.out.println("Failed to delete old chart image: " + path);
            }
        }
    }
}
