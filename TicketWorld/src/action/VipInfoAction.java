package action;

import edu.nju.onlinestock.factory.ServiceFactory;
import edu.nju.onlinestock.model.Orders;
import edu.nju.onlinestock.model.Vip;
import edu.nju.onlinestock.service.HostelService;
import edu.nju.onlinestock.service.OrdersService;
import edu.nju.onlinestock.service.VipBalanceService;
import edu.nju.onlinestock.service.VipService;

import java.util.*;

public class VipInfoAction extends BaseAction {

    VipService vipService=ServiceFactory.getVipService();
    VipBalanceService vipBalanceService=ServiceFactory.getVipBalanceService();
    OrdersService ordersService=ServiceFactory.getOrdersService();
    HostelService hostelService=ServiceFactory.getHostelService();

    public String jump2vipInfo(){
        String vipNum=String.valueOf(request.getSession().getAttribute("id"));
        Vip vip=vipService.findVipById(vipNum);
        request.setAttribute("vip",vip);
        return SUCCESS;
    }

    public String activateVip(){
        String vipNum=String.valueOf(request.getSession().getAttribute("id"));
        double money=Double.valueOf(request.getParameter("money"));
        if(vipBalanceService.activateVip(vipNum,money)){
            Vip vip=vipService.findVipById(vipNum);
            request.setAttribute("vip",vip);
            return "activate";
        }else{
            return "fail";
        }
    }

    public String cancelVip(){
        String vipNum=String.valueOf(request.getSession().getAttribute("id"));
        if(vipService.cancelVip(vipNum)) {
            Vip vip=vipService.findVipById(vipNum);
            request.setAttribute("vip",vip);
            return "cancel";
        }else{
            return "fail";
        }
    }

    public String convertCredit(){
        String vipNum=String.valueOf(request.getSession().getAttribute("id"));
        double point=Double.valueOf(request.getParameter("point"));
        if(vipBalanceService.convertCreditToMoney(vipNum,point)>0){
            Vip vip=vipService.findVipById(vipNum);
            request.setAttribute("vip",vip);
            return "convert";
        }
        return "fail";
    }

    public String topUp(){
        String vipNum=String.valueOf(request.getSession().getAttribute("id"));
        double money=Double.valueOf(request.getParameter("money"));
        if(vipBalanceService.topUp(vipNum,money)){
            Vip vip=vipService.findVipById(vipNum);
            request.setAttribute("vip",vip);
            return "topup";
        }
        return "fail";
    }

    public String modifyVip(){
        String vipName=request.getParameter("vipName");
        String bankCardId=request.getParameter("bankCardId");
        String vipNum=String.valueOf(request.getSession().getAttribute("id"));
        Vip vip=vipService.findVipById(vipNum);
        vip.setVipName(vipName);
        vip.setBankCardId(bankCardId);
        vipService.updateVip(vip);
        request.setAttribute("vip",vip);

        return "modify";
    }

    public String vipSta(){
        String vipNum=String.valueOf(request.getSession().getAttribute("id"));
        List<Orders> ordersList=ordersService.queryByVip(vipNum);
        int total=ordersList.size();
        Map<String,Double> hostels=new HashMap<>();
        double totalMoney=0;
        for(Orders orders:ordersList){
            String hostelName=hostelService.queryHostelByNum(orders.getHostelNum()).getHostelName();
            totalMoney=totalMoney+orders.getPaidMoney();
            if(!hostels.keySet().contains(orders.getHostelNum())){
                hostels.put(hostelName,orders.getPaidMoney());
            }else{
                double money= hostels.get(orders.getHostelNum());
                hostels.put(hostelName,money+orders.getPaidMoney());
            }
        }
        request.setAttribute("totalMoney",totalMoney);
        request.setAttribute("total",total);
        request.setAttribute("hostels",hostels);
        return "vipSta";
    }
}
