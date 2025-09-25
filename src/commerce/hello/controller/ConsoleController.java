package commerce.hello.controller;

import commerce.hello.domain.product.Category;
import commerce.hello.domain.product.Product;
import commerce.hello.domain.product.ProductRepository;
import commerce.hello.exception.InvalidateCmdException;
import commerce.hello.exception.OutOfStockException;
import commerce.hello.service.managerSevice.ManagerService;
import commerce.hello.service.orderService.OrderService;
import commerce.hello.service.queryService.QueryService;
import commerce.hello.view.View;
import commerce.hello.web.AppConfig;

import static commerce.hello.domain.product.Category.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import static commerce.hello.view.View.finalizeOrderForm;
import static commerce.hello.view.View.index;

public class ConsoleController {

    static AppConfig appConfig = new AppConfig();
    static OrderService orderService = appConfig.orderService();
    static ManagerService managerService = appConfig.managerService("1q2w3e4r!@");
    static QueryService queryService = appConfig.queryService();

    static ProductRepository productRepository = appConfig.productRepository(); //init 을 위해서 사용
    static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    {
        init();
    }

    //View.index 의 컨트롤러 메서드
    public void indexController() throws IOException {
        index();
        int cmd = Integer.parseInt(bufferedReader.readLine());
        if (0 < cmd && cmd < 4) {
            productsController(Category.codeToCategory(cmd));
        } else if (cmd == 0) {
            System.exit(0);
        } else if (cmd == 6) {
            //관리자 모드 실행
        } else {  //그 이외의 cmd 예외처리
            throw new InvalidateCmdException("잘못된 명령어 입니다");
        }
    }

    //View.products 의 컨트롤러 메서드
    public void productsController(Category model) throws IOException {
        List<Product> products = queryService.listProducts(model);
        View.products(products); //해당 카테고리 리스트 조회
        if (orderService.hasOrder()) { //장바구니에 주문이 하나라도 있는경우
            int size = products.size();
            orderManagerController(size);
            int cmd = Integer.parseInt(bufferedReader.readLine());
            if (0 < cmd && cmd <= size) {
                orderFormController(products.get(cmd - 1)); //선택한 상품 장바구니 추가 퐄으로 이동
            } else if (cmd == 0) {//뒤로가기
                indexController();
            } else if (cmd == size + 1) { //장바구니 확인
                finalizeOrderFormController(orderService.listOrders(), orderService.calculateOrder());
            } else if (cmd == size + 2) { //주문 취소 -> 첫화면으로 돌아가기
                orderCleanController();
                indexController();
            } else {  //그 이외의 cmd 예외처리
                throw new InvalidateCmdException("잘못된 명령어 입니다");
            }
        } else { //장바구니에 주문이 없는경우
            int cmd = Integer.parseInt(bufferedReader.readLine());
            if (0 < cmd && cmd <= products.size()) {
                orderFormController(products.get(cmd - 1));
            } else if (cmd == 0) {//뒤로가기
                indexController();
            } else {  //그 이외의 cmd 예외처리
                throw new InvalidateCmdException("잘못된 명령어 입니다");
            }
        }
    }

    private static void orderManagerController(int size) {
        View.orderManager(size); //주문관리 출력하기
    }

    private static void orderCleanController() {
        View.orderClean();
    }

    //View.orderForm 의 컨트롤러 메서드
    public void orderFormController(Product product) throws IOException {
        View.orderForm(product); //장바구니 폼 출력
        int cmd = Integer.parseInt(bufferedReader.readLine());
        if (cmd == 1) {
            try {
                orderService.addOrder(new Product(product.getName()
                        , product.getCategory()
                        , product.getPrice()
                        , product.getDiscription()
                        , 1)); //상품 장바구니에 추가 (장바구니 추가는 1개씩만 가능함)
                addOrderSuccessfulMessageController(product);
            } catch (OutOfStockException e) {
                addOrderFailedMessageController(); //장바구니 추가 실패 메세지 출력
            }
            productsController(product.getCategory());
        } else if (cmd == 2) productsController(product.getCategory());
        else throw new InvalidateCmdException("잘못된 명령어 입니다");
    }

    private static void addOrderSuccessfulMessageController(Product product) {
        View.addOrderSuccessfulMessage(product);//성공메세지 출력
    }

    private static void addOrderFailedMessageController() {
        View.addOrderFailedMessage();
    }

    public void finalizeOrderFormController(List<Product> model, int price) throws IOException {
        View.finalizeOrderForm(model, price);
        int cmd = Integer.parseInt(bufferedReader.readLine());
        if (cmd == 1) {
            finalizeOrderMessageController(orderService.finalizeOrder(), price);
        } else if (cmd == 2) {
            indexController();//메인으로 돌아가기
        }
    }

    public void finalizeOrderMessageController(Map<String, int[]> model, int price) {
        View.finalizeOrderMessage(model, price);
    }

    public static void init() {
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
