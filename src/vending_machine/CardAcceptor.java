package vending_machine;

import java.util.Scanner;

public class CardAcceptor implements PaymentAcceptor {
    private int balance;

    public CardAcceptor(int balance) {
        this.balance = balance;
    }

    @Override
    public int getAmount() {
        return balance;
    }

    @Override
    public void addAmount(int amount) {
    }

    @Override
    public boolean pay(int amount) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите номер карты: ");
        String card = scanner.nextLine();
        System.out.print("Введите одноразовый код: ");
        String otp = scanner.nextLine();
        if (balance >= amount) {
            balance -= amount;
            System.out.println("Платеж успешен");
            return true;
        }
        System.out.println("Недостаточно средств");
        return false;
    }
}
