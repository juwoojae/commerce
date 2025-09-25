package commerce.hello;

import commerce.hello.domain.product.Product;
import commerce.hello.domain.product.ProductRepository;
import commerce.hello.domain.product.ProductRepositoryImpl;

import static commerce.hello.domain.product.Category.*;
import static commerce.hello.domain.product.Category.FOOD;

/**
 * 데이터 추가 하기위한 클래스
 *
 */
public class init {
    public static void init() {
        ProductRepository productRepository=ProductRepositoryImpl.getInstance();
        //ELECTRONIC 카테고리
        productRepository.save(new Product("Galaxy S24", ELECTRONIC, 1200000, "최신 안드로이드 스마트폰", 25));
        productRepository.save(new Product("iPhone 15", ELECTRONIC, 1350000, "Apple의 최신 스마트폰", 30));
        productRepository.save(new Product("MacBook Pro", ELECTRONIC, 2400000, "M3 칩셋이 탑재된 노트북", 15));
        productRepository.save(new Product("AirPods Pro", ELECTRONIC, 350000, "노이즈 캔슬링 무선 이어폰", 1));

        // CLOTHE 카테고리
        productRepository.save(new Product("Nike Air Max 270", CLOTHES, 180000, "편안한 착화감의 러닝화", 50));
        productRepository.save(new Product("Adidas Hoodie", CLOTHES, 85000, "따뜻하고 스타일리시한 후드티", 40));
        productRepository.save(new Product("Levi's 501 Jeans", CLOTHES, 120000, "클래식한 데님 팬츠", 30));
        productRepository.save(new Product("Uniqlo Ultra Light Jacket", CLOTHES, 99000, "가벼운 경량 재킷", 25));

        // FOOD 카테고리
        productRepository.save(new Product("Banana", FOOD, 1500, "신선한 바나나 한 송이", 100));
        productRepository.save(new Product("Cheddar Cheese", FOOD, 7500, "풍미 깊은 체다 치즈", 20));
        productRepository.save(new Product("Ramen Pack", FOOD, 6000, "간편하게 즐기는 라면 세트", 50));
        productRepository.save(new Product("Organic Apple", FOOD, 3000, "유기농 사과 1kg", 30));
    }
}
