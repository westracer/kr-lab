package sample.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "schetchik", schema = "komraz", catalog = "")
public class SchetchikEntity {
    private int id;
    private String nomer;
    private Date proverkaDate;
    private TipSchetchikaEntity tip;
    private TarifEntity tarif;
    private TipElektrEntity tipEl;
    private ObjectEntity object;

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
    public TipSchetchikaEntity getTip() {
        return tip;
    }

    public void setTip(TipSchetchikaEntity tipId) {
        this.tip = tipId;
    }

    @ManyToOne
    @JoinColumn(name = "tarif_id")
    public TarifEntity getTarifId() {
        return tarif;
    }

    public void setTarifId(TarifEntity tarif) {
        this.tarif = tarif;
    }

    @ManyToOne
    @JoinColumn(name = "tip_el_id")
    public TipElektrEntity getTipEl() {
        return tipEl;
    }

    public void setTipEl(TipElektrEntity tipElId) {
        this.tipEl = tipElId;
    }

    @ManyToOne
    @JoinColumn(name = "object_id")
    public ObjectEntity getObjectId() {
        return object;
    }

    public void setObjectId(ObjectEntity objectId) {
        this.object = object;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SchetchikEntity that = (SchetchikEntity) o;

        if (id != that.id) return false;
        if (tip != that.tip) return false;
        if (tarif != that.tarif) return false;
        if (tipEl != that.tipEl) return false;
        if (object != that.object) return false;
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
        result = 31 * result + tarif.hashCode();
        result = 31 * result + tipEl.hashCode();
        result = 31 * result + object.hashCode();
        return result;
    }
}
