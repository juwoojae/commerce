package commerce.hello.service.queryService;

import commerce.hello.domain.product.Category;
import commerce.hello.domain.product.Product;

import java.util.*;

public interface QueryService {
    List<Product> findProductsOver(Category category);

    List<Product> findProductsUnder(Category category);
}
