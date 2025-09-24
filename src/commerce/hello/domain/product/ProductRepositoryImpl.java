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
    public Product findByName(String name) {
        return store.stream()
                .filter(product -> product.getName().equals(name))
                .findFirst()  // Optional<Product> 반환
                .orElse(null);  // 못 찾으면 null 반환
    }

    @Override
    public List<Product> findAll(Category category) {
        return store.stream()
                .filter(product -> product.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    @Override
    public Product update(String name, Product updateProduct) {
        Product findProduct = findByName(name);
        findProduct.setDiscription(updateProduct.getDiscription());
        findProduct.setPrice(updateProduct.getPrice());
        findProduct.setQuantity(updateProduct.getQuantity());
        return findProduct;
    }

    @Override
    public Product delete(String name) {
        Product findProduct=findByName(name);
        store.remove(findByName(name));
        return findProduct;
    }

}
