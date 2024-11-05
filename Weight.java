package s;

public class Weight {
	int _kilos, _grams;
	final int MIN_GRAM = 0;
	final int MAX_GRAM = 999;
	final int MIN_KG = 1;
	final int DEFAULT_VALUE_KG = 1;
	final int DEFAULT_VALUE_GR = 0;


	public Weight(int kilos, int grams) {
		this._kilos = kilos >= MIN_KG ? kilos : DEFAULT_VALUE;
		this._grams = grams >= MIN_GRAM && grams <= MAX_GRAM ? grams : DEFAULT_VALUE;

	}

	public Weight(Weight other) {
		this._kilos = other._kilos;
		this._grams = other._grams;
	}

	public Weight(int totalGrams) {
		if (totalGrams >= MIN_GRAM) {
			this._kilos = totalGrams / 1000; // convert the KG to grams
			this._grams = totalGrams % 1000; // What is "left over" from dividing by 1000 is the gram
		} else {
			this._kilos = DEFAULT_VALUE_KG;
			this._grams = DEFAULT_VALUE_GR;
		}
	}

	public int getKilos() {
		return this._kilos;
	}

	public int getGrams() {
		return this._grams;
	}

	public boolean equals(Weight other) {
		return ((this._grams == other._grams) && (this._kilos == other._kilos));
	}

	public boolean lighter(Weight other) {
		if (this._kilos < other._kilos) {
			return true;
		}
		if (this._kilos == other._kilos) {
			return this._grams < other._grams;
		}
		return false; // In this case, this._kilos > other._kilos...
	}

	public boolean heavier(Weight other) {
		return other.lighter(this); // If "other" is lighter, then "this" is heavier
	}

	public String toString() {
		return "" + (this._kilos + (double) this._grams / 1000);

	}

	public Weight add(int grams) {
		final int MINIMUM_WEIGHT = 1000; //Minimum weight in grams
		int gramConversion = this._grams + this._kilos * 1000; // Convert the weight to grams only.
		return gramConversion + grams >= MINIMUM_WEIGHT ? new Weight(gramConversion + grams) : new Weight(gramConversion);
	}
}
