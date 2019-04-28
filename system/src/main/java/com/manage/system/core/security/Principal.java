package com.manage.system.core.security;

import com.alibaba.fastjson.annotation.JSONField;
import com.manage.system.core.entity.BaseEntity;
import com.manage.system.modules.sys.entity.SystemBaseEntity;

import java.io.Serializable;

/**
 * 授权用户信息
 */
public class Principal extends BaseEntity implements java.security.Principal {

		private static final long serialVersionUID = 1L;

		private String username; // 登录名
		private String password;
		private String validateCode;
		private String name;

		public Principal(){

		}

		public Principal(String id, String username) {
			this.id = id;
			this.username = username;
		}

		@Override
		public String toString() {
			return id;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		@JSONField(serialize = false)
		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getValidateCode() {
			return validateCode;
		}

		public void setValidateCode(String validateCode) {
			this.validateCode = validateCode;
		}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Principal){
			Principal p= (Principal) obj;
			return this.id.equalsIgnoreCase(p.getId());
		}
		return super.equals(obj);
	}
}
