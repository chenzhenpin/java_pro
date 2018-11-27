package example.rsa;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

public class Run2 {
private static final String PUBLIC_KEY = "publcKey";
private static final String PRIVATE_KEY = "privateKey";


	/*	1、任意互质的两个质数p,q。
	2、得到连个质数的乘积n。
	3、得到两个质数的欧元函数f(n)=(p-1) * (q-1)。
	4、任取一个小于f(n)并与其互质的质数e。
	5、存在一个数d，使得e^d % f(n) = 1。
	6、则（n,e）为公钥，(n,d)为私钥。
	加密：待加密数据t:
	t^te% n = m(m为加密后的数据);
	解密：m^d % n = t(t为原数据)
	Java实现RSA算法加密解密，加签以及验签代码如下：
	1、Base64工具类：
*/
	//Base64加密
	public static String encryptBase64(byte[] key){
//	    return new String(Base64.encodeBase64(key));
		return new BASE64Encoder().encodeBuffer(key);
	}

	//Base64解密
	public static byte[] decryptBase64(String key) throws IOException {
	    return new BASE64Decoder().decodeBuffer(key);
	}

	//2、获取RSA秘钥对：

	public static Map<String, Object> keyMap()throws Exception{
	    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
	    keyPairGenerator.initialize(1024);
	    KeyPair keyPair = keyPairGenerator.generateKeyPair();
	    PublicKey publicKey = keyPair.getPublic();
	    PrivateKey privateKey = keyPair.getPrivate();

	    Map<String, Object> keyMap = new HashMap<>(2);
	    keyMap.put(PUBLIC_KEY, publicKey);
	    keyMap.put(PRIVATE_KEY, privateKey);
	    return keyMap;
	}
	//3、获取RSA公钥以及私钥分别保存到文件中：


	public static void keyToFile(Map keymap)throws Exception{
	    PublicKey publicKey = (PublicKey) keymap.get(PUBLIC_KEY);
	    PrivateKey privateKey = (PrivateKey)keymap.get(PRIVATE_KEY);
	    String encryptBase64PublicKey = encryptBase64(publicKey.getEncoded());
	    String encryptBase64PrivateKey = encryptBase64(privateKey.getEncoded());
	    FileWriter fw = new FileWriter("pubKey.txt");//按需要保存路径，下同
	    FileWriter fw1 = new FileWriter("priKey.txt");
	    fw.write(encryptBase64PublicKey);
	    fw.flush();
	    fw.close();
	    fw1.write(encryptBase64PrivateKey);
	    fw1.flush();
	    fw1.close();
	}
	//4、从文件中获取公钥：


	public static RSAPublicKey getPublicKeyForFile() throws Exception {
	    File file = new File("pubKey.txt");

	    FileInputStream fis = new FileInputStream(file);
	    BufferedReader br = new BufferedReader(new InputStreamReader(fis));
	    String readLine = null;
	    StringBuffer sb = new StringBuffer();
	    while ((readLine = br.readLine()) != null) {
	        sb.append(readLine);
	    }
	    String publicKeyStr = sb.toString();
	    System.out.println("从文件获取到的公钥：" + publicKeyStr);
	    //Base64解密
	    byte[] decryptBase64 = decryptBase64(publicKeyStr);
	    //选取密钥编码规则
	    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decryptBase64);
	    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	    RSAPublicKey rsaPublicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
	    return rsaPublicKey;
	}
	//5、从文件中获取私钥：

	//从文件中获取私钥
	public static RSAPrivateKey getPrivateKeyForFile() throws Exception {
	    FileInputStream fis = new FileInputStream("priKey.txt");
	    BufferedReader br = new BufferedReader(new InputStreamReader(fis));
	    String readLine = null;
	    StringBuffer sb = new StringBuffer();
	    while ((readLine = br.readLine()) != null) {
	        sb.append(readLine);
	    }
	    String privateKey = sb.toString();
	    System.out.println("从文件获取到的私钥：" + privateKey);
	    //Base64解密
	    byte[] bytes = decryptBase64(privateKey);
	    PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(bytes);
	    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	    RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8EncodedKeySpec);
	    return rsaPrivateKey;
	}

	//6.公钥加密/私钥解密：

	public static byte[] encryptOrDecrypt(Key key, byte[] data, int mode) throws Exception {
		//加密算法：RSA/ECB/PKCS1Padding
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    //提供者
	    Provider provider = new BouncyCastleProvider();
	    Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", provider);
	    cipher.init(mode, key);
	    int blockSize = cipher.getBlockSize();
	    int length = data.length;
	    int num = length / blockSize + 1;
	    int cache = blockSize;
	    byte[] bytes = null;
	    for (int i = 0; i < num; i++) {
	        if (i == num - 1) {
	            cache = length % blockSize;
	            if (cache == 0) {
	                return out.toByteArray();
	            }
	        }
	        bytes = cipher.doFinal(data, i * blockSize, cache);
	        out.write(bytes);
	    }
	    return out.toByteArray();
	}
	//7.RSA私钥加签：
	 public static String addSign(PrivateKey privateKey, String context)throws Exception{
	    //加签算法：SHA1WithRSA
		Signature signature = Signature.getInstance("SHA1WithRSA");
	        signature.initSign(privateKey);
	        signature.update(context.getBytes("utf-8"));
	        byte[] sign = signature.sign();
	        return encryptBase64(sign);
	    }
	//8.RSA公钥验签：
	public static boolean verifySign(PublicKey publicKey, String context, String signData)throws Exception{
	        Signature signature = Signature.getInstance("SHA1WithRSA");
	        signature.initVerify(publicKey);
	        byte[] bytes = decryptBase64(signData);
	        signature.update(context.getBytes("utf-8"));
	        boolean verify = signature.verify(bytes);
	        return verify;
	    }
//	9.主测试类：  
	  public static void main(String[] args) {        
		try {	
			Map<String, Object> map = keyMap();
			keyToFile(map);
			RSAPublicKey rsaPublicKey = getPublicKeyForFile();		
			System.out.println("rsaPublicKey公钥是：" + rsaPublicKey);		
			RSAPrivateKey rsaPrivateKey = getPrivateKeyForFile();		
			System.out.println("rsaPublicKey私钥是：" + rsaPrivateKey);		
			String data = "这是一个加密字符串";		
			//加密		
			byte[] encrypt = encryptOrDecrypt(rsaPublicKey, data.getBytes("utf-8"), Cipher.ENCRYPT_MODE);			
			String s = encryptBase64(encrypt);
			System.out.println("加密后的数据：" + s);		
			//解密		
			byte[] decrypt = encryptOrDecrypt(rsaPrivateKey, decryptBase64(s), Cipher.DECRYPT_MODE);		System.out.println("解密后的数据" + new String(decrypt));				
			//加签		
			String signData = addSign(rsaPrivateKey, data);			
			System.out.println("加签后的数据：" + signData);				
			//验签		
			boolean b = verifySign(rsaPublicKey, data, signData);
			System.out.println("验签得到的布尔值为：" + b);


	 	} catch (Exception e) {
	        e.printStackTrace();
	    }
	}


}
