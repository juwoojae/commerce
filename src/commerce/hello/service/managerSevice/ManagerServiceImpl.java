package commerce.hello.service.managerSevice;

import commerce.hello.authentication.Authentication;
import commerce.hello.domain.product.Product;
import commerce.hello.domain.product.ProductRepository;

public class ManagerServiceImpl {

    private final ProductRepository productRepository;
    private final Authentication authentication;

    public ManagerServiceImpl(Authentication authentication, ProductRepository productRepository) {
        this.authentication = authentication;
        this.productRepository = productRepository;
    }

    public Product register(Product product){
        productRepository.save(product);
        return product;
    }
    public Product findProduct(long id){
        return productRepository.findById(id);
    }


}

