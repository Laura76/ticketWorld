package action;

import edu.nju.onlinestock.factory.ServiceFactory;
import edu.nju.onlinestock.model.Hostel;
import edu.nju.onlinestock.model.Manager;
import edu.nju.onlinestock.model.Vip;
import edu.nju.onlinestock.service.HostelService;
import edu.nju.onlinestock.service.ManagerService;
import edu.nju.onlinestock.service.VipService;

import javax.servlet.http.HttpSession;

public class LoginAction extends BaseAction{

    private VipService vipService=ServiceFactory.getVipService();
    private HostelService hostelService=ServiceFactory.getHostelService();
    private ManagerService managerService=ServiceFactory.getManagerService();

    @Override
    public String execute() throws Exception{
            String name=request.getParameter("name");
            String password=request.getParameter("password");
            
            //以编号开头的字母区分会员与客栈
            char first= name.charAt(0);
            switch (first){
                case 'V':
                		//判断邮箱是否激活
                    Vip vip0=vipService.findVipById(name);
                    if(vip0.getEmailState()==0) {
                    		return "relogin";
                    }
                    //会员
                    Vip vip=vipService.findVipById(name);
                    if(vip==null){
                        System.out.println("Cannot find the vip");
                        return "relogin";
                    }else{
                        //成功登录
                        if(vipService.checkPassword(name,password)) {
                            System.out.println("Find the vip");
                            HttpSession session = request.getSession(true);
                            session.setAttribute("type", vip);
                            session.setAttribute("id", name);
                            return "vip";
                        }else{
                            return "relogin";
                        }
                    }
                case 'H':
                    //客栈
                    Hostel hostel=hostelService.queryHostelByNum(name);
                    if(hostel==null){
                        System.out.println("Cannot find the hostel");
                        return "relogin";
                    }else{
                        if(hostelService.checkPassword(name,password)){
                        System.out.println("Find the hostel");
                        HttpSession session=request.getSession(true);
                        session.setAttribute("hostel",hostel);
                        session.setAttribute("id", name);
                        return "hostel";
                        }else {
                            return "relogin";
                        }
                    }
                case 'M':
                    //经理
                    Manager manager=managerService.queryByNum(name);
                    if(manager==null){
                        System.out.println("Cannot find the manager");
                        return "relogin";
                    }else{
                        if(managerService.checkPassword(name,password)) {
                            HttpSession session = request.getSession(true);
                            session.setAttribute("manager", manager);
                            session.setAttribute("id", name);
                            return "manager";
                        }else {
                            return "relogin";
                        }
                    }
                default:
                    //不存在该编号
                    return "relogin";
            }


    }
}
