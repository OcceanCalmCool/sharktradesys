define(function(require){
require('$model/UI2/system/components/justep/windowReceiver/windowReceiver');
require('$model/UI2/system/components/justep/row/row');
require('$model/UI2/system/components/justep/panel/panel');
require('$model/UI2/system/components/justep/button/buttonGroup');
require('$model/UI2/system/components/justep/list/list');
require('$model/UI2/system/components/justep/windowDialog/windowDialog');
require('$model/UI2/system/components/justep/window/window');
require('$model/UI2/system/components/justep/data/data');
require('$model/UI2/system/components/justep/titleBar/titleBar');
require('$model/UI2/system/components/justep/contents/contents');
require('$model/UI2/system/components/justep/panel/child');
require('$model/UI2/system/components/justep/scrollView/scrollView');
require('$model/UI2/system/components/justep/contents/content');
require('$model/UI2/system/components/justep/model/model');
require('$model/UI2/system/components/justep/button/button');
require('$model/UI2/system/components/justep/output/output');
var __parent1=require('$model/UI2/system/lib/base/modelBase'); 
var __parent0=require('$model/UI2/sharkstrade/stkdetail'); 
var __result = __parent1._extend(__parent0).extend({
	constructor:function(contextUrl){
	this.__sysParam='true';
	this.__contextUrl=contextUrl;
	this.__id='__baseID__';
	this._flag_='042280fa43cf3908c0a0a8548bf8e621';
	this.callParent(contextUrl);
 var __Data__ = require("$UI/system/components/justep/data/data");new __Data__(this,{"autoLoad":true,"confirmDelete":true,"confirmRefresh":true,"defCols":{"modeltype":{"define":"modeltype","label":"选择模型代码","name":"modeltype","relation":"modeltype","rules":{"integer":true},"type":"Integer"},"searchret":{"define":"searchret","label":"选择结果","name":"searchret","relation":"searchret","type":"String"},"time":{"define":"time","label":"选择时间","name":"time","relation":"time","rules":{"datetime":true},"type":"DateTime"}},"directDelete":false,"events":{},"idColumn":"time","initData":"[]","limit":20,"xid":"tradedata"});
 new __Data__(this,{"autoLoad":true,"confirmDelete":true,"confirmRefresh":true,"defCols":{"stkcode":{"define":"stkcode","label":"股票代码","name":"stkcode","relation":"stkcode","type":"String"},"time":{"define":"time","label":"选择时间","name":"time","relation":"time","rules":{"datetime":true},"type":"DateTime"}},"directDelete":false,"events":{},"idColumn":"stkcode","limit":20,"xid":"stknumlistdata"});
 new __Data__(this,{"autoLoad":true,"confirmDelete":true,"confirmRefresh":true,"defCols":{"code":{"define":"code","label":"代码","name":"code","relation":"code","type":"String"},"curprice":{"define":"curprice","label":"当前价格","name":"curprice","relation":"curprice","rules":{"number":true},"type":"Float"},"inner":{"define":"inner","label":"内盘","name":"inner","relation":"inner","rules":{"integer":true},"type":"Integer"},"outter":{"define":"outter","label":"外盘 ","name":"outter","relation":"outter","rules":{"integer":true},"type":"Integer"},"time":{"define":"time","label":"时间","name":"time","relation":"time","rules":{"datetime":true},"type":"DateTime"}},"directDelete":false,"events":{},"idColumn":"code","limit":20,"xid":"stktraderec"});
}}); 
return __result;});