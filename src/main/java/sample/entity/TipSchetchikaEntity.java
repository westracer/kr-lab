package sample.entity;

import javax.persistence.*;

@Entity
@Table(name = "tip_schetchika", schema = "komraz", catalog = "")
public class TipSchetchikaEntity {
    private int id;
    private int shifr;
    private byte faz;
    private byte auto;
    private String klassTochnosti;

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
    public int getShifr() {
        return shifr;
    }

    public void setShifr(int shifr) {
        this.shifr = shifr;
    }

    @Basic
    @Column(name = "faz")
    public byte getFaz() {
        return faz;
    }

    public void setFaz(byte faz) {
        this.faz = faz;
    }

    @Basic
    @Column(name = "auto")
    public byte getAuto() {
        return auto;
    }

    public void setAuto(byte auto) {
        this.auto = auto;
    }

    @Basic
    @Column(name = "klass_tochnosti")
    public String getKlassTochnosti() {
        return klassTochnosti;
    }

    public void setKlassTochnosti(String klassTochnosti) {
        this.klassTochnosti = klassTochnosti;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TipSchetchikaEntity that = (TipSchetchikaEntity) o;

        if (id != that.id) return false;
        if (shifr != that.shifr) return false;
        if (faz != that.faz) return false;
        if (auto != that.auto) return false;
        if (klassTochnosti != null ? !klassTochnosti.equals(that.klassTochnosti) : that.klassTochnosti != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + shifr;
        result = 31 * result + (int) faz;
        result = 31 * result + (int) auto;
        result = 31 * result + (klassTochnosti != null ? klassTochnosti.hashCode() : 0);
        return result;
    }
}
