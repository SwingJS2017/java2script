$_L(["$wt.internal.SWTEventObject"],"$wt.graphics.ImageLoaderEvent",null,function(){
c$=$_C(function(){
this.imageData=null;
this.incrementCount=0;
this.endOfImage=false;
$_Z(this,arguments);
},$wt.graphics,"ImageLoaderEvent",$wt.internal.SWTEventObject);
$_K(c$,
function(source,imageData,incrementCount,endOfImage){
$_R(this,$wt.graphics.ImageLoaderEvent,[source]);
this.imageData=imageData;
this.incrementCount=incrementCount;
this.endOfImage=endOfImage;
},"$wt.graphics.ImageLoader,$wt.graphics.ImageData,~N,~B");
$_V(c$,"toString",
function(){
return"ImageLoaderEvent {source="+this.source+" imageData="+this.imageData+" incrementCount="+this.incrementCount+" endOfImage="+this.endOfImage+"}";
});
});
