package example.rsa;

/**
 * 引进的包都是Java自带的jar包
 * 秘钥相关包
 * base64 编解码
 * 这里只用到了编码
 */
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;



public class Run {

    
    public class Keys {
        
    }
    public static final String KEY_ALGORITHM = "RSA";
    //public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    //获得公钥
    public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
        //获得map中的公钥对象 转为key对象
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        //byte[] publicKey = key.getEncoded();
        //编码返回字符串
        return encryptBASE64(key.getEncoded());
    }

    //获得私钥
    public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
        //获得map中的私钥对象 转为key对象
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        //byte[] privateKey = key.getEncoded();
        //编码返回字符串
        return encryptBASE64(key.getEncoded());
    }

    //解码返回byte
    public static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    //编码返回字符串
    public static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }
    
    //map对象中存放公私钥
    public static Map<String, Object> initKey(String key) throws Exception {
        //获得对象 KeyPairGenerator 参数 RSA 1024个字节
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        SecureRandom secureRandom = new SecureRandom(key.getBytes());
        keyPairGen.initialize(1024,secureRandom);
        //通过对象 KeyPairGenerator 获取对象KeyPair
        KeyPair keyPair = keyPairGen.generateKeyPair();
        
        //通过对象 KeyPair 获取RSA公私钥对象RSAPublicKey RSAPrivateKey
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        //公私钥对象存入map中
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }
    // 通过公钥byte[]将公钥还原，适用于RSA算法
    public static PublicKey getPublicKey(byte[] keyBytes) throws
    	NoSuchAlgorithmException,InvalidKeySpecException {
    	    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
    	    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    	    PublicKey publicKey = keyFactory.generatePublic(keySpec);
    	    return publicKey;
    }
    // 通过私钥byte[]将私钥钥还原，适用于RSA算法
	public static PrivateKey getPrivateKey(byte[] keyBytes) throws
	NoSuchAlgorithmException,InvalidKeySpecException {
	    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
	    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	    PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
	    return privateKey;
	}
    public static void main(String[] args) {
        Map<String, Object> keyMap;
        String secret="xx1WET12^%3^(WE45";
        try {
            keyMap = initKey(secret);
            String publicKey = getPublicKey(keyMap);
            byte[] pub= ((RSAPublicKey)keyMap.get(PUBLIC_KEY)).getEncoded();
            byte[] pri= ((RSAPrivateKey)keyMap.get(PRIVATE_KEY)).getEncoded();  
            
            Key pubkey=getPublicKey(pub);
            String pubkeyStr=encryptBASE64(pubkey.getEncoded());
            System.out.println(publicKey);
            System.out.println(publicKey.equals(pubkeyStr));
            System.out.println(pubkeyStr);
            Key pubkey2=getPrivateKey(pri);
            String pubkey2Str=encryptBASE64(pubkey2.getEncoded());
            String privateKey = getPrivateKey(keyMap);
            System.out.println(privateKey.equals(pubkey2Str));
           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}   