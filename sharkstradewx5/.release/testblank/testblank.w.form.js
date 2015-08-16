define(function(require){
require('$model/UI2/system/components/justep/model/model');
require('$model/UI2/system/components/justep/window/window');
var __parent1=require('$model/UI2/system/lib/base/modelBase'); 
var __parent0=require('$model/UI2/sharkstrade/testblank'); 
var __result = __parent1._extend(__parent0).extend({
	constructor:function(contextUrl){
	this.__sysParam='true';
	this.__contextUrl=contextUrl;
	this.__id='__baseID__';
	this._flag_='12c998c8798751f9bdffa05cee2ff015';
	this.callParent(contextUrl);
}}); 
return __result;});