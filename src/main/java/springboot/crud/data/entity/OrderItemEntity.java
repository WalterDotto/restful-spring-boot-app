package springboot.crud.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_items")
public class OrderItemEntity {

  @Id
  @Column(name = "order_item_id", insertable = false, updatable = false)
  private Integer id;

  @Column(name = "order_id", insertable = false, updatable = false)
  private Integer orderId;

  @Column(name = "product_id")
  private Integer productCode;

}