package wxservlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.WxMenu;
import me.chanjar.weixin.common.bean.WxMenu.WxMenuButton;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.StringUtils;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutTextMessage;

/**
 * Servlet implementation class yrfz
 */
public class yrfz extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected WxMpInMemoryConfigStorage wxMpConfigStorage;
	protected WxMpService wxMpService;
	protected WxMpMessageRouter wxMpMessageRouter;

	@Override
	public void init() throws ServletException {
		super.init();
		//添加时间戳防止微信浏览器缓存网页
		Date date = new Date();
		String wxpost = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		wxpost = df.format(date);
		
		wxMpConfigStorage = new WxMpInMemoryConfigStorage();
		wxMpConfigStorage.setAppId("wxb3763506ea43ba7c"); // 设置微信公众号的appid
		wxMpConfigStorage.setSecret("cb2b058da1fe54fb56320b98bae45220"); // 设置微信公众号的appcorpSecret
		wxMpConfigStorage.setToken("sharktrade"); // 设置微信公众号的token
		wxMpConfigStorage.setAesKey("y0WwxSVUo2qRNfZIOSJXDS2FYcSzKNLrdXRcjFZE7x8"); //设置微信公众号的EncodingAESKey

		wxMpService = new WxMpServiceImpl();
		wxMpService.setWxMpConfigStorage(wxMpConfigStorage);
		
		ArrayList<WxMenuButton> x5Meuns = new ArrayList<WxMenuButton>();

		WxMenuButton indexPage = new WxMenuButton();
		indexPage.setName("如意投资");
		indexPage.setType(WxConsts.BUTTON_VIEW);
		
		indexPage.setUrl("http://103.6.222.80:8777/www/x5/UI2/sharkstrade/index.w?v=" + wxpost);
		x5Meuns.add(indexPage);

		WxMenu x5Menu = new WxMenu();
		x5Menu.setButtons(x5Meuns);
		
		try {
			wxMpService.menuDelete();
			wxMpService.menuCreate(x5Menu);
		} catch (WxErrorException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		
		WxMpMessageHandler handler = new WxMpMessageHandler() {
			@Override
			public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService) {
				WxMpXmlOutTextMessage m = WxMpXmlOutMessage.TEXT().content("测试加密消息").fromUser(wxMessage.getToUserName()).toUser(wxMessage.getFromUserName()).build();
				return m;
			}
		};
		
		wxMpMessageRouter = new WxMpMessageRouter(wxMpService);
		wxMpMessageRouter.rule().async(false).content("哈哈") // 拦截内容为“哈哈”的消息
				.handler(handler).end();

	}

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);

		String signature = request.getParameter("signature");
		String nonce = request.getParameter("nonce");
		String timestamp = request.getParameter("timestamp");

		if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
			// 消息签名不正确，说明不是公众平台发过来的消息
			response.getWriter().println("非法请求");
			return;
		}

		String echostr = request.getParameter("echostr");
		if (StringUtils.isNotBlank(echostr)) {
			// 说明是一个仅仅用来验证的请求，回显echostr
			response.getWriter().println(echostr);
			return;
		}

		String encryptType = StringUtils.isBlank(request.getParameter("encrypt_type")) ? "raw" : request.getParameter("encrypt_type");

		if ("raw".equals(encryptType)) {
			// 明文传输的消息
			WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(request.getInputStream());
			WxMpXmlOutMessage outMessage = wxMpMessageRouter.route(inMessage);
			response.getWriter().write(outMessage.toXml());
			return;
		}

		if ("aes".equals(encryptType)) {
			// 是aes加密的消息
			String msgSignature = request.getParameter("msg_signature");
			WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(request.getInputStream(), wxMpConfigStorage, timestamp, nonce, msgSignature);
			WxMpXmlOutMessage outMessage = wxMpMessageRouter.route(inMessage);
			response.getWriter().write(outMessage.toEncryptedXml(wxMpConfigStorage));
			return;
		}

		response.getWriter().println("不可识别的加密类型");
		return;
	}
}
