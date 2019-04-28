package sample.entity;

import sample.util.DbHelper;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "dogovor", schema = "komraz", catalog = "")
public class DogovorEntity {
    private int id;
    private UrLicoEntity urLico;
    private String nomer;
    private Date date;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "ur_lico_id")
    public UrLicoEntity getUrLico() {
        return urLico;
    }

    public void setUrLico(UrLicoEntity urLico) {
        this.urLico = urLico;
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
    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DogovorEntity that = (DogovorEntity) o;

        if (id != that.id) return false;
        if (urLico != that.urLico) return false;
        if (!Objects.equals(nomer, that.nomer)) return false;
        if (!Objects.equals(date, that.date)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + urLico.hashCode();
        result = 31 * result + (nomer != null ? nomer.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return toStringExtended();
    }

    @SuppressWarnings("unchecked")
    public List<SchetchikEntity> allSchetchiki() {
        DbHelper.checkSession();

        CriteriaBuilder cb = DbHelper.session.getCriteriaBuilder();

        CriteriaQuery cq = cb.createQuery(ObjectEntity.class);
        Root root = cq.from(ObjectEntity.class);
        cq = cq.where(cb.equal(root.get("dogovor"), this));
        TypedQuery allQuery = DbHelper.session.createQuery(cq.select(root));
        List<ObjectEntity> objects = allQuery.getResultList();

        if (objects.isEmpty()) {
            return new ArrayList<>();
        }

        cq = cb.createQuery(SchetchikEntity.class);
        root = cq.from(SchetchikEntity.class);
        cq = cq.where(
                root
                .get("object")
                .in(objects)
        );
        allQuery = DbHelper.session.createQuery(cq.select(root));

        return allQuery.getResultList();
    }

    public String toStringExtended() {
        StringBuilder sb = new StringBuilder("Договор №" + nomer);
        if (urLico != null) {
            sb.append(" с ").append(urLico.getName());
        }

        if (date != null) {
            sb.append(" от ").append(DateTimeFormatter.ofPattern("dd.MM.yyyy").format(DbHelper.getLocalDate(date)));
        }

        return sb.toString();
    }
}
