package commerce.hello.view;

import commerce.hello.domain.member.Grade;
import commerce.hello.domain.product.Category;
import commerce.hello.domain.product.Product;
import commerce.hello.service.queryService.QueryServiceImpl;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public class View {
    /**
     * 첫 시작화면 (메인메뉴)
     * 주문이 완료되면 이쪽으로 리다이렉트
     */
    public static void index() {
        System.out.println();
        System.out.println("[ 실시간 커머스 플랫폼 메인 ]");
        System.out.println("1. 전자제품");
        System.out.println("2. 의류 ");
        System.out.println("3. 식품 ");
        System.out.println("0. 종료        | 프로그램 종료");
        System.out.println("6. 관리자 모드");
        System.out.printf("입력: ");
    }

    //여기서 부터 주문 서비스

    /**
     * 1. 전자제품
     * 2. 의류
     * 3. 식품
     * 중에 하나의 리스트를 컨트롤러가 넘겨주면
     * 여기서 화면 출력
     * @param products
     */
    public static void products(List<Product> products) {
        System.out.println();
        System.out.printf("[ %s 카테고리 ] \n", products.get(0).getCategory().getName());
        for (int i = 1; i <= products.size(); i++) {
            System.out.printf("%d. %s\n", i, products.get(i - 1));
        }
        System.out.println("0. 뒤로 가기");
    }

    /**
     * 상품을 선택했을때
     * 장바구니에 추가할것인지 말것인지를 클라이언트에게 보여주는 선택폼
     * @param product
     */
    public static void orderForm(Product product) {
        System.out.println();
        System.out.printf("선택한 상품: %s \n", product);
        System.out.println("위 상품을 장바구니에 추가하시겠습니까?");
        System.out.println("1. 확인        2. 취소");
        System.out.printf("입력: ");
    }

    /**
     * 장바구니에 성공적으로 추가되었을때 출력해주는 메세지
     * @param product
     */
    public static void addOrderSuccessfulMessage(Product product) {
        System.out.println();
        System.out.printf("%s 가 장바구니에 추가되었습니다.\n", product.getName());
    }

    /**
     * OutOfStuckException을 던졌을때 장바구니추가에 실패했음을 알리는 메세지
     */
    public static void addOrderFailedMessage() {
        System.out.println();
        System.out.println("해당상품은 현재 재고가 없습니다");
    }

    /**
     * 만약 장바구니에 상품이 하나라도 있다면 orderOptionController 가
     * 이 view 를 보여준다
     * @param size
     */
    public static void orderOption(int size) {
        System.out.println();
        System.out.println("[ 주문 관리 ]");
        System.out.printf("%d. 장바구니 확인\n", size + 1);
        System.out.printf("%d. 주문 취소\n", size + 2);
    }

    public static void orderClean() {
        System.out.println();
        System.out.println("진행중인 주문을 취소합니다");
    }

    public static void finalizeOrderForm(List<Product> orders, int price) {
        System.out.println();
        System.out.println("[ 장바구니 내역 ]");
        for (int i = 1; i <= orders.size(); i++) {
            System.out.printf("%d. %s\n", i, orders.get(i - 1));
        }
        System.out.println();
        System.out.println("[ 총 주문 금액 ]");
        System.out.println(String.format("%,d", price) + " 원");
        System.out.println();
        System.out.println("1. 주문 확정      2. 메인으로 돌아가기");
    }
    // 최종 주문 완료후 나오는 화면
    public static void finalizeOrderMessage(Map<String, int[]> result, int price) {
        System.out.println();
        System.out.println("주문이 완료되었습니다~ 총 금액: " + price + "원");
        for (String string : result.keySet()) {
            System.out.printf("%s 재고가 %d개 → %d개로 업데이트되었습니다.\n", string, result.get(string)[0], result.get(string)[1]);
        }
    }
    public static void finalizeOrderMessage(Map<String, int[]> result, int standardPrice, int gradeBasedPrice, Grade grade) {
        int diffPrice = standardPrice - gradeBasedPrice;
        System.out.println();
        System.out.println("주문이 완료되었습니다!\n");
        System.out.printf("할인전 금액: %s원\n", String.format("%,d", standardPrice));
        System.out.printf("%s 등급 할인(%d%%): %d원\n", grade, (int)(grade.getDiscountRate()*100),diffPrice);
        System.out.printf("최종 결제 금액: %s원\n", String.format("%,d", gradeBasedPrice));
        for (String string : result.keySet()) {
            System.out.printf("%s 재고가 %d개 → %d개로 업데이트되었습니다.\n", string, result.get(string)[0], result.get(string)[1]);
        }
    }
    // 최종 주문 완료후 나오는 화면
    //여기서부터 관리자 서비스
    public static void authenticateManagerForm() {
        System.out.println();
        System.out.printf("관리자 비밀번호를 입력해주세요: ");
    }

    public static void managerOption() {
        System.out.println();
        System.out.println("[ 관리자 모드 ]");
        System.out.println("1. 상품 추가");
        System.out.println("2. 상품 수정");
        System.out.println("3. 상품 삭제");
        System.out.println("0. 메인으로 돌아가기");
    }

    public static void addProduct() {
        System.out.println();
        System.out.println("어느 카테고리에 상품을 추가하시겠습니까?");
        System.out.println("1. 전자제품");
        System.out.println("2. 의류");
        System.out.println("3. 식품");
    }

    //addProductForm 의 View 시작
    public static void addProductForm(int code) {
        System.out.printf("[ %s 카테고리에 상품 추가 ]\n", Category.codeToCategory(code).getName());
    }

    public static void addProductFormName() {
        System.out.printf("상품명을 입력해주세요: ");
    }

    public static void addProductFormPrice() {
        System.out.printf("가격을 입력해주세요: ");
    }

    public static void addProductFormDiscription() {
        System.out.printf("상품 설명을 입력해주세요: ");
    }

    public static void addProductFormQuantity() {
        System.out.printf("재고수량을 입력해주세요: ");
    }
    //addProductForm 의 View 끝

    public static void confirmAddProductForm(Product product) {
        System.out.println();
        System.out.printf("선택한 상품: %s \n", product);
        System.out.println("위 정보로 상품을 추가하시겠습니까?");
        System.out.println("1. 확인        2. 취소");
        System.out.printf("입력: ");
    }

    public static void confirmAddProductMessage() {
        System.out.println("상품이 성공적으로 추가되었습니다! ");
    }

    public static void editProduct() {
        System.out.printf("수정할 상품명을 입력해주세요: ");
    }

    public static void editProductOption() {
        System.out.println("1. 가격");
        System.out.println("2. 설명");
        System.out.println("3. 수량");
    }

    public static void editProductFormPrice(Product product) {
        System.out.printf("현재 가격: %d%n", product.getPrice());
        System.out.print("새로운 가격을 입력해주세요: ");
    }

    public static void editProductFormDiscription(Product Product) {
        System.out.printf("현재 설명: %s%n", Product.getDiscription());
        System.out.print("새로운 설명을 입력해주세요: ");
    }

    public static void editProductFormQuantiry(Product Product) {
        System.out.printf("현재 재고수량: %d%n", Product.getQuantity());
        System.out.print("새로운 재고수량을 입력해주세요: ");
    }

    public static void editProductFormPriceMessage(Product oldProduct, int newPrice) {
        System.out.printf("%s의 가격이 %,d원 → %,d원으로 수정되었습니다.%n",
                oldProduct.getName(), oldProduct.getPrice(), newPrice);
    }

    public static void editProductFormDiscriptionMessage(Product oldProduct, String newDiscription) {
        System.out.printf("%s의 설명이 '%s' → '%s'으로 수정되었습니다.%n",
                oldProduct.getName(), oldProduct.getDiscription(), newDiscription);
    }

    public static void editProductFormQuantiryMessage(Product oldProduct, int newQuantity) {
        System.out.printf("%s의 재고수량이 %d → %d으로 수정되었습니다.%n",
                oldProduct.getName(), oldProduct.getQuantity(), newQuantity);
    }

    public static void removeProductFrom() {
        System.out.printf("제거할 상품의 이름을 입력하세요: ");
    }

    public static void removeProductMessage(Product product) {
        System.out.printf("%s 가 재고에서 삭제되었습니다.", product.getName());
    }

    // 여기서부터 QueryService 로직
    public static void productsOption(Category category) {
        System.out.println();
        System.out.printf("[ %s 카테고리 ]\n", category.getName());
        System.out.println("1. 전체 상품 보기");
        System.out.println("2. 가격 대별 필터링 (100만원 이하)");
        System.out.println("3. 가격 대별 필터링 (100만원 초과)");
        System.out.println("0. 뒤로가기");
    }

    public static void filterProductsUnder(List<Product> products) {
        System.out.println();
        System.out.println("[ 100만원 이하 상품 목록 ]");
        for (int i = 1; i <= products.size(); i++) {
            System.out.printf("%d. %s\n", i, products.get(i - 1));
        }
        System.out.println("0. 뒤로 가기");
    }

    public static void filterProductsOver(List<Product> products) {
        System.out.println("[ 100만원 이상 상품 목록 ]");
        for (int i = 1; i <= products.size(); i++) {
            System.out.printf("%d. %s\n", i, products.get(i - 1));
        }
        System.out.println("0. 뒤로 가기");
    }

    public static void gradeForm() {
        System.out.println("고객 등급을 입력해주세요.");
        System.out.println("1. BRONZE   :  0% 할인");
        System.out.println("2. SILVER   :  5% 할인");
        System.out.println("3. GOLD     : 10% 할인");
        System.out.println("4. PLATINUM : 15% 할인");
    }


}