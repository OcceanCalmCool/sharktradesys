define(function(require) {
	var $ = require("jquery");
	var justep = require("$UI/system/lib/justep");
	var Baas = require("$UI/sharkstrade/baas");
	require("./echarts-2.2.2/build/dist/echarts-all");

	var Model = function() {
		this.callParent();
	};
	// 全局变量分别用来两次请求Java
	var reqobj = {};
	var reqstkselect = {};

	Model.prototype.windowReceiver = function(event) {
		reqobj = event.data;
		var dataori = this.comp("tradedata");
		var datastklist = this.comp("stknumlistdata");

		var params = {
			"columns" : Baas.getDataColumns(dataori),
			"limit" : 20,
			"offset" : 0,
			"search" : reqobj
		};

		var success = function(resultData) {
			var append = event.options && event.options.append;
			dataori.loadData(resultData, append);
		};
		var timestamp = new Date().getTime();
		Baas.sendRequest({
			"url" : "/stk/yrfzapi?v=" + timestamp, // servlet请求地址
			"action" : "queryOrder", // action
			"params" : params, // action对应的参数
			"success" : success
		// 请求成功后的回调方法
		});

		// 取出table的最后一行，时间段内的交集
		// 这里有可能数据库没有数据的情况
		var stkset = {};
		var row = dataori.getLastRow();
		var names = [];
		var tmpstklist = [];
		
		if (row.val("searchret") != null) {
			names.push(row.val("searchret"));
			var tmpstr = names[0];
			tmpstklist = tmpstr.split(",");
			for ( var elt in tmpstklist) {
				var key = tmpstklist[elt];
				stkset[key] = row.val("time");
			}
			for ( var elt in stkset) {
				datastklist.add({
					"time" : stkset[elt],
					"stkcode" : elt
				});
			}
		}
		
	};

	Model.prototype.returnClick = function(event) {
		this.comp("indexReturn").open();
	};

	Model.prototype.showGraph = function(event) {
		var datastktraderec = this.comp("stktraderec");
		var timestamp = new Date().getTime();
		var params = {
			"columns" : Baas.getDataColumns(datastktraderec),
			"limit" : 100,
			"offset" : 0,
			"search" : reqstkselect
		};
		var success = function(resultData) {
			var append = event.options && event.options.append;
			datastktraderec.loadData(resultData, append);
		};
		Baas.sendRequest({
			"url" : "/stk/yrfzselectstk?v=" + timestamp, // servlet请求地址
			"action" : "querySelect", // action
			"params" : params, // action对应的参数
			"success" : success
		// 请求成功后的回调方法
		});
		var xtime = [];
		var ydelta = [];
		var yprice = [];
		var lRow = datastktraderec.getLastRow();

		datastktraderec.first();
		do {
			row = datastktraderec.getCurrentRow();
			xtime.push(row.val('time'));
			ydelta.push(row.val('outter') - row.val('inner'));
			yprice.push(row.val('curprice'));
			datastktraderec.next();
		} while (lRow != row);
		datastktraderec.clear();

		// 生成图形
		var option = {
			tooltip : {
				trigger : 'axis'
			},
			legend : {
				data : [ 'Delta', 'Price' ]
			},
			toolbox : {
				show : true,
				feature : {
					magicType : {
						show : true,
						type : [ 'line', 'bar' ]
					},
					dataView : {
						show : true,
						readOnly : false
					}
				}
			},
			calculable : true,
			xAxis : [ {
				type : 'category',
				boundaryGap : false,
				data : xtime,
			} ],
			yAxis : [ {
				type : 'value',
				name : 'Delta',
				axisLabel : {
					formatter : '{value}'
				}
			}, {
				type : 'value',
				name : 'Price',
				axisLabel : {
					formatter : '{value}'
				},
				scale : true
			} ],
			series : [ {
				name : 'Delta',
				type : 'line',
				data : ydelta,
				markLine : {
					data : [ {
						type : 'average',
						name : 'Average'
					} ]
				}
			}, {
				name : 'Price',
				type : 'line',
				data : yprice,
				yAxisIndex : 1,
				markLine : {
					data : [ {
						type : 'average',
						name : 'Average'
					} ]
				}
			} ]
		};
		var myChart = echarts.init(this.getElementByXid('main'));
		myChart.setOption(option);
		window.onresize = myChart.resize;
	};

	Model.prototype.btnInfoClick = function(event) {
		var row = event.bindingContext.$object;
		reqstkselect.gonetime = reqobj.gonetime;
		reqstkselect.stkcode = row.val("stkcode");
	};
	return Model;
});