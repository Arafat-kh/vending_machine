package vending_machine;

public interface PaymentAcceptor {
    int getAmount();

    void addAmount(int amount);

    boolean pay(int amount);
}