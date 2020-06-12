package springboot.crud.data.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "orders")
public class OrderEntity {

    public OrderEntity() {}

    public OrderEntity(Integer id, Date createdDate, Integer cancelReason) {
        this.id = id;
        this.createdDate = createdDate;
        this.cancelReason = cancelReason;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", insertable = false, updatable = false)
    private Integer id;


    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "cancel_reason")
    private Integer cancelReason;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(Integer cancelReason) {
        this.cancelReason = cancelReason;
    }
}
