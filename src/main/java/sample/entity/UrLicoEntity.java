package sample.entity;

import javax.persistence.*;

@Entity
@Table(name = "ur_lico", schema = "komraz", catalog = "")
public class UrLicoEntity {
    private int id;
    private String name;
    private String inn;
    private String kpp;
    private String info;
    private String shifr;

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
    @Column(name = "inn")
    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    @Basic
    @Column(name = "kpp")
    public String getKpp() {
        return kpp;
    }

    public void setKpp(String kpp) {
        this.kpp = kpp;
    }

    @Basic
    @Column(name = "info")
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Basic
    @Column(name = "shifr")
    public String getShifr() {
        return shifr;
    }

    public void setShifr(String shifr) {
        this.shifr = shifr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UrLicoEntity that = (UrLicoEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (inn != null ? !inn.equals(that.inn) : that.inn != null) return false;
        if (kpp != null ? !kpp.equals(that.kpp) : that.kpp != null) return false;
        if (info != null ? !info.equals(that.info) : that.info != null) return false;
        if (shifr != null ? !shifr.equals(that.shifr) : that.shifr != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (inn != null ? inn.hashCode() : 0);
        result = 31 * result + (kpp != null ? kpp.hashCode() : 0);
        result = 31 * result + (info != null ? info.hashCode() : 0);
        result = 31 * result + (shifr != null ? shifr.hashCode() : 0);
        return result;
    }
}
