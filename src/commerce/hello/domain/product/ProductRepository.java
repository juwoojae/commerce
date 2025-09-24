package commerce.hello.domain.product;

import java.util.List;

public interface ProductRepository {

    Product save(Product product);

    Product findById(long idx);

    List<Product> findAll(Category category);

    Product update(long productId, Product updateProduct);

    Product delete(Product product);

}
