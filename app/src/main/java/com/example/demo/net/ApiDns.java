package com.example.demo.net;

import okhttp3.Dns;
import org.jetbrains.annotations.NotNull;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * 定义DNS，解决网络请求慢的问题
 * android 默认不支持Ipv6的地址访问
 * 当在不同的网络下解析出的地址可能存在IPv4和IPv6的集合，但IPv6默认为集合的第一个
 * 需要设置如果出现IPv4与IPv6同时存在时，把该集合中的第一个改为IPv4
 */
public class ApiDns implements Dns {
    @NotNull
    @Override
    public List<InetAddress> lookup(@NotNull String s) throws UnknownHostException {
        try {
            List<InetAddress> mInetAddressesList = new ArrayList<>();
            InetAddress[] mInetAddresses = InetAddress.getAllByName(s);
            //遍历获取的地址集合，把IPv4放在集合的第一个
            for (InetAddress address : mInetAddresses) {
                if (address instanceof Inet4Address) {
                    mInetAddressesList.add(0, address);
                } else {
                    mInetAddressesList.add(address);
                }
            }
            return mInetAddressesList;
        } catch (NullPointerException var4) {
            UnknownHostException unknownHostException = new UnknownHostException("Broken system behaviour");
            unknownHostException.initCause(var4);
            throw unknownHostException;
        }
    }
}