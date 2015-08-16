<?xml version="1.0" encoding="utf-8"?>
<div xmlns="http://www.w3.org/1999/xhtml" xid="window" class="window" component="$UI/system/components/justep/window/window"
  design="device:mobile">  
  <div component="$UI/system/components/justep/model/model" xid="model" style="height:auto;left:670px;top:176px;"> 
    <div component="$UI/system/components/justep/data/data" autoLoad="true"
      xid="data1" idColumn="name"> 
      <column label="自选模型" name="name" type="String" xid="default2"/>  
      <data xid="default3">[{"name":"资金流出"},{"name":"资金流入"}]</data> 
    </div>  
    <div component="$UI/system/components/justep/data/data" autoLoad="true"
      xid="userModel" idColumn="name"> 
      <column label="自选模型" name="stkmodel" type="String" xid="default1"/>  
      <column label="选择天数" name="gonetime" type="String" xid="default4"/>  
      <column label="幅度" name="range" type="String" xid="default6"/>  
      <column label="间隔" name="timestep" type="String" xid="default7"/> 
    </div>  
    <div component="$UI/system/components/justep/data/data" autoLoad="true"
      xid="tradedata" idColumn="time">
      <column label="选择时间跨度" name="time" type="DateTime" xid="xid1"/>  
      <column label="模型类别代码" name="modeltype" type="Integer" xid="xid2"/>  
      <column label="选择结果" name="searchret" type="String" xid="xid3"/>
    </div>
  <div component="$UI/system/components/justep/data/data" autoLoad="true" xid="datastklabel" idColumn="stkcode"><column label="开始时间" name="starttime" type="DateTime" xid="xid4"></column>
  <column label="结束时间" name="endtime" type="DateTime" xid="xid5"></column>
  <column label="股票代码" name="stkcode" type="String" xid="xid6"></column></div>
  <div component="$UI/system/components/justep/data/data" autoLoad="true" xid="datatraderec" idColumn="code"><column label="时间" name="time" type="DateTime" xid="xid7"></column>
  <column label="代码" name="code" type="String" xid="xid8"></column>
  <column label="内盘" name="inner" type="Integer" xid="xid9"></column>
  <column label="外盘" name="outter" type="Integer" xid="xid10"></column>
  <column label="价格" name="curprice" type="Float" xid="xid11"></column></div></div>  
  <span component="$UI/system/components/justep/windowDialog/windowDialog" xid="stkDetail"
    src="$UI/sharkstrade/stkdetail.w"/>
  <div component="$UI/system/components/justep/panel/panel" class="x-panel x-full"
    xid="panel1"> 
    <div class="x-panel-top" xid="top1"> 
      <div component="$UI/system/components/justep/titleBar/titleBar" class="x-titlebar"
        xid="titleBar1" title="如意助手"> 
        <div class="x-titlebar-left" xid="div1"/>  
        <div class="x-titlebar-title" xid="div2">如意助手</div>  
        <div class="x-titlebar-right reverse" xid="div3"/> 
      </div> 
    </div>  
    <div class="x-panel-bottom" xid="bottom1"> 
      <div component="$UI/system/components/justep/button/buttonGroup" class="btn-group btn-group-justified"
        tabbed="true" xid="buttonGroup1"> 
        <a component="$UI/system/components/justep/button/button" class="btn btn-default btn-icon-top"
          label="选股" xid="button1" icon="icon-ios7-paperplane" target="customer"> 
          <i xid="i1" class="icon-ios7-paperplane"/>  
          <span xid="span1">模型</span> 
        </a>  
        <a component="$UI/system/components/justep/button/button" class="btn btn-default btn-icon-top"
          label="个股" xid="button2" icon="icon-android-locate" target="searchinfo"> 
          <i xid="i2" class="icon-android-locate"/>  
          <span xid="span2">个股</span> 
        </a>  
        <a component="$UI/system/components/justep/button/button" class="btn btn-default btn-icon-top"
          label="资讯" xid="button3" icon="icon-android-friends" target="market"> 
          <i xid="i3" class="icon-android-friends"/>  
          <span xid="span3">行情</span> 
        </a>  
        <a component="$UI/system/components/justep/button/button" class="btn btn-default btn-icon-top"
          label="自选" xid="button4" icon="icon-android-add-contact" target="mine"> 
          <i xid="i4" class="icon-android-add-contact"/>  
          <span xid="span4">个人</span> 
        </a> 
      </div> 
    </div>  
    <div class="x-panel-content" xid="content1"> 
      <div component="$UI/system/components/justep/contents/contents" class="x-contents x-full"
        active="0" xid="pages"> 
        <div class="x-contents-content" xid="customer"> 
          <div component="$UI/system/components/justep/controlGroup/controlGroup"
            class="x-control-group" title="时间选择" xid="controlGroup4"> 
            <div class="x-control-group-title" xid="controlGroupTitle4" style="height:40px;"> 
              <span xid="span10">时间选择</span> 
            </div>  
            <div component="$UI/system/components/justep/labelEdit/labelEdit"
              class="x-label-edit x-label30" xid="lbinputtime"> 
              <label class="x-label" xid="gtlabel" bind-text="userModel.label('gonetime')"/>  
              <input component="$UI/system/components/justep/input/input" class="form-control x-edit"
                xid="gonetime"/>  
              <label xid="dayunit"><![CDATA[天]]></label>
            </div> 
          </div>  
          <div component="$UI/system/components/justep/controlGroup/controlGroup"
            class="x-control-group" title="模型定义" xid="controlGroup3"> 
            <div class="x-control-group-title" xid="controlGroupTitle3"> 
              <span xid="span8">模型定义</span> 
            </div>  
            <div component="$UI/system/components/justep/labelEdit/labelEdit"
              class="x-label-edit x-label30" xid="lbselectmodel" style="font-family:Cambria;"> 
              <label class="x-label" xid="modellb" bind-text="userModel.label('stkmodel')"/>  
              <select component="$UI/system/components/justep/select/select" class="form-control x-edit"
                xid="modelselect" bind-ref="userModel.ref('stkmodel')" optionsAutoLoad="true"
                bind-labelRef="userModel.ref('stkmodel')" bind-options="data1" bind-optionsValue="name"/> 
            </div>  
            <div component="$UI/system/components/justep/labelEdit/labelEdit"
              class="x-label-edit x-label30" xid="lbinputrange"> 
              <label class="x-label" xid="rangelabel" bind-text="userModel.label('range')"/>  
              <input component="$UI/system/components/justep/input/input" class="form-control x-edit"
                xid="range" disabled="true"/> 
            </div>  
            <div component="$UI/system/components/justep/labelEdit/labelEdit"
              class="x-label-edit x-label30" xid="labelInputtstep"> 
              <label class="x-label" xid="tsteplabel" bind-text="userModel.label('timestep')"/>  
              <input component="$UI/system/components/justep/input/input" class="form-control x-edit"
                xid="tstep" disabled="true"/> 
            </div> 
          </div>  
          <div component="$UI/system/components/justep/button/buttonGroup"
            class="btn-group btn-group-justified" tabbed="true" xid="buttonGroup2">
            <a component="$UI/system/components/justep/button/button" class="btn x-orange"
              label="Search" xid="modelsearch" icon="icon-search" onClick="searchClick"> 
              <i xid="i6" class="icon-search"/>  
              <span xid="span9">Search</span> 
            </a>
          </div> 
        </div>  
        <div class="x-contents-content" xid="searchinfo"><div component="$UI/system/components/justep/controlGroup/controlGroup" class="x-control-group" title="个股选择" xid="controlGroup1" style="height:261px;">
   <div class="x-control-group-title" xid="controlGroupTitle1">
    <span xid="span5">title</span></div> 
  <div component="$UI/system/components/justep/labelEdit/labelEdit" class="x-label-edit x-label30" xid="labelInput4">
   <label class="x-label" xid="stkcodelb" bind-text="datastklabel.label('stkcode')"></label>
   <input component="$UI/system/components/justep/input/input" class="form-control x-edit" xid="stkinput" dataType="String"></input></div><div component="$UI/system/components/justep/labelEdit/labelEdit" class="x-label-edit x-label30" xid="labelInput1">
   <label class="x-label" xid="starttime" bind-text="datastklabel.label('starttime')"></label>
   <input component="$UI/system/components/justep/input/input" class="form-control x-edit" xid="stinput" dataType="DateTime"></input></div>
  <div component="$UI/system/components/justep/labelEdit/labelEdit" class="x-label-edit x-label30" xid="labelInput2">
   <label class="x-label" xid="endtime" bind-text="datastklabel.label('endtime')"></label>
   <input component="$UI/system/components/justep/input/input" class="form-control x-edit" xid="etinput" dataType="DateTime"></input></div>
  
  <div component="$UI/system/components/justep/button/buttonGroup" class="btn-group btn-group-justified" tabbed="true" xid="buttonGroup3"><a component="$UI/system/components/justep/button/button" class="btn x-orange" label="Search" xid="stksearch" icon="icon-android-search" onClick="btnStkSearch">
   <i xid="i5" class="icon-android-search"></i>
   <span xid="span6">Search</span></a></div></div>
  <div component="$UI/system/components/justep/controlGroup/controlGroup" class="x-control-group" title="资金流动图" xid="controlGroup5" style="height:100%;width:100%;">
   <div class="x-control-group-title" xid="controlGroupTitle5">
    <span xid="span11"><![CDATA[title]]></span></div> 
  <div xid="stkgdiv" class="main" style="display:inline-block;width:100%;height:100%;"></div></div></div>
        <div class="x-contents-content" xid="market"/>  
        <div class="x-contents-content" xid="mine"/> 
      </div> 
    </div> 
  </div> 
</div>
