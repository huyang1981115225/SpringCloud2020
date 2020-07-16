package com.atguigu.springcloud.aop;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.soap.SOAPException;
import java.util.List;

/**
 * Created by huyang on 2019/4/25.
 */
public class AuthInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private static final String USERNAME="root";
    private static final String PASSWORD="admin";

    public AuthInterceptor() {
        //定义在哪个阶段进行拦截
        super(Phase.PRE_PROTOCOL);
    }

    @Override
    public void handleMessage(SoapMessage soapMessage) throws Fault {
        List<Header> headers = null;
        String username = null;
        String password = null;
        try {
            headers = soapMessage.getHeaders();
        } catch (Exception e) {
            log.error("getSOAPHeader error: {}",e.getMessage(),e);
        }

        if (headers == null) {
            throw new Fault(new IllegalArgumentException("找不到Header，无法验证用户信息"));
        }
        //获取用户名,密码
        for (Header header : headers) {
            SoapHeader soapHeader = (SoapHeader) header;
            Element e = (Element) soapHeader.getObject();
            NodeList usernameNode = e.getElementsByTagName("username");
            NodeList pwdNode = e.getElementsByTagName("password");
            username=usernameNode.item(0).getTextContent();
            password=pwdNode.item(0).getTextContent();
            if( StringUtils.isEmpty(username)||StringUtils.isEmpty(password)){
                log.error("用户名或密码为空");
                throw new Fault(new IllegalArgumentException("用户名或密码为空"));
            }
        }
        //校验用户名密码
        if(!((USERNAME).equals(username) && (PASSWORD).equals(password))){
            SOAPException soapExc = new SOAPException("用户名或密码不正确！");
            log.error("用户名或密码错误");
            throw new Fault(soapExc);
        }
    }
}
