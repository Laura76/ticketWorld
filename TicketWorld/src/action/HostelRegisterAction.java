package action;

import edu.nju.onlinestock.factory.ServiceFactory;
import edu.nju.onlinestock.model.Approval;
import edu.nju.onlinestock.model.Hostel;
import edu.nju.onlinestock.service.ApprovalService;
import edu.nju.onlinestock.service.HostelService;
import util.ApprovalStateEnum;
import util.ApprovalType;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

public class HostelRegisterAction extends BaseAction {
    private HostelService hostelService=ServiceFactory.getHostelService();
    private ApprovalService approvalService=ServiceFactory.getApprovalService();

    public String getInfo(){
        String hostelNum=String.valueOf(request.getSession().getAttribute("id"));
        Hostel hostel=hostelService.queryHostelByNum(hostelNum);
        request.setAttribute("hostel",hostel);
        return "info";
    }

    public String hostelInfo(){
        String hostelName=request.getParameter("hostelName");
        String hostelPassword=request.getParameter("password");
        String province=request.getParameter("province");
        String city=request.getParameter("city");
        String address=request.getParameter("address");
        String hostelInfo=request.getParameter("hostelInfo");
        int level=Integer.valueOf(request.getParameter("level"));
        String hostelNum=hostelService.getHostelNum();
        Hostel hostel=new Hostel();
        hostel.setHostelNum(hostelNum);
        hostel.setHostelName(hostelName);
        hostel.setHostelPassword(hostelPassword);
        hostel.setProvince(province);
        hostel.setCity(city);
        hostel.setAddress(address);
        hostel.setHostelInfo(hostelInfo);
        hostel.setLevel(level);
        hostel.setApprovalState(ApprovalStateEnum.WAIT.toString());

        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        hostel.setApplyDate(sdf.format(date));
        hostelService.registerHostel(hostel);

        Approval approval=new Approval();
        approval.setHostelNum(hostelNum);
        approval.setApprovalType(ApprovalType.REGISTER.toString());
        approval.setApproveState(ApprovalStateEnum.WAIT.toString());
        approval.setApplyDate(sdf.format(date));
        approvalService.save(approval);

        HttpSession session=request.getSession(true);
        session.setAttribute("hostel",hostel);
        session.setAttribute("id",hostelNum);
        return "hostelInfo";
    }

    public String hostelRegister(){
        return "hostelRegister";
    }

    public String modify(){
        String hostelNum=String.valueOf(request.getSession().getAttribute("id"));
        String hostelName=request.getParameter("hostelName");
        String province=request.getParameter("province");
        String city=request.getParameter("city");
        String address=request.getParameter("address");
        String hostelInfo=request.getParameter("hostelInfo");
        int level=Integer.valueOf(request.getParameter("level"));
        Hostel hostel=hostelService.queryHostelByNum(hostelNum);
        hostel.setHostelName(hostelName);
        hostel.setProvince(province);
        hostel.setCity(city);
        hostel.setAddress(address);
        hostel.setHostelInfo(hostelInfo);
        hostel.setLevel(level);
        hostel.setApprovalState(ApprovalStateEnum.WAIT.toString());
        hostelService.updateHostel(hostel);

        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Approval approval=new Approval();
        approval.setHostelNum(hostelNum);
        approval.setApprovalType(ApprovalType.MODIFY.toString());
        approval.setApproveState(ApprovalStateEnum.WAIT.toString());
        approval.setApplyDate(sdf.format(date));
        approvalService.save(approval);

        request.getSession().setAttribute("hostel",hostel);
        return "modified";
    }
}
