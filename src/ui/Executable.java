package ui;

import java.util.Scanner;
import model.Controller;

public class Executable {
    private Scanner reader;
    private Controller controller;

    public Executable() {
        reader = new Scanner(System.in);
        controller = new Controller();
    }

    public static void main(String[] args) {
        Executable exe = new Executable();
        exe.menu();
    }

    public void menu() {
        boolean exit = false;
        while (!exit) {
            try {
                System.out.println("\n--- GESTION ENERGETICA EL REMANSO ---");
                System.out.println("1. Agregar Dispositivo");
                System.out.println("2. Agregar Evento (Uso)");
                System.out.println("3. Actualizar Consumo");
                System.out.println("4. Consultar Resumen por Piso");
                System.out.println("5. Dispositivos que requieren cambio (> 0.3 KWh)");
                System.out.println("0. Salir");
                System.out.print("Opcion: ");
                int opt = reader.nextInt();
                reader.nextLine();

                switch (opt) {
                    case 1: registerDevice(); break;
                    case 2: registerEvent(); break;
                    case 3: updateCons(); break;
                    case 4: calculateTotal(); break;
                    case 5: System.out.println(controller.getHighConsumptionDevices()); break;
                    case 0: exit = true; break;
                    default: System.out.println("Opcion no valida.");
                }
            } catch (Exception e) {
                System.out.println("Error de entrada. Intente de nuevo.");
                reader.nextLine();
            }
        }
    }

    private void registerDevice() {
        System.out.print("Piso (1-12): ");
        int floor = reader.nextInt() - 1;
        System.out.print("Serial: ");
        String serial = reader.next();
        System.out.print("Consumo Nominal (KWh): ");
        double cons = reader.nextDouble();
        System.out.println(controller.addDevice(floor, serial, cons));
    }

    private void registerEvent() {
        System.out.print("Serial del dispositivo: ");
        String serial = reader.next();
        System.out.print("Fecha: ");
        String date = reader.next();
        System.out.print("Horas de uso: ");
        double hours = reader.nextDouble();
        System.out.println(controller.addEventToDevice(serial, date, hours));
    }

    private void updateCons() {
        System.out.print("Serial: ");
        String serial = reader.next();
        System.out.print("Nuevo valor de consumo: ");
        double val = reader.nextDouble();
        System.out.println(controller.updateDeviceConsumption(serial, val));
    }

    private void calculateTotal() {
        // Mostramos el mapa de pisos antes de preguntar
        System.out.println(controller.getBuildingSummary());
        
        System.out.print("Seleccione el Piso a consultar (1-12): ");
        int floor = reader.nextInt() - 1;
        System.out.print("Ingrese el Serial exacto: ");
        String serial = reader.next();
        
        System.out.println(controller.getDeviceTotalConsumption(floor, serial));
    }
}