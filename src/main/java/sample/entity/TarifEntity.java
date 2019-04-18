package sample.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "tarif", schema = "komraz", catalog = "")
public class TarifEntity {
    private int id;
    private String pokazatel;
    private double price;
    private Date startDate;
    private int shifr;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "pokazatel")
    public String getPokazatel() {
        return pokazatel;
    }

    public void setPokazatel(String pokazatel) {
        this.pokazatel = pokazatel;
    }

    @Basic
    @Column(name = "price")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Basic
    @Column(name = "start_date")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "shifr")
    public int getShifr() {
        return shifr;
    }

    public void setShifr(int shifr) {
        this.shifr = shifr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TarifEntity that = (TarifEntity) o;

        if (id != that.id) return false;
        if (Double.compare(that.price, price) != 0) return false;
        if (shifr != that.shifr) return false;
        if (pokazatel != null ? !pokazatel.equals(that.pokazatel) : that.pokazatel != null) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (pokazatel != null ? pokazatel.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + shifr;
        return result;
    }
}
