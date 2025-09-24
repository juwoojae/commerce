package commerce.hello.service.orderService;


import commerce.hello.domain.product.Product;

import java.util.List;


public interface OrderService {

    void addOrder(Product product);

    int calculateOrder();

    void finalizeOrder();

    boolean hasOrder();

    List<Product> listOrders();
}
