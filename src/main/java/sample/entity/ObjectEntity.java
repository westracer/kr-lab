package sample.entity;

import javax.persistence.*;

@Entity
@Table(name = "object", schema = "komraz", catalog = "")
public class ObjectEntity {
    private int id;
    private double ploshad;
    private int adresId;
    private int dogovorId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "ploshad")
    public double getPloshad() {
        return ploshad;
    }

    public void setPloshad(double ploshad) {
        this.ploshad = ploshad;
    }

    @Basic
    @Column(name = "adres_id")
    public int getAdresId() {
        return adresId;
    }

    public void setAdresId(int adresId) {
        this.adresId = adresId;
    }

    @Basic
    @Column(name = "dogovor_id")
    public int getDogovorId() {
        return dogovorId;
    }

    public void setDogovorId(int dogovorId) {
        this.dogovorId = dogovorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ObjectEntity that = (ObjectEntity) o;

        if (id != that.id) return false;
        if (Double.compare(that.ploshad, ploshad) != 0) return false;
        if (adresId != that.adresId) return false;
        if (dogovorId != that.dogovorId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        temp = Double.doubleToLongBits(ploshad);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + adresId;
        result = 31 * result + dogovorId;
        return result;
    }
}
