package cn.szcloudtech.seclvlite.service;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

public class SecLevelMidHandler extends MessageToMessageDecoder<ByteBuf> {


	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
		msg = msg.copy(1, msg.readableBytes() - 1);//舍弃第一位STX起始符
		out.add(msg);
	}

}
