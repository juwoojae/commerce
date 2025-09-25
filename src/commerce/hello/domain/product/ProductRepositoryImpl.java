package commerce.hello.domain.product;

import java.util.*;
import java.util.stream.Collectors;

public class ProductRepositoryImpl implements ProductRepository {

    private static final List<Product> store = new ArrayList<>();
    private static long sequence = 0L;

    /**
     * 모든 service 가 ProductRepository 를 공유하므로 싱글톤으로 관리한다
     */
    private static final ProductRepositoryImpl instance = new ProductRepositoryImpl();
    private ProductRepositoryImpl() {
    }
    public static ProductRepositoryImpl getInstance(){
        return instance;
    }

    @Override
    public Product save(Product product) {
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

    /**
     *
     * @param name productRepository 에서 업데이트 될 name -> findByName 으로 findproduct 찾기
     * @param updateProduct 업데이트 할 updateProduct
     * @return findProduct (변환후)
     * 주의 !! update 할때는 이름은 바꾸면 안됩니다
     */
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
