package main;

import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Level;

/*
На бензозаправку приезжают автомобили, чтобы заправиться топливом.
Всего на заправке четыре колонки. Среднее время заправки одной машины {6 мин.}.
Наблюдается такое количество машин, желающих заправиться, что колонки не простаивают.
Через каждые {45 мин.} работы колонки закрываются на техническое обслуживание:
первая– на 10 мин, вторая – на 15 мин., третья – на 5 мин., четвертая– на 13 мин.
Необходимо разработать многопоточное приложение, которое позволяет сделать следующее:
a) вводятся все данные, отмеченные в условии задачи как {..}; б) выводится количество автомобилей,
которое будет обслужено всеми колонками за {4} часа; выводится количество автомобилей,
которое обслужит каждая колонка за {4} часа.
*/

public class GasStation {
    public static final int GASSTATION_DURATION_MIN = 240;
    public static final int MAINTENANCE_INTERVAL_MIN = 45;
    private static final Logger logger = Logger.getLogger(GasStation.class.getName());

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            logger.info("Введите среднее время заправки (в минутах): ");
            int refuelTime = scanner.nextInt();

            int[] maintenanceTimes = new int[4];
            for (int i = 0; i < 4; i++) {
                logger.info(String.format("Введите время техобслуживания для колонки #%d (в минутах):", i + 1));
                maintenanceTimes[i] = scanner.nextInt();
            }

            Pump[] pumps = new Pump[4];
            for (int i = 0; i < 4; i++) {
                pumps[i] = new Pump(i + 1, refuelTime, maintenanceTimes[i]);
            }

            for (Pump pump : pumps) {
                pump.start();
            }

            for (Pump pump : pumps) {
                pump.join();
            }

            int totalServed = 0;
            logger.info("\nРезультат:");
            for (Pump pump : pumps) {
                logger.info(String.format("Колонка #%d обслужила: %d машин.", pump.getPumpId(), pump.getCarsServed()));
                totalServed += pump.getCarsServed();
            }
            logger.info("Всего обслужено машин: " + totalServed);

        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "Произошло прерывание потока", e);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Ошибка во время работы программы", e);
        }
    }
}