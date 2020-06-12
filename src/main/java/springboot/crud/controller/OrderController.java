package springboot.crud.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import springboot.crud.data.dto.OrderDto;
import springboot.crud.data.entity.OrderEntity;
import springboot.crud.data.repository.OrderRepository;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Controller
@RequestMapping("order")
public class OrderController {

    private OrderRepository orderRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping({"","/"})
    public ModelAndView showOrderMain(){
        ModelAndView orderMain = new ModelAndView("order/orders");
        orderMain.addObject("Orders", orderRepository.findAll());
        return  orderMain;
    }

    @GetMapping("/find")
    public ModelAndView showFormSearchOrderById() {
       ModelAndView orderSearch = new ModelAndView("order/search-order-page");
       orderSearch.addObject("idOrder", new String());
       return  orderSearch;
    }

    @PostMapping("/find")
    public String searchOrderById(@ModelAttribute("order_id") @NotBlank String orderId,
                                  BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "search-order-page";
        }
        return orderRepository.findById(Integer.valueOf(orderId)).map(orderEntity -> "redirect:/order/show/" + orderId).orElse("error al buscar la orden");
    }

    @GetMapping("/show/{order_id}")
    public ModelAndView showOrderById(@PathVariable ("order_id") String orderId){
        Optional<OrderEntity> maybeOrderEntity = orderRepository.findById(Integer.valueOf(orderId));
        OrderEntity orderEntity = maybeOrderEntity.get();

        ModelAndView modelAndView = new ModelAndView("order/show-order-page");
        modelAndView.addObject("order_id", orderEntity.getId());
        modelAndView.addObject("order_cancel_reason_id", orderEntity.getCancelReason());
        return  modelAndView;
    }

    @GetMapping("/insert")
    public ModelAndView insertPage(){
        ModelAndView insertPage = new ModelAndView("order/insert-order");
        insertPage.addObject("orderDto", new OrderDto());
        return  insertPage;
    }

    @PostMapping("/insert")
    public String insertOrder(@ModelAttribute @Valid OrderDto dto,
                              BindingResult bindingResult ){
        if(bindingResult.hasErrors()){
            return "order/insert-order";
        }

         OrderEntity orderEntity = new OrderEntity();//Creo una nueva entidad para guardar los datos
         orderEntity.setCancelReason(Integer.valueOf(dto.getCancelReason()));//Le asigno los datos ingresados por el usuario
         orderRepository.save(orderEntity);//Guardo la entidad
         return "redirect:/order";
    }

    @GetMapping("/delete/{order_id}")
    public String deleteOrder(@PathVariable("order_id") String orderId){

        Optional<OrderEntity> maybeOrderEntity = orderRepository.findById(Integer.valueOf(orderId));

        if(!maybeOrderEntity.isPresent()){
            return "Error al borrar la orden";
        }

        orderRepository.delete(maybeOrderEntity.get());
        return "redirect:/order";
    }

    @GetMapping("/edit/{order_id}")
    public ModelAndView showEditPage(@PathVariable("order_id") String orderId){

        Optional<OrderEntity> maybeOrderEntity = orderRepository.findById(Integer.valueOf(orderId));
        if(!maybeOrderEntity.isPresent()){
            return new ModelAndView("error");
        }

        OrderEntity orderEntity = maybeOrderEntity.get(); //Traigo toda la entidad
        ModelAndView modelAndView = new ModelAndView("order/edit-order-page");
        modelAndView.addObject("order_id", orderId );
        modelAndView.addObject(new OrderDto(orderEntity.getCancelReason()));
        return modelAndView;
    }

    @PostMapping("/edit/{order_id}")
    public String editOrder(@PathVariable ("order_id") Integer orderId, @ModelAttribute @Valid OrderDto orderDto,
                            BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "Error al editar la orden";
        }

        Optional<OrderEntity> maybeOrderEntity = orderRepository.findById(orderId);
        if(!maybeOrderEntity.isPresent()){
            return "No se encuentra la orden";
        }

        OrderEntity orderEntityToBeModified = maybeOrderEntity.get();
        orderEntityToBeModified.setCancelReason(orderDto.getCancelReason());
        orderRepository.save(orderEntityToBeModified);
        return "redirect:/order";

    }

    @GetMapping("/show-orders")
    public ModelAndView getAllOrders() {

        ModelAndView indexView = new ModelAndView("index");
        indexView.addObject("orders", orderRepository.findAll());

        return indexView;
    }

}
