package commerce.hello.service.orderService;

import commerce.hello.domain.member.Grade;
import commerce.hello.domain.order.OrderRepository;
import commerce.hello.domain.product.Category;
import commerce.hello.domain.product.Product;
import commerce.hello.domain.product.ProductRepository;

import java.util.List;
import java.util.Map;

public class GradeBasedOrderService extends StandardOrderService{

    public GradeBasedOrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        super(orderRepository, productRepository);
    }
    public int calculateOrder(Grade grade) {
        int calculationNum= orderRepository.findAll()
                .stream()
                .mapToInt(p -> p.getPrice() * p.getQuantity())
                .sum();
        return (int)(calculationNum-(calculationNum* grade.getDiscountRate()));
    }
}
