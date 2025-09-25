package test;

import commerce.hello.domain.order.OrderRepository;
import commerce.hello.domain.order.OrderRepositoryImpl;
import commerce.hello.domain.product.Product;
import commerce.hello.domain.product.ProductRepository;
import commerce.hello.domain.product.ProductRepositoryImpl;
import commerce.hello.exception.OutOfStockException;
import commerce.hello.exception.SecurityException;
import commerce.hello.service.managerSevice.ManagerService;
import commerce.hello.service.managerSevice.ManagerServiceImpl;
import commerce.hello.service.managerSevice.ProxyManagerService;
import commerce.hello.service.orderService.GradeBasedOrderService;
import commerce.hello.service.orderService.OrderService;
import commerce.hello.service.orderService.StandardOrderService;
import commerce.hello.service.queryService.QueryService;
import commerce.hello.web.AppConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static commerce.hello.domain.member.Grade.GOLD;
import static commerce.hello.domain.product.Category.ELECTRONIC;
import static org.junit.jupiter.api.Assertions.*;

public class AppConfigTest {

    AppConfig appConfig = new AppConfig();
    ProductRepository productRepository = appConfig.productRepository();
    OrderRepository orderRepository = appConfig.orderRepository();
    OrderService orderService = appConfig.orderService();
    ManagerService managerService = appConfig.managerService("1q2w3e4r!@");
    QueryService queryService = appConfig.queryService();

    @BeforeEach
    void beforEach(){
        productRepository.save(new Product("Galaxy S24", ELECTRONIC,1200000,"최신 안드로이드 스마트폰",25));
        productRepository.save(new Product("iPhone 15", ELECTRONIC, 1350000, "Apple의 최신 스마트폰", 30));
        productRepository.save(new Product("MacBook Pro", ELECTRONIC, 2400000, "M3 칩셋이 탑재된 노트북", 15));
        productRepository.save(new Product("AirPods Pro", ELECTRONIC, 350000, "노이즈 캔슬링 무선 이어폰", 1));
    }

    @Test
    void appConfigTest(){
        orderService.addOrder(new Product("Galaxy S24",ELECTRONIC,1200000,"최신 안드로이드 스마트폰",1));
        assertEquals(1,orderRepository.findAll().size());
        assertEquals(1,orderRepository.findByName("Galaxy S24").getQuantity());
        System.out.println(productRepository.findAll(ELECTRONIC));
        this.orderService.addOrder(new Product("Galaxy S24",ELECTRONIC,1200000,"최신 안드로이드 스마트폰",1));
        //장바구니에 있는상품을 또 추가하는 경우
        assertEquals(2,orderRepository.findByName("Galaxy S24").getQuantity());
        //
        this.orderService.addOrder(new Product("AirPods Pro", ELECTRONIC, 350000, "노이즈 캔슬링 무선 이어폰", 1));
        //재고수량을 넘어가는경우
        //orderService.addOrder(new Product("AirPods Pro", ELECTRONIC, 350000, "노이즈 캔슬링 무선 이어폰", 1));
        assertThrows(OutOfStockException.class, ()->{
            this.orderService.addOrder(new Product("AirPods Pro", ELECTRONIC, 350000, "노이즈 캔슬링 무선 이어폰", 1));});
        assertEquals(1,orderRepository.findByName("AirPods Pro").getQuantity());
        int calculateOrder = orderService.calculateOrder();
        GradeBasedOrderService gradeBasedOrderService = (GradeBasedOrderService) orderService;
        int calculateOrder1 = gradeBasedOrderService.calculateOrder(GOLD);
    }
    @Test
    void finalizeOrderTest(){
        orderService.addOrder(new Product("Galaxy S24",ELECTRONIC,1200000,"최신 안드로이드 스마트폰",1));
        orderService.addOrder(new Product("Galaxy S24",ELECTRONIC,1200000,"최신 안드로이드 스마트폰",1));
        orderService.addOrder(new Product("iPhone 15", ELECTRONIC, 1350000, "Apple의 최신 스마트폰", 1));
        assertEquals(3750000,orderService.calculateOrder());
        Map<String,int[]> finalizeOrder = orderService.finalizeOrder(); //주문완료
        for (String string : finalizeOrder.keySet()) {
            System.out.println(string+" "+ finalizeOrder.get(string)[0] +"->"+finalizeOrder.get(string)[1]);
        }

        assertEquals(23,productRepository.findByName("Galaxy S24").getQuantity());
        assertFalse(orderService.hasOrder());
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
    @Test
    void quaryServiceTest(){
        System.out.println(queryService.listProducts(ELECTRONIC));
        System.out.println(queryService.listProducts(ELECTRONIC).size());
        System.out.println(queryService.findProductsOver(ELECTRONIC));
        System.out.println(queryService.findProductsUnder(ELECTRONIC));

    }
}
