package commerce.hello;
import static commerce.hello.domain.product.Category.*;
import commerce.hello.domain.product.Product;
import commerce.hello.domain.product.ProductRepository;
import commerce.hello.domain.product.ProductRepositoryImpl;

public class Main {
    public static void main(String[] args) {
        Product product1 = new Product("Galaxy S24", ELECTRONIC,1200000,"최신 안드로이드 스마트폰",25);
        Product product2 = new Product("iPhone 15", ELECTRONIC,100000,"최신 Apple 스마트폰",2);
        System.out.println(product1);
        System.out.println(product2);
        ProductRepository productRepository = new ProductRepositoryImpl();
        productRepository.save(product1);
        productRepository.save(product2);
        Product byId = productRepository.findById(product1.getId());
        System.out.println(byId);
        System.out.println(product1);
        productRepository.update(product1.getId(), new Product(product1.getName(), product1.getCategory(), 1, "바뀜", 0));
        System.out.println("product1 = " + product1);
        productRepository.delete(product2);

    }
}
