package fr.robotv2.gts.ui;

import java.util.Comparator;

import fr.robotv2.gts.object.Sell;

public class timeComparator implements Comparator<Sell>{

	@Override
	public int compare(Sell o1, Sell o2) {
		return (int) (o2.getStart() - o1.getStart());
	}

}
