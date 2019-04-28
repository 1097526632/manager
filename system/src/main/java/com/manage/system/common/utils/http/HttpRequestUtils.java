package com.manage.system.common.utils.http;

import com.alibaba.fastjson.JSONObject;
import com.manage.system.common.utils.StringUtils;
import okhttp3.*;
import org.apache.commons.io.IOUtils;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 */
public final class HttpRequestUtils {

	private HttpRequestUtils() {
	}

	public static String get(String url) {
		return delegate.get(url);
	}

	public static String get(String url, Map<String, String> queryParas) {
		return delegate.get(url, queryParas);
	}

	public static String get(String url, Map<String, String> queryParas, Map<String,String> header) {
		return delegate.get(url, queryParas,header);
	}

	public static String post(String url, String data) {
		return delegate.post(url, data);
	}

	public static String post(String url, Map<String, String> queryParas) {
		return delegate.post(url, queryParas);
	}

	public static String post(String url, Map<String, String> queryParas, Map<String,String> headParams) {
		return delegate.post(url, queryParas,headParams);
	}

	public static String postJson(String url, Object queryParas, Map<String,String> headParams) {
		return delegate.postJson(url, queryParas,headParams);
	}

	public static String postSSL(String url, String data, String certPath, String certPass) {
		return delegate.postSSL(url, data, certPath, certPass);
	}

	public static String postSSL(String url, String data, InputStream certFile, String certPass) {
		return delegate.postSSL(url, data, certPass, certFile);
	}

	public static InputStream download(String url, String params) {
		return delegate.download(url, params);
	}

	public static String upload(String url, File file, String params) {
		return delegate.upload(url, file, params);
	}

	/**
	 * http请求工具 委托 优先使用OkHttp 最后使用JFinal HttpKit
	 */
	private interface HttpDelegate {
		String get(String url);

		String get(String url, Map<String, String> queryParas);

		String post(String url, String data);

		String post(String url, Map<String, String> queryParas);
		String post(String url, Map<String, String> queryParas, Map<String, String> headParams);
		String postJson(String url, Object queryParas, Map<String, String> headParams);

		String postSSL(String url, String data, String certPath, String certPass);

		String postSSL(String url, String data, String certPass, InputStream certFile);

		InputStream download(String url, String params);

		String upload(String url, File file, String params);

		String get(String url, Map<String, String> queryParas, Map<String, String> header);
	}

	// http请求工具代理对象
	private static final HttpDelegate delegate;

	static {
		HttpDelegate delegateToUse = null;
		// okhttp3.OkHttpClient?
		String className=HttpRequestUtils.class.getName();
		if (className.endsWith("okhttp3.OkHttpClient")) {
			delegateToUse = new OkHttp3Delegate();
		}else {
			delegateToUse = new HttpKitDelegate();
		}
		delegate = delegateToUse;
	}

	/**
	 * OkHttp3代理
	 */
	private static class OkHttp3Delegate implements HttpDelegate {
		private OkHttpClient httpClient;

		public OkHttp3Delegate() {
			// 分别设置Http的连接,写入,读取的超时时间
			httpClient = new OkHttpClient().newBuilder().connectTimeout(10, TimeUnit.SECONDS)
					.writeTimeout(10, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).build();
		}

		private static final MediaType CONTENT_TYPE_FORM = MediaType
				.parse("application/x-www-form-urlencoded");

