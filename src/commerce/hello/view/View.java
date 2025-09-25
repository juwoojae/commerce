package commerce.hello.view;

import commerce.hello.domain.order.OrderRepository;
import commerce.hello.domain.product.Category;
import commerce.hello.domain.product.Product;
import commerce.hello.domain.product.ProductRepository;
import commerce.hello.service.managerSevice.ManagerService;
import commerce.hello.service.managerSevice.ManagerServiceImpl;
import commerce.hello.service.orderService.OrderService;
import commerce.hello.service.queryService.QueryService;
import commerce.hello.web.AppConfig;
import static commerce.hello.domain.product.Category.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

class View {


    static AppConfig appConfig = new AppConfig();
    static OrderService orderService = appConfig.orderService();
    static ManagerService managerService = appConfig.managerService("1q2w3e4r!@");
    static QueryService queryService = appConfig.queryService();

    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));


    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        index();
        int cmd = Integer.parseInt(bufferedReader.readLine());
        switch (cmd) {
            case 1:
                product(ELECTRONIC);
                break; // switch 블록 빠져나오기
            case 2:
                queryService.listProducts(CLOTHES);
                break;
            case 3:
                queryService.listProducts(FOOD);

            default:
                // 어느 case와도 일치하지 않을 때 실행
        }

    }

    public static void index() {
        System.out.println("[ 실시간 커머스 플랫폼 메인 ]");
        System.out.println("1. 전자제품");
        System.out.println("2. 의류 ");
        System.out.println("3. 식품 ");
        System.out.println("4. 종료        | 프로그램 종료");
        System.out.println("6. 관리자 모드");
    }
    public static void product(Category category){
        List<Product> products = queryService.listProducts(category);
        System.out.println(products.size());
        System.out.printf("[ %s 카테고리 ] \n",category.getName());
        for(int i =1;i<=products.size();i++){
            System.out.printf("%d. %s",i,products.get(i-1));
        }
    }
    public
}