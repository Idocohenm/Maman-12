package s;

public class Baby {
	String _firstName, _lastName, _id;
	Date _dateOfBirth;
	Weight _birthWeight, _currentWeight;
	final String DEFAULT_ID = "000000000";
	final int MONTH_0_TO_2_GAIN_PER_DAY = 30;
	final int MONTH_2_TO_4_GAIN_PER_MONTH = 750;
	final int MONTH_4_TO_8_GAIN_PER_MONTH = 500;
	final int MONTH_8_TO_12_GAIN_PER_MONTH = 250;

	public Baby(String fName, String lName, String id, int day, int month, int year, int birthWeightInGrams) {
		this._firstName = fName;
		this._lastName = lName;
		this._id = id.length() == 9 ? id : DEFAULT_ID;
		this._dateOfBirth = new Date(day, month, year);
		this._birthWeight = new Weight(birthWeightInGrams);
		this._currentWeight = new Weight(_birthWeight);
	}

	public Baby(Baby other) {
		this._firstName = other._firstName;
		this._lastName = other._lastName;
		this._id = other._id;
		this._dateOfBirth = new Date(other._dateOfBirth);
		this._birthWeight = new Weight(other._birthWeight);
		this._currentWeight = new Weight(other._currentWeight);
	}

	public String getFirstName() {
		return this._firstName;
	}

	public String getLastName() {
		return this._lastName;
	}

	public String getId() {
		return this._id;
	}

	public Date getDateOfBirth() {
		return new Date(this._dateOfBirth);
	}

	public Weight getBirthWeight() {
		return new Weight(this._birthWeight);
	}

	public Weight getCurrentWeight() {
		return new Weight(this._currentWeight);
	}

	public void setCurrentWeight(Weight weightToSet) {
		this._currentWeight = new Weight(weightToSet);
	}

	public String toString() {
		return "Name: " + this._firstName + " " + this._lastName + "\n" + "Id: " + this._id + "\n" + "Date of Birth: "
				+ this._dateOfBirth + "\n" + "Birth Weight: " + this._birthWeight + "\n" + "Current Weight: "
				+ this._currentWeight + "\n";
	}

	public boolean equals(Baby other) {
		return this._firstName.equals(other._firstName) && this._lastName.equals(other._lastName)
				&& this._dateOfBirth.equals(other._dateOfBirth) && this._id.equals(other._id);
	}

	public boolean areTwins(Baby other) {
		return this._lastName.equals(other._lastName) && !this._firstName.equals(other._firstName)
				&& this._id.equals(other._id) && this._dateOfBirth.difference(other._dateOfBirth) <= 1;
	}

	public boolean heavier(Baby other) {
		return this._currentWeight.heavier(other._currentWeight);
	}

	public void updateCurrentWeight(int grams) {
		this._currentWeight = this._currentWeight.add(grams);
	}

	public boolean older(Baby other) {
		return this._dateOfBirth.before(other._dateOfBirth);
	}

	public int isWeightInValidRange(Date date) {
		double curWeightGrams = this._currentWeight.getGrams() + this._currentWeight.getKilos() * 1000;
		double birthWeightGrams = this._birthWeight.getGrams() + this._birthWeight.getKilos() * 1000;
		if (date.before(this._dateOfBirth)) //Date is before his birth day, not valid
			return 1;
		if (daysSinceEpoch(date) - daysSinceEpoch(this._dateOfBirth) >= 365) //Baby is already 1 years old or more
			return 2;
		else {
			return minimumExpectedWeight(daysSinceEpoch(date) - daysSinceEpoch(this._dateOfBirth) , birthWeightGrams) <= curWeightGrams ? 4 : 3;
		}//Checks if the expected weight is less or more then current weight
	}
	
	//------------------------------------------private methods---------------------------------------------------------------------------//
	//Calculate the days since epoch (0/0/0)
	private int daysSinceEpoch(Date date) {
		final int DAYS_IN_MONTH = 30; final int DAYS_IN_YEAR = 365;
		return date.getDay() + date.getMonth() * DAYS_IN_MONTH + date.getYear() * DAYS_IN_YEAR;
	}
	
	//Calculate the minimum expected weight, given the age of the baby and the born weight
	private double minimumExpectedWeight(int daysSinceBirth, double birthWeightExpected) {

		birthWeightExpected *= 0.9; 
		if (daysSinceBirth <= 7)  
			// First week of birth
			return birthWeightExpected;
		if (daysSinceBirth <= 60) { 
			//between month 0-2, everyday +30grams
			return birthWeightExpected + (MONTH_0_TO_2_GAIN_PER_DAY * (daysSinceBirth - 7)); 
		}
		if (daysSinceBirth <= 120) {
			//month 2-4, every month +750grams
			return birthWeightExpected + (MONTH_0_TO_2_GAIN_PER_DAY * 60 + 
					MONTH_2_TO_4_GAIN_PER_MONTH * ((daysSinceBirth - 60) / 30)); 
		}
		if (daysSinceBirth <= 240) {
			//month 4-8, every month +500grams
			return birthWeightExpected + (MONTH_0_TO_2_GAIN_PER_DAY * 60 + MONTH_2_TO_4_GAIN_PER_MONTH * 2 
					+ MONTH_4_TO_8_GAIN_PER_MONTH * (daysSinceBirth - 120) / 30);
		} else {
			//month 8-12, every month +250grams
			return birthWeightExpected + (MONTH_0_TO_2_GAIN_PER_DAY * 60 + MONTH_2_TO_4_GAIN_PER_MONTH * 2 + MONTH_4_TO_8_GAIN_PER_MONTH * 4
					+ MONTH_8_TO_12_GAIN_PER_MONTH * ((daysSinceBirth - 240) / 30)); 
		}

	}
}
