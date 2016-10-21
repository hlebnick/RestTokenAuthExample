package com.resttest.tools;

import org.apache.commons.codec.digest.DigestUtils;

public class PasswordHashGenerator {

    public static void main(String[] args) {
        System.out.println(DigestUtils.sha1Hex("user"));
    }
}
