package sample.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "pokazaniya", schema = "komraz", catalog = "")
public class PokazaniyaEntity {
    private int id;
    private double value;
    private Date date;
    private int schetchikId;

    @Id
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

    @Basic
    @Column(name = "schetchik_id")
    public int getSchetchikId() {
        return schetchikId;
    }

    public void setSchetchikId(int schetchikId) {
        this.schetchikId = schetchikId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PokazaniyaEntity that = (PokazaniyaEntity) o;

        if (id != that.id) return false;
        if (Double.compare(that.value, value) != 0) return false;
        if (schetchikId != that.schetchikId) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;

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
        result = 31 * result + schetchikId;
        return result;
    }
}
