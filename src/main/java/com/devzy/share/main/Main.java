package com.devzy.share.main;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.common.UUIDs;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.devzy.share.common.ElasticsearchSingleton;
import com.devzy.share.elasticsearch.http.ElasticsearchExtendHttpFactory;
import com.devzy.share.elasticsearch.rest.ElasticsearchExtendHighRestFactory;
import com.devzy.share.elasticsearch.rest.ElasticsearchExtendRestFactory;
import com.devzy.share.elasticsearch.transport.ElasticsearchExtendTransportFactory;
import com.devzy.share.util.DateUtil;
import com.devzy.share.util.NumberUtil;

public class Main {
	private static Logger logger = LogManager.getLogger();
	private static ElasticsearchExtendTransportFactory tfactory = ElasticsearchSingleton.getIntance().transportES();
	private static ElasticsearchExtendHttpFactory hfactory = ElasticsearchSingleton.getIntance().httpES();
	private static ElasticsearchExtendRestFactory rfactory = ElasticsearchSingleton.getIntance().restES();
	private static ElasticsearchExtendHighRestFactory hrfactory = ElasticsearchSingleton.getIntance().highES();
	/**
	 * 访问方式:0.Transport方式[内置接口],1.HTTP[标准HTTP方式],2.Rest[内置HTTP方式],3.HighRest[内置HTTP方式]
	 */
	private static int ACCESS_TYPE = 0;
	/**
	 * 计数器
	 */
	private static AtomicInteger atomic = new AtomicInteger(0);
	
	public static void write(long count){
		logger.info("--Write Count:"+count);
		long start = System.currentTimeMillis();
		for(int i=0;(count<1?true:i<count);i++){
			Date today = new Date();
			Random random = new Random();
			JSONObject json = new JSONObject();
			json.put("id", UUIDs.randomBase64UUID());
			json.put("cellId", random.nextInt(1000));
			json.put("cid", random.nextInt());
			json.put("devicetype", random.nextInt(10));
			json.put("iso2", UUIDs.randomBase64UUID());
			json.put("lac", random.nextInt(10000));
			json.put("latitude", random.nextDouble());
			json.put("longitude", random.nextGaussian());
			json.put("logId", UUIDs.randomBase64UUID(random));
			json.put("mcc", 460);
			json.put("plmn", today.getTime());
			json.put("uid", UUIDs.randomBase64UUID(random));
			json.put("country", "CN[中国]");
			json.put("province", "ShannXi[陕西]");
			json.put("city", "Xi'An[西安]");
			json.put("sessionid", UUIDs.randomBase64UUID());
			json.put("userCode", random.nextInt(1000)+"@ucloudlink.com");
			json.put("imei", 1365222+random.nextInt(1000));
			json.put("imsi", 1365222+random.nextInt(1000));
			Date beginTime = new Date(today.getTime()+random.nextInt());
			json.put("beginTime", beginTime);
			json.put("countDay", today);
			json.put("card", random.nextInt(100));
			json.put("cardDownFlow", random.nextDouble()*1000*1000);
			json.put("cardUpFlow", random.nextDouble()*1000*1000);
			json.put("sys", random.nextDouble()*1000*1000);
			json.put("sysDownFlow", random.nextDouble()*1000*1000);
			json.put("sysUpFlow", random.nextDouble()*1000*1000);
			json.put("userDownFlow", random.nextDouble()*1000*1000);
			json.put("userUpFlow", random.nextDouble()*1000*1000);
			json.put("user", random.nextDouble()*1000*1000);
//			int len = 0;
//			for (String key : json.keySet()) {
//				len += json.get(key).toString().length();
//			}
			String description = "["+DateUtil.formatDateTimeStr(beginTime)+"]"+"Elasticsearch 是一个分布式可扩展的实时搜索和分析引擎。它能帮助你搜索、分析和浏览数据，而往往大家并没有在某个项目一开始就预料到需要这些功能。Elasticsearch 之所以出现就是为了重新赋予硬盘中看似无用的原始数据新的活力。Elasticsearch 为很多世界流行语言提供良好的、简单的、开箱即用的语言分析器集合：阿拉伯语、亚美尼亚语、巴斯克语、巴西语、保加利亚语、加泰罗尼亚语、中文、捷克语、丹麦、荷兰语、英语、芬兰语、法语、加里西亚语、德语、希腊语、北印度语、匈牙利语、印度尼西亚、爱尔兰语、意大利语、日语、韩国语、库尔德语、挪威语、波斯语、葡萄牙语、罗马尼亚语、俄语、西班牙语、瑞典语、土耳其语和泰语";
			json.put("description", description);
//			System.out.println(len+"---->"+description.length());
			String result = "";
			if(ACCESS_TYPE==0)result = tfactory.insert("transport", "test", json.toJSONString());
			if(ACCESS_TYPE==1)result = hfactory.insert("http", "test", json.toJSONString());
			if(ACCESS_TYPE==2)result = rfactory.insert("rest", "test", json.toJSONString());
			if(ACCESS_TYPE==3)result = hrfactory.insert("high", "test", json.toJSONString());
			atomic.incrementAndGet();
			long end = System.currentTimeMillis();
			double time = (end - start) / 1000.00;
			if(time>=100){
				System.exit(1);
			}
			double ss = time % 60;
			int mm = Double.valueOf(time / 60).intValue() % 60;
			int hh = Double.valueOf(time / 60 / 60).intValue() % 60;
			logger.info("["+atomic.get()+"]ES Write 耗时:"+(hh>0?hh+"小时":"")+(mm>0?mm+"分钟":"")+ss+"秒-------------"+result);
		}
	}
	public static void read(long count){
		logger.info("--Read Count:"+count);
		long start = System.currentTimeMillis();
		for(int i=0;(count<1?true:i<count);i++){
			String query = "Elasticsearch 语言分析器 中文 西安";
			String result = "";
			if(ACCESS_TYPE==0)result = tfactory.selectAll("transport", "test", query);
			if(ACCESS_TYPE==1)result = hfactory.selectAll("http", "test", query);
			if(ACCESS_TYPE==2)result = rfactory.selectAll("rest", "test", query);
			if(ACCESS_TYPE==3)result = hrfactory.selectAll("high", "test", query);
			atomic.incrementAndGet();
			long end = System.currentTimeMillis();
			double time = (end - start) / 1000.00;
			if(time>=100){
				System.exit(1);
			}
			double ss = time % 60;
			int mm = Double.valueOf(time / 60).intValue() % 60;
			int hh = Double.valueOf(time / 60 / 60).intValue() % 60;
			logger.info("["+atomic.get()+"]ES Read 耗时:"+(hh>0?hh+"小时":"")+(mm>0?mm+"分钟":"")+ss+"秒-------------"+result.length());
		}
	}
	
