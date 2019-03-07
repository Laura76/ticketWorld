package action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.sun.org.apache.xml.internal.security.encryption.EncryptedData;

import edu.nju.onlinestock.factory.ServiceFactory;
import edu.nju.onlinestock.model.Vip;
import edu.nju.onlinestock.service.VipService;
import util.VipStateEnum;

public class VipRegisterAction extends BaseAction{
    private VipService vipService=ServiceFactory.getVipService();

    public String execute(){
    		//邮箱验证后才可以注册成功
        try{
            String vipName=request.getParameter("vipName");
            String passwd=request.getParameter("passwd");
            String bankCardId=request.getParameter("bankCardId");
            String email=request.getParameter("email");
            Calendar c = Calendar.getInstance();  
            long time = c.getTimeInMillis();  
            //创建激活码  
            String emailCode=email+passwd+time;
            //过期时间为24小时后  
            //int token_exptime=(int)(time+1000*60*60*24);  
            String emailCode_exptime=(time+1000*20)+"";    //这里测试是用的20秒  
              
            Vip vip=new Vip();
            vip.setVipNum(vipService.getVipNum());
            vip.setVipName(vipName);
            vip.setVipPassword(passwd);
            vip.setBankCardId(bankCardId);
            vip.setState(VipStateEnum.REGISTER.toString());
            vip.setVipPoint(0);
            vip.setMoney(0);
            vip.setVipLevel(0);
            
            vip.setEmail(email);
            vip.setEmailState(0);
            vip.setEmailCode(emailCode);
            vip.setEmailCode_exptime(emailCode_exptime);
            
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
            StringBuffer sb=new StringBuffer("<div style=\"width:660px;overflow:hidden;border-bottom:1px solid #bdbdbe;\"><div style=\"height:52px;overflow:hidden;border:1px solid #464c51;background:#353b3f url(http://www.lofter.com/rsc/img/email/hdbg.png);\"><a href=\"http://www.lofter.com?mail=qbclickbynoticemail_20120626_01\" target=\"_blank\" style=\"display:block;width:144px;height:34px;margin:10px 0 0 20px;overflow:hidden;text-indent:-2000px;background:url(http://www.lofter.com/rsc/img/email/logo.png) no-repeat;\">LOFTER</a></div>"+"<div style=\"padding:24px 20px;\">您好，"+email+"<br/><br/><br/><br/>请点击下面链接激活账号，24小时生效，否则重新注册账号，链接只能使用一次，请尽快激活！</br>");  
            sb.append("<a href=\"http://localhost:8080/onlineStockTradeWebH/jsp/emailValidate?op=activate&id=");  
            sb.append(vipService.getVipNum());  
            sb.append("&token=");  
            sb.append(emailCode);  
            sb.append("\">http://localhost:8080/onlineStockTradeWebH/WEB-INF/classes/action/EmailValidate.java?op=activate&id=");  
            sb.append(vipService.getVipNum());  
            sb.append("&token=");  
            sb.append(emailCode);  
            sb.append("</a>"+"<br/>如果以上链接无法点击，请把上面网页地址复制到浏览器地址栏中打开<br/><br/><br/><br/>"+sdf.format(new Date())+ "</div></div>" );  
            //发送邮件  
            SendEmail.send(email, sb.toString());  
            
            vipService.registerVip(vip);
            request.getSession().setAttribute("id",vip.getVipNum());
            return "error";
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
        
        
    }
}
