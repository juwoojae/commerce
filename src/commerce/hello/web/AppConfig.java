package commerce.hello.web;

import commerce.hello.domain.member.MemberRepository;
import commerce.hello.domain.order.OrderRepository;
import commerce.hello.domain.order.OrderRepositoryImpl;
import commerce.hello.domain.product.ProductRepository;
import commerce.hello.domain.product.ProductRepositoryImpl;
import commerce.hello.service.managerSevice.ManagerService;
import commerce.hello.service.managerSevice.ManagerServiceImpl;
import commerce.hello.service.managerSevice.ProxyManagerService;
import commerce.hello.service.orderService.GradeBasedOrderService;
import commerce.hello.service.orderService.OrderService;
import commerce.hello.service.orderService.StandardOrderService;
import commerce.hello.service.queryService.QueryService;
import commerce.hello.service.queryService.QueryServiceImpl;

/**
 * 의존관계 주입을 해주는 IoC 컨테이너
 */
public class AppConfig {
    public ProductRepository productRepository(){
        return ProductRepositoryImpl.getInstance();
    }
    public OrderRepository orderRepository(){
        return new OrderRepositoryImpl();
    }
    public OrderService orderService(){
        //return new StandardOrderService(orderRepository, productRepository);
        return new GradeBasedOrderService(orderRepository(), productRepository());
    }
    public ManagerService managerService(String password){
        return new ProxyManagerService(new ManagerServiceImpl(productRepository()), password);
    }
    public QueryService queryService(){
        return new QueryServiceImpl(productRepository());
    }

}
