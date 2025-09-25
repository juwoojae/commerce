package commerce.hello.service.queryService;

import commerce.hello.domain.product.Category;
import commerce.hello.domain.product.Product;
import commerce.hello.domain.product.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

public class QueryServiceImpl implements QueryService{

    ProductRepository productRepository ;
    public QueryServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * 100만원 이상 가격의 상품 필터링
     * @param category
     * @return 100만원 이상 가격의 상품 리스트
     */
    @Override
    public List<Product> findProductsOver(Category category) {
        return productRepository.findAll(category)
                .stream()
                .filter(product -> product.getPrice() >= 1000000)
                .collect(Collectors.toList());
    }

    /**
     * 100만원 미만 가격의 상품 필터링
     * @param category
     * @return 100만원 미만 가격의 상품 리스트
     */
    @Override
    public List<Product> findProductsUnder(Category category) {
        return productRepository.findAll(category)
                .stream()
                .filter(product -> product.getPrice() < 1000000)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> listProducts(Category category){
        return productRepository.findAll(category);
    }
}
