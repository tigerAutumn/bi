$(window).load(function(){
	var pack_active=$(".pack_active");
	var pack_ul=$(".pack_ul");
	var pack_list=$(".pack_list");
	var pack_money=function(){
		pack_ul.stop().slideToggle();
	};
	pack_active.on("click",function(){
		pack_money();
		return false;
	});
	pack_list.on("click",function(){
		pack_money();
		pack_active.html($(this).html());
		var money = parseFloat($("#buyAmount").val());
		var subtract = parseFloat(pack_active.find('.full_sub').attr('subtract'));
		var full = parseFloat(pack_active.find('.full_sub').attr('full'));
		var red_packet_id = null;
		if(pack_active.find('.full_sub').attr('red_packet_id')) {
			red_packet_id = parseFloat(pack_active.find('.full_sub').attr('red_packet_id'));
		} else {
			var red_packet_id = '';
		}
		var all = money - subtract;
		$(".red_pack_sub").text("-"+subtract+"元");
		$(".red_pack_all").text(all+"元");
		$("#redPacketId").val(red_packet_id);
		return false;
	});
	$('.con_zf2').on("click",function(){
		var true_false=$(".z2con_m:not(:hidden)").find(".pack_ul").is(":hidden");
		if(true_false){
			return false;
		}else{
			pack_money();
		}
	})
	
})