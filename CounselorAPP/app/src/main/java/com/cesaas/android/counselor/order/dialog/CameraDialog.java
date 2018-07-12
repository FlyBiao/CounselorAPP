package com.cesaas.android.counselor.order.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;

/**
 * 相机Dialog
 * @author FGB
 *
 */
public class CameraDialog extends BaseDialog {

	private CameraInterface interfaces;

	private TextView d_camera_camera;
	private TextView d_camera_photo;
	private TextView d_camera_cancle;

	public CameraDialog(Context context, boolean wins, CameraInterface interfaces) {
		super(context, R.style.ActionSheetDialogStyle, wins);
		this.interfaces = interfaces;
		setContentView(R.layout.dialog_camera);
		initView();
	}

	private void initView() {
		d_camera_camera = (TextView) findViewById(R.id.d_camera_camera);
		d_camera_photo = (TextView) findViewById(R.id.d_camera_photo);
		d_camera_cancle = (TextView) findViewById(R.id.d_camera_cancle);
	}

	@Override
	public void mShow() {
		d_camera_camera.setOnClickListener(this);
		d_camera_photo.setOnClickListener(this);
		d_camera_cancle.setOnClickListener(this);
		super.mShow();
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		if (R.id.d_camera_camera == v.getId()) {
			interfaces.onCamera();
			dismiss();
		} else if (R.id.d_camera_photo == v.getId()) {
			interfaces.onPhoto();
			dismiss();
		} else if (R.id.d_camera_cancle == v.getId()) {
			this.dismiss();
		}
	}

	public interface CameraInterface {

		public void onCamera(); // 调用相机

		public void onPhoto(); // 调用相册

	}

}