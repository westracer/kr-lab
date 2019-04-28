package sample.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "obiem_potr", schema = "komraz", catalog = "")
public class ObiemPotrEntity {
    private int id;
    private Date date;
    private double znachenie;
    private Integer dogovorId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    @Column(name = "znachenie")
    public double getZnachenie() {
        return znachenie;
    }

    public void setZnachenie(double znachenie) {
        this.znachenie = znachenie;
    }

    @Basic
    @Column(name = "dogovor_id")
    public Integer getDogovorId() {
        return dogovorId;
    }

    public void setDogovorId(Integer dogovorId) {
        this.dogovorId = dogovorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ObiemPotrEntity that = (ObiemPotrEntity) o;

        if (id != that.id) return false;
        if (Double.compare(that.znachenie, znachenie) != 0) return false;
        if (!Objects.equals(date, that.date)) return false;
        if (!Objects.equals(dogovorId, that.dogovorId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        temp = Double.doubleToLongBits(znachenie);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (dogovorId != null ? dogovorId.hashCode() : 0);
        return result;
    }
}
