package sample.entity;

import javax.persistence.*;

@Entity
@Table(name = "adres", schema = "komraz", catalog = "")
public class AdresEntity {
    private int id;
    private String name;
    private String kodPoKladr;
    private RaionEntity raion;

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
    @Column(name = "kod_po_kladr")
    public String getKodPoKladr() {
        return kodPoKladr;
    }

    public void setKodPoKladr(String kodPoKladr) {
        this.kodPoKladr = kodPoKladr;
    }

    @ManyToOne
    @JoinColumn(name = "raion_id")
    public RaionEntity getRaion() {
        return raion;
    }

    public void setRaion(RaionEntity raion) {
        this.raion = raion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdresEntity that = (AdresEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (kodPoKladr != null ? !kodPoKladr.equals(that.kodPoKladr) : that.kodPoKladr != null) return false;
        if (raion != null ? !raion.equals(that.raion) : that.raion != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (kodPoKladr != null ? kodPoKladr.hashCode() : 0);
        result = 31 * result + (raion != null ? raion.hashCode() : 0);
        return result;
    }
}
