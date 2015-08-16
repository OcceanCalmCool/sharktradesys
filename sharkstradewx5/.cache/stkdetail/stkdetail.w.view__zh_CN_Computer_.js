window.__justep.__ResourceEngine.loadCss([{url: '/$v75b3df92e8fc4f8792ae5d6dca12b7f1$lzh_CN$s$d/UI2/system/components/comp.min.css', include: '$model/UI2/system/components/justep/row/css/row,$model/UI2/system/components/justep/attachment/css/attachment,$model/UI2/system/components/justep/barcode/css/barcodeImage,$model/UI2/system/components/bootstrap/form/css/form,$model/UI2/system/components/justep/panel/css/panel,$model/UI2/system/components/justep/common/css/scrollable,$model/UI2/system/components/bootstrap/accordion/css/accordion,$model/UI2/system/components/bootstrap/pager/css/pager,$model/UI2/system/components/justep/scrollView/css/scrollView,$model/UI2/system/components/justep/input/css/datePickerPC,$model/UI2/system/components/bootstrap/navs/css/navs,$model/UI2/system/components/justep/contents/css/contents,$model/UI2/system/components/justep/popMenu/css/popMenu,$model/UI2/system/components/justep/lib/css/icons,$model/UI2/system/components/justep/titleBar/css/titleBar,$model/UI2/system/components/justep/dataTables/css/dataTables,$model/UI2/system/components/justep/dialog/css/dialog,$model/UI2/system/components/justep/messageDialog/css/messageDialog,$model/UI2/system/components/bootstrap/navbar/css/navbar,$model/UI2/system/components/justep/toolBar/css/toolBar,$model/UI2/system/components/justep/popOver/css/popOver,$model/UI2/system/components/justep/input/css/datePicker,$model/UI2/system/components/justep/dataTables/css/dataTables,$model/UI2/system/components/bootstrap/dialog/css/dialog,$model/UI2/system/components/justep/wing/css/wing,$model/UI2/system/components/bootstrap/scrollSpy/css/scrollSpy,$model/UI2/system/components/justep/menu/css/menu,$model/UI2/system/components/bootstrap/carousel/css/carousel,$model/UI2/system/components/bootstrap/dropdown/css/dropdown,$model/UI2/system/components/justep/common/css/forms,$model/UI2/system/components/justep/bar/css/bar,$model/UI2/system/components/bootstrap/tabs/css/tabs,$model/UI2/system/components/bootstrap/pagination/css/pagination'},{url: '/$vd39f23bf2e844a8b9bca7607f5a6646f$lzh_CN$s$d/UI2/system/components/bootstrap.min.css', include: '$model/UI2/system/components/bootstrap/lib/css/bootstrap,$model/UI2/system/components/bootstrap/lib/css/bootstrap-theme'}]);window.__justep.__ResourceEngine.loadJs(['/$v2b8a0a689e654b158d2d64448e0b2611$lzh_CN$s$d/UI2/system/components/comp.min.js','/$v5c8aa6a168c349b39ec4411943b88e9d$lzh_CN$s$d/UI2/system/common.min.js','/$v1e16333084bf4851a12459b401f833f4$lzh_CN$s$d/UI2/system/core.min.js']);define(function(require){
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
