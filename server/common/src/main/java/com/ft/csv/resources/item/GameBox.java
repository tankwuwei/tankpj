package com.ft.csv.resources.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ft.csv.resources.StringExt;

import engine.util.ResourceBase;

public class GameBox extends ResourceBase {

	public int Count;

	public List<Integer> GamePropID;
	public List<Double> Probability;

	@Override
	public void parse(Map<String, String> it) {
		id = StringExt.getInt(it.get("---"));
		Count = StringExt.getInt(it.get("Count"));

		setContent(it);
	}

	private void setContent(Map<String, String> it) {
		String str = it.get("Content");
		if (str != null) {
			String[] strArr = str.replaceAll("\\)\\,\\(", "\\|").replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("\"", "").replaceAll("GamePropID=", "").replaceAll("Probability=", "").split("\\|");
			GamePropID = new ArrayList<Integer>(strArr.length);
			Probability = new ArrayList<Double>(strArr.length);
			for (String val : strArr) {
				String[] arr = val.split(",");
				GamePropID.add(Integer.valueOf(arr[0]));
				Probability.add(Double.valueOf(arr[1]));

			}
		}
	}

	@Override
	public String toString() {
		return "GameBox [id=" + id + ", Count=" + Count + ", GamePropID=" + GamePropID + ", Probability=" + Probability + "]";
	}

}
