import enums.ActionLetter;
import model.*;
import util.UniversalArray;
import util.UniversalArrayImpl;
import vending_machine.CardAcceptor;
import vending_machine.CoinAcceptor;
import vending_machine.PaymentAcceptor;

import java.util.Scanner;

public class AppRunner {

    private final UniversalArray<Product> products = new UniversalArrayImpl<>();
    private final PaymentAcceptor acceptor;

    private static boolean isExit = false;

    private AppRunner() {
        products.addAll(new Product[]{
                new Water(ActionLetter.B, 20),
                new CocaCola(ActionLetter.C, 50),
                new Soda(ActionLetter.D, 30),
                new Snickers(ActionLetter.E, 80),
                new Mars(ActionLetter.F, 80),
                new Pistachios(ActionLetter.G, 130)
        });

        acceptor = new CardAcceptor(500);
    }

    public static void run() {
        AppRunner app = new AppRunner();
        while (!isExit) {
            app.startSimulation();
        }
    }

    private void startSimulation() {
        print("В автомате доступны:");
        showProducts(products);
        print("Баланс: " + acceptor.getAmount());

        UniversalArray<Product> allowed = new UniversalArrayImpl<>();
        for (int i = 0; i < products.size(); i++) {
            if (acceptor.getAmount() >= products.get(i).getPrice()) {
                allowed.add(products.get(i));
            }
        }

        showActions(allowed);
        print(" a - Пополнить баланс");
        print(" h - Выйти");

        String action = fromConsole().toLowerCase();

        if (action.equals("a")) {
            if (acceptor instanceof CoinAcceptor) {
                print("Введите сумму: ");
                int sum = Integer.parseInt(fromConsole());
                acceptor.addAmount(sum);
            } else {
                print("Карту нельзя пополнять вручную.");
            }
        } else if (action.equals("h")) {
            isExit = true;
        } else {
            for (int i = 0; i < allowed.size(); i++) {
                if (action.equals(allowed.get(i).getActionLetter().getValue())) {
                    boolean success = acceptor.pay(allowed.get(i).getPrice());
                    if (success) {
                        print("Вы купили " + allowed.get(i).getName());
                    } else {
                        print("Недостаточно средств");
                    }
                }
            }
        }
    }

    private void showProducts(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(products.get(i).toString());
        }
    }

    private void showActions(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(String.format(" %s - %s", products.get(i).getActionLetter().getValue(), products.get(i).getName()));
        }
    }

    private String fromConsole() {
        return new Scanner(System.in).nextLine();
    }

    private void print(String msg) {
        System.out.println(msg);
    }
}
