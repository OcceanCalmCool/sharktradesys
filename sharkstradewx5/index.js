define(function(require) {
	var $ = require("jquery");

	var justep = require("$UI/system/lib/justep");
	var utils = require("$UI/system/components/justep/common/utils");
	var Baas = require("$UI/sharkstrade/baas");
	require("./echarts-2.2.2/build/dist/echarts-all");

	var Model = function() {
		this.callParent();
	};
	var reqobj = {};
	Model.prototype.searchClick = function(event) {
		var text = this.comp("modelselect").val();
		reqobj.gonetime = this.comp("gonetime").val();
		reqobj.range = this.comp("range").val();
		reqobj.tstep = this.comp("tstep").val();
		if (text === "资金流出") {
			reqobj.modelid = 1;
		} else if (text === "资金流入") {
			reqobj.modelid = 2;
		} else {
			reqobj.modelid = -1;
		}
		;
		// 打开子页面sktdetail,并且向字页面传递参数
		if (reqobj.gonetime != null && reqobj.modelid != -1) {
			this.comp('stkDetail').open({
				data : reqobj
			});
		}
		reqobj = {};
	};
	Model.prototype.btnStkSearch = function(event) {
		var timestamp = new Date().getTime();
		var stkdatatrade = this.comp("datatraderec");
		var reqstkselect = {};
		reqstkselect.code = this.comp("stkinput").val();
		reqstkselect.stime = this.comp("stinput").val();
		reqstkselect.etime = this.comp("etinput").val();
		var params = {
			"columns" : Baas.getDataColumns(stkdatatrade),
			"limit" : 1000,
			"offset" : 0,
			"search" : reqstkselect
		};
		var success = function(resultData) {
			var append = event.options && event.options.append;
			stkdatatrade.loadData(resultData, append);
		};
		Baas.sendRequest({
			"url" : "/stk/yrfzselectstk?v=" + timestamp, // servlet请求地址
			"action" : "querySelectByCode", // action
			"params" : params, // action对应的参数
			"success" : success
		// 请求成功后的回调方法
		});
		var xtime = [];
		var ydelta = [];
		var yprice = [];
		var lRow = stkdatatrade.getLastRow();
		// 这里可以通过最后一行数据判断有没有数据，如果没有数据，后面都不需要执行
		if (lRow != null) {
			stkdatatrade.first();
			do {
				row = stkdatatrade.getCurrentRow();
				xtime.push(row.val('time'));
				ydelta.push(row.val('outter') - row.val('inner'));
				yprice.push(row.val('curprice'));
				stkdatatrade.next();
			} while (lRow != row);
			stkdatatrade.clear();
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
			var myChart = echarts.init(this.getElementByXid('stkgdiv'));
			myChart.setOption(option);
			window.onresize = myChart.resize;
		}
		;
	};
	return Model;
});