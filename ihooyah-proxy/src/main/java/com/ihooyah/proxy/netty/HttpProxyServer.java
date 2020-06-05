package com.ihooyah.proxy.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * A simple http proxy server.
 *
 * @author shuaicj 2017/09/21
 */
@Component
@Slf4j
public class HttpProxyServer {

    @Value("${proxy.port}") private int port;
    @Value("${remoteHost}") private String remoteHost;
    @Value("${remotePort}") private int remotePort;

    @PostConstruct
    public void start() {
        new Thread(() -> {
            log.info("HttpProxyServer started on port: {}", port);
            System.out.println("port="+port+"remoteHost="+remoteHost+"remotePort="+remotePort);
            EventLoopGroup bossGroup = new NioEventLoopGroup(1);
            EventLoopGroup workerGroup = new NioEventLoopGroup();
            try {
                ServerBootstrap b = new ServerBootstrap();
                b.group(bossGroup, workerGroup)
                        .channel(NioServerSocketChannel.class)
                        .handler(new LoggingHandler(LogLevel.DEBUG))
                        .childHandler(new HexDumpProxyInitializer(remoteHost, remotePort))
                        .childOption(ChannelOption.AUTO_READ, false)
                        .bind(port).sync().channel().closeFuture().sync();
            } catch (InterruptedException e) {
                log.error("shit happens", e);
            } finally {
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
            }
        }).start();
    }
}
