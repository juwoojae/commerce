package commerce.hello.controller;

import commerce.hello.domain.product.Category;
import commerce.hello.domain.product.Product;
import commerce.hello.domain.product.ProductRepository;
import commerce.hello.exception.InvalidateCmdException;
import commerce.hello.service.managerSevice.ManagerService;
import commerce.hello.service.orderService.OrderService;
import commerce.hello.service.queryService.QueryService;
import commerce.hello.view.View;
import commerce.hello.web.AppConfig;
import commerce.hello.exception.SecurityException;

import java.io.BufferedReader;
import java.io.IOException;


public class ControllerV2 extends ControllerV1 {

    ManagerService managerService;

    /**
     * ControllerV1 의 메서드 오버라이딩 6. 관리자 모드 추가
     *
     * @throws IOException
     */
    @Override
    public void indexController() throws IOException {
        try {
            View.index();
            int cmd = Integer.parseInt(bufferedReader.readLine());
            if (0 < cmd && cmd < 4) productsController(Category.codeToCategory(cmd));
            else if (cmd == 6) authenticateManagerFormController();
            else if (cmd == 0) System.exit(0);
            else throw new InvalidateCmdException("잘못된 명령어 입니다");
        } catch (InvalidateCmdException e) {
            System.out.println("잘못된 명령어 입니다. 다시 입력해주세요");
            indexController();
        }
    }

    public void authenticateManagerFormController() throws IOException {
        View.authenticateManagerForm();
        String password = bufferedReader.readLine();
        try {
            managerService = appConfig.managerService(password);
            managerOptionController();
        } catch (SecurityException e) {
            //비밀번호를 틀린경우 반복 호출
            System.out.println("비밀번호 오류!! 재시도");
            authenticateManagerFormController();
        }
    }

    public void managerOptionController() throws IOException {
        View.managerOption();
        int cmd = Integer.parseInt(bufferedReader.readLine());
        if (cmd == 1) addProductController(); //상품 추가
        else if (cmd == 2) editProductController(); //상품 수정
        else if (cmd == 3) removeProductController();  //상품 제거
        else if (cmd == 0) indexController(); //메인 화면
        else throw new InvalidateCmdException("잘못된 명령어 입니다");
    }


    public void addProductController() throws IOException {
        View.addProduct();
        int cmd = Integer.parseInt(bufferedReader.readLine());
        addProductFormController(cmd);
    }

    public void addProductFormController(int code) throws IOException {
        View.addProductForm(code);
        Product addProduct = new Product();
        // 상품명 입력
        View.addProductFormName();
        addProduct.setName(bufferedReader.readLine());
        // 가격 입력
        View.addProductFormPrice();
        addProduct.setPrice(Integer.parseInt(bufferedReader.readLine()));
        // 설명 입력
        View.addProductFormDiscription();
        addProduct.setDiscription(bufferedReader.readLine());
        // 재고 수량 입력
        View.addProductFormQuantity();
        addProduct.setQuantity(Integer.parseInt(bufferedReader.readLine()));
        // 카테고리 설정
        addProduct.setCategory(Category.codeToCategory(code));
        confirmAddProductFormController(addProduct);
    }

    public void confirmAddProductFormController(Product product) throws IOException {
        View.confirmAddProductForm(product);
        int cmd = Integer.parseInt(bufferedReader.readLine());
        if (cmd == 1) { //확정
            managerService.register(product);
            confirmAddProductMessageController();
            managerOptionController();
        } else if (cmd == 2) { //취소
            managerOptionController();
        } else {
            throw new InvalidateCmdException("잘못된 명령어 입니다");
        }
    }

    public void confirmAddProductMessageController() {
        View.confirmAddProductMessage();
    }

    public void editProductController() throws IOException {
        try {
            View.editProduct();
            String editName = bufferedReader.readLine();
            Product findProduct = managerService.findProduct(editName);
            if (findProduct == null) {  //해당 이름이 존재하지 않는다면
                throw new NullPointerException("찾을수 없음");
            } else { //해당 이름이 존재한다면
                editProductOptionController(findProduct);
            }
        } catch (NullPointerException e) {
            System.out.println("해당하는 이름이 존재하지 않습니다");
            editProductController();
        }
    }

    public void editProductOptionController(Product product) throws IOException {
        View.editProductOption();
        int cmd = Integer.parseInt(bufferedReader.readLine());
        try {
            if (cmd == 1) {
                editProductFormPriceController(product);
                cmd = Integer.parseInt(bufferedReader.readLine());
            } else if (cmd == 2) {
                editProductFormDiscriptionController(product);
                cmd = Integer.parseInt(bufferedReader.readLine());
            } else if (cmd == 3) {
                editProductFormQuantityController(product);
                cmd = Integer.parseInt(bufferedReader.readLine());
            } else {
                throw new InvalidateCmdException("잘못된 명령어 입니다");
            }
        } catch (InvalidateCmdException e) {
            System.out.println("잘못된 명령어 입니다. 다시 입력해주세요");
            editProductOptionController(product);
        }
    }

    public void editProductFormPriceController(Product product) throws IOException {
        View.editProductFormPrice(product);
        int newPrice = Integer.parseInt(bufferedReader.readLine());
        editProductFormPriceMessageController(product, newPrice);
    }

    public void editProductFormDiscriptionController(Product product) throws IOException {
        View.editProductFormDiscription(product);
        String newDiscription = bufferedReader.readLine();
        editProductFormDiscriptionMessageController(product, newDiscription);
    }

    public void editProductFormQuantityController(Product product) throws IOException {
        View.editProductFormQuantiry(product);
        int newQuantity = Integer.parseInt(bufferedReader.readLine());
        editProductFormQuantiryMessageController(product, newQuantity);
    }

    public void editProductFormPriceMessageController(Product oldProduct, int newPrice) throws IOException {
        View.editProductFormPriceMessage(oldProduct, newPrice);
        Product newProduct = oldProduct;  //옛 상품에서 가격만 바꾸기
        newProduct.setPrice(newPrice);
        managerService.update(oldProduct.getName(), newProduct);
        managerOptionController();
    }

    public void editProductFormDiscriptionMessageController(Product oldProduct, String newDiscription) throws IOException {
        View.editProductFormDiscriptionMessage(oldProduct, newDiscription);
        Product newProduct = oldProduct;  //옛 상품에서 가격만 바꾸기
        newProduct.setDiscription(newDiscription);
        managerService.update(oldProduct.getName(), newProduct);
        managerOptionController();
    }

    public void editProductFormQuantiryMessageController(Product oldProduct, int newQuantity) throws IOException {
        View.editProductFormPriceMessage(oldProduct, newQuantity);
        Product newProduct = oldProduct;  //옛 상품에서 가격만 바꾸기
        newProduct.setPrice(newQuantity);
        managerService.update(oldProduct.getName(), newProduct);
        managerOptionController();
    }

    public void removeProductController() throws IOException {
        try {
            View.removeProductFrom();
            String editName = bufferedReader.readLine();
            Product findProduct = managerService.findProduct(editName);
            if (findProduct == null) {  //해당 이름이 존재하지 않는다면
                throw new NullPointerException("찾을수 없음");
            } else { //해당 이름이 존재한다면
                removeProductMessageController(findProduct);
            }
        } catch (NullPointerException e) {
            System.out.println("해당하는 이름이 존재하지 않습니다");
            removeProductController();
        }
    }
    public void removeProductMessageController(Product product) throws IOException {
        managerService.remove(product.getName());
        View.removeProductMessage(product);
        managerOptionController();
    }

}




