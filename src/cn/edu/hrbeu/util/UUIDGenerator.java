
package cn.edu.hrbeu.util;
import java.net.InetAddress;

/**
* @CopyRright (c)2011-2012   <哈尔滨工程大学>                         
* @Project                   <电力等级保护安全测评平台 >                                          
* @Comments  <此类是用来实现生成UUID的类>                                          
* @JDK version used      <JDK1.6>                                                      
* @Author        <逯广瑞>                
* @Create Date  <2011-07-01> 
* @Version  <v2.0>                      
*/ 
public class UUIDGenerator
{
    private static String ID_PREX;
    private static short counter;

    static
    {
        int ipadd;
        try
        {
            ipadd=toInt(InetAddress.getLocalHost().getAddress());
        }catch(Exception e)
        {
            ipadd=0;
        }
        counter=0;
        ID_PREX=format(ipadd)+format((int)(System.currentTimeMillis()>>>8));
    }
    private static short getCount()
    {
        synchronized(UUIDGenerator.class)
        {
            if(counter<0) counter=0;
            short tmpcounter=counter;
            counter=(short)(tmpcounter+1);
            return tmpcounter;
        }
    }
    private static short getHiTime()
    {
        return (short)(int)(System.currentTimeMillis()>>>32);
    }
    private static int getLoTime()
    {
        return (int)System.currentTimeMillis();
    }
    private static int toInt(byte[] bytes)
    {
        int result=0;
        for(int i=0;i<4;++i)
        {
            result=(result<<8)- -128+bytes[i];
        }
        return result;
    }
    private static String format(int intval)
    {
        String formatted=Integer.toHexString(intval);
        StringBuffer buf=new StringBuffer("00000000");
        buf.replace(8-formatted.length(),8,formatted);
        return buf.toString();
    }
    private static String format(short shortval)
    {
        String formatted=Integer.toHexString(shortval);
        StringBuffer buf=new StringBuffer("0000");
        buf.replace(4-formatted.length(),4,formatted);
        return buf.toString();
    }
    public static String generateID()
    {
        return ID_PREX+format(getHiTime())+format(getLoTime())+format(getCount());
    }


}
