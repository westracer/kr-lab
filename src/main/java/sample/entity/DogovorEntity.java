package sample.entity;

import javax.persistence.*;
import java.sql.Date;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "dogovor", schema = "komraz", catalog = "")
public class DogovorEntity {
    private int id;
    private UrLicoEntity urLico;
    private String nomer;
    private Date date;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "ur_lico_id")
    public UrLicoEntity getUrLico() {
        return urLico;
    }

    public void setUrLico(UrLicoEntity urLico) {
        this.urLico = urLico;
    }

    @Basic
    @Column(name = "nomer")
    public String getNomer() {
        return nomer;
    }

    public void setNomer(String nomer) {
        this.nomer = nomer;
    }

    @Basic
    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DogovorEntity that = (DogovorEntity) o;

        if (id != that.id) return false;
        if (urLico != that.urLico) return false;
        if (nomer != null ? !nomer.equals(that.nomer) : that.nomer != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + urLico.hashCode();
        result = 31 * result + (nomer != null ? nomer.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Договор №" + nomer;
    }

    public String toStringExtended() {
        StringBuilder sb = new StringBuilder(toString());
        if (urLico != null) {
            sb.append(" с ").append(urLico.getName());
        }

        if (date != null) {
            java.util.Date d = new java.util.Date(date.getTime());
            sb.append(" от ").append(DateTimeFormatter.ofPattern("dd.MM.yyyy").format(d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
        }

        return sb.toString();
    }
}
