package sample.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "pokazaniya", schema = "komraz", catalog = "")
public class PokazaniyaEntity {
    private int id;
    private double value;
    private Date date;
    private SchetchikEntity schetchik;

    @Id
    @GeneratedValue(generator="increment")
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "value")
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Basic
    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @ManyToOne
    @JoinColumn(name = "schetchik_id")
    public SchetchikEntity getSchetchik() {
        return schetchik;
    }

    public void setSchetchik(SchetchikEntity schetchik) {
        this.schetchik = schetchik;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PokazaniyaEntity that = (PokazaniyaEntity) o;

        if (id != that.id) return false;
        if (Double.compare(that.value, value) != 0) return false;
        if (schetchik != that.schetchik) return false;
        if (!Objects.equals(date, that.date)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        temp = Double.doubleToLongBits(value);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + schetchik.hashCode();
        return result;
    }
}
