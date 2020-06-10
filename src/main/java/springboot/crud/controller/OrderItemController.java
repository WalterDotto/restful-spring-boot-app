package springboot.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import springboot.crud.data.dto.CancelReasonDto;
import springboot.crud.data.dto.IdDto;
import springboot.crud.data.dto.OrderItemDto;
import springboot.crud.data.entity.OrderItemEntity;
import springboot.crud.data.repository.OrderItemRepository;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Optional;


@Controller
@RequestMapping("order-item")
public class OrderItemController {

    private OrderItemRepository orderItemRepository;


    @Autowired
    public OrderItemController(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @GetMapping({"", "/"})
    @ResponseBody
    public ModelAndView showCancelReasonsMain() {

        ModelAndView modelAndView = new ModelAndView("order-item/main");
        modelAndView.addObject("orderItems", orderItemRepository.findAll());

        return modelAndView;
    }

    @GetMapping("/find")
    public ModelAndView showFindByIdPage() {

        ModelAndView modelAndView = new ModelAndView("order-item/find-step-1-page");

        modelAndView.addObject("idDto", new IdDto());
        return modelAndView;
    }


    @PostMapping("/find")
    public String findReasonById(@Valid @ModelAttribute IdDto idDto,
                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "order-item/find-step-1-page";
        }

        Optional<OrderItemEntity> maybeOrderItemEntity = orderItemRepository.findById(Integer.valueOf(idDto.getId()));

        if (!maybeOrderItemEntity.isPresent()) {
            return "error";
        }

        return "redirect:/order-item/show/" + idDto.getId();
    }

    @GetMapping("/show/{order_item_id}")
    public ModelAndView showFindByIdPage(@PathVariable("order_item_id") String orderItemId) {

        Optional<OrderItemEntity> maybeOrderItemEntity = orderItemRepository.findById(Integer.valueOf(orderItemId));

        OrderItemEntity orderItemEntity = maybeOrderItemEntity.get();

        ModelAndView modelAndView = new ModelAndView("order-item/find-step-2-page");
        modelAndView.addObject("order_item_id", orderItemEntity.getId());
        modelAndView.addObject("order_id", orderItemEntity.getOrderId());
        modelAndView.addObject("product_code", orderItemEntity.getProductCode());

        return modelAndView;
    }


    @GetMapping("/insert")
    public ModelAndView showInsertPage() {

        ModelAndView modelAndView = new ModelAndView("cancel-reason/insert-page");
        modelAndView.addObject("orderItemDto", new IdDto());
        return modelAndView;
    }

    @PostMapping("/insert")
    public String insertReason(@ModelAttribute @Valid OrderItemDto dto,
                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "cancel-reason/insert-page";
        }

        OrderItemEntity newOrderItemEntity = new OrderItemEntity();
        newOrderItemEntity.setOrderId(dto.getOrderId());
        newOrderItemEntity.setProductCode(dto.getProductCode());

        orderItemRepository.save(newOrderItemEntity);

        return "redirect:/order-item";

    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditPage(@PathVariable("id") int orderItemId) {

        Optional<OrderItemEntity> maybeOrderItemEntity = orderItemRepository.findById(orderItemId);

        if (!maybeOrderItemEntity.isPresent()) {
            return new ModelAndView("error");
        }

        OrderItemEntity orderItemEntity = maybeOrderItemEntity.get();

        ModelAndView modelAndView = new ModelAndView("order-item/edit-page");

        modelAndView.addObject("order_item_id", orderItemId);
        modelAndView.addObject(new OrderItemDto(orderItemEntity.getOrderId(), orderItemEntity.getProductCode()));

        return modelAndView;
    }

    @PostMapping("/edit/{id}")
    public String editReason(@PathVariable("id") int reasonId, @ModelAttribute @Valid OrderItemDto dto,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "order-item/edit-page";
        }

        Optional<OrderItemEntity> maybeCancelReasonEntity = orderItemRepository.findById(reasonId);

        if (!maybeCancelReasonEntity.isPresent()) {
            return "error";
        }

        OrderItemEntity orderItemEntityToBeModified = maybeCancelReasonEntity.get();

        orderItemEntityToBeModified.setOrderId(dto.getOrderId());
        orderItemEntityToBeModified.setProductCode(dto.getProductCode());

        orderItemRepository.save(orderItemEntityToBeModified);

        return "redirect:/order-item";

    }


    @GetMapping("/delete/{reason_id}")
    public String deleteReason(@PathVariable("reason_id") int reasonId) {

        Optional<OrderItemEntity> maybeCancelReasonEntity = orderItemRepository.findById(reasonId);

        if (!maybeCancelReasonEntity.isPresent()) {
            return "error";
        }

        orderItemRepository.delete(maybeCancelReasonEntity.get());

        return "redirect:/order-item";
    }


}
