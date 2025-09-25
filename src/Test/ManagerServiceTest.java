package Test;

import commerce.hello.SecurityException;
import commerce.hello.domain.order.OrderRepository;
import commerce.hello.domain.order.OrderRepositoryImpl;
import commerce.hello.domain.product.Product;
import commerce.hello.domain.product.ProductRepository;
import commerce.hello.domain.product.ProductRepositoryImpl;
import commerce.hello.service.managerSevice.ManagerService;
import commerce.hello.service.managerSevice.ManagerServiceImpl;
import commerce.hello.service.managerSevice.ProxyManagerService;
import commerce.hello.service.orderService.OrderService;
import commerce.hello.service.orderService.StandardOrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static commerce.hello.domain.product.Category.ELECTRONIC;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    void managerServiceTest(){
        //로그인 검증하기
        assertThrows(SecurityException.class,()->{new ProxyManagerService(new ManagerServiceImpl(productRepository),"1233");});
        ManagerService managerService = new ProxyManagerService(new ManagerServiceImpl(productRepository),"1q2w3e4r!@");
        //register 검증
        Product product = managerService.register(new Product("new1", ELECTRONIC, 1, "추가1", 1));
        managerService.register(new Product("new2", ELECTRONIC, 1, "추가2", 1));
        assertEquals(product, productRepository.findByName("new1"));
        //remove 검증
        managerService.remove("new1");
        assertEquals(null,productRepository.findByName("new1"));
        //update 검증
        Product product1 = managerService.update("new2", new Product("new2", ELECTRONIC, 100, "추가3", 1));
        System.out.println(product1);
        assertEquals(100,productRepository.findByName("new2").getPrice());
    }
}
