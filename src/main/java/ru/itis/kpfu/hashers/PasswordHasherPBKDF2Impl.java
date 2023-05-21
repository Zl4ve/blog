package ru.itis.kpfu.hashers;

import org.apache.commons.codec.binary.Hex;
import ru.itis.kpfu.exceptions.EncodingException;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class PasswordHasherPBKDF2Impl implements PasswordHasher {

    public static final String SALT = "23435236";

    @Override
    public String hash(String password) {
        try {
            byte[] salt = SALT.getBytes();
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 10000   , 64);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            SecretKey key = factory.generateSecret(spec);
            return Hex.encodeHexString(key.getEncoded());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new EncodingException("Error while encoding", e);
        }
    }

}
