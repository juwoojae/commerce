package commerce.hello.controller;

import commerce.hello.domain.product.Product;
import commerce.hello.domain.product.ProductRepository;
import commerce.hello.service.managerSevice.ManagerService;
import commerce.hello.service.orderService.OrderService;
import commerce.hello.service.queryService.QueryService;
import commerce.hello.web.AppConfig;
import static commerce.hello.domain.product.Category.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static commerce.hello.view.View.index;
import static commerce.hello.view.View.product;

public class ConsoleController {

static AppConfig appConfig = new AppConfig();
static OrderService orderService = appConfig.orderService();
static ManagerService managerService = appConfig.managerService("1q2w3e4r!@");
static QueryService queryService = appConfig.queryService();

static ProductRepository productRepository = appConfig.productRepository(); //init 을 위해서 사용
static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));


public static void main(String[] args) throws IOException {
    init();
    index();
    int cmd = Integer.parseInt(bufferedReader.readLine());
    switch (cmd) {
        case 1:
            product(ELECTRONIC);
            cmd = Integer.parseInt(bufferedReader.readLine());

            break; // switch 블록 빠져나오기
        case 2:
            product(CLOTHES);
            break;
        case 3:
            product(FOOD);

        default:
            System.exit(1);
            // 어느 case와도 일치하지 않을 때 실행
    }
    int cmd = Integer.parseInt(bufferedReader.readLine());

}

    public static void init(){
        //ELECTRONIC 카테고리
        productRepository.save(new Product("Galaxy S24", ELECTRONIC,1200000,"최신 안드로이드 스마트폰",25));
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
