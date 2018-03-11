package com.ft.gmserver.pages.gm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ft.gmserver.Center.CenterClient;
import com.ft.gmserver.Center.CenterManager;
import com.ft.gmserver.pages.pagebase;
import com.ft.gmserver.pages.paging;
import com.ft.gmserver.struct.structShopItem;

import engine.bean.BeanFactory;
import engine.log.LogUtil;
import engine.string.StringUtil;
import generated.cgame.packets.objects.ShopItemList;

public class pageshopitem extends pagebase {

	public String content(String param) {
		CenterClient centerClient = BeanFactory.getBean(CenterManager.class).getCenterClient_GameServer();
		centerClient.getShopItem();

		int pageNumber = 1;
		String propid = null;
		String type = null;
		String onsale = null;
		if (param != null) {
			Map<String, String> params = StringUtil.split(param, "&", "=");
			pageNumber = params.get("page") == null ? 1 : Integer.parseInt(params.get("page"));
			propid = params.get("propid");
			type = params.get("type");
			onsale = params.get("onsale");
		}
		try {
			List<ShopItemList> list = centerClient.shopitemList;

			List<ShopItemList> propidlist = new ArrayList<ShopItemList>();
			if (StringUtils.isNotEmpty(propid)) {
				for (ShopItemList o : list) {
					if (o.propid == Integer.parseInt(propid))
						propidlist.add(o);
				}
			} else {
				propidlist.addAll(list);
			}

			List<ShopItemList> typelist = new ArrayList<ShopItemList>();
			if (StringUtils.isNotEmpty(type)) {
				for (ShopItemList o : propidlist) {
					if (o.type == Short.parseShort(type))
						typelist.add(o);
				}
			} else {
				typelist.addAll(propidlist);
			}

			List<ShopItemList> onsalelist = new ArrayList<ShopItemList>();
			if (StringUtils.isNotEmpty(onsale)) {
				for (ShopItemList o : typelist) {
					if (o.onsale == Short.parseShort(onsale))
						onsalelist.add(o);
				}
			} else {
				onsalelist.addAll(typelist);
			}

			List<ShopItemList> pagelist = new ArrayList<ShopItemList>();
			pagelist.addAll(onsalelist.subList((pageNumber - 1) * pageSize, (pageNumber - 1) * pageSize + pageSize < onsalelist.size() ? (pageNumber - 1) * pageSize + pageSize : onsalelist.size()));

			List<structShopItem> all = new ArrayList<>();
			for (ShopItemList o : pagelist) {
				all.add(new structShopItem(o));
			}

			paging paging = new paging(pageNumber, pageSize, onsalelist.size());

			context.put("all", all);
			context.put("pager", paging);
			context.put("propid", propid);
			context.put("type", type);
			context.put("onsale", onsale);
			StringBuilder paras = new StringBuilder();
			paras.append("propid=");
			if (propid != null)
				paras.append(propid);
			paras.append("&type=");
			if (type != null)
				paras.append(type);
			paras.append("&onsale=");
			if (onsale != null)
				paras.append(onsale);
			context.put("paras", paras.toString());
			velocityEngine.mergeTemplate("templates/gm/shopitem.vm", "utf-8", context, sw);
		} catch (Exception e) {
			LogUtil.warn(e);
		}
		return sw.toString();
	}

	@Override
	public String content() {
		// TODO Auto-generated method stub
		return null;
	}

}