		private String exec(Request request) {
			try {
				Response response = httpClient.newCall(request).execute();

				if (!response.isSuccessful())
					throw new RuntimeException("Unexpected code " + response);

				return response.body().string();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public String get(String url) {
			Request request = new Request.Builder().url(url).get().build();
			return exec(request);
		}

		@Override
		public String get(String url, Map<String, String> queryParas) {
			HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
			for (Entry<String, String> entry : queryParas.entrySet()) {
				urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
			}
			HttpUrl httpUrl = urlBuilder.build();
			Request request = new Request.Builder().url(httpUrl).get().build();
			return exec(request);
		}

		@Override
		public String get(String url, Map<String, String> queryParas, Map<String, String> header) {
			HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
			for (Entry<String, String> entry : queryParas.entrySet()) {
				urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
			}
			HttpUrl httpUrl = urlBuilder.build();
			Request.Builder reBuilder = new Request.Builder().url(httpUrl).get();
			if(header!=null){
				for(Entry<String,String> entry:header.entrySet()){
					reBuilder.addHeader(entry.getKey(),entry.getValue());
				}
			}
			Request request=reBuilder.build();
			return exec(request);
		}

		@Override
		public String post(String url, String params) {
			RequestBody body = RequestBody.create(CONTENT_TYPE_FORM, params);
			Request request = new Request.Builder().url(url).post(body).build();
			return exec(request);
		}

		@Override
		public String postSSL(String url, String data, String certPass, InputStream certFile) {
			RequestBody body = RequestBody.create(CONTENT_TYPE_FORM, data);
			Request request = new Request.Builder().url(url).post(body).build();

			InputStream inputStream = certFile;
			try {
				KeyStore clientStore = KeyStore.getInstance("PKCS12");
				char[] passArray = certPass.toCharArray();
				clientStore.load(inputStream, passArray);

				KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
				kmf.init(clientStore, passArray);
				KeyManager[] kms = kmf.getKeyManagers();
				SSLContext sslContext = SSLContext.getInstance("TLSv1");

				sslContext.init(kms, null, new SecureRandom());

				@SuppressWarnings("deprecation")
				OkHttpClient httpsClient = new OkHttpClient().newBuilder()
						.connectTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS)
						.readTimeout(30, TimeUnit.SECONDS).sslSocketFactory(sslContext.getSocketFactory()).build();

				Response response = httpsClient.newCall(request).execute();

				if (!response.isSuccessful())
					throw new RuntimeException("Unexpected code " + response);

				return response.body().string();
			} catch (Exception e) {
				throw new RuntimeException(e);
			} finally {
				IOUtils.closeQuietly(inputStream);
			}
		}

		@Override
		public String postSSL(String url, String data, String certPath, String certPass) {
			RequestBody body = RequestBody.create(CONTENT_TYPE_FORM, data);
			Request request = new Request.Builder().url(url).post(body).build();

			InputStream inputStream = null;
			try {
				KeyStore clientStore = KeyStore.getInstance("PKCS12");
				inputStream = new FileInputStream(certPath);
				char[] passArray = certPass.toCharArray();
				clientStore.load(inputStream, passArray);

				KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
				kmf.init(clientStore, passArray);
				KeyManager[] kms = kmf.getKeyManagers();
				SSLContext sslContext = SSLContext.getInstance("TLSv1");

				sslContext.init(kms, null, new SecureRandom());

				@SuppressWarnings("deprecation")
				OkHttpClient httpsClient = new OkHttpClient().newBuilder()
						.connectTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS)
						.readTimeout(30, TimeUnit.SECONDS).sslSocketFactory(sslContext.getSocketFactory()).build();

				Response response = httpsClient.newCall(request).execute();

				if (!response.isSuccessful())
					throw new RuntimeException("Unexpected code " + response);

				return response.body().string();
			} catch (Exception e) {
				throw new RuntimeException(e);
			} finally {
				IOUtils.closeQuietly(inputStream);
			}
		}

