KindEditor.plugin('serverImgs',function(K){
	var self=this;				
	self.clickToolbar('serverImgs',function(){ // 点击图标时执行
		PhotoGallery.custom=null;
		PhotoGallery.show(self);
		
	})
});