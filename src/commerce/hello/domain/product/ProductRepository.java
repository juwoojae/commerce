package commerce.hello.domain.product;

import java.util.List;

/**
 * 과제의 CommerceSystem 클래스.
 * product 를 관리하는 저장소 필드, 저장소 CRUD 메서드
 */
public interface ProductRepository {
    Product save(Product product);

    Product findByName(String name);

    List<Product> findAll(Category category);

    Product update(String name, Product updateProduct);

    Product delete(String name);

}
