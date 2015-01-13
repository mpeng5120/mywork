package wifiRunnablesAndChatlistener;

import java.util.Map;

import com.tr.R;

import android.app.Activity;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ����ʱ�࣬
 * @author ������
 *
 */
public class DelayCount extends CountDownTimer {     
	private Activity targetActivity;
	private ListView tempListView;
	
	private String selectstrString;
	//private PositionQueryRunnable positionQueryRunnable=new PositionQueryRunnable(null, null, null, null, null, null);
	
	public DelayCount(long millisInFuture, long countDownInterval,Activity temp,ListView tempListView,String selectFlag) {     
		super(millisInFuture, countDownInterval);     
		
		targetActivity=temp;
		this.tempListView=tempListView;
		if(this.tempListView==null){
			return;
		}
		selectstrString=selectFlag;
	}     
	
	public void destroy() {

	}

	@Override     
	public void onFinish() {     
		try {
			
//			if (selectstrString.equals("freePoint")) {
//                      
//                        if(tempListView.getChildAt(0)==null){
//                        	System.out.println("tempListView.getChildAt(0)==null");
//                        }
//                        
//					if((tempListView.getChildAt(0).findViewById(R.id.currentPos_free_setting))==null){
//						return;
//					}
//					if((tempListView.getChildAt(1).findViewById(R.id.currentPos_free_setting))==null){
//						return;
//					}
//					if((tempListView.getChildAt(2).findViewById(R.id.currentPos_free_setting))==null){
//						return;
//					}
//					if((tempListView.getChildAt(3).findViewById(R.id.currentPos_free_setting))==null){
//						return;
//					}
//					if((tempListView.getChildAt(4).findViewById(R.id.currentPos_free_setting))==null){
//						return;
//					}
//				
//					
//			} else
			if(selectstrString.equals("counter")){
				new Thread(new CounterQueryRunnable(targetActivity, tempListView)).start();
				
			}else if(selectstrString.equals("option")){
				Log.i("mpeng","1111111111111");
				new Thread(new DelayoptionQueryRunnble(targetActivity, tempListView)).start();
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}     
	@Override     
	public void onTick(long millisUntilFinished) {}    
}   
