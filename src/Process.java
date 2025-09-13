import java.io.IOException;
import java.util.*;

public class Process {
    private static Map<String, java.lang.Process> processes = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nВыберите действие:");
            System.out.println("1. Открыть блокнот");
            System.out.println("2. Открыть Paint");
            System.out.println("3. Открыть калькулятор");
            System.out.println("4. Открыть все");
            System.out.println("5. Закрыть все");
            System.out.println("6. Показать PID процессов");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: start("notepad.exe"); break;
                case 2: start("mspaint.exe"); break;
                case 3: start("calc.exe"); break;
                case 4: open(); break;
                case 5: close(); break;
                case 6: info(); break;
                default:
                    System.out.println("Выход из программы.");
                    return;
            }
        }
    }

    private static void start(String name) {
        try {
            System.out.print("Открыть " + name + "? (да/нет): ");
            if (!scanner.nextLine().equalsIgnoreCase("да")) return;

            java.lang.Process p = new ProcessBuilder(name).start();
            processes.put(name, p);
            System.out.println(name + " запущен. PID: " + p.pid());

            System.out.print("Закрыть сейчас? (да/нет): ");
            if (scanner.nextLine().equalsIgnoreCase("да")) {
                destroy(name);
            }
        } catch (IOException e) {
            System.out.println("Не удалось запустить " + name);
        }
    }

    private static void destroy(String name) {
        java.lang.Process p = processes.get(name);
        if (p != null) {
            p.destroy();
            processes.remove(name);
            System.out.println(name + " закрыт.");
        } else {
            System.out.println(name + " не запущен.");
        }
    }

    private static void info() {
        if (processes.isEmpty()) {
            System.out.println("Нет запущенных процессов.");
        } else {
            for (String name : processes.keySet()) {
                System.out.println(name + " PID: " + processes.get(name).pid());
            }
        }
    }

    private static void open() {
        am("notepad.exe");
        am("mspaint.exe");
        am("calc.exe");
    }

    private static void close() {
        for (String name : new ArrayList<>(processes.keySet())) {
            destroy(name);
        }
    }
    

    private static void am(String name) {
        try {
            java.lang.Process p = new ProcessBuilder(name).start();
            processes.put(name, p);
            System.out.println(name + " запущен. PID: " + p.pid());
        } catch (IOException e) {
            System.out.println("Не удалось запустить " + name);
        }
    }
}
