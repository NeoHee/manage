KindEditor.plugin('serverImgs',function(K){
	var self=this;				
	self.clickToolbar('serverImgs',function(){ // ���ͼ��ʱִ��
		PhotoGallery.custom=null;
		PhotoGallery.show(self);
		
	})
});