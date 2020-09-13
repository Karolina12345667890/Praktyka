package uph.ii.SIMS;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static org.hibernate.annotations.common.util.StringHelper.isNotEmpty;

@Component
public class AttributeEncryptor implements AttributeConverter<String, String> {

    private CipherInitializer cipherInitializer;

    public AttributeEncryptor() {
        this(new CipherInitializer());
    }

    public AttributeEncryptor(CipherInitializer cipherInitializer) {
        this.cipherInitializer = cipherInitializer;
    }

    public static String DATABASE_ENCRYPTION_KEY = "asdasdasdasda123";



    @Override
    public String convertToDatabaseColumn(String attribute) {

        if (isNotEmpty(DATABASE_ENCRYPTION_KEY) && isNotNullOrEmpty(attribute)) {
            try {
                Cipher cipher = cipherInitializer.prepareAndInitCipher(Cipher.ENCRYPT_MODE, DATABASE_ENCRYPTION_KEY);
                return encrypt(cipher, attribute);
            } catch (NoSuchAlgorithmException | InvalidKeyException | InvalidAlgorithmParameterException | BadPaddingException | NoSuchPaddingException | IllegalBlockSizeException e) {
                throw new RuntimeException(e);
            }
        }
        return entityAttributeToString(attribute);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        if (isNotEmpty(DATABASE_ENCRYPTION_KEY) && isNotEmpty(dbData)) {
            try {
                Cipher cipher = cipherInitializer.prepareAndInitCipher(Cipher.DECRYPT_MODE, DATABASE_ENCRYPTION_KEY);
                return decrypt(cipher, dbData);
            } catch (NoSuchAlgorithmException | InvalidKeyException | InvalidAlgorithmParameterException | BadPaddingException | NoSuchPaddingException | IllegalBlockSizeException e) {
                throw new RuntimeException(e);
            }
        }
        return stringToEntityAttribute(dbData);
    }


    boolean isNotNullOrEmpty(String attribute) {
        return isNotEmpty(attribute);
    }


    String stringToEntityAttribute(String dbData) {
        return dbData;
    }


    String entityAttributeToString(String attribute) {
        return attribute;
    }

    byte[] callCipherDoFinal(Cipher cipher, byte[] bytes) throws IllegalBlockSizeException, BadPaddingException {
        return cipher.doFinal(bytes);
    }

    private String encrypt(Cipher cipher, String attribute) throws IllegalBlockSizeException, BadPaddingException {
        byte[] bytesToEncrypt = entityAttributeToString(attribute).getBytes();
        byte[] encryptedBytes = callCipherDoFinal(cipher, bytesToEncrypt);
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    private String decrypt(Cipher cipher, String dbData) throws IllegalBlockSizeException, BadPaddingException {
        byte[] encryptedBytes = Base64.getDecoder().decode(dbData);
        byte[] decryptedBytes = callCipherDoFinal(cipher, encryptedBytes);
        return stringToEntityAttribute(new String(decryptedBytes));
    }
}

