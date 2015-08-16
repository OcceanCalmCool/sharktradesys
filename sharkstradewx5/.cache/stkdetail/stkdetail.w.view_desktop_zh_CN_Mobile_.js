window.__justep.__ResourceEngine.loadCss([{url: '/$vcfa01e93267e40e29e61d035b388a820$lzh_CN$sdesktop$d/UI2/system/components/comp.min.css', include: '$model/UI2/system/components/justep/row/css/row,$model/UI2/system/components/justep/attachment/css/attachment,$model/UI2/system/components/justep/barcode/css/barcodeImage,$model/UI2/system/components/bootstrap/form/css/form,$model/UI2/system/components/justep/panel/css/panel,$model/UI2/system/components/justep/common/css/scrollable,$model/UI2/system/components/bootstrap/accordion/css/accordion,$model/UI2/system/components/bootstrap/pager/css/pager,$model/UI2/system/components/justep/scrollView/css/scrollView,$model/UI2/system/components/justep/input/css/datePickerPC,$model/UI2/system/components/bootstrap/navs/css/navs,$model/UI2/system/components/justep/contents/css/contents,$model/UI2/system/components/justep/popMenu/css/popMenu,$model/UI2/system/components/justep/lib/css/icons,$model/UI2/system/components/justep/titleBar/css/titleBar,$model/UI2/system/components/justep/dataTables/css/dataTables,$model/UI2/system/components/justep/dialog/css/dialog,$model/UI2/system/components/justep/messageDialog/css/messageDialog,$model/UI2/system/components/bootstrap/navbar/css/navbar,$model/UI2/system/components/justep/toolBar/css/toolBar,$model/UI2/system/components/justep/popOver/css/popOver,$model/UI2/system/components/justep/input/css/datePicker,$model/UI2/system/components/justep/dataTables/css/dataTables,$model/UI2/system/components/bootstrap/dialog/css/dialog,$model/UI2/system/components/justep/wing/css/wing,$model/UI2/system/components/bootstrap/scrollSpy/css/scrollSpy,$model/UI2/system/components/justep/menu/css/menu,$model/UI2/system/components/bootstrap/carousel/css/carousel,$model/UI2/system/components/bootstrap/dropdown/css/dropdown,$model/UI2/system/components/justep/common/css/forms,$model/UI2/system/components/justep/bar/css/bar,$model/UI2/system/components/bootstrap/tabs/css/tabs,$model/UI2/system/components/bootstrap/pagination/css/pagination'},{url: '/$vdc54bb829a7046468f14d523016b14c9$lzh_CN$sdesktop$d/UI2/system/components/bootstrap.min.css', include: '$model/UI2/system/components/bootstrap/lib/css/bootstrap,$model/UI2/system/components/bootstrap/lib/css/bootstrap-theme'}]);window.__justep.__ResourceEngine.loadJs(['/$v2401adefbddb4a75a9ca942b09d04a29$lzh_CN$sdesktop$d/UI2/system/components/comp.min.js','/$va19512821c9a4ca0be3b9b51186e2935$lzh_CN$sdesktop$d/UI2/system/common.min.js','/$ve84b2b95df904046bd8d970c50a32862$lzh_CN$sdesktop$d/UI2/system/core.min.js']);define(function(require){
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
}}); 
return __result;});