	public static void thread(final long count,final String opt,final int thread,final int type){
		ACCESS_TYPE=type;
		int cpu = Runtime.getRuntime().availableProcessors();
		for(int i=0;(thread<1?true:i<thread);i++){
			Thread service = new Thread(new Runnable() {
				@Override
				public void run() {
					if(opt.equalsIgnoreCase("r")){
						read(count);
					}else{
						write(count);
					}
				}
			});
			service.setName(cpu+"-Elasticsearch-Thread-"+i);
			service.start();
		}
	}
	public static void execute(final long count,final String opt,final int thread,final int type){
		ACCESS_TYPE=type;
		ExecutorService service = Executors.newSingleThreadExecutor();
		if(thread>1){
			service = Executors.newFixedThreadPool(thread);
		}else{
			if(thread<1){
				service = Executors.newCachedThreadPool();
			}
		}
		for(int i=0;(thread<1?true:i<thread);i++){
			service.submit(new Runnable() {
				@Override
				public void run() {
					if(opt.equalsIgnoreCase("r")){
						read(count);
					}else{
						write(count);
					}
				}
			});
		}
	}
	public static void main(String[] args) {
		long count = args!=null&&args.length>1&&NumberUtil.isNumber(args[0])?Long.valueOf(args[0]):1;
		String opt = args!=null&&args.length>2&&args[1].equalsIgnoreCase("r")?"r":"w";
		int thread = args!=null&&args.length>3&&NumberUtil.isNumber(args[2])?Integer.valueOf(args[2]):1;
		int type = args!=null&&args.length>4&&NumberUtil.isNumber(args[3])?Integer.valueOf(args[3]):0;
		logger.info("--{count:"+count+",opt:"+opt+",thread:"+thread+",type:"+type+"[0.Transport,1.HTTP,2.Rest,3.HighRest]}--"+JSON.toJSONString(args));
		if(args!=null&&args.length>5){
			execute(count, opt, thread, type);
		}else{
			thread(count, opt, thread, type);
		}
	}
}
