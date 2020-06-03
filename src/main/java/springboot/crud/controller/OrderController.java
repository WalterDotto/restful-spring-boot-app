package springboot.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import springboot.crud.data.entity.OrderEntity;
import springboot.crud.data.repository.OrderRepository;

import java.util.List;

@RestController
public class OrderController {

    private OrderRepository orderRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @GetMapping("/order")
    public List<OrderEntity> getAllOrdersPlain() {

        return orderRepository.findAll();
    }

    @GetMapping("/show-orders")
    public ModelAndView getAllOrders() {


        ModelAndView indexView = new ModelAndView("index");
        indexView.addObject("orders", orderRepository.findAll());

        return indexView;

    }

}
