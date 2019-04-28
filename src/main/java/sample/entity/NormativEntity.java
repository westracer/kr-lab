package sample.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "normativ", schema = "komraz", catalog = "")
public class NormativEntity {
    private int id;
    private String category;
    private Date prinyat;
    private double norma;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "category")
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Basic
    @Column(name = "prinyat")
    public Date getPrinyat() {
        return prinyat;
    }

    public void setPrinyat(Date prinyat) {
        this.prinyat = prinyat;
    }

    @Basic
    @Column(name = "norma")
    public double getNorma() {
        return norma;
    }

    public void setNorma(double norma) {
        this.norma = norma;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NormativEntity that = (NormativEntity) o;

        if (id != that.id) return false;
        if (Double.compare(that.norma, norma) != 0) return false;
        if (!Objects.equals(category, that.category)) return false;
        if (!Objects.equals(prinyat, that.prinyat)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (prinyat != null ? prinyat.hashCode() : 0);
        temp = Double.doubleToLongBits(norma);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
