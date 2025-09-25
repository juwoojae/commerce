package commerce.hello;

import commerce.hello.controller.ControllerV1;
import commerce.hello.controller.ControllerV2;
import commerce.hello.controller.ControllerV3;
import commerce.hello.domain.product.Product;
import commerce.hello.domain.product.ProductRepository;
import commerce.hello.domain.product.ProductRepositoryImpl;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        init.init();  //데이터 케이스 넣기
        ControllerV1 consoleController = new ControllerV3();
        consoleController.indexController();

    }
}
