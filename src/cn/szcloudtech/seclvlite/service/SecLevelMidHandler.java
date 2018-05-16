package cn.szcloudtech.seclvlite.service;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

public class SecLevelMidHandler extends MessageToMessageDecoder<ByteBuf> {


	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
		while (msg.getByte(0) == 0x00) {//很诡异的事情，转发端第一个字节不是0，但是接收端却接到第一个是0，这里是把开头的0都去掉
			msg = msg.copy(1, msg.readableBytes() - 1);
		}
		msg = msg.copy(1, msg.readableBytes() - 1);//舍弃第一位STX起始符
		out.add(msg);
	}

}
