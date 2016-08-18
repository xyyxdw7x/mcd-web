package com.asiainfo.biapp.framework.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.StringRefAddr;

import org.apache.commons.codec.binary.Hex;

import org.apache.tomcat.dbcp.dbcp.BasicDataSourceFactory;

import com.asiainfo.biapp.framework.util.DESBase64Util;

public class EncryptedDataSourceFactory extends BasicDataSourceFactory {

	@SuppressWarnings("rawtypes")
	@Override
	public Object getObjectInstance(Object obj, Name name, Context nameCtx,
			Hashtable environment) throws Exception {
		if(obj instanceof Reference){
			 setPassword((Reference) obj);
		}
		return super.getObjectInstance(obj, name, nameCtx, environment);
	}
	/**
	 * 
	 * @param ref
	 * @throws Exception
	 */
	private void setPassword(Reference ref) throws Exception {
		findDecryptAndReplace("password", ref);
	}
	/**
	 * 
	 * @param refType
	 * @param ref
	 * @throws Exception
	 */
	private void findDecryptAndReplace(String refType, Reference ref) throws Exception {
		int idx = find(refType, ref);
		String decrypted = decrypt(idx, ref);
		replace(idx, refType, decrypted, ref);
	}
	/**
	 * 
	 * @param idx
	 * @param refType
	 * @param newValue
	 * @param ref
	 * @throws Exception
	 */
	private void replace(int idx, String refType, String newValue, Reference ref) throws Exception {
		ref.remove(idx);
		ref.add(idx, new StringRefAddr(refType, newValue));
	}
	/**
	 * 
	 * @param idx
	 * @param ref
	 * @return
	 * @throws Exception
	 */
	private String decrypt(int idx, Reference ref) throws Exception {
		String info=ref.get(idx).getContent().toString();
		String newStr=DESBase64Util.decodeInfo(info);
		return newStr;
	}
	/**
	 * 
	 * @param addrType
	 * @param ref
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	private int find(String addrType, Reference ref) throws Exception {
		Enumeration enu = ref.getAll();
		int index=-1;
		for (int i = 0; enu.hasMoreElements(); i++) {
			RefAddr addr = (RefAddr) enu.nextElement();
			if (addr.getType().compareTo(addrType) == 0) {
				index= i;
				break;
			}
		}
		return index;
    }
	
	/**
	 * 对一个输入流进行md5加密
	 * @param is
	 * @param md
	 * @param byteArraySize
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	private	String	getDigest(InputStream is, MessageDigest md, int byteArraySize)
			throws NoSuchAlgorithmException, IOException {
		md.reset();
		byte[] bytes = new byte[byteArraySize];
		int numBytes;
		while ((numBytes = is.read(bytes)) != -1) {
			md.update(bytes, 0, numBytes);
		}
		byte[] digest = md.digest();
		String result = new String(Hex.encodeHex(digest));
		return result;
	}
	
	/**
	 * 加密一个字符串
	 * @param oldPwd
	 * @return
	 * @throws NoSuchAlgorithmException 
	 */
	private String encryptedString(String oldString) throws NoSuchAlgorithmException{
		String newStr=null;
		MessageDigest md=MessageDigest.getInstance("MD5");
		md.reset();
		md.update(oldString.getBytes());
		byte[] digest = md.digest();
		newStr = new String(Hex.encodeHex(digest));
		return newStr;
	}
	
	public static void main(String[] args) throws NoSuchAlgorithmException {
		EncryptedDataSourceFactory edsf=new EncryptedDataSourceFactory();
		String oldString="mcd/Mcd@2016";
		String newStr=edsf.encryptedString(oldString);
		System.out.println(newStr);
	}
}
