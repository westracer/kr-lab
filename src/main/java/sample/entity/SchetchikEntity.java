package sample.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "schetchik", schema = "komraz", catalog = "")
public class SchetchikEntity {
    private int id;
    private String nomer;
    private Date proverkaDate;
    private TipElektrEntity tip;
    private int tarifId;
    private int tipElId;
    private int objectId;

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
    @Column(name = "nomer")
    public String getNomer() {
        return nomer;
    }

    public void setNomer(String nomer) {
        this.nomer = nomer;
    }

    @Basic
    @Column(name = "proverka_date")
    public Date getProverkaDate() {
        return proverkaDate;
    }

    public void setProverkaDate(Date proverkaDate) {
        this.proverkaDate = proverkaDate;
    }

    @ManyToOne
    @JoinColumn(name = "tip_id")
    public TipElektrEntity getTip() {
        return tip;
    }

    public void setTip(TipElektrEntity tipId) {
        this.tip = tipId;
    }

    @Basic
    @Column(name = "tarif_id")
    public int getTarifId() {
        return tarifId;
    }

    public void setTarifId(int tarifId) {
        this.tarifId = tarifId;
    }

    @Basic
    @Column(name = "tip_el_id")
    public int getTipElId() {
        return tipElId;
    }

    public void setTipElId(int tipElId) {
        this.tipElId = tipElId;
    }

    @Basic
    @Column(name = "object_id")
    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SchetchikEntity that = (SchetchikEntity) o;

        if (id != that.id) return false;
        if (tip != that.tip) return false;
        if (tarifId != that.tarifId) return false;
        if (tipElId != that.tipElId) return false;
        if (objectId != that.objectId) return false;
        if (nomer != null ? !nomer.equals(that.nomer) : that.nomer != null) return false;
        if (proverkaDate != null ? !proverkaDate.equals(that.proverkaDate) : that.proverkaDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nomer != null ? nomer.hashCode() : 0);
        result = 31 * result + (proverkaDate != null ? proverkaDate.hashCode() : 0);
        result = 31 * result + tip.hashCode();
        result = 31 * result + tarifId;
        result = 31 * result + tipElId;
        result = 31 * result + objectId;
        return result;
    }
}
