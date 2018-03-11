package engine.net;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.activation.MimetypesFileTypeMap;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

public class HttpFileRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
	private static final String CRLF = "\r\n";
	private String localDir;

	private static SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

	public HttpFileRequestHandler(String localDir) {
		this.localDir = localDir;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {

		// 解码不成功
		if (!req.decoderResult().isSuccess()) {
			sendErrorToClient(ctx, HttpResponseStatus.BAD_REQUEST);
			return;
		}
		if (req.method().compareTo(HttpMethod.GET) != 0) {
			sendErrorToClient(ctx, HttpResponseStatus.METHOD_NOT_ALLOWED);
			return;
		}
		String uri = req.uri();
		uri = URLDecoder.decode(uri, "utf-8");
		String filePath = getFilePath(uri);
		File file = new File(filePath);
		// 如果文件不存在
		if (!file.exists()) {
			sendErrorToClient(ctx, HttpResponseStatus.NOT_FOUND);
			return;
		}
		// 如果是目录，则显示子目录
		if (file.isDirectory()) {
			sendDirListToClient(ctx, file, uri);
			return;
		}
		// 如果是文件，则将文件流写到客户端
		if (file.isFile()) {
			sendFileToClient(ctx, file, uri);
			return;
		}
		ctx.close();

	}

	public String getFilePath(String uri) throws Exception {
		return localDir + uri;
	}

	private void sendErrorToClient(ChannelHandlerContext ctx, HttpResponseStatus status) throws Exception {
		ByteBuf buffer = Unpooled.copiedBuffer(("系统服务出错：" + status.toString() + CRLF).getBytes("utf-8"));
		FullHttpResponse resp = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, buffer);
		resp.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=utf-8");
		ctx.writeAndFlush(resp).addListener(ChannelFutureListener.CLOSE);
	}

	private void sendDirListToClient(ChannelHandlerContext ctx, File dir, String uri) throws Exception {
		StringBuffer sb = new StringBuffer("");
		String dirpath = dir.getPath();
		// sb.append("<!DOCTYPE HTML>" + CRLF);
		// sb.append("<html><head><title>");
		// sb.append(dirpath);
		// sb.append("目录：");
		// sb.append("</title></head><body>" + CRLF);

		sb.append("<h3>");
		sb.append("当前目录>>" + dirpath);
		sb.append("</h3>");
		sb.append("<table>");
		sb.append("<tr><td colspan='3'><a href=\"../\">上一级..</a>  </td></tr>");
		if (uri.equals("/")) {
			uri = "";
		} else {
			if (uri.charAt(0) == '/') {
				uri = uri.substring(0);
			}
			uri += "/";
		}

		String fnameShow;
		for (File f : dir.listFiles()) {
			if (f.isHidden() || !f.canRead()) {
				continue;
			}
			String fname = f.getName();
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(f.lastModified());
			String lastModified = sdf.format(cal.getTime());
			sb.append("<tr>");
			if (f.isFile()) {
				fnameShow = "<font color='green'>" + fname + "</font>";
			} else {
				fnameShow = "<font color='red'>" + fname + "</font>";
			}
			sb.append("<td style='width:200px'> " + lastModified + "</td><td style='width:100px'>" + Files.size(f.toPath()) + "</td><td><a href=\"" + uri + fname + "\">" + fnameShow + "</a></td>");
			sb.append("</tr>");

		}
		sb.append("</table>");
		ByteBuf buffer = Unpooled.copiedBuffer(sb.toString(), CharsetUtil.UTF_8);
		FullHttpResponse resp = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buffer);
		resp.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=utf-8");
		ctx.writeAndFlush(resp).addListener(ChannelFutureListener.CLOSE);
	}

	private void sendFileToClient(ChannelHandlerContext ctx, File file, String uri) throws Exception {

		// System.out.println("文件名: " + file.getPath());

		ByteBuf buffer;
		FullHttpResponse resp;

		// 文本文件
		if (file.getName().indexOf(".css") > 0 || file.getName().indexOf(".js") > 0) {
			Long filelength = file.length();
			byte[] filecontent = new byte[filelength.intValue()];
			FileInputStream in = new FileInputStream(file);
			in.read(filecontent);
			in.close();

			String s = new String(filecontent, CharsetUtil.UTF_8);
			// System.out.println(s);

			buffer = Unpooled.copiedBuffer(s, CharsetUtil.UTF_8);
			resp = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buffer);

			if (file.getName().indexOf(".html") > 0) {
				resp.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=utf-8");
			}

		} else {

			buffer = Unpooled.copiedBuffer(Files.readAllBytes(file.toPath()));
			resp = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buffer);
			MimetypesFileTypeMap mimeTypeMap = new MimetypesFileTypeMap();
			resp.headers().set(HttpHeaderNames.CONTENT_TYPE, mimeTypeMap.getContentType(file));

		}

		resp.headers().set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN, "*");// 跨域权限
		ctx.writeAndFlush(resp).addListener(ChannelFutureListener.CLOSE);

	}
}
