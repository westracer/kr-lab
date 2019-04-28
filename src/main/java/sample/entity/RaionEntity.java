package sample.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "raion", schema = "komraz", catalog = "")
public class RaionEntity {
    private int id;
    private String name;
    private String shifr;
    private String kodOkato;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    @Column(name = "kod_okato")
    public String getKodOkato() {
        return kodOkato;
    }

    public void setKodOkato(String kodOkato) {
        this.kodOkato = kodOkato;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RaionEntity that = (RaionEntity) o;

        if (id != that.id) return false;
        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(shifr, that.shifr)) return false;
        if (!Objects.equals(kodOkato, that.kodOkato)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (shifr != null ? shifr.hashCode() : 0);
        result = 31 * result + (kodOkato != null ? kodOkato.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return this.name + " [" + this.kodOkato + "]";
    }
}
