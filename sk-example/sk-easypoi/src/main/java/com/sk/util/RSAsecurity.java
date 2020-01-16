package com.sk.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RSAsecurity {

    public static Map<String, String> getKey(int length) {
        try {
            //1.初始化秘钥

            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");

            //秘钥长度
            keyPairGenerator.initialize(length);
            //初始化秘钥对
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            //公钥
            RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
            //私钥
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();

            byte[] publicKeyByte = rsaPublicKey.getEncoded();

            byte[] privateKeyByte = rsaPrivateKey.getEncoded();

            String publicKey = Base64.encodeBase64String(publicKeyByte);

            String privateKey = Base64.encodeBase64String(privateKeyByte);

            Map<String, String> map = new HashMap<String, String>();

            map.put("publicKey", publicKey);

            map.put("privateKey", privateKey);

            return map;

        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new HashMap<String, String>();
    }

    public static void main(String[] args) {
        //Map<String,String> map = RSAsecurity.getKey(1024);
        //System.out.println(map.get("publicKey"));
        RSAsecurity.pubENpriDE();
        //System.out.println("---");
    }

    /*RSA 工具类。提供加密，解密，生成密钥对等方法。
    RSA加密原理概述
    RSA的安全性依赖于大数的分解，公钥和私钥都是两个大素数（大于100的十进制位）的函数。
    据猜测，从一个密钥和密文推断出明文的难度等同于分解两个大素数的积
    密钥的产生：
    1.选择两个大素数 p,q ,计算 n=p*q;
    2.随机选择加密密钥 e ,要求 e 和 (p-1)*(q-1)互质
    3.利用 Euclid 算法计算解密密钥 d , 使其满足 e*d = 1(mod(p-1)*(q-1)) (其中 n,d 也要互质)
    4:至此得出公钥为 (n,e) 私钥为 (n,d)
    RSA速度
    * 由于进行的都是大数计算，使得RSA最快的情况也比DES慢上100倍，无论 是软件还是硬件实现。
    * 速度一直是RSA的缺陷。一般来说只用于少量数据 加密。*/

    public static void priENpubDE() {
        String src = "RSA 加密字符串";
        try {
            //1.初始化秘钥
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            //秘钥长度
            keyPairGenerator.initialize(1024);
            //初始化秘钥对
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            //公钥
            RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
            String publicKeyStr = Base64.encodeBase64String(rsaPublicKey.getEncoded());
            //私钥
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
            String privateKeyStr = Base64.encodeBase64String(rsaPrivateKey.getEncoded());

            //2.私钥加密，公钥解密----加密
            //生成私钥
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyStr));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            //Cipher类为加密和解密提供密码功能，通过getinstance实例化对象
            Cipher cipher = Cipher.getInstance("RSA");
            //初始化加密
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] result = cipher.doFinal(src.getBytes());
            System.out.println("私钥加密，公钥解密----加密:" + Base64.encodeBase64String(result));

            //3.私钥加密，公钥解密----解密
            //生成公钥
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyStr));
            keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            cipher = Cipher.getInstance("RSA");
            //初始化解密
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            result = cipher.doFinal(result);
            System.out.println("私钥加密，公钥解密----解密:" + new String(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void pubENpriDE() {
        String src = "RSA 加密字符串";
        try {
            //1.初始化秘钥
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            //秘钥长度
            keyPairGenerator.initialize(512);
            //初始化秘钥对
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            //公钥
            RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
            //私钥
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();

            //2.公钥加密，私钥解密----加密
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            //初始化加密
            //Cipher类为加密和解密提供密码功能，通过getinstance实例化对象
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            //加密字符串
            byte[] result = cipher.doFinal(src.getBytes());
            System.out.println("公钥加密，私钥解密----加密:" + Base64.encodeBase64String(result));

            //3.公钥加密，私钥解密-----解密
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
            keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            //初始化解密
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            //解密字符串
            result = cipher.doFinal(result);
            System.out.println("公钥加密，私钥解密-----解密:" + new String(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
