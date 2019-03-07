package action;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import edu.nju.onlinestock.factory.ServiceFactory;
import edu.nju.onlinestock.model.Orders;
import edu.nju.onlinestock.model.RoomPlan;
import edu.nju.onlinestock.service.OrdersService;
import edu.nju.onlinestock.service.RoomPlanService;
import edu.nju.onlinestock.service.VipService;
import util.OrderConditionEnum;


public class VoteAllocation extends TimerTask {
	private String hostelNum;
	private String startDate;
	private String endDate;
    /**
	 * @param hostelNum
	 * @param startDate
	 * @param endDate
	 */
	public VoteAllocation(String hostelNum, String startDate, String endDate) {
		// TODO Auto-generated constructor stub
		this.hostelNum=hostelNum;
		this.startDate=startDate;
		this.endDate=endDate;
	}
	RoomPlanService roomPlanService=ServiceFactory.getRoomPlanService();
    OrdersService ordersService=ServiceFactory.getOrdersService();
    VipService vipService=ServiceFactory.getVipService();
    
      @Override
   public void run() {
    	  List<RoomPlan> roomPlans = roomPlanService.queryByHostel(hostelNum);
    	  List<Orders> ordersList=ordersService.queryByHostel(hostelNum);
          
    	  int KingsizeRoomNum=0;
    	  double SingleRoomNum=0;
    	  double DoubleRoomNum=0;
    	  
      for(RoomPlan roomPlan:roomPlans) {    
    	  	if(roomPlan.getStartDate().equals(startDate)&&roomPlan.getEndDate().equals(endDate)) {
    	  		switch(roomPlan.getRoomTypeId()) {
    	  			case 1:KingsizeRoomNum=roomPlan.getRoomNum();break;
    	  			case 3:SingleRoomNum=roomPlan.getRoomNum();break;
    	  			case 2:DoubleRoomNum=roomPlan.getRoomNum();break;
    	  			default:break;
    	  		}
    	  	}
      }
      //使用map表示座位表(贵宾座0为空1为有人   2为过道）
      double row=1+1+Math.ceil(SingleRoomNum/KingsizeRoomNum)+1+Math.ceil(DoubleRoomNum/KingsizeRoomNum);
      double[][] array = new double[(int)row][KingsizeRoomNum];
      for(int i=0;i<(int)row-1;i++) {
    	  	if(i==1||i==2+Math.ceil(SingleRoomNum/KingsizeRoomNum)) {
    	  		for(int j=0;j<KingsizeRoomNum;j++) {
        	  		array[i][j]=2;
        	  	}
    	  	}else {
    	  		for(int j=0;j<KingsizeRoomNum;j++) {
        	  		array[i][j]=0;
        	  	}
    	  	}
      }
      if(SingleRoomNum%KingsizeRoomNum!=0) {
    	  	for(int i=0;i<KingsizeRoomNum-SingleRoomNum%KingsizeRoomNum;i++) {
    	  		array[(int) (2+Math.floor(SingleRoomNum/KingsizeRoomNum))][(int) (i+SingleRoomNum%KingsizeRoomNum)]=2;
    	  	}
      }
      if(DoubleRoomNum%KingsizeRoomNum!=0) {
    	  	for(int i=0;i<KingsizeRoomNum-DoubleRoomNum%KingsizeRoomNum;i++) {
    	  		array[(int) (row-1)][(int) (i+DoubleRoomNum%KingsizeRoomNum)]=2;
    	  	}
      }
      //把已选的座位的0变为1，之后在从前向后给未配票的分配座位（注意贵宾座一等座二等座）
      for(Orders order:ordersList) {
    	  	if(order.getOrderCondition().equals(OrderConditionEnum.VALID.toString())&&order.getCheckinDate().equals(startDate)&&order.getCheckoutDate().equals(endDate)) {
    	  		if(!order.getSeatNum().equals("null")) {
    	  			String seatNum=order.getSeatNum();
    	  			String[] seatNums=seatNum.split(",");
    	  			for(String temp:seatNums) {
    	  				array[Integer.parseInt(temp.substring(0,1))-1][Integer.parseInt(temp.substring(2,3))-1]=1;
    	  			}
    	  		}
    	  	}
      }
      for(Orders order:ordersList) {
    	  if(order.getOrderCondition().equals(OrderConditionEnum.VALID.toString())&&order.getCheckinDate().equals(startDate)&&order.getCheckoutDate().equals(endDate)) {
  	  		if(order.getSeatNum().equals("null")) {
  	  			int roomTypeId=order.getRoomTypeId();
  	  			String seatNum="";
  	  			int roomNum=order.getRoomNum();
  	  			int sum=0;
  	  			switch(roomTypeId) {
  	  				case 1:
  	  					for(int i=0;i<array[0].length;i++) {
  	  						if(array[0][i]==0)sum++;
  	  					}
  	  					if(sum>=order.getRoomNum()) {
  	  						for(int i=0;i<array[0].length;i++) {
  	  							if(array[0][i]==0&&roomNum!=0) {
	  								array[0][i]=1;
	  								roomNum--;
	  								seatNum+="1排"+String.valueOf(i+1)+"座,";
	  							}
  	  						}
  	  					}else {
  	  						//配票不成功的退款
  	  						ordersService.cancel(order.getOrderNum());
  	  					}
  	  					break;
  	  				case 3:
  	  				for(int j=2;j<2+Math.ceil(SingleRoomNum/KingsizeRoomNum);j++){
	  						for(int i=0;i<array[0].length;i++) {
	  							if(array[j][i]==0)sum++;
	  						}
	  					}
  	  					if(sum>=roomNum) {
  	  					for(int j=2;j<2+Math.ceil(SingleRoomNum/KingsizeRoomNum);j++){
  	  						for(int i=0;i<array[0].length;i++) {
  	  							if(array[j][i]==0&&roomNum!=0) {
  	  								array[j][i]=1;
  	  								roomNum--;
  	  								seatNum+=String.valueOf(j+1)+"排"+String.valueOf(i+1)+"座,";
  	  							}
  	  						}
  	  					}
  	  					}else {
  	  						ordersService.cancel(order.getOrderNum());
  	  					}
  	  					break;
  	  				case 2:
  	  				for(int j=(int) (3+Math.ceil(SingleRoomNum/KingsizeRoomNum));j<array.length;j++){
  						for(int i=0;i<array[0].length;i++) {
  							if(array[j][i]==0)sum++;
  						}
  					}
  	  				if(sum>=roomNum) {
  	  				for(int j=(int) (3+Math.ceil(SingleRoomNum/KingsizeRoomNum));j<array.length;j++){
	  						for(int i=0;i<array[0].length;i++) {
	  							if(array[j][i]==0&&roomNum!=0) {
	  								array[j][i]=1;
	  								roomNum--;
	  								seatNum+=String.valueOf(j+1)+"排"+String.valueOf(i+1)+"座,";
	  							}
	  						}
	  					}
  	  				}else {
  	  					ordersService.cancel(order.getOrderNum());
  	  				}
  	  					break;
  	  				default:break;
  	  			}
  	  			order.setSeatNum(seatNum);
  	  			ordersService.updateOrders(order);
  	  		}
  	  	}
      }
      
      for(int i=0;i<(int)row;i++) {
    	  	for(int j=0;j<KingsizeRoomNum;j++) {
    	  		System.out.print(array[i][j]);
    	  	}
    	  	System.out.println();
        }
   }
      public static void main(String[] args) {
    	  	VoteAllocation test=new VoteAllocation("H000004","2018-04-20","2018-04-21");
    	  	test.run();
      }
   }
