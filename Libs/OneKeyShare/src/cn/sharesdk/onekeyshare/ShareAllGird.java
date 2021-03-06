/*
 * 官网地站:http://www.ShareSDK.cn
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 ShareSDK.cn. All rights reserved.
 */

package cn.sharesdk.onekeyshare;

import cn.sharesdk.framework.AbstractWeibo;
import android.content.Context;
import android.content.Intent;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;

/**
* 快捷分享入口，也是平台宫格列表的UI外壳。
* <p>
*由于不同分享平台之间存在着各种参数上的差异，所以快捷分享支持下面的参数列表。 列表中的字段可以通过
*{@link Intent#putExtra(key, value)}的方式传递进来。请注 意：<b>并非
*所有的参数都需要一次性传递</b>，实际操作中只要根据 {@link AbstractWeibo#share(ShareParams)}
*方法的说明，传递不同平台所需要的 字段就行了。
* <p>
* <table border="1">
* <tr>
* <th align="center">&nbsp;</th>
* <th align="center">类型</th>
* <th align="center">说明</th>
* </tr>
* <tr>
* <td align="center">notif_icon</td>
* <td align="center">int</td>
* <td>分享时Notification的图标</td>
* </tr>
* <tr>
* <td align="center">notif_title</td>
* <td align="center">String</td>
* <td>分享时Notification的标题</td>
* </tr>
* <tr>
* <td align="center">address</td>
* <td align="center">String</td>
* <td>address是接收人地址，仅在信息和邮件使用，否则可以不提供</td>
* </tr>
* <tr>
* <td align="center">title</td>
* <td align="center">String</td>
* <td>title标题，在印象笔记、邮箱、信息、微信（包括好友和朋友圈）、人人网和QQ空间使用，否则可以不提供</td>
* </tr>
* <tr>
* <td align="center">titleUrl</td>
* <td align="center">String</td>
* <td>titleUrl是标题的网络链接，仅在人人网和QQ空间使用，否则可以不提供</td>
* </tr>
* <tr>
* <td align="center">text</td>
* <td align="center">String</td>
* <td>text是分享文本，所有平台都需要这个字段</td>
* </tr>
* <tr>
* <td align="center">imagePath</td>
* <td align="center">String</td>
* <td>imagePath是本地的图片路径，除Linked-In以外的平台都支持这个字段</td>
* </tr>
* <tr>
* <td align="center">imageUrl</td>
* <td align="center">String</td>
* <td>imageUrl是网络的图片路径，仅在人人网和QQ空间使用，其优先级高于imagePath，故若不为null，则imagePath对此二平台无效</td>
* </tr>
* <tr>
* <td align="center">url</td>
* <td align="center">String</td>
* <td>url仅在微信（包括好友和朋友圈）中使用，否则可以不提供</td>
* </tr>
* <tr>
* <td align="center">thumbPath</td>
* <td align="center">String</td>
* <td>thumbPath是缩略图的本地路径，仅在微信（包括好友和朋友圈）中使用，否则可以不提供</td>
* </tr>
* <tr>
* <td align="center">appPath</td>
* <td align="center">String</td>
* <td>appPath是待分享应用程序的本地路劲，仅在微信（包括好友和朋友圈）中使用，否则可以不提供</td>
* </tr>
* <tr>
* <td align="center">comment</td>
* <td align="center">String</td>
* <td>comment是我对这条分享的评论，仅在人人网和QQ空间使用，否则可以不提供</td>
* </tr>
* <tr>
* <td align="center">site</td>
* <td align="center">String</td>
* <td>site是分享此内容的网站名称，仅在QQ空间使用，否则可以不提供</td>
* </tr>
* <tr>
* <td align="center">siteUrl</td>
* <td align="center">String</td>
* <td>siteUrl是分享此内容的网站地址，仅在QQ空间使用，否则可以不提供</td>
* </tr>
* <tr>
* <td align="center">venueName</td>
* <td align="center">String</td>
* <td>venueName是分享社区名称，仅在Foursquare使用，否则可以不提供</td>
* </tr>
* <tr>
* <td align="center">venueDescription</td>
* <td align="center">String</td>
* <td>venueDescription是分享社区描述，仅在Foursquare使用，否则可以不提供</td>
* </tr>
* <tr>
* <td align="center">latitude</td>
* <td align="center">float</td>
* <td>latitude是维度数据，仅在新浪微博、腾讯微博和Foursquare使用，否则可以不提供</td>
* </tr>
* <tr>
* <td align="center">longitude</td>
* <td align="center">float</td>
* <td>longitude是经度数据，仅在新浪微博、腾讯微博和Foursquare使用，否则可以不提供</td>
* </tr>
* <tr>
* <td align="center">silent</td>
* <td align="center">boolean</td>
* <td>表示是否直接分享</td>
* </tr>
* </table>
* <p>
* 依照上面的参数列表，如果想要实现QQ空间的分享功能，可以这样子操作：<br>
* （QQ空间和人人网已经同时支持发送本地或网络图片了）
* <p>
* <table border="1">
* <tr>
* <td>
* Intent i = new Intent();<br>
* <font color="007f00">// 分享时Notification的图标</font><br>
* i.putExtra("notif_icon", R.drawable.ic_launcher);<br>
* <font color="007f00">// 分享时Notification的标题</font><br>
* i.putExtra("notif_title", getContext().getString(R.string.app_name));<br>
* <br>
* <font color="007f00">// 标题</font><br>
* i.putExtra("title", getContext().getString(R.string.share));<br>
* <font color="007f00">// 标题的网络链接</font><br>
* i.putExtra("titleUrl", "http://sharesdk.cn");<br>
* <font color="007f00">// 分享文本，所有平台都需要这个字段</font><br>
* i.putExtra("text", getContext().getString(R.string.share_content));<br>
* <font color="007f00">// 网络的图片路径</font><br>
* <font color="007f00">// i.putExtra("imageUrl",
* "http://sharesdk.cn/Public/Frontend/images/logo.png");</font><br>
* <font color="007f00">// 本地的图片路径</font><br>
* i.putExtra("imageUrl", "/mnt/sdcard/logo.png");<br>
* <font color="007f00">// 我对这条分享的评论</font><br>
* i.putExtra("comment", getContext().getString(R.string.share));<br>
* <font color="007f00">// 分享此内容的网站名称</font><br>
* i.putExtra("site", getContext().getString(R.string.share));<br>
* <font color="007f00">// 分享此内容的网站地址</font><br>
* i.putExtra("siteUrl", "http://sharesdk.cn");<br>
* <br>
* <font color="007f00">// 是否直接分享</font><br>
* i.putExtra("silent", false);<br>
* new ShareAllGird(getContext()).show(i);</td>
* </tr>
* </table>
* <p>
* “直接分享”表示不进入{@link SharePage}而直接执行分享的操作。比如微信（包括好友
*和朋友圈）就总是执行直接分享，这是因为微信分享时调用微信客户端，拥有自己的分享 界面。
*/
public class ShareAllGird extends FakeActivity implements OnClickListener {
	private FrameLayout flPage; // 页面
	private WeiboGridView grid; // 宫格列表
	private Button btnCancel; // 取消按钮
	private Animation animShow; // 滑上来的动画
	private Animation animHide; // 滑下去的动画
	private boolean finishing;

