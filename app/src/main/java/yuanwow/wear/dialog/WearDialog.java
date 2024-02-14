package yuanwow.wear.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.yuanwow.adb.R;

/**
 * description:自定义dialog
 */

public class WearDialog extends Dialog {
	/**
	 * 显示的图片
	 */
	

	/**
	 * 显示的标题
	 */
	private TextView titleTv ;

	/**
	 * 显示的消息
	 */
	private TextView messageTv ;

	/**
	 * 确认和取消按钮
	 */
	private Button negtiveBn ,positiveBn;

	private LinearLayout v1;

	private View v2;

	/**
	 * 按钮之间的分割线
	 */
	
	public WearDialog(Context context) {
		super(context, R.style.CustomDialog);
	}

	/**
	 * 都是内容数据
	 */
	private String message;
	private String title;
	private String positive,negtive ;
	private int imageResId = -1 ;

	/**
	 * 底部是否只有一个按钮
	 */
	private boolean isSingle = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.dialog_common);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        
		//按空白处不能取消动画
		setCanceledOnTouchOutside(false);
		//初始化界面控件
		initView();
		//初始化界面数据
		refreshView();
		//初始化界面控件的事件
		initEvent();
	}

	/**
	 * 初始化界面的确定和取消监听器
	 */
	private void initEvent() {
		//设置确定按钮被点击后，向外界提供监听
		positiveBn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if ( onClickBottomListener!= null) {
						onClickBottomListener.onPositiveClick(WearDialog.this);
					}else{
                        dismiss();
                    }
				}
			});
		//设置取消按钮被点击后，向外界提供监听
		negtiveBn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if ( onClickBottomListener!= null) {
						onClickBottomListener.onNegtiveClick(WearDialog.this);
					}else{
                        dismiss();
                    }
				}
			});
	}
	public OnClickBottomListener onClickBottomListener;
	public WearDialog setOnClickBottomListener(OnClickBottomListener onClickBottomListener) {
		this.onClickBottomListener = onClickBottomListener;
		return this;
	}
	public interface OnClickBottomListener{
		/**
		 * 点击确定按钮事件
		 */
		public void onPositiveClick(WearDialog w);
		/**
		 * 点击取消按钮事件
		 */
		public void onNegtiveClick(WearDialog w);
	}
	/**
	 * 初始化界面控件的显示数据
	 */
	private void refreshView() {
		//如果用户自定了title和message
		if (!TextUtils.isEmpty(title)) {
			titleTv.setText(title);
			titleTv.setVisibility(View.VISIBLE);
		}else {
			titleTv.setVisibility(View.GONE);
		}
		if (!TextUtils.isEmpty(message)) {
			messageTv.setText(message);
		}
		//如果设置按钮的文字
		if (!TextUtils.isEmpty(positive)) {
			positiveBn.setText(positive);
		}else {
			positiveBn.setText("确定");
		}
		if (!TextUtils.isEmpty(negtive)) {
			negtiveBn.setText(negtive);
		}else {
			negtiveBn.setText("取消");
		}

		
		/**
		 * 只显示一个按钮的时候隐藏取消按钮，回掉只执行确定的事件
		 */
		if (isSingle){
			
			negtiveBn.setVisibility(View.GONE);
		}else {
			negtiveBn.setVisibility(View.VISIBLE);
			
		}
	}

	@Override
	public void show() {
		super.show();
		refreshView();
	}

	/**
	 * 初始化界面控件
	 */
	private void initView() {
		negtiveBn = (Button) findViewById(R.id.negtive);
		positiveBn = (Button) findViewById(R.id.positive);
		titleTv = (TextView) findViewById(R.id.title);
		messageTv = (TextView) findViewById(R.id.message);
		v1 = (LinearLayout) findViewById(R.id.dialogcommonLinearLayout1);
	}

	@Override
	public void setContentView(int layoutResID) {
		v1.removeAllViews();
		v2=LayoutInflater.from(getContext()).inflate(layoutResID,null);
		v1.addView(v2);
	}
    

    @Override
    public void setContentView(View v) {
        v1.removeAllViews();
        v2=v;
        v1.addView(v2);
	}

	@Override
	public <T extends View> T findViewById(int id) {
		//return super.findViewById(id);
		if(v2!=null){
			return v2.findViewById(id);
		}else{
			return super.findViewById(id);
		}
	}
	
	/**
	 * 设置确定取消按钮的回调
	 */
	 
	
	public String getMessage() {
		return message;
	}

	public WearDialog setMessage(String message) {
		this.message = message;
		return this ;
	}
	public String getTitle() {
		return title;
	}
	
	@Override
	public void setTitle(CharSequence title) {
		this.title = title.toString();
	}

	public String getPositive() {
		return positive;
	}

	public WearDialog setPositive(String positive) {
		this.positive = positive;
		return this ;
	}

	public String getNegtive() {
        
		return negtive;
	}

	public WearDialog setNegtive(String negtive) {
		this.negtive = negtive;
        isSingle=false;
		return this ;
	}

	public int getImageResId() {
		return imageResId;
	}

	public boolean isSingle() {
		return isSingle;
	}

	public WearDialog setSingle(boolean single) {
		isSingle = single;
		return this ;
	}

	public WearDialog setImageResId(int imageResId) {
		this.imageResId = imageResId;
		return this ;
	}
	
	

}

