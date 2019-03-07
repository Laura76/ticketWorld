package action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.sun.org.apache.xml.internal.security.encryption.EncryptedData;

import edu.nju.onlinestock.factory.ServiceFactory;
import edu.nju.onlinestock.model.Vip;
import edu.nju.onlinestock.service.VipService;
import util.VipStateEnum;

public class EmailValidateAction extends BaseAction{
    private VipService vipService=ServiceFactory.getVipService();
    public String execute(){
    		//邮箱验证后才可以注册成功
        try{
            String vipNum=request.getParameter("id");
            String emailCode=request.getParameter("token");
            Vip vip=vipService.findVipById(vipNum);
            if(vip.getEmailCode().equals(emailCode)) {
            		vip.setEmailState(1);
            		vipService.updateVip(vip);
            		request.getSession().setAttribute("id",vipNum);
            		return "relogin";
            }
            return "error";
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
        
        
    }
}
