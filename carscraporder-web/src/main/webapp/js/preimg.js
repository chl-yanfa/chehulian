    function preimg(x){
    	var src=x;
    	    for(var i=0;i<20;i++){
	        /*添加图片*/
	        var img=document.createElement("img");
	        img.src=src[i%4];
	        img.className="imglist";
	        img.style.marginLeft="40px";
	        img.style.marginTop="20px";
	        document.getElementsByClassName("block")[0].appendChild(img);
	        img.index = i;
	        /*img点击时添加浮层*/
	        img.onclick=function(){
	            var ceng=document.createElement("div");
	            ceng.className="ceng";
	            document.body.appendChild(ceng);
	            /*创建关闭按钮*/
	            var close=document.createElement("span");
	            close.innerHTML="×";
	            close.className="close";
	            ceng.appendChild(close);
	            /*close.addEventListener("click",function(){
	                ceng.remove();
	            }) //谷歌支持 IE不支持*/
	            close.addEventListener("click",function(){
	                document.body.removeChild(ceng);
	            })
	            //创建预览框
	            var kuang=document.createElement("div");
	            kuang.className="kuang";
	            ceng.appendChild(kuang);
	            //给创建的预览框添加 4个ul li
	            var UL=document.createElement("ul");
	            UL.className="ulkuang";
	            kuang.appendChild(UL);
	            for(var k=0;k<4;k++){
	                var list=document.createElement("li");
	                list.className="list";
	                var liimg=document.createElement("img");
	                liimg.className="liimg";
	                liimg.src=src[k];
	                UL.appendChild(list);
	                list.appendChild(liimg);
	                /*调整图片层级*/
	                list.style.zIndex=3-k;
	            }
	            //添加左右控件
	            var spanleft=document.createElement("span");
	            spanleft.className="left";
	            spanleft.innerHTML="<";
	            kuang.appendChild(spanleft);
	            spanleft.addEventListener("click",showshift);
	            var spanright=document.createElement("span");
	            spanright.className="right";
	            spanright.innerHTML=">";
	            kuang.appendChild(spanright);
	            spanright.addEventListener("click",showshift);
	 
	            //轮换的函数
	            function showshift(e) {
	                if(e.target.className=="left"){
	                    //对象target.className会返回left/right
	                    shift("left");
	                }else {
	                    shift("right");
	                }
	            }
	            function shift(way) { //传way控制向左还是向右翻
	                var Li=document.getElementsByClassName("list");
	                for(var j=0;j<Li.length;j++){
	                    //获取一下
	                    var zindex=parseInt(Li[j].style.zIndex);
	 
	                    if(way=="left"){
	                        //向左翻  给每个-1 小于0时 设置为3 再赋值
	                        zindex-=1;
	                        if(zindex<0){
	                            zindex=3;
	                        }
	                    }else {
	                        //向右翻 给每个+1 大于3时 设置为0 再赋值
	                        zindex+=1;
	                        if(zindex>3){
	                            zindex=0;
	                        }
	                    }
	                    Li[j].style.zIndex=zindex;
	                }
	            }
	            /*任意点击一张显示对应的预览图*/
	            for (var b=0;b<this.index%4;b++) {  //this.index===img.index; img.zIndex=i;
	                shift("right");
	            }
	        }
	    }
    }
