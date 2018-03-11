package com.ft.csv.resources.item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.ft.csv.resources.StringExt;

import engine.util.ResourceBase;

public class PaintingDecal extends ResourceBase {

	public List<Short> pos;

	@Override
	public void parse(Map<String, String> it) {
		id = StringExt.getInt(it.get("---"));
		setpos(it);
	}

	private void setpos(Map<String, String> it) {
		String str = it.get("Sockets");
		if (str != null) {
			String[] strArr = str.replaceAll("\\)\\,\\(", "\\|").replaceAll("\\(", "").replaceAll("\\)", "").split("\\|");
			pos = new ArrayList<Short>(strArr.length);
			for (String s : strArr) {
				pos.add(convert(s.split("\\,")[0]));
			}
			Collections.sort(pos);
		}
	}

	private short convert(String str) {
		switch (str) {
		case "BodyFront":
			return 3;
		case "TurretFront":
			return 4;
		case "TurretBack":
			return 5;
		case "TurretLeft":
			return 6;
		case "TurretRight":
			return 7;
		default:
			return -1;
		}
	}

	@Override
	public String toString() {
		return "PaintingDecal [id=" + id + ", pos=" + pos + "]";
	}

}
