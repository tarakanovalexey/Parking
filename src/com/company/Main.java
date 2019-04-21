package com.company;

import com.company.entities.Car;
import com.company.entities.Parking;
import com.company.entities.Ticket;
import com.company.threads.Enter;
import com.company.threads.Quit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        System.out.print("Добро пожаловать!\n" +
                "Введите кол-во парковочных мест: ");
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        int space;
        while(true)
            try {
                space = Integer.parseInt(r.readLine());
                if (space > 0)
                    break;
                else System.out.println("Введите целое неотрицательное число больше нуля!");
            }
            catch (Exception e) {
                System.out.println("Введите целое неотрицательное число больше нуля!");
            }
        Parking.setTotalspace(space);
            for (int i=0; i<space; i++)
                Parking.getTickets().add(new Ticket(i));

        String text = "";

        int counter = 1;
        int cparse;
        int[] arr;

        while (true) {
            System.out.println("Введите команду (help для списка команд):");
            try {
                text = r.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (text.equals("exit") || text.equals("e"))
                break;
            if (text.equals("help") || text.equals("h")) {
                System.out.println("Команды:\n" +
                        "p:N - (park) чтобы припарковать машину, в командной строке вводится, где N - количество машин на въезд\n" +
                        "u:N - (unpark) чтобы выехать с парковки. N - номер парковочного билета\n" +
                        "u:[1..n] - (unpark) чтобы выехать с парковки нескольким машинам, где в квадратных скобках, через запятую передаются номера парковочных билетов\n" +
                        "l - (list) список машин, находящихся на парковке. Для каждой машины выводится ее порядковый номер и номер билета\n" +
                        "c - (count) количество оставшихся мест на парковке\n" +
                        "e - (exit) выход из приложения\n" +
                        "t:N - (time) установить время для въезда и выезда одной машины (N - от 1 до 5 сек)\n" +
                        "h - (help) вызов списка команд");
            } else if (text.matches("p:\\d+")) {
                //а нужен второй поток уже тут
                cparse = Integer.parseInt(text.split("p:")[1]);
                if (Parking.getTickets().size() >= cparse) {
                    Enter enterthread = new Enter();
                    enterthread.setCars(cparse);
                    enterthread.start();
                } else
                    System.out.println("На парковке нет столько свободных мест!");
            } else if (text.matches("u:\\d+")) {
                cparse = Integer.parseInt(text.split("u:")[1]);
                if (Parking.getTotalspace() >= cparse) {
                    counter = -1;
                    for (Map.Entry<Ticket, Car> each : Parking.getMapping().entrySet()) {
                        Quit thread = new Quit();
                        if (each.getKey().getId() == cparse) {
                            thread.setTicket(each.getKey());
                            thread.start();
                            counter = 1;
                        }
                    }
                    if (counter == -1) {
                        System.out.println("Не припаркована машина с билетом №" + cparse);
                    }
                }
                else System.out.println("Нет такого билета");
            } else if (text.matches("u:\\[(\\d+,)+\\d+]")) {
                arr = Arrays.stream(text.split("u:")[1].substring(1, text.split("u:")[1].length() - 1).split(","))
                        .map(String::trim).mapToInt(Integer::parseInt).toArray();
                for (int each1 : arr) {
                    if (Parking.getTotalspace() >= each1) {
                        counter = -1;
                        for (Map.Entry<Ticket, Car> each : Parking.getMapping().entrySet()) {
                            Quit thread = new Quit();
                            if (each.getKey().getId() == each1) {
                                thread.setTicket(each.getKey());
                                thread.start();
                                counter = 1;
                            }
                        }
                        if (counter == -1) {
                            System.out.println("Не припаркована машина с билетом №" + each1);
                        }
                    }
                }
            } else if (text.equals("l") || text.equals("list")) {
                counter = 1;
                System.out.println("Сейчас на парковке " + Parking.getMapping().size() + " машин");
                if (Parking.getMapping().size()>0)
                        for (Map.Entry<Ticket, Car> each: Parking.getMapping().entrySet()) {
                            System.out.println("#"+counter + " | № билета: " + each.getKey().getId());
                            counter++;
                        }
            } else if (text.equals("c") || text.equals("count")) {
                System.out.println("Сейчас на парковке " + (Parking.getTotalspace()-Parking.getMapping().size()) + " свободных мест");
            } else if (text.matches("t:\\d+")) {
                cparse = Integer.parseInt(text.split("t:")[1]);
                if (cparse >= 1 && cparse <=5) {
                    Enter.setSleeptime(cparse);
                    Quit.setSleeptime(cparse);
                    System.out.println("Время въезда/выезда установлено на "+cparse+" секунд");
                } else {
                    System.out.println("Ошибка. Значение может быть только от 1 до 5! Возврат в меню.");
                }
            } else
                System.out.println("Неопознанная команда. Попробуйте ещё раз.");
        }
        System.out.print("Работа завершена!");
        try {
            r.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

}
