package edu.ufcg.jhonatanbds.converter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import edu.ufcg.jhonatanbds.entity.ToDo;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

/**
 * Created by Jhonatan on 16/02/2017.
 */
public class ToDosToPDFConverter {

    private static Font catFont = new Font(Font.FontFamily.HELVETICA, 18,
            Font.BOLD);
    private static Font subFont = new Font(Font.FontFamily.COURIER, 16,
            Font.BOLD);

    public static HttpEntity<byte[]> createPDF(List<ToDo> toDos) throws DocumentException, IOException {
        File file = new File("relatorio_de_tarefas.pdf");
        FileOutputStream fileout = new FileOutputStream(file);
        Document document = new Document();
        PdfWriter.getInstance(document, fileout);

        document.open();
        addContent(document, toDos);
        document.close();

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_PDF);
        header.set(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=" + file.getName().replace(" ", "_"));
        header.setContentLength(file.length());

        return new HttpEntity<byte[]>(Files.readAllBytes(file.toPath()),
                header);
    }

    private static void addContent(Document document, List<ToDo> toDos) throws DocumentException {
        Paragraph title =  new Paragraph("Relátorio de Tarefas", catFont);
        addEmptyLine(title, 2);
        document.add(title);

        for (ToDo toDo : toDos) {
            Paragraph paragraph = new Paragraph(toDo.toString());
            addEmptyLine(paragraph, 2);
            document.add(paragraph);
        }
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
