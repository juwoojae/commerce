package Test;

import commerce.hello.domain.order.OrderRepository;
import commerce.hello.domain.order.OrderRepositoryImpl;
import commerce.hello.domain.product.Product;
import commerce.hello.domain.product.ProductRepository;
import commerce.hello.domain.product.ProductRepositoryImpl;
import commerce.hello.service.orderService.OrderService;
import commerce.hello.service.orderService.StandardOrderService;
import org.junit.jupiter.api.BeforeEach;

import static commerce.hello.domain.product.Category.ELECTRONIC;

public class ManagerServiceTest {

    ProductRepository productRepository = new ProductRepositoryImpl();
    OrderRepository orderRepository = new OrderRepositoryImpl();
    OrderService orderService = new StandardOrderService(orderRepository,productRepository);

    @BeforeEach
    void beforEach(){
        productRepository.save(new Product("Galaxy S24", ELECTRONIC,1200000,"최신 안드로이드 스마트폰",25));
        productRepository.save(new Product("iPhone 15", ELECTRONIC, 1350000, "Apple의 최신 스마트폰", 30));
        productRepository.save(new Product("MacBook Pro", ELECTRONIC, 2400000, "M3 칩셋이 탑재된 노트북", 15));
        productRepository.save(new Product("AirPods Pro", ELECTRONIC, 350000, "노이즈 캔슬링 무선 이어폰", 1));
    }


}
