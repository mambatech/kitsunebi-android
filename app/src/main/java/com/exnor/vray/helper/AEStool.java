package com.exnor.vray.helper;


import android.util.Base64;

import java.security.GeneralSecurityException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * created by edison 2020/6/7
 */
public class AEStool {

    private static final String KEY = "6368616e676520746869732070617373";

    public static byte[] encrypt(String key, byte[] origData) throws GeneralSecurityException {

        byte[] keyBytes = getKeyBytes(key);
        byte[] buf = new byte[16];
        System.arraycopy(keyBytes, 0, buf, 0, keyBytes.length > buf.length ? keyBytes.length : buf.length);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(buf, "AES"), new IvParameterSpec(keyBytes));
        return cipher.doFinal(origData);

    }

    public static byte[] decrypt(String key, byte[] crypted) throws GeneralSecurityException {
        byte[] keyBytes = getKeyBytes(key);
        byte[] buf = new byte[16];
        System.arraycopy(keyBytes, 0, buf, 0, keyBytes.length > buf.length ? keyBytes.length : buf.length);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(buf, "AES"), new IvParameterSpec(keyBytes));
        return cipher.doFinal(crypted);
    }

    private static byte[] getKeyBytes(String key) {
        byte[] bytes = key.getBytes();
        return bytes.length == 16 ? bytes : Arrays.copyOf(bytes, 16);
    }

    public static String encrypt(String key, String val) throws GeneralSecurityException {
        byte[] origData = val.getBytes();
        byte[] crypted = encrypt(key, origData);
        return Base64.encodeToString(crypted,Base64.DEFAULT);
    }

    public static String decrypt(String val) throws GeneralSecurityException {
        byte[] crypted = Base64.decode(val,Base64.URL_SAFE);
        byte[] origData = decrypt(KEY, crypted);
        return new String(origData);
    }

}
