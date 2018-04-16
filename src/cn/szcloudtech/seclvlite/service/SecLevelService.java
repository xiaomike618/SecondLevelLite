package cn.szcloudtech.seclvlite.service;

import cn.szcloudtech.ctlog.CTLog;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class SecLevelService {
	private final String TAG = this.getClass().getSimpleName();
	private static SecLevelService instance;
	
	private SecLevelService() {}
	
	public synchronized static SecLevelService getInstance() {
		if (instance == null) {
			instance = new SecLevelService();
		}
		return instance;
	}
	
	private EventLoopGroup mBossGroup;
	private EventLoopGroup mWorkerGroup;
	private final static int BACKLOG_SIZE = 128;
	private final static int MAX_FRAME_LEN = 10240;
	private final static byte[] DELIMITER = new byte[] {0x03};//ascii 码表对应的ETX，正文结束符
	
	public void start(int port) {
		new Thread("netty-thread") {
			@Override
			public void run() {
				super.run();
				mBossGroup = new NioEventLoopGroup();
				mWorkerGroup = new NioEventLoopGroup();
				try {
					ServerBootstrap b = new ServerBootstrap();
					b.group(mBossGroup, mWorkerGroup)
					.channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel socketChannel) throws Exception {
							ByteBuf delimiter = Unpooled.copiedBuffer(DELIMITER);
							socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(MAX_FRAME_LEN, delimiter));
							socketChannel.pipeline().addLast(new SecLevelMidHandler());
							socketChannel.pipeline().addLast(new StringDecoder());
							socketChannel.pipeline().addLast(new SecLevelHandler());
						}
					})
					.option(ChannelOption.SO_BACKLOG, BACKLOG_SIZE)
					.childOption(ChannelOption.SO_KEEPALIVE, true);
					
					ChannelFuture f = b.bind(port).sync();
					f.channel().closeFuture().sync();
				} catch (InterruptedException e) {
					CTLog.e(TAG, e);
				} finally {
					mWorkerGroup.shutdownGracefully();
					mBossGroup.shutdownGracefully();
				}
			}
		}.start();
		SecLevelDecodeManager.getInstance().start();
	}
	
	public void stop() {
		if (mWorkerGroup != null) {
			mWorkerGroup.shutdownGracefully();
		}
		if (mBossGroup != null) {
			mBossGroup.shutdownGracefully();
		}
		SecLevelDecodeManager.getInstance().stop();
	}
}
