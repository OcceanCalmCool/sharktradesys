define(function(require) {
	var $ = require("jquery");
	var justep = require("$UI/system/lib/justep");

	var baas = {
		BASE_URL : "",

/**
 		options = {
 			"async" : 是否异步请求，默认false
 			"url" : 服务端请求地址，不包含BASE_URL
 			"action" : 动作标识
 			"params" : 动作对应的参数
 			"success" : 请求成功后的回调，参数(resultData, xhr)
 			"error" : 请求失败后的回调，参数(msg, xhr)
 		}
 */
		sendRequest : function(options) {
			var self = this;
			$.ajax({
				"type" : "post",
				"async" : options.async ? options.async : false,
				"dataType" : "json",
				"url" : this.BASE_URL + options.url,
				"data" : {
					"action" : options.action,
					// 序列化参数JSON字符串
					"params" : JSON.stringify(options.params)
				},
				"complete" : function(xhr) {
					if (xhr.readyState == 4 && xhr.status == 200) {
						if (options.success) {
							options.success.call(this, xhr.responseJSON, xhr);
						};
					} else {
						var msg = self.getErrorMsg(xhr);
						if (options.error) {
							options.error.call(this, msg, xhr);
						} else {
							self.showError(msg);
						}
					}
				}
			});
		},
		
		getErrorMsg : function(xhr) {
			return $(xhr.responseText).filter("h1:first").text() || xhr.statusText;
		},
		
		showError : function(msg) {
			justep.Util.hint(msg, {
				"type" : "danger"
			});
		},
		
		getDataColumns : function(data) {
			var columns = {};
			$.each(data.defCols, function(key){
				columns[key] = {
					"name" : data.defCols[key]["name"],
					"type" : data.defCols[key]["type"]
				};
			});
			return columns;
		}
		
	};

	return baas;
});