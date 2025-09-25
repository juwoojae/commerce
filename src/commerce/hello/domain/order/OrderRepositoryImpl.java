package commerce.hello.domain.order;

import commerce.hello.domain.product.Category;
import commerce.hello.domain.product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class OrderRepositoryImpl implements OrderRepository{

    private static final List<Product> store = new ArrayList<>();
    /**
     * 클라이언트가 장바구니(store) 에 상품을 추가하는 메서드
     * @param product
     * @return
     */
    @Override
    public Product save(Product product) {
        store.add(product);
        return product;
    }
    @Override
    public Product findByName(String name){
        return store.stream()
                .filter(product -> product.getName().equals(name))
                .findFirst()  // Optional<Product> 반환
                .orElse(null);  // 못 찾으면 null 반환
    }
    /**
     * 클라이언트가 장바구니 조회를 하기위해 장바구니(store) 를 리턴
     * @return : store 를 얕은복사를 통해서 반환(캡슐화)
     */
    @Override
    public List<Product> findAll() {
        return new ArrayList<>(store);
    }
    /**
     * 장바구니 내역을 비워주는 메서드
     */
    @Override
    public void clearStore(){
        store.clear();
    }

    @Override
    public boolean storeIsEmpty(){
        return store.isEmpty();
    }
}
