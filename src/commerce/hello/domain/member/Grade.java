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
    public static Grade codeToGrade(int code){
        if(code==1) return BRONZE;
        else if (code==2) return SILVER;
        else if (code==3) return GOLD;
        else return PLATINUM;
    }
}
