package com.meetkiki.conrrent.object;

import org.openjdk.jol.info.ClassLayout;

import java.math.BigDecimal;

public class ObjectHeadTest {

	private int intValue = 0;
	public Integer intValue2 = 999;
	private short s1=256;
	private Short s2=new Short("2222");
	private long l1=222222222222222L;
	private Long l2 = new Long(222222222222222L);
	public boolean isT = false;
	public Boolean isT2 = true;
	public byte b1=-128;
	public Byte b2=127;
	public char c1='a';
	public Character c2 = Character.MAX_VALUE;
	private float f1=22.22f;
	private Float f2=new Float("222.222");
	private double d1=22.222d;
	private Double d2 = new Double("2222.2222");
	private BigDecimal bigDecimal = BigDecimal.ONE;
	private String aa = "asdfasdfasdfasdfds";

	public static void main(String[] args) {
		ObjectHeadTest object = new ObjectHeadTest();
		//打印hashcode
		System.out.println(object.hashCode());
		//打印hashcode二进制
		System.out.println(Integer.toBinaryString(object.hashCode()));
		//打印hashcode十六进制
		System.out.println(Integer.toHexString(object.hashCode()));
		//查看字节序
		System.out.println("======================================");
		System.out.println(ClassLayout.parseClass(ObjectHeadTest.class).toPrintable());
		System.out.println("======================================");
		System.out.println(ClassLayout.parseInstance(object).toPrintable());
//		ClassLayout.parseInstance(object).
	}
}
