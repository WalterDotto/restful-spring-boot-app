package springboot.crud.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {

    @NotNull
    private Integer orderId;

    @NotNull
    private Integer productCode;

}
