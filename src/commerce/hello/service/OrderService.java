package commerce.hello.service;


import commerce.hello.domain.product.Product;

import java.util.List;
import java.util.List.*;


public interface OrderService {

    void addOrder(Product product);

    int calculateOrder();

    void finalizeOrder();

    List<Product> listOrders();
}
