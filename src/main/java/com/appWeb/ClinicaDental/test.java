package com.appWeb.ClinicaDental;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.sql.Date;

public class test {
    public static void main(String[] args) {
        System.out.println(Hashing.sha256().hashString("medixde", StandardCharsets.UTF_8).toString());
    }
}
