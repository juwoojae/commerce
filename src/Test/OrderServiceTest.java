package Test;

import commerce.hello.OutOfStockException;
import commerce.hello.domain.order.OrderRepository;
import commerce.hello.domain.order.OrderRepositoryImpl;
import commerce.hello.domain.product.Product;
import static org.junit.jupiter.api.Assertions.*;

import commerce.hello.domain.product.ProductRepository;
import commerce.hello.domain.product.ProductRepositoryImpl;
import commerce.hello.service.OrderService;
import commerce.hello.service.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static commerce.hello.domain.product.Category.ELECTRONIC;

public class OrderServiceTest {

    ProductRepository productRepository = new ProductRepositoryImpl();
    OrderRepository orderRepository = new OrderRepositoryImpl();
    OrderService orderService = new OrderServiceImpl(orderRepository,productRepository);

    @BeforeEach
    void beforEach(){
        productRepository.save(new Product("Galaxy S24", ELECTRONIC,1200000,"최신 안드로이드 스마트폰",25));
        productRepository.save(new Product("iPhone 15", ELECTRONIC, 1350000, "Apple의 최신 스마트폰", 30));
        productRepository.save(new Product("MacBook Pro", ELECTRONIC, 2400000, "M3 칩셋이 탑재된 노트북", 15));
        productRepository.save(new Product("AirPods Pro", ELECTRONIC, 350000, "노이즈 캔슬링 무선 이어폰", 1));
    }

    @Test
    void OrderRepositoryTest(){

        Product product1 = new Product("Galaxy S24", ELECTRONIC,1200000,"최신 안드로이드 스마트폰",25);
        Product product2 = new Product("iPhone 15", ELECTRONIC,100000,"최신 Apple 스마트폰",2);
        OrderRepository orderRepository = new OrderRepositoryImpl();
        orderRepository.save(product1);
        orderRepository.save(product2);

        int product1Quantity = product1.getQuantity();
        assertEquals(26, ++product1Quantity);

        assertEquals(orderRepository.findByName("Galaxy S24"), product1);
        assertEquals(orderRepository.findByName("Galaxy S2"),null);

        assertFalse(orderRepository.storeIsEmpty());

        assertEquals(2,orderRepository.findAll().size());

        orderRepository.clearStore();
        assertEquals(0,orderRepository.findAll().size());
    }
    @Test
   void addOrderTest(){
        //장바구니에 최초 추가하는 경우
        orderService.addOrder(new Product("Galaxy S24",ELECTRONIC,1200000,"최신 안드로이드 스마트폰",1));
        assertEquals(1,orderRepository.findAll().size());
        assertEquals(1,orderRepository.findByName("Galaxy S24").getQuantity());
        System.out.println(productRepository.findAll(ELECTRONIC));
        orderService.addOrder(new Product("Galaxy S24",ELECTRONIC,1200000,"최신 안드로이드 스마트폰",1));
        //장바구니에 있는상품을 또 추가하는 경우
        assertEquals(2,orderRepository.findByName("Galaxy S24").getQuantity());
        //
        orderService.addOrder(new Product("AirPods Pro", ELECTRONIC, 350000, "노이즈 캔슬링 무선 이어폰", 1));
        //재고수량을 넘어가는경우
        //orderService.addOrder(new Product("AirPods Pro", ELECTRONIC, 350000, "노이즈 캔슬링 무선 이어폰", 1));
        assertThrows(OutOfStockException.class, ()->{orderService.addOrder(new Product("AirPods Pro", ELECTRONIC, 350000, "노이즈 캔슬링 무선 이어폰", 1));});
        assertEquals(1,orderRepository.findByName("AirPods Pro").getQuantity());
    }
    @Test
    void finalizeOrderTest(){
        orderService.addOrder(new Product("Galaxy S24",ELECTRONIC,1200000,"최신 안드로이드 스마트폰",1));
        orderService.addOrder(new Product("Galaxy S24",ELECTRONIC,1200000,"최신 안드로이드 스마트폰",1));
        orderService.addOrder(new Product("iPhone 15", ELECTRONIC, 1350000, "Apple의 최신 스마트폰", 1));
        assertEquals(3750000,orderService.calculateOrder());
        orderService.finalizeOrder(); //주문완료
        assertEquals(23,productRepository.findByName("Galaxy S24").getQuantity());
    }
}
