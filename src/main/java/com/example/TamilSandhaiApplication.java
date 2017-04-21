package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootApplication
public class TamilSandhaiApplication {
	public static void main(String[] args) {


		SpringApplication.run(TamilSandhaiApplication.class, args);

		/*String s="<div class=\"brdb PB5\">\n"
			+ "                                                                                                       "
			+ "                                 <div class=\"FL gL_10 bseArr PT4 w70\">\n"
			+ "                                                                                                       "
			+ "                                     <div class=\"FR MT2 live\">LIVE</div>\n"
			+ "                                                                                                       "
			+ "                                     <div class=\"FR PR3\">\n"
			+ "                                                                                                       "
			+ "                                         <span class=\"bl_15\">\n"
			+ "                                                                                                       "
			+ "                                             <strong>NSE</strong>\n"
			+ "                                                                                                       "
			+ "                                         </span>\n"
			+ "                                                                                                       "
			+ "                                     </div>\n"
			+ "                                                                                                       "
			+ "                                     <div  id=\"nse_upd_time\" class=\"CL\" style=\"font-size:11px;"
			+ "line-height:2;\">Apr 20, 15:58</div>\n"
			+ "                                                                                                       "
			+ "                                 </div>\n"
			+ "                                                                                                       "
			+ "                                 <div  id=\"Nse_Prc_tick_div\" class=\"FL PR5 gD_30\">\n"
			+ "                                                                                                       "
			+ "                                     <span class=\"PA2\" id=\"Nse_Prc_tick\">\n"
			+ "                                                                                                       "
			+ "                                         <strong>2325.50</strong>\n"
			+ "                                                                                                       "
			+ "                                     </span>\n"
			+ "                                                                                                       "
			+ "                                 </div>\n"
			+ "                                                                                                       "
			+ "                                 <div id=\"n_changetext\" class=\"FL gL_13 PT15\">\n"
			+ "                                                                                                       "
			+ "                                     <span class=\"gr_15 uparw_pc\">\n"
			+ "                                                                                                       "
			+ "                                         <strong>26.35</strong>\n"
			+ "                                                                                                       "
			+ "                                     </span> (+1.15%)\n"
			+ "                                                                                                       "
			+ "                                 </div>";



		Pattern pattern =//= Pattern.compile("Nse_Prc_tick_div(.*?)span");
		Pattern.compile(".*Nse_Prc_tick\"([^']*)/strong>.*");
		Matcher matcher = pattern.matcher(s);
		if (matcher.find())
		{
			System.out.println(matcher.group(0));
		}*/
	}


}
