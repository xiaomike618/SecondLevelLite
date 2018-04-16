package cn.szcloudtech.seclvlite.service;

import cn.szcloudtech.ctlog.CTLog;
import cn.szcloudtech.seclvlite.util.Global;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ForwardConnector {
	private final String TAG = this.getClass().getSimpleName();
	private static final int CONNECT_TIMEOUT = 500;
	private EventLoopGroup loopGroup;
	private ForwardConnector() {}
	private static ForwardConnector instance;
	
	public synchronized static ForwardConnector getInstance() {
		if (instance == null) {
			instance = new ForwardConnector();
		}
		return instance;
	}
	
	public void start(String ip, int port) {
		try {
			final ForwardHandler handler = new ForwardHandler();
			Bootstrap b = new Bootstrap();
			loopGroup = new NioEventLoopGroup();
			b.group(loopGroup)
			.channel(NioSocketChannel.class)
			.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel channel) throws Exception {
					channel.pipeline().addLast(handler);
				}
			})
			.option(ChannelOption.SO_KEEPALIVE, true)
			.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, CONNECT_TIMEOUT);
			ChannelFuture future = b.connect(ip, port).sync();
			Global.isForwardRunning = true;
			future.channel().closeFuture().sync();
		} catch (Exception e) {
			CTLog.e(TAG, e);
		} finally {
			loopGroup.shutdownGracefully();
		}
	}
	
	public void stop() {
		if (loopGroup != null) {
			loopGroup.shutdownGracefully();
		}
		Global.isForwardRunning = false;
	}
}
