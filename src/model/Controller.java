package model;

public class Controller {
    private Device[][] tower;
    public static final int FLOORS = 12;
    public static final int DEVICES_PER_FLOOR = 4;

    public Controller() {
        tower = new Device[FLOORS][DEVICES_PER_FLOOR];
    }

    /** 
     * Nombre: addDevice
     * Tipo: Modificador
     * Descripcion: Busca la primera columna disponible en el piso indicado para agregar un dispositivo.
     */
    public String addDevice(int floor, String serial, double consumption) {
        if (floor < 0 || floor >= FLOORS) return "Error: Piso fuera de rango.";
        if (findDeviceGlobal(serial) != null) return "Error: El serial ya existe.";

        for (int j = 0; j < DEVICES_PER_FLOOR; j++) {
            if (tower[floor][j] == null) {
                tower[floor][j] = new Device(serial, consumption);
                return "Dispositivo registrado en el piso " + (floor + 1);
            }
        }
        return "Error: El piso " + (floor + 1) + " ya tiene 4 dispositivos.";
    }

    /** 
     * Nombre: addEventToDevice
     * Tipo: Modificador
     * Descripcion: Añade un evento de consumo buscando el serial en todo el edificio.
     */
    public String addEventToDevice(String serial, String date, double hours) {
        Device d = findDeviceGlobal(serial);
        if (d != null) {
            d.addEvent(date, hours);
            return "Evento registrado exitosamente.";
        }
        return "Error: No se encontro el serial " + serial;
    }

    /** 
     * Nombre: updateDeviceConsumption
     * Tipo: Modificador
     * Descripcion: Actualiza el valor KWh de un dispositivo existente.
     */
    public String updateDeviceConsumption(String serial, double newCons) {
        Device d = findDeviceGlobal(serial);
        if (d != null) {
            d.setConsumptionKWh(newCons);
            return "Consumo actualizado.";
        }
        return "Error: Dispositivo no encontrado.";
    }

    /** 
     * Nombre: getDeviceTotalConsumption
     * Tipo: Analizador
     * Descripcion: Genera resumen de un dispositivo especifico filtrado por piso y serial.
     */
    public String getDeviceTotalConsumption(int floor, String serial) {
        for (int j = 0; j < DEVICES_PER_FLOOR; j++) {
            Device d = tower[floor][j];
            if (d != null && d.getSerial().equalsIgnoreCase(serial)) {
                return "\n>>> RESUMEN INDIVIDUAL <<<\n" +
                       "Piso: " + (floor + 1) + " | Serial: " + d.getSerial() + "\n" +
                       "Consumo Total: " + d.calculateTotalConsumption() + " KWh\n";
            }
        }
        return "Error: No se encontro el serial " + serial + " en el piso " + (floor + 1);
    }

    /** 
     * Nombre: getBuildingSummary
     * Tipo: Analizador
     * Descripcion: Lista todos los dispositivos organizados por piso.
     */
    public String getBuildingSummary() {
        StringBuilder sb = new StringBuilder("\n--- ESTADO ACTUAL DEL EDIFICIO ---\n");
        for (int i = 0; i < FLOORS; i++) {
            sb.append("Piso ").append(i + 1).append(": ");
            boolean empty = true;
            for (int j = 0; j < DEVICES_PER_FLOOR; j++) {
                if (tower[i][j] != null) {
                    sb.append("[").append(tower[i][j].getSerial()).append("] ");
                    empty = false;
                }
            }
            if (empty) sb.append("(Vacio)");
            sb.append("\n");
        }
        return sb.toString();
    }

    public String getHighConsumptionDevices() {
        String report = "Dispositivos > 0.3 KWh:\n";
        for (int i = 0; i < FLOORS; i++) {
            for (int j = 0; j < DEVICES_PER_FLOOR; j++) {
                if (tower[i][j] != null && tower[i][j].getConsumptionKWh() > 0.3) {
                    report += "- Piso " + (i+1) + " | Serial: " + tower[i][j].getSerial() + "\n";
                }
            }
        }
        return report;
    }

    private Device findDeviceGlobal(String serial) {
        for (int i = 0; i < FLOORS; i++) {
            for (int j = 0; j < DEVICES_PER_FLOOR; j++) {
                if (tower[i][j] != null && tower[i][j].getSerial().equalsIgnoreCase(serial)) {
                    return tower[i][j];
                }
            }
        }
        return null;
    }
}