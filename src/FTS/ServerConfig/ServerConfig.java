/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FTS.ServerConfig;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Prafful
 */
public class ServerConfig {
    private static final String ENCRYPTION_KEY = "18$hadow$oft1998";
    private static final String CHARACTER_ENCODING = "UTF-8";
    private static final String CIPHER_TRANSFORMATION = "AES/CBC/PKCS5PADDING";
    private static final String AES_ENCRYPTION_ALGORITHM = "AES";
    public final String Filepath="C://FTS//";
    public final String ServerConfigXmlDir=Filepath+"ServerConfig.xml";
    File ServerConfigXmlFile = new File(ServerConfigXmlDir);
    DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder documentBuilder;
    public boolean ConfigExists()
    {
        boolean exists = ServerConfigXmlFile.exists();
        return exists;
    }
    public static String encrypt(String strClearText)
    {
	String strData="";
	
	try {
            Cipher cipher   = Cipher.getInstance(CIPHER_TRANSFORMATION);
            byte[] key      = ENCRYPTION_KEY.getBytes(CHARACTER_ENCODING);
            SecretKeySpec secretKey = new SecretKeySpec(key, AES_ENCRYPTION_ALGORITHM);
            IvParameterSpec ivparameterspec = new IvParameterSpec(key);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivparameterspec);
            byte[] cipherText = cipher.doFinal(strClearText.getBytes("UTF8"));
            Base64.Encoder encoder = Base64.getEncoder();
            strData = encoder.encodeToString(cipherText);
		
	} catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException E) {
            System.err.println("Encrypt Exception : "+E.getMessage());
            E.printStackTrace();
        }
	return strData;
    }
    public static String decrypt(String strEncrypted)
    {
	String strData="";
	
	try {
            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
            byte[] key = ENCRYPTION_KEY.getBytes(CHARACTER_ENCODING);
            SecretKeySpec secretKey = new SecretKeySpec(key, AES_ENCRYPTION_ALGORITHM);
            IvParameterSpec ivparameterspec = new IvParameterSpec(key);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivparameterspec);
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] cipherText = decoder.decode(strEncrypted.getBytes("UTF8"));
            strData = new String(cipher.doFinal(cipherText), "UTF-8");
		
	} catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException E) {
             System.err.println("Decrypt Exception : "+E.getMessage());
        }
	return strData;
    }
    public boolean WriteXmlProcess(String Host, String Port, String User, String Password)
    {
        try
        {
            new File(Filepath).mkdirs();
            documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            Element root = document.createElement("MysqlConfig");
            document.appendChild(root);
            Element host = document.createElement("host");
            host.appendChild(document.createTextNode(Host));
            root.appendChild(host);
            Element port = document.createElement("port");
            port.appendChild(document.createTextNode(Port));
            root.appendChild(port);
            Element user = document.createElement("user");
            user.appendChild(document.createTextNode(User));
            root.appendChild(user);
            Element password = document.createElement("password");
            password.appendChild(document.createTextNode(encrypt(Password)));
            root.appendChild(password);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(ServerConfigXmlDir));
            transformer.transform(domSource, streamResult);
            System.out.println("Done creating XML File");
            return true;
        } 
        catch (ParserConfigurationException | TransformerException | DOMException ex) {
            System.out.println(ex.getMessage());
            return false;
        }        
    }
    public boolean UpdateXmlProcess(String Host, String Port, String User, String Password)
    {        
        return false;
    }
    public String getConnectionLink()
    {
        String link="";
        try 
        {
            documentBuilder = documentFactory.newDocumentBuilder();
            Document doc = documentBuilder.parse(ServerConfigXmlFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("MysqlConfig");

            for (int temp = 0; temp < nList.getLength(); temp++) {

		Node nNode = nList.item(temp);				
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {

			Element eElement = (Element) nNode;
                        link="jdbc:mysql://"+eElement.getElementsByTagName("host").item(0).getTextContent()+":"+eElement.getElementsByTagName("port").item(0).getTextContent()+"/fms";
		}
            }
        } 
        catch (IOException | ParserConfigurationException | DOMException | SAXException e) {
        }
        return link;
    }
    public String getMySQlUser()
    {
        String username="";
        try 
        {
            documentBuilder = documentFactory.newDocumentBuilder();
            Document doc = documentBuilder.parse(ServerConfigXmlFile);
            doc.getDocumentElement().normalize();NodeList nList = doc.getElementsByTagName("MysqlConfig");

            for (int temp = 0; temp < nList.getLength(); temp++) {

		Node nNode = nList.item(temp);				
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {

			Element eElement = (Element) nNode;
                        username=eElement.getElementsByTagName("user").item(0).getTextContent();
		}
            }
        } 
        catch (IOException | ParserConfigurationException | DOMException | SAXException e) {
        }
        return username;
    }
    public String getMySQlPassword()
    {
        String pwd="";
        try 
        {
            documentBuilder = documentFactory.newDocumentBuilder();
            Document doc = documentBuilder.parse(ServerConfigXmlFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("MysqlConfig");

            for (int temp = 0; temp < nList.getLength(); temp++) {

		Node nNode = nList.item(temp);				
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {

			Element eElement = (Element) nNode;
                        pwd=decrypt(eElement.getElementsByTagName("password").item(0).getTextContent());
		}
            }
        } 
        catch (IOException | ParserConfigurationException | DOMException | SAXException e) {
        }
        return pwd;
    }    
}