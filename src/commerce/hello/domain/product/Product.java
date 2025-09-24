package commerce.hello.domain.product;

public class Product {

    private long id;
    private String name;
    private Category category;
    private int price;
    private String discription;
    private int quantity;

    public Product(String name,Category category,int price, String discription, int quantity) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.discription = discription;
        this.quantity = quantity;
    }

    public Category getCategory() {
        return category;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return " "+name+ "     "+"| "+String.format("%,d", price)+" 원 | "+discription+" | 재고: "+quantity + "개";
    }
}
