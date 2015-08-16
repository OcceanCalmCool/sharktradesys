<?xml version="1.0" encoding="utf-8"?>
<div xmlns="http://www.w3.org/1999/xhtml" xid="window" class="window" component="$UI/system/components/justep/window/window"
  design="device:mobile">  
  <div component="$UI/system/components/justep/model/model" xid="model" style="height:auto;left:704px;top:55px;"> 
    <span component="$UI/system/components/justep/windowReceiver/windowReceiver"
      xid="windowReceiver1" onReceive="windowReceiver"/>  
    <div component="$UI/system/components/justep/data/data" autoLoad="true"
      xid="tradedata" idColumn="time"> 
      <column label="选择时间" name="time" type="DateTime" xid="xid1"/>  
      <column label="选择模型代码" name="modeltype" type="Integer" xid="xid2"/>  
      <column label="选择结果" name="searchret" type="String" xid="xid3"/>  
      <data xid="default1">[]</data> 
    </div>  
    <div component="$UI/system/components/justep/data/data" autoLoad="true"
      xid="stknumlistdata" idColumn="stkcode"> 
      <column label="选择时间" name="time" type="DateTime" xid="xid4"/>  
      <column label="股票代码" name="stkcode" type="String" xid="xid5"/> 
    </div> 
  <div component="$UI/system/components/justep/data/data" autoLoad="true" xid="stktraderec" idColumn="code"><column label="时间" name="time" type="DateTime" xid="xid6"></column>
  <column label="代码" name="code" type="String" xid="xid7"></column>
  <column label="内盘" name="inner" type="Integer" xid="xid8"></column>
  <column label="外盘 " name="outter" type="Integer" xid="xid9"></column>
  <column label="当前价格" name="curprice" type="Float" xid="xid10"></column></div></div>  
  <span component="$UI/system/components/justep/windowDialog/windowDialog" xid="indexReturn"
    src="$UI/sharkstrade/index.w"/>  
  <div component="$UI/system/components/justep/panel/panel" class="x-panel x-full"
    xid="panel1"> 
    <div class="x-panel-top" xid="top1"> 
      <div component="$UI/system/components/justep/titleBar/titleBar" class="x-titlebar"
        xid="titleBar1" title="模型结果"> 
        <div class="x-titlebar-left" xid="div1"> 
          <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-only-icon"
            label="button" xid="closebtn" icon="icon-chevron-left" onClick="returnClick"> 
            <i xid="i1" class="icon-chevron-left"/>  
            <span xid="span1"/> 
          </a> 
        </div>  
        <div class="x-titlebar-title" xid="div2">模型结果</div>  
        <div class="x-titlebar-right reverse" xid="div3"/> 
      </div> 
    </div>  
    <div class="x-panel-content" xid="content1"> 
      <div component="$UI/system/components/justep/contents/contents" class="x-contents x-full"
        active="0" xid="contents1"> 
        <div class="x-contents-content" xid="listcontent"> 
          <div component="$UI/system/components/justep/panel/panel" class="x-panel x-full"
            xid="panel2"> 
            <div class="x-panel-content xui-hignlight-selected x-scroll-view"
              xid="content4" style="bottom: 0px; top: 0px;"> 
              <div class="x-scroll" component="$UI/system/components/justep/scrollView/scrollView"
                xid="scrollView1"> 
                <div class="x-content-center x-pull-down container" xid="div4" bind-visible=' $model.stknumlistdata.val("stkcode")'> 
                  <i class="x-pull-down-img glyphicon x-icon-pull-down" xid="i3"/>  
                  <span class="x-pull-down-label" xid="span3">下拉刷新...</span> 
                </div>  
                <div class="x-scroll-content" xid="div5" bind-visible=' $model.stknumlistdata.val("stkcode")'> 
                  <div component="$UI/system/components/justep/list/list" class="x-list"
                    xid="list1" data="stknumlistdata"> 
                    <ul class="x-list-template" xid="listTemplateUl1"> 
                      <li xid="li1"> 
                        <div component="$UI/system/components/justep/row/row"
                          class="x-row" style="border-color:#008000 #008000 #008000 #008000;" xid="row1"> 
                          <div class="x-col" xid="col2"> 
                            <div component="$UI/system/components/justep/output/output"
                              class="x-output" xid="stkcodeoutput" bind-ref="ref('stkcode')"
                              style="width:207px;" bind-value=' $object.val("stkcode")'/> 
                          </div>  
                          <div xid="col1" class="x-col"> 
                            <a component="$UI/system/components/justep/button/button"
                              class="btn x-orange btn-only-icon" xid="showinfo"
                              icon="icon-android-send" onClick="btnInfoClick" target="detailinfo"> 
                              <i xid="i5" class="icon-android-send"/>  
                              <span xid="span6"></span> 
                            </a> 
                          </div> 
                        </div> 
                      </li> 
                    </ul> 
                  </div> 
                </div>  
                <div class="x-content-center x-pull-up" xid="div6" bind-visible=' $model.stknumlistdata.val("stkcode")'> 
                  <span class="x-pull-up-label" xid="span4">加载更多...</span> 
                </div> 
              </div> 
            <span xid="span7" bind-visible='!($model.stknumlistdata.val("stkcode"))' style="background-color:#408080;background-image:none;font-family:Calibri;color:#FFFFFF;"><![CDATA[未找到符合条件的股票]]></span></div> 
          </div> 
        </div>  
        <div class="x-contents-content" xid="detailinfo" onActive="showGraph">
        	<div xid="main" style="display:inline-block;height:100%;width:100%;" class="x-full"/>
         </div> 
      </div> 
    </div>  
    <div class="x-panel-bottom" xid="bottom1"> 
      <div component="$UI/system/components/justep/button/buttonGroup" class="btn-group btn-group-justified"
        tabbed="true"> 
        <a component="$UI/system/components/justep/button/button" class="btn btn-default"
          label="列表" xid="listinfo" target="listcontent"> 
          <i xid="i2"/>  
          <span xid="span2">列表</span>
        </a>  
        <a component="$UI/system/components/justep/button/button" class="btn btn-default"
          label="详情" xid="detail" target="detailinfo"> 
          <i xid="i4"/>  
          <span xid="span5">详情</span> 
        </a> 
      </div> 
    </div> 
  </div> 
</div>
