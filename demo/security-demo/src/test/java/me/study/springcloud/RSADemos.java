/*
 * Copyright (c) 2018
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2018-12-06T22:48:04.161+08:00
 * LGPL licence
 *
 */

package me.study.springcloud;

import com.google.common.base.Charsets;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.Base64Utils;

import java.io.File;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

public class RSADemos {

    @Test
    public void generateKeys() throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException {
        final KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());

        keystore.load(new ClassPathResource("mytest.jks").getInputStream(), "mypass".toCharArray());


        final Certificate cert = keystore.getCertificate("mytest");
        final PublicKey publicKey = cert.getPublicKey();
        FileUtils.writeStringToFile(new File("src/test/resources/rsa.pub"), Base64Utils.encodeToString(publicKey.getEncoded()), Charsets.UTF_8);
    }
}
