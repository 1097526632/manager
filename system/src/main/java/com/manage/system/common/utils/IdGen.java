package com.manage.system.common.utils;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * 封装各种生成唯一性ID算法的工具类.
 */
@Service
public class IdGen {

	private static SecureRandom random = new SecureRandom();

	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 使用SecureRandom随机生成Long.
	 */
	public static long randomLong() {
		return Math.abs(random.nextLong());
	}

	/**
	 * 基于Base62编码的SecureRandom随机生成bytes.
	 */
	public static String randomBase62(int length) {
		byte[] randomBytes = new byte[length];
		random.nextBytes(randomBytes);
		return Encodes.encodeBase62(randomBytes);
	}

	/**
	 * Activiti ID 生成
	 */
	public String getNextId() {
		return IdGen.uuid();
	}

	/**
	 * 机器码
	 * @return
	 */
	public static String getMachineCode(){
		String macInfo=MacUtils.getOs()+"-"+MacUtils.getHardSN()+"-"+MacUtils.getMboardSN()+"-"+MacUtils.getCpuSN();
		return  encodeMac(macInfo);
	}

	private static String encodeMac(String code){
		String tempCode=code;
		for(int i=0;i<5;i++){
			tempCode=Encodes.encodeBase64(tempCode);
		}
		return  tempCode;
	}

	public static void main(String[] args) {
		System.out.println(getMachineCode());
	/*	System.out.println(IdGen.uuid());
		System.out.println(IdGen.uuid().length());
		System.out.println(new IdGen().getNextId());
		for (int i=0; i<1000; i++){
			System.out.println(IdGen.randomLong() + "  " + IdGen.randomBase62(5));
		}*/
	}

}
