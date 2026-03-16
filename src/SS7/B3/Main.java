package SS7.B3;

public class Main {

    public static void main(String[] args) {

        double codAmount = 500000;
        double cardAmount = 1000000;
        double momoAmount = 750000;

        // COD Payment
        CODPayable cod = new CODPayment();
        PaymentProcessor processor1 = new PaymentProcessor();
        processor1.processCOD(cod, codAmount);

        // Credit Card Payment
        CardPayable card = new CreditCardPayment();
        PaymentProcessor processor2 = new PaymentProcessor();
        processor2.processCard(card, cardAmount);

        // MoMo Payment
        EWalletPayable momo = new MomoPayment();
        PaymentProcessor processor3 = new PaymentProcessor();
        processor3.processEWallet(momo, momoAmount);

        // Kiểm tra LSP: thay CreditCard bằng Momo
        CardPayable lspTest = new MomoPayment();
        PaymentProcessor processor4 = new PaymentProcessor();
        processor4.processCard(lspTest, 600000);
    }
}

/* ===== Interface nhỏ theo ISP ===== */

interface CODPayable {
    void processCOD(double amount);
}

interface CardPayable {
    void processCard(double amount);
}

interface EWalletPayable {
    void processEWallet(double amount);
}

/* ===== Implementation ===== */

class CODPayment implements CODPayable {

    public void processCOD(double amount) {
        System.out.println("Xử lý thanh toán COD: " + (long)amount + " - Thành công");
    }
}

class CreditCardPayment implements CardPayable {

    public void processCard(double amount) {
        System.out.println("Xử lý thanh toán thẻ tín dụng: " + (long)amount + " - Thành công");
    }
}

class MomoPayment implements CardPayable, EWalletPayable {

    public void processCard(double amount) {
        System.out.println("Thanh toán bằng MoMo qua thẻ: " + (long)amount + " - Thành công");
    }

    public void processEWallet(double amount) {
        System.out.println("Xử lý thanh toán MoMo: " + (long)amount + " - Thành công");
    }
}

/* ===== Processor ===== */

class PaymentProcessor {

    public void processCOD(CODPayable payment, double amount) {
        payment.processCOD(amount);
    }

    public void processCard(CardPayable payment, double amount) {
        payment.processCard(amount);
    }

    public void processEWallet(EWalletPayable payment, double amount) {
        payment.processEWallet(amount);
    }
}
