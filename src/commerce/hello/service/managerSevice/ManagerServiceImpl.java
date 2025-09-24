package commerce.hello.service.managerSevice;

import commerce.hello.domain.product.Category;
import commerce.hello.domain.product.Product;
import commerce.hello.domain.product.ProductRepository;

import java.util.List;

public class ManagerServiceImpl implements ManagerService {

    private final ProductRepository productRepository;

    public ManagerServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Override
    public Product register(Product product) {
        productRepository.save(product);
        return product;
    }
    @Override
    public Product remove(String name){
        return productRepository.delete(name);
    }
    @Override
    public Product update(String name,Product product){
        return productRepository.update(name, product);
    }
    @Override
    public List<Product> ListProducts(Category category){
        return productRepository.findAll(category);
    }
}

