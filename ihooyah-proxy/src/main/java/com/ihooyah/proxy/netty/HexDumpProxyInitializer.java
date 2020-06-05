package com.ihooyah.proxy.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.stereotype.Component;

public class HexDumpProxyInitializer extends ChannelInitializer<SocketChannel> {
    private final String remoteHost;
    private final int remotePort;

    public HexDumpProxyInitializer(String remoteHost, int remotePort) {
        this.remoteHost = remoteHost;
        this.remotePort = remotePort;
    }

    @Override
    public void initChannel(SocketChannel ch) {
        // 获取当前类名；
        System.out.println(this.getClass());
        ch.pipeline().addLast(
                new LoggingHandler(LogLevel.INFO),
                new HexDumpProxyFrontendHandler(remoteHost, remotePort)
        );
    }
}