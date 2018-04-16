package cn.szcloudtech.seclvlite.service;

import cn.szcloudtech.seclvlite.util.Global;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class SecLevelHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		ChannelDetector.getInstance().updateChannel(ctx, System.currentTimeMillis());
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		ChannelDetector.getInstance().removeChannel(ctx);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		super.channelRead(ctx, msg);
		ChannelDetector.getInstance().updateChannel(ctx, System.currentTimeMillis());
		Global.sReportBuffer.offer((String)msg);
		if (Global.isForwardRunning) {
			byte[] temp = ((String)msg).getBytes();
			byte[] buf = new byte[temp.length + 2];
			buf[0] = 0x02;
			for (int i = 0; i < temp.length; i++) {
				buf[i + 1] = temp[i];
			}
			buf[temp.length] = 0x03;
			Global.pushForwardMessage(new String(buf));
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		super.channelReadComplete(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
		ChannelDetector.getInstance().removeChannel(ctx);
	}

}
