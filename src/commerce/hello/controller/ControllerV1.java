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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

public class ControllerV1 {
    /**
     * 설정객체 생성
     * 설정객체로 각Service 생성 및 의존관계 주입
     */
    AppConfig appConfig = new AppConfig();
    OrderService orderService = appConfig.orderService();
    QueryService queryService = appConfig.queryService();

    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));


    //View.index 를 렌더링 하는 O? 컨트롤러 메서드
    public void indexController() throws IOException {
        try {
            View.index();
            int cmd = Integer.parseInt(bufferedReader.readLine());
            if (0 < cmd && cmd < 4) productsController(Category.codeToCategory(cmd));
            else if (cmd == 0) System.exit(0);
            throw new InvalidateCmdException("잘못된 명령어 입니다");
        }catch (InvalidateCmdException e){
            System.out.println("잘못된 명령어 입니다. 다시 입력해주세요");
            indexController();
        }
    }

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

    public void orderManagerController(int size) {
        View.orderOption(size); //주문관리 출력하기
    }

    public void orderCleanController() {
        View.orderClean();
    }

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

    public void addOrderSuccessfulMessageController(Product product) {
        View.addOrderSuccessfulMessage(product);//성공메세지 출력
    }

    public void addOrderFailedMessageController() {
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

}
