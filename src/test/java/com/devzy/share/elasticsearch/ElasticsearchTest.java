package com.devzy.share.elasticsearch;

import java.util.Date;

import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;

import com.alibaba.fastjson.JSONObject;
import com.devzy.share.common.ElasticsearchSingleton;
import com.devzy.share.elasticsearch.http.ElasticsearchHttpFactory;
import com.devzy.share.elasticsearch.transport.ElasticsearchTransportFactory;

public class ElasticsearchTest {
	private static ElasticsearchTransportFactory tfactory = ElasticsearchSingleton.getIntance().transportES();
	private static ElasticsearchHttpFactory hfactory = ElasticsearchSingleton.getIntance().httpES();
//	private static ElasticsearchRestFactory rfactory = ElasticsearchSingleton.getIntance().restES();
//	private static ElasticsearchRestFactory hrfactory = ElasticsearchSingleton.getIntance().highrestES();
	public static void test(){
		JSONObject json = new JSONObject();
		json.fluentPut("id", 1).fluentPut("name", "user").fluentPut("price", 2.36).fluentPut("flag", true);
		json.fluentPut("datetime", new Date()).fluentPut("array", new String[]{"11","22"}).fluentPut("chara", 'a').fluentPut("obj", new Object());
		hfactory.insert("test", "json", json.toJSONString());
	}
	public static void test1() throws Exception{
		String analyze = "{\"settings\":{\"analysis\":{\"filter\":{\"default_stopwords\":{\"type\":\"stop\",\"stopwords\":[\"a\",\"an\",\"and\",\"are\",\"as\",\"at\",\"be\",\"but\",\"by\",\"for\",\"if\",\"in\",\"into\",\"is\",\"it\",\"no\",\"not\",\"of\",\"on\",\"or\",\"such\",\"that\",\"the\",\"their\",\"then\",\"there\",\"these\",\"they\",\"this\",\"to\",\"was\",\"will\",\"with\"]}},\"char_filter\":{\"symbol_transform\":{\"mappings\":[\"&=> and \",\"||=> or \"],\"type\":\"mapping\"}},\"analyzer\":{\"es_analyzer\":{\"filter\":[\"lowercase\",\"default_stopwords\"],\"char_filter\":[\"html_strip\",\"symbol_transform\"],\"type\":\"custom\",\"tokenizer\":\"standard\"}}}}}";
		Settings settings = Settings.builder().loadFromSource(analyze, XContentType.JSON).build();
//		AnalysisRegistry registry = new AnalysisModule(new Environment(settings), Collections.singletonList(new AnalysisPlugin() {
//			 	@Override
//	            public Map<String, AnalysisProvider<TokenFilterFactory>> getTokenFilters() {
//	                return AnalysisPlugin.super.getTokenFilters();
//	            }
//			 	@Override
//			 	public Map<String, AnalysisProvider<TokenizerFactory>> getTokenizers() {
//			 		return AnalysisPlugin.super.getTokenizers();
//			 	}
//	            @Override
//	            public Map<String, AnalysisProvider<CharFilterFactory>> getCharFilters() {
//	                return AnalysisPlugin.super.getCharFilters();
//	            }
//		} )).getAnalysisRegistry();
//		IndexSettings idxSettings = new IndexSettings(IndexMetaData.builder("test").build(), settings);
//		IndexAnalyzers indexAnalyzers = registry.build(idxSettings);
		CreateIndexResponse response = tfactory.getClient().admin().indices().prepareCreate("thr2").setSettings(settings).get();
		System.out.println(response.isAcknowledged());
	}
	public static void main(String[] args) throws Exception {
		String result = "";
//		result = hfactory.indices();
//		System.out.println(result);
//		System.out.println("---------------------------------------------------------------------------------");
//		result = rfactory.indices();
//		System.out.println(result);
//		System.out.println("---------------------------------------------------------------------------------");
//		result = tfactory.indices();
//		System.out.println(result);
//		System.exit(1);
		test1();
//		result = tfactory.es_analyzer("thr1");
		System.out.println(result);
		System.out.println("---------------------------------------------------------------------------------");
		System.exit(1);
	}

}
