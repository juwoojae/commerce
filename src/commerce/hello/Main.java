package commerce.hello;

import commerce.hello.controller.ConsoleController;
import commerce.hello.domain.product.Product;
import commerce.hello.domain.product.ProductRepository;
import commerce.hello.domain.product.ProductRepositoryImpl;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ConsoleController consoleController = new ConsoleController();
        consoleController.indexController();
    }
}
