package commerce.hello.domain.order;

import commerce.hello.domain.product.Category;
import commerce.hello.domain.product.Product;

import java.util.List;

/**
 * 클라이언트의 장바구니 역할을 하는 저장소
 * 주문(order) 내용을 저장, 취소 , 조회를 통한 관리
 */
public interface OrderRepository {

    Product save(Product product);

    Product findByName(String name);

    List<Product> findAll();

    void clearStore();

    boolean storeIsEmpty();

}
