package commerce.hello.exception;

/**
 * 장바구니에 담은 수량이 총 재고를 넘어갈경우에 던지는 예외
 */
public class OutOfStockException extends RuntimeException {
  public OutOfStockException(String message) {
    super(message);
  }
}
