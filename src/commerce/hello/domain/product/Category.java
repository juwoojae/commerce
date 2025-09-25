package commerce.hello.domain.product;

public enum Category {

    ELECTRONIC("전자제품"),CLOTHES("의류"),FOOD("식품");

    String name ;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
