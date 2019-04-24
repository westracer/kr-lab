package sample.util;

import com.independentsoft.office.word.Paragraph;
import com.independentsoft.office.word.Run;
import com.independentsoft.office.word.WordDocument;
import com.independentsoft.office.word.tables.Cell;
import com.independentsoft.office.word.tables.Row;
import com.independentsoft.office.word.tables.Table;
import sample.Main;
import sample.entity.DogovorEntity;
import sample.entity.SchetchikEntity;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
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

    public static DogovorEditor getInstance() {
        DogovorEditor de = new DogovorEditor();
        URL url = Main.class.getResource("../template/dogovor.docx");

        try {
            de.is = url.openStream();
            de.doc = new WordDocument(url.getPath());
        } catch (IOException | XMLStreamException e) {
            return null;
        }

        return de;
    }

    public void replaceAndExport(DogovorEntity dogovor, String savePath) {
        for (TemplateString ts : TemplateString.values()) {
            String templateRepl = "";
            switch (ts) {
                case DATE_NOW:
                    if (dogovor.getDate() != null) {
                        templateRepl = DateTimeFormatter.ofPattern(DATE_FORMAT)
                                .withLocale(new Locale("ru"))
                                .format(DbHelper.getLocalDate(dogovor.getDate()));
                    }
                    break;
                case DOC_NUMBER:
                    templateRepl += dogovor.getNomer();
                    break;
                case CUSTOMER_INN:
                    if (dogovor.getUrLico() != null) {
                        templateRepl += dogovor.getUrLico().getInn();
                    }
                    break;
                case CUSTOMER_KPP:
                    if (dogovor.getUrLico() != null) {
                        templateRepl += dogovor.getUrLico().getKpp();
                    }
                    break;
                case CUSTOMER_INFO:
                    if (dogovor.getUrLico() != null) {
                        templateRepl += dogovor.getUrLico().getInfo();
                    }
                    break;
                case CUSTOMER_NAME:
                    if (dogovor.getUrLico() != null) {
                        templateRepl += dogovor.getUrLico().getName();
                    }
                    break;
                default:
            }

            doc.replace("{{" + ts.toString() + "}}", templateRepl);
        }

        Table table = doc.getTables().get(INDEX_OF_SCHETICHIK_TABLE);

        for (SchetchikEntity sch : dogovor.allSchetchiki()) {
            Row row = new Row();

            String[] cellStrings = new String[7];
            Arrays.fill(cellStrings, "");

            cellStrings[0] = sch.getNomer();
            cellStrings[1] = sch.getObject().getAdres().getName();
            cellStrings[2] = sch.getTip().toShortString();
            cellStrings[3] = "" + sch.getTip().getShifr();
            cellStrings[4] = sch.getTip().getKlassTochnosti();

            LocalDate d = DbHelper.getLocalDate(sch.getProverkaDate());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            cellStrings[5] = formatter.format(d);
            cellStrings[6] = formatter.format(d.plusYears(1));

            for (String cellString : cellStrings) {
                Paragraph p = new Paragraph();
                p.add(new Run(cellString));

                Cell c = new Cell();
                c.add(p);

                row.add(c);
            }

            table.add(row);
        }

        try {
            File f = new File(savePath);
            if (f.exists()) f.delete();
            doc.save(savePath);
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
