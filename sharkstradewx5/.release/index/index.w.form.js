define(function(require){
require('$model/UI2/system/components/justep/labelEdit/labelEdit');
require('$model/UI2/system/components/justep/panel/panel');
require('$model/UI2/system/components/justep/panel/child');
require('$model/UI2/system/components/justep/contents/content');
require('$model/UI2/system/components/justep/select/select');
require('$model/UI2/system/components/justep/button/buttonGroup');
require('$model/UI2/system/components/justep/windowDialog/windowDialog');
require('$model/UI2/system/components/justep/model/model');
require('$model/UI2/system/components/justep/window/window');
require('$model/UI2/system/components/justep/controlGroup/controlGroup');
require('$model/UI2/system/components/justep/contents/contents');
require('$model/UI2/system/components/justep/titleBar/titleBar');
require('$model/UI2/system/components/justep/data/data');
require('$model/UI2/system/components/justep/button/button');
require('$model/UI2/system/components/justep/input/input');
var __parent1=require('$model/UI2/system/lib/base/modelBase'); 
var __parent0=require('$model/UI2/sharkstrade/index'); 
var __result = __parent1._extend(__parent0).extend({
	constructor:function(contextUrl){
	this.__sysParam='true';
	this.__contextUrl=contextUrl;
	this.__id='__baseID__';
	this._flag_='2f9744cd59a96f91dc59473911932bf7';
	this.callParent(contextUrl);
 var __Data__ = require("$UI/system/components/justep/data/data");new __Data__(this,{"autoLoad":true,"confirmDelete":true,"confirmRefresh":true,"defCols":{"name":{"define":"name","label":"自选模型","name":"name","relation":"name","type":"String"}},"directDelete":false,"events":{},"idColumn":"name","initData":"[{\"name\":\"资金流出\"},{\"name\":\"资金流入\"}]","limit":20,"xid":"data1"});
 new __Data__(this,{"autoLoad":true,"confirmDelete":true,"confirmRefresh":true,"defCols":{"gonetime":{"define":"gonetime","label":"选择天数","name":"gonetime","relation":"gonetime","type":"String"},"range":{"define":"range","label":"幅度","name":"range","relation":"range","type":"String"},"stkmodel":{"define":"stkmodel","label":"自选模型","name":"stkmodel","relation":"stkmodel","type":"String"},"timestep":{"define":"timestep","label":"间隔","name":"timestep","relation":"timestep","type":"String"}},"directDelete":false,"events":{},"idColumn":"name","limit":20,"xid":"userModel"});
 new __Data__(this,{"autoLoad":true,"confirmDelete":true,"confirmRefresh":true,"defCols":{"modeltype":{"define":"modeltype","label":"模型类别代码","name":"modeltype","relation":"modeltype","rules":{"integer":true},"type":"Integer"},"searchret":{"define":"searchret","label":"选择结果","name":"searchret","relation":"searchret","type":"String"},"time":{"define":"time","label":"选择时间跨度","name":"time","relation":"time","rules":{"datetime":true},"type":"DateTime"}},"directDelete":false,"events":{},"idColumn":"time","limit":20,"xid":"tradedata"});
 new __Data__(this,{"autoLoad":true,"confirmDelete":true,"confirmRefresh":true,"defCols":{"endtime":{"define":"endtime","label":"结束时间","name":"endtime","relation":"endtime","rules":{"datetime":true},"type":"DateTime"},"starttime":{"define":"starttime","label":"开始时间","name":"starttime","relation":"starttime","rules":{"datetime":true},"type":"DateTime"},"stkcode":{"define":"stkcode","label":"股票代码","name":"stkcode","relation":"stkcode","type":"String"}},"directDelete":false,"events":{},"idColumn":"stkcode","limit":20,"xid":"datastklabel"});
 new __Data__(this,{"autoLoad":true,"confirmDelete":true,"confirmRefresh":true,"defCols":{"code":{"define":"code","label":"代码","name":"code","relation":"code","type":"String"},"curprice":{"define":"curprice","label":"价格","name":"curprice","relation":"curprice","rules":{"number":true},"type":"Float"},"inner":{"define":"inner","label":"内盘","name":"inner","relation":"inner","rules":{"integer":true},"type":"Integer"},"outter":{"define":"outter","label":"外盘","name":"outter","relation":"outter","rules":{"integer":true},"type":"Integer"},"time":{"define":"time","label":"时间","name":"time","relation":"time","rules":{"datetime":true},"type":"DateTime"}},"directDelete":false,"events":{},"idColumn":"code","limit":20,"xid":"datatraderec"});
 var justep = require('$UI/system/lib/justep');if(!this['__justep__']) this['__justep__'] = {};if(!this['__justep__'].selectOptionsAfterRender)	this['__justep__'].selectOptionsAfterRender = function($element) {		var comp = justep.Component.getComponent($element);		if(comp) comp._addDefaultOption();	};
}}); 
return __result;});