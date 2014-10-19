package com.qiubaiclient.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qiubaiclient.model.ItemBean;

import android.content.ClipData.Item;
import android.content.Context;
import android.util.Log;

/**
 * 工具类
 * 
 * @author xiangxm
 * 
 */
public class Common {

	// ****************测试需要用到的方法************************
	/**
	 * 解析json数据插入数据库
	 * 
	 * @param context
	 * @param id
	 *            资源文件对应的id
	 */
	public static String readData(Context context, int id) {
		// 将资源文件读取出来
		InputStream in = context.getResources().openRawResource(id);
		// 将in读入reader 中
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(in, "GBK"));

			StringBuffer buffer = new StringBuffer("");
			String tem = "";
			while ((tem = br.readLine()) != null) {
				buffer.append(tem);
			}
			br.close();

			return buffer.toString();
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return null;
	}

	public static void parseJsonStr(String jsonStr) {

		List<ItemBean> jsonList = new ArrayList<ItemBean>() ;
		
		JSONObject jsonObject = JSON.parseObject(jsonStr) ;
 		
		JSONArray jsonArray = jsonObject.getJSONArray("items") ;
		
		jsonList = JSON.parseArray(jsonArray.toString(), ItemBean.class) ;
		
		for(ItemBean item:jsonList){
			
			Log.i("------test", item.getPublished_at()) ;
			
		}
	}

	// ****************************************
}
