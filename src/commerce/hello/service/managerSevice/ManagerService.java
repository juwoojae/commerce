package commerce.hello.service.managerSevice;

import commerce.hello.domain.product.Category;
import commerce.hello.domain.product.Product;

import java.util.List;

/**
 * 관리자 서비스
 * MemberRepository 에 상품을 추가, 수정, 저장 기능
 */

public interface ManagerService  {
    Product register(Product product);

    Product remove(String name);

    Product update(String name, Product product);

    List<Product> ListProducts(Category category);
}
