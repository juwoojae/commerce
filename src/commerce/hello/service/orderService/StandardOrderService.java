package commerce.hello.service.orderService;

import commerce.hello.OutOfStockException;
import commerce.hello.domain.order.OrderRepository;
import commerce.hello.domain.product.Product;
import commerce.hello.domain.product.ProductRepository;

import java.util.*;

public class StandardOrderService implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public StandardOrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    /**
     * 장바구니 추가 메서드
     * 재고 관리 시스템 상품을 장바구니에 담을 때 재고를 확인하고, 재고가 부족할 경우 경고 메시지를 출력한다.
     *
     * @param product : 장바구니 추가할 상품 - Product(name, price, discription, quantity= 1)  (항상 갯수는 1개씩 추가한다)
     */
    @Override
    public void addOrder(Product product) {
        //1. 장바구니에 먼저 담기
        //파라메터의 product 를 orderRepository(장바구니) 에서 찾기
        Product orderProduct = orderRepository.findByName(product.getName());
        int orderProductQuantity;
        if (orderProduct == null) { //장바구니에 product가 없는경우(처음 올라가는경우) 장바구니에 올리기
            orderRepository.save(product);
        } else { //장바구니에 product 가 올라간적이 있는경우
            orderProductQuantity = orderProduct.getQuantity();
            //2.productRepository (재고) 에 재고가 충분한지 판단
            if (orderProductQuantity + 1 <= productRepository.findByName(product.getName()).getQuantity()) {
                //장바구니에 추가해도 될만큼 재고가 충분하다면
                orderProduct.setQuantity(++orderProductQuantity); //해당상품의 수량 +1 해주기
            } else { //장바구니 수량> 재고 인경우
                throw new OutOfStockException("재고 수량 초과 " + orderProductQuantity);
            }
        }
    }

    /**
     * 금액 계산 메서드
     */
    @Override
    public int calculateOrder() {
        return orderRepository.findAll()
                .stream()
                .mapToInt(p -> p.getPrice() * p.getQuantity())
                .sum();
    }
    /**
     * 주문 확정 메서드
     * 1. productRepository(재고) 에서 차감하는 로직
     * 2. 장바구니 초기화

     */
    @Override
    public void finalizeOrder() {
        for (Product orderProduct : orderRepository.findAll()) {
            //orderProduct 의 상품 재고
            Product stockProduct = productRepository.findByName(orderProduct.getName());
            stockProduct.setQuantity(stockProduct.getQuantity() - orderProduct.getQuantity());//재고 수정하기
        }
        orderRepository.clearStore();
    }

    /**
     * 장바구니의 리스트를 반환하는 메서드
     */
    @Override
    public List<Product> listOrders(){
        return orderRepository.findAll();
    }
}
