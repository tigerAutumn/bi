(function() {
	var items = $('.ask-help');
	var sdt = items.find('dt');
	var sdd = items.find('dd');
	var cur = 'current';
	sdt.css('cursor', 'pointer');
	sdt.append('<span class="status-tag"><b class="arrows-down"></b><b class="arrows-down-in"></b></span>');
	var param = Util.getUrlKey('y');
	if(param != ''){
		var dl = $('#'+param);
		sdt.removeClass(cur);
		sdd.hide();
		dl.find('dt').addClass(cur);
		dl.find('dd').show();
	};
	sdt.on('click', function(){
		var self = $(this);
		var sib = self.siblings('dd');
		sdt.removeClass(cur);
		if(sib.is(':hidden')){
			self.addClass(cur);
			sdd.hide();
			sib.show();
		} else{
			sib.hide();
		}
	});
	/*items.each( function(){
		var self = $(this);
		var dt = self.find('dt');
		var dd = self.find('dd');
		dt.on('click', function(){
			if(dd.is(':hidden')){
				sdt.removeClass(cur);
				sdd.hide();
				dt.addClass(cur);
				dd.show();
			} else{
				dt.removeClass(cur);
				dd.hide();
			}
		});
	});*/
})();