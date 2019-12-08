package com.alex;

import com.ibasco.agql.core.exceptions.AsyncGameLibUncheckedException;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Base64;
import java.util.Objects;

public class EncryptUtils {
    private static final String worldsMostSecureUnhackableIvKey = "aGqLsOurc3querYs";
    private static final String worldsMostSecureUnhackableKey = "4EUv4wuTdnKEpwn3k5EYJU7Qha3mBGDx";

    public static String encrypt(String plainText) {
        return encrypt(plainText, worldsMostSecureUnhackableKey);
    }

    /**
     * @see <a href="https://gist.github.com/bricef/2436364">https://gist.github.com/bricef/2436364</a>
     */
    public static String encrypt(String plainText, String secretKey) {
        try {
            if (StringUtils.isBlank(secretKey))
                throw new IllegalArgumentException("Secret key not specified");
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
            SecretKeySpec key = createSecretKey(secretKey);
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(worldsMostSecureUnhackableIvKey.getBytes(StandardCharsets.UTF_8)));
            return Base64.getEncoder().encodeToString((cipher.doFinal(Objects.requireNonNull(padNullBytes(plainText)))));
        } catch (NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            throw new AsyncGameLibUncheckedException(e);
        }
    }

    public static String decrypt(String cipherText) {
        return decrypt(cipherText, worldsMostSecureUnhackableKey);
    }

    /**
     * @see <a href="https://gist.github.com/bricef/2436364">https://gist.github.com/bricef/2436364</a>
     */
    public static String decrypt(String cipherText, String secretKey) {
        try {
            if (StringUtils.isBlank(secretKey))
                throw new IllegalArgumentException("Secret key not specified");
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
            SecretKeySpec key = createSecretKey(secretKey);
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(worldsMostSecureUnhackableIvKey.getBytes(StandardCharsets.UTF_8)));
            byte[] cipherBytes = Base64.getDecoder().decode(cipherText);
            return new String(cipher.doFinal(cipherBytes), StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            throw new AsyncGameLibUncheckedException(e);
        }
    }

    private static SecretKeySpec createSecretKey(String secretKey) {
        if (StringUtils.isBlank(secretKey))
            throw new IllegalArgumentException("Secret key not specified");
        return new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "AES");
    }

    public static String retrieveKey() {
        String key = StringUtils.defaultIfBlank(System.getProperty("secretKey"), EncryptUtils.worldsMostSecureUnhackableKey);
        return key;
    }

    public static byte[] padNullBytes(String text) {
        if (StringUtils.isEmpty(text))
            return null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            bos.write(text.getBytes(StandardCharsets.UTF_8));
            while ((bos.size() % 16) != 0) {
                bos.write(0);
            }
        } catch (IOException e) {
            throw new AsyncGameLibUncheckedException(e);
        }
        return bos.toByteArray();
    }
}