window.__justep.__ResourceEngine.loadCss([{url: '/$v015f6521a24a4e3eb021d8e306d86f18$lzh_CN$sdesktop$d/UI2/system/components/comp.min.css', include: '$model/UI2/system/components/justep/row/css/row,$model/UI2/system/components/justep/attachment/css/attachment,$model/UI2/system/components/justep/barcode/css/barcodeImage,$model/UI2/system/components/bootstrap/form/css/form,$model/UI2/system/components/justep/panel/css/panel,$model/UI2/system/components/justep/common/css/scrollable,$model/UI2/system/components/bootstrap/accordion/css/accordion,$model/UI2/system/components/bootstrap/pager/css/pager,$model/UI2/system/components/justep/scrollView/css/scrollView,$model/UI2/system/components/justep/input/css/datePickerPC,$model/UI2/system/components/bootstrap/navs/css/navs,$model/UI2/system/components/justep/contents/css/contents,$model/UI2/system/components/justep/popMenu/css/popMenu,$model/UI2/system/components/justep/lib/css/icons,$model/UI2/system/components/justep/titleBar/css/titleBar,$model/UI2/system/components/justep/dataTables/css/dataTables,$model/UI2/system/components/justep/dialog/css/dialog,$model/UI2/system/components/justep/messageDialog/css/messageDialog,$model/UI2/system/components/bootstrap/navbar/css/navbar,$model/UI2/system/components/justep/toolBar/css/toolBar,$model/UI2/system/components/justep/popOver/css/popOver,$model/UI2/system/components/justep/input/css/datePicker,$model/UI2/system/components/justep/dataTables/css/dataTables,$model/UI2/system/components/bootstrap/dialog/css/dialog,$model/UI2/system/components/justep/wing/css/wing,$model/UI2/system/components/bootstrap/scrollSpy/css/scrollSpy,$model/UI2/system/components/justep/menu/css/menu,$model/UI2/system/components/bootstrap/carousel/css/carousel,$model/UI2/system/components/bootstrap/dropdown/css/dropdown,$model/UI2/system/components/justep/common/css/forms,$model/UI2/system/components/justep/bar/css/bar,$model/UI2/system/components/bootstrap/tabs/css/tabs,$model/UI2/system/components/bootstrap/pagination/css/pagination'},{url: '/$vf2c637a146ea44e5a5f31f871d02ec50$lzh_CN$sdesktop$d/UI2/system/components/bootstrap.min.css', include: '$model/UI2/system/components/bootstrap/lib/css/bootstrap,$model/UI2/system/components/bootstrap/lib/css/bootstrap-theme'}]);window.__justep.__ResourceEngine.loadJs(['/$v663aa1c7fe544b9f85dc8f1af533aa05$lzh_CN$sdesktop$d/UI2/system/components/comp.min.js','/$v75281ff7c5c3401d96aadd277821a8ee$lzh_CN$sdesktop$d/UI2/system/common.min.js','/$v7a18e6352dfc4e2eb182a94dbe0ede65$lzh_CN$sdesktop$d/UI2/system/core.min.js']);define(function(require){
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
 var __Data__ = require("$UI/system/components/justep/data/data");new __Data__(this,{"autoLoad":true,"confirmDelete":true,"confirmRefresh":true,"defCols":{"name":{"define":"name","label":"自选模型","name":"name","relation":"name","type":"String"}},"directDelete":false,"events":{},"idColumn":"name","initData":"[{\"name\":\"内盘大于外盘\"},{\"name\":\"内盘小于外盘\"}]","limit":20,"xid":"data1"});
 new __Data__(this,{"autoLoad":true,"confirmDelete":true,"confirmRefresh":true,"defCols":{"gonetime":{"define":"gonetime","label":"选择天数","name":"gonetime","relation":"gonetime","type":"String"},"range":{"define":"range","label":"幅度","name":"range","relation":"range","type":"String"},"stkmodel":{"define":"stkmodel","label":"自选模型","name":"stkmodel","relation":"stkmodel","type":"String"},"timestep":{"define":"timestep","label":"间隔","name":"timestep","relation":"timestep","type":"String"}},"directDelete":false,"events":{},"idColumn":"name","limit":20,"xid":"userModel"});
 new __Data__(this,{"autoLoad":true,"confirmDelete":true,"confirmRefresh":true,"defCols":{"modeltype":{"define":"modeltype","label":"模型类别代码","name":"modeltype","relation":"modeltype","rules":{"integer":true},"type":"Integer"},"searchret":{"define":"searchret","label":"选择结果","name":"searchret","relation":"searchret","type":"String"},"time":{"define":"time","label":"选择时间跨度","name":"time","relation":"time","rules":{"datetime":true},"type":"DateTime"}},"directDelete":false,"events":{},"idColumn":"time","limit":20,"xid":"tradedata"});
 var justep = require('$UI/system/lib/justep');if(!this['__justep__']) this['__justep__'] = {};if(!this['__justep__'].selectOptionsAfterRender)	this['__justep__'].selectOptionsAfterRender = function($element) {		var comp = justep.Component.getComponent($element);		if(comp) comp._addDefaultOption();	};
}}); 
return __result;});
