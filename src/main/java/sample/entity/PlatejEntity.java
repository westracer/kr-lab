package sample.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "platej", schema = "komraz", catalog = "")
public class PlatejEntity {
    private int id;
    private double summa;
    private Date date;
    private int urLicoId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "summa")
    public double getSumma() {
        return summa;
    }

    public void setSumma(double summa) {
        this.summa = summa;
    }

    @Basic
    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "ur_lico_id")
    public int getUrLicoId() {
        return urLicoId;
    }

    public void setUrLicoId(int urLicoId) {
        this.urLicoId = urLicoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlatejEntity that = (PlatejEntity) o;

        if (id != that.id) return false;
        if (Double.compare(that.summa, summa) != 0) return false;
        if (urLicoId != that.urLicoId) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        temp = Double.doubleToLongBits(summa);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + urLicoId;
        return result;
    }
}
