package SS7.B2;

public class Main {

    public static void main(String[] args) {

        double total = 1000000;

        // Percentage Discount 10%
        OrderCalculator calc1 = new OrderCalculator(new PercentageDiscount(10));
        System.out.println("Số tiền sau giảm: " + (long) calc1.calculate(total));

        // Fixed Discount 50.000
        OrderCalculator calc2 = new OrderCalculator(new FixedDiscount(50000));
        System.out.println("Số tiền sau giảm: " + (long) calc2.calculate(total));

        // No Discount
        OrderCalculator calc3 = new OrderCalculator(new NoDiscount());
        System.out.println("Số tiền sau giảm: " + (long) calc3.calculate(total));

        // Holiday Discount 15% (thêm mới không sửa code cũ)
        OrderCalculator calc4 = new OrderCalculator(new HolidayDiscount());
        System.out.println("Số tiền sau giảm: " + (long) calc4.calculate(total));
    }
}

/* Interface */
interface DiscountStrategy {
    double applyDiscount(double totalAmount);
}

/* Percentage Discount */
class PercentageDiscount implements DiscountStrategy {

    private double percent;

    public PercentageDiscount(double percent) {
        this.percent = percent;
    }

    public double applyDiscount(double totalAmount) {
        return totalAmount - (totalAmount * percent / 100);
    }
}

/* Fixed Discount */
class FixedDiscount implements DiscountStrategy {

    private double amount;

    public FixedDiscount(double amount) {
        this.amount = amount;
    }

    public double applyDiscount(double totalAmount) {
        return totalAmount - amount;
    }
}

/* No Discount */
class NoDiscount implements DiscountStrategy {

    public double applyDiscount(double totalAmount) {
        return totalAmount;
    }
}

/* Holiday Discount (thêm mới) */
class HolidayDiscount implements DiscountStrategy {

    public double applyDiscount(double totalAmount) {
        return totalAmount - (totalAmount * 0.15);
    }
}

/* OrderCalculator */
class OrderCalculator {

    private DiscountStrategy discountStrategy;

    public OrderCalculator(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    public double calculate(double totalAmount) {
        return discountStrategy.applyDiscount(totalAmount);
    }
}