package commerce.hello.controller;

import commerce.hello.domain.member.Grade;
import commerce.hello.domain.product.Category;
import commerce.hello.domain.product.Product;
import commerce.hello.exception.InvalidateCmdException;
import commerce.hello.service.orderService.GradeBasedOrderService;
import commerce.hello.view.View;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ControllerV3 extends ControllerV2 {

    GradeBasedOrderService gradeBasedOrderService = (GradeBasedOrderService) orderService; //다운캐스팅 한후, gradeBasedOrderService 의 고유 메서드 사용

    @Override
    public void indexController() throws IOException {
        try {
            View.index();
            int cmd = Integer.parseInt(bufferedReader.readLine());
            if (0 < cmd && cmd < 4) productsOptionController(Category.codeToCategory(cmd));
            else if (cmd == 0) System.exit(0);
            else if (cmd == 6) authenticateManagerFormController();
            else throw new InvalidateCmdException("잘못된 명령어 입니다");
        } catch (InvalidateCmdException e) {
            System.out.println("잘못된 명령어 입니다. 다시 입력해주세요");
            indexController();
        }
    }

    private void productsOptionController(Category category) throws IOException {
        View.productsOption(category);
        int cmd = Integer.parseInt(bufferedReader.readLine());
        if (cmd == 1) productsController(category); //(카테고리) 번호를 카테고리 로 전환 1. 전자기기, 2.옷, 3.음식
        else if (cmd == 2) filterProductsUnderController(queryService.findProductsUnder(category));
        else if (cmd == 3) filterProductsOverController(queryService.findProductsOver(category));
        else if (cmd == 0) { //이전화면으로
            indexController();
        }
    }

    private void filterProductsUnderController(List<Product> model) throws IOException {
        View.filterProductsUnder(model);
        if (orderService.hasOrder()) { //장바구니에 주문이 하나라도 있는경우 -> orderManagerController 를 호출
            int size = model.size();
            orderManagerController(size);
            int cmd = Integer.parseInt(bufferedReader.readLine());
            if (0 < cmd && cmd <= size) {
                orderFormController(model.get(cmd - 1)); //선택한 상품 장바구니 추가 퐄으로 이동
            } else if (cmd == 0) {//뒤로가기
                productsOptionController(model.get(0).getCategory());
            } else if (cmd == size + 1) { //장바구니 확인
                finalizeOrderFormController(orderService.listOrders());
            } else if (cmd == size + 2) { //주문 취소 -> 첫화면으로 돌아가기
                orderCleanController();
                productsOptionController(model.get(0).getCategory());
            } else {  //그 이외의 cmd 예외처리
                throw new InvalidateCmdException("잘못된 명령어 입니다");
            }
        } else { //장바구니에 주문이 없는경우
            int cmd = Integer.parseInt(bufferedReader.readLine());
            if (0 < cmd && cmd <= model.size()) {
                orderFormController(model.get(cmd - 1));
                filterProductsUnderController(model);
            } else if (cmd == 0) {//뒤로가기
                productsOptionController(model.get(0).getCategory());
            } else {  //그 이외의 cmd 예외처리
                throw new InvalidateCmdException("잘못된 명령어 입니다");
            }
        }
    }

    private void filterProductsOverController(List<Product> model) throws IOException {
        View.filterProductsOver(model);
        if (orderService.hasOrder()) { //장바구니에 주문이 하나라도 있는경우
            int size = model.size();
            orderManagerController(size);
            int cmd = Integer.parseInt(bufferedReader.readLine());
            if (0 < cmd && cmd <= size) {
                orderFormController(model.get(cmd - 1)); //선택한 상품 장바구니 추가 퐄으로 이동
            } else if (cmd == 0) {//뒤로가기
                indexController();
            } else if (cmd == size + 1) { //장바구니 확인
                finalizeOrderFormController(orderService.listOrders());
            } else if (cmd == size + 2) { //주문 취소 -> 첫화면으로 돌아가기
                orderCleanController();
                indexController();
            } else {  //그 이외의 cmd 예외처리
                throw new InvalidateCmdException("잘못된 명령어 입니다");
            }
        } else { //장바구니에 주문이 없는경우
            int cmd = Integer.parseInt(bufferedReader.readLine());
            if (0 < cmd && cmd <= model.size()) {
                orderFormController(model.get(cmd - 1));
                filterProductsOverController(model);
            } else if (cmd == 0) {//뒤로가기
                indexController();
            } else {  //그 이외의 cmd 예외처리
                throw new InvalidateCmdException("잘못된 명령어 입니다");
            }
        }
    }

    @Override
    public void finalizeOrderFormController(List<Product> model) throws IOException {
        int price = orderService.calculateOrder();//장바구니 총액 계산
        System.out.println("ControllerV3.finalizeOrderFormController");
        System.out.println(orderService.listOrders());
        View.finalizeOrderForm(model, price);
        int cmd = Integer.parseInt(bufferedReader.readLine());
        if (cmd == 1) {
            gradeFormController();
        } else if (cmd == 2) {
            indexController();//메인으로 돌아가기
        }
    }

    /**
     * Grade 정보 입력받은후, ,model, grade 와함꼐
     * finalizeOrderMessageController 를 호출한다
     *
     * @throws IOException
     */
    public void gradeFormController() throws IOException {
        try {
            View.gradeForm();
            int cmd = Integer.parseInt(bufferedReader.readLine());
            if (0 < cmd && cmd < 5) {
                //StandardOrderService 의 메서드 .calculateOrder()
                int oldPrice = gradeBasedOrderService.calculateOrder();
                //GradeBassedOrderService 의 메서드 .calculateOrder()
                int newPrice = gradeBasedOrderService.calculateOrder(Grade.codeToGrade(cmd));
                Map<String, int[]> finalizeOrder = gradeBasedOrderService.finalizeOrder();//여기가 비어있다는건 장바구니자체가 empty 한상황
                finalizeOrderMessageController(finalizeOrder,oldPrice,newPrice, Grade.codeToGrade(cmd));
                indexController();
            } else throw new InvalidateCmdException("잘못된 명령어 입니다");
        } catch (InvalidateCmdException e) {
            System.out.println("잘못된 명령어 입니다. 다시 입력해주세요");
            gradeFormController();
        }
    }


    /**
     * finalizeOrderMessageController 의 메서드 오버로딩
     * V1 (Map model) 매개변수 - finalizeOrderMessage(Map<String, int[]> result, int price)
     * V3 (Map model, Grade grade) - finalizeOrderMessage(Map<String, int[]> result, int standardPrice, int gradeBasedPrice, Grade grade)
     *
     * @param model 아 이제 왜 틀렸는지 알거같은 model
     */
    public void finalizeOrderMessageController(Map<String, int[]> model, int oldPrice, int newPrice, Grade grade) {
        //초기화한 이후에 받으니 당연히 없는거 같음
        System.out.println();
        View.finalizeOrderMessage(model, oldPrice, newPrice, grade);
    }

}
