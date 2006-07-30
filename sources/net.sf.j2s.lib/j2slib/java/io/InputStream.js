$_L(null,"java.io.InputStream",["java.io.IOException","java.lang.IndexOutOfBoundsException","$.NullPointerException"],function(){
c$=$_T(java.io,"InputStream");
$_M(c$,"read",
function(b){
return this.read(b,0,b.length);
},"~A");
$_M(c$,"read",
function(b,off,len){
if(b==null){
throw new NullPointerException();
}else if((off<0)||(off>b.length)||(len<0)||((off+len)>b.length)||((off+len)<0)){
throw new IndexOutOfBoundsException();
}else if(len==0){
return 0;
}var c=this.read();
if(c==-1){
return-1;
}b[off]=c;
var i=1;
try{
for(;i<len;i++){
c=this.read();
if(c==-1){
break;
}if(b!=null){
b[off+i]=c;
}}
}catch(e){
if($_O(e,java.io.IOException)){
}else{
throw e;
}
}
return i;
},"~A,~N,~N");
$_M(c$,"skip",
function(n){
var remaining=n;
var nr;
if(java.io.InputStream.skipBuffer==null)($t$=java.io.InputStream.skipBuffer=$_A(2048,0),java.io.InputStream.prototype.skipBuffer=java.io.InputStream.skipBuffer,$t$);
var localSkipBuffer=java.io.InputStream.skipBuffer;
if(n<=0){
return 0;
}while(remaining>0){
nr=this.read(localSkipBuffer,0,Math.min(2048,remaining));
if(nr<0){
break;
}remaining-=nr;
}
return n-remaining;
},"~N");
$_M(c$,"available",
function(){
return 0;
});
$_M(c$,"close",
function(){
});
$_M(c$,"mark",
function(readlimit){
},"~N");
$_M(c$,"reset",
function(){
throw new java.io.IOException("mark/reset not supported");
});
$_M(c$,"markSupported",
function(){
return false;
});
$_S(c$,
"SKIP_BUFFER_SIZE",2048,
"skipBuffer",null);
});
