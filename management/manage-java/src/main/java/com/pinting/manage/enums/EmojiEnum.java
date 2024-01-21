package com.pinting.manage.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum EmojiEnum {
	
	
	smile("&lt;img alt=\"微笑\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/smile.gif\" /&gt;", "/::)"), //微笑
	tongue("&lt;img alt=\"吐舌头\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/tongue.gif\" /&gt;", "/::P"), //调皮
	titter("&lt;img alt=\"偷笑\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/titter.gif\" /&gt;", "/:,@P"), ///偷笑
	laugh("&lt;img alt=\"大笑\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/laugh.gif\" /&gt;", "/::D"),  //呲牙
	sad("&lt;img alt=\"难过\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/sad.gif\" /&gt;", "/::("),  //难过
	wronged("&lt;img alt=\"委屈\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/wronged.gif\" /&gt;", "/:P-("), //委屈
	fastcry("&lt;img alt=\"快哭了\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/fastcry.gif\" /&gt;", "/::'|"),  //快哭了
	cry("&lt;img alt=\"哭\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/cry.gif\" /&gt;", "/::<"),  //流泪
	wail("&lt;img alt=\"大哭\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/wail.gif\" /&gt;", "/::'("),  //大哭
	mad("&lt;img alt=\"生气\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/mad.gif\" /&gt;", "/::+"), //酷
	
	knock("&lt;img alt=\"敲打\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/knock.gif\" /&gt;", "/:xx"), //敲打
	curse("&lt;img alt=\"骂人\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/curse.gif\" /&gt;", "/::-S"), //咒骂
	crazy("&lt;img alt=\"抓狂\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/crazy.gif\" /&gt;", "/::Q"), //抓狂
	angry("&lt;img alt=\"发火\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/angry.gif\" /&gt;", "/::@"), //发怒
	ohmy("&lt;img alt=\"惊讶\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/ohmy.gif\" /&gt;", "/::O"),  //惊讶
	
	awkward("&lt;img alt=\"尴尬\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/awkward.gif\" /&gt;", "/::-|"), //尴尬
	panic("&lt;img alt=\"惊恐\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/panic.gif\" /&gt;", "/::!"),   //惊恐
	shy("&lt;img alt=\"害羞\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/shy.gif\" /&gt;", "/::$"),  //害羞
	cute("&lt;img alt=\"可怜\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/cute.gif\" /&gt;", "/:8*"), //可怜
	envy("&lt;img alt=\"羡慕\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/envy.gif\" /&gt;", "/::B"),   //色
	
	proud("&lt;img alt=\"得意\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/proud.gif\" /&gt;", "/:8-)"),  //得意
	struggle("&lt;img alt=\"奋斗\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/struggle.gif\" /&gt;", "/:,@f"),  //奋斗
	quiet("&lt;img alt=\"安静\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/quiet.gif\" /&gt;", "/:,@x"),   //嘘
	shutup("&lt;img alt=\"闭嘴\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/shutup.gif\" /&gt;", "/::X"),   //闭嘴
	doubt("&lt;img alt=\"疑问\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/doubt.gif\" /&gt;", "/:?"),  //疑问
	
	despise("&lt;img alt=\"鄙视\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/despise.gif\" /&gt;", "/:>-|"),   //鄙视
	sleep("&lt;img alt=\"睡觉\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/sleep.gif\" /&gt;", "/::Z"),  //睡
	bye("&lt;img alt=\"再见\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/bye.gif\" /&gt;", "/:bye");  //再见
	
/*	smile("&lt;img alt=\"微笑\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/smile.gif\" /&gt;", "/微笑"),
	tongue("&lt;img alt=\"吐舌头\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/tongue.gif\" /&gt;", "/调皮"),
	titter("&lt;img alt=\"偷笑\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/titter.gif\" /&gt;", "/偷笑"),
	laugh("&lt;img alt=\"大笑\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/laugh.gif\" /&gt;", "/呲牙"),
	sad("&lt;img alt=\"难过\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/sad.gif\" /&gt;", "/难过"),
	wronged("&lt;img alt=\"委屈\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/wronged.gif\" /&gt;", "/委屈"),
	fastcry("&lt;img alt=\"快哭了\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/fastcry.gif\" /&gt;", "/快哭了"),
	cry("&lt;img alt=\"哭\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/cry.gif\" /&gt;", "/流泪"),
	wail("&lt;img alt=\"大哭\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/wail.gif\" /&gt;", "/大哭"),
	mad("&lt;img alt=\"生气\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/mad.gif\" /&gt;", "/酷"),
	
	knock("&lt;img alt=\"敲打\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/knock.gif\" /&gt;", "/敲打"),
	curse("&lt;img alt=\"骂人\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/curse.gif\" /&gt;", "/咒骂"),
	crazy("&lt;img alt=\"抓狂\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/crazy.gif\" /&gt;", "/抓狂"),
	angry("&lt;img alt=\"发火\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/angry.gif\" /&gt;", "/发怒"),
	ohmy("&lt;img alt=\"惊讶\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/ohmy.gif\" /&gt;", "/惊讶"),
	
	awkward("&lt;img alt=\"尴尬\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/awkward.gif\" /&gt;", "/尴尬"),
	panic("&lt;img alt=\"惊恐\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/panic.gif\" /&gt;", "/惊恐"),
	shy("&lt;img alt=\"害羞\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/shy.gif\" /&gt;", "/害羞"),
	cute("&lt;img alt=\"可怜\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/cute.gif\" /&gt;", "/可怜"),
	envy("&lt;img alt=\"羡慕\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/envy.gif\" /&gt;", "/色"),
	
	proud("&lt;img alt=\"得意\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/proud.gif\" /&gt;", "/得意"),
	struggle("&lt;img alt=\"奋斗\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/struggle.gif\" /&gt;", "/奋斗"),
	quiet("&lt;img alt=\"安静\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/quiet.gif\" /&gt;", "/嘘"),
	shutup("&lt;img alt=\"闭嘴\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/shutup.gif\" /&gt;", "/闭嘴"),
	doubt("&lt;img alt=\"疑问\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/doubt.gif\" /&gt;", "/疑问"),
	
	despise("&lt;img alt=\"鄙视\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/despise.gif\" /&gt;", "/鄙视"),
	sleep("&lt;img alt=\"睡觉\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/sleep.gif\" /&gt;", "/睡"),
	bye("&lt;img alt=\"再见\" src=\"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/bye.gif\" /&gt;", "/再见");
	*/
	
	
	 
    private EmojiEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    private String code;
    
    private String description;

    private static List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

    static {
        for (EmojiEnum s : EnumSet.allOf(EmojiEnum.class)) {
            Map<String, Object> lookup = new HashMap<String, Object>();
            lookup.put("code", s.getCode());
            lookup.put("description", s.getDescription());
            list.add(lookup);
        }
    }

    public static List<Map<String, Object>> getMapList() {
        return list;
    }
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
