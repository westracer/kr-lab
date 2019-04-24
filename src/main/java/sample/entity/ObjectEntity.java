package sample.entity;

import javax.persistence.*;

@Entity
@Table(name = "object", schema = "komraz", catalog = "")
public class ObjectEntity {
    private int id;
    private double ploshad;
    private AdresEntity adres;
    private DogovorEntity dogovor;

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
    @Column(name = "ploshad")
    public double getPloshad() {
        return ploshad;
    }

    public void setPloshad(double ploshad) {
        this.ploshad = ploshad;
    }

    @ManyToOne
    @JoinColumn(name = "adres_id")
    public AdresEntity getAdres() {
        return adres;
    }

    public void setAdres(AdresEntity adres) {
        this.adres = adres;
    }

    @ManyToOne
    @JoinColumn(name = "dogovor_id")
    public DogovorEntity getDogovor() {
        return dogovor;
    }

    public void setDogovor(DogovorEntity dogovor) {
        this.dogovor = dogovor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ObjectEntity that = (ObjectEntity) o;

        if (id != that.id) return false;
        if (Double.compare(that.ploshad, ploshad) != 0) return false;
        if (adres != that.adres) return false;
        if (dogovor != that.dogovor) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        temp = Double.doubleToLongBits(ploshad);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + adres.hashCode();
        result = 31 * result + dogovor.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return adres.toString();
    }
}
