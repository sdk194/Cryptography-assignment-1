


import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Scanner;

public class Assignment1 {
    //Given values
    static String primeHex = "b59dd79568817b4b9f6789822d22594f376e6a9abc0241846de426e5dd8f6eddef00b465f38f509b2b18351064704fe75f012fa346c5e2c442d7c99eac79b2bc8a202c98327b96816cb8042698ed3734643c4c05164e739cb72fba24f6156b6f47a7300ef778c378ea301e1141a6b25d48f1924268c62ee8dd3134745cdf7323";
    static String generatorHex = "44ec9d52c8f9189e49cd7c70253c2eb3154dd4f08467a64a0267c9defe4119f2e373388cfa350a4e66e432d638ccdc58eb703e31d4c84e50398f9f91677e88641a2d2f6157e2f4ec538088dcf5940b053c622e53bab0b4e84b1465f5738f549664bd7430961d3e5a2e7bceb62418db747386a58ff267a9939833beefb7a6fd68";
    static String givenKeyHex = "5af3e806e0fa466dc75de60186760516792b70fdcd72a5b6238e6f6b76ece1f1b38ba4e210f61a2b84ef1b5dc4151e799485b2171fcf318f86d42616b8fd8111d59552e4b5f228ee838d535b4b987f1eaf3e5de3ea0c403a6c38002b49eade15171cb861b367732460e3a9842b532761c16218c4fea51be8ea0248385f6bac0d";
    static String secretKeyHex = "643D778E57B550933CCB70EADC13ED3F8DE2634F7AD7179CC31708756DEFFEE27B292757F441C2552CFA1455644940F354052320B99A802AA7B86B62199CA5BE61DB2E784AF3B29AABC97EE0D192BCEA6A2D34B04C133248A03BA008B0ADE1B88BB0949E5E89B5BDC1402F7ADB41D1C1CD0762EA16E8E71ED69639803763D909";

    private static void saveDH(String dh) {
        try {
            FileWriter dhFile = new FileWriter("DH.txt");
            dhFile.write(dh);
            dhFile.close();
        } catch (IOException e) {
            System.err.println("DH.txt does not seem to exist or cannot be created");
        }
    }

    // Function that grabs the value of tge IV.txt file
    private static String getKeyText(String fileName) {
        String key = null;
        try {
            File test = new File(fileName);
            Scanner reader = new Scanner(test);
            key = reader.nextLine();
            reader.close();
        } catch (FileNotFoundException | NullPointerException e) {
            System.err.println("The File does not seem to exist or the pathname is null");
        }
        return key;
    }

    public static BigInteger myModPow(BigInteger n, BigInteger pwr, BigInteger mod) {
        BigInteger curr = new BigInteger("1");
        String binary = pwr.toString(2);
        for (int i = 0; i < binary.length(); i++) {
            curr = curr.multiply(curr);
            if (binary.charAt(i) == '1') {
                curr = curr.multiply(n);
            }
            curr = curr.mod(mod);
        }
        return curr;
    }

    // Generate value for DH.txt
    public static BigInteger genValues(BigInteger n, BigInteger secret, BigInteger p, boolean isDH) {
        BigInteger value = myModPow(n, secret, p);

        if (isDH) {
            saveDH(value.toString(16));
        }
        return value;
    }

    public static String hashKey(String key) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] hash = digest.digest(key.getBytes());

            BigInteger conversion = new BigInteger(1, hash);

            return conversion.toString(16);

        } catch (NoSuchAlgorithmException e) {
            System.err.println("The algorithm to create the hash key does not exist");
            return null;
        }
    }

    public static void encryptFile(String fileName, BigInteger aesKeyInteger, BigInteger ivInteger) throws IOException,
            NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        byte[] ivByteArray = ivInteger.toByteArray();
        IvParameterSpec iv;
        if (ivByteArray.length > 16) { // Remove leading 0
            byte[] fixedIV = Arrays.copyOfRange(ivByteArray, 1, ivByteArray.length);
            iv = new IvParameterSpec(fixedIV);
        }
        else {
            iv = new IvParameterSpec(ivByteArray);
        }

        byte[] aesByteArray = aesKeyInteger.toByteArray();
        SecretKey aesKey = new SecretKeySpec(aesByteArray, "AES");

        cipher.init(Cipher.ENCRYPT_MODE, aesKey, iv);
        byte[] message = Files.readAllBytes(Path.of(fileName));
        int messageLength = message.length;
        int newLength = 0;
        if (messageLength % 16 != 0) {
            newLength = (16 - (messageLength % 16)) + messageLength;
        }
        else {
            newLength = messageLength + 16;
        }
        byte[] paddedMessage = new byte[newLength];

        System.arraycopy(message, 0, paddedMessage, 0, messageLength);

        paddedMessage[messageLength] = (byte) 0x80;

        byte[] outputBytes = cipher.doFinal(paddedMessage);
        System.out.println(new BigInteger(1, outputBytes).toString(16));

    }

    public static void main(String[] args) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        BigInteger prime = new BigInteger(primeHex, 16);
        BigInteger generator = new BigInteger(generatorHex, 16);
        BigInteger givenKey = new BigInteger(givenKeyHex, 16);
        BigInteger secretKey = new BigInteger(secretKeyHex, 16);

        String ivHex = getKeyText("./IV.txt");
        BigInteger iv = new BigInteger(ivHex, 16);

        // Uncomment to generate a new DH.txt
        // genValues(generator, secretKey, prime, true);

        BigInteger sharedSecret = genValues(givenKey, secretKey, prime, false);

        String hashText = hashKey(sharedSecret.toString());
        if (hashText == null) {
            System.err.println("hashKey seems to have returned with an exception");
            return;
        }
        BigInteger hash = new BigInteger(hashText, 16); // hash is the aes key


        encryptFile("./Assignment1.class", hash, iv);
    }
}