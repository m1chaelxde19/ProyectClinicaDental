package com.appWeb.ClinicaDental;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class test {
    public static void main(String[] args) {
        Timestamp fechaHora = Timestamp.valueOf(LocalDateTime.now());
        System.out.println(fechaHora);
    }
}
