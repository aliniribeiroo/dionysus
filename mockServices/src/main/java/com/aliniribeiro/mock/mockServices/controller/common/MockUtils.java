package com.aliniribeiro.mock.mockServices.controller.common;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

public class MockUtils {


    public static String getRandomId(){
        return UUID.randomUUID().toString();
    }

    public static int getRandomBirthYear(){
        return randBetween(1900, 2010);
    }

    public static LocalDate getRandomDate(){
        Random random = new Random();
        int minDay = (int) LocalDate.of(2000, 1, 1).toEpochDay();
        int maxDay = (int) LocalDate.of(2019, 1, 1).toEpochDay();
        long randomDay = minDay + random.nextInt(maxDay - minDay);

        return LocalDate.ofEpochDay(randomDay);
    }


    public static String getRandomCPF(){
        String start = "";
        Integer num;
        for (int i = 0; i < 9; i++) {
            num = new Integer((int) (Math.random() * 10));
            start += num.toString();
        }
        return start + calcDigVerif(start);
    }


    private static String calcDigVerif(String num) {
        Integer firstDig, secDig;
        int soma = 0, peso = 10;
        for (int i = 0; i < num.length(); i++)
            soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;
        if (soma % 11 == 0 | soma % 11 == 1)
            firstDig = new Integer(0);
        else
            firstDig = new Integer(11 - (soma % 11));
        soma = 0;
        peso = 11;
        for (int i = 0; i < num.length(); i++)
            soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;
        soma += firstDig.intValue() * 2;
        if (soma % 11 == 0 | soma % 11 == 1)
            secDig = new Integer(0);
        else
            secDig = new Integer(11 - (soma % 11));
        return firstDig.toString() + secDig.toString();
    }

    private static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }
}
