package sample.util;

import com.independentsoft.office.word.WordDocument;

import javax.xml.stream.XMLStreamException;
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

        try {
            doc.save("C:\\Users\\westy\\Documents\\Lab shite\\комраз\\test.docx");
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
