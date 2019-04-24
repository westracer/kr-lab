package sample.util;

import com.independentsoft.office.ExtendedBoolean;
import com.independentsoft.office.word.Paragraph;
import com.independentsoft.office.word.Run;
import com.independentsoft.office.word.WordDocument;
import com.independentsoft.office.word.tables.Cell;
import com.independentsoft.office.word.tables.Row;
import com.independentsoft.office.word.tables.Table;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DogovorEditor {
    private enum TemplateString {
        DATE_NOW, DOC_NUMBER, CUSTOMER_NAME, CUSTOMER_INFO, CUSTOMER_INN, CUSTOMER_KPP
    }

    private static final int INDEX_OF_SCHETICHIK_TABLE = 2;
    private static final String DATE_FORMAT = "«d» MMMM YYYY г";

    private InputStream is;
    private WordDocument doc;

    private DogovorEditor() {}

    public static DogovorEditor getInstance(URL url) {
        DogovorEditor de = new DogovorEditor();

        try {
            de.is = url.openStream();
            de.doc = new WordDocument(url.getPath());
        } catch (IOException | XMLStreamException e) {
            return null;
        }

        return de;
    }

    public void replace() {
        for (TemplateString ts : TemplateString.values()) {
            String templateRepl = "";
            switch (ts) {
                case DATE_NOW:
                    templateRepl = DateTimeFormatter.ofPattern(DATE_FORMAT)
                            .withLocale(new Locale("ru"))
                            .format(LocalDate.of(2014, 2, 28));
                    break;
                case DOC_NUMBER:
                    templateRepl = "test";
                    break;
                case CUSTOMER_INN:
                    templateRepl = "test";
                    break;
                case CUSTOMER_KPP:
                    templateRepl = "test";
                    break;
                case CUSTOMER_INFO:
                    templateRepl = "test";
                    break;
                case CUSTOMER_NAME:
                    templateRepl = "test";
                    break;
                default:
            }

            doc.replace("{{" + ts.toString() + "}}", templateRepl);
        }

        Table table = doc.getTables().get(INDEX_OF_SCHETICHIK_TABLE);
        Row row = new Row();

        for (int i = 0; i < 7; i++) {
            Run run1 = new Run(Integer.toString(i));

            Paragraph p = new Paragraph();
            p.add(run1);

            Cell c = new Cell();
            c.add(p);

            row.add(c);
        }

        table.add(row);

        try {
            String path = "C:\\Users\\westy\\Documents\\Lab shite\\комраз\\test.docx";
            File f = new File(path);
            f.delete();
            doc.save(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            is.close();
        } catch (IOException ignored) {}
    }
}