		@Override
		public InputStream download(String url, String params) {
			Request request;
			if (StringUtils.isNotBlank(params)) {
				RequestBody body = RequestBody.create(CONTENT_TYPE_FORM, params);
				request = new Request.Builder().url(url).post(body).build();
			} else {
				request = new Request.Builder().url(url).get().build();
			}
			try {
				Response response = httpClient.newCall(request).execute();

				if (!response.isSuccessful())
					throw new RuntimeException("Unexpected code " + response);

				return response.body().byteStream();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		@Override
		public String upload(String url, File file, String params) {
			RequestBody fileBody = RequestBody
					.create(MediaType.parse("application/octet-stream"), file);

			MultipartBody.Builder builder = new MultipartBody.Builder()
					.setType(MultipartBody.FORM).addFormDataPart("media", file.getName(), fileBody);

			if (StringUtils.isNotBlank(params)) {
				builder.addFormDataPart("description", params);
			}

			RequestBody requestBody = builder.build();
			Request request = new Request.Builder().url(url).post(requestBody).build();

			return exec(request);
		}



		@Override
		public String post(String url, Map<String, String> params) {
			return post(url,params,null);
		}

		@Override
		public String post(String url, Map<String, String> params, Map<String, String> headParams) {
			FormBody.Builder builder = new FormBody.Builder();
			if (params == null) {
				params = new HashMap<String, String>();
			}
			if (params != null) {
				Set<Entry<String, String>> entries = params.entrySet();
				for (Entry<String, String> entry : entries) {
					builder.add(entry.getKey(), entry.getValue());
				}
			}
			 Request.Builder reBuilder= new Request.Builder().url(url).post(builder.build());
			if(headParams!=null){
				Set<Entry<String, String>> entries = headParams.entrySet();
				for (Entry<String, String> entry : entries) {
					reBuilder.addHeader(entry.getKey(), entry.getValue());
				}
			}

			Request request=reBuilder.build();
			return exec(request);
		}

		public String postJson(String url, Object params, Map<String,String> headParams){

			if (params == null) {
				params = new HashMap<String, String>();
			}
			RequestBody requestBody=null;
			if (params != null) {
				requestBody= FormBody.create(MediaType.parse("application/json; charset=utf-8"),(params instanceof String)?params.toString(): JSONObject.toJSONString(params));
			}
			Request.Builder reBuilder= new Request.Builder().url(url).post(requestBody);
			if(headParams!=null){
				Set<Entry<String, String>> entries = headParams.entrySet();
				for (Entry<String, String> entry : entries) {
					reBuilder.addHeader(entry.getKey(), entry.getValue());
				}
			}
			Request request=reBuilder.build();
			return exec(request);
		}


	}

	/**
	 * HttpKit代理
	 */
	private static class HttpKitDelegate implements HttpDelegate {

		@Override
		public String get(String url) {
			return HttpKit.get(url);
		}

		@Override
		public String get(String url, Map<String, String> queryParas) {
			return HttpKit.get(url, queryParas);
		}

		@Override
		public String get(String url, Map<String, String> queryParas, Map<String, String> header) {
			return HttpKit.get(url, queryParas,header);
		}

		@Override
		public String post(String url, String data) {
			return HttpKit.post(url, data);
		}

		@Override
		public String post(String url, Map<String, String> queryParas) {
			return HttpKit.post(url, queryParas, "null");
		}

		@Override
		public String post(String url, Map<String, String> params, Map<String, String> headParams) {
			return HttpKit.post(url, params, "null",headParams);
		}

		@Override
		public String postJson(String url, Object params, Map<String, String> headParams) {
			String jsonParams="";
			if(params!=null){
				jsonParams=(params instanceof String)?params.toString(): JSONObject.toJSONString(params);
			}
			return HttpKit.post(url, null, jsonParams,headParams);
		}


		@Override
		public String postSSL(String url, String data, String certPass, InputStream certFile) {
			return HttpKitExt.postSSL(url, data, certFile, certPass);
		}

		@Override
		public String postSSL(String url, String data, String certPath, String certPass) {
			return HttpKitExt.postSSL(url, data, certPath, certPass);
		}

		@Override
		public InputStream download(String url, String params) {
			try {
				return HttpKitExt.downloadMaterial(url, params);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public String upload(String url, File file, String params) {
			try {
				return HttpKitExt.uploadMedia(url, file, params);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

	}
}
