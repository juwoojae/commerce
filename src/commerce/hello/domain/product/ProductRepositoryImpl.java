package commerce.hello.domain.product;

import java.util.*;
import java.util.stream.Collectors;

public class ProductRepositoryImpl implements ProductRepository {

    private static final List<Product> store = new ArrayList<>();
    private static long sequence = 0L;

    @Override
    public Product save(Product product) {
        product.setId(++sequence);
        store.add(product);
        return product;
    }

    @Override
    public Product findById(long id) {
        return store.stream()
                .filter(product -> (product.getId() == id))
                .collect(Collectors.toList()).getFirst();
    }

    @Override
    public List<Product> findAll(Category category) {
        return store.stream()
                .filter(product -> product.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    @Override
    public Product update(long productId, Product updateProduct) {
        Product findProduct = findById(productId);
        findProduct.setDiscription(updateProduct.getDiscription());
        findProduct.setPrice(updateProduct.getPrice());
        findProduct.setQuantity(updateProduct.getQuantity());
        return findProduct;
    }

    @Override
    public Product delete(Product product) {
        store.remove(product);
        return product;
    }

}