	public ShareAllGird(Context context) {
		super(context);
	}

	protected void onCreate(Context context) {
		Intent i = getIntent();
		if (i.hasExtra("platform")) {
			// 直接进入分享编辑页面
			new SharePage(context).show(i);
			finish();
			return;
		}

		initPageView();
		initAnim();
		setContentView(flPage);

		// 设置宫格列表数据
		grid.setData(i);
		btnCancel.setOnClickListener(this);

		// 显示列表
		flPage.clearAnimation();
		flPage.startAnimation(animShow);

		// 打开分享菜单的统计
		AbstractWeibo.logDemoEvent(1, null);
	}

	private void initPageView() {
		flPage = new FrameLayout(getContext());

		// 宫格列表的容器，为了“下对齐”，在外部包含了一个FrameLayout
		LinearLayout llPage = new LinearLayout(getContext());
		llPage.setOrientation(LinearLayout.VERTICAL);
		llPage.setBackgroundResource(R.drawable.share_vp_back);
		FrameLayout.LayoutParams lpLl = new FrameLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		lpLl.gravity = Gravity.BOTTOM;
		llPage.setLayoutParams(lpLl);
		flPage.addView(llPage);

		// 宫格列表
		grid = new WeiboGridView(getContext());
		LinearLayout.LayoutParams lpWg = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		int dp_10 = cn.sharesdk.framework.res.R.dipToPx(getContext(), 10);
		lpWg.setMargins(0, dp_10, 0, 0);
		grid.setLayoutParams(lpWg);
		llPage.addView(grid);

		// 取消按钮
		btnCancel = new Button(getContext());
		btnCancel.setTextColor(0xffffffff);
		btnCancel.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
		btnCancel.setText(R.string.cancel);
		btnCancel.setPadding(0, 0, 0, cn.sharesdk.framework.res.R.dipToPx(getContext(), 5));
		btnCancel.setBackgroundResource(R.drawable.btn_cancel_back);
		LinearLayout.LayoutParams lpBtn = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, cn.sharesdk.framework.res.R.dipToPx(getContext(), 45));
		lpBtn.setMargins(dp_10, 0, dp_10, dp_10 * 2);
		btnCancel.setLayoutParams(lpBtn);
		llPage.addView(btnCancel);
	}

	private void initAnim() {
		animShow = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0,
				Animation.RELATIVE_TO_SELF, 0,
				Animation.RELATIVE_TO_SELF, 1,
				Animation.RELATIVE_TO_SELF, 0);
		animShow.setDuration(300);

		animHide = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0,
				Animation.RELATIVE_TO_SELF, 0,
				Animation.RELATIVE_TO_SELF, 0,
				Animation.RELATIVE_TO_SELF, 1);
		animHide.setDuration(300);
	}

	public void onClick(View v) {
		if (v.equals(btnCancel)) {
			finish();
		}
	}

	public void finish() {
		if (finishing) {
			return;
		}

		if (animHide == null) {
			finishing = true;
			super.finish();
			return;
		}

		// 取消分享菜单的统计
		AbstractWeibo.logDemoEvent(2, null);
		finishing = true;
		animHide.setAnimationListener(new AnimationListener() {
			public void onAnimationStart(Animation animation) {

			}

			public void onAnimationRepeat(Animation animation) {

			}

			public void onAnimationEnd(Animation animation) {
				flPage.setVisibility(View.GONE);
				ShareAllGird.super.finish();
			}
		});
		flPage.clearAnimation();
		flPage.startAnimation(animHide);
	}

}
