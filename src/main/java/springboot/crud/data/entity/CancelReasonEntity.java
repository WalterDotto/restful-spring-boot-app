package springboot.crud.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cancel_reasons")
public class CancelReasonEntity {

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name = "reason_id", insertable = false, updatable = false)
  private Integer id;

  @Column(name = "description")
  private String description;

}
