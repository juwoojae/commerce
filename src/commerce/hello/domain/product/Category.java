package commerce.hello.domain.product;

import java.util.HashMap;
import java.util.Map;

public enum Category {

    ELECTRONIC("전자제품"), CLOTHES("의류"), FOOD("식품");

    String name;
    final static Map<Integer, Category> CodeToCategory = new HashMap<>();


    Category(String name) {
        this.name = name;

    }

    public String getName() {
        return name;
    }
    public static Category codeToCategory(int code) {
        if (code == 1) return ELECTRONIC;
        else if (code == 2) return CLOTHES;
        else return FOOD;
    }
}
