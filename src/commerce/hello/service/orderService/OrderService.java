package commerce.hello.service.orderService;


import commerce.hello.domain.product.Product;

import java.util.List;
import java.util.Map;


public interface OrderService {

    void addOrder(Product product);

    int calculateOrder();

    Map<String,int []> finalizeOrder();

    boolean hasOrder();

    List<Product> listOrders();
}
