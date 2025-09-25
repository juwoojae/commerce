package commerce.hello.view;

import commerce.hello.domain.product.Product;
import java.util.List;
import java.util.Map;

public class View {
    public static void index() {
        System.out.println("[ 실시간 커머스 플랫폼 메인 ]");
        System.out.println("1. 전자제품");
        System.out.println("2. 의류 ");
        System.out.println("3. 식품 ");
        System.out.println("4. 종료        | 프로그램 종료");
        System.out.println("6. 관리자 모드");
    }
    //여기서 부터 주문 서비스
    public static void orderForm(List<Product> products){
        System.out.printf("[ %s 카테고리 ] \n",products.get(0).getCategory().getName());
        for(int i =1;i<=products.size();i++){
            System.out.printf("%d. %s\n",i,products.get(i-1));
        }
    }
    public static void addOrder(Product product){
        System.out.printf("선택한 상품: %s \n",product);
        System.out.println("위 상품을 장바구니에 추가하시겠습니까?");
        System.out.println("1. 확인        2. 취소");
    }
    public static void addOrderSuccessfulMessage(Product product){
        System.out.printf("%s 가 장바구니에 추가되었습니다.\n",product.getName());
    }
    public static void addOrderFailedMessage(){
        System.out.println("해당상품은 현재 제고가 없습니다");
    }
    public static void orderManager(){
        System.out.println();
        System.out.println("[ 주문 관리 ]");
        System.out.println("4. 장바구니 확인");
        System.out.println("5. 주문 취소");
    }
    public static void orderClean(){
        System.out.println("진행중인 주문을 취소합니다");
    }
    public static void finalizeOrderForm(List<Product> orders,int price){
        System.out.println("[ 장바구니 내역 ]");
        for(int i =1;i<=orders.size();i++){
            System.out.printf("%d. %s\n",i,orders.get(i-1));
        }

        System.out.println("[ 총 주문 금액 ]");
        System.out.println(String.format("%,d", price)+" 원");
        System.out.println();
        System.out.println("1. 주문 확정      2. 메인으로 돌아가기");
    }
    public static void finalizeOrderMessage(int price, Map<String,int []> result){
        System.out.println("주문이 완료되었습니다~ 총 금액: "+price+"원");
        for (String string : result.keySet()) {
            System.out.printf("%s 재고가 %d개 → %d개로 업데이트되었습니다.\n",string,result.get(string)[0], result.get(string)[1]);
        }
    }

}