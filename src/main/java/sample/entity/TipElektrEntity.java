package sample.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tip_elektr", schema = "komraz", catalog = "")
public class TipElektrEntity {
    private int id;
    private String shifr;
    private double prisoed;
    private double zayavl;
    private double max;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "shifr")
    public String getShifr() {
        return shifr;
    }

    public void setShifr(String shifr) {
        this.shifr = shifr;
    }

    @Basic
    @Column(name = "prisoed")
    public double getPrisoed() {
        return prisoed;
    }

    public void setPrisoed(double prisoed) {
        this.prisoed = prisoed;
    }

    @Basic
    @Column(name = "zayavl")
    public double getZayavl() {
        return zayavl;
    }

    public void setZayavl(double zayavl) {
        this.zayavl = zayavl;
    }

    @Basic
    @Column(name = "max")
    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TipElektrEntity that = (TipElektrEntity) o;

        if (id != that.id) return false;
        if (Double.compare(that.prisoed, prisoed) != 0) return false;
        if (Double.compare(that.zayavl, zayavl) != 0) return false;
        if (Double.compare(that.max, max) != 0) return false;
        if (!Objects.equals(shifr, that.shifr)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (shifr != null ? shifr.hashCode() : 0);
        temp = Double.doubleToLongBits(prisoed);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(zayavl);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(max);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return this.shifr + ": " + this.zayavl + "/" + this.prisoed + "/" + this.max;
    }
}
