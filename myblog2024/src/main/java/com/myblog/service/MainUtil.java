package com.myblog.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class MainUtil {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("Testing"));
    }
}
