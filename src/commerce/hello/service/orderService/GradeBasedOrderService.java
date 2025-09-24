package commerce.hello.service.orderService;

import commerce.hello.domain.product.Product;

import java.util.List;

public class GradeBasedOrderService implements OrderService{
    @Override
    public void addOrder(Product product) {

    }

    @Override
    public int calculateOrder() {
        return 0;
    }

    @Override
    public void finalizeOrder() {

    }

    @Override
    public boolean hasOrder() {
        return false;
    }

    @Override
    public List<Product> listOrders() {
        return List.of();
    }
}
