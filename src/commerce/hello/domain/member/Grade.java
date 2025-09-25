package commerce.hello.domain.member;

public enum Grade {
    BRONZE(0),SILVER(0.05), GOLD(0.1),PLATINUM(0.15);

    double discountRate;

    Grade(double discountRate) {
        this.discountRate = discountRate;
    }

    public double getDiscountRate() {
        return discountRate;
    }
}
