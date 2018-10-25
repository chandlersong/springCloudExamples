/*
 * Copyright (c) 2018
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2018-10-25T23:11:46.157+08:00
 * LGPL licence
 *
 */

package me.study.springcloud;

import com.google.common.base.Charsets;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.util.Base64Utils;

import java.io.File;
import java.io.IOException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class RSADemos {

    @Test
    public void generateKeys() throws NoSuchAlgorithmException, IOException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(1024);
        KeyPair kp = keyGen.generateKeyPair();
        Key pub = kp.getPublic();
        Key pvt = kp.getPrivate();

        FileUtils.writeStringToFile(new File("src/main/key/rsa.pub"), Base64Utils.encodeToString(pub.getEncoded()), Charsets.UTF_8);
        FileUtils.writeStringToFile(new File("src/main/key/rsa.key"), Base64Utils.encodeToString(pvt.getEncoded()), Charsets.UTF_8);
    }
}
